package command;

import util.AnswerSender;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

/**
 * Help command.
 * Shows help about all commands
 */
public class Help implements Command {
    private final HashMap<String, Command> commands;
    private final AnswerSender sender;

    /**
     * Constructor for this command
     *
     * @param commands - hash map with all commands, taken from invoker. It is used to get access to description() method of each command
     */
    Help(HashMap<String, Command> commands, AnswerSender answerSender) {
        this.commands = commands;
        sender = answerSender;
    }

    @Override
    public void execute(String args) {
        Set<String> helpSource = new TreeSet<>(commands.keySet());
        helpSource.remove("save");
        StringBuilder answer = new StringBuilder();
        for (String entry : helpSource) {
            answer.append(" -").append(entry).append("\n");
            answer.append("\t").append(commands.get(entry).description()).append("\n");
        }
        answer.append(" - execute_script").append("\n");
        answer.append("Launches script from file picked from argument").append("\n");
        sender.prepareAnswer(answer.toString());
    }

    @Override
    public String description() {
        return "This command shows help about all commands\n";
    }
}
