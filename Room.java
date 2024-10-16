import java.util.HashMap;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> exits;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) { initRoom(description); }


    // As soon as the object is instantiated, it should ask for a description and create the possible exit hashmap
    private void initRoom(String description) {
        this.description = description;
        exits = new HashMap<String, Room>();
    }


    // put a direction, and the room located in that way
    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    // return the room located in the input direction
    public Room getExits(String direction) {
        return exits.get(direction);
    }

    // return hashmap keys
    public String possibleExits() {
        StringBuilder info = new StringBuilder();
        for (String exit : exits.keySet()){
            info.append(exit).append(" ");
        }
        return info.toString();
    }
    
    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

}
