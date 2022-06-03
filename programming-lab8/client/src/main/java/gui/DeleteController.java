package gui;

import data.CommandToSend;
import data.Status;
import data.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import network.CommandSender;
import network.CommandToSendFormer;
import network.Session;
import workersOperations.WorkerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static gui.DeleteController.DeleteOperationType.*;

public class DeleteController extends Controller implements Initializable {
    private final CommandSender sender;
    private ResourceBundle errors;
    private Worker toDelete = null;
    private Worker toCompare = null;
    private DeleteOperationType type;
    private final WorkerInputController workerInputController;
    @FXML
    private Label errorsLabel;
    @FXML
    private ChoiceBox<Status> statusPicker;
    @FXML
    private TextField idField;
    @FXML
    private RadioButton byIdButton;
    @FXML
    private RadioButton byStatusButton;
    @FXML
    private RadioButton lessThenButton;
    @FXML
    private RadioButton allButton;
    public DeleteController(Application application, WorkerFactory workerFactory, CommandSender commandSender) {
        app = application;
        sender = commandSender;
        stage = new Stage();
        workerInputController = new WorkerInputController(application, workerFactory, this);
    }

    @Override
    public void launch() throws IOException {
        errors = ResourceBundle.getBundle("bundles.Errors", app.getLocale());
        ResourceBundle titles = ResourceBundle.getBundle("bundles.Titles", app.getLocale());
        ResourceBundle labels = ResourceBundle.getBundle("bundles.DeleteLabels", app.getLocale());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Application.class.getResource("/gui/Deletion.fxml"));
        loader.setResources(labels);
        loader.setController(this);
        stage.setTitle(titles.getString("delete"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        if (toDelete != null) {
            idField.setText(String.valueOf(toDelete.getId()));
            byIdPicked();
        }
        stage.show();
    }

    public void launch(Worker worker) throws IOException {
        toDelete = worker;
        launch();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stage.setMinHeight(300);
        stage.setMinWidth(335);
        for (Status s : Status.values()) {
            statusPicker.getItems().add(s);
        }
    }

    @FXML
    private void byIdPicked() {
        byIdButton.setSelected(true);
        byStatusButton.setSelected(false);
        allButton.setSelected(false);
        lessThenButton.setSelected(false);
        checkAndGetId();
        type = BY_ID;
    }

    @FXML
    private void byStatusPicked() {
        allButton.setSelected(false);
        lessThenButton.setSelected(false);
        byIdButton.setSelected(false);
        type = BY_STATUS;
    }

    @FXML
    private void lessThenPicked() throws IOException {
        allButton.setSelected(false);
        byStatusButton.setSelected(false);
        byIdButton.setSelected(false);
        type = LESS_THEN;
        workerInputController.launch();
    }

    @FXML
    private void allPicked() {
        lessThenButton.setSelected(false);
        byIdButton.setSelected(false);
        byStatusButton.setSelected(false);
        type = CLEAR;
    }

    @FXML
    private void delete() throws IOException {
        CommandToSend command;
        switch (type) {
            case BY_ID:
                if (checkAndGetId() == null) {
                    break;
                }
                if (!checkPermission()) {
                    errorsLabel.setText(errors.getString("PERMISSION_DENIED"));
                    break;
                }
                command = CommandToSendFormer.getInstance().getCommandToSend("remove_by_id", String.valueOf(checkAndGetId()));
                sender.sendCommand(command);
                stage.close();
                break;
            case CLEAR:
                command = CommandToSendFormer.getInstance().getCommandToSend("clear", null);
                sender.sendCommand(command);
                stage.close();
                break;
            case BY_STATUS:
                if (statusPicker.getValue() == null) {
                    errorsLabel.setText("YOU_SHOULD_CHOOSE_ANY_STATUS");
                    break;
                }
                command = CommandToSendFormer.getInstance().getCommandToSend("remove_all_by_status", statusPicker.getValue().toString());
                sender.sendCommand(command);
                stage.close();
                break;
            case LESS_THEN:
                if (toCompare != null) {
                    command = CommandToSendFormer.getInstance().getCommandToSend("remove_lower", null, toCompare);
                    sender.sendCommand(command);
                    stage.close();
                    toCompare = null;
                } else {
                    errorsLabel.setText(errors.getString("YOU_SHOULD_ENTER_WORKER_FIRST"));
                }
                break;
        }
    }

    private boolean checkPermission() {
        if (toDelete != null && toDelete.getId() == checkAndGetId()) {
            return toDelete.getCreator().equals(Session.getInstance().getUserName());
        }
        return true;
    }

    private Integer checkAndGetId() {
        try {
            if (idField.getText() != null) {
                return Integer.parseInt(idField.getText());
            }
        } catch (NumberFormatException e) {
            errorsLabel.setText(errors.getString("INCORRECT_ID_FORMAT"));
        }
        return null;
    }

    @FXML
    private void clearErrors() {
        errorsLabel.setText("");
    }

    public void putWorkerToCompare(Worker worker) {
        toCompare = worker;
    }

    @Override
    public void shutdown() {
        stage.close();
    }

    enum DeleteOperationType {
        CLEAR,
        BY_ID,
        BY_STATUS,
        LESS_THEN
    }
}