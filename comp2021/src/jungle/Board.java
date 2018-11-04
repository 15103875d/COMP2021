package jungle;

public class Board {

    public static int[][] initial_position_X = {{0,0},{3,7}};

    private Piece[][] board;
    private final int row = 9;
    private final int column = 7;


    public Board() {   // Clear the board to set the initial state of board
        board = new Piece[row][column];
        for(int i=0;i<row;i++)
            for(int j=0;j<column;j++)
                board[i][j] = Piece.empty;
    }

    public void print() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    public void SetPiece(Piece piece, int x, int y){
        board[x][y] = piece;
    }

    public void update(Piece piece, char FromX, int FromY, char ToX, int ToY){
        int From_X_index = FromX - 'A';
        int From_Y_index = FromY - 1;
        int To_X_index = ToX - 'A';
        int To_Y_index = ToY - 1;

        SetPiece(piece, To_X_index, To_Y_index);
        SetPiece(Piece.empty, From_X_index,From_Y_index);
    }
}
