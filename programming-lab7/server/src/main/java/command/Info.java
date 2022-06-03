package command;

import util.network.Answer;
import util.StorageManager;

public class Info implements Command {
    private final StorageManager storageManager;

    Info(StorageManager manager) {
        storageManager = manager;
    }

    @Override
    public Answer execute(String args) {
        Answer answer = new Answer();
        for (String line : storageManager.getInfo()) {
            answer.add(line);
        }
        return answer;
    }

    @Override
    public String description() {
        return "This command shows general information about the collection\n";
    }
}
