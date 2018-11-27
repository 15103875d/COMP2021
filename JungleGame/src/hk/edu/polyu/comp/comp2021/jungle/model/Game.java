package hk.edu.polyu.comp.comp2021.jungle.model;
import hk.edu.polyu.comp.comp2021.jungle.constants.*;

import java.io.*;

/**
 * The model of the game
 * @link #Game
 *
 */

public class Game {

    private final int NUM_PLAYER = 2;
    private Board board;
    private Player[] player;
    private boolean isSaved;
    private int turn;  // Indicate which player moves currently
    private int winner;

    /**
     * Default constructor of Game
     * No argument Required. Used for new game.
     */
    public Game(){
        board = new Board();
        player = new Player[NUM_PLAYER];
        for(int i=0;i<NUM_PLAYER;i++)
            player[i] = new Player(Player.DEFAULT_NAME[i]);
        turn = 1;
        isSaved = false;
        winner = 0;
    }

    /**
     * Used to set the player's info.
     * @param id {int}The index of array 'player'. Only 0 and 1 will be accepted.
     * @param name {String} The name assigned to the user.
     */
    public void setPlayer(int id, String name){
            player[id].setName(name);
            //System.out.println("id: " + id + " " + player[id].getName());
    }

    /**
     * Used to set the winner.
     * @param winner The winner index. Only 1 or 2 will be accepted.
     */
    public void setWinner(int winner){this.winner = winner;}

    /**
     * Check the winner to end the game.
     * @return The index of winner
     */
    public int getWinner(){return winner;}

    /**
     * Get the player info with an instance of Player.
     * @param id The index of player
     * @return a instance of Player
     */
    public Player getPlayer(int id){
        return player[id];
    }


    /**
     * Save the program status of game to a given path.
     * @param path The path could be relative path or absolute path
     *
     */
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
            out.write("\n");
            out.write(board.toString());
            out.write("\n");

            out.flush();
            out.close();

            isSaved = true;
        }

        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Load the game status from given path
     * @param path The path could be relative path or absolute path
     * @return 0 for normal, 1 for abnormal
     */
    public int load(String path) {
        int returnValue = 0;
        File file = new File(path);
        //InputStream input = null;

        try {
            BufferedReader in = new BufferedReader(new FileReader(file));

            String []players = new String [NUM_PLAYER];
            players[0] = in.readLine();
            players[1] = in.readLine();

            player[0].setName(players[0]);
            player[1].setName(players[1]);

            this.turn = Integer.valueOf(in.readLine());

            String[] board = new String[BoardConstants.ROW_NUM];

            int piece_num1, piece_num2;
            piece_num1 = piece_num2 = 0;

            for(int i=0;i < BoardConstants.ROW_NUM;i++){
                board[i] = in.readLine();

                for(int j=0;j < BoardConstants.COLUMN_NUM; j++){
                    char tmp = board[i].charAt(j);
                    if(!Character.isLetter(tmp)){ this.board.setPiece(Piece.empty, i, j);}
                    else{

                        int owner = 1;
                        if(Character.isLowerCase(tmp))
                        {
                            piece_num2++;
                            owner = 2;
                            tmp = Character.toUpperCase(tmp);
                        }

                        else piece_num1++;

                        int rank = Piece.findPiece(tmp);
                        boolean isInTrap = false;
                        if(Board.isTrap(i, j) > 0 && Board.isTrap(i, j) != owner)isInTrap = true;

                        Piece piece = new Piece(rank, owner, false, isInTrap);
                        this.board.setPiece(piece, i, j);
                    }
                }
            }

            player[0].SetReminingPieces(piece_num1);
            player[1].SetReminingPieces(piece_num2);

            System.out.println("Player 1 has " + player[0].GetReminingPieces() + " pieces.");
            System.out.println("Player 2 has " + player[1].GetReminingPieces() + " pieces.");
            isSaved = true;
        }

        catch(Exception e){
          //  UIMessage.message("No such file.");
            return 1;
           // e.printStackTrace();
        }

        return 0;
    }

    /**
     * Get the board
     * @return The board status.
     */
    public Board getBoard(){return board;}



    /**
     *
     *
     * Check if the move is valid. Different returned value will


     @param from The original place of the piece
     @param to The destination of the piece.

     @return The value of moveCode
     Meaning of returned value:
     0: OK
     1: Cannot move outside the board
     2: Cannot move the piece that you don't own
     3: Cannot move to the position occupied by your piece8
     4: You must move the piece to an adjacent position
     5: This piece cannot move to the river
     6: Cannot move piece to our den
     7: Cannot capture piece with higher ranks and not in your traps.
     8: The rat cannot capture elephant from river.
     9: Rats inside river. Cannot jump across the river
     10: Lion and Tight cannot move in that way.
     11: A Rat can only attack another one when both in water or land.
     */
    public int isValidMove(Tuple from, Tuple to)
    {
        int moveCode = 0;
        Piece currentPiece = Piece.empty, toPosition = Piece.empty;

        if(Board.inBoard(from) && Board.inBoard(to)) {
            currentPiece = board.getPiece(from);
            toPosition = board.getPiece(to);
            if (currentPiece.getOwner() == turn && !currentPiece.getIsRemoved()) {
                if (board.inBoard(to)) {
                    if (board.getPiece(to).getOwner() != turn) {
                        int specialCode = board.getPiece(from).getSkills();
                        int moveDist = Tuple.distance(from,to);
                        if (moveDist == 1) { // For a normal move, the distance should be exactly 1
                            if (Board.isRiver(to) && specialCode != 1) {
                                moveCode = 5;
                            } else if (board.isOurDen(turn - 1, to)) {
                                moveCode = 6;
                            } else if (currentPiece.CompareTo(toPosition) < 0){
                                moveCode = 7;
                            }else if((currentPiece.getRank() == 1) && (toPosition.getRank() == 8) && Board.isRiver(from)){
                                moveCode = 8;
                            }else if((currentPiece.getRank() == 1) && (toPosition.getRank() == 1) && (Board.isRiver(from) != Board.isRiver(to)))
                                moveCode = 10 + 1;
                        }

                        else if(specialCode == 2){  // Lion and Tiger
                            if(from.getX() == to.getX()){ // May jump horizontally
                                int x = from.getX();
                                int smallY = from.getY(), largeY = to.getY();
                                if(smallY > largeY){int t = smallY; smallY = largeY;largeY = t;}
                                if(largeY - smallY == 3){
                                    for(int i = smallY + 1; i < largeY ; i++){
                                        if(!(Board.isRiver(x,i))){
                                            moveCode = 10;break;
                                        }

                                        if(!(board.getPiece(x, i).isEmpty())){
                                            moveCode = 9;
                                            break;
                                        }
                                    }
                                } else moveCode = 10;
                            }
                            else if(from.getY() == to.getY()) {
                                int y = from.getY();
                                int smallX = from.getX(), largeX = to.getX();
                                if(smallX > largeX){int t = smallX; smallX = largeX;largeX = t;}
                                if(largeX - smallX == 4){
                                    for(int i = smallX + 1; i < largeX ; i++){
                                        if(!(Board.isRiver(i,y))){
                                            moveCode = 10;break;
                                        }

                                        if(!(board.getPiece(i, y).isEmpty())){
                                            moveCode = 9;break;
                                        }
                                    }
                                }else moveCode = 10;
                            } else moveCode = 10;
                        } else moveCode = 4;
                    }  else moveCode = 3;
                } else moveCode = 1;
            } else moveCode = 2;
        } else moveCode = 1;

        if(moveCode == 0){ // valid move
            if(board.getPiece(to).getOwner() != 0) {
                toPosition.setIsRemoved(true);
                player[2 - turn].SetReminingPieces(player[2 - turn].GetReminingPieces() - 1);
                if (player[2 - turn].GetReminingPieces() == 0) setWinner(turn);// No piece remains
            }

            if(to.equalTo(BoardConstants.denPosition[2 - turn]))setWinner(turn);

            if(!currentPiece.isEmpty()) {
                if (Board.isTrap(to) == 3 - turn)
                    currentPiece.setInTrap(true);
                else currentPiece.setInTrap(false);

                System.out.println(currentPiece.getInTrap());
            }

            //Set Traps

        }

        //checkWinner();

        return moveCode;
}

    /**
     * Set the value of isSaved.
     * @param status {boolean}
     */
    public void setSaveStatus(boolean status) { isSaved = status;}

    /**
     * Get the save status
     * @return status
     */

    public boolean getSaveStatus() { return isSaved;}

    /**
     * Change the current player
     * Player  are number 1 and 2, so 3 - turn will change it.
     */

    public void takeTurn(){
        turn = 3 - turn;
    }

    /**
     * Get the current turn of player.
     * @return {int} 1 or 2
     */

    public int getTurn(){
        return turn;
    }

    /**
     * Print the board
     */

    public void boardPrint(){
        board.print();
    }
}
