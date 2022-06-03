import data.CommandToSend;
import data.TwoSetsContainer;
import network.CommandSender;
import network.CommandToSendFormer;
import network.Receiver;
import network.Session;
import utilL.WorkerDecoder;
import workersOperations.FieldsReader;
import workersOperations.WorkerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
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
        receiver.start();
        AuthorizationManager.authorizeUser(scanner, sender, receiver);
        CommandToSend commandSetsRequest = new CommandToSend("get_set_of_commands", null, null,
                Session.getInstance().getUserName(),
                Session.getInstance().getPassword());
        sender.sendCommand(commandSetsRequest);
        TwoSetsContainer cont;
        while (receiver.getCommands() == null) ;
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