package utilL;

import data.Worker;

import java.util.Comparator;

public class EndDateComparator implements Comparator<Worker> {
    public int compare(Worker w1, Worker w2){
        if(w1.getEndDate() == null){
            return -1;
        }
        return w1.getEndDate().compareTo(w2.getEndDate());
    }

    public static int compares(Worker w1, Worker w2){
        if(w1.getEndDate() == null){
            return -1;
        }
        if (w2.getEndDate() == null){
            return 1;
        }
        return w1.getEndDate().compareTo(w2.getEndDate());
    }
}