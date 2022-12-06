package at.ac.fhcampuswien.xsolutions;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

import static at.ac.fhcampuswien.xsolutions.User.usersList;

public class LoginController {

    public static String loggedInUserName;

    public static String getLoggedInUserName() {
        return "Kellner: " + loggedInUserName;
    }

    @FXML
    private Label errorLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private VBox bg;

    @FXML
    void unfocusFields(MouseEvent event) {
        passwordField.setFocusTraversable(false); //Unfocus the input fileds on click
        usernameField.setFocusTraversable(false);
    }

    @FXML
    void exitButton(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void userLogin(ActionEvent event) throws IOException { //Compare every "Row" of the list to the input
        for (int i = 0; i < usersList.size(); i++) {
            if (Objects.equals(usernameField.getText(), usersList.get(i).getUserName()) && Objects.equals(passwordField.getText(), usersList.get(i).getPassword())){
                loggedInUserName = usersList.get(i).getName();
                executeLogin(event);
                return;
            }
        }
        errorLabel.setVisible(true);
    }

    void executeLogin(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("main.fxml"))));
        stage.setFullScreenExitHint("");
        stage.setFullScreen(true); // Set to Full Screen Mode
        stage.show();
    }

    public void initialize(){
        errorLabel.setVisible(false);
    }

}
