package command;

import data.TwoSetsContainer;
import util.network.Answer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class GetSetOfCommands implements Command {
    private final Map<String, Command> commands;

    GetSetOfCommands(Map<String, Command> commands) {
        this.commands = new HashMap<>(commands);
    }

    @Override
    public Answer execute(String args) {
        Answer answer = new Answer();
        TreeSet<String> commandsNeededWorker = new TreeSet<>();
        commandsNeededWorker.add("add");
        commandsNeededWorker.add("add_if_max");
        commandsNeededWorker.add("add_if_min");
        commandsNeededWorker.add("remove_lower");
        commandsNeededWorker.add("update");
        Collection<String> names = new TreeSet<>(commands.keySet());
        names.remove("save");
        answer.add(new TwoSetsContainer(new TreeSet<>(names), commandsNeededWorker));
        return answer;
    }

    @Override
    public String description() {
        return ("This command sends two sets of commands to client");
    }
}
