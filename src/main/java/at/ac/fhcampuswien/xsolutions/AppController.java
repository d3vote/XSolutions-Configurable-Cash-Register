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
import java.util.ResourceBundle;
import com.fasterxml.jackson.databind.type.TypeFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static at.ac.fhcampuswien.xsolutions.App.arrayTables;
import static at.ac.fhcampuswien.xsolutions.LoginController.isAdmin;
import static at.ac.fhcampuswien.xsolutions.Product.productsList;
import static at.ac.fhcampuswien.xsolutions.Tables.*;


public class AppController implements Initializable {
    @FXML
    private Label totalPrice;

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
    int currentTable;

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
    private Label searchResult;

    @FXML
    private AnchorPane settingsTab;

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
    void setupBill(ActionEvent event) {

    }

    @FXML
    void setupProducts(ActionEvent event) {

    }

    @FXML
    void setupTables(ActionEvent event) {
        settingsLabelParameter.setText("Input Tables amount:");
    }

    @FXML
    void changeValue(ActionEvent event) {
        int newSize = Integer.parseInt(settingsInputField.getText())
        Tables[] newArray = new Tables[newSize];
        int oldSize = arrayTables.length;
        for (int i = 0; i < Math.min(oldSize, newSize); i++) {
            newArray[i] = arrayTables[i];
        }
        arrayTables = newArray;
    }

    @FXML
    void setupUsers(ActionEvent event) {

    }

    @FXML
    void openSettings(MouseEvent event) {
        settingsTab.setVisible(!settingsTab.isVisible());
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

        tablesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                currentTable = tablesListView.getSelectionModel().getSelectedIndex();
                billText.setText(arrayTables[currentTable].getBill());        //Setting the Info of the Bill
                totalPrice.setText(arrayTables[currentTable].getAmountAfterTaxes() + "$");
            }
        });

    }
}