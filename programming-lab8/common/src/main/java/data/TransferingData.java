package data;

import java.io.Serializable;

public class TransferingData implements Serializable {
    private final Object data;

    public TransferingData(String line){
        data = line;
    }

    public TransferingData(Worker worker){
        data = worker;
    }

    public TransferingData(TwoSetsContainer cont){
        data = cont;
    }

    public TransferingData(Boolean cont) {
        data = cont;
    }

    public Object getData(){
        return data;
    }

    public Worker getWorker(){
        if(data instanceof Worker){
            return (Worker) data;
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append(data.getClass()).append(" ");
        if (data instanceof Boolean) {
            output.append((Boolean) data);
        }
        return output.toString();
    }
}
