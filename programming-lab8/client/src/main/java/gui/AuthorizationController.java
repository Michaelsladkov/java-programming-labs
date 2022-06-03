package gui;

import app.AuthorizationManager;
import app.AuthorizationStatus;
import app.OperationCode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import network.CommandSender;
import network.Receiver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.UnresolvedAddressException;
import java.util.Locale;
import java.util.ResourceBundle;

import static app.AuthorizationStatus.*;
import static app.OperationCode.AUTHORIZATION;
import static app.OperationCode.REGISTRATION;

public class AuthorizationController extends Controller implements ControllerWithMessages {
    private final Receiver receiver;
    private final CommandSender commandSender;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField hostField;
    private ResourceBundle errors;

    public AuthorizationController(Application application, Stage primaryStage,
                                   Receiver receiver, CommandSender sender) {
        app = application;
        stage = primaryStage;
        DatagramChannel channel = receiver.getDatagramChannel();
        this.receiver = receiver;
        commandSender = sender;
    }

    @FXML
    private void englishPick() {
        app.setLocale(Locale.ENGLISH);
        app.showAuthorizationScene();
    }

    @FXML
    private void russianPick() {
        app.setLocale(new Locale("ru"));
        app.showAuthorizationScene();
    }

    @FXML
    private void croatianPick() {
        app.setLocale(new Locale("cr"));
        app.showAuthorizationScene();
    }

    @FXML
    private void spanishPick() {
        app.setLocale(new Locale("sp"));
        app.showAuthorizationScene();
    }

    @FXML
    private void netherlandsPick() {
        app.setLocale(new Locale("ne"));
        app.showAuthorizationScene();
    }

    @FXML
    private void resetErrors() {
        errorLabel.setText("");
    }

    @FXML
    private void login() {
        authorize(AUTHORIZATION);
    }

    private void authorize(OperationCode opCode) {
        int port;
        String host = hostField.getText();
        if (!host.contains(":")) {
            errorLabel.setText(errors.getString("INCORRECT_HOST"));
            return;
        } else {
            try {
                port = Integer.parseInt(host.split(":")[1]);
            } catch (NumberFormatException e) {
                errorLabel.setText(errors.getString("INCORRECT_HOST"));
                return;
            }
        }
        SocketAddress address = new InetSocketAddress(host.split(":")[0], port);
        commandSender.setServerSocketAddress(address);
        String login = loginField.getText();
        String password = passwordField.getText();
        AuthorizationStatus status;
        try {
            status = AuthorizationManager.authorizeUser(login, password, opCode, commandSender, receiver);
        } catch (UnresolvedAddressException e) {
            errorLabel.setText(errors.getString("INCORRECT_HOST"));
            return;
        } catch (IOException e) {
            errorLabel.setText(errors.getString("FAILED_TO_CONNECT"));
            return;
        }
        if (status == SUCCESS) {
            stage.close();
            app.showMainScene();
        }
        if (status == UNKNOWN_USER) {
            errorLabel.setText(errors.getString("UNKNOWN_USER"));
        }
        if (status == BAD_LOGIN) {
            errorLabel.setText(errors.getString("BAD_LOGIN"));
        }
    }

    @FXML
    private void register() {
        authorize(REGISTRATION);
    }

    @Override
    public void launch() throws IOException {
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        loader = new FXMLLoader();
        loader.setLocation(Application.class.getResource("/gui/Authorization.fxml"));
        loader.setResources(ResourceBundle.getBundle("bundles.AuthorizationLabels", app.getLocale()));
        loader.setController(this);
        errors = ResourceBundle.getBundle("bundles.Errors", app.getLocale());
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        System.out.println(app.getLocale());
        stage.show();
    }

    @Override
    public void shutdown() {
        stage.close();
    }

    @Override
    public void sendMessage(String message) {
        System.out.println(message);
    }
}
