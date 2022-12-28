package at.ac.fhcampuswien.xsolutions;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static at.ac.fhcampuswien.xsolutions.Configurator.getTableCount;
import static at.ac.fhcampuswien.xsolutions.Configurator.readConfigTableCount;
import static at.ac.fhcampuswien.xsolutions.Product.JSONtoProductList;
import static at.ac.fhcampuswien.xsolutions.Product.productsList;
import static at.ac.fhcampuswien.xsolutions.Tables.setCurrency;
import static at.ac.fhcampuswien.xsolutions.User.JSONtoUsersList;

public class App extends Application {
    public static Tables[] arrayTables;

    //Creating JavaFX UI
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

    public static void generateTables(int amount){
        arrayTables = new Tables[amount];
        for(int i = 0;i < amount; i++){
            arrayTables[i]  = new Tables();
        }
    }

    public static String getDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateString = currentDate.format(formatter);
        return dateString;
    }



    public static void main(String[] args) throws IOException {
        JSONtoUsersList(); //Convert the JSON File back to Objects in a List
        JSONtoProductList();
        readConfigTableCount();
        generateTables(getTableCount());
        productsList.sort(new Comparator<Product>() { //Sort Products List
            @Override
            public int compare(Product p1, Product p2) {
                return p2.getProductTitle().compareTo(p1.getProductTitle());
            }
        });
        launch();
    }
}