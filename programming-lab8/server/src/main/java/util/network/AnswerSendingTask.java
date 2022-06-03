package util.network;

import data.TransferingData;
import data.Worker;
import org.slf4j.Logger;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.Comparator;
import java.util.LinkedList;

public class AnswerSendingTask implements Runnable {
    private final Answer answerCapsule;
    private final Logger logger;
    private final DatagramSocket socket;
    private final SocketAddress socketAddress;

    public AnswerSendingTask(Answer answer, Logger logger, DatagramSocket socket, SocketAddress socketAddress) {
        answerCapsule = answer;
        this.logger = logger;
        this.socket = socket;
        this.socketAddress = socketAddress;
    }

    public void run() {
        try {
            socket.disconnect();
            ByteArrayOutputStream byteArrayOutputStream;
            ObjectOutputStream objectOutputStream;
            LinkedList<TransferingData> answer = answerCapsule.getList();
            socket.connect(socketAddress);
            if (answer.isEmpty()) {
                return;
            }
            if (answer.peek().getData() instanceof Worker) {
                answer.sort(Comparator.comparingDouble((TransferingData w) -> w.getWorker().getCoordinates().getDistanceFromZero()));
            }
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(answer);
            objectOutputStream.flush();
            logger.info("Formed answer length: " + answer.size() +
                    (answer.peek() == null ? "null" : answer.peek().toString()));
            DatagramPacket packet = new DatagramPacket(byteArrayOutputStream.toByteArray(),
                    byteArrayOutputStream.toByteArray().length, socketAddress);
            socket.send(packet);
            logger.info("Answer sent to " + socket.getRemoteSocketAddress());
        }
        catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}