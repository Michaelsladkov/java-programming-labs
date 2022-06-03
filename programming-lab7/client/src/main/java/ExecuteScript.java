import network.CommandSender;
import network.CommandToSendFormer;
import workersOperations.WorkerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * ExecuteScript command
 * Takes text file and read commands from it
 */
public class ExecuteScript {
    private final HashSet<String> scriptFiles = new HashSet<>();
    private final WorkerFactory workerFactory;
    private final CommandToSendFormer former;
    private final CommandSender sender;
    private final Set<String> commands;

    public ExecuteScript(CommandToSendFormer ctsf, CommandSender send, WorkerFactory factory, Set<String> commands) {
        former = ctsf;
        sender = send;
        workerFactory = factory;
        this.commands = commands;
    }

    public void execute(String args) {
        FileReader scriptReader;
        try {
            scriptReader = new FileReader(args);
            if (!scriptFiles.add(args)) {
                System.out.println("forbidden recursion detected");
                return;
            }
        } catch (FileNotFoundException e) {
            System.out.println("No such file.");
            return;
        }
        Scanner previousWorkerFactoryScanner = workerFactory.getScanner();
        Scanner scriptScanner = new Scanner(scriptReader);
        workerFactory.setScanner(scriptScanner);
        CommandLineListener listener = new CommandLineListener(scriptScanner, former, sender, this);
        listener.startRead(commands);
        workerFactory.setScanner(previousWorkerFactoryScanner);
        scriptFiles.clear();
    }
}