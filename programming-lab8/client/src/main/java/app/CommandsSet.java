package app;

import java.util.HashSet;
import java.util.Set;

public class CommandsSet {
    private static final Set<String> commandsSet = new HashSet<>();

    static {
        commandsSet.add("add");
        commandsSet.add("add_if_min");
        commandsSet.add("add_if_max");
        commandsSet.add("remove_by_id");
        commandsSet.add("remove_lower");
        commandsSet.add("remove_all_by_status");
        commandsSet.add("clear");
        commandsSet.add("help");
        commandsSet.add("info");
        commandsSet.add("ping");
        commandsSet.add("update");
        commandsSet.add("show");
    }

    public static Set<String> getCommands() {
        return commandsSet;
    }
}
