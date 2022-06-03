package util;

import data.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * This class is responsible for all operations with collection
 */
public class StorageManager {
    private final SortedSet<Worker> storage;
    private final ZonedDateTime initTime;
    private volatile boolean isModified = false;

    private static StorageManager instance;

    public static StorageManager getInstance() {
        if (instance == null) {
            instance = new StorageManager();
        }
        return instance;
    }

    private StorageManager() {
        storage = new ConcurrentSkipListSet<>();
        initTime = ZonedDateTime.now();
    }

    /**
     * @return Returns maximal worker
     */
    public Worker getMax() {
        Worker max;
        try {
            storage.first();
        } catch (NoSuchElementException e) {
            return null;
        }
        max = storage.stream().max(Worker::compareTo).get();
        return max;
    }

    /**
     * @return Returns minimal worker
     */
    public Worker getMin() {
        Worker min;
        try {
            storage.first();
        } catch (NoSuchElementException e) {
            return null;
        }
        min = storage.stream().min(Worker::compareTo).get();
        return min;
    }

    /**
     * Adds new worker instance to collection
     *
     * @param worker worker instance to be added
     * @return true if collection hasn't already contain this worker instance and addition is successful
     */
    public boolean add(Worker worker) {
        isModified = true;
        if (worker == null) {
            return false;
        }
        return storage.add(worker);
    }

    /**
     * @param id id of required worker instance
     * @return worker instance with required id
     */
    public Worker getById(int id) {
        Worker worker;
        worker = storage.stream().filter(w -> w.getId() == id).findFirst().orElse(null);
        return worker;
    }

    /**
     * @param worker worker class instance to be removed
     * @return true if removing is successful
     */
    public boolean remove(Worker worker) {
        isModified = true;
        return storage.remove(worker);
    }

    public boolean remove(Collection<Worker> workers) {
        isModified = true;
        return storage.removeAll(workers);
    }

    /**
     * Removes all elements from collection
     */
    public void clear() {
        isModified = true;
        storage.clear();
    }

    public void updateAll(ResultSet data) throws SQLException {
        clear();
        while (data.next()) {
            add(new Worker(data.getString(2),
                    new Coordinates(data.getLong(3), data.getLong(4)),
                    data.getDate(5), data.getInt(6),
                    ZonedDateTime.of(data.getTimestamp(7).toLocalDateTime(), TimeZone.getDefault().toZoneId()),
                    data.getDate(8) != null ? data.getDate(8).toLocalDate() : null,
                    Status.valueOf(data.getString(9)), new Person(data.getDouble(10),
                    Country.valueOf(data.getString(13)),
                    data.getString(11).equals("null") ? null : Color.valueOf(data.getString(11)),
                    data.getString(12).equals("null") ? null : Color.valueOf(data.getString(12))),
                    data.getInt(1), data.getString(14)));
        }
    }

    /**
     * @return true if collection have unsaved changes
     */
    public boolean isModified() {
        return isModified;
    }

    /**
     * @return String array with info about collection
     */
    public String[] getInfo() {
        String type = "this is TreeSet of worker class instances";
        String init = "initialized " + initTime.toString();
        String size = "number of elements: " + storage.size();
        String state;
        if (isModified()) {
            state = "recently modified";
        } else {
            state = "all changes are saved";
        }
        return new String[]{type, init, size, state};
    }

    /**
     * @return maximal id of worker in collection
     */
    public int getMaxId() {
        int maxId;
        try {
            maxId = storage.first().getId();
        } catch (NoSuchElementException e) {
            return 0;
        }

        for (Worker w : storage) {
            if (w.getId() > maxId) {
                maxId = w.getId();
            }
        }
        return maxId;
    }

    /**
     * changes isModified state to false
     */
    public void hasBeenSaved() {
        isModified = false;
    }

    /**
     * @return copy of collection with workers
     */
    public TreeSet<Worker> getCollection() {
        return new TreeSet<>(storage);
    }

    /**
     * Loads external collection to TreeSet
     *
     * @param collection external collection of worker instances
     */
    public void load(Collection<Worker> collection) {
        storage.addAll(collection);
        isModified = true;
    }
}
