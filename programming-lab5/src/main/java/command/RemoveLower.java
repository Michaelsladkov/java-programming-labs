package command;

import data.IncorrectValueException;
import data.NullFieldException;
import data.Worker;
import util.InputInterruptedException;
import util.StorageManager;
import util.WorkerFactory;

public class RemoveLower implements Command{
    private final StorageManager manager;
    private final WorkerFactory factory;
    RemoveLower(StorageManager storageManager, WorkerFactory workerFactory){
        manager = storageManager;
        factory = workerFactory;
    }

    @Override
    public void execute(String args) {
        try {
            Worker worker = factory.readWorkerFromConsole();
            for(Worker collected:manager.getCollection()){
                if(worker.compareTo(collected)<0){
                    manager.remove(collected);
                }
            }
            System.out.println("All lower elements have been removed");
        }
        catch (IncorrectValueException| NullFieldException| InputInterruptedException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String description() {
        return "This command takes worker from console and remove all smaller workers from collection\n";
    }
}
