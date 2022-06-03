package command;

import util.StorageManager;
import util.FileWorks;

import java.io.IOException;

public class Save implements Command {
    private final FileWorks fileWorks;
    private final StorageManager manager;
    Save( FileWorks fileWorks, StorageManager storageManager){
        this.fileWorks = fileWorks;
        manager = storageManager;
    }

    @Override
    public void execute(String args) {
        try {
            fileWorks.saveCollection(manager.getCollection());
            System.out.println("Collection saved successfully");
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String description() {
        return "This command writes csv file with collection elements - to be opened next time\n" +
                "File name is workers.csv\n";
    }
}