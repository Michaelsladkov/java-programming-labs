package command;

import data.IncorrectValueException;
import data.NullFieldException;
import data.Worker;
import util.InputInterruptedException;
import util.StorageManager;
import util.WorkerFactory;

/** AddIfMax command
 * Begins reading worker from console and add it to collection if it is less than minimal element in collection
 */
public class AddIfMin implements Command{
    private final StorageManager manager;
    private final WorkerFactory factory;
    /**
     * Constructor for this command
     * @param storageManager - receiver, collection manager
     * @param workerFactory - factory class for workers
     */
    AddIfMin(StorageManager storageManager, WorkerFactory workerFactory){
        manager = storageManager;
        factory = workerFactory;
    }

    @Override
    public void execute(String args) {
        try {
            Worker worker = factory.readWorkerFromConsole();
            if(worker.compareTo(manager.getMin())>0){
                manager.add(worker);
                System.out.println("Worker added");
            } else {
                System.out.println("Your worker isn't less than max from collection");
            }
        }
        catch (InputInterruptedException |NullFieldException|IncorrectValueException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String description() {
        return "This command allows you to enter your worker and save it to collection\n" +
                "Saving is only proceeds if entered worker is less than minimal from collection\n";
    }
}
