package util;

import org.slf4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;

/**
 * This class is responsible for listening command line? separating command name and arguments and invoker calling
 */
public class Listener extends Thread {
    private final DatagramSocket datagramSocket;
    private final RequestManager manager;
    private final Logger logger;

    public Listener(DatagramSocket datagramSocket,
                    RequestManager requestManager,
                    Logger log) {
        this.datagramSocket = datagramSocket;
        manager = requestManager;
        logger = log;
    }

    /**
     * starts reading loop
     * this loop reads commands ad calls invoker
     * loop is finished if input is empty or exit command is activated
     */
    public void run() {
        DatagramPacket request;
        while (!isInterrupted()) {
            datagramSocket.disconnect();
            byte[] requestBuf = new byte[1024];
            try {
                request = new DatagramPacket(requestBuf, requestBuf.length);
                datagramSocket.receive(request);
            } catch (SocketTimeoutException e) {
                continue;
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            logger.info("Request received from {}", request.getSocketAddress());
            manager.processRequest(request);
        }
    }
}