package network;

import data.CommandToSend;
import data.IncorrectValueException;
import data.NullFieldException;
import data.Worker;
import workersOperations.InputInterruptedException;
import workersOperations.WorkerFactory;

import java.util.Set;

public class CommandToSendFormer {
    private final WorkerFactory factory;
    private final Set<String> workerNeedCommand;

    public CommandToSendFormer(WorkerFactory factory, Set<String> commands) {
        this.factory = factory;
        workerNeedCommand = commands;
    }

    public CommandToSend getCommandToSend(String type, String args) {
        Worker worker;
        try {
            if (workerNeedCommand.contains(type)) {
                worker = factory.readWorkerFromConsole();
                return new CommandToSend(type, args, worker, Session.getInstance().getUserName(), Session.getInstance().getPassword());
            }
        } catch (IncorrectValueException | NullFieldException | InputInterruptedException e) {
            System.out.println(e.getMessage());
        }
        return new CommandToSend(type, args, null, Session.getInstance().getUserName(), Session.getInstance().getPassword());
    }
}
