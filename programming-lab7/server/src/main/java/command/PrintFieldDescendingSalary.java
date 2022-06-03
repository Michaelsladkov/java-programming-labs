package command;

import data.Worker;
import util.StorageManager;
import util.network.Answer;

import java.util.Comparator;

public class PrintFieldDescendingSalary implements Command {
    private final StorageManager manager;

    PrintFieldDescendingSalary(StorageManager storageManager) {
        manager = storageManager;
    }

    @Override
    public Answer execute(String args) {
        Answer answer = new Answer();
        manager.getCollection().stream()
                .map(Worker::getSalary)
                .sorted(Comparator.reverseOrder())
                .forEach(l -> answer.add(l + ""));
        return answer;
    }

    @Override
    public String description() {
        return "Returns list of salaries in descending order\n";
    }
}
