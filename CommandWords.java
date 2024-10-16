import java.util.HashMap;

/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class CommandWords
{
    private HashMap<String, CommandWord> validCommands;
    // a constant array that holds all valid command words
    /**
     * Constructor - initialise the command words.
     * Assign command words with the related command in the enum
     */
    public CommandWords()
    {
        validCommands = new HashMap<String, CommandWord>();
        validCommands.put("go", CommandWord.GO);
        validCommands.put("help", CommandWord.HELP);
        validCommands.put("quit", CommandWord.QUIT);
        validCommands.put("look", CommandWord.LOOK);
        validCommands.put("back", CommandWord.BACK);
    }

    // find the value in hashmap, if it is not found, it will return null -> UNKNOWN
    public CommandWord getCommandWord(String commandWord) {
        CommandWord command = validCommands.get(commandWord);
        // check whether the input command is unknown or not. If it is, return the unknown enum
        if (command == null)
            return CommandWord.UNKNOWN;
        return command;
    }

    /**
     * Check whether a given String is a valid command word. 
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        return validCommands.containsKey(aString);
    }
}
