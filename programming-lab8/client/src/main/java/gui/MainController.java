package gui;

import app.CommandsSet;
import app.ExecuteScript;
import bundles.Titles_en;
import data.*;
import gui.graphics.DrawingCoordinates;
import gui.graphics.Graphics;
import javafx.beans.value.ObservableValueBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import network.CommandSender;
import network.CommandToSendFormer;
import network.Session;
import workersOperations.FieldsReader;
import workersOperations.Filter;
import workersOperations.Storage;
import workersOperations.WorkerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.ClosedChannelException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainController extends Controller implements Initializable, ControllerWithMessages {
    private final CommandSender commandSender;
    private final WorkerInformationController workerInformationController;
    @FXML
    public TableColumn<Worker, Integer> id;
    @FXML
    public TableColumn<Worker, String> name;
    @FXML
    public TableColumn<Worker, String> coordinates;
    @FXML
    public TableColumn<Worker, String> created;
    @FXML
    public TableColumn<Worker, Long> salary;
    @FXML
    public TableColumn<Worker, String> startDate;
    @FXML
    public TableColumn<Worker, String> endDate;
    @FXML
    public TableColumn<Worker, Status> status;
    @FXML
    public TableColumn<Worker, Double> height;
    @FXML
    public TableColumn<Worker, Color> eyeColor;
    @FXML
    public TableColumn<Worker, Color> hairColor;
    @FXML
    public TableColumn<Worker, Country> nationality;
    @FXML
    public TableColumn<Worker, String> creator;
    @FXML
    public TextField nameFilter;
    @FXML
    public TextField coordinatesFilter;
    @FXML
    public DatePicker startDateFilterFrom;
    @FXML
    public DatePicker startDateFilterTill;
    @FXML
    public TextField salaryFilterFrom;
    @FXML
    public TextField salaryFilterTo;
    @FXML
    public DatePicker creationDateFilterFrom;
    @FXML
    public DatePicker creationDateFilterTill;
    @FXML
    public DatePicker endDateFilterFrom;
    @FXML
    public DatePicker endDateFilterTill;
    @FXML
    public ChoiceBox<Status> statusFilter;
    @FXML
    public TextField heightFilterFrom;
    @FXML
    public TextField heightFilterTo;
    @FXML
    public ChoiceBox<Color> eyeColorFilter;
    @FXML
    public ChoiceBox<Color> hairColorFilter;
    @FXML
    public ChoiceBox<Country> nationalityFilter;
    private ResourceBundle formats;
    private ResourceBundle errors;
    private Worker pickedWorker;
    private ExecutorService executor;
    private ExecuteScript scriptExecutor;
    private Updater updater;
    private Fetcher fetcher;
    private final Filter filter;
    @FXML
    private Label username;
    @FXML
    private TextArea textArea;
    @FXML
    private TableView<Worker> mainTable;
    @FXML
    private Canvas canvas;
    public MainController(Application application, Stage primaryStage, CommandSender sender) {
        app = application;
        stage = primaryStage;
        commandSender = sender;
        workerInformationController = new WorkerInformationController(application);
        filter = new Filter(this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pickedWorker = null;
        scriptExecutor = new ExecuteScript(CommandToSendFormer.getInstance(),
                commandSender, new WorkerFactory(1, new FieldsReader(new Scanner(System.in))), CommandsSet.getCommands());
        username.setText(Session.getInstance().getUserName());
        errors = ResourceBundle.getBundle("bundles.Errors", app.getLocale());
        formats = ResourceBundle.getBundle("bundles.Formats", app.getLocale());
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        coordinates.setCellValueFactory(param -> new ObservableValueBase<String>() {
            @Override
            public String getValue() {
                return param.getValue().getCoordinates().toString();
            }
        });
        created.setCellValueFactory(param -> new ObservableValueBase<String>() {
            @Override
            public String getValue() {
                Date creationDate = param.getValue().getCreationDate();
                if (creationDate instanceof java.sql.Date) {
                    return ((java.sql.Date) creationDate).toLocalDate().format((DateTimeFormatter) formats.getObject("date"));
                } else {
                    ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(creationDate.toInstant(), ZoneId.systemDefault());
                    return zonedDateTime.format((DateTimeFormatter) formats.getObject("date"));
                }
            }
        });
        salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        startDate.setCellValueFactory(param -> new ObservableValueBase<String>() {
            @Override
            public String getValue() {
                return param.getValue().getStartDate().format((DateTimeFormatter) formats.getObject("date_time"));
            }
        });
        endDate.setCellValueFactory(param -> new ObservableValueBase<String>() {
            @Override
            public String getValue() {
                LocalDate date = param.getValue().getEndDate();
                return date == null ? "null" : date.format((DateTimeFormatter) formats.getObject("date"));
            }
        });
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        height.setCellValueFactory(param -> new ObservableValueBase<Double>() {
            @Override
            public Double getValue() {
                return param.getValue().getPerson().getHeight();
            }
        });
        eyeColor.setCellValueFactory(param -> new ObservableValueBase<Color>() {
            @Override
            public Color getValue() {
                return param.getValue().getPerson().getEyeColor();
            }
        });
        hairColor.setCellValueFactory(param -> new ObservableValueBase<Color>() {
            @Override
            public Color getValue() {
                return param.getValue().getPerson().getHairColor();
            }
        });
        nationality.setCellValueFactory(param -> new ObservableValueBase<Country>() {
            @Override
            public Country getValue() {
                return param.getValue().getPerson().getNationality();
            }
        });
        creator.setCellValueFactory(new PropertyValueFactory<>("creator"));
        mainTable.setOnMousePressed(e -> {
            if (e.getClickCount() == 1 && e.isPrimaryButtonDown()) {
                pickedWorker = mainTable.getSelectionModel().getSelectedItem();
            }
        });
        canvas.setOnMousePressed(e -> {
            double x = e.getX();
            double y = e.getY();
            long m = Graphics.getMax();
            Optional<Worker> optionalWorker = Storage.getInstance().get().stream().
                    filter(w -> DrawingCoordinates.getDistance(DrawingCoordinates.getCoordinates(w, m, canvas), new DrawingCoordinates(x, y)) < 10).
                    findAny();
            if (optionalWorker.isPresent()) {
                pickedWorker = optionalWorker.get();
                try {
                    workerInformationController.launch(pickedWorker, x, y);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        executor = Executors.newCachedThreadPool();
        updater = new Updater();
        fetcher = new Fetcher();
        executor.execute(updater);
        executor.execute(fetcher);
        eyeColorFilter.getItems().addAll(Color.values());
        eyeColorFilter.getItems().add(null);
        hairColorFilter.getItems().addAll(Color.values());
        hairColorFilter.getItems().add(null);
        statusFilter.getItems().addAll(Status.values());
        statusFilter.getItems().add(null);
        nationalityFilter.getItems().addAll(Country.values());
        nationalityFilter.getItems().add(null);
    }

    public void shutdown() {
        if (updater != null && executor != null) {
            updater.shutdownRequested = true;
            fetcher.shutdownRequested = true;
            executor.shutdownNow();
            workerInformationController.shutdown();
        }
    }

    @FXML
    private void englishPick() {
        app.setLocale(Locale.ENGLISH);
        app.showMainScene();
        updater.updateRequested = true;
    }

    @FXML
    private void russianPick() {
        app.setLocale(new Locale("ru"));
        app.showMainScene();
        updater.updateRequested = true;
    }

    @FXML
    private void croatianPick() {
        app.setLocale(new Locale("cr"));
        app.showMainScene();
        updater.updateRequested = true;
    }

    @FXML
    private void spanishPick() {
        app.setLocale(new Locale("sp"));
        app.showMainScene();
        updater.updateRequested = true;
    }

    @FXML
    private void netherlandsPick() {
        app.setLocale(new Locale("ne"));
        app.showMainScene();
        updater.updateRequested = true;
    }

    @FXML
    private void info() {
        CommandToSend command = CommandToSendFormer.getInstance().getCommandToSend("info", null);
        try {
            commandSender.sendCommand(command);
        } catch (IOException e) {
            textArea.setText(e.getMessage());
        }
    }

    @FXML
    private void add() {
        app.showAddScene();
    }

    @FXML
    private void delete() {
        if (pickedWorker == null) {
            app.showDeleteScene();
        } else {
            app.showDeleteScene(pickedWorker);
            pickedWorker = null;
            workerInformationController.shutdown();
        }
    }

    @FXML
    private void descendingSalaries() {
        CommandToSend command = CommandToSendFormer.getInstance().getCommandToSend("print_field_descending_salary", null);
        try {
            commandSender.sendCommand(command);
        } catch (IOException e) {
            sendMessage(e.getMessage());
        }
    }

    @FXML
    private void update() {
        if (pickedWorker == null) {
            textArea.setText(errors.getString("PICK_WORKER_FIRST"));
        } else {
            if (pickedWorker.getCreator().equals(Session.getInstance().getUserName())) {
                app.showUpdateScene(pickedWorker);
                workerInformationController.shutdown();
            } else {
                textArea.setText(errors.getString("PERMISSION_DENIED"));
            }
        }
        pickedWorker = null;
    }

    @FXML
    private void checkConnection() {
        CommandToSend command = CommandToSendFormer.getInstance().getCommandToSend("ping", null);
        try {
            commandSender.sendCommand(command);
        } catch (IOException e) {
            sendMessage(e.getMessage());
        }
    }

    @FXML
    private void executeScript() throws IOException {
        Stage filePickStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle((new Titles_en()).getString("choose script"));
        File script = fileChooser.showOpenDialog(filePickStage);
        scriptExecutor.execute(script.getPath());
    }

    @Override
    public void sendMessage(String text) {
        textArea.clear();
        textArea.appendText(text);
    }

    @Override
    public void launch() throws IOException {
        stage.setMinHeight(640);
        stage.setMinWidth(1200);
        loader = new FXMLLoader();
        loader.setLocation(Application.class.getResource("/gui/Main.fxml"));
        loader.setController(this);
        loader.setResources(ResourceBundle.getBundle("bundles.MainLabels", app.getLocale()));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    class Updater implements Runnable {
        private volatile boolean shutdownRequested = false;
        private volatile boolean updateRequested = false;

        public void run() {
            while (!shutdownRequested) {
                if (Storage.getInstance().isChanged() || updateRequested) {
                    mainTable.setItems(Storage.getInstance().get());
                    mainTable.refresh();
                    Graphics.draw(canvas);
                    if (updateRequested && !Storage.getInstance().isChanged()) {
                        Graphics.drawAllNow(canvas);
                    }
                    updateRequested = false;
                }
            }
        }
    }

    class Fetcher implements Runnable {
        private volatile boolean shutdownRequested = false;
        private final boolean updateRequested = false;

        public void run() {
            while (!shutdownRequested) {
                CommandToSend show = CommandToSendFormer.getInstance().getCommandToSend("show", null, null);
                try {
                    commandSender.sendCommand(show);
                } catch (ClosedChannelException e) {
                    shutdownRequested = true;
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    break;
                }
                Storage.getInstance().applyFiltration(filter);
            }
        }
    }
}