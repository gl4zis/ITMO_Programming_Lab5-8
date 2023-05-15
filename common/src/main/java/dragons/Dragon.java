package dragons;

import exceptions.IncorrectDataException;
import org.apache.commons.lang3.RandomStringUtils;
import parsers.DateParser;
import user.User;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

/**
 * Class describing dragon objects, that stored at collection.
 * Has unique id number, name, coordinates, weight, date of creation, color, character, head and may have age.
 */

public class Dragon implements Comparable<Dragon>, Serializable {
    public static final Comparator<Dragon> coordComp = (o1, o2) -> {
        if (o1.coordinates.compareTo(o2.coordinates) == 0)
            return o1.id - o2.id;
        else return o1.coordinates.compareTo(o2.coordinates);
    };
    public static final Comparator<Dragon> ageComp = (o1, o2) -> {
        if (o1.getAge() == o2.getAge())
            return o1.id - o2.id;
        else return o1.getAge() - o2.getAge();
    };
    private final User creator;
    private String key;
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private long weight; //Значение поля должно быть больше 0
    private Color color; //Поле не может быть null
    private DragonCharacter character; //Поле не может быть null
    private DragonHead head;
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer age; //Значение поля должно быть больше 0, Поле может быть null

    /**
     * Constructor, sets all the params.
     * For creating dragon by data from database
     *
     * @throws IncorrectDataException if some params are incorrect
     */
    public Dragon(int id, String key, String name, Coordinates coordinates, Date creationDate, long weight, Color color, DragonCharacter character, DragonHead head, User user)
            throws IncorrectDataException {
        this(name, coordinates, weight, color, character, head, user);
        if (creationDate == null || user == null)
            throw new IncorrectDataException("Incorrect date of creation or about creator");
        if (id <= 0) throw new IncorrectDataException("Incorrect id");
        this.id = id;
        this.key = key;
        this.creationDate = creationDate;
    }

    /**
     * Constructor with auto-generated creation date.
     * For creating dragon by user
     *
     * @throws IncorrectDataException if some params are incorrect
     */
    public Dragon(String name, Coordinates coordinates, long weight, Color color, DragonCharacter character, DragonHead head, User user)
            throws IncorrectDataException {
        if (name == null || coordinates == null || color == null || character == null || head == null)
            throw new IncorrectDataException("There are nulls in dragon characteristics");
        if (name.equals("") || weight <= 0)
            throw new IncorrectDataException("Incorrect dragon characteristics (name or weight)");
        this.id = 1;
        this.key = RandomStringUtils.randomAlphanumeric(16);
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = new Date();
        this.weight = weight;
        this.color = color;
        this.character = character;
        this.head = head;
        this.creator = user;
    }

    /**
     * Updates this using vars from other dragon.
     * Using in update command
     *
     * @param dragon object, on that swaps this, saving id and creationDate
     */
    public void update(Dragon dragon) {
        name = dragon.getName();
        coordinates = dragon.getCoordinates();
        if (dragon.getAge() > -1) age = dragon.getAge();
        else age = null;
        weight = dragon.getWeight();
        color = dragon.getColor();
        character = dragon.getDragonCharacter();
        head = dragon.getDragonHead();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Returns age. If age == null, returns -1
     *
     * @return age
     */
    public int getAge() {
        if (age == null) return -1;
        else return age;
    }

    /**
     * Sets age. If age <= 0 throws exception
     */
    public void setAge(int age) {
        if (age <= 0) throw new IncorrectDataException("Incorrect age for dragon");
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

    public Date getCreationDate() {
        return creationDate;
    }

    public User getCreator() {
        return creator;
    }

    /**
     * Returns string output with info about this dragon
     *
     * @return output
     */
    @Override
    public String toString() {
        return "Id - " + id + "\n" +
                "Key - " + key + "\n" +
                "Name - " + name + "\n" +
                "Coordinates:" + coordinates + "\n" +
                "Date of creation - " + DateParser.dateToString(creationDate) + "\n" +
                "Age - " + age + " years\n" +
                "Weight - " + weight + " kg\n" +
                "Color - " + color + "\n" +
                "Character - " + character + "\n" +
                "Head - " + head + "\n" +
                "Creator - " + creator;
    }

    /**
     * Returns id as a hashCode
     *
     * @return id
     */
    @Override
    public int hashCode() {
        return id;
    }

    /**
     * Compares two dragons. Returns true if objects are equals, false if not
     *
     * @param obj other dragon, that compares with this
     * @return this.equals(object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass().equals(this.getClass())) {
            return id.equals(((Dragon) obj).id);
        } else {
            return false;
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Compares two dragons. Returns positive integral number if this dragon more than input dragon.
     * Returns negative integral dragons if this dragon less than input dragon.
     * Returns 0 if this.equals(dragon) == true
     *
     * @param dragon the object to be compared.
     */
    @Override
    public int compareTo(Dragon dragon) {
        if (name.compareTo(dragon.name) == 0) {
            return id - dragon.hashCode();
        } else return name.compareTo(dragon.name);
    }
}