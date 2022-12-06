package at.ac.fhcampuswien.xsolutions;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static at.ac.fhcampuswien.xsolutions.Product.JSONtoProductList;
import static at.ac.fhcampuswien.xsolutions.User.JSONtoUsersList;


public class App extends Application {
    public static Tables[] arrayTables;


    //Creating JavaFX UI
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        stage.setScene(scene);
        stage.setFullScreen(true); // Set to Full Screen Mode
        stage.show();
    }

    public static void generateTables(int amount){
        arrayTables = new Tables[amount];
        for(int i = 0;i < amount; i++){
            arrayTables[i]  = new Tables();
        }
    }

    //Testing
    public static void main(String[] args) throws IOException {
        //JSONtoUsersList(); //Convert the JSON File back to Objects in a List
        JSONtoProductList();
        generateTables(35);

//        Product pizzaSalami= new Product("Pizza Salami",10);

//        Users userDmytro = new Users("Dmytro", true, 1);
//        Users userTolga = new Users("Tolga", true, 1);
//        Users userTosha = new Users("Tosha", true, 1);
//        Users userTheresa = new Users("Theresa", true, 1);
//        Users userEtrit = new Users("Etrit", true, 1);

        launch();
    }
}