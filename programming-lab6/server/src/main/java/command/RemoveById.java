package command;

import util.AnswerSender;
import util.StorageManager;

public class RemoveById extends CommandNeedsId {
    private final StorageManager manager;
    private final AnswerSender sender;

    RemoveById(StorageManager storageManager, AnswerSender answerSender) {
        sender = answerSender;
        manager = storageManager;
    }

    @Override
    public void execute(String args) {
        Integer id = getId(args, sender);
        if (id == null) {
            return;
        }
        try {
            if (manager.remove(manager.getById(id))) {
                sender.addToAnswer("Element removed");
            }
        } catch (NullPointerException e) {
            sender.addToAnswer("No worker with this id found");
        }
    }

    @Override
    public String description() {
        return "This command gets worker id and remove worker with that id from collection\n";
    }
}