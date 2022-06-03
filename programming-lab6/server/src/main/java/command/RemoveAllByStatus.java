package command;

import data.Status;
import util.AnswerSender;
import util.FieldsReader;
import util.StorageManager;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class RemoveAllByStatus implements Command {
    private final StorageManager manager;
    private final FieldsReader fieldsReader;
    private final AnswerSender sender;

    RemoveAllByStatus(StorageManager storageManager, FieldsReader fieldsReader, AnswerSender answerSender) {
        manager = storageManager;
        sender = answerSender;
        this.fieldsReader = fieldsReader;
    }

    @Override
    public void execute(String args) {
        Status status;
        try {
            status = fieldsReader.readStatus(args);
        } catch (NoSuchElementException e) {
            sender.addToAnswer("Input interrupted");
            return;
        }
        if (status == null) {
            sender.prepareAnswer("Your input doesn't match any status");
            return;
        }
        if (!manager.remove(manager.getCollection().
                stream().
                filter(worker -> worker.getStatus().equals(status)).
                collect(Collectors.toList()))) {
            sender.addToAnswer("no elements with this status found");
        }
    }

    @Override
    public String description() {
        return "Removes all workers with given status\n";
    }
}