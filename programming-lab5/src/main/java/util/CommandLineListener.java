package util;

import command.Invoker;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is responsible for listening command line? separating command name and arguments and invoker calling
 */
public class CommandLineListener {
    private final Scanner scanner;
    private final Invoker invoker;
    private final Pattern commandNamePattern;
    private final Pattern argsPattern;

    /**
     * @param scanner - scanner, source of commands (usually System.in or File reader connected to script
     * @param invoker - invoker which will execute received commands
     */
    public CommandLineListener(Scanner scanner, Invoker invoker){
        this.scanner = scanner;
        this.invoker = invoker;
        commandNamePattern = Pattern.compile("^\\w+");
        argsPattern = Pattern.compile("\\b(.*\\s*)*");
    }

    /**
     * starts reading loop
     * this loop reads commands ad calls invoker
     * loop is finished if input is empty or exit command is activated
     */
    public void startRead(){
        String line;
        String command;
        String args;
        while(!invoker.isStopRequested()) {
            try {
                line = scanner.nextLine();
            }
            catch (NoSuchElementException e){
                break;
            }
            Matcher matcher = commandNamePattern.matcher(line);
            if(matcher.find()) {
                command = matcher.group();
            } else{
                System.out.println("Your input is not a command");
                continue;
            }
            line=line.substring(command.length());
            matcher = argsPattern.matcher(line);
            if(matcher.find()){
                args = matcher.group();
            }
            else{
                args = "";
            }
            invoker.execute(command, args);
        }
    }
}