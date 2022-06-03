package command;


/**
 * Exit command
 * Stops command line listener. It stop's all app in console mode or interrupts script in script mode
 * @see util.CommandLineListener
 */
public class Exit implements Command{
    private final Invoker invoker;
    /**
     * Constructor for this command
     * @param invoker - Invoker, translated to this class to init new
     */
    Exit(Invoker invoker){
        this.invoker=invoker;
    }

    @Override
    public void execute(String args) {
        invoker.requestExit(this);
    }

    @Override
    public String description() {
        return "This command stops running of this application\n";
    }
}
