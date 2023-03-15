package org.application.lab5.parsers;

import org.application.lab5.dragons.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class for reading from console
 */

public abstract class InputConsoleReader {

    private static final String ERROR_MESSAGE = "Неверный ввод. Повторите еще раз";

    /** Reads next line from the console.
     * Stops the app, if input == "exit 0"
     * @return inputLine
     */
    public static String readNextLine() {
        String line = "";
        try {
            Scanner console = new Scanner(System.in);
            line = console.nextLine();
            if (line.equals("exit 0")) {
                System.exit(0);
            }
        } catch (NoSuchElementException e) {
            System.exit(0);
        }
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
                System.out.println(ERROR_MESSAGE);
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
                System.out.println(ERROR_MESSAGE);
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
            System.out.println(ERROR_MESSAGE);
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
                System.out.println(ERROR_MESSAGE);
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
            System.out.println(ERROR_MESSAGE);
        } while (true);
        return var;
    }

    /** Reads dragon name from console
     * @return dragonName
     */
    private static String readDragonName() {
        System.out.print("Введите имя: ");
        return readStringVar(false);
    }

    /** Reads dragon coordinates from console.
     * (Two fractional numbers x and y.
     * X should be more than -497)
     * @return dragonCoordinates
     */
    private static Coordinates readDragonCoordinates() {
        System.out.print("Введите координату x (дробное число >-497): ");
        double x = readDoubleVar(false, -497 + 1E-13, Double.POSITIVE_INFINITY);
        System.out.print("Введите координату y (дробное число): ");
        float y = readDoubleVar(false).floatValue();
        return new Coordinates(x, y);
    }

    /** Reads dragon age from console.
     * Expects positive integral number.
     * If input == "", returns -1
     * @return dragonAge
     */
    private static int readDragonAge() {
        System.out.print("Введите возраст (целое число от 1 до " + Integer.MAX_VALUE + "): ");
        Long age = readLongVar(true, 1, Long.MAX_VALUE);
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
        System.out.print("Введите вес (целое число от 1 до " + Long.MAX_VALUE + "): ");
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
                Выберите цвет (введите цифру)
                \t1: Зеленый
                \t2: Красный
                \t3: Оранжевый
                \t4: Коричневый
                """);
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
                Выберите характер (введите цифру)
                \t1: Рассудительный
                \t2: Злой
                \t3: Капризный
                """);
        int colorNum = readLongVar(false, 1, 3).intValue();
        return character.get(colorNum);
    }

    /** Reads dragon head from console.
     * Expects non-negative integral number in console as count of eyes
     * @return dragonHead
     */
    private static DragonHead readDragonHead() {
        System.out.print("Введите количество глаз (целое неотрицательное число): ");
        long eyesCount = readLongVar(false, 0, Long.MAX_VALUE);
        return new DragonHead(eyesCount);
    }

    /** Reads whole dragon from console
     * @return dragon
     */
    public static Dragon readDragon() {
        System.out.println("""
                Введите все поля объекта Dragon.
                Поле 'Возраст' можно оставить пустым.""");
        int age = -1;
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
        return dragon;
    }
}
