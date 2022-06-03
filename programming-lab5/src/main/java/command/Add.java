package command;

import data.IncorrectValueException;
import data.NullFieldException;
import util.InputInterruptedException;
import util.StorageManager;
import util.WorkerFactory;

/** Add command
 * Begins reading worker from console and add it to collection
 */
public class Add implements Command{
    private final WorkerFactory factory;
    private final StorageManager manager;

    /**
     * Command constructor
     * @param manager - receiver, collection manager
     * @param factory - factory class for workers
     */
    Add(StorageManager manager, WorkerFactory factory){
        this.factory = factory;
        this.manager = manager;
    }

    @Override
    public void execute(String args){
        try {
            if(manager.add(factory.readWorkerFromConsole())) {
                System.out.println("Worker created");
            }
            else {
                System.out.println("This element probably duplicates existing one and can't be added");
            }
        }
        catch (IncorrectValueException|NullFieldException|InputInterruptedException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String description() {
        return "This command allows you to enter new worker and write it to collection\n" +
                "There are input messages which will help you to enter all the data\n";
    }
}
