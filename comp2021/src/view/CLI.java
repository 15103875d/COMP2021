package view;
import jungle.*;
import java.util.Scanner;

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

    Scanner sc = new Scanner(System.in);

    public void run() {
        System.out.println("Welcome to Jungle Game.");
        shouldQuit = false;
        String inputCmd = "";


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
        //command = new Command(args[0]);
        Game game;

        if ((command = Command.getByString(ins)) != null)
            switch (command) {
                case CMD_Start:
                    game = new Game();
                    String temp_name;

                    System.out.print("Please input the name of player 1: ");
                    temp_name = sc.nextLine();
                    if(temp_name == null){
                        System.out.println("No input. Use the default name.");
                        temp_name = Player.DEFAULT_NAME[0];
                    }
                    game.SetPlayer(0, temp_name);

                    System.out.print("Please input the name of player 2: ");
                    temp_name = sc.nextLine();
                    if(temp_name == null){
                        System.out.println("No input. Use the default name.");
                        temp_name = Player.DEFAULT_NAME[1];
                    }
                    game.SetPlayer(1, temp_name);



                    break;
                /*case CMD_Load:
                    printMessage(calculator.toString());
                    break;*/
            }
    }

    private void printMessage(String message) {
        System.out.print(message);
    }

    private void printPrompt() {
        printMessage("#");
    }
}
