import data.CommandToSend;
import data.TwoSetsContainer;
import util.CommandLineListener;
import util.CommandSender;
import util.CommandToSendFormer;
import util.ExecuteScript;
import util.FieldsReader;
import util.Receiver;
import util.WorkerFactory;
import utilL.WorkerDecoder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * This is main class of my lab application
 * All instances are being created here
 */


public class Main {
    public static void main(String[] args) throws InterruptedException{
        Scanner scanner = new Scanner(System.in);
        FieldsReader fieldsReader = new FieldsReader(scanner);
        WorkerFactory workerFactory = new WorkerFactory(0, fieldsReader);
        workerFactory.setScanner(scanner);
        WorkerDecoder decoder = new WorkerDecoder();
        InetAddress serverAddress;
        InetAddress clientAddress;
        int serverPort = 3131;
        try {
            serverAddress = InetAddress.getLocalHost();
            clientAddress = InetAddress.getLocalHost();
            if (args.length != 0 && args[0].contains(":")) {
                serverAddress = InetAddress.getByName(args[0].split(":")[0]);
                serverPort = Integer.parseInt(args[0].split(":")[1]);
            } else {
                System.out.println("Server IP wasn't found. Default 'localhost:3131' will be used");
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return;
        }
        SocketAddress serverSocketAddress = new InetSocketAddress(serverAddress, serverPort);
        DatagramChannel datagramChannel;
        try {
            datagramChannel = DatagramChannel.open();
            datagramChannel.bind(new InetSocketAddress(clientAddress, 0));
            System.out.println(datagramChannel.getLocalAddress());
            datagramChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Receiver receiver = new Receiver(datagramChannel, decoder);
        CommandSender sender;
        sender = new CommandSender(datagramChannel, serverSocketAddress);
        CommandToSend commandSetsRequest = new CommandToSend("get_set_of_commands", null, null);
        sender.sendCommand(commandSetsRequest);
        receiver.start();
        TimeUnit.MILLISECONDS.sleep(100);
        TwoSetsContainer cont;
        while (receiver.getCommands() == null){
            System.out.println("Type \"e\" to exit or \"t\" to try again");
            String line = scanner.nextLine();
            if(line.equals("e")){
                System.exit(0);
            }
            if(line.equals("t")){
                sender.sendCommand(commandSetsRequest);
            }
            TimeUnit.MILLISECONDS.sleep(100);
        }
        cont = receiver.getCommands();
        CommandToSendFormer former = new CommandToSendFormer(workerFactory, cont.getWorkerNeedCommands());
        ExecuteScript executor = new ExecuteScript(former, sender, workerFactory, cont.getAvailableCommands());
        CommandLineListener listener = new CommandLineListener(scanner, former, sender, executor);
        System.out.println("Ready.");
        listener.startRead(cont.getAvailableCommands());
        try {
            receiver.stopReceiver();
        } catch (IOException e) {
            e.printStackTrace();
        }
        receiver.interrupt();
    }
}