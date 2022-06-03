package workersOperations;

import com.sun.javafx.collections.ObservableListWrapper;
import data.Worker;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class Storage {
    private static Storage instance = null;
    private volatile ObservableList<Worker> storage;
    private volatile ObservableList<Worker> observableStorage;
    private final HashMap<Worker, Worker> differedElements;
    private final List<Worker> newElements;
    private final List<Worker> removedElements;
    private boolean changed = false;


    private Storage() {
        storage = new ObservableListWrapper<>(new ArrayList<>());
        observableStorage = new ObservableListWrapper<>(new ArrayList<>());
        differedElements = new HashMap<>();
        newElements = new ArrayList<>();
        removedElements = new ArrayList<>();
    }

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    public synchronized void put(List<Worker> collection) {
        storage = new ObservableListWrapper<>(collection);
    }

    public ObservableList<Worker> get() {
        changed = false;
        return observableStorage;
    }

    public HashMap<Worker, Worker> getDifferedElements() {
        return differedElements;
    }

    public List<Worker> getRemovedElements() {
        return removedElements;
    }

    public List<Worker> getNewElements() {
        return newElements;
    }

    public boolean isChanged() {
        return changed;
    }

    public synchronized void applyFiltration(Filter filter) {
        ObservableList<Worker> collection = filter.filter(storage);
        newElements.clear();
        differedElements.clear();
        for (Worker w : collection) {
            Optional<Worker> optionalWorker = observableStorage.stream().filter(e -> e.getId() == w.getId()).findFirst();
            if (!optionalWorker.isPresent()) {
                newElements.add(w);
                continue;
            }
            Worker worker = optionalWorker.get();
            if (!worker.equals(w)) {
                differedElements.put(optionalWorker.get(), w);
            }
        }
        removedElements.clear();
        for (Worker w : observableStorage) {
            if (collection.stream().noneMatch(worker -> worker.getId() == w.getId())) {
                removedElements.add(w);
            }
        }
        changed = !(newElements.isEmpty() && differedElements.isEmpty() && removedElements.isEmpty());
        observableStorage = collection;
    }
}
