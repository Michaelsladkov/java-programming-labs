package command;

import util.database.DatabaseWorks;
import util.StorageManager;
import util.network.Answer;

public class RemoveById extends CommandNeedsId implements LoginNeededCommand {
    private final StorageManager manager;
    private final DatabaseWorks databaseWorks;
    private String login;

    RemoveById(StorageManager storageManager,  DatabaseWorks databaseWorks) {
        manager = storageManager;
        this.databaseWorks = databaseWorks;
    }

    @Override
    public void putLogin(String login) {
        this.login = login;
    }

    @Override
    public Answer execute(String args) {
        Answer answer = new Answer();
        Integer id;
        try {
            id = getId(args);
        }
        catch (NumberFormatException e) {
            answer.add("Incorrect id format");
            return answer;
        }
        if (id == null) {
            answer.add("You need to put an id");
            return answer;
        }
        String result = databaseWorks.deleteWorker(id, login);
        if (result.equals("successfully removed")) {
            try {
                manager.remove(manager.getById(id));
            } catch (NullPointerException e) {
                answer.add("No worker with this id found");
            }
        }
        answer.add(result);
        return answer;
    }

    @Override
    public String description() {
        return "This command gets worker id and remove worker with that id from collection\n";
    }
}