package at.ac.fhcampuswien.xsolutions;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static at.ac.fhcampuswien.xsolutions.User.usersList;

public class LoginController implements Initializable {

    public static String loggedInUserName;

    public static boolean isAdmin;

    public static String getLoggedInUserName() {
        return loggedInUserName;
    }

    public static boolean getIsAdminLogin() {
        return isAdmin;
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
    private Pane loadingBar;

    @FXML
    void unfocusFields(MouseEvent event) {
        passwordField.setFocusTraversable(false); // Unfocus the input fileds on click
        usernameField.setFocusTraversable(false);
    }

    @FXML
    void exitButton(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void userLogin(ActionEvent event) throws IOException { //Compare every "Row" of the list to the input
        for (User user : usersList) {
            if (Objects.equals(usernameField.getText(), user.getUserName()) && Objects.equals(passwordField.getText(), user.getPassword())) {
                loggedInUserName = user.getName();
                errorLabel.setVisible(false);
                isAdmin = user.getIsAdmin();
                loadingBar.setVisible(false);
                executeLogin(event);
                return;
            }
        }
        errorLabel.setVisible(true);
    }

    void executeLogin(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main.fxml")))));
        stage.setFullScreenExitHint("");
        stage.setFullScreen(true); // Set to Full Screen Mode
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorLabel.setVisible(false);
    }
}