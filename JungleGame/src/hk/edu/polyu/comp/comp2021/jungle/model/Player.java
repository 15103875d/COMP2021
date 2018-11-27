package hk.edu.polyu.comp.comp2021.jungle.model;

/**
 * A class to describe the player
 * name: the name of player
 * remaining_pieces: number of pieces remained
 */
public class Player {
    /**
     * The default name when no name assigned to the player
     */
    public  static final String[] DEFAULT_NAME = {"Alice", "Bob"};
    private String name;
    private int remaining_pieces;

    /**
     * Constructor of player
     * @param name the name assigned of given player
     */
    Player(String name){
        this.name = name;
        remaining_pieces = Piece.TOTAL_PIECES;
    }

    /**
     * Get the name of a player
     * @return the name of the player
     */
    public String getName(){
        return name;
    }

    /**
     * Set a name of player
     * @param name the name assigned
     */
    public void setName(String name){ this.name = name; }

    /**
     * Get the number of piece a player has.
     * @return the number of pieces remained.
     */
    public int GetReminingPieces(){
        return remaining_pieces;
    }

    /**
     * Set the remining pieces
     * @param remaining the new remaining pieces
     */
    public void SetReminingPieces(int remaining){
        this.remaining_pieces = remaining;
    }



}
