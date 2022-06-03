package command;

import util.StorageManager;
/** Clear command
 * Remove all elements from collection
 */
public class Clear implements Command{
    private final StorageManager storage;

    /**
     * Constructor for this command
     * @param storage - receiver, collection manager
     */
    Clear(StorageManager storage){
        this.storage = storage;
    }
    @Override
    public void execute(String args){
        storage.clear();
        System.out.println("Collection cleared");
    }

    @Override
    public String description() {
        return "This command clears the collection\n";
    }
}