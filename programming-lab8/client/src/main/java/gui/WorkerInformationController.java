package gui;

import data.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import workersOperations.WorkerDecoder;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WorkerInformationController implements Initializable {
    private ResourceBundle titles;
    private final Application app;
    private Stage stage;
    @FXML
    private Label main;

    public WorkerInformationController(Application application) {
        app = application;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titles = ResourceBundle.getBundle("bundles.Titles", app.getLocale());
    }

    public void launch(Worker w, double x, double y) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Application.class.getResource("/gui/WorkerInformation.fxml"));
        loader.setController(this);
        Scene scene = new Scene(loader.load());
        stage = new Stage();
        stage.setTitle(titles.getString("info"));
        WorkerDecoder decoder = new WorkerDecoder(app.getLocale());
        main.setText(decoder.describeWorker(w));
        stage.setX(x);
        stage.setY(y - 80);
        stage.setMinWidth(380);
        stage.setMinHeight(380);
        stage.setScene(scene);
        stage.show();
    }

    public void shutdown() {
        if (stage != null) {
            stage.close();
        }
    }
}