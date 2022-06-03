package command;

import data.Status;
import util.database.DatabaseWorks;
import util.FieldsReader;
import util.StorageManager;
import util.network.Answer;

import java.util.NoSuchElementException;

public class RemoveAllByStatus implements Command, LoginNeededCommand {
    private final StorageManager manager;
    private final FieldsReader fieldsReader;
    private final DatabaseWorks databaseWorks;
    private String login;

    RemoveAllByStatus(StorageManager storageManager, FieldsReader fieldsReader,
                      DatabaseWorks databaseWorks) {
        manager = storageManager;
        this.fieldsReader = fieldsReader;
        this.databaseWorks = databaseWorks;
    }

    @Override
    public Answer execute(String args) {
        Answer answer = new Answer();
        Status status;
        try {
            status = fieldsReader.readStatus(args);
        } catch (NoSuchElementException e) {
            answer.add("Input interrupted");
            return answer;
        }
        if (status == null) {
            answer.add("Your input doesn't match any status");
            return answer;
        }
        int result = databaseWorks.removeAllByStatus(status.toString(), login);
        if (result <= 0) {
            answer.add("No elements with such status found");
            return answer;
        }
        manager.getCollection().stream().
                filter(w -> w.getStatus().equals(status) && w.getCreator().equals(login)).
                forEach(manager::remove);
        answer.add(result+" Elements removed");
        return answer;
    }

    @Override
    public void putLogin(String login) {
        this.login = login;
    }

    @Override
    public String description() {
        return "Removes all workers with given status\n";
    }
}