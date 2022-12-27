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
import javafx.geometry.Pos;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.fasterxml.jackson.databind.type.TypeFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static at.ac.fhcampuswien.xsolutions.App.arrayTables;
import static at.ac.fhcampuswien.xsolutions.LoginController.isAdmin;
import static at.ac.fhcampuswien.xsolutions.Product.productToJSON;
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
    private ListView<String> productsListView;

    @FXML
    private ListView<String> productsListViewSettings;

    @FXML
    private ListView<String> tablesListView;                // Left Panel

    String[] tablesListAsString = new String[Tables.getCount()];   //Array of Tables on the Left Panel
    ObservableList<String> observableList = FXCollections.observableArrayList(tablesListAsString);
    String[] productsListAsString = new String[Product.getCount()];   //Array of Tables on the Left Panel
    Map<Product, Integer> productCounter; // For Bill

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
    private Pane imageForProduct;

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
    private Label productsSettingsDescription;

    @FXML
    private Label productsSettingsName;

    @FXML
    private Pane productsSettingsPane;

    @FXML
    private Label productsSettingsURL;

    @FXML
    private TextField newProductDescription;

    @FXML
    private TextField newProductName;

    @FXML
    private TextField newURL;

    @FXML
    private Label datum;
    @FXML
    private Button add;
    @FXML
    private Button remove;


    @FXML
    void assignProductToGrid(MouseEvent event){
        Node source = (Node) event.getSource();
        /*
        int currentTable = tablesListView.getSelectionModel().getSelectedIndex();
        if (source == GridPaneProducts){
            productTitleInGrid.setText(productsList.get(0).productTitle);
            arrayTables[currentTable].addToAllProducts(productsList.get(0).productTitle + " " + productsList.get(0).productPrice + "$");
            billText.setText(arrayTables[currentTable].getBill());
            imageForProduct.setStyle("-fx-background-image: url(\"" + productsList.get(0).getProductImageUrl() + "\"); -fx-background-size: contain; -fx-background-repeat: no-repeat; -fx-background-position: center center;");     }
        // Will add something tomorrow got a good idea on what to do.
        */
    }
    @FXML
    void dateSetter(){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateString = currentDate.format(formatter);
        datum.setText(dateString);
    }
    @FXML
    void addToBillButton(){
        int currentTable = tablesListView.getSelectionModel().getSelectedIndex();
        Product product = productsList.get(0);
        int counter = productCounter.getOrDefault(product, 0) + 1;
        productCounter.put(product, counter);

}
    @FXML
    private void addProductElementsToGrid(GridPane grid, Product product,int column,int row) {
        // Create the elements
        Pane imagePane = new Pane();
        Label productTitleLabel = new Label(product.getProductTitle());
        Button addButton = new Button("Add");
        Button removeButton = new Button("Remove");

        // Sets size of new elements
        imagePane.setPrefSize(220, 135);
        productTitleLabel.setPrefSize(215, 30);
        addButton.setPrefSize(109, 25);
        removeButton.setPrefSize(109, 25);

        // sets positions of new elements


        // Set the properties of the elements
        addButton.setOnAction(event -> addToBillButton());
        removeButton.setOnAction(event -> removeFromBillButton());


        // Add the elements to the grid
        grid.add(imagePane, column, row);
        grid.add(productTitleLabel, column, row);
        grid.add(addButton, column, row);
        grid.add(removeButton, column, row);
    }


    @FXML
    void removeFromBillButton(){

    }
    @FXML
    void initializeImage(){
        imageForProduct.setStyle("-fx-background-image: url(\"" + productsList.get(0).getProductImageUrl() + "\"); -fx-background-size: contain; -fx-background-repeat: no-repeat; -fx-background-position: center center;");

    }
    @FXML
    void setupProducts(ActionEvent event) {
        usersSettingsPane.setVisible(false);
        tablesSettingPane.setVisible(false);
        productsSettingsPane.setVisible(true);
    }

    @FXML
    void setupTables(ActionEvent event) {
        usersSettingsPane.setVisible(false);
        tablesSettingPane.setVisible(true);
        productsSettingsPane.setVisible(false);
    }

    @FXML
    void setupUsers(ActionEvent event) {
        tablesSettingPane.setVisible(false);
        usersSettingsPane.setVisible(true);
        productsSettingsPane.setVisible(false);
    }

    @FXML
    void setupBill(ActionEvent event) {
        usersSettingsPane.setVisible(false);
        tablesSettingPane.setVisible(false);
        productsSettingsPane.setVisible(false);
    }

    @FXML
    void productsSettingsChangeName(ActionEvent event) {
        int currentProduct = productsListViewSettings.getSelectionModel().getSelectedIndex();
        String text = newProductName.getText();
        if (!Objects.equals(text, ""))  {
            productsList.get(currentProduct).setProductTitle(text);
        }
        updateProductsList(currentProduct);
    }

    @FXML
    void productsSettingsChangeURL(ActionEvent event) {
        int currentProduct = productsListViewSettings.getSelectionModel().getSelectedIndex();
        String text = newURL.getText();
        if (!Objects.equals(text, ""))  {
            productsList.get(currentProduct).setProductImageUrl(text);
        }
        updateProductsList(currentProduct);
    }

    @FXML
    void productsSettingsChangeDescription(ActionEvent event) {
        int currentProduct = productsListViewSettings.getSelectionModel().getSelectedIndex();
        String text = newProductDescription.getText();
        if (!Objects.equals(text, ""))  {
            productsList.get(currentProduct).setProductDescription(text);
        }
        updateProductsList(currentProduct);
    }

    @FXML
    void productsSettingsCreateNew(MouseEvent event) throws IOException {
        int currentProduct = productsListViewSettings.getSelectionModel().getSelectedIndex();
        new Product("New Product", 0, "description");
        updateProductsList(currentProduct);
    }

    @FXML
    void productsSettingsDelete(MouseEvent event) {
        int currentProduct = productsListViewSettings.getSelectionModel().getSelectedIndex();
        productsList.remove(currentProduct);
        updateProductsList(currentProduct);
    }

    private void updateProductsList(int currentProduct) {
        productsListViewSettings.getItems().clear();
        for (Product product : productsList) {
            productsListViewSettings.getItems().add(product.getProductTitle());
        }
        if (currentProduct >= 0 && currentProduct < productsListViewSettings.getItems().size()){
            productsSettingsName.setText("Product Name: " + productsList.get(currentProduct).getProductTitle());
            productsSettingsDescription.setText("Description: " + productsList.get(currentProduct).getProductDescription());
            productsSettingsURL.setText("Image URL: " + productsList.get(currentProduct).getProductImageUrl());
            productsListViewSettings.getSelectionModel().select(currentProduct);
        }
        productToJSON();
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
        dateSetter();
        initializeImage();
        addProductElementsToGrid(GridPaneProducts,productsList.get(0),1, 0);

        for (Tables arrayTable : arrayTables) {   //Parsing Tables
            tablesListView.getItems().add(arrayTable.getTableNumberAsString());
        }

        for (Product product : productsList) {
            productsListViewSettings.getItems().add(product.getProductTitle());
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

        productsListViewSettings.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                int currentProduct = productsListViewSettings.getSelectionModel().getSelectedIndex();
                if (currentProduct >= 0 && currentProduct < productsListViewSettings.getItems().size()){
                    productsSettingsName.setText("Product Name: " + productsList.get(currentProduct).getProductTitle());
                    productsSettingsDescription.setText("Description: " + productsList.get(currentProduct).getProductDescription());
                    productsSettingsURL.setText("Image URL: " + productsList.get(currentProduct).getProductImageUrl());
                }
            }
        });

    }
}