import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room joker = new Room("in a magic transporter room");
    private Room[] rooms;
    private List<Room> record = new LinkedList<>();
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }


    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, lab, office, library, club, canteen, gym;


        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        library = new Room("in a library");
        club = new Room("in a club");
        canteen = new Room("in a canteen");
        gym = new Room("in a gym");

        // make sure to update this whenever you add a new room
        rooms = new Room[]{outside, theater, lab, office, library, club, canteen, gym};

        // set exits
        outside.setExit("east", office);

        office.setExit("up", library);
        office.setExit("south", canteen);
        office.setExit("east", theater);
        office.setExit("west", outside);

        library.setExit("down", office);

        lab.setExit("north", theater);
        lab.setExit("west", canteen);
        lab.setExit("south", joker);


        theater.setExit("west", office);
        theater.setExit("south", lab);

        canteen.setExit("east", lab);
        canteen.setExit("north", office);
        canteen.setExit("up", gym);

        gym.setExit("down", canteen);
        gym.setExit("up", club);

        club.setExit("down", gym);
        currentRoom = outside;  // start game outside
    }


    // create a random index and call the rooms with that random index out of the array
    private Room magicTransporterRoom(Room[] arr) {
        System.out.println("You are " + joker.getDescription());
        System.out.println("You will be transported into a random room~~");
        System.out.println("Boom!");
        Random r = new Random();
        int randomNumber=r.nextInt(arr.length);
        return arr[randomNumber];
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }


    // Show a detail on what the location is and give possible exits out of the room
    private void possibleExits() {
        System.out.println("You are " + currentRoom.getDescription());
        System.out.print("Exits: ");
        System.out.println(currentRoom.possibleExits());
        System.out.println();
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        possibleExits();
    }


    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command)
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        // possible commands
        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;

            case LOOK:
                possibleExits();
                break;

            case BACK:
                goBack();
                break;

        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("   go quit help");
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        // To use "back" command, save the previous room before moving on
        record.addLast(currentRoom);
        Room nextRoom = currentRoom.getExits(direction);

        // no neighbor room
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }

        // enter the magic-transporter room
        else if (nextRoom == joker) {
            currentRoom = magicTransporterRoom(rooms);
            possibleExits();
        }

        // move onto the next room
        else {
            currentRoom = nextRoom;
            possibleExits();
        }
    }

    // take the previous room records out of the linkedlist
    // if the record is empty, ask user again to confirm "Go where"
    private void goBack() {
        try {
            currentRoom = record.removeLast();
            possibleExits();
        }
        catch (NoSuchElementException e) {
            System.out.println("Go where?");
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }


}
