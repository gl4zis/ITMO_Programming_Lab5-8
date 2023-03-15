package org.application.lab5.parsers;

import org.application.lab5.commands.CommandManager;
import org.application.lab5.dragons.*;
import org.application.lab5.exceptions.IncorrectDataException;
import org.application.lab5.exceptions.IncorrectInputException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Class for reading something from script file
 */
public class InputScriptReader {

    private static final String INCORRECT_DATA_MESSAGE = "v";
    private final Reader reader;
    private boolean execution;

    /** Constructor, that creates object for reading script.
     * Each object creates before start reading one of the script files
     * @param filePath full path on script file
     * @throws FileNotFoundException if file doesn't exist
     * @throws SecurityException if app can't read something from file
     */
    public InputScriptReader(String filePath) throws FileNotFoundException, SecurityException {
        if (filePath.length() > 0 && filePath.charAt(0) == '~')
            filePath = System.getenv("HOME") + filePath.substring(1);
        File script = new File(filePath);
        FileInputStream in = new FileInputStream(script);
        reader = new InputStreamReader(in);
        execution = true;
    }

    /** Reads next line from file using InputStreamReader.
     * Read symbols until find \n or end of file.
     * Support single-line comments (<some command> //Comment).
     * If file ends, sets execution = false
     * @return line
     */
    public String readNextLine() {
        StringBuilder line = null;
        try {
            line = new StringBuilder();
            int nextSym = reader.read();
            boolean comment;
            while (nextSym != -1 && nextSym != 10) {
                line.append((char) nextSym);
                comment = (char) nextSym == '/';
                nextSym = reader.read();
                if ((char) nextSym == '/' && comment) {
                    line.deleteCharAt(line.length() - 1);
                    while (!line.isEmpty() && line.lastIndexOf(" ") == line.length() - 1) {
                        line.deleteCharAt(line.length() - 1);
                    }
                    while (nextSym != -1 && nextSym != 10) nextSym = reader.read();
                    break;
                }
            }
            //if (!comment && nextSym != -1) line.deleteCharAt(line.length() - 1);  //Закоментить, при запуске на винде
            if (nextSym == -1) execution = false;
        } catch (IOException e) {
            execution = false;
            System.out.println("Что-то случилось с файликом =(");
        }
        return line.toString();
    }

    /**
     * Finds next command in script and returns the hole line with command.
     * Returns null, if execution is false
     *
     * @param commandManager CommandManager object, that this method works with (needs for having list of loaded commands)
     * @return line
     */
    public String findNextCommand(CommandManager commandManager) {
        Set<String> commands = commandManager.getCommandNames();
        while (execution) {
            String line = readNextLine();
            try {
                String command = line.split(" ")[0];
                if (commands.contains(command)) return line;
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }
        return null;
    }

    /**
     * Reads dragon name from script file
     *
     * @return dragonName
     * @throws IncorrectDataException if inputted value is empty
     */
    private String readDragonName() throws IncorrectDataException {
        String name = readNextLine();
        if (name.equals("")) throw new IncorrectDataException(INCORRECT_DATA_MESSAGE);
        else return name;
    }

    /**
     * Reads dragon coordinates from script file.
     * Expected two fractional numbers x and y.
     * X should be more than -497
     *
     * @return dragonCoordinates
     * @throws IncorrectDataException if some values are incorrect
     */
    private Coordinates readDragonCoordinates() throws IncorrectDataException {
        try {
            double x = Double.parseDouble(readNextLine());
            float y = Float.parseFloat(readNextLine());
            return new Coordinates(x, y);
        } catch (NumberFormatException e) {
            throw new IncorrectDataException(INCORRECT_DATA_MESSAGE);
        }
    }

    /**
     * Reads dragon age from script file.
     * Expected positive integral number.
     * If input line == "", returns -1
     *
     * @return dragonAge
     * @throws IncorrectDataException if inputted var isn't integral number
     */
    private int readDragonAge() throws IncorrectDataException {
        String arg = readNextLine();
        try {
            return Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            if (arg.equals("")) {
                return -1;
            } else throw new IncorrectDataException(INCORRECT_DATA_MESSAGE);
        }
    }

    /**
     * Reads dragon weight from script file.
     * Expects positive integral number
     *
     * @return dragonWeight
     * @throws IncorrectDataException if inputted var isn't integral number
     */
    private Long readDragonWeight() throws IncorrectDataException {
        try {
            return Long.parseLong(readNextLine());
        } catch (NumberFormatException e) {
            throw new IncorrectDataException(INCORRECT_DATA_MESSAGE);
        }
    }

    /**
     * Reads dragon color from script file.
     * Expects integral number from 1 to 4
     *
     * @return dragonColor
     * @throws IncorrectDataException if inputted var isn't integral number
     */
    private Color readDragonColor() throws IncorrectDataException {
        try {
            int colorNum = Integer.parseInt(readNextLine());
            Map<Integer, Color> colors = new HashMap<>();
            colors.put(1, Color.GREEN);
            colors.put(2, Color.RED);
            colors.put(3, Color.ORANGE);
            colors.put(4, Color.BROWN);
            return colors.get(colorNum);
        } catch (NullPointerException | NumberFormatException e) {
            throw new IncorrectDataException(INCORRECT_DATA_MESSAGE);
        }
    }

    /**
     * Reads dragon character from script file.
     * Expects integral number from 1 to 3
     *
     * @return dragonCharacter
     * @throws IncorrectDataException if inputted var isn't integral number
     */
    private DragonCharacter readDragonCharacter() throws IncorrectDataException {
        try {
            int charNum = Integer.parseInt(readNextLine());
            Map<Integer, DragonCharacter> characters = new HashMap<>();
            characters.put(1, DragonCharacter.WISE);
            characters.put(2, DragonCharacter.CHAOTIC_EVIL);
            characters.put(3, DragonCharacter.FICKLE);
            return characters.get(charNum);
        } catch (NullPointerException | NumberFormatException e) {
            throw new IncorrectDataException(INCORRECT_DATA_MESSAGE);
        }
    }

    /**
     * Reads dragon head from script file.
     * Expects non-negative integral number as eyes count
     *
     * @return dragonHead
     * @throws IncorrectDataException if inputted var isn't integral number
     */
    private DragonHead readDragonHead() throws IncorrectInputException {
        try {
            float eyesCount = Long.parseLong(readNextLine());
            return new DragonHead(eyesCount);
        } catch (NumberFormatException e) {
            throw new IncorrectDataException(INCORRECT_DATA_MESSAGE);
        }
    }

    /**
     * Reads whole dragon object from script file.
     * Return null, if some data in file is incorrect
     *
     * @return dragon
     */
    public Dragon readDragon() {
        try {
            String name = readDragonName();
            Coordinates coordinates = readDragonCoordinates();
            int age = readDragonAge();
            Long weight = readDragonWeight();
            Color color = readDragonColor();
            DragonCharacter character = readDragonCharacter();
            DragonHead head = readDragonHead();
            Dragon dragon = new Dragon(name, coordinates, weight, color, character, head);
            if (age > -1) dragon.setAge(age);
            return dragon;
        } catch (IncorrectDataException e) {
            return null;
        }
    }
}
