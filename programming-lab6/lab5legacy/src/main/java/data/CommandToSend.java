package data;

import java.io.Serializable;

public class CommandToSend implements Serializable {
    private final String commandType;
    private final String commandArgs;
    private final Object load;

    public CommandToSend(String type, String args){
        commandType = type;
        commandArgs = args;
        load = null;
    }

    public CommandToSend(String type, String args, Object load){
        commandType = type;
        commandArgs = args;
        this.load = load;
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
        return commandType+" "
                +(commandArgs != null ? commandArgs : "null")+" "
                +(load != null ? load.toString() : "null");
    }
}
