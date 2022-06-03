package command;

import data.Worker;
import util.StorageManager;
import util.network.Answer;
import utilL.EndDateComparator;

public class MinByEndDate implements Command {
    private final StorageManager manager;

    MinByEndDate(StorageManager storageManager) {
        manager = storageManager;
    }

    @Override
    public Answer execute(String args) {
        Answer answer = new Answer();
        Worker first;
        first = manager.getCollection()
                .stream()
                .min(EndDateComparator::compares)
                .orElse(null);
        if (first != null) {
            answer.add(first);
        } else {
            answer.add("no workers here");
        }
        return answer;
    }

    @Override
    public String description() {
        return "Gives detailed description of worker with minimal value of endDate field\n";
    }
}