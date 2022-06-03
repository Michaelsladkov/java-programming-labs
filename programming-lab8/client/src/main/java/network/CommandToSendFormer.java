package network;

import data.CommandToSend;
import data.Worker;

import java.util.HashSet;
import java.util.Set;

public class CommandToSendFormer {
    private static CommandToSendFormer instance = null;
    private final Set<String> workerNeedCommand;

    private CommandToSendFormer() {
        workerNeedCommand = new HashSet<>();
        workerNeedCommand.add("add");
        workerNeedCommand.add("add_if_max");
        workerNeedCommand.add("add_if_min");
        workerNeedCommand.add("update");
        workerNeedCommand.add("remove_lower");
    }

    public static CommandToSendFormer getInstance() {
        if (instance == null) {
            instance = new CommandToSendFormer();
        }
        return instance;
    }

    public CommandToSend getCommandToSend(String type, String args) {
        Worker worker;
        return new CommandToSend(type, args, null, Session.getInstance().getUserName(), Session.getInstance().getPassword());
    }

    public CommandToSend getCommandToSend(String type, String args, Worker worker) {
        if (workerNeedCommand.contains(type)) {
            return new CommandToSend(type, args, worker, Session.getInstance().getUserName(), Session.getInstance().getPassword());
        }
        return getCommandToSend(type, args);
    }
}
