package data;

public class NullFieldException extends Exception{
    public NullFieldException(String fieldName){
        super(fieldName+" can't be null");
    }
}
