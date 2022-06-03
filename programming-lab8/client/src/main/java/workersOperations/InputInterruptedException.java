package workersOperations;

public class InputInterruptedException extends Exception {
    public InputInterruptedException() {
        super("input was interrupted");
    }
}