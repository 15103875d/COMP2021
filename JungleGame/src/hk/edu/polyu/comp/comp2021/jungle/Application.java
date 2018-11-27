package hk.edu.polyu.comp.comp2021.jungle;
import hk.edu.polyu.comp.comp2021.jungle.view.CLI;

/**
 *  This Class is the entry of the game.
 *  @link CLI
 */

public class Application {
    /**
     *
     * @param  args should remain empty
     * Entry of the game。
     */
    public static void main(String[] args){
        CLI Console = new CLI();
        Console.run();
    }
}
