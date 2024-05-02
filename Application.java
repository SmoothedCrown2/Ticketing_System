/**
 * Example of how to use TicketSelector class
 * @author Kameren Jouhal
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
        try {
            // Open the file
            File f = new File(file);
            // Open a buffered reader for the file
            BufferedReader r = new BufferedReader(new FileReader(f));
            // Loop until the end of the file
            String line = r.readLine();
            while(line != null) {
                // Split the line
                String split[] = line.split("\\|");
                // Assumes the name comes first and the number of tickets comes second
                // Also assumes there are only two items in the line
                String name = split[0];
                int tickets = Integer.parseInt(split[1]);
                // Add the person to the draw
                selector.addPerson(name, tickets);
                // Go to the next line
                line = r.readLine();
            }
            // Close the bufferedreader
            r.close();
        } catch(Exception e) {
            System.out.println("Error while reading the file");
            e.printStackTrace();
        }

        // Picks integer
        int picks;
        // Print for user input on number of people to pick
        System.out.println("How many people would you like to pick?");
        // Assign user input to picks
        picks = s.nextInt();
        // Select the number of picks
        selector.selection(picks);

        // Write back the new values to the file
        try {
            // Open the file
            File f = new File(file);
            // Open a buffered writer
            BufferedWriter w = new BufferedWriter(new FileWriter(f)); 
            // Loop through all the people in unique
            for(int i = 0; i < selector.unique.size(); i += 1) {
                // Get the person
                Person p = selector.unique.get(i);
                // Append to the file the name and tickets of the person in the correct format
                w.append(p.name + "|" + p.tickets);
                // If i + 1 does not equal selector.unique.size(), go to the next line
                if(i + 1 != selector.unique.size()) {
                    w.newLine();
                }
            }
            // Close the buffered writer
            w.close();
        } catch(Exception e) {
            System.out.println("Error while writing to the file");
            e.printStackTrace();
        }
        // Close the scanner
        s.close();
    }
}
