package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
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
            File script = new File(args);

            if (!script.getAbsoluteFile().exists()) {
                System.out.println("This script file not exist");
                return;
            }
            if (!script.canRead()) {
                System.out.println("This script file can't be read");
                return;
            }
            if(!script.isFile()){
                System.out.println("This is not script file");
                return;
            }
            System.out.println(script.getAbsolutePath());
            scriptReader = new FileReader(script);
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
        workerFactory.setScriptRunning(true);
        CommandLineListener listener = new CommandLineListener(scriptScanner, former, sender, this);
        listener.startRead(commands);
        workerFactory.setScanner(previousWorkerFactoryScanner);
        scriptFiles.clear();
        workerFactory.setScriptRunning(false);
    }
}