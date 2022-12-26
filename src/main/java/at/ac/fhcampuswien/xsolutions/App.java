package at.ac.fhcampuswien.xsolutions;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

import static at.ac.fhcampuswien.xsolutions.Product.JSONtoProductList;
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


    //Testing
    public static void main(String[] args) throws IOException {
        JSONtoUsersList(); //Convert the JSON File back to Objects in a List
        JSONtoProductList();
        generateTables(35);

//        Product pizzaSalami= new Product("Pizza Salami",10);

//        User userDmytro = new User("Dmytro", true, "d3vote", "test");
//        User userTolga = new User("Tolga", true, "tolga", "test");
//        User userTosha = new User("Tosha", true, "tosha", "test");
//        User userTheresa = new User("Theresa", true, "theresa", "test");
//        User userEtrit = new User("Etrit", true, "etrit", "test");

        launch();
    }
}