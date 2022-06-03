package command;

import data.Worker;
import util.database.DatabaseWorks;
import util.network.Answer;
import util.StorageManager;
import util.WorkerFactory;

public class RemoveLower implements Command, LoginNeededCommand {
    private final StorageManager manager;
    private final WorkerFactory factory;
    private final DatabaseWorks databaseWorks;
    private String login;

    RemoveLower(StorageManager storageManager, WorkerFactory workerFactory,
                DatabaseWorks dbWorks) {
        manager = storageManager;
        factory = workerFactory;
        databaseWorks = dbWorks;
    }

    @Override
    public void putLogin(String login) {
        this.login = login;
    }

    @Override
    public Answer execute(String args) {
        Answer answer = new Answer();
        Worker worker = factory.getWorkerFromLoad();
        for (Worker collected : manager.getCollection()) {
            if (worker.compareTo(collected) < 0) {
                if (databaseWorks.deleteWorker(worker.getId(), login).equals("successfully removed")) {
                    manager.remove(collected);
                }
            }
        }
        answer.add("All lower elements have been removed");
        return answer;
    }

    @Override
    public String description() {
        return "This command takes worker from console and remove all smaller workers from collection\n";
    }
}
