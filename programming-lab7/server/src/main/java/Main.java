import ch.qos.logback.core.rolling.RollingFileAppender;
import command.Invoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.*;
import util.database.DatabaseConnector;
import util.database.DatabaseInitializer;
import util.database.DatabaseWorks;
import util.network.AnswerSender;
import util.network.Listener;
import util.network.RequestManager;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(RollingFileAppender.class);
        StorageManager manager = StorageManager.getInstance();
        Scanner scanner = new Scanner(System.in);
        FieldsReader fieldsReader = new FieldsReader(scanner);
        WorkerFactory workerFactory = new WorkerFactory(0, fieldsReader);
        workerFactory.setScanner(scanner);
        DatagramSocket datagramSocket;
        int port = 3131;
        try {
            if (args.length != 0) {
                port = Integer.parseInt(args[0]);
            }
        } catch (NumberFormatException e) {
            System.out.println("incorrect format");
        }
        if (port == 3131) {
            System.out.println("Correct port wasn't picked, " + port + " will be used");
        }
        try {
            datagramSocket = new DatagramSocket(port);
            datagramSocket.setSoTimeout(50);
        } catch (SocketException e) {
            System.out.println("failed to create socket");
            e.printStackTrace();
            return;
        }
        Connection db;
        try {
            db = DatabaseConnector.connect();
        } catch (SQLException e) {
            System.out.println("Connection establishing problems");
            e.printStackTrace();
            return;
        }
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(db);
        try {
            databaseInitializer.checkOrInitTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseWorks databaseWorks;
        try {
            databaseWorks = new DatabaseWorks(db);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Hashing algorithm  not found");
            e.printStackTrace();
            return;
        }
        AnswerSender answerSender;
        answerSender = new AnswerSender(datagramSocket, logger);
        Invoker invoker = new Invoker(manager, workerFactory, fieldsReader, answerSender, logger, databaseWorks);
        RequestManager requestManager = new RequestManager(answerSender, workerFactory, invoker, logger);
        Listener listener = new Listener(datagramSocket, requestManager, logger);
        listener.start();
        databaseWorks.updateAll();
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
            System.out.println("This command is forbidden for server");
        }
        listener.interrupt();
    }
}