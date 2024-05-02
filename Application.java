/**
 * Example of how to use TicketSelector class
 * @author Kameren Jouhal
 */
import java.util.Scanner;

public class Application {
    
    public static void main(String args[]) {
        TicketSelector selector = new TicketSelector();
        // File string
        String file;
        // Scanner
        Scanner s = new Scanner(System.in);
        // Print for user to input name of file
        System.out.println("Input file name");
        // Assign the user input to the file string
        file = s.nextLine();

        // Read from file
        selector.readFile(file, "|");

        // Picks integer
        int picks;
        // Print for user input on number of people to pick
        System.out.println("How many people would you like to pick?");
        // Assign user input to picks
        picks = s.nextInt();
        // Select the number of picks
        selector.selection(picks);

        // Write back to the file
        selector.writeToFile(file, "|");

        // Close the scanner
        s.close();
    }
}
