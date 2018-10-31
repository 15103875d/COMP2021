package jungle;

public class piece {
  int rank; 
  char positionX; 
  int positionY; 
  boolean team; 
  boolean isRemoved; 
  
  //constructor piece 
  public piece(int rank, char positionX, int positionY, boolean team, boolean isRemoved){
    this.rank = rank; 
    this.positionX = positionX;
    this.positionY = positionY;
    this.team = team;
    this.isRemoved = isRemoved; 
    
  }
  
  
}
