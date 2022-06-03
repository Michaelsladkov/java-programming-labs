package command;

import data.Worker;
import util.AnswerSender;
import util.StorageManager;

import java.util.Comparator;

public class PrintFieldDescendingSalary implements Command {
    private final StorageManager manager;
    private final AnswerSender sender;

    PrintFieldDescendingSalary(StorageManager storageManager, AnswerSender answerSender) {
        manager = storageManager;
        sender = answerSender;
    }

    @Override
    public void execute(String args) {
        manager.getCollection().stream()
                .map(Worker::getSalary)
                .sorted(Comparator.reverseOrder())
                .forEach(s -> sender.addToAnswer(s + ""));
    }

    @Override
    public String description() {
        return "Returns list of salaries in descending order\n";
    }
}
