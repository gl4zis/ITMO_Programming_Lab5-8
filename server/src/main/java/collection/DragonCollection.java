package collection;

import dragons.Dragon;
import exceptions.IdCollisionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import parsers.DateParser;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class that contains collection of dragons, some vars and methods to work with it
 */
public class DragonCollection {
    private static final Logger LOGGER = LogManager.getLogger(DragonCollection.class);
    private static final String TYPE = "LinkedHashSet";
    private final ArrayList<Integer> idList = new ArrayList<>();
    private final LinkedHashSet<Dragon> collection;
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
     * Finds dragon in collection by its id
     *
     * @param id id of dragon, which will be finds in collection
     */
    public Dragon find(int id) {
        Optional<Dragon> dragon = collection.stream().filter(p -> p.getId() == id).findAny();
        return dragon.orElse(null);
    }

    /**
     * Returns maximum id from all dragon's ids in collection
     *
     * @return maxId
     */
    public int getMaxId() {
        OptionalInt maxId = idList.stream().mapToInt(p -> p).max();
        return maxId.orElse(0);
    }

    /**
     * Removes all objects from collection
     */
    public void clear() {
        int dragonCount = idList.size();
        collection.clear();
        idList.clear();
        LOGGER.debug(dragonCount + " dragons removed from the collection");
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
        return collection.stream().collect(Collectors.averagingLong(Dragon::getWeight)).longValue();
    }

    /**
     * Returns dragon with minimum age in collection
     *
     * @return minDragon
     */
    public Dragon minByAge() {
        Optional<Dragon> dragon = collection.stream().min(Dragon.ageComp);
        return dragon.orElse(null);
    }

    /**
     * Returns minimum of dragons in collection.
     * (Compares by name and id)
     *
     * @return minDragon
     */
    public Dragon getMinDragon() {
        Optional<Dragon> minDragon = collection.stream().min(Dragon::compareTo);
        return minDragon.orElse(null);
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
