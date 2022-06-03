package command;

import util.database.DatabaseWorks;
import util.StorageManager;
import util.WorkerFactory;
import util.network.Answer;

public class Update extends CommandNeedsId {
    private final StorageManager manager;
    private final WorkerFactory factory;
    private final DatabaseWorks databaseWorks;

    Update(StorageManager storageManager, WorkerFactory workerFactory,DatabaseWorks databaseWorks) {
        manager = storageManager;
        factory = workerFactory;
        this.databaseWorks = databaseWorks;
    }

    @Override
    public Answer execute(String args) {
        Answer answer = new Answer();
        Integer id;
        try {
            id = getId(args);
        }
        catch (NumberFormatException e) {
            answer.add("incorrect id format");
            return answer;
        }
        if (id == null) {
            answer.add("you should put an id");
            return answer;
        }
        String result = databaseWorks.updateWorker(id, factory.getWorkerFromLoad());
        if (result.equals("successfully updated")) {
            try {
                manager.remove(manager.getById(id));
            } catch (NullPointerException e) {
                answer.add("No worker with this id found");
                return answer;
            }
            factory.setStartId(id - 1);
            manager.add(factory.getWorkerFromLoad());
            factory.setStartId(manager.getMaxId());
        }
        answer.add(result);
        return answer;
    }

    @Override
    public String description() {
        return "This command reads new worker and save it to collection with id given in argument\n";
    }
}