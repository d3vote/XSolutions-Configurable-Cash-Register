package at.ac.fhcampuswien.xsolutions;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

import static at.ac.fhcampuswien.xsolutions.App.arrayBills;
import static at.ac.fhcampuswien.xsolutions.App.arrayTables;
import static at.ac.fhcampuswien.xsolutions.Tables.*;


public class AppController implements Initializable {
    @FXML
    private Label totalPrice;

    @FXML
    private ListView<String> tablesListView; // Left Panel
    String[] tablesListAsString = new String[getCount()]; //List of the Left Panel

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
    public void initialize(URL arg0, ResourceBundle arg1){

        for (int i = 0; i < getCount(); i++) {                       //Adding as many tables to the array as have been created
            tablesListAsString[i] = getTableNumberAsString(i+1);
        }
        tablesListView.getItems().addAll(tablesListAsString);         //Parsing them in to the Left Panel
        tablesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                currentTable = tablesListView.getSelectionModel().getSelectedIndex();
                billText.setText(arrayTables[currentTable].getBill());        //Setting the Info of the Bill
                totalPrice.setText(arrayTables[currentTable].reformatAmountBeforeTaxes()); //Setting the price of the Bill
            }
        });
    }
}