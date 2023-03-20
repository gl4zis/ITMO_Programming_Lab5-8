package org.application.lab5.parsers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.application.lab5.dragons.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class for reading from console
 */
public abstract class InputConsoleReader {
    private static final Logger LOGGER = LogManager.getLogger(InputConsoleReader.class);
    private static final String ERROR_MESSAGE = "Incorrect input. Try again";

    /** Reads next line from the console.
     * Stops the app, if input == "exit" or ctrl+D
     * @return inputLine
     */
    public static String readNextLine() {
        String line = "";
        try {
            Scanner console = new Scanner(System.in);
            line = console.nextLine();
        } catch (NoSuchElementException e) {
            LOGGER.debug("Correct exit");
            System.exit(0);
        }
        LOGGER.trace("Entered console line: '" + line + "'");
        return line;
    }

    /** Reads String type var from console with limits.
     * (can be null or not)
     * @param canBeNull boolean param, true if var can be null
     * @return stringVar
     */
    private static String readStringVar(boolean canBeNull) {
        boolean mistake;
        String var;
        do {
            var = readNextLine();
            if (var.equals("") && !canBeNull) {
                mistake = true;
                LOGGER.warn(ERROR_MESSAGE);
            } else mistake = false;
        } while (mistake);
        return var;
    }

    /** Reads Double type var from console
     * @param canBeNull boolean param, true if var can be null
     * @return doubleVar
     */
    private static Double readDoubleVar(boolean canBeNull) {
        double var;
        do {
            try {
                String strVar = readStringVar(canBeNull);
                if (strVar.equals(""))
                    return null;
                var = Double.parseDouble(strVar);
                return var;
            } catch (NumberFormatException e) {
                LOGGER.warn(ERROR_MESSAGE);
            }
        } while (true);
    }

    /** Reads Double type of var from console with limits.
     * (between min value and max value)
     * @param canBeNull boolean param, true if var can be null
     * @param minValue double param, minimum value of var
     * @param maxValue double param, maximum value of var
     * @return doubleVar
     */
    private static Double readDoubleVar(boolean canBeNull, double minValue, double maxValue) {
        Double var;
        do {
            var = readDoubleVar(canBeNull);
            if (var == null)
                return null;
            if (var - minValue > -1E-13 && var - maxValue < 1E-13)
                break;
            LOGGER.warn(ERROR_MESSAGE);
        } while (true);
        return var;
    }

    /** Reads Long type var from console
     * @param canBeNull boolean param, true if var can be null
     * @return longVar
     */
    private static Long readLongVar(boolean canBeNull) {
        long var;
        do {
            try {
                String strVar = readStringVar(canBeNull);
                if (strVar.equals(""))
                    return null;
                var = Long.parseLong(strVar);
                return var;
            } catch (NumberFormatException e) {
                LOGGER.warn(ERROR_MESSAGE);
            }
        } while (true);
    }

    /** Reads Double type of var from console with limits.
     * (between min value and max value)
     * @param canBeNull boolean param, true if var can be null
     * @param minValue long param, minimum value of var
     * @param maxValue long param, maximum value of var
     * @return longVar
     */
    private static Long readLongVar(boolean canBeNull, long minValue, long maxValue) {
        Long var;
        do {
            var = readLongVar(canBeNull);
            if (var == null)
                return null;
            if (var >= minValue && var <= maxValue)
                break;
            LOGGER.warn(ERROR_MESSAGE);
        } while (true);
        return var;
    }

    /** Reads dragon name from console
     * @return dragonName
     */
    private static String readDragonName() {
        System.out.print("Enter name: ");
        return readStringVar(false);
    }

    /** Reads dragon coordinates from console.
     * (Two fractional numbers x and y.
     * X should be more than -497)
     * @return dragonCoordinates
     */
    private static Coordinates readDragonCoordinates() {
        System.out.print("Enter X coordinate (fractional number > -497): ");
        double x = readDoubleVar(false, -497 + 1E-13, Double.POSITIVE_INFINITY);
        System.out.print("Enter Y coordinate (fractional number): ");
        float y = readDoubleVar(false).floatValue();
        return new Coordinates(x, y);
    }

    /** Reads dragon age from console.
     * Expects positive integral number.
     * If input == "", returns -1
     * @return dragonAge
     */
    private static int readDragonAge() {
        System.out.print("Enter age (integral number from 1 to " + Integer.MAX_VALUE + "): ");
        Long age = readLongVar(true, 1, Integer.MAX_VALUE);
        if (age != null)
            return age.intValue();
        else
            return -1;
    }

    /** Reads dragon weight from console.
     * Expects positive integral number
     * @return dragonWeight
     */
    private static long readDragonWeight() {
        System.out.print("Enter weight (integral number from 1 to " + Long.MAX_VALUE + "): ");
        return readLongVar(false, 1, Long.MAX_VALUE);
    }

    /** Reads dragon color from console.
     * Expects integral number from 1 to 4 in console
     * @return dragonColor
     */
    private static Color readDragonColor() {
        Map<Integer, Color> colors = new HashMap<>();
        colors.put(1, Color.GREEN);
        colors.put(2, Color.RED);
        colors.put(3, Color.ORANGE);
        colors.put(4, Color.BROWN);
        System.out.print("""
                \t1: Green
                \t2: Red
                \t3: Orange
                \t4: Brown
                Choose color (enter number):\040""");
        int colorNum = readLongVar(false, 1, 4).intValue();
        return colors.get(colorNum);
    }

    /** Reads dragon character from console.
     * Expects integral number from 1 to 3 in console
     * @return dragonCharacter
     */
    private static DragonCharacter readDragonCharacter() {
        Map<Integer, DragonCharacter> character = new HashMap<>();
        character.put(1, DragonCharacter.WISE);
        character.put(2, DragonCharacter.CHAOTIC_EVIL);
        character.put(3, DragonCharacter.FICKLE);
        System.out.print("""
                \t1: Wise
                \t2: Evil
                \t3: Fickle
                Choose character (enter number):\040""");
        int colorNum = readLongVar(false, 1, 3).intValue();
        return character.get(colorNum);
    }

    /** Reads dragon head from console.
     * Expects non-negative integral number in console as count of eyes
     * @return dragonHead
     */
    private static DragonHead readDragonHead() {
        System.out.print("Enter count of eyes (integral non-negative number): ");
        long eyesCount = readLongVar(false, 0, Long.MAX_VALUE);
        return new DragonHead(eyesCount);
    }

    /** Reads whole dragon from console
     * @return dragon
     */
    public static Dragon readDragon() {
        System.out.println("""
                Enter dragon profile.
                You can leave age empty""");
        int age;
        String name = readDragonName();
        Coordinates coordinates = readDragonCoordinates();
        age = readDragonAge();
        long weight = readDragonWeight();
        Color color = readDragonColor();
        DragonCharacter character = readDragonCharacter();
        DragonHead head = readDragonHead();
        Dragon dragon = new Dragon(name, coordinates, weight, color, character, head);
        if (age != -1)
            dragon.setAge(age);
        LOGGER.debug("Dragon object has created");
        return dragon;
    }
}
