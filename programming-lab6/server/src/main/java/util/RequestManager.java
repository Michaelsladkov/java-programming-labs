package util;

import command.Invoker;
import data.CommandToSend;
import org.slf4j.Logger;

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

    public void processRequest(DatagramPacket request) {
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
            logger.info("Received command: " + clientInput.toString());
            sender.setSocketAddress(request.getSocketAddress());
        }
        if (clientInput != null) {
            command = clientInput.getCommandType();
            args = clientInput.getCommandArgs();
            if (clientInput.getLoad() != null) {
                workerFactory.putLoad(clientInput.getLoad());
            }
            invoker.execute(command, args);
        }
    }
}
