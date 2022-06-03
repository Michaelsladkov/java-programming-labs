package command;

import util.*;

import java.util.HashMap;


public class Invoker {
    private final HashMap<String, Command> commandHashMap;
    private final StorageManager manager;
    private final WorkerFactory factory;
    private final WorkerDecoder decoder;
    private final FileWorks fileWorks;
    private final FieldsReader fieldsReader;
    private boolean isStopRequested=false;
    private final Class[] allowedToStop = {Exit.class};
    public Invoker(StorageManager storageManager, WorkerFactory workerFactory, WorkerDecoder workerDecoder, FileWorks fileWorks, FieldsReader fieldsReader){
        commandHashMap=new HashMap<>();
        manager = storageManager;
        factory = workerFactory;
        decoder = workerDecoder;
        this.fileWorks = fileWorks;
        this.fieldsReader = fieldsReader;
        initHashMap();
    }

    public void initHashMap(){
        commandHashMap.put("clear", new Clear(manager));
        commandHashMap.put("ping", new Ping());
        commandHashMap.put("add", new Add(manager,factory));
        commandHashMap.put("info", new Info(manager));
        commandHashMap.put("show", new Show(manager,decoder));
        commandHashMap.put("exit", new Exit(this));
        commandHashMap.put("save", new Save(fileWorks, manager));
        commandHashMap.put("update", new Update(manager,factory));
        commandHashMap.put("remove_by_id", new RemoveById(manager));
        commandHashMap.put("add_if_max", new AddIfMax(manager,factory));
        commandHashMap.put("add_if_min", new AddIfMin(manager, factory));
        commandHashMap.put("remove_lower", new RemoveLower(manager, factory));
        commandHashMap.put("remove_all_by_status", new RemoveAllByStatus(manager, fieldsReader));
        commandHashMap.put("min_by_end_date", new MinByEndDate(manager,decoder));
        commandHashMap.put("print_field_descending_salary", new PrintFieldDescendingSalary(manager));
        commandHashMap.put("execute_script", new ExecuteScript(this, factory));
        commandHashMap.put("help", new Help(this.commandHashMap));
    }

    public void execute(String name, String args){
        if(commandHashMap.containsKey(name)) {
            commandHashMap.get(name).execute(args);
        }
        else{
            System.out.println("Your input doesn't match any command");
        }
    }

    public void requestExit(Object requester){
        for(Class caller:allowedToStop){
            if(caller.equals(requester.getClass())){
                isStopRequested = true;
                break;
            }
        }
    }

    public boolean isStopRequested(){
        return isStopRequested;
    }
}