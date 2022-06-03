package command;

import data.Worker;
import util.StorageManager;
import util.WorkerDecoder;

import java.util.NoSuchElementException;
import java.util.TreeSet;

public class MinByEndDate implements Command{
    private final StorageManager manager;
    private final WorkerDecoder decoder;
    MinByEndDate(StorageManager storageManager, WorkerDecoder workerDecoder){
        manager = storageManager;
        decoder = workerDecoder;
    }

    @Override
    public void execute(String args) {
        TreeSet<Worker> tmp = new TreeSet<>(manager.getCollection());
        Worker first;
        try {
            first = tmp.first();
        }catch (NoSuchElementException e) {
            System.out.println("collection is empty");
            return;
        }
        for (Worker worker:tmp) {
            if (worker.getEndDate().compareTo(first.getEndDate()) < 0) {
                first = worker;
            }
        }
        decoder.describe(first);
    }

    @Override
    public String description() {
        return "Gives detailed description of worker with minimal value of endDate field\n";
    }
}