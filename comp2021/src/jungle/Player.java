package jungle;

public class Player {
    public  static final String[] DEFAULT_NAME = {"Alice", "Bob"};
    private String name;
    private int remaining_pieces;

    Player() {
        this.name = "";
        remaining_pieces = Piece.TOTAL_PIECES;
    }

    Player(String name){
        this.name = name;
        remaining_pieces = Piece.TOTAL_PIECES;
    }


    public String GetName(){
        return name;
    }

    public void SetName(String name){
        this.name = name;
    }
    public int GetReminingPieces(){
        return remaining_pieces;
    }

    public void SetReminingPieces(int remaining){
        this.remaining_pieces = remaining;
    }
}
