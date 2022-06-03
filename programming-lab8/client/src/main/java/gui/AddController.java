package gui;

import data.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import network.CommandSender;
import network.CommandToSendFormer;
import workersOperations.WorkerFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class AddController extends Controller implements Initializable {
    protected ResourceBundle errors;
    protected ResourceBundle labels;
    protected final WorkerFactory factory;
    protected final CommandSender sender;
    @FXML
    protected Label errorsLabel;
    @FXML
    protected TextField nameField;
    @FXML
    protected TextField coordinatesXField;
    @FXML
    protected TextField coordinatesYField;
    @FXML
    protected TextField salaryField;
    @FXML
    protected DatePicker startDatePicker;
    @FXML
    protected DatePicker endDatePicker;
    @FXML
    protected ChoiceBox<Status> statusPicker;
    @FXML
    protected TextField heightField;
    @FXML
    protected ChoiceBox<Color> eyeColorPicker;
    @FXML
    protected ChoiceBox<Color> hairColorPicker;
    @FXML
    protected ChoiceBox<Country> nationalityPicker;

    public AddController(Application application, WorkerFactory workerFactory, CommandSender commandSender) {
        app = application;
        factory = workerFactory;
        sender = commandSender;
        stage = new Stage();
    }

    public void shutdown() {
        stage.close();
    }

    @FXML
    protected void checkXCoordinate() {
        try {
            Integer.parseInt(coordinatesXField.getText());
        } catch (NumberFormatException | NullPointerException e) {
            errorsLabel.setText(errors.getString("INCORRECT_COORDINATES_FORMAT"));
        }
    }

    @FXML
    protected void checkYCoordinate() {
        try {
            Integer.parseInt(coordinatesYField.getText());
        } catch (NumberFormatException | NullPointerException e) {
            errorsLabel.setText(errors.getString("INCORRECT_COORDINATES_FORMAT"));
        }
    }

    @FXML
    protected void checkSalary() {
        try {
            Long.parseLong(salaryField.getText());
        } catch (NumberFormatException | NullPointerException e) {
            errorsLabel.setText(errors.getString("INCORRECT_SALARY_FORMAT"));
        }
    }

    @FXML
    protected void checkHeight() {
        try {
            Double.parseDouble(heightField.getText());
        } catch (NumberFormatException | NullPointerException e) {
            errorsLabel.setText(errors.getString("INCORRECT_HEIGHT_FORMAT"));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stage.setMinWidth(450);
        stage.setMinHeight(460);
        for (Status s : Status.values()) {
            statusPicker.getItems().add(s);
        }
        for (Color c : Color.values()) {
            eyeColorPicker.getItems().add(c);
            hairColorPicker.getItems().add(c);
        }
        for (Country c : Country.values()) {
            nationalityPicker.getItems().add(c);
        }
    }

    @Override
    public void launch() throws IOException {
        preLaunch(this);
        stage.show();
    }

    protected void preLaunch(Controller controller) throws IOException {
        loader = new FXMLLoader();
        loader.setLocation(Application.class.getResource("/gui/Addition.fxml"));
        loader.setResources(ResourceBundle.getBundle("bundles.MainLabels", app.getLocale()));
        loader.setController(controller);
        errors = ResourceBundle.getBundle("bundles.Errors", app.getLocale());
        labels = ResourceBundle.getBundle("bundles.MainLabels", app.getLocale());
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle(ResourceBundle.getBundle("bundles.Titles", app.getLocale()).getString("addition"));
        System.out.println(app.getLocale());
    }

    @FXML
    private void add() throws IOException {
        Worker w = readWorker();
        if (w == null) {
            return;
        }
        CommandToSend command = CommandToSendFormer.getInstance().getCommandToSend("add", null, w);
        sender.sendCommand(command);
        stage.close();
    }

    @FXML
    private void addIfMax() throws IOException {
        Worker w = readWorker();
        if (w == null) {
            return;
        }
        CommandToSend command = CommandToSendFormer.getInstance().getCommandToSend("add_if_max", null, w);
        sender.sendCommand(command);
        stage.close();
    }

    @FXML
    private void addIfMin() throws IOException {
        Worker w = readWorker();
        if (w == null) {
            return;
        }
        CommandToSend command = CommandToSendFormer.getInstance().getCommandToSend("add_if_min", null, w);
        sender.sendCommand(command);
        stage.close();
    }

    @FXML
    protected void clearErrors() {
        errorsLabel.setText("");
    }

    protected Worker readWorker() {
        try {
            if (nameField.getText() == null) {
                errorsLabel.setText(errors.getString("FIELD") + " " + labels.getString("name") + " " +
                        errors.getString("CAN'T_BE_NULL"));
                return null;
            }
            if (statusPicker.getValue() == null) {
                errorsLabel.setText(errors.getString("FIELD") + " " + labels.getString("status") + " " +
                        errors.getString("CAN'T_BE_NULL"));
                return null;
            }
            if (startDatePicker.getValue() == null) {
                errorsLabel.setText(errors.getString("FIELD") + " " + labels.getString("start_date") + " " +
                        errors.getString("CAN'T_BE_NULL"));
                return null;
            }
            if (nationalityPicker.getValue() == null) {
                errorsLabel.setText(errors.getString("FIELD") + " " + labels.getString("nationality") + " " +
                        errors.getString("CAN'T_BE_NULL"));
                return null;
            }
            checkHeight();
            checkSalary();
            checkXCoordinate();
            checkYCoordinate();
            LocalDate endDate = endDatePicker.getValue();
            return factory.createWorker(nameField.getText(),
                    new Coordinates(Long.parseLong(coordinatesXField.getText()), Long.parseLong(coordinatesYField.getText())),
                    Long.parseLong(salaryField.getText()),
                    startDatePicker.getValue().atStartOfDay(TimeZone.getDefault().toZoneId()),
                    endDate != null ? endDate.atStartOfDay().toLocalDate() : null,
                    statusPicker.getValue(),
                    Double.parseDouble(heightField.getText()),
                    eyeColorPicker.getValue(),
                    hairColorPicker.getValue(),
                    nationalityPicker.getValue());
        } catch (NullFieldException | IncorrectValueException e) {
            errorsLabel.setText(e.getMessage());
        }
        return null;
    }
}