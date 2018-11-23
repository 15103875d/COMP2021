package view;

public class UIMessage {


    public static String[] moveCodeMsg = {
            "    0: OK",
            "    1: Cannot move outside the board" ,
            "    2: Cannot move the piece that you don't own" ,
            "    3: Cannot move to the position occupied by your piece" ,
            "    4: You must move the piece to an adjacent position" ,
            "    5: This piece cannot move to the river" ,
            "    6: Cannot move piece to our den" ,
            "    7: Cannot capture piece with higher ranks and not in your traps." ,
            "    8: The rat cannot capture elephant from river." ,
            "    9: Rats inside river. Cannot jump across the river" ,
            "    10: Lion and Tight cannot move in that way."
            };

    public static void move_error_message(int errno){
        message("Error Code " + moveCodeMsg[errno]);
    }

    public static void message(String str){
        System.out.println(str);
    }

    public static void welcome(){
        System.out.println("Welcome to Jungle Game.");
        System.out.println("Type 'start' or 'load [filename]' to play a new game or a loaded game.");
    }

    public static void turnMessage(int id){
        System.out.println("It is player " + id + "'s turn.");
    }

    public static void showName(int id, String name){
        System.out.println("Player " + id + "'s name is " + name + ".");
    }
}
