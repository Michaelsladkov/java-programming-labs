package command;

import util.AnswerSender;
import util.StorageManager;

/**
 * Clear command
 * Remove all elements from collection
 */
public class Clear implements Command {
    private final StorageManager storage;
    private final AnswerSender sender;

    /**
     * Constructor for this command
     *
     * @param storage - receiver, collection manager
     */
    Clear(StorageManager storage, AnswerSender answerSender) {
        this.storage = storage;
        sender = answerSender;
    }

    @Override
    public void execute(String args) {
        storage.clear();
        sender.prepareAnswer("Collection cleared");
    }

    @Override
    public String description() {
        return "This command clears the collection\n";
    }
}