package data;

public class IncorrectValueException extends Exception{
    public IncorrectValueException(String fieldName){
        super(fieldName+" can't be null");
    }
    public IncorrectValueException(String fieldName, String message){
        super(fieldName+" value is incorrect. "+message);
    }
}
