
import command.Invoker;
import util.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * This is main class of my lab application
 * All instances are being created here
 */
public class Main {
    /**
     * Main method which is started whe you run the app
     * Creates all instances and starts listening loop of Command Line Listener
     * @param args - command line arguments, can contain the path to .csv file with saved objects
     * @see CommandLineListener
     */
    public static void main(String[] args){
        WorkerDecoder decoder = new WorkerDecoder();
        StorageManager manager = new StorageManager();
        Scanner scanner = new Scanner(System.in);
        FieldsReader fieldsReader = new FieldsReader(scanner);
        WorkerFactory workerFactory = new WorkerFactory(0, fieldsReader);
        workerFactory.setScanner(scanner);
        FileWorks fileWorks = new FileWorks(decoder, manager, workerFactory);
        Invoker invoker = new Invoker(manager, workerFactory, decoder, fileWorks, fieldsReader);
        CommandLineListener listener = new CommandLineListener(scanner, invoker);
        if(args.length != 0){
            try {
                FileReader reader=new FileReader(args[0]);
                fileWorks.setOutputFile(args[0]);
                manager.load(fileWorks.readCollection(reader));
                workerFactory.setStartId(manager.getMaxId());
            }
            catch (FileNotFoundException e){
                System.out.println("No such file.");
            }
        }
        listener.startRead();
    }
}
