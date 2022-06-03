package data;

import java.io.Serializable;
import java.util.Set;

public class TwoSetsContainer implements Serializable {
    private final Set<String> availableCommands;
    private final Set<String> workerNeedCommands;

    public TwoSetsContainer(Set<String> availableCommands, Set<String> workerNeedCommands){
        this.availableCommands = availableCommands;
        this.workerNeedCommands = workerNeedCommands;
    }

    public Set<String> getAvailableCommands(){
        return availableCommands;
    }

    public Set<String> getWorkerNeedCommands(){
        return workerNeedCommands;
    }
}
