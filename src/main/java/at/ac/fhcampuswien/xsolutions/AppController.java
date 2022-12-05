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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import com.fasterxml.jackson.databind.type.TypeFactory;
import javafx.stage.Stage;

import static at.ac.fhcampuswien.xsolutions.App.arrayTables;
import static at.ac.fhcampuswien.xsolutions.Product.productsList;
import static at.ac.fhcampuswien.xsolutions.Tables.*;


public class AppController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label totalPrice;

    @FXML
    private ListView<String> tablesListView;                // Left Panel
    String[] tablesListAsString = new String[getCount()];   //Array of Tables on the Left Panel
    ObservableList<String> observableList = FXCollections.observableArrayList(tablesListAsString);

    @FXML
    private ListView<String> productsListView;

    @FXML
    void exitButton(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private ScrollPane bill;

    @FXML
    private Label billText;
    int currentTable;

    @FXML
    private Button pay_btn;

    @FXML
    void userLogout(ActionEvent event) throws IOException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("login.fxml"))));
        stage.setFullScreen(true); // Set to Full Screen Mode
        stage.show();
    }

    @FXML
    private TextField searchField;

    @FXML
    private GridPane user;

    @FXML
    public void initialize(URL arg0, ResourceBundle arg1){

        for (int i = 0; i < getCount(); i++) {                       //Adding as many tables to the array as have been created
            tablesListAsString[i] = arrayTables[i].getTableNumberAsString();
        }
        tablesListView.getItems().addAll(tablesListAsString);         //Parsing Tables in to the Left Panel

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