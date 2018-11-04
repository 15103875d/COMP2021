package jungle;

import java.io.File;

public class Game {



    private final int NUM_PLAYER = 2;
    private Board board;
    private Player[] player;
    boolean isSaved;
    private int turn;  // Indicate which player moves currently

    public Game(){
        board = new Board();
        player = new Player[NUM_PLAYER];
        for(int i=0;i<NUM_PLAYER;i++)
            player[i] = new Player();
        turn = 1;
        isSaved = false;
    }

    public void Load(Game other){
        board = other.board;
        player = other.player;
        turn = other.turn;
    }

    public void SetPlayer(int id, String name){
        if(id<NUM_PLAYER && id>=0)
            player[id].SetName(name);
    }

    public void AssignInitialPosition(){
        for(int i=1;i<=Piece.TOTAL_PIECES;i++){
        }
    }
}
