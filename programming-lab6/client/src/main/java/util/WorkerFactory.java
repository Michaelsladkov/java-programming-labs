package util;

import data.Color;
import data.Coordinates;
import data.Country;
import data.IncorrectValueException;
import data.NullFieldException;
import data.Person;
import data.Status;
import data.Worker;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * This class is responsible for creation of new instances of Worker class
 */
public class WorkerFactory {
    private final FieldsReader fieldsReader;
    private int id;
    private Scanner scanner;

    /**
     * @param startId      - start point for id counter. It is being changed after file reading - to make id's unique.
     * @param fieldsReader - instance of FieldsReader which will be used to get values for worker's fields
     */
    public WorkerFactory(int startId, FieldsReader fieldsReader) {
        this.id = startId;
        this.fieldsReader = fieldsReader;
    }

    /**
     * Creates new worker with new id and creation date
     *
     * @param name        worker's name
     * @param coordinates worker's coordinates
     * @param salary      worker's salary
     * @param startDate   worker's start date
     * @param endDate     worker's end date
     * @param status      worker's status
     * @param height      worker's height
     * @param eyeColor    worker's eye color
     * @param hairColor   worker's hair color
     * @param nationality worker's nationality
     * @return worker instance
     * @throws NullFieldException      if field which shouldn't be null is null
     * @throws IncorrectValueException if value is unable to be applied for field
     */
    public Worker createWorker(String name, Coordinates coordinates,
                               long salary, ZonedDateTime startDate,
                               LocalDate endDate, Status status,
                               Double height, Color eyeColor,
                               Color hairColor, Country nationality) throws NullFieldException, IncorrectValueException {
        return createWorkerWithIdAndDate(++id, name,
                coordinates, salary,
                startDate, endDate,
                status, height,
                eyeColor, hairColor,
                nationality, new Date());
    }

    /**
     * Creates new worker with given id and creation date
     *
     * @param _id          worker's id
     * @param name         worker's name
     * @param coordinates  worker's coordinates
     * @param salary       worker's salary
     * @param startDate    worker's start date
     * @param endDate      worker's end date
     * @param status       worker's status
     * @param height       worker's height
     * @param eyeColor     worker's eye color
     * @param hairColor    worker's hair color
     * @param nationality  worker's nationality
     * @param creationDate worker's creation date
     * @return worker instance
     * @throws NullFieldException      if field which shouldn't be null is null
     * @throws IncorrectValueException if value is unable to be applied for field
     */
    public Worker createWorkerWithIdAndDate(int _id, String name, Coordinates coordinates,
                                            long salary, ZonedDateTime startDate,
                                            LocalDate endDate, Status status,
                                            Double height, Color eyeColor,
                                            Color hairColor, Country nationality,
                                            Date creationDate) throws NullFieldException, IncorrectValueException {
        if (name == null || name.isEmpty()) {
            throw new NullFieldException("Name");
        }
        if (coordinates == null) {
            throw new NullFieldException("Coordinates");
        }
        if (salary <= 0) {
            throw new IncorrectValueException("Salary", "Should be more than 0");
        }
        if (startDate == null) {
            throw new NullFieldException("Start date");
        }
        if (status == null) {
            throw new NullFieldException("Status");
        }
        if (height == null) {
            throw new NullFieldException("Height");
        }
        if (nationality == null) {
            throw new NullFieldException("Nationality");
        }
        if (height <= 0) {
            throw new IncorrectValueException("Height", "Should be more than 0");
        }
        Person person = new Person(height, nationality, eyeColor, hairColor);
        return new Worker(name, coordinates, creationDate, salary, startDate, endDate, status, person, _id);
    }

    /**
     * Reads worker's field values from console or script and creates new worker instance
     *
     * @return worker instance
     * @throws NullFieldException      if field which shouldn't be null is null
     * @throws IncorrectValueException if value is unable to be applied for field
     */
    public Worker readWorkerFromConsole() throws NullFieldException, IncorrectValueException, InputInterruptedException {
        fieldsReader.setScanner(scanner);
        try {
            return createWorker(
                    fieldsReader.readName(),
                    new Coordinates(fieldsReader.readCoordinate('x'), fieldsReader.readCoordinate('y')),
                    fieldsReader.readSalary(),
                    fieldsReader.readStartDate(),
                    fieldsReader.readEndDate(),
                    fieldsReader.readStatus(),
                    fieldsReader.readHeight(),
                    fieldsReader.readColor("eye color"),
                    fieldsReader.readColor("hair color"),
                    fieldsReader.readNationality()
            );
        } catch (NoSuchElementException e) {
            throw new InputInterruptedException();
        }
    }

    public Scanner getScanner() {
        return scanner;
    }

    /**
     * Sets new scanner to be used in readWorkerFromConsole()
     * This method is used in execute_script command
     *
     * @param scanner scanner instance to be set
     */
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public void setScriptRunning(boolean running){
        fieldsReader.setScriptRunning(running);
    }
}