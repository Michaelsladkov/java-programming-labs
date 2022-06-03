package command;

import org.slf4j.Logger;
import util.FieldsReader;
import util.StorageManager;
import util.WorkerFactory;
import util.database.DatabaseWorks;
import util.network.Answer;
import util.network.AnswerSender;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Invoker {
    private final StorageManager manager;
    private final WorkerFactory factory;
    private final FieldsReader fieldsReader;
    private final AnswerSender sender;
    private final Logger logger;
    private final DatabaseWorks dbWorker;

    public Invoker(StorageManager storageManager,
                   WorkerFactory workerFactory,
                   FieldsReader fieldsReader,
                   AnswerSender answerSender,
                   Logger log,
                   DatabaseWorks databaseWorks) {
        manager = storageManager;
        factory = workerFactory;
        this.fieldsReader = fieldsReader;
        sender = answerSender;
        logger = log;
        dbWorker = databaseWorks;
    }

    Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()/3);

    private void initHashMap(HashMap<String, Command> commandHashMap) {
        commandHashMap.put("clear", new Clear(manager, dbWorker));
        commandHashMap.put("ping", new Ping());
        commandHashMap.put("add", new Add(manager, factory, dbWorker));
        commandHashMap.put("info", new Info(manager));
        commandHashMap.put("show", new Show(manager));
        commandHashMap.put("update", new Update(manager, factory, dbWorker));
        commandHashMap.put("remove_by_id", new RemoveById(manager, dbWorker));
        commandHashMap.put("add_if_max", new AddIfMax(manager, factory, dbWorker));
        commandHashMap.put("add_if_min", new AddIfMin(manager, factory, dbWorker));
        commandHashMap.put("remove_lower", new RemoveLower(manager, factory, dbWorker));
        commandHashMap.put("remove_all_by_status", new RemoveAllByStatus(manager, fieldsReader, dbWorker));
        commandHashMap.put("min_by_end_date", new MinByEndDate(manager));
        commandHashMap.put("print_field_descending_salary", new PrintFieldDescendingSalary(manager));
        commandHashMap.put("help", new Help(commandHashMap));
        commandHashMap.put("get_set_of_commands", new GetSetOfCommands(commandHashMap));
        commandHashMap.put("login", new Login(dbWorker));
        commandHashMap.put("register", new Register(dbWorker));
    }

    private class CommandExecutor implements Runnable {
        private final String commandName;
        private final String commandArgs;
        private final String userLogin;
        private final String userPassword;
        private final SocketAddress address;
        private final HashMap<String, Command> commandHashMap;

        private CommandExecutor(String commandName, String commandArgs, String userLogin, String userPassword,
                                SocketAddress address) {
            this.commandName = commandName;
            this.commandArgs = commandArgs;
            this.userLogin = userLogin;
            this.userPassword = userPassword;
            this.address = address;
            commandHashMap = new HashMap<>();
            initHashMap(commandHashMap);
        }

        @Override
        public void run() {
            if (commandHashMap.containsKey(commandName)) {
                if (dbWorker.checkUser(userLogin, userPassword) || commandName.equals("register") || commandName.equals("login")) {
                    if (commandHashMap.get(commandName) instanceof LoginNeededCommand) {
                        ((LoginNeededCommand) commandHashMap.get(commandName)).putLogin(userLogin);
                    }
                    if (commandHashMap.get(commandName) instanceof PasswordNeededCommand) {
                        ((PasswordNeededCommand) commandHashMap.get(commandName)).putPassword(userPassword);
                    }
                    logger.info(commandName + " command executed");
                    sender.sendAnswer(commandHashMap.get(commandName).execute(commandArgs), address);
                }
            } else {
                sender.sendAnswer(new Answer("Your input doesn't match any command"), address);
            }
        }
    }

    public void execute(String name, String args, String login, String password, SocketAddress address) {
        executor.execute(new CommandExecutor(name, args, login, password, address));
    }
}