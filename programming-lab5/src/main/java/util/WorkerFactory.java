package util;

import data.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is responsible for creation of new instances of Worker class
 */
public class WorkerFactory {
    private int id;
    private Scanner scanner;
    private final FieldsReader fieldsReader;

    /**
     * @param startId - start point for id counter. It is being changed after file reading - to make id's unique.
     * @param fieldsReader - instance of FieldsReader which will be used to get values for worker's fields
     */
    public WorkerFactory(int startId, FieldsReader fieldsReader){
        this.id = startId;
        this.fieldsReader = fieldsReader;
    }

    /**
     * Creates new worker with new id and creation date
     * @param name worker's name
     * @param coordinates worker's coordinates
     * @param salary worker's salary
     * @param startDate worker's start date
     * @param endDate worker's end date
     * @param status worker's status
     * @param height worker's height
     * @param eyeColor worker's eye color
     * @param hairColor worker's hair color
     * @param nationality worker's nationality
     * @return worker instance
     * @throws NullFieldException if field which shouldn't be null is null
     * @throws IncorrectValueException if value is unable to be applied for field
     */
    public Worker createWorker(String name, Coordinates coordinates,
                               long salary, ZonedDateTime startDate,
                               LocalDate endDate, Status status,
                               Double height, Color eyeColor,
                               Color hairColor, Country nationality) throws NullFieldException, IncorrectValueException{
        return createWorkerWithIdAndDate(++id, name,coordinates,salary,startDate,endDate,status,height,eyeColor,hairColor,nationality, new Date());
    }

    /**
     * Creates new worker with given id and creation date
     * @param _id worker's id
     * @param name worker's name
     * @param coordinates worker's coordinates
     * @param salary worker's salary
     * @param startDate worker's start date
     * @param endDate worker's end date
     * @param status worker's status
     * @param height worker's height
     * @param eyeColor worker's eye color
     * @param hairColor worker's hair color
     * @param nationality worker's nationality
     * @param creationDate worker's creation date
     * @return worker instance
     * @throws NullFieldException if field which shouldn't be null is null
     * @throws IncorrectValueException if value is unable to be applied for field
     */
    public Worker createWorkerWithIdAndDate(int _id, String name, Coordinates coordinates,
                               long salary, ZonedDateTime startDate,
                               LocalDate endDate, Status status,
                               Double height, Color eyeColor,
                               Color hairColor, Country nationality,
                                Date creationDate) throws NullFieldException, IncorrectValueException {
        if(name==null||name.length()==0){
            throw new NullFieldException("Name");
        }
        if(coordinates==null){
            throw new NullFieldException("Coordinates");
        }
        if(salary<=0){
            throw new IncorrectValueException("Salary", "Should be more than 0");
        }
        if(startDate==null){
            throw new NullFieldException("Start date");
        }
        if (status==null){
            throw new NullFieldException("Status");
        }
        if(height==null){
            throw new NullFieldException("Height");
        }
        if(nationality==null){
            throw new NullFieldException("Nationality");
        }
        if(height<=0){
            throw new IncorrectValueException("Height", "Should be more than 0");
        }
        Person person=new Person(height, nationality, eyeColor, hairColor);
        return new Worker(name, coordinates, creationDate, salary, startDate, endDate, status, person, _id);
    }

    /**
     * Reads worker's field values from console or script and creates new worker instance
     * @return worker instance
     * @throws NullFieldException if field which shouldn't be null is null
     * @throws IncorrectValueException if value is unable to be applied for field
     */
    public Worker readWorkerFromConsole() throws NullFieldException, IncorrectValueException, InputInterruptedException{
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
        }
        catch (NoSuchElementException e){
            throw new InputInterruptedException();
        }
    }

    /**
     * Sets new scanner to be used in readWorkerFromConsole()
     * This method is used in execute_script command
     * @param scanner scanner instance to be set
     */
    public void setScanner(Scanner scanner){
        this.scanner=scanner;
    }

    /**
     * Reads csv line and creates worker with values from that line
     * @param line - line of csv input file
     * @param num - number of line in csv file (needed to specify the place of error in input file)
     * @return worker class instance
     * @throws IncorrectFileException - if number of fields in line doesn't match number of fields in worker class
     * @throws NullFieldException if field which shouldn't be null is null
     * @throws IncorrectValueException if value is unable to be applied for field
     */
    public Worker getFromCSV(String line, int num) throws IncorrectFileException, NullFieldException, IncorrectValueException {
        int id;
        String name;
        Coordinates coordinates;
        long salary;
        ZonedDateTime startDate;
        LocalDate endDate;
        Status status;
        double height;
        Color eyeColor;
        Color hairColor;
        Country nationality;
        Date creationDate;
        Pattern namePattern = Pattern.compile("\"\\S.*\"");
        Matcher fieldMatcher = Pattern.compile("(,\".+\",)|([^,\"]+)").matcher(line);
        String[] fields = new String[13];
        int fieldsCounter=0;
        while (fieldMatcher.find()){
            if(fieldsCounter==13){
                throw new IncorrectFileException(num);
            }
            fields[fieldsCounter]=fieldMatcher.group();
            fieldsCounter++;
        }
        if(fieldsCounter<13){
            throw new IncorrectFileException(num);
        }
        try {
            id = Integer.parseInt(fields[0]);
        } catch (NumberFormatException e) {
            throw new IncorrectValueException("id", "unable to parse");
        }
        name = fields[1];
        Matcher nameMatcher = namePattern.matcher(name);
        if(nameMatcher.find()){
            name=nameMatcher.group();
            name=name.substring(1,name.length()-1);
        }
        else {
            name=null;
        }
        try {
            coordinates=new Coordinates(Long.parseLong(fields[2]), Long.parseLong(fields[3]));
        }catch (NumberFormatException e) {
            throw new IncorrectValueException("Coordinates", "unable to parse");
        }
        try{
            salary=Long.parseLong(fields[4]);
        }catch (NumberFormatException e) {
            throw new IncorrectValueException("Salary", "unable to parse");
        }
        try{
            startDate=ZonedDateTime.parse(fields[5]);
        }catch (DateTimeParseException e) {
            throw new IncorrectValueException("StartDate", "unable to parse");
        }
        try{
            endDate= LocalDate.parse(fields[6]);
        }catch (DateTimeParseException e) {
            if(fields[6].equals("null")){
                endDate=null;
            }
            else {
                throw new IncorrectValueException("StartDate", "unable to parse");
            }
        }
        try {
            status=Status.valueOf(fields[7]);
        } catch (IllegalArgumentException e){
            if(fields[7]==null) throw new IncorrectValueException("Status value is incorrect");
            else throw new IncorrectValueException("Status", "unable to parse");
        }
        try{
            height=Double.parseDouble(fields[8]);
        } catch (NumberFormatException e){throw new IncorrectValueException("Height", "unable to parse");}
        try{
            eyeColor=Color.valueOf(fields[9]);
        } catch (IllegalArgumentException e){
            if(fields[9].equals("null")) eyeColor=null;
            else throw new IncorrectValueException("eyeColor", "unable to parse");
        }
        try{
            hairColor=Color.valueOf(fields[10]);
        } catch (IllegalArgumentException e){
            if(fields[10].equals("null")) hairColor=null;
            else throw new IncorrectValueException("hairColor", "unable to parse");
        }
        try {
            nationality=Country.valueOf(fields[11]);
        } catch (IllegalArgumentException e){
            if(fields[11].equals("null")) throw new IncorrectValueException("Nationality value is incorrect");
            else throw new IncorrectValueException("Nationality", "unable to parse");
        }
        try{
            creationDate= new Date(Long.parseLong(fields[12]));
        }catch (NumberFormatException e) {
            throw new IncorrectValueException("CreationDate", "unable to parse");
        }
        return createWorkerWithIdAndDate(id,name,coordinates,salary,startDate,endDate,status,height,eyeColor,hairColor, nationality,creationDate);
    }

    /**
     * Set's new start point for id counter
     * @param newId - start point for id counter. It is being changed after file reading - to make id's unique.
     */
    public void setStartId(int newId){
        id=newId;
    }

    public Scanner getScanner(){
        return scanner;
    }
}