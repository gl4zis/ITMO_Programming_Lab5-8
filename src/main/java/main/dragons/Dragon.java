package main.dragons;

import main.DateParser;
import main.exceptions.IncorrectFileDataException;

import java.util.Date;

public class Dragon implements Comparable<Dragon> {
    private static int uniqNumber = 1;
    private final String name; //Поле не может быть null, Строка не может быть пустой
    private final Coordinates coordinates; //Поле не может быть null
    private final long weight; //Значение поля должно быть больше 0
    private final Color color; //Поле не может быть null
    private final DragonCharacter character; //Поле не может быть null
    private final DragonHead head;
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer age; //Значение поля должно быть больше 0, Поле может быть null

    public Dragon(int id, String name, Coordinates coordinates, Date creationDate, long weight, Color color, DragonCharacter character, DragonHead head)
            throws IncorrectFileDataException {
        this(name, coordinates, weight, color, character, head);
        if (creationDate == null) throw new NullPointerException();
        if (id < 0) throw new IncorrectFileDataException("Неккоректный id объекта Dragon");
        this.id = id;
        this.creationDate = creationDate;

    }

    public Dragon(String name, Coordinates coordinates, long weight, Color color, DragonCharacter character, DragonHead head) {
        if (name == null || coordinates == null || color == null || character == null || head == null)
            throw new NullPointerException();
        if (name.equals("") || weight <= 0) throw new IncorrectFileDataException("Некорректные поля объекта Dragon");
        this.id = uniqNumber++;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = new Date();
        this.weight = weight;
        this.color = color;
        this.character = character;
        this.head = head;
    }

    public static void decUniqNumber() {
        uniqNumber--;
    }

    public static void setUniqNumber(int maxId) {
        uniqNumber = ++maxId;
    }

    @Override
    public String toString() {
        return "Id - " + id + "\n" +
                "имя - " + name + "\n" +
                "Координаты:" + coordinates + "\n" +
                "Дата создания - " + DateParser.dateToString(creationDate) + "\n" +
                "Возраст - " + age + " лет\n" +
                "Вес - " + weight + " кг\n" +
                "Цвет - " + color + "\n" +
                "Характер - " + character + "\n" +
                "Голова - " + head;
    }

    @Override
    public int hashCode() {
        return Math.toIntExact(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass().equals(this.getClass())) {
            return id.equals(((Dragon) obj).id);
        } else {
            return false;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Date GetCreationDate() {
        return creationDate;
    }

    public int getAge() {
        if (age == null) return -1;
        else return age;
    }

    public void setAge(int age) throws IncorrectFileDataException {
        if (age <= 0) throw new IncorrectFileDataException("Некорректные поля объекта Dragon");
        this.age = age;
    }

    public long getWeight() {
        return weight;
    }

    public Color getColor() {
        return color;
    }

    public DragonCharacter getDragonCharacter() {
        return character;
    }

    public DragonHead getDragonHead() {
        return head;
    }

    @Override
    public int compareTo(Dragon dragon) {
        if (dragon.equals(this)) {
            return 0;
        } else if (name.compareTo(dragon.name) == 0) {
            return id - dragon.getId();
        } else return name.compareTo(dragon.name);
    }
}