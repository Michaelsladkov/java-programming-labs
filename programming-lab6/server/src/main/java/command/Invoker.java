package command;

import org.slf4j.Logger;
import util.*;

import java.util.HashMap;


public class Invoker {
    private final HashMap<String, Command> commandHashMap;
    private final StorageManager manager;
    private final WorkerFactory factory;
    private final FileWorks fileWorks;
    private final FieldsReader fieldsReader;
    private final AnswerSender sender;
    private final Logger logger;

    public Invoker(StorageManager storageManager,
                   WorkerFactory workerFactory,
                   FileWorks fileWorks,
                   FieldsReader fieldsReader,
                   AnswerSender answerSender,
                   Logger log) {
        commandHashMap = new HashMap<>();
        manager = storageManager;
        factory = workerFactory;
        this.fileWorks = fileWorks;
        this.fieldsReader = fieldsReader;
        sender = answerSender;
        logger = log;
        initHashMap();
    }

    public void initHashMap() {
        commandHashMap.put("clear", new Clear(manager, sender));
        commandHashMap.put("ping", new Ping(sender));
        commandHashMap.put("add", new Add(manager, factory, sender));
        commandHashMap.put("info", new Info(manager, sender));
        commandHashMap.put("show", new Show(manager, sender));
        commandHashMap.put("save", new Save(fileWorks, manager));
        commandHashMap.put("update", new Update(manager, factory, sender));
        commandHashMap.put("remove_by_id", new RemoveById(manager, sender));
        commandHashMap.put("add_if_max", new AddIfMax(manager, factory, sender));
        commandHashMap.put("add_if_min", new AddIfMin(manager, factory, sender));
        commandHashMap.put("remove_lower", new RemoveLower(manager, factory, sender));
        commandHashMap.put("remove_all_by_status", new RemoveAllByStatus(manager, fieldsReader, sender));
        commandHashMap.put("min_by_end_date", new MinByEndDate(manager, sender));
        commandHashMap.put("print_field_descending_salary", new PrintFieldDescendingSalary(manager, sender));
        //commandHashMap.put("execute_script", new ExecuteScript(this, factory, sender));
        commandHashMap.put("help", new Help(this.commandHashMap, sender));
        commandHashMap.put("get_set_of_commands", new GetSetOfCommands(this.commandHashMap, sender));
    }

    public void execute(String name, String args) {
        if (commandHashMap.containsKey(name)) {
            logger.info(name + " command executed");
            commandHashMap.get(name).execute(args);
        } else {
            sender.addToAnswer("Your input doesn't match any command");
        }
        if (!name.equals("save")) {
            sender.sendAnswer();
        }
    }
}