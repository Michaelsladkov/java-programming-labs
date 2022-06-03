package command;

import data.Worker;
import util.AnswerSender;
import util.StorageManager;
import java.util.Collection;

public class Show implements Command {
    private final StorageManager storageManager;
    private final AnswerSender sender;

    Show(StorageManager manager,  AnswerSender answerSender) {
        storageManager = manager;
        sender = answerSender;
    }

    @Override
    public void execute(String args) {
        Collection<Worker> collection = storageManager.getCollection();
        if (collection.size() == 0) {
            sender.addToAnswer("Nothing to show");
        }
        for (Worker w : collection) {
            sender.addToAnswer(w);
        }
    }

    @Override
    public String description() {
        return "This command shows detailed description of each worker in collection\n";
    }
}
