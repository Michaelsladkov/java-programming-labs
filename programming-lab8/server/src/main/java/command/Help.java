package command;

import util.network.Answer;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Help command.
 * Shows help about all commands
 */
public class Help implements Command {
    private final Map<String, Command> commands;

    /**
     * Constructor for this command
     *
     * @param commands - hash map with all commands, taken from invoker. It is used to get access to description() method of each command
     */
    Help(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public Answer execute(String args) {
        Answer answerTosend = new Answer();
        StringBuilder answer = new StringBuilder();
        Set<String> names = new HashSet<>(commands.keySet());
        names.remove("login");
        names.remove("register");
        names.remove("get_set_of_commands");
        for (String entry : names) {
            answer.append(" -").append(entry).append("\n");
            answer.append("\t").append(commands.get(entry).description()).append("\n");
        }
        answerTosend.add(answer.toString());
        return answerTosend;
    }

    @Override
    public String description() {
        return "This command shows help about all commands\n";
    }
}
