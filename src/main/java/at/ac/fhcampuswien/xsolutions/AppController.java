package at.ac.fhcampuswien.xsolutions;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static at.ac.fhcampuswien.xsolutions.Tables.*;


public class AppController implements Initializable {

    @FXML
    private TextField name;
    Stage stage;

    @FXML
    private ListView<String> tablesListView;
    String[] tablesListString = new String[getCount()];

    @FXML
    void exitButton(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    public void initialize(URL arg0, ResourceBundle arg1){
        for (int i = 0; i < getCount(); i++) {
            tablesListString[i] = "Table " + (i+1);
        }
        tablesListView.getItems().addAll(tablesListString);
    }
}