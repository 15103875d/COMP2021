package view;
import jungle.*;
import java.util.Scanner;
import constants.CLIConstants;


public class CLI {
    public enum Command{
        CMD_Start("start"),
        CMD_Load("load"),
        CMD_Save("save"),
        CMD_Move("move"),
        CMD_Quit("quit");

        private String CommandStr;

        Command(String CommandStr){this.CommandStr = CommandStr;}

        public String getCommandStr() {
            return CommandStr;
        }

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

    Scanner sc = new Scanner(System.in);

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
                        String temp_name = null;

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
                                   printMessage("Player " + game.getTurn() + " moved from " + args[CLIConstants.FIRST] + args[CLIConstants.SECOND]);

                               }

                               else {
                                   UIMessage.move_error_message(returnValue);
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

                        }
                    }
                    break;
            }
    }

    private void printMessage(String message) {
        System.out.print(message);
    }

    private void printPrompt() {
        printMessage("#");
    }
}
