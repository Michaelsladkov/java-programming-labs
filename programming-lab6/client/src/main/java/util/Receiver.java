package util;


import data.TransferingData;
import data.TwoSetsContainer;
import data.Worker;
import utilL.WorkerDecoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.PortUnreachableException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.LinkedList;

public class Receiver extends Thread {
    private final DatagramChannel datagramChannel;
    private final WorkerDecoder decoder;
    private TwoSetsContainer commands = null;

    public Receiver(DatagramChannel channel, WorkerDecoder workerDecoder) {
        datagramChannel = channel;
        decoder = workerDecoder;
    }

    public TwoSetsContainer getCommands() {
        return commands;
    }

    public void stopReceiver() throws IOException {
        datagramChannel.disconnect();
        datagramChannel.close();
    }

    public void run() {
        Object answer = null;
        LinkedList<TransferingData> answerList;
        while (!isInterrupted() && datagramChannel.isOpen()) {
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
                System.out.println("Server is temporary unavailable");
                continue;
            } catch (IOException | ClassNotFoundException e) {
                continue;
            }
            if (answer instanceof LinkedList) {
                answerList = (LinkedList) answer;
                if (answerList.peek().getData() instanceof TwoSetsContainer) {
                    commands = (TwoSetsContainer) answerList.poll().getData();
                    continue;
                }
                if (answerList.peek() == null) {
                    continue;
                }
                if (answerList.peek().getData() instanceof String) {
                    while (!answerList.isEmpty()) {
                        System.out.println((String) answerList.pollFirst().getData());
                    }
                    continue;
                }
                if (answerList.peek().getData() instanceof Worker) {
                    for (Object o : answerList) {
                        decoder.describe((Worker) ((TransferingData) o).getData());
                    }
                    continue;
                }
            }
            byteArrayInputStream.reset();
            answer = null;
            inputMessageBuffer.clear();
        }
    }
}
