package command;

import util.AnswerSender;

public abstract class CommandNeedsId implements Command {
    protected Integer getId(String args, AnswerSender sender) {
        if (args.length() == 0) {
            sender.prepareAnswer("id is required");
            return null;
        }
        int id;
        try {
            id = Integer.parseInt(args);
        } catch (NumberFormatException e) {
            sender.prepareAnswer("Your input is not an id");
            return null;
        }
        return id;
    }
}