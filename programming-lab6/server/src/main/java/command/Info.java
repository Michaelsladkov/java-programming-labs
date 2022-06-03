package command;

import util.AnswerSender;
import util.StorageManager;

public class Info implements Command {
    private final StorageManager storageManager;
    private final AnswerSender sender;

    Info(StorageManager manager, AnswerSender answerSender) {
        sender = answerSender;
        storageManager = manager;
    }

    @Override
    public void execute(String args) {
        for (String line : storageManager.getInfo()) {
            sender.addToAnswer(line);
        }
    }

    @Override
    public String description() {
        return "This command shows general information about the collection\n";
    }
}
