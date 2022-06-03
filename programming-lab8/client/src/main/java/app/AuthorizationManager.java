package app;

import data.CommandToSend;
import network.CommandSender;
import network.Receiver;
import network.Session;

import java.io.IOException;

public class AuthorizationManager {
    public static AuthorizationStatus authorizeUser(String login, String password,
                                                    OperationCode opCode, CommandSender sender,
                                                    Receiver receiver) throws IOException {
        while (receiver.confirmAuthorization() == null || Session.getInstance() == null) {
            receiver.resetAuthorization();
            if (opCode == OperationCode.REGISTRATION) {
                sender.sendCommand(new CommandToSend("register",
                        login, login, password));
                while (receiver.confirmAuthorization() == null) ;
                if (!receiver.confirmAuthorization()) {
                    return AuthorizationStatus.BAD_LOGIN;
                } else {
                    Session.createInstance(login, password);
                    return AuthorizationStatus.SUCCESS;
                }
            }
            if (opCode == OperationCode.AUTHORIZATION) {
                sender.sendCommand(new CommandToSend("login",
                        login, login, password));
                while (receiver.confirmAuthorization() == null) ;
                if (receiver.confirmAuthorization()) {
                    Session.createInstance(login, password);
                    return AuthorizationStatus.SUCCESS;
                } else {
                    return AuthorizationStatus.UNKNOWN_USER;
                }
            }
        }
        return AuthorizationStatus.SUCCESS;
    }
}
