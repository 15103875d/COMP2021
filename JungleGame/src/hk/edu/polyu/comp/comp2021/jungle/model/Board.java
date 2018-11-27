package hk.edu.polyu.comp.comp2021.jungle.model;
import hk.edu.polyu.comp.comp2021.jungle.constants.BoardConstants;

import java.util.ArrayList;

/**
 * The expression of the board status
 */
public class Board {

    private static final ArrayList<Tuple> traps = new ArrayList<>();
    private static final ArrayList<Tuple> river = new ArrayList<>();

    private static final char riverChar = '~';
    private static final char trapChar = '*';
    private static final char emptyChar = '.';

    private Piece[][] board;
    /**
     Define the size of the board.
     Default: 9 rows, 7 columns
     */

    public static final int MAX_ROW = 9;

    /**
     Define the size of the board.
     Default: 9 rows, 7 columns
     */

    public static final int MAX_COLUMN = 7;


    /**
     * Board Default Constructor
     * Set all board empty
     */
    public Board() {   // Clear the board to set the initial state of board
        board = new Piece[MAX_ROW][MAX_COLUMN];
        for(int i=0;i< MAX_ROW;i++) {
            for (int j = 0; j < MAX_COLUMN; j++) {
                board[i][j] = Piece.empty;
            }
        }

        for(int i=3;i<=5;i++) {
            river.add(new Tuple(i,1));
            river.add(new Tuple(i,2));
            river.add(new Tuple(i,4));
            river.add(new Tuple(i,5));
        }

        traps.add(new Tuple(0,2));
        traps.add(new Tuple(0,4));
        traps.add(new Tuple(1,3));
        traps.add(new Tuple(7,3));
        traps.add(new Tuple(8,2));
        traps.add(new Tuple(8,4));

        for(int i=1;i<=Piece.TOTAL_PIECES;i++){
            Piece currentPiece1 = new Piece(i, 1, false,false);
            Piece currentPiece2 = new Piece(i, 2, false,false);
            Tuple currentTuple = Piece.getInitialPosition(i);
            int currentX = currentTuple.getX(), currentY = currentTuple.getY();

            setPiece(currentPiece1, MAX_ROW - 1 - currentX, MAX_COLUMN - 1 - currentY);
            setPiece(currentPiece2, currentX, currentY);
        }
    }

    /**
     * Get piece at located x, y
     * @param x {integer}
     * @param y {integer}
     * @return the piece on board[x][y];
     */
    public Piece getPiece(int x, int y){
        return board[x][y];
    }

    /**
     * Overload. Get piece at Tuple position
     * @param position {Tuple} a tuple
     * @return the piece on given position
     */
    public Piece getPiece(Tuple position) {
        int x = position.getX(), y = position.getY();
        return board[x][y];
    }

    /**
     * Check whether a position on the board
     * @param x row number
     * @param y column number
     * @return true for in board, false for out.
     */
    public static boolean inBoard(int x, int y){
        return x>=0 && x<MAX_ROW && y>=0 && y<MAX_COLUMN;
    }

    /**
     * Override. Check whether a position on the board
     * @param position The position checked.
     * @return true for in board, false for out.
     */
    public static boolean inBoard(Tuple position){
        int x = position.getX(), y = position.getY();
        return x>=0 && x<MAX_ROW && y>=0 && y<MAX_COLUMN;
    }



    /**
     * print the board status.
     * '.' for empty
     * '#' for traps
     * '~' for rivers
     * Letters for pieces
     */

    public void print() {
        for (int i = 0; i < MAX_ROW; i++) {
            for (int j = 0; j < MAX_COLUMN; j++) {
                if(!board[i][j].isEmpty())  // non-empty means there exists a piece on current position
                    System.out.print(board[i][j]);
                else{
                    if(isTrap(i, j) > 0)System.out.print(trapChar);
                    else if(isRiver(i, j))System.out.print(riverChar);
                    else System.out.print(emptyChar);

                }
            }
            System.out.println();
        }
    }

    /**
     * Put piece on the given position
     * @param piece A type of piece
     * @param x row number
     * @param y column number
     */

    public void setPiece(Piece piece, int x, int y){
        board[x][y] = piece;
    }

    /**
     * Check whether a position is river
     * @param position {Tuple} a position
     * @return true for river block, false for not
     */

    public static boolean isRiver(Tuple position){
        for(Tuple i:river) {
            if (position.equalTo(i)) return true;
        }
        return false;
    }

    /**
     * Override. Check whether a position is river
     * @param x row number
     * @param y column number
     * @return true for river block, false for not
     */
    public static boolean isRiver(int x, int y) {
        return isRiver(new Tuple(x, y));
    }

    /**
     * Check whether a position is a trap.
     * @param x row number
     * @param y column number
     * @return 0 for not trap blocks. 1 for player 1's trap, 2 for player 2's trap.
     */

    public static int isTrap(int x, int y){
        boolean findTrap = false;
        for(Tuple i:traps) {
            if (i.equalTo(x, y)) {
                findTrap = true;
                break;
            }
        }

        if(!findTrap)return 0;
        if(x<=1)return 2;
        return 1;
    }
    /**
     * Override Check whether a position is a trap.
     * @param position Expressed by Tuple.
     * @return 0 for not trap blocks. 1 for player 1's trap, 2 for player 2's trap.
     */


    public static int isTrap(Tuple position){
        return isTrap(position.getX(), position.getY());
    }

    /**
     * Update the board.
     * @param from The starting point.
     * @param to The destination.
     */
    public void update(Tuple from, Tuple to){
        Piece temp = board[from.getX()][from.getY()];
        board[to.getX()][to.getY()] = temp;
        board[from.getX()][from.getY()] = Piece.empty;
    }

    /**
     *
     * @param id the player id
     * @param position the position of board
     * @return false for not out den, true for yes.
     */
    public boolean isOurDen(int id, Tuple position){
        return position.equalTo(BoardConstants.denPosition[id]);
    }

    /**
     * Transfer a board to string.
     * @return a string which represents the board.
     */
    public String toString(){
        String res = "";

        for (int i = 0; i < MAX_ROW; i++) {
            for (int j = 0; j < MAX_COLUMN; j++) {
                if(!board[i][j].isEmpty())  // non-empty means there exists a piece on current position
                    res = res + board[i][j];
                else{
                    if(isTrap(i, j) > 0)res = res + trapChar;
                    else if(isRiver(i, j))res = res + riverChar;
                    else res = res + emptyChar;
                }
            }
            res = res + '\n';
        }
        return res;
    }
}
