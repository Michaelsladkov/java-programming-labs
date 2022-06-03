package data;

import java.io.Serializable;

public class CommandToSend implements Serializable {
    private final String commandType;
    private final String commandArgs;
    private final Object load;
    private final String userName;
    private final String password;

    public CommandToSend(String type, String args, String userName, String password) {
        commandType = type;
        commandArgs = args;
        load = null;
        this.userName = userName;
        this.password = password;
    }

    public CommandToSend(String type, String args, Object load, String userName, String password) {
        commandType = type;
        commandArgs = args;
        this.load = load;
        this.userName = userName;
        this.password = password;
    }

    public String getCommandType(){
        return commandType;
    }

    public String getCommandArgs(){
        return commandArgs;
    }

    public Object getLoad(){
        return load;
    }

    @Override
    public String toString() {
        return commandType + " "
                + (commandArgs != null ? commandArgs : "null") + " "
                + (load != null ? load.toString() : "null") + " "
                + userName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }
}
