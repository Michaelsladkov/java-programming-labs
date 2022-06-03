package data;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;

public class Worker implements Comparable<Worker> {
    private final int id;
    private final String name;
    private final Coordinates coordinates;
    private final Date creationDate;
    private final long salary;
    private final ZonedDateTime startDate;
    private final LocalDate endDate;
    private final Status status;
    private final Person person;

    public Worker(String name,
                  Coordinates coordinates,
                  Date creationDate,
                  long salary,
                  ZonedDateTime startDate,
                  LocalDate endDate,
                  Status status,
                  Person person,
                  int id){
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.salary = salary;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.person = person;
        this.id = id;
    }

    public long getValue(){
        return creationDate.getTime()+salary+status.ordinal();
    }

    @Override
    public int compareTo (Worker worker) {
        if(this.equals(worker)){
            return 0;
        }
        return Long.compare(this.getValue(),worker.getValue());
    }

    @Override
    public boolean equals(Object obj) {
        if(!this.getClass().equals(obj.getClass())){
            return false;
        }
        Worker wrkr = (Worker)obj;
        if(this.getId() == wrkr.getId()) return true;
        boolean ret;
        ret = this.name.equals(wrkr.getName());
        ret &= this.coordinates.equals(wrkr.getCoordinates());
        ret &= this.startDate.equals(wrkr.getStartDate());
        ret &= this.endDate==null ? wrkr.getEndDate()==null : this.endDate.equals(wrkr.getEndDate());
        ret &= this.salary==wrkr.getSalary();
        ret &= this.status.equals(wrkr.getStatus());
        ret &= this.person.equals(wrkr.getPerson());
        return ret;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public Coordinates getCoordinates(){
        return coordinates;
    }

    public Date getCreationDate(){
        return creationDate;
    }

    public long getSalary(){return salary;}

    public ZonedDateTime getStartDate(){
        return startDate;
    }

    public LocalDate getEndDate(){ return endDate;}

    public Status getStatus(){return status;}

    public Person getPerson(){return person;}
}