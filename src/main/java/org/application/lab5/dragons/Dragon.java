package org.application.lab5.dragons;

import org.application.lab5.exceptions.IncorrectDataException;
import org.application.lab5.general.UniqIdGenerator;
import org.application.lab5.parsers.DateParser;

import java.util.Date;

/**
 * Class describing dragon objects, that stored at collection.
 * Has unique id number, name, coordinates, weight, date of creation, color, character, head and may have age.
 */

public class Dragon implements Comparable<Dragon> {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private long weight; //Значение поля должно быть больше 0
    private Color color; //Поле не может быть null
    private DragonCharacter character; //Поле не может быть null
    private DragonHead head;
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer age; //Значение поля должно быть больше 0, Поле может быть null

    /** Constructor, sets all the params.
     * For creating dragon by data from JSON file
     * @throws IncorrectDataException if some params are incorrect
     */
    public Dragon(int id, String name, Coordinates coordinates, Date creationDate, long weight, Color color, DragonCharacter character, DragonHead head)
            throws IncorrectDataException {
        this(name, coordinates, weight, color, character, head);
        if (creationDate == null) throw new IncorrectDataException("Incorrect date of creation");
        if (id < 0) throw new IncorrectDataException("Incorrect id");
        this.id = id;
        this.creationDate = creationDate;
    }

    /** Constructor with auto-generated id and creation date.
     * For create dragon by user
     * @throws IncorrectDataException if some params are incorrect
     */
    public Dragon(String name, Coordinates coordinates, long weight, Color color, DragonCharacter character, DragonHead head)
            throws IncorrectDataException {
        if (name == null || coordinates == null || color == null || character == null || head == null)
            throw new IncorrectDataException("There are nulls in dragon characteristics");
        if (name.equals("") || weight <= 0)
            throw new IncorrectDataException("Incorrect dragon characteristics (name or weight)");
        this.id = UniqIdGenerator.getIntId();
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = new Date();
        this.weight = weight;
        this.color = color;
        this.character = character;
        this.head = head;
    }

    /** Updates this using vars from other dragon.
     * Using in update command
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

    /** Returns id
     * @return id
     */
    public int getId() {
        return id;
    }

    /** Returns name
     * @return name
     */
    public String getName() {
        return name;
    }

    /** Returns coordinates
     * @return coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /** Returns date of creation
     * @return creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /** Returns age. If age == null, returns -1
     * @return age
     */
    public int getAge() {
        if (age == null) return -1;
        else return age;
    }

    /** Sets age, expects positive integer
     * @param age positive integral number
     * @throws IncorrectDataException if it's not a number, or it's negative number
     */
    public void setAge(int age) throws IncorrectDataException {
        if (age <= 0) throw new IncorrectDataException("Incorrect age for dragon");
        this.age = age;
    }

    /** Returns weight
     * @return weight
     */
    public long getWeight() {
        return weight;
    }

    /** Returns color
     * @return color
     */
    public Color getColor() {
        return color;
    }

    /** Returns character
     * @return character
     */
    public DragonCharacter getDragonCharacter() {
        return character;
    }

    /** Returns head
     * @return head
     */
    public DragonHead getDragonHead() {
        return head;
    }

    /** Returns string output with info about this dragon
     * @return output
     */
    @Override
    public String toString() {
        return "Id - " + id + "\n" +
                "Name - " + name + "\n" +
                "Coordinates:" + coordinates + "\n" +
                "Date of creation - " + DateParser.dateToString(creationDate) + "\n" +
                "Age - " + age + " years\n" +
                "Weight - " + weight + " kg\n" +
                "Color - " + color + "\n" +
                "Character - " + character + "\n" +
                "Head - " + head;
    }

    /** Returns id as a hash code
     * @return id
     */
    @Override
    public int hashCode() {
        return id;
    }

    /** Compares two dragons. Returns true if objects are equals, false if not
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

    /** Compares two dragons. Returns positive integral number if this dragon more than input dragon.
     * Returns negative integral dragons if this dragon less than input dragon.
     * Returns 0 if this.equals(dragon) == true
     * @param dragon the object to be compared.
     */
    @Override
    public int compareTo(Dragon dragon) {
        if (dragon.equals(this)) {
            return 0;
        } else if (name.compareTo(dragon.name) == 0) {
            return id - dragon.getId();
        } else return name.compareTo(dragon.name);
    }
}