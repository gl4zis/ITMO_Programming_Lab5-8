package parsers;

import dragons.*;
import exceptions.ExitException;
import exceptions.IncorrectInputException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * For processing any input stream and reads something from it
 */
public class MyScanner {
    private static final Logger LOGGER = LogManager.getLogger(MyScanner.class);
    private final boolean isConsole;
    private final Scanner scanner;

    public MyScanner(InputStream is) {
        isConsole = is.equals(System.in);
        scanner = new Scanner(is);
    }

    /**
     * Reads next line.
     * Checking ctrl+d in console and end of file if reads from file
     *
     * @return inputted line or null if file end
     */
    public String nextLine() {
        try {
            String line = scanner.nextLine().trim();
            if (isConsole)
                LOGGER.trace("Entered console line: '" + line + "'");
            return line;
        } catch (NoSuchElementException e) {
            if (isConsole)
                throw new ExitException();
            else return null;
        }
    }

    /**
     * Non-blocking reads next console line.
     * Works only for System.in!!
     *
     * @return inputted line or null if it is not System.in or if there are nothing inputted
     */
    public String checkConsole() {
        if (!isConsole) return null;
        try {
            if (System.in.available() > 0)
                return nextLine();
        } catch (IOException ignored) {
        }
        return null;
    }

    /**
     * Prints message if working with console
     */
    private void print(String message) {
        if (isConsole) {
            System.out.print(message);
        }
    }

    /**
     * Throws exception if not working with console
     */
    private void throwIncInput(String message) {
        if (!isConsole)
            throw new IncorrectInputException(message);
    }

    private String readName() {
        do {
            print("Enter name: ");
            String name = nextLine();
            if (name.isEmpty()) {
                throwIncInput("Incorrect name");
            } else return name;
            print("Incorrect data inputted!\n");
        } while (true);
    }

    private Coordinates readCoordinates() {
        double X;
        float Y;
        X = readCoordinate(-497, "X");
        Y = (float) readCoordinate(Double.NEGATIVE_INFINITY, "Y");
        return new Coordinates(X, Y);
    }

    private double readCoordinate(double minValue, String nameOfCoord) {
        double X;
        do {
            print("Enter " + nameOfCoord + " coordinate (fractional number > -497): ");
            try {
                X = Double.parseDouble(nextLine());
                if (X <= minValue) {
                    throwIncInput("Incorrect " + nameOfCoord);
                } else break;
            } catch (NumberFormatException e) {
                throwIncInput("Incorrect " + nameOfCoord);
            }
            print("Incorrect data inputted!\n");
        } while (true);
        return X;
    }

    private int readAge() {
        do {
            print("Enter age: ");
            String ageStr = nextLine();
            if (ageStr.isEmpty()) {
                return -1;
            }
            try {
                return Integer.parseInt(ageStr);
            } catch (NumberFormatException e) {
                throwIncInput("Incorrect age");
            }
            print("Incorrect data inputted!\n");
        } while (true);
    }

    private long readWeight() {
        do {
            print("Enter weight: ");
            try {
                return Long.parseLong(nextLine());
            } catch (NumberFormatException e) {
                throwIncInput("Incorrect weight");
            }
            print("Incorrect data inputted!\n");
        } while (true);
    }

    private Color readColor() {
        Map<Integer, Color> colors = new HashMap<>();
        colors.put(1, Color.GREEN);
        colors.put(2, Color.RED);
        colors.put(3, Color.ORANGE);
        colors.put(4, Color.BROWN);
        int colorNum;
        do {
            print("""
                    \t1: Green
                    \t2: Red
                    \t3: Orange
                    \t4: Brown
                    Choose color (enter number):\040""");
            try {
                colorNum = Integer.parseInt(nextLine());
                if (colorNum >= 1 && colorNum <= 4) break;
                throwIncInput("Incorrect color");
            } catch (NumberFormatException e) {
                throwIncInput("Incorrect color");
            }
            print("Incorrect data inputted!\n");
        } while (true);
        return colors.get(colorNum);
    }

    private DragonCharacter readCharacter() {
        Map<Integer, DragonCharacter> characters = new HashMap<>();
        characters.put(1, DragonCharacter.WISE);
        characters.put(2, DragonCharacter.CHAOTIC_EVIL);
        characters.put(3, DragonCharacter.FICKLE);
        int colorNum;
        do {
            print("""
                    \t1: Wise
                    \t2: Evil
                    \t3: Fickle
                    Choose character (enter number):\040""");
            try {
                colorNum = Integer.parseInt(nextLine());
                if (colorNum >= 1 && colorNum <= 3) break;
                throwIncInput("Incorrect character");
            } catch (NumberFormatException e) {
                throwIncInput("Incorrect character");
            }
            print("Incorrect data inputted!\n");
        } while (true);
        return characters.get(colorNum);
    }

    private DragonHead readHead() {
        Float eyesCount;
        do {
            print("Enter count of eyes: ");
            try {
                eyesCount = Float.parseFloat(nextLine());
                if (eyesCount == eyesCount.longValue()) break;
                throwIncInput("Incorrect count of eyes");
            } catch (NumberFormatException e) {
                throwIncInput("Incorrect count of eyes");
            }
            print("Incorrect data inputted!\n");
        } while (true);
        return new DragonHead(eyesCount);
    }

    /**
     * Reads all dragon parameters and create new dragon
     *
     * @return created dragon
     */
    public Dragon readDragon() {
        print("""
                Enter dragon profile.
                You can leave age empty
                """);
        try {
            String name = readName();
            Coordinates coords = readCoordinates();
            int age = readAge();
            long weight = readWeight();
            Color color = readColor();
            DragonCharacter character = readCharacter();
            DragonHead head = readHead();

            Dragon dragon = new Dragon(name, coords, weight, color, character, head);
            LOGGER.debug("Dragon object has created");
            if (age > -1)
                dragon.setAge(age);
            return dragon;
        } catch (IncorrectInputException e) {
            LOGGER.debug(e.getMessage());
            return null;
        }
    }
}
