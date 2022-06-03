package command;

import data.Worker;
import util.database.DatabaseWorks;
import util.network.Answer;
import util.StorageManager;
import util.WorkerFactory;

/**
 * Add command
 * Begins reading worker from console and add it to collection
 */
public class Add implements Command {
    private final WorkerFactory factory;
    private final StorageManager manager;
    private final DatabaseWorks databaseWorks;
    /**
     * Command constructor
     *
     * @param manager - receiver, collection manager
     * @param factory - factory class for workers
     */
    Add(StorageManager manager, WorkerFactory factory, DatabaseWorks databaseWorks) {
        this.factory = factory;
        this.manager = manager;
        this.databaseWorks = databaseWorks;
    }

    @Override
    public Answer execute(String args) {
        Answer answer = new Answer();
        Worker toAdd = new Worker(factory.getWorkerFromLoad(), databaseWorks.generateId());

        if (databaseWorks.insertWorker(toAdd)) {
            manager.add(toAdd);
            answer.add("Worker created");
        } else {
            answer.add("This element probably duplicates existing one and can't be added");
        }
        return answer;
    }

    @Override
    public String description() {
        return "This command allows you to enter new worker and write it to collection\n" +
                "There are input messages which will help you to enter all the data\n";
    }
}
