package command;

import data.IncorrectValueException;
import data.NullFieldException;
import util.InputInterruptedException;
import util.StorageManager;
import util.WorkerFactory;

public class Update extends CommandNeedsId{
    private final StorageManager manager;
    private final WorkerFactory factory;
    Update(StorageManager storageManager, WorkerFactory workerFactory){
        manager=storageManager;
        factory=workerFactory;
    }

    @Override
    public void execute(String args) {
        Integer id = getId(args);
        if(id == null){
            return;
        }
        try {
            manager.remove(manager.getById(id));
        } catch (NullPointerException e){
            System.out.println("No worker with this id found");
            return;
        }
        factory.setStartId(id-1);
        try {
            manager.add(factory.readWorkerFromConsole());
            System.out.println("Worker updated");
        }
        catch (IncorrectValueException|NullFieldException| InputInterruptedException e){
            System.out.println(e.getMessage());
            return;
        }
        factory.setStartId(manager.getMaxId());
    }

    @Override
    public String description() {
        return "This command reads new worker and save it to collection with id given in argument\n";
    }
}