package command;

import data.Worker;
import util.AnswerSender;
import util.StorageManager;
import util.WorkerFactory;

/**
 * AddIfMax command
 * Begins reading worker from console and add it to collection if it is less than minimal element in collection
 */
public class AddIfMin implements Command {
    private final StorageManager manager;
    private final WorkerFactory factory;
    private final AnswerSender sender;

    /**
     * Constructor for this command
     *
     * @param storageManager - receiver, collection manager
     * @param workerFactory  - factory class for workers
     */
    AddIfMin(StorageManager storageManager, WorkerFactory workerFactory, AnswerSender answerSender) {
        manager = storageManager;
        factory = workerFactory;
        sender = answerSender;
    }

    @Override
    public void execute(String args) {
        Worker worker = factory.getWorkerFromLoad();
        if (worker.compareTo(manager.getMin()) > 0) {
            manager.add(worker);
            sender.prepareAnswer("Worker added");
        } else {
            sender.prepareAnswer("Your worker isn't less than max from collection");
        }
    }

    @Override
    public String description() {
        return "This command allows you to enter your worker and save it to collection\n" +
                "Saving is only proceeds if entered worker is less than minimal from collection\n";
    }
}
