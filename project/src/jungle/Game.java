package jungle;

import java.io.*;

public class Game {

    private final int NUM_PLAYER = 2;
    private Board board;
    private Player[] player;
    boolean isSaved;
    private int turn;  // Indicate which player moves currently

    public Game(){
        board = new Board();
        player = new Player[NUM_PLAYER];
        for(int i=0;i<NUM_PLAYER;i++)
            player[i] = new Player(Player.DEFAULT_NAME[i]);
        turn = 1;
        isSaved = false;
    }

    public void Load(Game other){
        board = other.board;
        player = other.player;
        turn = other.turn;
    }

    public void setPlayer(int id, String name){
            player[id].setName(name);
            //System.out.println("id: " + id + " " + player[id].getName());
    }

    public Player getPlayer(int id){
        return player[id];
    }

    public void save(String path){

        File file = new File(path);
        OutputStream output = null;

        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(file));

            for(int i=0;i<NUM_PLAYER;i++) { // Output Players information
                out.write(player[i].getName());
                out.write("\n");
            }

            out.write(String.valueOf(turn)); // Output the board
            out.write(board.toString());
            out.write("\n");

            out.flush();
            out.close();
        }

        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void load(String path){

    }

    public Board getBoard(){return board;}



    /*
    Meaning of returned value:
    0: OK
    1: Cannot move outside the board
    2: Cannot move the piece that you don't own
    3: Cannot move to the position occupied by your piece
    4: You must move the piece to an adjacent position
    5: This piece cannot move to the river
    6: Cannot move piece to our den
    7: Cannot capture piece with higher ranks and not in your traps.
    8: The rat cannot capture elephant from river.
    9: Rats inside river. Cannot jump across the river
    10: Lion and Tight cannot move in that way.
     */
    public int isValidMove(Tuple from, Tuple to)
    {
        int moveCode = 0;

        if(board.inBoard(from) && board.inBoard(to)) {
            Piece currentPiece = board.getPiece(from), toPosition = board.getPiece(to);
            if (currentPiece.getOwner() == turn) {
                if (board.inBoard(to)) {
                    if (board.getPiece(to).getOwner() != turn) {
                        int specialCode = board.getPiece(from).getSkills();
                        int moveDist = Tuple.distance(from,to);
                        if (moveDist == 1) { // For a normal move, the distance should be exactly 1
                            if (Board.isRiver(to) && specialCode != 1) {
                                moveCode = 5;
                            } else if (board.isOurDen(turn - 1, to)) {
                                moveCode = 6;
                            } else if (currentPiece.CompareTo(toPosition) < 0 && board.isTrap(to) - 1 != turn){
                                moveCode = 7;
                            }else if((currentPiece.getRank() == 1) && (toPosition.getRank() == 8) && Board.isRiver(from)){
                                moveCode = 8;
                            }
                        }

                        else if(specialCode == 2){  // Lion and Tiger
                            if(from.getX() == to.getX()){ // May jump horizontally
                                int x = from.getX();
                                int smallY = from.getY(), largeY = to.getY();
                                if(smallY > largeY){int t = smallY; smallY = largeY;largeY = t;}
                                if(largeY - smallY == 3){
                                    for(int i = smallY + 1; i < largeY ; i++){
                                        if(!(board.isRiver(x,i))){
                                            moveCode = 10;break;
                                        }

                                        if(!(board.getPiece(x, i).isEmpty())){
                                            moveCode = 9;break;
                                        }
                                    }
                                }
                            }

                            else if(from.getY() == to.getY()) {
                                int y = from.getY();
                                int smallX = from.getX(), largeX = to.getX();
                                if(smallX > largeX){int t = smallX; smallX = largeX;largeX = t;}
                                if(largeX - smallX == 3){
                                    for(int i = smallX + 1; i < largeX ; i++){
                                        if(!(board.isRiver(i,y))){
                                            moveCode = 10;break;
                                        }

                                        if(!(board.getPiece(i, y).isEmpty())){
                                            moveCode = 9;break;
                                        }
                                    }
                                }
                            } else moveCode = 10;
                        } else moveCode = 4;
                    }  else moveCode = 3;
                } else moveCode = 2;
            } else moveCode = 1;
        }

        return moveCode;
    }

    /*public void assignInitialPosition(){
        for(int i=1;i<=Piece.TOTAL_PIECES;i++){
        }
    }*/

    public void takeTurn(){
        turn = 3 - turn;
    }

    public int getTurn(){
        return turn;
    }

    public void boardPrint(){
        board.print();
    }
}
