package command;

import data.Worker;
import util.database.DatabaseWorks;
import util.network.Answer;
import util.StorageManager;
import util.WorkerFactory;

/**
 * AddIfMax command
 * Begins reading worker from console and add it to collection if it is less than minimal element in collection
 */
public class AddIfMin implements Command {
    private final StorageManager manager;
    private final WorkerFactory factory;
    private final DatabaseWorks databaseWorks;

    /**
     * Constructor for this command
     *
     * @param storageManager - receiver, collection manager
     * @param workerFactory  - factory class for workers
     */
    AddIfMin(StorageManager storageManager, WorkerFactory workerFactory, DatabaseWorks databaseWorks) {
        manager = storageManager;
        factory = workerFactory;
        this.databaseWorks = databaseWorks;
    }

    @Override
    public Answer execute(String args) {
        Answer answer = new Answer();
        Worker worker = new Worker(factory.getWorkerFromLoad(), databaseWorks.generateId());
        if (worker.compareTo(manager.getMin()) > 0) {
            if (databaseWorks.insertWorker(worker)) {
                manager.add(worker);
                answer.add("Worker added");
            } else {
               answer.add("Failed to add worker to db");
            }

        } else {
            answer.add("Your worker isn't less than max from collection");
        }
        return answer;
    }

    @Override
    public String description() {
        return "This command allows you to enter your worker and save it to collection\n" +
                "Saving is only proceeds if entered worker is less than minimal from collection\n";
    }
}
