package gui;

import data.Worker;
import javafx.stage.Stage;
import network.CommandSender;
import network.Receiver;
import workersOperations.FieldsReader;
import workersOperations.WorkerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application extends javafx.application.Application {
    private Locale locale = Locale.ENGLISH;
    private Receiver receiver;
    private AuthorizationController authController;
    private MainController mainController;
    private Controller addController;
    private UpdateController updateController;
    private DeleteController deleteController;
    private ControllerWithMessages controllerWithMessages;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        InetAddress clientAddress;
        DatagramChannel datagramChannel;
        try {
            clientAddress = InetAddress.getLocalHost();
            datagramChannel = DatagramChannel.open();
            datagramChannel.bind(new InetSocketAddress(clientAddress, 0));
            datagramChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        primaryStage.setTitle("Workers");
        ExecutorService executor = Executors.newFixedThreadPool(1);
        FieldsReader fieldsReader = new FieldsReader(new Scanner(System.in));
        WorkerFactory workerFactory = new WorkerFactory(0, fieldsReader);
        CommandSender sender = new CommandSender(datagramChannel);
        mainController = new MainController(this, primaryStage, sender);
        receiver = new Receiver(datagramChannel, this);
        executor.execute(receiver);
        authController = new AuthorizationController(this, primaryStage, receiver, sender);
        addController = new AddController(this, workerFactory, sender);
        updateController = new UpdateController(this, workerFactory, sender);
        deleteController = new DeleteController(this, workerFactory, sender);
        primaryStage.setOnCloseRequest(event -> {
            try {
                receiver.stopReceiver();
                mainController.shutdown();
                deleteController.shutdown();
                addController.shutdown();
                updateController.shutdown();
                executor.shutdownNow();
                stop();
                System.exit(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        showAuthorizationScene();
        controllerWithMessages = authController;
    }

    void showAuthorizationScene() {
        try {
            authController.launch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void showMainScene() {
        try {
            mainController.launch();
            controllerWithMessages = mainController;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void showAddScene() {
        try {
            addController.launch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void showUpdateScene(Worker w) {
        try {
            updateController.launch(w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void showDeleteScene() {
        try {
            deleteController.launch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void showDeleteScene(Worker worker) {
        try {
            deleteController.launch(worker);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void sendMessage(String msg) {
        controllerWithMessages.sendMessage(msg);
    }
}
