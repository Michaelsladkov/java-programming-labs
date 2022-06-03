package command;

import util.AnswerSender;
import util.StorageManager;
import util.WorkerFactory;

/**
 * Add command
 * Begins reading worker from console and add it to collection
 */
public class Add implements Command {
    private final WorkerFactory factory;
    private final StorageManager manager;
    private final AnswerSender sender;

    /**
     * Command constructor
     *
     * @param manager - receiver, collection manager
     * @param factory - factory class for workers
     */
    Add(StorageManager manager, WorkerFactory factory, AnswerSender answerSender) {
        this.factory = factory;
        this.manager = manager;
        sender = answerSender;
    }

    @Override
    public void execute(String args) {
        if (manager.add(factory.getWorkerFromLoad())) {
            sender.prepareAnswer("Worker created");
        } else {
            sender.prepareAnswer("This element probably duplicates existing one and can't be added");
        }
    }

    @Override
    public String description() {
        return "This command allows you to enter new worker and write it to collection\n" +
                "There are input messages which will help you to enter all the data\n";
    }
}
