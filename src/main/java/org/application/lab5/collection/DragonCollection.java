package org.application.lab5.collection;

import org.application.lab5.parsers.DateParser;
import org.application.lab5.dragons.Dragon;
import org.application.lab5.exceptions.IdCollisionException;
import org.application.lab5.exceptions.ObjectNotFoundException;

import java.util.*;

/**
 * Class that contains collection of dragons, some vars and methods for work with it
 */

public class DragonCollection {

    private LinkedHashSet<Dragon> collection;
    private static final String TYPE = "LinkedHashSet";
    private final ArrayList<Integer> idList = new ArrayList<>();
    private Date creationDate;

    /**
     * Constructor creates new empty collection
     */
    public DragonCollection() {
        collection = new LinkedHashSet<>();
        creationDate = new Date();
    }

    /**
     * Adds new dragon in collection
     *
     * @param dragon will be added in collection
     * @throws IdCollisionException if dragon with this id already in the collection
     */
    public void add(Dragon dragon) throws IdCollisionException {
        if (idList.contains(dragon.getId())) throw new IdCollisionException();
        idList.add(dragon.getId());
        collection.add(dragon);
    }

    /**
     * Removes dragon from collection
     *
     * @param dragon will be removed from collection
     */
    public void remove(Dragon dragon) {
        idList.remove((Integer) dragon.getId());
        collection.remove(dragon);
    }

    /**
     * Removes dragon from collection by its id
     *
     * @param id id of dragon, which will be removed from collection
     * @throws ObjectNotFoundException if there is no dragon with this id in the collection
     */
    public void remove(int id) throws ObjectNotFoundException {
        boolean contains = false;
        for (Dragon dragon : getItems()) {
            if (dragon.getId() == id) {
                remove(dragon);
                contains = true;
                break;
            }
        }
        if (!contains) throw new ObjectNotFoundException();
    }

    /**
     * Returns maximum id from all dragon's ids in collection
     *
     * @return maxId
     */
    public int getMaxId() {
        int maxId = 0;
        for (Integer id : idList) {
            if (id > maxId) maxId = id;
        }
        return maxId;
    }

    /**
     * Removes all objects from collection
     */
    public void clear() {
        collection.clear();
        idList.clear();
    }

    /**
     * Returns date of creation this collection
     *
     * @return creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Sets date of creation, read from JSON file
     *
     * @param date will be sets to this collection
     */
    void setCreationDate(Date date) {
        creationDate = date;
    }

    /**
     * Returns collection of dragons
     *
     * @return collection
     */
    public Collection<Dragon> getItems() {
        return collection;
    }

    /**
     * Returns average of dragon's weights in collection
     *
     * @return averageWeight
     */
    public long getAverageWeight() {
        long averageWeight = 0;
        for (Dragon dragon : collection) {
            long weight = dragon.getWeight();
            averageWeight += weight;
        }
        averageWeight /= collection.size();
        return averageWeight;
    }

    /**
     * Returns dragon with minimum age in collection
     *
     * @return minDragon
     */
    public Dragon minByAge() {
        double minAge = Double.POSITIVE_INFINITY;
        Dragon minDragon = null;
        for (Dragon dragon : collection) {
            if (dragon.getAge() >= 0) {
                int age = dragon.getAge();
                if (age - minAge < 0) {
                    minAge = age;
                    minDragon = dragon;
                }
            }
        }
        return minDragon;
    }

    /**
     * Returns minimum of dragons in collection.
     * (Compares by name and id)
     *
     * @return minDragon
     */
    public Dragon getMinDragon() {
        if (collection.size() == 0) return null;
        else {
            Dragon minDragon = (Dragon) collection.toArray()[0];
            for (Dragon dragon : collection) {
                if (dragon.compareTo(minDragon) < 0) minDragon = dragon;
            }
            return minDragon;
        }
    }

    /**
     * Sorts collection.
     * (By name and id)
     */
    public void sort() {
        ArrayList<Dragon> collectionList = new ArrayList<>(collection);
        Collections.sort(collectionList);
        collection = new LinkedHashSet<>(collectionList);
    }

    /**
     * Return string output with info about collection
     *
     * @return output
     */
    @Override
    public String toString() {
        return "Тип коллекции: " + TYPE + "\n" +
                "Количество элементов: " + collection.size() + "\n" +
                "Дата создания: " + DateParser.dateToString(creationDate) + "\n" +
                "Наибольший Id элементов: " + getMaxId();
    }
}
