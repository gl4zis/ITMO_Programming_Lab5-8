package parsers;

import dragons.*;
import exceptions.ExitException;
import exceptions.IncorrectInputException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import user.User;

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

    public User readUser(boolean sign) {
        User user = User.authorize();
        if (sign) {
            System.out.print("Repeat password: ");
            String repPasswd = nextLine();
            if (!User.hashPasswd(repPasswd, 500).equals(user.getHashedPasswd())) {
                LOGGER.warn("Incorrect repeated password");
                return null;
            }
        }
        return user;
    }

    /**
     * Using while setting up user
     *
     * @return true if user want to sign up, false if sign in
     */
    public boolean chooseUpIn() {
        String sign;
        do {
            System.out.print("If you want to sign up type '+', to sign in type '-': ");
            sign = nextLine();
            if (sign.equals("exit")) throw new ExitException();
            if (sign.equals("-")) return false;
            if (sign.equals("+")) return true;
            LOGGER.warn("Incorrect input");
        } while (true);
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
        X = readCoordinate(-497, "Enter X coordinate (fractional number > -497): ");
        Y = (float) readCoordinate(Double.NEGATIVE_INFINITY, "Enter Y coordinate: ");
        return new Coordinates(X, Y);
    }

    private double readCoordinate(double minValue, String message) {
        double X;
        do {
            print(message);
            try {
                X = Double.parseDouble(nextLine());
                if (X <= minValue) {
                    throwIncInput("Incorrect coordinate");
                } else break;
            } catch (NumberFormatException e) {
                throwIncInput("Incorrect coordinate");
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
                if (Integer.parseInt(ageStr) > 0)
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
                long weight = Long.parseLong(nextLine());
                if (weight > 0)
                    return weight;
            } catch (NumberFormatException e) {
                throwIncInput("Incorrect weight");
            }
            print("Incorrect data inputted!\n");
        } while (true);
    }

    private Color readColor() {
        Map<Integer, Color> colors = new HashMap<>();
        for (Color color : Color.values()) {
            colors.put(color.ordinal() + 1, color);
        }
        int colorNum;
        do {
            for (Color color : Color.values()) {
                print(String.format("\t%d: %s\n", color.ordinal() + 1, color.name()));
            }
            print("Choose color (enter number): ");
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
        for (DragonCharacter character : DragonCharacter.values()) {
            characters.put(character.ordinal() + 1, character);
        }
        int colorNum;
        do {
            for (DragonCharacter character : DragonCharacter.values()) {
                print(String.format("\t%d: %s\n", character.ordinal() + 1, character.name()));
            }
            print("Choose character (enter number): ");
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
    public Dragon readDragon(User user) {
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

            Dragon dragon = new Dragon(name, coords, weight, color, character, head, user);
            LOGGER.debug("Dragon object has created");
            if (age > -1)
                dragon.setAge(age);
            return dragon;
        } catch (IncorrectInputException | NullPointerException | ClassCastException e) {
            LOGGER.debug(e.getMessage());
            return null;
        }
    }
}
