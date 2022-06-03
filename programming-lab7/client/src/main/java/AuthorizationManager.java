import data.CommandToSend;
import network.CommandSender;
import network.Receiver;
import network.Session;

import java.util.Scanner;
public class AuthorizationManager {
    public static void authorizeUser(Scanner scanner, CommandSender sender, Receiver receiver) {
        while (receiver.confirmAuthorization() == null || Session.getInstance() == null) {
            receiver.resetAuthorization();
            String line = "";
            while (line.isEmpty() || (!line.equals("n") && !line.equals("a") && !(line.charAt(0) == 'e'))) {
                System.out.println("Are you new user or already smesharik? (n/a/e[xit])");
                line = scanner.nextLine();
            }
            if (line.charAt(0) == 'e') {
                System.exit(0);
            }
            String login = "";
            while (login.isEmpty()) {
                System.out.println("Enter login:");
                login = scanner.nextLine();
            }
            System.out.println("Enter password:");
            String password = "";
            password = scanner.nextLine();
            if (line.equals("n")) {
                sender.sendCommand(new CommandToSend("register",
                        login, login, password));
                while (receiver.confirmAuthorization() == null) ;
                if (!receiver.confirmAuthorization()) {
                    System.out.println("Try to use some other login");
                } else {
                    Session.createInstance(login, password);
                }
            }
            if (line.equals("a")) {
                sender.sendCommand(new CommandToSend("login",
                        login, login, password));
                while (receiver.confirmAuthorization() == null) ;
                if (receiver.confirmAuthorization()) {
                    Session.createInstance(login, password);
                } else {
                    System.out.println("We don't know this combination of login and password");
                }
            }
        }
    }
}
