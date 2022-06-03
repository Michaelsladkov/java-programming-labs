package network;

import data.TransferingData;
import data.TwoSetsContainer;
import data.Worker;
import gui.Application;
import workersOperations.Storage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.PortUnreachableException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.*;
import java.util.stream.Collectors;

public class Receiver implements Runnable {
    private final DatagramChannel datagramChannel;
    private final Application application;
    private volatile TwoSetsContainer commands = null;
    private volatile Boolean isAuthorized = null;
    private volatile boolean interrupted = false;

    public Receiver(DatagramChannel channel, Application app) {
        datagramChannel = channel;
        application = app;
    }

    public DatagramChannel getDatagramChannel() {
        return datagramChannel;
    }

    public TwoSetsContainer getCommands() {
        return commands;
    }

    public Boolean confirmAuthorization() {
        return isAuthorized;
    }

    public void resetAuthorization() {
        isAuthorized = null;
    }

    public void stopReceiver() throws IOException {
        datagramChannel.disconnect();
        datagramChannel.close();
        interrupted = true;
    }

    public void run() {
        Object answer = null;
        LinkedList<TransferingData> answerList;
        while (!interrupted && datagramChannel.isOpen()) {
            ByteBuffer inputMessageBuffer = ByteBuffer.allocate(4096);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(inputMessageBuffer.array());
            try {
                if (datagramChannel.receive(inputMessageBuffer) != null) {
                    ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                    answer = objectInputStream.readObject();
                } else {
                    continue;
                }
            } catch (PortUnreachableException e) {
                application.sendMessage(ResourceBundle
                        .getBundle("bundles.Errors", application.getLocale()).getString("FAILED_TO_CONNECT"));
            } catch (IOException | ClassNotFoundException e) {
                continue;
            }
            if (answer instanceof LinkedList) {
                answerList = (LinkedList) answer;
                if (answerList.peek() == null) {
                    continue;
                }
                if (answerList.peek().getData() instanceof String) {
                    if (answerList.peek().getData().equals("Nothing to show")) {
                        Storage.getInstance().put(new ArrayList<>());
                        continue;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    while (!answerList.isEmpty()) {
                        stringBuilder.append((String) answerList.pollFirst().getData());
                        stringBuilder.append("\n");
                    }
                    application.sendMessage(stringBuilder.toString());
                    continue;
                }
                if (answerList.peek().getData() instanceof TwoSetsContainer) {
                    commands = (TwoSetsContainer) answerList.poll().getData();
                    continue;
                }
                if (answerList.peek().getData() instanceof Worker) {
                    List<Worker> workers = answerList.stream().map(TransferingData::getData)
                            .filter(e -> e instanceof Worker).map(e -> (Worker) e).collect(Collectors.toList());
                    Storage.getInstance().put(new ArrayList<>(workers));
                }
                if (answerList.peek().getData() instanceof Boolean) {
                    isAuthorized = (Boolean) Objects.requireNonNull(answerList.poll()).getData();
                }
            }
            answer = null;
        }
    }
}
