package command;

import java.util.HashMap;
import java.util.Map;

/**
 * Help command.
 * Shows help about all commands
 */
public class Help implements Command{
    private final HashMap<String, Command> commands;

    /**
     * Constructor for this command
     * @param commands - hash map with all commands, taken from invoker. It is used to get access to description() method of each command
     */
    Help(HashMap<String, Command> commands){this.commands = commands;}

    @Override
    public void execute(String args) {
        for(Map.Entry<String, Command> entry:commands.entrySet()){
            System.out.println(" -"+entry.getKey());
            System.out.println(entry.getValue().description());
        }
    }

    @Override
    public String description() {
        return "This command shows help about all commands\n";
    }
}
