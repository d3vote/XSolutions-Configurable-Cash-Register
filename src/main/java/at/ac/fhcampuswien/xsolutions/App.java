package at.ac.fhcampuswien.xsolutions;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static at.ac.fhcampuswien.xsolutions.Configurator.*;
import static at.ac.fhcampuswien.xsolutions.Product.*;
import static at.ac.fhcampuswien.xsolutions.ReceiptHistory.loadReceiptHistory;
import static at.ac.fhcampuswien.xsolutions.User.JSONtoUsersList;

public class App extends Application {
    public static Tables[] arrayTables;

    /** GUI start
     */
    @Override
    public void start(Stage stage) throws IOException {
        stage.getIcons().add(new Image("file:src/main/resources/icon.png"));
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        stage.setTitle("Configurable Cash Register");
        stage.setScene(scene);
        stage.setFullScreenExitHint("");
        stage.setFullScreen(true); // Set to Full Screen Mode
        stage.show();
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

    /** Initializes everything needed for the App to function
     * <p>(Settings, credentials, table generation from config.txt,
     * products sorting and receipt history loading and GUI starting)</p>
     */
    public static void main(String[] args) throws IOException {
        createAppData();

        JSONtoUsersList(); //Convert the JSON File back to Objects in a List
        JSONtoProductList();

        readConfig();

        generateTables(getTableConfig());

        //Sort Products List
        productsList.sort((p1, p2) -> p2.getProductTitle().compareTo(p1.getProductTitle()));

        loadReceiptHistory();

        launch();
    }
}