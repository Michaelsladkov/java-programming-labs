import ch.qos.logback.core.rolling.RollingFileAppender;
import command.Invoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.*;
import java.util.Scanner;

//не сохранять в неуказанный файл
// gsoc не работает при вызове из строки
//show работает при убитом сервере
public class Main {
    private static Invoker invoker;

    static {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                invoker.execute("save", null);
            }
        });
    }

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(RollingFileAppender.class);
        StorageManager manager = new StorageManager();
        Scanner scanner = new Scanner(System.in);
        FieldsReader fieldsReader = new FieldsReader(scanner);
        WorkerFactory workerFactory = new WorkerFactory(0, fieldsReader);
        workerFactory.setScanner(scanner);
        DatagramSocket datagramSocket;
        int port = 8000;
        try {
            if (args.length > 1) {
                port = Integer.parseInt(args[1]);
            } else if (args.length != 0) {
                port = Integer.parseInt(args[0]);
            }
        } catch (NumberFormatException e) {
            System.out.println("incorrect format");
        }
        if (port == 3131) {
            System.out.println("Correct port wasn't picked, " + port + " will be used");
        }
        try {
            datagramSocket = new DatagramSocket(null);
            datagramSocket.setSoTimeout(50);
            datagramSocket.bind(new InetSocketAddress(InetAddress.getByName("192.168.1.230"), 8000));
        } catch (SocketException|UnknownHostException e) {
            System.out.println("failed to create socket");
            e.printStackTrace();
            return;
        }
        AnswerSender answerSender;
        answerSender = new AnswerSender(datagramSocket, logger);
        WorkerDecoder decoder = new WorkerDecoder();
        FileWorks fileWorks = new FileWorks(decoder, manager, workerFactory);
        invoker = new Invoker(manager, workerFactory, fileWorks, fieldsReader, answerSender, logger);
        RequestManager requestManager = new RequestManager(answerSender, workerFactory, invoker, logger);
        Listener listener = new Listener(datagramSocket, requestManager, logger);
        if (args.length != 0) {
            try {
                FileReader reader = new FileReader(args[0]);
                fileWorks.setOutputFile(args[0]);
                manager.load(fileWorks.readCollection(reader));
                workerFactory.setStartId(manager.getMaxId());
            } catch (FileNotFoundException e) {
                System.out.println("No such file.");
            }
        }
        listener.start();
        try {
            logger.info("Server started on address " + InetAddress.getLocalHost() + "port: " + port);
            System.out.println("Server started on address " + InetAddress.getLocalHost() + ":" + port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            if (line.equals("exit")) {
                break;
            }
            if (line.equals("save")) {
                invoker.execute("save", null);
                continue;
            }
            System.out.println("This command is forbidden for server");
        }
        listener.interrupt();
    }
}
