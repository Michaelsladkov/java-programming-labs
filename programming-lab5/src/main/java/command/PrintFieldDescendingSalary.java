package command;

import data.Worker;
import util.StorageManager;

import java.util.ArrayList;

public class PrintFieldDescendingSalary implements Command{
    private final StorageManager manager;
    PrintFieldDescendingSalary(StorageManager storageManager){
        manager = storageManager;
    }

    @Override
    public void execute(String args) {
        ArrayList<Long> salaries = new ArrayList<>();
        for(Worker worker:manager.getCollection()){
            salaries.add(worker.getSalary());
        }
        salaries.sort((o1, o2) -> Long.compare(o2, o1));
        for(long salary:salaries){
            System.out.println(salary);
        }
    }

    @Override
    public String description() {
        return "Returns list of salaries in descending order\n";
    }
}
