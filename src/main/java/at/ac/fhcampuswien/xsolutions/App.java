package at.ac.fhcampuswien.xsolutions;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static at.ac.fhcampuswien.xsolutions.Configurator.*;
import static at.ac.fhcampuswien.xsolutions.Product.*;
import static at.ac.fhcampuswien.xsolutions.ReceiptHistory.loadReceiptHistory;
import static at.ac.fhcampuswien.xsolutions.User.JSONtoUsersList;

public class App extends Application {
    public static Tables[] arrayTables;
    public static Stage stage = null;
    public static boolean run = false;

    /** GUI start
     */
    @Override
    public void start(Stage stage) {
        App.stage = stage;
        showSplashScreen();
        loadMainStage();
    }

    private void showSplashScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("splashscreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.getIcons().add(new Image("file:src/main/resources/icon.png"));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.setAlwaysOnTop(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadMainStage() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                updateProgress(0.5, 1.0);
                return null;
            }
        };
        task.setOnSucceeded(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);

                FXMLLoader newLoader = new FXMLLoader(getClass().getResource("login.fxml"));
                Parent root2 = newLoader.load();
                Stage newStage = new Stage();
                scene = new Scene(root2);
                newStage.getIcons().add(new Image("file:src/main/resources/icon.png"));

                newStage.setScene(scene);
                newStage.setFullScreenExitHint("");
                newStage.setX(0);
                newStage.setY(0);
                newStage.setMaximized(true);
                newStage.setFullScreen(true);
                stage.hide();
                run = true;
                newStage.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        new Thread(task).start();
    }

    /** Generates an amount of Tables and adds them to an array of tables (arrayTables)
     */
    public static void generateTables(int amount){
        arrayTables = new Tables[amount];
        for(int i = 0;i < amount; i++){
            arrayTables[i]  = new Tables();
        }
    }

    public static String getDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return currentDate.format(formatter);
    }

    public static String getShortDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MMMM");
        return currentDate.format(formatter);
    }

    public static int[] getTableLocation(int index, int cols) {
        int row = index / cols;
        int col = index % cols;
        return new int[]{row, col};
    }

    /** Initializes everything needed for the App to function
     * <p>(Settings, credentials, table generation from config.txt,
     * products sorting and receipt history loading and GUI starting)</p>
     */
    public static void main(String[] args) throws IOException {
        createAppData();

        JSONtoUsersList(); //Convert the JSON File back to Objects in a List
        JSONtoProductList();
        JSONtoCategories();

        readConfig();

        generateTables(getTableConfig());

        //Sort Products List by their Category
        sortProducts();

        loadReceiptHistory();

        launch();
    }
}