package command;

import util.network.Answer;

/**
 * Interface for commands which are called from command line.
 */
public interface Command {
    /**
     * Command execution procedure
     *
     * @param args - arguments from command line
     */
    Answer execute(String args);

    default String description() {
        return "this command don't have a description\n";
    }
}
