package gui;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class Controller {
    protected Application app;
    protected Stage stage;
    protected FXMLLoader loader;

    public abstract void launch() throws IOException;

    public abstract void shutdown();
}
