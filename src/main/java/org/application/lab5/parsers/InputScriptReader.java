package org.application.lab5.parsers;

import org.application.lab5.commands.CommandManager;
import org.application.lab5.dragons.*;
import org.application.lab5.exceptions.IncorrectDataException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InputScriptReader {
    private Reader reader;
    public boolean execution;

    public InputScriptReader(String filePath) throws FileNotFoundException, SecurityException {
        initFile(filePath);
        execution = true;
    }

    private void initFile(String filePath) throws FileNotFoundException, SecurityException {
        File script = new File(filePath);
        FileInputStream in = new FileInputStream(script);
        reader = new InputStreamReader(in);
    }

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

    public String readNextLine() {
        StringBuilder line = null;
        try {
            line = new StringBuilder();
            int nextSym = reader.read();
            boolean comment = false;
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
            if (!comment && nextSym != -1) line.deleteCharAt(line.length() - 1);
            if (nextSym == -1) execution = false;
        } catch (IOException e) {
            execution = false;
            System.out.println("Что-то случилось с файликом =(");
        }
        return line.toString();
    }

    private String readName() {
        return readNextLine();
    }

    private Coordinates readCoordinates() throws NumberFormatException, IncorrectDataException {
        double x = Double.parseDouble(readNextLine());
        float y = Float.parseFloat(readNextLine());
        return new Coordinates(x, y);
    }

    private Integer readAge() throws IncorrectDataException {
        String arg = readNextLine();
        try {
            return Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            if (arg.equals("")) {
                return null;
            } else throw new IncorrectDataException("Неправильные поля объекта Dragon");
        }
    }

    private Long readWeight() throws IncorrectDataException, NumberFormatException {
        return Long.parseLong(readNextLine());
    }

    private Color readColor() {
        String strColor = readNextLine();
        try {
            try {
                int colorNum = Integer.parseInt(strColor);
                Map<Integer, Color> colors = new HashMap<>();
                colors.put(1, Color.GREEN);
                colors.put(2, Color.RED);
                colors.put(3, Color.ORANGE);
                colors.put(4, Color.BROWN);
                return colors.get(colorNum);
            } catch (NumberFormatException e) {
                Map<String, Color> colors = new HashMap<>();
                colors.put("Зеленый", Color.GREEN);
                colors.put("Красный", Color.RED);
                colors.put("Оранжевый", Color.ORANGE);
                colors.put("Коричневый", Color.BROWN);
                return colors.get(strColor);
            }
        } catch (NullPointerException e) {
            throw new IncorrectDataException("Неправильные поля объекта Dragon");
        }
    }

    private DragonCharacter readCharacter() {
        String strChar = readNextLine();
        try {
            try {
                int charNum = Integer.parseInt(strChar);
                Map<Integer, DragonCharacter> characters = new HashMap<>();
                characters.put(1, DragonCharacter.WISE);
                characters.put(2, DragonCharacter.CHAOTIC_EVIL);
                characters.put(3, DragonCharacter.FICKLE);
                return characters.get(charNum);
            } catch (NumberFormatException e) {
                Map<String, DragonCharacter> characters = new HashMap<>();
                characters.put("Рассудительный", DragonCharacter.WISE);
                characters.put("Злой", DragonCharacter.CHAOTIC_EVIL);
                characters.put("Капризный", DragonCharacter.FICKLE);
                return characters.get(strChar);
            }
        } catch (NullPointerException e) {
            throw new IncorrectDataException("Неправильные поля объекта Dragon");
        }
    }

    private DragonHead readHead() throws NumberFormatException {
        float eyesCount = Long.parseLong(readNextLine());
        return new DragonHead(eyesCount);
    }

    public Dragon readDragon() {
        try {

            String name = readName();
            Coordinates coordinates = readCoordinates();
            Integer age = readAge();
            Long weight = readWeight();
            Color color = readColor();
            DragonCharacter character = readCharacter();
            DragonHead head = readHead();
            Dragon dragon = new Dragon(name, coordinates, weight, color, character, head);
            try {
                dragon.setAge(age);
            } catch (NullPointerException ignored) {
            }
            return dragon;
        } catch (IncorrectDataException e) {
            return null;
        }
    }
}
