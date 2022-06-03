package command;

import util.database.DatabaseWorks;
import util.network.Answer;
import util.StorageManager;

/**
 * Clear command
 * Remove all elements from collection
 */
public class Clear implements Command, LoginNeededCommand {
    private final StorageManager storage;
    private final DatabaseWorks databaseWorks;
    private String login;

    /**
     * Constructor for this command
     *
     * @param storage - receiver, collection manager
     */
    Clear(StorageManager storage, DatabaseWorks databaseWorks) {
        this.databaseWorks = databaseWorks;
        this.storage = storage;
    }

    @Override
    public void putLogin(String login) {
        this.login = login;
    }

    @Override
    public Answer execute(String args) {
        Answer answer = new Answer();
        databaseWorks.clear(login);
        storage.getCollection().stream().
                filter(worker -> worker.getCreator().equals(login)).forEach(w->storage.remove(w));
        answer.add("Collection cleared");
        return answer;
    }

    @Override
    public String description() {
        return "This command clears the collection\n";
    }
}