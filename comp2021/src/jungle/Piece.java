package jungle;

public class Piece {
  int rank;
  int team;
  boolean isRemoved;

  public static final int TOTAL_PIECES = 8;
  public static final Piece empty = new Piece(0,0,false);
  public static final String[] NameOfPiece = {".","R","C","D","W","P","T","L","E"};// 'P' for "Leopard"

  
  //constructor piece 
  public Piece(int rank, int team, boolean isRemoved) {
      this.rank = rank;
      this.team = team;
      this.isRemoved = isRemoved;
  }

  public int CompareTo(Piece enemy){
      if(rank < enemy.rank)return -1;
      else if(rank == enemy.rank)return 0;
      else return 1;
  }

  public boolean isEmpty(){
      return rank == 0;
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
