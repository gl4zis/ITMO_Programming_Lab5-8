package org.application.lab5.collection;

import org.application.lab5.parsers.DateParser;
import org.application.lab5.dragons.Dragon;
import org.application.lab5.exceptions.IdCollisionException;
import org.application.lab5.exceptions.ObjectNotFoundException;

import java.util.*;

public class DragonCollection {
    private LinkedHashSet<Dragon> collection;
    private final String type = "LinkedHashSet";
    private final ArrayList<Integer> idList = new ArrayList<>();
    private Date creationDate;
    private int length;
    private int maxId;

    public DragonCollection() {
        collection = new LinkedHashSet<>();
        length = 0;
        maxId = 0;
        creationDate = new Date();
    }

    public void add(Dragon item) throws IdCollisionException {
        if (idList.contains(item.getId())) throw new IdCollisionException();
        idList.add(item.getId());
        maxId = arrayMaxInt(idList);
        collection.add(item);
        length++;
    }

    public void remove(Dragon dragon) {
        idList.remove((Integer) dragon.getId());
        maxId = arrayMaxInt(idList);
        collection.remove(dragon);
        length--;
    }

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

    private int arrayMaxInt(ArrayList<Integer> list) {
        int maxItem = 0;
        for (Integer item : list) {
            if (item > maxItem) maxItem = item;
        }
        return maxItem;
    }

    public void setMaxId(int maxId) {
        this.maxId = maxId;
    }

    public void clear() {
        collection.clear();
        idList.clear();
        maxId = 0;
        length = 0;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date date) {
        creationDate = date;
    }

    public Collection<Dragon> getItems() {
        return collection;
    }


    public long getAverageWeight() {
        long AverageWeight = 0;
        for (Dragon dragon : collection) {
            long weight = dragon.getWeight();
            AverageWeight += weight;
        }
        AverageWeight /= collection.size();
        return AverageWeight;
    }

    public Dragon minByAge() {
        double minAge = Double.POSITIVE_INFINITY;
        Dragon minItem = null;
        for (Dragon dragon : collection) {
            if (dragon.getAge() >= 0) {
                int age = dragon.getAge();
                if (age - minAge < 0) {
                    minAge = age;
                    minItem = dragon;
                }
            }
        }
        return minItem;
    }

    public Dragon getMin() {
        if (length == 0) return null;
        else {
            Dragon minDragon = (Dragon) collection.toArray()[0];
            for (Dragon dragon : collection) {
                if (dragon.compareTo(minDragon) < 0) minDragon = dragon;
            }
            return minDragon;
        }
    }

    public void sort() {
        ArrayList<Dragon> collectionList = new ArrayList<>(collection);
        Collections.sort(collectionList);
        collection = new LinkedHashSet<>(collectionList);
    }

    @Override
    public String toString() {
        return "Тип коллекции: " + type + "\n" +
                "Количество элементов: " + length + "\n" +
                "Дата создания: " + DateParser.dateToString(creationDate) + "\n" +
                "Наибольший Id элементов: " + maxId;
    }
}
