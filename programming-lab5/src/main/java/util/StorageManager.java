package util;

import data.Worker;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.TreeSet;

/**
 * This class is responsible for all operations with collection
 */
public class StorageManager {
    private final TreeSet<Worker> storage;
    private boolean isModified=false;
    private final ZonedDateTime initTime;

    public StorageManager(){
        storage=new TreeSet<>();
        initTime=ZonedDateTime.now();
    }

    /**
     * @return Returns maximal worker
     */
    public Worker getMax(){
        Worker max;
        try {
            max = storage.first();
        }catch (NoSuchElementException e) {
            return null;
        }
        for(Worker w : storage){
            if(max.compareTo(w)>0){
                max=w;
            }
        }
        return max;
    }

    /**
     * @return Returns minimal worker
     */
    public Worker getMin(){
        Worker min;
        try {
            min = storage.first();
        }catch (NoSuchElementException e) {
            return null;
        }
        for(Worker w : storage){
            if(min.compareTo(w)<0){
                min=w;
            }
        }
        return min;
    }

    /**
     * Adds new worker instance to collection
     * @param worker worker instance to be added
     * @return true if collection hasn't already contain this worker instance and addition is successful
     */
    public boolean add(Worker worker){
        isModified=true;
        if(worker == null){
            return false;
        }
        return storage.add(worker);
    }

    /**
     * @param id id of required worker instance
     * @return worker instance with required id
     */
    public Worker getById(int id){
        for(Worker w : storage){
           if(w.getId()==id){
               return w;
           }
        }
        return null;
    }

    /**
     * @param worker worker class instance to be removed
     * @return true if removing is successful
     */
    public boolean remove(Worker worker){
        isModified=true;
        return storage.remove(worker);
    }

    /**
     * Removes all elements from collection
     */
    public void clear()
    {
        isModified=true;
        storage.clear();
    }

    /**
     * @return true if collection have unsaved changes
     */
    public boolean isModified(){
        return isModified;
    }

    /**
     * @return String array with info about collection
     */
    public String[] getInfo(){
        String type = "this is TreeSet of worker class instances";
        String init = "initialized "+initTime.toString();
        String size = "number of elements: "+storage.size();
        String state;
        if(isModified()){
            state="recently modified";
        }
        else{
            state="all changes are saved";
        }
        return new String[]{type,init,size,state};
    }

    /**
     * @return maximal id of worker in collection
     */
    public int getMaxId(){
        int maxId;
        try {
            maxId=storage.first().getId();
        }catch (NoSuchElementException e){
            return 0;
        }

        for(Worker w:storage){
            if(w.getId()>maxId){
                maxId=w.getId();
            }
        }
        return maxId;
    }

    /**
     * changes isModified state to false
     */
    public void hasBeenSaved(){
        isModified=false;
    }

    /**
     * @return copy of collection with workers
     */
    public TreeSet<Worker> getCollection(){
        return new TreeSet<>(storage);
    }

    /**
     * Loads external collection to TreeSet
     * @param collection external collection of worker instances
     */
    public void load(Collection<Worker> collection){
        storage.addAll(collection);
        isModified=true;
    }
}
