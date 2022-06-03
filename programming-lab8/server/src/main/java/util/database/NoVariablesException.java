package util.database;

public class NoVariablesException extends RuntimeException {
    public NoVariablesException(String message) {
        super(message);
    }

    public NoVariablesException() {
        super("Variables DB_LOGIN and DB_PASSWORD are not assigned");
    }
}
