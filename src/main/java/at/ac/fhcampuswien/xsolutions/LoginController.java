package at.ac.fhcampuswien.xsolutions;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private Label errorLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField usernameField;

    @FXML
    void exitButton(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void userLogin(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("main.fxml"))));
        stage.setFullScreenExitHint("");
        stage.setFullScreen(true); // Set to Full Screen Mode
        stage.show();
    }

}
