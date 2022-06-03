package command;

import data.Status;
import data.Worker;
import util.FieldsReader;
import util.StorageManager;

import java.util.NoSuchElementException;

public class RemoveAllByStatus implements Command{
    private final StorageManager manager;
    private final FieldsReader fieldsReader;
    RemoveAllByStatus(StorageManager storageManager, FieldsReader fieldsReader){
        manager = storageManager;
        this.fieldsReader=fieldsReader;
    }

    @Override
    public void execute(String args) {
        Status status;
        try {
            status = fieldsReader.readStatus();
        }
        catch (NoSuchElementException e){
            System.out.println("Input interrupted");
            return;
        }
        for(Worker worker:manager.getCollection()){
            if(worker.getStatus()==status){
                manager.remove(worker);
                System.out.println("id "+worker.getId()+" removed");
            }
        }
        if(!manager.isModified()){
            System.out.println("no elements with this status found");
        }
    }

    @Override
    public String description() {
        return "Removes all workers with given status\n";
    }
}