package hk.edu.polyu.comp.comp2021.jungle.model;

/**
 * Another type of expression of coordinators.
 * x represents the row number
 * y represents the column number
 */
public class Tuple {
    private int x;
    private int y;

    /**
     * Tuple constructor with two integer
     * @param x row number (range: 0 - 8)
     * @param y column number (range: 0 - 6)
     */
    public Tuple(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Get the row number
     * @return row number
     */

    public int getX(){
        return x;
    }
    /**
     * Get the column number
     * @return column number
     */
    public int getY(){
        return y;
    }

    /**
     * Get absolute number of an integer
     * @param x a input integer123
     * @return the absolute value of input
     */
    private int abs(int x){
        return x>=0?x:-x;
    }

    /**
     * Judge if a tuple is equal to another.
     * @param other {Tuple}another tuple
     * @return True for equal, false for not.
     */

    public boolean equalTo(Tuple other){
        return x==other.getX() && y==other.getY();
    }

    /**
     * Override
     * @param x {int} row number
     * @param y {int} column number
     * @return true for equal, false for not
     */
    public boolean equalTo(int x, int y){
        return this.x == x && this.y == y;
    }

    /**
     * Converter from a 2-decimal coordinators to Tuple
     * @param x {int} row number
     * @param y {int} column number
     * @return a tuple
     */
    public static Tuple toTuple(int x, int y) {   // input as integer x, y. Where (0,0) is the top-left corner
        return new Tuple(x, y);
    }

    /**
     * Converter from String coordinators to Tuple
     * @param x the letter
     * @param y the number
     * @return a tuple
     * E.g. A1 will be converted to (8, 0)
     */

    public static Tuple toTuple(char x, int y) {   //input as chess format. where  'A1' means the bottom-left corner (8,0);
        int realX = 8 - (x - 'A');
        int realY = y - 1;
        return new Tuple(realX, realY);
    }

    /**
     * Transfer from string to Tuple
     * @param input a string with 2 characters
     * @return Transferred tuple result.
     */
    public static Tuple toTuple(String input){
        if(input.length() < 2)return null;
        int x = '9' - input.charAt(1); //row
        int y = input.charAt(0) - 'A'; //column

     //   System.out.println(input + " " + x + " " + y);

        if(Board.inBoard(x, y)) return toTuple(x, y);
        else return null;
    }

    /**
     * The Manhattan Distance between two block.
     * @param p {Tuple}the first position
     * @param q {Tuple}the first position
     * @return {int} the Manhattan distance between the two position
     */
    public static int distance(Tuple p, Tuple q){
        return p.getDist(q);
    }

    /**
     * The Manhattan Distance of another position
     * @param other another position
     * @return the manhattan distance between the current position and the given one.
     */
    public int getDist(Tuple other){
        return abs(x - other.getX()) + abs(y - other.getY());
    }
}
