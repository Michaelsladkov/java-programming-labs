package util;

import data.Color;
import data.Country;
import data.Status;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class is responsible for input values from file or console
 */
public class FieldsReader {
    private Scanner scanner;

    /**
     * @param scanner scanner to be used as input
     */
    public FieldsReader(Scanner scanner){
        this.scanner = scanner;
    }

    /**
     * @param newScanner scanner to be used as input
     */
    public void setScanner(Scanner newScanner){
        scanner=newScanner;
    }

    /**
     * Reads and checks name value
     * @return allowed name
     */
    public String readName() throws NoSuchElementException{
        String name = null;
        while (name == null){
            System.out.print("Enter worker's name: ");
            name = readLine();
            if(name == null){
                System.out.println("your input doesn't contain name, try again");
                continue;
            }
        }
        return name;
    }

    /**
     * @param axis x or y
     * @return coordinates instance
     */
    public Long readCoordinate(char axis) throws NoSuchElementException{
        Long coordinate = null;
        while (coordinate == null){
            System.out.print("Enter "+axis+" coordinate: ");
            try {
                coordinate = Long.parseLong(readLine());
            }
            catch (NumberFormatException e){
                System.out.println("your input doesn't contain coordinate, try again");
                coordinate = null;
            }
        }
        return coordinate;
    }

    /**
     * Reads and checks salary value
     * @return checked salary
     */
    public Long readSalary() throws NoSuchElementException{
        Long salary = null;
        while (salary == null){
            System.out.print("Enter salary: ");
            try {
                salary = Long.parseLong(readLine());
                if(salary<=0){
                    System.out.println("Salary should be more than 0");
                    salary=null;
                }
            }
            catch (NumberFormatException e){
                System.out.println("your input doesn't contain salary, try again");
                salary = null;
            }
        }
        return salary;
    }

    /**
     * reads line from input
     * @return null or not empty line
     */
    public String readLine() throws NoSuchElementException{
        String line;
        line = scanner.nextLine();
        if(line.length()==0) line = null;
        return line;
    }

    /**
     * Reads and checks start date value
     * @return checked start date
     */
    public ZonedDateTime readStartDate() throws NoSuchElementException{
        ZonedDateTime startDate = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm:ss z");
        while (startDate==null){
            System.out.print("Enter start date: ");
            try {
                startDate = ZonedDateTime.parse(readLine(), formatter);
            }
            catch (NullPointerException e){
                System.out.println("your input doesn't contain date, try again");
                startDate = null;
            }
            catch (DateTimeParseException e)
            {
                System.out.println("Your format is incorrect. Use yyyy-mm-dd hh:mm:ss +/-hh:mm");
            }
        }
        return  startDate;
    }

    /**
     * Reads and checks end date value
     * @return checked end date
     */
    public LocalDate readEndDate() throws NoSuchElementException{
        LocalDate endDate = null;
        while (endDate == null){
            System.out.print("Enter end date: ");
            try {
                endDate = LocalDate.parse(readLine());
            }
            catch (NullPointerException e){
                break;
            }
            catch (DateTimeParseException e)
            {
                System.out.println("Your format is incorrect. Use yyyy-mm-dd");
            }
        }
        return endDate;
    }

    /**
     * Reads and checks color value
     * @param fieldName eyeColor or hairColor
     * @return checked color
     */
    public Color readColor(String fieldName) throws NoSuchElementException{
        Color color = null;
        while(color == null){
            System.out.println("Enter one of following values for "+fieldName+" : ");
            for (Color declaredColor : Color.values()) {
                System.out.println(declaredColor.toString());
            }
            try {
                color = Color.valueOf(readLine().toUpperCase());
            }
            catch (IllegalArgumentException e) {
                System.out.println("Your input doesn't contain any of possible states. Try again");
                color = null;
            }
            catch (NullPointerException e){
                break;
            }
        }
        return color;
    }

    /**
     * Reads and checks status value
     * @return checked status
     */
    public Status readStatus() throws NoSuchElementException{
        Status status = null;
        while (status==null){
            System.out.println("Enter one of following states: ");
            for(Status declaredStatus:Status.values()){
                System.out.println(declaredStatus.toString());
            }
            try {
                status = Status.valueOf(readLine().toUpperCase());
            }
            catch (IllegalArgumentException| NullPointerException e){
                System.out.println("Your input doesn't contain any of possible states. Try again");
                status = null;
            }
        }
        return status;
    }

    /**
     * Reads and checks height value
     * @return checked height
     */
    public Double readHeight() throws NoSuchElementException{
        Double height = null;
        while (height == null)
        {
            System.out.print("Enter height: ");
            try {
                height = Double.parseDouble(readLine());
            }
            catch (NullPointerException e){
                System.out.println("your input doesn't contain height, try again");
                height = null;
            }
            catch (NumberFormatException e){
                System.out.println("You have used an incorrect number format");
            }
            if(height!=null) if(height<=0){
                System.out.println("Height should be more than 0");
                height = null;
            }
        }
        return height;
    }

    /**
     * Reads and checks nationality value
     * @return checked country
     */
    public Country readNationality() throws NoSuchElementException{
        Country nationality = null;
        while (nationality == null){
            System.out.println("Enter one of following countries: ");
            for(Country declaredCountry:Country.values()){
                System.out.println(declaredCountry.toString());
            }
            try {
                nationality = Country.valueOf(readLine().toUpperCase());
            }
            catch (IllegalArgumentException | NullPointerException e){
                System.out.println("Your input doesn't contain any of possible countries. Try again");
                nationality = null;
            }
        }
        return nationality;
    }
}