package util;

public class IncorrectFileException extends Exception{
    public IncorrectFileException(int num){
        super("Number on fields is incorrect in line "+num);
    }
}
