package hk.edu.polyu.comp.comp2021.jungle.view;

import hk.edu.polyu.comp.comp2021.jungle.model.Tuple;

/**
 * Used to output the message in the console
 */

public class UIMessage {


    /**
     * @see hk.edu.polyu.comp.comp2021.jungle.model.Game#isValidMove(Tuple, Tuple)
     *
     */
    public static final String[] moveCodeMsg = {
            "    0: OK",
            "    1: Cannot move outside the board" ,
            "    2: Cannot move the piece that you don't own" ,
            "    3: Cannot move to the position occupied by one of your pieces" ,
            "    4: You must move the piece to an adjacent position" ,
            "    5: This piece cannot move to the river" ,
            "    6: Cannot move piece to your den" ,
            "    7: Cannot capture piece with higher ranks and not in your traps. (Special: the elephant cannot capture the rat)" ,
            "    8: The rat cannot capture elephant from river." ,
            "    9: Rats inside river. Cannot jump across the river" ,
            "    10: Lion and Tiger cannot move in that way.",
            "    11: A Rat can only attack another one when both in water or land"
            };

    /**
     * output the related message of wrong moves.
     * @param errno the type of errors.
     */
    public static void move_error_message(int errno){
        message("Error Code " + moveCodeMsg[errno]);
    }

    /**
     * Output when wrong command input.
     */
    public static void wrong_command(){message("No such command. ");}

    /**
     * Output the given message to console.
     * @param str the message need to be output.
     */
    public static void message(String str){
        System.out.println(str);
    }

    /**
     * The welcome message. When game is launched.
     */

    public static void welcome(){
        System.out.println("Welcome to Jungle Game.");
        System.out.println("Type 'start' or 'load [filename]' to play a new game or a loaded game.");
    }

    /**
     * Output which player will move next.
     * @param id the user index. Only 1 and 2 will be accepted.
     */
    public static void turnMessage(int id){
        System.out.println("It is player " + id + "'s turn.");
    }

    /**
     * Show the name of player. Only used in the starting of game
     * @param id he user index. Only 1 and 2 will be accepted.
     * @param name the name assigned to the player
     */
    public static void showName(int id, String name){
        System.out.println("Player " + id + "'s name is " + name + ".");
    }
}
