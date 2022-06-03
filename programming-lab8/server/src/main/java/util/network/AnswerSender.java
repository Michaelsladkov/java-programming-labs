package util.network;

import org.slf4j.Logger;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

public class AnswerSender {
    private final DatagramSocket datagramSocket;
    private final Logger logger;
    private final ExecutorService senderExecutor;

    public AnswerSender(DatagramSocket socket, Logger log) {
        datagramSocket = socket;
        logger = log;
        senderExecutor = new ForkJoinPool();
    }


    public void sendAnswer(Answer answerCapsule, SocketAddress socketAddress) {
        senderExecutor.execute(new AnswerSendingTask(answerCapsule, logger, datagramSocket, socketAddress));
    }
}
