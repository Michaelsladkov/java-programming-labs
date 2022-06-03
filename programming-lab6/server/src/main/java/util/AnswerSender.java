package util;

import data.TransferingData;
import data.TwoSetsContainer;
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

public class AnswerSender {
    private final DatagramSocket datagramSocket;
    private final LinkedList<TransferingData> answer;
    private final Logger logger;
    private SocketAddress socketAddress;

    public AnswerSender(DatagramSocket socket, Logger log) {
        answer = new LinkedList<>();
        datagramSocket = socket;
        logger = log;
    }

    public void setSocketAddress(SocketAddress newSocketAddress) {
        socketAddress = newSocketAddress;
    }

    public void prepareAnswer(Object answer) {
        this.answer.clear();
        addToAnswer(answer);
    }

    public void addToAnswer(Object part) {
        TransferingData container = new TransferingData("empty");
        if(part instanceof String){
            container = new TransferingData((String) part);
        }
        if(part instanceof Worker){
            container = new TransferingData((Worker) part);
        }
        if(part instanceof TwoSetsContainer){
            container = new TransferingData((TwoSetsContainer) part);
        }
        answer.add(container);
    }

    public void sendAnswer() {
        ByteArrayOutputStream byteArrayOutputStream;
        ObjectOutputStream objectOutputStream;
        if (answer.isEmpty()) {
            return;
        }
        if (answer.peek().getData() instanceof Worker){
            answer.sort(Comparator.comparingDouble((TransferingData w) -> w.getWorker().getCoordinates().getDistanceFromZero()));
        }
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            datagramSocket.connect(socketAddress);
            objectOutputStream.writeObject(answer);
            objectOutputStream.flush();
            logger.info("Formed answer length: " + answer.size());
            DatagramPacket packet = new DatagramPacket(byteArrayOutputStream.toByteArray(),
                    byteArrayOutputStream.toByteArray().length);
            datagramSocket.send(packet);
            logger.info("Answer sent to " + datagramSocket.getRemoteSocketAddress());
        } catch (IOException e) {
            logger.info("Failed to send answer " + e.getMessage() + e.getCause());
        }
        answer.clear();
    }
}
