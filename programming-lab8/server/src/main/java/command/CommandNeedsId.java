package command;

public abstract class CommandNeedsId implements Command {
    protected Integer getId(String args) throws NumberFormatException {
        if (args.length() == 0) {
            return null;
        }
        int id;
        id = Integer.parseInt(args);
        return id;
    }
}