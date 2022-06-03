package command;

import data.Worker;
import util.StorageManager;
import util.WorkerDecoder;

import java.util.Collection;

public class Show implements Command {
    private final StorageManager storageManager;
    private final WorkerDecoder workerDecoder;
    Show(StorageManager manager, WorkerDecoder decoder){
        storageManager=manager;
        workerDecoder=decoder;
    }

    @Override
    public void execute(String args) {
        Collection<Worker> collection=storageManager.getCollection();
        if(collection.size()==0){
            System.out.println("Nothing to show");
        }
        for(Worker w:collection){
            workerDecoder.describe(w);
        }
    }

    @Override
    public String description() {
        return "This command shows detailed description of each worker in collection\n";
    }
}
