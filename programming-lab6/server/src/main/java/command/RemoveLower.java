package command;

import data.Worker;
import util.AnswerSender;
import util.StorageManager;
import util.WorkerFactory;

public class RemoveLower implements Command {
    private final StorageManager manager;
    private final WorkerFactory factory;
    private final AnswerSender sender;

    RemoveLower(StorageManager storageManager, WorkerFactory workerFactory, AnswerSender answerSender) {
        manager = storageManager;
        factory = workerFactory;
        sender = answerSender;
    }

    @Override
    public void execute(String args) {
        Worker worker = factory.getWorkerFromLoad();
        for (Worker collected : manager.getCollection()) {
            if (worker.compareTo(collected) < 0) {
                manager.remove(collected);
            }
        }
        sender.addToAnswer("All lower elements have been removed");
    }

    @Override
    public String description() {
        return "This command takes worker from console and remove all smaller workers from collection\n";
    }
}
