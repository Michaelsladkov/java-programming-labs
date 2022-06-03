package util.network;

import data.TransferingData;
import data.TwoSetsContainer;
import data.Worker;

import java.util.LinkedList;

public class Answer {
    private final LinkedList<TransferingData> answer;

    public Answer() {
        answer = new LinkedList<>();
    }

    public Answer(String line) {
        answer = new LinkedList<>();
        answer.add(new TransferingData(line));
    }

    public void add(String part) {
        TransferingData container = new TransferingData(part);
        answer.add(container);
    }

    public void add(Worker part) {
        TransferingData container = new TransferingData(part);
        answer.add(container);
    }

    public void add(TwoSetsContainer part) {
        TransferingData container = new TransferingData(part);
        answer.add(container);
    }

    public void add(Boolean part) {
        TransferingData container = new TransferingData(part);
        answer.add(container);
    }

    public LinkedList<TransferingData> getList() {
        return answer;
    }
}
