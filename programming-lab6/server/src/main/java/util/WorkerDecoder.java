package util;

import data.Person;
import data.Worker;

/**
 * This class is responsible for serialisation and describing the Worker class instance
 */
public class WorkerDecoder {

    /**
     * Serialise worker instance to csv line
     *
     * @param worker class instance to be serialised
     * @return csv line
     */
    public String getCSVLine(Worker worker) {
        String output = "";
        output += worker.getId() + ",";
        output += '"' + worker.getName() + '"' + ",";
        output += worker.getCoordinates().getX() + ",";
        output += worker.getCoordinates().getY() + ",";
        output += worker.getSalary() + ",";
        output += worker.getStartDate().toString() + ",";
        if (worker.getEndDate() != null) {
            output += worker.getEndDate().toString() + ",";
        } else {
            output += "null,";
        }
        output += worker.getStatus() + ",";
        Person person = worker.getPerson();
        output += person.getHeight() + ",";
        output += person.getEyeColor() + ",";
        output += person.getHairColor() + ",";
        output += person.getNationality() + ",";
        output += worker.getCreationDate().getTime() + "\n";
        return output;
    }
}
