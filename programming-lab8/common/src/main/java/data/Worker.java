package data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;

public class Worker implements Comparable<Worker>, Serializable {
    private final int id;
    private final String name;
    private final Coordinates coordinates;
    private final Date creationDate;
    private final long salary;
    private final ZonedDateTime startDate;
    private final LocalDate endDate;
    private final Status status;
    private final Person person;
    private final String creator;

    public Worker(String name,
                  Coordinates coordinates,
                  Date creationDate,
                  long salary,
                  ZonedDateTime startDate,
                  LocalDate endDate,
                  Status status,
                  Person person,
                  int id,
                  String creator) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.salary = salary;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.person = person;
        this.id = id;
        this.creator = creator;
    }

    public Worker(Worker otherWorker, int newId) {
        this.id = newId;
        name = otherWorker.getName();
        coordinates = otherWorker.getCoordinates();
        creationDate = otherWorker.getCreationDate();
        salary = otherWorker.getSalary();
        status = otherWorker.getStatus();
        startDate = otherWorker.getStartDate();
        endDate = otherWorker.getEndDate();
        person = otherWorker.getPerson();
        creator = otherWorker.getCreator();
    }

    public long getValue() {
        return  salary + status.ordinal();
    }

    @Override
    public int compareTo(Worker worker) {
        if (this.equals(worker)) {
            return 0;
        }
        return Long.compare(worker.getValue(), this.getValue());
    }

    @Override
    public boolean equals(Object obj) {
        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }
        Worker wrkr = (Worker) obj;
        boolean ret;
        ret = this.name.equals(wrkr.getName());
        ret &= this.coordinates.equals(wrkr.getCoordinates());
        ret &= this.startDate.equals(wrkr.getStartDate());
        ret &= this.endDate == null ? wrkr.getEndDate() == null : this.endDate.equals(wrkr.getEndDate());
        ret &= this.salary == wrkr.getSalary();
        ret &= this.status.equals(wrkr.getStatus());
        ret &= this.person.equals(wrkr.getPerson());
        return ret;
    }

    @Override
    public int hashCode() {
        int hash = id;
        hash+=name.hashCode();
        hash+=coordinates.getX()*13+coordinates.getY()*17;
        hash+=startDate.hashCode();
        hash += endDate == null ? 713 : endDate.hashCode();
        hash += salary*7;
        hash += person.hashCode();
        return hash;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public long getSalary() {
        return salary;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Status getStatus() {
        return status;
    }

    public Person getPerson() {
        return person;
    }

    public String getCreator() {
        return creator;
    }
}