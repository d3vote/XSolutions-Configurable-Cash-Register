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
import static at.ac.fhcampuswien.xsolutions.App.updateTableCount;
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
    }
    @FXML
    void dateSetter(){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateString = currentDate.format(formatter);
        datum.setText(dateString);
    }
    @FXML
    void addToBillButton(Product item){
        int currentTableIndex = tablesListView.getSelectionModel().getSelectedIndex();
        Tables currentTable = arrayTables[currentTableIndex];

        // If the product is already in the map, increase its quantity by 1
        if (currentTable.getProductCounter().containsKey(item)) {
            int currentQuantity = currentTable.getProductCounter().get(item);
            currentTable.getProductCounter().put(item, currentQuantity + 1);
        }
        // Otherwise, add the product to the map with a quantity of 1
        else {
            currentTable.getProductCounter().put(item, 1);
        }

        //String currentText = billText.getText();
        //String newText = currentText + "\n" + item.getProductTitle() + " x " + currentTable.getProductCounter().get(item) + " $" + currentTable.getProductCounter().get(item)*item.getProductPrice();
        currentTable.addUsedProducts(item);
        currentTable.addToTotal(item.getProductPrice());
        totalPrice.setText(currentTable.getAmountAfterTaxes() + "$");
        billText.setText(currentTable.getBill());
    }
    @FXML
    private void addProductElementsToGrid(GridPane grid) {
        int row = 0;
        int col = 0;
        for (Product item : productsList) {
            // Create the elements
            Pane imagePane = new Pane();
            imagePane.setStyle("-fx-background-image: url(\"" + item.getProductImageUrl() + "\"); -fx-background-size: contain; -fx-background-repeat: no-repeat; -fx-background-position: center center;");
            Label productTitleLabel = new Label(item.getProductTitle());
            Button addButton = new Button("Add");
            Button removeButton = new Button("Remove");

            // Sets size of new elements
            imagePane.setPrefSize(220, 135);
            productTitleLabel.setPrefSize(215, 30);
            addButton.setPrefSize(109, 25);
            removeButton.setPrefSize(109, 25);

            // sets positions of new elements
            HBox buttonBox = new HBox();
            buttonBox.getChildren().addAll(addButton, removeButton);
            buttonBox.setAlignment(Pos.BASELINE_CENTER);
            VBox productPane = new VBox();
            productTitleLabel.setAlignment(Pos.CENTER);
            productPane.getChildren().addAll(productTitleLabel, imagePane, buttonBox);

            // Set the properties of the elements
            addButton.setOnAction(event -> addToBillButton(item));
            removeButton.setOnAction(event -> removeFromBillButton(item));

            // Add the elements to the grid
            grid.add(productPane, col, row);

            col++;
            if (col % 4 == 0) {
                col = 0;
                row++;
            }
        }
    }


    @FXML
    void removeFromBillButton(Product item){

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
        updateTableCount(newSize);
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
        addProductElementsToGrid(GridPaneProducts);

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