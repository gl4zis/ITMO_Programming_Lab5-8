package org.application.lab5.collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.application.lab5.dragons.Dragon;
import org.application.lab5.exceptions.IdCollisionException;
import org.application.lab5.exceptions.NonUniqueValueException;
import org.application.lab5.exceptions.ObjectNotFoundException;
import org.application.lab5.parsers.DateParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

/**
 * Class that contains collection of dragons, some vars and methods to work with it
 */
public class DragonCollection {
    private static final Logger LOGGER = LogManager.getLogger(DragonCollection.class);
    private static final String TYPE = "LinkedHashSet";
    private final ArrayList<Integer> idList = new ArrayList<>();
    public boolean saved = true;
    private LinkedHashSet<Dragon> collection;
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
        saved = false;
    }

    /**
     * Removes dragon from collection
     *
     * @param dragon will be removed from collection
     */
    public void remove(Dragon dragon) {
        idList.remove((Integer) dragon.getId());
        collection.remove(dragon);
        saved = false;
    }

    /**
     * Finds dragon in collection by its id
     *
     * @param id id of dragon, which will be finds in collection
     * @throws ObjectNotFoundException if there is no dragon with this id in the collection
     */
    public Dragon find(int id) throws ObjectNotFoundException, NonUniqueValueException {
        for (Dragon dragon : collection) {
            if (dragon.getId() == id) {
                return dragon;
            }
        }
        if (id >= 100000) {
            List<Dragon> idMatch = new ArrayList<>();
            for (Dragon dragon : collection) {
                if (Integer.toString(dragon.getId()).startsWith(Integer.toString(id))) {
                    idMatch.add(dragon);
                }
            }
            if (idMatch.size() == 1) return idMatch.get(0);
            else if (idMatch.size() > 1) throw new NonUniqueValueException();
            else throw new ObjectNotFoundException();
        } else throw new ObjectNotFoundException();
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
        int dragonCount = idList.size();
        collection.clear();
        idList.clear();
        saved = false;
        LOGGER.debug(dragonCount + " dragons removed from the collection");
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
     * Sorts collection using comparator through ArrayList
     */
    public void sort(Comparator<Dragon> comp) {
        List<Dragon> listColl = new ArrayList<>(collection);
        listColl.sort(comp);
        collection = new LinkedHashSet<>(listColl);
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
     * Return string output with info about collection
     *
     * @return output
     */
    @Override
    public String toString() {
        return "Collection type: " + TYPE + "\n" +
                "Collection size: " + collection.size() + "\n" +
                "Date of creation: " + DateParser.dateToString(creationDate) + "\n" +
                "Maximum dragon id: " + getMaxId();
    }

    /**
     * Serializes all collection to the JSON
     *
     * @return jsonCollection
     */
    @SuppressWarnings("unchecked")
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("creationDate", DateParser.dateToString(creationDate));
        JSONArray dragons = new JSONArray();
        for (Dragon dragon : collection) {
            JSONObject jsonDragon = dragon.toJson();
            dragons.add(jsonDragon);
        }
        json.put("dragons", dragons);
        return json;
    }
}
