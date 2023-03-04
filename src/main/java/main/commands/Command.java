package main.commands;

import main.InputReader;
import main.dragons.*;
import main.exceptions.IncorrectFileDataException;
import main.exceptions.IncorrectInputException;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public abstract class Command {
    public static Map<String, Command> commands;

    static {
        commands = new HashMap<>();
        commands.put(HelpCommand.getInstance().getName(), HelpCommand.getInstance());
        commands.put(InfoCommand.getInstance().getName(), InfoCommand.getInstance());
        commands.put(ShowCommand.getInstance().getName(), ShowCommand.getInstance());
        commands.put(AddCommand.getInstance().getName(), AddCommand.getInstance());
        commands.put(ClearCommand.getInstance().getName(), ClearCommand.getInstance());
        commands.put(SaveCommand.getInstance().getName(), SaveCommand.getInstance());
        commands.put(ExitCommand.getInstance().getName(), ExitCommand.getInstance());
        commands.put(AddIfMinCommand.getInstance().getName(), AddIfMinCommand.getInstance());
        commands.put(RemoveGreaterCommand.getInstance().getName(), RemoveGreaterCommand.getInstance());
        commands.put(RemoveLowerCommand.getInstance().getName(), RemoveLowerCommand.getInstance());
        commands.put(AverageOfWeightCommand.getInstance().getName(), AverageOfWeightCommand.getInstance());
        commands.put(MinByAgeCommand.getInstance().getName(), MinByAgeCommand.getInstance());
        commands.put(RemoveByIdCommand.getInstance().getName(), RemoveByIdCommand.getInstance());
        commands.put(UpdateCommand.getInstance().getName(), UpdateCommand.getInstance());
        commands.put(ExecuteScriptCommand.getInstance().getName(), ExecuteScriptCommand.getInstance());
        commands.put(FilterLessThanWeightCommand.getInstance().getName(), FilterLessThanWeightCommand.getInstance());
    }

    private final String name;
    protected boolean haveArgs;

    protected Command(String name) {
        this.name = name;
    }

    public static void seekCommand(String line) throws IncorrectInputException {
        String[] input = line.split(" ");
        if (input.length > 2)
            throw new IncorrectInputException("Неизвестная команда. Введите команду help, чтобы посмотреть информацию о коммандах");
        else {
            String command = input[0];
            if (commands.containsKey(command)) {
                try {
                    String arg = input[1];
                    commands.get(command).execute(arg);
                } catch (ArrayIndexOutOfBoundsException e) {
                    commands.get(command).execute();
                }
            } else {
                throw new IncorrectInputException("Неизвестная команда. Введите команду help, чтобы посмотреть информацию о коммандах");
            }
        }
    }


    protected static Dragon readDragon() {
        System.out.println("""
                Введите все поля объекта Dragon.
                Поле 'Возраст' можно оставить пустым.""");
        Map<Integer, Color> colors = new HashMap<>();
        colors.put(1, Color.GREEN);
        colors.put(2, Color.RED);
        colors.put(3, Color.ORANGE);
        colors.put(4, Color.BROWN);
        Map<Integer, DragonCharacter> characters = new HashMap<>();
        characters.put(1, DragonCharacter.WISE);
        characters.put(2, DragonCharacter.CHAOTIC_EVIL);
        characters.put(3, DragonCharacter.FICKLE);
        String name;
        double x;
        float y;
        int age = -1;
        long weight;
        Color color;
        DragonCharacter character;
        DragonHead head;
        float eyesCount;
        do {
            System.out.print("Введите имя: ");
            name = InputReader.readNextConsoleLine();
            if (!name.equals("")) break;
            System.out.println("Неверный ввод");
        } while (true);
        do {
            System.out.print("Введите координату x (дробное число > -497.0): ");
            try {
                x = Double.parseDouble(InputReader.readNextConsoleLine());
                if (x > -497) break;
                else System.out.println("Неверный ввод");
            } catch (NumberFormatException e) {
                System.out.println("Неверный ввод");
            }
        } while (true);
        do {
            System.out.print("Введите координату y (дробное число): ");
            try {
                y = Float.parseFloat(InputReader.readNextConsoleLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Неверный ввод");
            }
        } while (true);
        Coordinates coordinates = new Coordinates(x, y);
        do {
            System.out.print("Введите возраст (целое число от 1 до " + Integer.MAX_VALUE + "): ");
            String str = InputReader.readNextConsoleLine();
            if (str.equals("")) {
                break;
            } else {
                try {
                    age = Integer.parseInt(str);
                    if (age > 0) break;
                    else System.out.println("Неверный ввод");
                } catch (NumberFormatException e) {
                    System.out.println("Неверный ввод");
                }
            }
        } while (true);
        do {
            System.out.print("Введите вес (целое число от 1 до " + Long.MAX_VALUE + "): ");
            try {
                weight = Long.parseLong(InputReader.readNextConsoleLine());
                if (weight > 0) break;
                else System.out.println("Неверный ввод");
            } catch (NumberFormatException e) {
                System.out.println("Неверный ввод");
            }
        } while (true);
        do {
            System.out.print("""
                    Выберите цвет (введите цифру)
                    \t1: Зеленый
                    \t2: Красный
                    \t3: Оранжевый
                    \t4: Коричневый
                    """);
            try {
                int colorNumber = Integer.parseInt(InputReader.readNextConsoleLine());
                if (4 >= colorNumber && 1 <= colorNumber) {
                    color = colors.get(colorNumber);
                    break;
                } else System.out.println("Неверный ввод");
            } catch (NumberFormatException e) {
                System.out.println("Неверный ввод");
            }
        } while (true);
        do {
            System.out.print("""
                    Выберите характер (введите цифру)
                    \t1: Рассудительный
                    \t2: Злой
                    \t3: Капризный
                    """);
            try {
                int chrNumber = Integer.parseInt(InputReader.readNextConsoleLine());
                if (3 >= chrNumber && 1 <= chrNumber) {
                    character = characters.get(chrNumber);
                    break;
                } else System.out.println("Неверный ввод");
            } catch (NumberFormatException e) {
                System.out.println("Неверный ввод");
            }
        } while (true);
        do {
            System.out.print("Введите количество глаз (дробное число): ");
            try {
                eyesCount = Float.parseFloat(InputReader.readNextConsoleLine());
                head = new DragonHead(eyesCount);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Неверный ввод");
            }
        } while (true);
        Dragon dragon = new Dragon(name, coordinates, weight, color, character, head);
        if (age > 0) {
            dragon.setAge(age);
        }
        return dragon;
    }

    public static Command getInstance() {
        return null;
    }

    public static void addNewCommand(ArgsCommand newCommand) {
        commands.put(newCommand.getName(), newCommand);
    }

    public String getName() {
        return name;
    }

    public abstract void execute() throws IncorrectInputException;

    public abstract void execute(String arg) throws IncorrectInputException;
}
