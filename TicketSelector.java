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
