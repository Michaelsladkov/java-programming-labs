package command;

import data.Worker;
import util.StorageManager;
import util.network.Answer;

import java.util.Collection;

public class Show implements Command {
    private final StorageManager storageManager;

    Show(StorageManager manager) {
        storageManager = manager;
    }

    @Override
    public Answer execute(String args) {
        Answer answer = new Answer();
        Collection<Worker> collection = storageManager.getCollection();
        if (collection.size() == 0) {
            answer.add("Nothing to show");
        }
        for (Worker w : collection) {
            answer.add(w);
        }
        return answer;
    }

    @Override
    public String description() {
        return "This command shows detailed description of each worker in collection\n";
    }
}
