package command;

import util.StorageManager;

public class RemoveById extends CommandNeedsId{
    private final StorageManager manager;
    RemoveById(StorageManager storageManager){
        manager=storageManager;
    }

    @Override
    public void execute(String args) {
        Integer id = getId(args);
        if(id == null){
            return;
        }
        try {
            if(manager.remove(manager.getById(id))){
                System.out.println("Element removed");
            }
        } catch (NullPointerException e){
            System.out.println("No worker with this id found");
        }
    }

    @Override
    public String description() {
        return "This command gets worker id and remove worker with that id from collection\n";
    }
}