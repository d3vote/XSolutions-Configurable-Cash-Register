package at.ac.fhcampuswien.xsolutions;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import com.fasterxml.jackson.databind.type.TypeFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static at.ac.fhcampuswien.xsolutions.App.arrayTables;
import static at.ac.fhcampuswien.xsolutions.LoginController.isAdmin;
import static at.ac.fhcampuswien.xsolutions.Product.productsList;
import static at.ac.fhcampuswien.xsolutions.Tables.*;
import static at.ac.fhcampuswien.xsolutions.User.userToJson;
import static at.ac.fhcampuswien.xsolutions.User.usersList;


public class AppController implements Initializable {
    @FXML
    private Label totalPrice;

    @FXML
    private ListView<String> usersListView;

    @FXML
    private ListView<String> tablesListView;                // Left Panel
    String[] tablesListAsString = new String[Tables.getCount()];   //Array of Tables on the Left Panel
    ObservableList<String> observableList = FXCollections.observableArrayList(tablesListAsString);

    String[] productsListAsString = new String[Product.getCount()];   //Array of Tables on the Left Panel

    @FXML
    private ListView<String> productsListView;

    @FXML
    private ScrollPane bill;

    @FXML
    private Label billText;

    @FXML
    private Button pay_btn;

    @FXML
    private TextField searchField;

    @FXML
    private GridPane user;

    @FXML
    private Label kellnerLabel;

    @FXML
    private ImageView productImgHolder;

    @FXML
    private Label productTitle;

    @FXML
    private VBox vboxProduct;

    @FXML
    private FlowPane flowpaneProducts;

    @FXML
    private Label productTitleInGrid;

    @FXML
    private GridPane GridPaneProducts;

    @FXML
    private ScrollPane ScrollPaneProducts;

    @FXML
    private ImageView imageForProduct;

    @FXML
    private Label searchResult;

    @FXML
    private Pane settingsTab;

    @FXML
    private Button userSettings;

    @FXML
    private Button billSettings;

    @FXML
    private Button productsSettings;

    @FXML
    private Button tablesSettings;

    @FXML
    private TextField settingsInputField;

    @FXML
    private Label settingsLabelParameter;

    @FXML
    private Pane productsPane;

    @FXML
    private VBox tablesSettingPane;

    @FXML
    private Pane usersSettingsPane;

    @FXML
    private Label userSettingsAdminRights;

    @FXML
    private Label userSettingsFullName;

    @FXML
    private Label userSettingsUsername;

    @FXML
    private TextField newFullNameField;

    @FXML
    private TextField newUsernameField;

    @FXML
    private TextField newPasswordField;


    @FXML
    void assignProductToGrid(MouseEvent event){
        Node source = (Node) event.getSource();
        int currentTable = tablesListView.getSelectionModel().getSelectedIndex();
        if (source == GridPaneProducts){
            productTitleInGrid.setText(productsList.get(0).productTitle);
            arrayTables[currentTable].addToAllProducts(productsList.get(0).productTitle + " " + productsList.get(0).productPrice + "$");
            billText.setText(arrayTables[currentTable].getBill());
        }
        // Will add something tomorrow got a good idea on what to do.
    }

    @FXML
    void setupProducts(ActionEvent event) {
        usersSettingsPane.setVisible(false);
        tablesSettingPane.setVisible(false);
    }

    @FXML
    void setupTables(ActionEvent event) {
        usersSettingsPane.setVisible(false);
        tablesSettingPane.setVisible(true);
    }

    @FXML
    void setupUsers(ActionEvent event) {
        tablesSettingPane.setVisible(false);
        usersSettingsPane.setVisible(true);
    }

    @FXML
    void setupBill(ActionEvent event) {
        usersSettingsPane.setVisible(false);
        tablesSettingPane.setVisible(false);
    }

    @FXML
    void userSettingsChangeName(ActionEvent event) {
        int currentUser = usersListView.getSelectionModel().getSelectedIndex();
        String text = newFullNameField.getText();
        if (!Objects.equals(text, ""))  {
            usersList.get(currentUser).setFullName(text);
        }
        updateUsersList(currentUser);
    }

    @FXML
    void userSettingsChangePassword(ActionEvent event) {
        int currentUser = usersListView.getSelectionModel().getSelectedIndex();
        String text = newPasswordField.getText();
        if (!Objects.equals(text, ""))  {
            usersList.get(currentUser).setPassword(text);
        }
        updateUsersList(currentUser);
    }

    @FXML
    void userSettingsChangeUsername(ActionEvent event) {
        int currentUser = usersListView.getSelectionModel().getSelectedIndex();
        String text = newUsernameField.getText();
        if (!Objects.equals(text, ""))  {
            usersList.get(currentUser).setUserName(text);
        }
        updateUsersList(currentUser);
    }

    @FXML
    void userSettingsToggleAdminRights(ActionEvent event) {
        int currentUser = usersListView.getSelectionModel().getSelectedIndex();
        usersList.get(currentUser).setAdmin(!usersList.get(currentUser).getIsAdmin());
        updateUsersList(currentUser);
    }

    @FXML
    void userSettingsDeleteUser(MouseEvent event) {
        int currentUser = usersListView.getSelectionModel().getSelectedIndex();
        usersList.remove(currentUser);
        updateUsersList(currentUser);
    }

    @FXML
    void userSettingsCreateNewUser(MouseEvent event) throws IOException {
        int currentUser = usersListView.getSelectionModel().getSelectedIndex();
        new User("New User", false, "newuser");
        updateUsersList(currentUser);
    }

    private void updateUsersList(int currentUser) {
        usersListView.getItems().clear();
        for (User user : usersList) {
            usersListView.getItems().add(user.getName());
        }
        if (currentUser >= 0 && currentUser < usersListView.getItems().size()){
            userSettingsFullName.setText("Full Name: " + usersList.get(currentUser).getName());
            userSettingsUsername.setText("Username: " + usersList.get(currentUser).getUserName());
            userSettingsAdminRights.setText("Admin rights: " + usersList.get(currentUser).getIsAdmin());
            usersListView.getSelectionModel().select(currentUser);
        }
        userToJson();
    }

    @FXML
    void changeValue(ActionEvent event) {
        int newSize = Integer.parseInt(settingsInputField.getText());

        //Regenerate Tables
        Tables[] newArray = new Tables[newSize];
        setTablesCount(0);
        for (int i = 0; i < newSize; i++) {
            newArray[i] = new Tables();
        }
        arrayTables = newArray;

        tablesListView.getItems().clear();                                  //Clear Table List
        for (Tables arrayTable : arrayTables) {                             //Parsing Tables
            tablesListView.getItems().add(arrayTable.getTableNumberAsString());
        }
    }

    @FXML
    void openSettings(MouseEvent event) {
        settingsTab.setVisible(!settingsTab.isVisible());
        productsPane.setVisible(!productsPane.isVisible());
    }

    @FXML
    void exitButton(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void userLogout(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("login.fxml"))));
        stage.setFullScreenExitHint("");
        stage.setFullScreen(true); // Set to Full Screen Mode
        stage.show();
    }

    @FXML
    public void initialize(URL arg0, ResourceBundle arg1){
        kellnerLabel.setText(LoginController.getLoggedInUserName());
        userSettings.setVisible(isAdmin);
        tablesSettings.setVisible(isAdmin);
        productsSettings.setVisible(isAdmin);

        for (Tables arrayTable : arrayTables) {   //Parsing Tables
            tablesListView.getItems().add(arrayTable.getTableNumberAsString());
        }

        for (User user : usersList) {
            usersListView.getItems().add(user.getName());
        }

        tablesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                int currentTable = tablesListView.getSelectionModel().getSelectedIndex();
                billText.setText(arrayTables[currentTable].getBill());        //Setting the Info of the Bill
                totalPrice.setText(arrayTables[currentTable].getAmountAfterTaxes() + "$");
            }
        });

        usersListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                int currentUser = usersListView.getSelectionModel().getSelectedIndex();
                if (currentUser >= 0 && currentUser < usersListView.getItems().size()){
                    userSettingsFullName.setText("Full Name: " + usersList.get(currentUser).getName());
                    userSettingsUsername.setText("Username: " + usersList.get(currentUser).getUserName());
                    userSettingsAdminRights.setText("Admin rights: " + usersList.get(currentUser).getIsAdmin());
                }
            }
        });

    }
}