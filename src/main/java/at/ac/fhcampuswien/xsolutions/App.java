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
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main.fxml"));
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
        generateTables(20);

        //Product p2 = new Product("Spaghetti bolognese",11.99);
        //Product p3 = new Product("Spaghetti arrabiata",9.99,"Spaghetti with a spicy sauce made of tomatoes cooked with chilli peppers, used in Italian cookery.");
        //Product p4 = new Product("Lasagna",13.99);
        //Product pizzaSalami= new Product("Pizza Salami",10);


//        User userDmytro = new User("Dmytro", true, "d3vote", "test");
//        User userTolga = new User("Tolga", true, "tolga", "test");
//        User userTosha = new User("Tosha", true, "tosha", "test");
//        User userTheresa = new User("Theresa", true, "theresa", "test");
//        User userEtrit = new User("Etrit", true, "etrit", "test");

        launch();
    }
}