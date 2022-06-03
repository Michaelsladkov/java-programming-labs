package util;

import data.Person;
import data.Worker;

/**
 * This class is responsible for serialisation and describing the Worker class instance
 */
public class WorkerDecoder {
    /**
     * Describes the worker in console
     * @param worker worker class instance to be described
     */
    public void describe( Worker worker){
        System.out.println("---------------------------------------------------");
        System.out.println("Worker");
        System.out.println("id: "+worker.getId());
        System.out.println("Name:"+worker.getName());
        System.out.println("Coordinates: "+"("+worker.getCoordinates().getX()+", "+worker.getCoordinates().getY()+")");
        System.out.println("Salary: "+worker.getSalary());
        System.out.println("Start date: "+worker.getStartDate().toString());
        if(worker.getEndDate()!=null){
            System.out.println("End date: "+worker.getEndDate().toString());
        }
        else{
            System.out.println("End date: null");
        }
        System.out.println("Status: "+worker.getStatus());
        System.out.println("Personal stats:");
        Person person = worker.getPerson();
        System.out.println("\tHeight: "+person.getHeight());
        System.out.println("\tEye color: "+person.getEyeColor());
        System.out.println("\tHair color: "+person.getHairColor());
        System.out.println("\tNationality: "+person.getNationality());
        System.out.println("Creation time: "+worker.getCreationDate());
    }

    /**
     * Serialise worker instance to csv line
     * @param worker class instance to be serialised
     * @return csv line
     */
    public String getCSVLine(Worker worker){
        String output="";
        output += worker.getId()+",";
        output += '"'+worker.getName()+'"'+",";
        output += worker.getCoordinates().getX()+",";
        output += worker.getCoordinates().getY()+",";
        output += worker.getSalary()+",";
        output += worker.getStartDate().toString()+",";
        if(worker.getEndDate()!=null){
            output += worker.getEndDate().toString()+",";
        }
        else {
            output += "null,";
        }
        output += worker.getStatus()+",";
        Person person = worker.getPerson();
        output += person.getHeight()+",";
        output += person.getEyeColor()+",";
        output += person.getHairColor()+",";
        output += person.getNationality()+",";
        output += worker.getCreationDate().getTime()+"\n";
        return output;
    }
}
