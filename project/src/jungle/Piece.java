package jungle;

public class Piece {
    private int rank;
    private int team;
    private boolean isRemoved;
    private boolean isInTrap;

    public static final int TOTAL_PIECES = 8;
    public static final Piece empty = new Piece(0,0,false,false);

    public static final String[] NameOfPiece = {".","R","C","D","W","P","T","L","E"};// 'P' for "Leopard"
    public static final int[] TypeOfSpecial = {0,1,0,0,0,0,2,2,0}; // Special rules for pieces. 0 for Nil, 1 for cross the river(rats), 2 for jump across the river(Tiger, Lion).
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

  
  //constructor piece
  public Piece(int rank, int team, boolean isRemoved, boolean isInTrap) {
      this.rank = rank;
      this.team = team;
      this.isRemoved = isRemoved;
      this.isInTrap = isInTrap;
  }

  public int getRank(){return rank;}

  public boolean exist(){return !isRemoved;}

  public int getSkills(){
        return TypeOfSpecial[rank];
  }

  public int getOwner(){
        return team;
  }

  public boolean inTrap(){return isInTrap;}

  public static Tuple getInitialPosition(int rank){
        return InitPosition[rank];
  }

  public int CompareTo(Piece enemy){
      if(rank > enemy.rank || enemy.isInTrap)return 1;
      else if(rank == enemy.rank)return 0;
      else return 1;
  }

  public boolean isEmpty(){
      return rank == 0;
    }
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
