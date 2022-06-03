package gui;

import data.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import workersOperations.WorkerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WorkerInputController extends AddController {
    private final DeleteController deleteController;
    @FXML
    private Button addButton;
    @FXML
    private Button addIfMaxButton;
    @FXML
    private Button addIfMinButton;
    public WorkerInputController(Application application, WorkerFactory workerFactory, DeleteController deleteController) {
        super(application, workerFactory, null);
        stage = new Stage();
        this.deleteController = deleteController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
    }

    @Override
    public void launch() throws IOException {
        preLaunch(this);
        addIfMaxButton.setVisible(false);
        addIfMinButton.setVisible(false);
        stage.show();
    }

    @FXML
    private void add() {
        Worker w = readWorker();
        if (w == null) {
            return;
        }
        stage.close();
        deleteController.putWorkerToCompare(w);
    }
}
