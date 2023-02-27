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

    public static void seekScriptCommand(Reader reader, String line) throws IncorrectInputException, IOException {
        String[] input = line.split(" ");
        if (input.length > 2)
            throw new IncorrectInputException("Неизвестная команда");
        else {
            String command = input[0];
            if (commands.containsKey(command)) {
                try {
                    String arg = input[1];
                    commands.get(command).scriptExecute(reader, arg);
                } catch (ArrayIndexOutOfBoundsException e) {
                    commands.get(command).scriptExecute(reader);
                }
            } else {
                throw new IncorrectInputException("Неизвестная команда");
            }
        }
    }

    protected static Dragon readDragonFromScript(Reader reader) throws IOException, IncorrectInputException {
        Map<Integer, Color> colors = new HashMap<>();
        colors.put(1, Color.GREEN);
        colors.put(2, Color.RED);
        colors.put(3, Color.ORANGE);
        colors.put(4, Color.BROWN);
        Map<Integer, DragonCharacter> characters = new HashMap<>();
        characters.put(1, DragonCharacter.WISE);
        characters.put(2, DragonCharacter.CHAOTIC_EVIL);
        characters.put(3, DragonCharacter.FICKLE);

        double x = 0;
        float y = 0;
        int age = -1;
        long weight = 0;
        Color color = null;
        DragonCharacter character = null;
        float eyesCount = 0;
        DragonHead head = null;
        boolean mistake = false;
        String name = InputReader.readNextFileLine(reader);
        if (name.equals("")) mistake = true;
        try {
            x = Double.parseDouble(InputReader.readNextFileLine(reader));
            if (x <= -497) mistake = true;
        } catch (NumberFormatException e) {
            mistake = true;
        }
        try {
            y = Float.parseFloat(InputReader.readNextFileLine(reader));
        } catch (NumberFormatException e) {
            mistake = true;
        }
        Coordinates coordinates = new Coordinates(x, y);
        try {
            String arg = InputReader.readNextFileLine(reader);
            if (!arg.equals("")) age = Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            mistake = true;
        } catch (NullPointerException ignore) {
        }
        try {
            weight = Long.parseLong(InputReader.readNextFileLine(reader));
        } catch (NumberFormatException e) {
            mistake = true;
        }
        try {
            int colorNumber = Integer.parseInt(InputReader.readNextFileLine(reader));
            if (colorNumber >= 1 && colorNumber <= 4) color = colors.get(colorNumber);
            else mistake = true;
        } catch (NumberFormatException e) {
            mistake = true;
        }
        try {
            int chrNumber = Integer.parseInt(InputReader.readNextFileLine(reader));
            if (chrNumber >= 1 && chrNumber <= 3) character = characters.get(chrNumber);
            else mistake = true;
        } catch (NumberFormatException e) {
            mistake = true;
        }
        try {
            eyesCount = Float.parseFloat(InputReader.readNextFileLine(reader));
        } catch (NumberFormatException e) {
            mistake = true;
        }
        head = new DragonHead(eyesCount);
        if (mistake) throw new IncorrectInputException("Некорректные данный нового объекта");
        else {
            Dragon dragon = new Dragon(name, coordinates, weight, color, character, head);
            try {
                dragon.setAge(age);
            } catch (IncorrectFileDataException ignore) {
            }
            return dragon;
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

    public abstract void scriptExecute(Reader reader) throws IncorrectInputException, IOException;

    public abstract void scriptExecute(Reader reader, String arg) throws IncorrectInputException;
}
