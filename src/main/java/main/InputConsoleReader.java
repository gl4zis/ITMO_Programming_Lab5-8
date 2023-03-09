package main;

import main.commands.CommandManager;
import main.dragons.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public abstract class InputConsoleReader {

    static final String ERROR_MESSAGE = "Неверный ввод. Повторите еще раз";

    public static String readNextLine() {
        String line = "";
        try {
            Scanner console = new Scanner(System.in);
            line = console.nextLine();
        } catch (NoSuchElementException e) {
            System.exit(0);
        }
        return line;
    }

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

    private static String readDragonName() {
        System.out.print("Введите имя: ");
        return readStringVar(false);
    }

    private static Coordinates readDragonCoordinates() {
        System.out.print("Введите координату x (дробное число >-497): ");
        double x = readDoubleVar(false, -497 + 1E-13, Double.POSITIVE_INFINITY);
        System.out.print("Введите координату y (дробное число): ");
        float y = readDoubleVar(false).floatValue();
        return new Coordinates(x, y);
    }

    private static int readDragonAge() {
        System.out.print("Введите возраст (целое число от 1 до " + Integer.MAX_VALUE + "): ");
        Long age = readLongVar(true, 1, Long.MAX_VALUE);
        if (age != null)
            return age.intValue();
        else
            return -1;
    }

    private static long readDragonWeight() {
        System.out.print("Введите вес (целое число от 1 до " + Long.MAX_VALUE + "): ");
        return readLongVar(false, 1, Long.MAX_VALUE);
    }

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

    private static DragonHead readDragonHead() {
        System.out.print("Введите количество глаз (целое неотрицательное число): ");
        long eyesCount = readLongVar(false, 0, Long.MAX_VALUE);
        return new DragonHead(eyesCount);
    }

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
