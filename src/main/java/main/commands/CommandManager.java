package main.commands;

import main.InputReader;
import main.dragons.*;
import main.exceptions.IncorrectInputException;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    public Map<String, Command> commands;

    public CommandManager() {
        commands = addStandartCommands();
    }

    private Map<String, Command> addStandartCommands() {
        Command add = new AddCommand();
        Command addIfMin = new AddIfMinCommand();
        Command averageOfWeight = new AverageOfWeightCommand();
        Command clear = new ClearCommand();
        Command executeScript = new ExecuteScriptCommand();
        Command exit = new ExitCommand();
        Command filterLessThanWeight = new FilterLessThanWeightCommand();
        Command help = new HelpCommand();
        Command info = new InfoCommand();
        Command minByAge = new MinByAgeCommand();
        Command removeById = new RemoveByIdCommand();
        Command removeGreater = new RemoveGreaterCommand();
        Command removeLower = new RemoveLowerCommand();
        Command save = new SaveCommand();
        Command show = new ShowCommand();
        Command update = new UpdateCommand();
        Map<String, Command> commands = new HashMap<>();
        commands.put(add.getName(), add);
        commands.put(addIfMin.getName(), addIfMin);
        commands.put(averageOfWeight.getName(), averageOfWeight);
        commands.put(clear.getName(), clear);
        commands.put(executeScript.getName(), executeScript);
        commands.put(exit.getName(), exit);
        commands.put(filterLessThanWeight.getName(), filterLessThanWeight);
        commands.put(help.getName(), help);
        commands.put(info.getName(), info);
        commands.put(minByAge.getName(), minByAge);
        commands.put(removeById.getName(), removeById);
        commands.put(removeGreater.getName(), removeGreater);
        commands.put(removeLower.getName(), removeLower);
        commands.put(save.getName(), save);
        commands.put(show.getName(), show);
        commands.put(update.getName(), update);
        return commands;
    }

    public void seekCommand(String line) throws IncorrectInputException {
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


    static Dragon readDragon() {
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

    public void addNewCommand(ArgsCommand newCommand) {
        commands.put(newCommand.getName(), newCommand);
    }
}
