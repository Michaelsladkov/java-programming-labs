package util.network;

import command.Invoker;
import data.CommandToSend;
import data.Worker;
import org.slf4j.Logger;
import util.WorkerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;

public class RequestManager {
    private final AnswerSender sender;
    private final WorkerFactory workerFactory;
    private final Invoker invoker;
    private final Logger logger;

    public RequestManager(AnswerSender answerSender,
                          WorkerFactory factory,
                          Invoker invoker,
                          Logger log) {
        sender = answerSender;
        workerFactory = factory;
        logger = log;
        this.invoker = invoker;
    }

    public RequestProcessor processRequest(DatagramPacket rq) {
        return new RequestProcessor(rq);
    }
    private class RequestProcessor implements Runnable {
        private DatagramPacket request;

        private RequestProcessor(DatagramPacket rq) {
            request = rq;
        }

        public void run() {
            Object rawInput;
            ObjectInputStream objectInputStream;
            ByteArrayInputStream inputStream;
            CommandToSend clientInput = null;
            String args;
            String command;
            inputStream = new ByteArrayInputStream(request.getData());
            try {
                objectInputStream = new ObjectInputStream(inputStream);
                rawInput = objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }
            if (rawInput instanceof CommandToSend) {
                clientInput = (CommandToSend) rawInput;
                logger.info("Received command: " + clientInput);
            }
            if (clientInput != null) {
                command = clientInput.getCommandType();
                args = clientInput.getCommandArgs();
                if (clientInput.getLoad() != null) {
                    if (clientInput.getLoad() instanceof Worker) {
                        workerFactory.putLoad(clientInput.getLoad());
                    }
                }
                invoker.execute(command, args, clientInput.getUserName(), clientInput.getPassword(), request.getSocketAddress());
            }
        }
    }
}
