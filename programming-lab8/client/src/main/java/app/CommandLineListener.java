package app;

import data.CommandToSend;
import network.CommandSender;
import network.CommandToSendFormer;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is responsible for listening command line? separating command name and arguments and sending command
 */
public class CommandLineListener {
    private final Scanner scanner;
    private final Pattern commandNamePattern;
    private final Pattern argsPattern;
    private final CommandToSendFormer former;
    private final CommandSender sender;
    private final ExecuteScript executor;

    /**
     * @param scanner - scanner, source of commands (usually System.in or File reader connected to script
     */
    public CommandLineListener(Scanner scanner,
                               CommandToSendFormer former,
                               CommandSender commandSender,
                               ExecuteScript executeScript) {
        this.former = former;
        this.scanner = scanner;
        sender = commandSender;
        commandNamePattern = Pattern.compile("^\\w+");
        argsPattern = Pattern.compile("\\b(.*\\s*)*");
        executor = executeScript;
    }

    /**
     * starts reading loop
     * this loop reads commands ad calls invoker
     * loop is finished if input is empty or exit command is activated
     */
    public void startRead(Set<String> availableCommands) throws IOException {
        String line;
        String command;
        String args;
        while (true) {
            try {
                line = scanner.nextLine();
            } catch (NoSuchElementException e) {
                break;
            }
            Matcher matcher = commandNamePattern.matcher(line);
            if (matcher.find()) {
                command = matcher.group();
            } else {
                System.out.println("Your input is not a command");
                continue;
            }
            line = line.substring(command.length());
            matcher = argsPattern.matcher(line);
            if (matcher.find()) {
                args = matcher.group();
            } else {
                args = "";
            }
            if (!command.equals("exit") && availableCommands.contains(command)) {
                CommandToSend commandToSend = former.getCommandToSend(command, args);
                sender.sendCommand(commandToSend);
            } else {
                if (command.equals("exit")) {
                    break;
                }
                if (command.equals("execute_script")) {
                    executor.execute(args);
                } else {
                    System.out.println("Wrong command");
                }
            }
        }
    }
}