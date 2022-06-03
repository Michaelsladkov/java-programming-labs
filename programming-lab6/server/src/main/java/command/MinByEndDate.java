package command;

import data.Worker;
import util.AnswerSender;
import util.StorageManager;
import utilL.EndDateComparator;

public class MinByEndDate implements Command {
    private final StorageManager manager;
    private final AnswerSender sender;

    MinByEndDate(StorageManager storageManager, AnswerSender answerSender) {
        manager = storageManager;
        sender = answerSender;
    }

    @Override
    public void execute(String args) {
        Worker first;
        first = manager.getCollection()
                .stream()
                .min(EndDateComparator::compares)
                .orElse(null);
        sender.prepareAnswer(first != null ? first : "no workers here");
    }

    @Override
    public String description() {
        return "Gives detailed description of worker with minimal value of endDate field\n";
    }
}