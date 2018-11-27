package hk.edu.polyu.comp.comp2021.jungle.model;

/**
 * Represent the piece
 * Rank is in range [1, 8]
 * Team is the belonging of the piece
 * isRemoved indicated if the piece is removed
 *
 */

public class Piece {
    private int rank;
    private int team;
    private boolean isRemoved;
    private boolean isInTrap;

    /**
     * Each player has 8 piece at game start.
     */
    public static final int TOTAL_PIECES = 8;

    /**
     * Empty blocks regarded as a empty piece with rank and team both 0.
     */
    public static final Piece empty = new Piece(0,0,false,false);

    /**
     * User the first letter to describe the piece. Special case: P for leopard, L for lion.
     */
    public static final String[] NameOfPiece = {".","R","C","D","W","P","T","L","E"};// 'P' for "Leopard"

    /**
     * Assign the special skills for piece.
     * 0 for nil. 1 for rat ( can go across the river). 2 for tight&lion ( can jump across the river)
     */
    public static final int[] TypeOfSpecial = {0,1,0,0,0,0,2,2,0}; // Special rules for pieces. 0 for Nil, 1 for cross the river(rats), 2 for jump across the river(Tiger, Lion).

    /**
     * The initial position of pieces.
     */
    private static final Tuple[] InitPosition;
    static{
        InitPosition = new Tuple[TOTAL_PIECES + 1];
        InitPosition[1] = new Tuple(2,0);
        InitPosition[2] = new Tuple(1,5);
        InitPosition[3] = new Tuple(1,1);
        InitPosition[4] = new Tuple(2,2);
        InitPosition[5] = new Tuple(2,4);
        InitPosition[6] = new Tuple(0,6);
        InitPosition[7] = new Tuple(0,0);
        InitPosition[8] = new Tuple(2,6);
        // The initial pieces are symmetric.
        // X(2, i) = 6 - X(1, i), Y(2, i) = 8 - Y(1, i)

    }


    /**
     * Constructor of a piece
     * @param rank the rank of piece
     * @param team the belonging of the piece
     * @param isRemoved Whether the piece is captured. Initial status: false
     * @param isInTrap Whether the piece is in enemy's traps Initial status: false
     */
  public Piece(int rank, int team, boolean isRemoved, boolean isInTrap) {
      this.rank = rank;
      this.team = team;
      this.isRemoved = isRemoved;
      this.isInTrap = isInTrap;
  }


    /**
     * Find which type of piece with given character
     * @param input a character
     * @return the rank of piece
     */
  public static int findPiece(char input){
        if(!Character.isLetter(input))return 0;
        if(Character.isLowerCase(input))input = Character.toUpperCase(input);

        int rank = 0;
        for(int i=1;i<=TOTAL_PIECES;i++){
            if(NameOfPiece[i].charAt(0) == input){rank = i; break;}
        }

        return rank;
  }

    /**
     * Get the rank of given piece
     * @return {int} the rank of the piece
     */
  public int getRank(){return rank;}

    /**
     * Get the special skills of the piece
     * @return the type of special skills
     */
  public int getSkills(){
        return TypeOfSpecial[rank];
  }

    /**
     * Get the owner the piece
     * @return {int} the owner of the piece
     */
  public int getOwner(){
        return team;
  }

    /**
     * Check whether a piece is in the enemy's traps
     * @return true for in enemy's traps, false for not
     */
  public boolean inTrap(){return isInTrap;}

    /**
     * Get the initial position of the rank. Based on player 1's position.
     * @param rank the rank of a piece
     * @return the initial position stored in tuple.
     */
  public static Tuple getInitialPosition(int rank){
        return InitPosition[rank];
  }

    /**
     * Check the one piece can capture an ememy's piece.
     * @param enemy the enemy's piece
     * @return 1 and 0 for can capture, 0 for not.
     */
  public int CompareTo(Piece enemy){
      //special

    //  System.out.println(rank + " " + enemy.getRank());

      if(rank == 1 && enemy.getRank() == 8)return 1;
      if(rank == 8 && enemy.getRank() == 1)return -1;
      if(rank > enemy.getRank() || enemy.inTrap())return 1;
      if(rank == enemy.getRank())return 0;
      return -1;
  }

    /**
     * Check if a block is empty
     * @return true for empty
     */
  public boolean isEmpty(){
      return rank == 0;
    }

    /**
     * Check if a piece is removed
     * @return true for removed piece
     */

    public boolean getIsRemoved(){return isRemoved;}

    /**
     * Set remove status of a piece
     * @param isRemoved {boolean}
     */
    public void setIsRemoved(boolean isRemoved){this.isRemoved = isRemoved;}

    /**
     * Set the inTrap status of a piece
     * @param isInTrap {boolean}
     */
    public void setInTrap(boolean isInTrap){this.isInTrap = isInTrap;}

    /**
     * Check if a piece is in enemy's traps
     * @return {boolean}
     */
    public boolean getInTrap(){return isInTrap;}

    /**
     * Check the owner of a piece
     * @return the index of owner
     */
    public int BelongTo(){
      if(isEmpty())return 0;
      return team + 1;
    }


    public String toString(){
      String s = "";
      if(isEmpty()){s = s + ".";}
      else {
          s = s + NameOfPiece[rank];
          if (team == 2) s = s.toLowerCase();
      }

      return s;
    }
}
