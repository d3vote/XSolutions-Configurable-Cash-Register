package at.ac.fhcampuswien.xsolutions;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;


public class App extends Application {
    public static void userToJson() {
        List<Users> users = Arrays.asList(
                new Users("Dmytro", true, 1),
                new Users("Tolga", true, 1),
                new Users("Tosha", true, 1),
                new Users("Theresa", true, 1),
                new Users("Etrit", true, 1)
        );
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            java.nio.file.Path path = Paths.get("src/main/java/usersList.json");
            objectMapper.writeValue(path.toFile(), users);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}