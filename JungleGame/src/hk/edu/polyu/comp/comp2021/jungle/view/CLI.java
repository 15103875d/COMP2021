
package hk.edu.polyu.comp.comp2021.jungle.view;
import hk.edu.polyu.comp.comp2021.jungle.model.*;
import hk.edu.polyu.comp.comp2021.jungle.constants.*;
import java.util.Scanner;

/**
 * Run a command line interface and ask for user's input.
 */
public class CLI {
    /**
     * 5 kinds of command
     * "start" / "open" / "save" / "move" / "quit"
     */
    public enum Command{
        /** start */ CMD_Start("start"),
        /** open */ CMD_Open("open"),
        /** save */ CMD_Save("save"),
        /** move */ CMD_Move("move"),
        /** quit */ CMD_Quit("quit");

        private String  CommandStr;

        Command(String CommandStr){this.CommandStr = CommandStr;}

        /**
        Get the command string.
         @return the command string
         */
        public String getCommandStr() {
            return CommandStr;
        }

        /**
        Check which command input
         @param inputStr The string input in the console
         @return the correspond result. Null for not exist.
         */
        public static Command getByString(String inputStr) {
            for (Command cmd : Command.values()) {
                if (cmd.getCommandStr().equalsIgnoreCase(inputStr.toLowerCase()))
                    return cmd;
            }
            return null;
        }
    }

    private boolean shouldQuit;
    private boolean gameStarted;
    private Game game = new Game();

    private Scanner sc = new Scanner(System.in);

    /**
    When run is called, the command line interface will be launched and user can input in the console.
     */
    public void run() {

        UIMessage.welcome();
        shouldQuit = false;
        gameStarted = false;

        String inputCmd;

        while (!shouldQuit) {
            printPrompt();
            inputCmd = sc.nextLine().trim();
            if (inputCmd.isEmpty())
                continue;
            executeCommand(inputCmd);
            if(game.getWinner() > 0)
            {
                shouldQuit = true;
                UIMessage.message("The winner is Player " + game.getWinner() + ".");
            }
        }
        sc.close();
    }

    private void executeCommand(String ins) {
        Command command;


        String[] args = ins.split("\\s++");

        if ((command = Command.getByString(args[0])) != null)
            switch (command) {
                case CMD_Start:
                    if(gameStarted){
                        UIMessage.message("The game was already started.");
                    }

                    else {
                        String temp_name;

                        System.out.print("Please input the name of player 1: ");
                        temp_name = sc.nextLine();
                        if (temp_name.isEmpty()) {
                            System.out.println("No input. Use the default name.");
                            temp_name = Player.DEFAULT_NAME[0];
                        }
                        game.setPlayer(0, temp_name);
                        UIMessage.showName(1, temp_name);

                        System.out.print("Please input the name of player 2: ");
                        temp_name = sc.nextLine();
                        if (temp_name.isEmpty()) {
                            System.out.println("No input. Use the default name.");
                            temp_name = Player.DEFAULT_NAME[1];
                        }
                        game.setPlayer(1, temp_name);
                        UIMessage.showName(2, temp_name);

                        UIMessage.turnMessage(game.getTurn());
                        game.boardPrint();
                        gameStarted = true;

                    }
                    break;

                case CMD_Move:
                    if(!gameStarted){
                        printMessage("This command can only be used after starting a game.");
                    }

                    else{
                        if(args.length !=  CLIConstants.CMD_MOVE_ARGUMENTS){
                            UIMessage.message("Invalid input. Format: move [fromPosition] [toPosition]");
                            break;
                        }
                        else{
                           Tuple from = Tuple.toTuple(args[CLIConstants.FIRST].toUpperCase());
                           Tuple to = Tuple.toTuple(args[CLIConstants.SECOND].toUpperCase());


                           if(from!=null && to!=null){

                               int returnValue = game.isValidMove(from, to);
                               if(returnValue == 0){
                                   game.getBoard().update(from, to);
                                   printMessage("Player " + game.getTurn() + " moved from " + args[CLIConstants.FIRST] + " to " + args[CLIConstants.SECOND] + "\n");
                                   game.takeTurn();
                                   UIMessage.turnMessage(game.getTurn());
                                   game.setSaveStatus(false);
                                   game.boardPrint();
                                   break;
                               }

                               else {
                                   UIMessage.move_error_message(returnValue);
                                   game.boardPrint();

                               }
                           }

                        }
                    }

                    break;

                case CMD_Save:
                    if(!gameStarted){
                        printMessage("This command can only be used after starting a game.");
                    }

                    else{
                        if(args.length !=  CLIConstants.CMD_SAVE_ARGUMENTS){
                            UIMessage.message("Invalid input. Format: save [Path]. E.g. 'save 1.txt' will save current game state to the file 1.txt in the current working directory.");
                        }

                        else{
                            game.save(args[1]);
                            UIMessage.message("The game have been saved to " + args[1] + ".");

                        }
                    }
                    break;

                case CMD_Open:
                    if(args.length !=  CLIConstants.CMD_LOAD_ARGUMENTS){
                        UIMessage.message("Invalid input. Format: Load [Path]");
                    }

                    else {
                        int returnValue = game.load(args[1]);
                        if(returnValue == 0) {
                            UIMessage.message("Game loaded from " + args[1] + ".");

                            UIMessage.turnMessage(game.getTurn());

                            game.boardPrint();
                            gameStarted = true;
                        }

                        else{
                            UIMessage.message("No such file.");
                        }

                    }

                    break;

                case CMD_Quit:
                    UIMessage.message("Quit? Y/N");
                    String input = sc.nextLine();
                    if(input.length() > 0){
                        if(input.charAt(0) == 'y' || input.charAt(0) == 'Y'){
                            if(gameStarted && !game.getSaveStatus()){
                                UIMessage.message("The game has not saved. Input path to save it or press enter to discard it.");
                                input = sc.nextLine();

                                if(!input.equals(""))
                                    game.save(input);
                            }

                            UIMessage.message("Bye!");
                            shouldQuit = true;
                        }


                    }

                    break;


                default:
                    UIMessage.message("Wrong command.");
                    break;
            }

            else UIMessage.wrong_command();
    }

    private void printMessage(String message) {
        System.out.print(message);
    }

    private void printPrompt() {
        printMessage("#");
    }
}
