/**
 * This class emulates a selection ticketing system
 * Each person starts with a certain amount of tickets in the draw (denoted in the file)
 * When a person is selected from the draw, they are removed completely from the draw and
 * their tickets are reset to 0 in the file
 * This guarantees that the people who are picked from the last draw are not picked again
 * the next time the draw is run
 * If a person is not selected in the draw, their ticket count is increased by 1, meaning they
 * will have an additional ticket in the next draw
 * 
 * Priority is left up to the user, but note that higher number of tickets means there is a more
 * likely chance that person will be picked
 * 
 * @author Kameren Jouhal
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;

public class TicketSelector {
    
    protected ArrayList<Person> draw; // Draw of people
    protected ArrayList<Person> unique; // Unique people in the draw

    /**
     * Assign default values to instance data
     */
    public TicketSelector() {
        draw = new ArrayList<Person>();
        unique = new ArrayList<Person>();
    }

    /**
     * Add the specified number of tickets to the draw 
     * for the specified person
     * 
     * @param name // Name of the person
     * @param tickets // Number of tickets
     */
    void addPerson(String name, int tickets) {
        // Create a random number generator
        Random rand = new Random();
        // Add the person to unique
        unique.add(new Person(name, tickets));
        // Loop for the specified number of tickets
        for(int i = 0; i < tickets; i += 1) {
            // Create a new person object
            Person p = new Person(name, tickets);
            // Generate a random index
            int index = rand.nextInt(draw.size() + 1);
            // Insert into the draw
            draw.add(index, p);
        }
    }

    /**
     * Select people from the draw
     * 
     * @param picks Number of selections
     */
    void selection(int picks) {
        // Create a random number generator
        Random rand = new Random();
        // Pick the specified number of people and print them out
        for(int i = 0; i < picks; i += 1) {
            // If draw has no more elements, break
            if(draw.size() == 0) {
                System.out.println("No more people can be selected from the draw");
                break;
            }
            // Generate a random index
            int index = rand.nextInt(draw.size());
            // Retrieve the person and print out that they have been selected
            Person p = draw.get(index);
            System.out.println(p.toString() + " has been selected!");
            // Remove the person from the draw
            for(int j = 0; j < draw.size(); j += 1) {
                // Get the person at index j
                Person d = draw.get(j);
                // If the index contains the person, remove it
                if(p.name.compareTo(d.name) == 0) {
                    draw.remove(j);
                }
            }
            // Set the person's ticket count to 0 in unique
            for(int k = 0; k < unique.size(); k += 1) {
                // Get the person at index k
                Person u = unique.get(k);
                // If the index contains the person, set their tickets to -1
                if(p.name.compareTo(u.name) == 0) {
                    unique.get(k).tickets = -1;
                    // Break out of the loop
                    break;
                }
            }
        }
        // After the selection, increment the tickets for everyone
        // Note that people who were picked are now set to 0 since they were set to -1
        for(int x = 0; x < unique.size(); x += 1) {
            // Get the person at index x
            Person u = unique.get(x);
            // Increment the tickets
            u.tickets += 1;
        }
    }

    /**
     * Reads in a file and parses the information
     * Adds the information to the draw
     * 
     * File should be in the format:
     * Name(delimiter)Number of tickets
     * Note: replace (delimiter) with a string
     * 
     * @param filename Name of the file
     * @param delimiter Delimiter in the file that separates name and the number of tickets
     * 
     * @return True if it was able to read from the file, false if not
     */
    boolean readFile(String filename, String delimiter) {
        try {
            // Open the file
            File f = new File(filename);
            // Open a buffered reader for the file
            BufferedReader r = new BufferedReader(new FileReader(f));
            // Loop until the end of the file
            String line = r.readLine();
            while(line != null) {
                // Split the line
                String split[] = line.split(delimiter);
                // Assumes the name comes first and the number of tickets comes second
                // Also assumes there are only two items in the line
                String name = split[0];
                int tickets = Integer.parseInt(split[1]);
                // Add the person to the draw
                addPerson(name, tickets);
                // Go to the next line
                line = r.readLine();
            }
            // Close the bufferedreader
            r.close();
            // Return true
            return true;
        } catch(Exception e) {
            // Print an error and the stack trace
            System.out.println("Error while reading the file");
            e.printStackTrace();
            // Return false
            return false;
        }
    }

    /**
     * Writes the updated people and tickets to the file
     * 
     * File will be in the format:
     * Name(delimiter)Number of tickets
     * Note: will replace (delimiter) with a string
     * 
     * @param filename Name of the file
     * @param delimiter Delimiter for the file that separates name and number of tickets
     * 
     * @return True if it was able to write to the file, false if not
     */
    boolean writeToFile(String filename, String delimiter) {
        try {
            // Open the file
            File f = new File(filename);
            // Open a buffered writer
            BufferedWriter w = new BufferedWriter(new FileWriter(f)); 
            // Loop through all the people in unique
            for(int i = 0; i < unique.size(); i += 1) {
                // Get the person
                Person p = unique.get(i);
                // Append to the file the name and tickets of the person in the correct format
                w.append(p.name + delimiter + p.tickets);
                // If i + 1 does not equal selector.unique.size(), go to the next line
                if(i + 1 != unique.size()) {
                    w.newLine();
                }
            }
            // Close the buffered writer
            w.close();
            // Return true
            return true;
        } catch(Exception e) {
            // Print error message and stack trace
            System.out.println("Error while writing to the file");
            e.printStackTrace();
            // Return false
            return false;
        }
    }
}

/**
 * Class for a person
 */
class Person {
    
    protected String name; // Name of the person
    protected int tickets; // Number of tickets

    /**
     * Constructor to assign instance data
     * 
     * @param n Name of the person
     * @param t Number of tickets
     */
    public Person(String n, int t) {
        // Set name and tickets
        name = n;
        tickets = t;
    }

    /**
     * The string respresentation of a person is their name
     */
    public String toString() {
        return name;
    }
}
