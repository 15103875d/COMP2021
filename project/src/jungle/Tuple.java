package jungle;

import constants.BoardConstants;

public class Tuple {
    private int x;
    private int y;

    public Tuple(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    private int abs(int x){
        return x>=0?x:-x;
    }

    public boolean equalTo(Tuple other){
        return x==other.x && y==other.y;
    }

    public boolean equalTo(int x, int y){
        return this.x == x && this.y == y;
    }

    public static Tuple toTuple(int x, int y) {   // input as integer x, y. Where (0,0) is the top-left corner
        return new Tuple(x, y);
    }

    public static Tuple toTuple(char x, int y) {   //input as chess format. where  'A1' means the bottom-left corner
        int realX = x - 'A';
        int realY = y - 1;
        return new Tuple(realX, realY);
    }

    public static Tuple toTuple(String input){

        int x = input.charAt(0) - 'A';
        int y = input.charAt(1) - '1';

        if(Board.inBoard(x, y)) return toTuple(8 - x, y);
        else return null;
    }

    public static int distance(Tuple p, Tuple q){
        return p.getDist(q);
    }

    public int getDist(Tuple other){
        return abs(x - other.getX()) + abs(y - other.getY());
    }
}
