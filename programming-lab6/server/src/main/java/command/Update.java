package command;

import util.AnswerSender;
import util.StorageManager;
import util.WorkerFactory;

public class Update extends CommandNeedsId {
    private final StorageManager manager;
    private final WorkerFactory factory;
    private final AnswerSender sender;

    Update(StorageManager storageManager, WorkerFactory workerFactory, AnswerSender answerSender) {
        manager = storageManager;
        factory = workerFactory;
        sender = answerSender;
    }

    @Override
    public void execute(String args) {
        Integer id = getId(args, sender);
        if (id == null) {
            return;
        }
        try {
            manager.remove(manager.getById(id));
        } catch (NullPointerException e) {
            sender.addToAnswer("No worker with this id found");
            return;
        }
        factory.setStartId(id - 1);
        manager.add(factory.getWorkerFromLoad());
        sender.addToAnswer("Worker updated");
        factory.setStartId(manager.getMaxId());
    }

    @Override
    public String description() {
        return "This command reads new worker and save it to collection with id given in argument\n";
    }
}