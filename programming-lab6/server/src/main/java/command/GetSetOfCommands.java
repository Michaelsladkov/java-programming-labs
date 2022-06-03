package command;

import data.TwoSetsContainer;
import util.AnswerSender;

import java.util.Collection;
import java.util.HashMap;
import java.util.TreeSet;

public class GetSetOfCommands implements Command {
    private final HashMap<String, Command> commands;
    private final AnswerSender sender;

    GetSetOfCommands(HashMap<String, Command> commands, AnswerSender answerSender) {
        this.commands = new HashMap<>(commands);
        sender = answerSender;
    }

    @Override
    public void execute(String arg) {
        TreeSet<String> commandsNeededWorker = new TreeSet<>();
        commandsNeededWorker.add("add");
        commandsNeededWorker.add("add_if_max");
        commandsNeededWorker.add("add_if_min");
        commandsNeededWorker.add("remove_lower");
        commandsNeededWorker.add("update");
        Collection<String> names = new TreeSet<>(commands.keySet());
        names.remove("save");
        sender.prepareAnswer(new TwoSetsContainer(new TreeSet<>(names), commandsNeededWorker));
    }

    @Override
    public String description() {
        return ("This command sends two sets of commands to client");
    }
}
