package gui;

import data.CommandToSend;
import data.Person;
import data.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import network.CommandSender;
import network.CommandToSendFormer;
import workersOperations.WorkerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateController extends AddController {
    private Worker toUpdate;
    @FXML
    private Button addButton;
    @FXML
    private Button addIfMaxButton;
    @FXML
    private Button addIfMinButton;
    public UpdateController(Application application, WorkerFactory workerFactory, CommandSender commandSender) {
        super(application, workerFactory, commandSender);
        stage = new Stage();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
    }

    public void launch(Worker toUpdate) throws IOException {
        this.toUpdate = toUpdate;
        preLaunch(this);
        nameField.setText(toUpdate.getName());
        coordinatesXField.setText(String.valueOf(toUpdate.getCoordinates().getX()));
        coordinatesYField.setText(String.valueOf(toUpdate.getCoordinates().getY()));
        salaryField.setText(String.valueOf(toUpdate.getSalary()));
        startDatePicker.setValue(toUpdate.getStartDate().toLocalDate());
        if (toUpdate.getEndDate() != null) {
            endDatePicker.setValue(toUpdate.getEndDate());
        }
        statusPicker.setValue(toUpdate.getStatus());
        Person personToUpdate = toUpdate.getPerson();
        heightField.setText(String.valueOf(personToUpdate.getHeight()));
        eyeColorPicker.setValue(personToUpdate.getEyeColor());
        hairColorPicker.setValue(personToUpdate.getHairColor());
        nationalityPicker.setValue(personToUpdate.getNationality());
        addButton.setText(labels.getString("update"));
        addIfMaxButton.setVisible(false);
        addIfMinButton.setVisible(false);
        stage.show();
    }

    @FXML
    private void add() throws IOException {
        Worker w = readWorker();
        if (w == null) {
            return;
        }
        CommandToSend command = CommandToSendFormer.getInstance().
                getCommandToSend("update", String.valueOf(toUpdate.getId()), w);
        sender.sendCommand(command);
        stage.close();
    }

    public void shutdown() {
        stage.close();
    }
}