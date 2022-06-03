package command;

import util.StorageManager;

public class Info implements Command {
    private final StorageManager storageManager;
    Info(StorageManager manager){
        storageManager = manager;
    }

    @Override
    public void execute(String args) {
        for(String line:storageManager.getInfo()){
            System.out.println(line);
        }
    }

    @Override
    public String description() {
        return "This command shows general information about the collection\n";
    }
}
