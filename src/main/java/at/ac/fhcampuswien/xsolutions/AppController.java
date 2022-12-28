package at.ac.fhcampuswien.xsolutions;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.stage.Stage;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import javafx.util.StringConverter;

import static at.ac.fhcampuswien.xsolutions.App.*;
import static at.ac.fhcampuswien.xsolutions.Configurator.setValue;
import static at.ac.fhcampuswien.xsolutions.LoginController.getLoggedInUserName;
import static at.ac.fhcampuswien.xsolutions.LoginController.isAdmin;
import static at.ac.fhcampuswien.xsolutions.Product.*;
import static at.ac.fhcampuswien.xsolutions.Tables.*;
import static at.ac.fhcampuswien.xsolutions.User.userToJson;
import static at.ac.fhcampuswien.xsolutions.User.usersList;


public class AppController implements Initializable {
    String[] tablesListAsString = new String[Tables.getCount()];   //Array of Tables on the Left Panel
    ObservableList<String> observableList = FXCollections.observableArrayList(tablesListAsString);
    String[] productsListAsString = new String[Product.getCount()];   //Array of Tables on the Left Panel
    List<Currency> currencies = Arrays.asList(Currency.getInstance(Locale.US),
                                                Currency.getInstance(Locale.GERMANY),
                                                Currency.getInstance(Locale.UK),
                                                Currency.getInstance(Locale.JAPAN));
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

    @FXML
    private ImageView adminButton;

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
    private Label productsSettingsPrice;

    @FXML
    private TextField newProductDescription;

    @FXML
    private TextField newProductName;

    @FXML
    private TextField newProductPrice;

    @FXML
    private TextField newURL;

    @FXML
    private Label datum;

    @FXML
    private Button add;

    @FXML
    private Button remove;

    @FXML
    private Pane systemSettingsPane;

    @FXML
    private TextField systemNewCurrencyField;

    @FXML
    private TextField systemNewTaxesField;

    @FXML
    private Button systemSettings;

    @FXML
    private Pane paymentMethodsPane;

    @FXML
    private Pane paymentSuccessfulPane;

    @FXML
    private ChoiceBox<Currency> systemNewCurrencySelector;

    // Set date in the Bill
    @FXML
    void dateSetter(){
        datum.setText(getDate());
    }

    // Add Product into Bill of selected Table
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

        // Add product to usedProducts list
        currentTable.addUsedProducts(item);
        // Update Total Price and Bill
        currentTable.addToSubtotal(item.getProductPrice());
        currentTable.setServerName(getLoggedInUserName());
        updateBill();
    }

    @FXML
    void removeFromBillButton(Product item) {
        int currentTableIndex = tablesListView.getSelectionModel().getSelectedIndex();
        Tables currentTable = arrayTables[currentTableIndex];

        // Check if the product is in the map
        if (currentTable.getProductCounter().containsKey(item) && currentTable.getUsedProducts().contains(item)) {
            // If the product is in the map, decrease its quantity by 1
            int currentQuantity = currentTable.getProductCounter().get(item);
            if (currentQuantity != 0) {
                currentQuantity--;
            }

            // Update the map with the updated quantity of the product
            currentTable.getProductCounter().put(item, currentQuantity);

            // If the product's quantity is 0, remove the product from the usedProducts list and update the bill
            if (currentQuantity == 0) {
                currentTable.removeUsedProducts(item);
            }
            currentTable.subtractFromSubtotal(item.getProductPrice());
            updateBill();
        }
        billText.setText(currentTable.getBill());
    }

    @FXML
    void showPaymentPane(ActionEvent event) {
        int currentTableIndex = tablesListView.getSelectionModel().getSelectedIndex();
        Tables currentTable = arrayTables[currentTableIndex];

        if (Double.parseDouble(currentTable.getTotal()) > 0){
            paymentMethodsPane.setVisible(true);
            paymentSuccessfulPane.setVisible(false);
        }

    }

    @FXML
    void closePaymentPane(ActionEvent event) {
        paymentMethodsPane.setVisible(false);
        paymentSuccessfulPane.setVisible(false);
    }

    @FXML
    void payCard(ActionEvent event) {
        resetBill();
    }

    @FXML
    void payCash(ActionEvent event) {
        resetBill();
    }

    void resetBill() {
        int currentTableIndex = tablesListView.getSelectionModel().getSelectedIndex();
        Tables currentTable = arrayTables[currentTableIndex];
        paymentSuccessfulPane.setVisible(true);
        currentTable.resetBill();
        updateBill();
    }

    // Parse all Products into Grid
    @FXML
    private void addProductElementsToGrid(GridPane grid, List<Product> productsList) {
        grid.getChildren().clear();
        int row = 0;
        int col = 0;
        for (Product item : productsList) {
            // Create the elements
            Pane imagePane = new Pane();
            imagePane.setStyle("-fx-background-image: url(\"" + item.getProductImageUrl() + "\"); -fx-background-size: contain; -fx-background-repeat: no-repeat; -fx-background-position: center center;");
            Label productTitleLabel = new Label(item.getProductTitle());
            Button addButton = new Button("Add");
            Button removeButton = new Button("Remove");

            // creates Tooltip that shows productDescription
            Tooltip tt = new Tooltip();
            tt.setText(item.productDescription);
            tt.setShowDelay(Duration.millis(100));
            tt.setStyle("-fx-font: normal bold 12 Langdon; "
                    + "-fx-base: #AE3522; "
                    + "-fx-text-fill: orange;");
            productTitleLabel.setTooltip(tt);

            addButton.getStyleClass().add("cartOptions-l");
            removeButton.getStyleClass().add("cartOptions-r");

            // Sets size of new elements
            imagePane.setMinSize(200, 120);
            imagePane.setMaxSize(220, 135);
            productTitleLabel.setMinSize(215, 30);
            addButton.setMinSize(90, 25);
            removeButton.setMinSize(90, 25);

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
            for (int i = 0;i<30; i++) {
                grid.addRow(grid.getRowCount()+1);
            }
        }
    }

    // Tab Switching
    @FXML
    void setupSystem() {
        usersSettingsPane.setVisible(false);
        tablesSettingPane.setVisible(false);
        productsSettingsPane.setVisible(false);
        systemSettingsPane.setVisible(true);
    }

    @FXML
    void setupProducts() {
        usersSettingsPane.setVisible(false);
        tablesSettingPane.setVisible(false);
        productsSettingsPane.setVisible(true);
        systemSettingsPane.setVisible(false);
    }

    @FXML
    void setupTables() {
        usersSettingsPane.setVisible(false);
        tablesSettingPane.setVisible(true);
        productsSettingsPane.setVisible(false);
        systemSettingsPane.setVisible(false);
    }

    @FXML
    void setupUsers() {
        tablesSettingPane.setVisible(false);
        usersSettingsPane.setVisible(true);
        productsSettingsPane.setVisible(false);
        systemSettingsPane.setVisible(false);
    }

    // SYSTEM SETTINGS TAB METHODS
    @FXML
    void systemSettingsChangeCurrency() throws IOException {
        String newCurrency = String.valueOf(systemNewCurrencySelector.getValue().getSymbol());
        setCurrency(newCurrency);
        setValue("currency", String.valueOf(newCurrency));
        updateBill();
    }

    void updateBill(){
        int currentTableIndex = tablesListView.getSelectionModel().getSelectedIndex();
        Tables currentTable = arrayTables[currentTableIndex];
        billText.setText(currentTable.getBill());
        totalPrice.setText(currentTable.getTotal() + getCurrency());
    }

    @FXML
    void systemSettingsChangeLanguage() {

    }

    @FXML
    void systemSettingsChangeTaxes() {
        setTaxes(Double.parseDouble(systemNewTaxesField.getText()));
        updateBill();
    }

    // PRODUCT SETTINGS TAB METHODS
    @FXML
    void productsSettingsChangeName() {
        int currentProduct = productsListViewSettings.getSelectionModel().getSelectedIndex();
        String text = newProductName.getText();
        if (!Objects.equals(text, ""))  {
            productsList.get(currentProduct).setProductTitle(text);
        }
        updateProductsList(currentProduct);
    }

    @FXML
    void productsSettingsChangeURL() {
        int currentProduct = productsListViewSettings.getSelectionModel().getSelectedIndex();
        String text = newURL.getText();
        if (!Objects.equals(text, ""))  {
            productsList.get(currentProduct).setProductImageUrl(text);
        }
        updateProductsList(currentProduct);
    }

    @FXML
    void productsSettingsChangeDescription() {
        int currentProduct = productsListViewSettings.getSelectionModel().getSelectedIndex();
        String text = newProductDescription.getText();
        if (!Objects.equals(text, ""))  {
            productsList.get(currentProduct).setProductDescription(text);
        }
        updateProductsList(currentProduct);
    }

    @FXML
    void productsSettingsChangePrice() {
        int currentProduct = productsListViewSettings.getSelectionModel().getSelectedIndex();
        Double text = Double.valueOf(newProductPrice.getText());
        if (!Objects.equals(text, ""))  {
            productsList.get(currentProduct).setProductPrice(text);
        }
        updateProductsList(currentProduct);
    }

    @FXML
    void productsSettingsCreateNew() throws IOException {
        int currentProduct = productsListViewSettings.getSelectionModel().getSelectedIndex();
        new Product("New Product", 0, "description");
        updateProductsList(currentProduct);
    }

    @FXML
    void productsSettingsDelete() {
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

    // USER SETTINGS TAB METHODS
    @FXML
    void userSettingsChangeName() {
        int currentUser = usersListView.getSelectionModel().getSelectedIndex();
        String text = newFullNameField.getText();
        if (!Objects.equals(text, ""))  {
            usersList.get(currentUser).setFullName(text);
        }
        updateUsersList(currentUser);
    }

    @FXML
    void userSettingsChangePassword() {
        int currentUser = usersListView.getSelectionModel().getSelectedIndex();
        String text = newPasswordField.getText();
        if (!Objects.equals(text, ""))  {
            usersList.get(currentUser).setPassword(text);
        }
        updateUsersList(currentUser);
    }

    @FXML
    void userSettingsChangeUsername() {
        int currentUser = usersListView.getSelectionModel().getSelectedIndex();
        String text = newUsernameField.getText();
        if (!Objects.equals(text, ""))  {
            usersList.get(currentUser).setUserName(text);
        }
        updateUsersList(currentUser);
    }

    @FXML
    void userSettingsToggleAdminRights() {
        int currentUser = usersListView.getSelectionModel().getSelectedIndex();
        usersList.get(currentUser).setAdmin(!usersList.get(currentUser).getIsAdmin());
        updateUsersList(currentUser);
    }

    @FXML
    void userSettingsDeleteUser() {
        int currentUser = usersListView.getSelectionModel().getSelectedIndex();
        usersList.remove(currentUser);
        updateUsersList(currentUser);
    }

    @FXML
    void userSettingsCreateNewUser() throws IOException {
        int currentUser = usersListView.getSelectionModel().getSelectedIndex();
        new User("New User", false, "newuser");
        updateUsersList(currentUser);
    }

    private void updateUsersList(int currentUser) {
        // Clear User ListView
        usersListView.getItems().clear();
        // Recreate User ListView
        for (User user : usersList) {
            usersListView.getItems().add(user.getName());
        }
        // Update User Info
        if (currentUser >= 0 && currentUser < usersListView.getItems().size()){
            userSettingsFullName.setText("Full Name: " + usersList.get(currentUser).getName());
            userSettingsUsername.setText("Username: " + usersList.get(currentUser).getUserName());
            userSettingsAdminRights.setText("Admin rights: " + usersList.get(currentUser).getIsAdmin());
            usersListView.getSelectionModel().select(currentUser);
        }
        userToJson();
    }

    // Update Table Amount inside JSON, update List and Config
    @FXML
    void changeValue() throws IOException {
        int newSize = Integer.parseInt(settingsInputField.getText());

        // Regenerate Tables
        Tables[] newArray = new Tables[newSize];
        setTablesCount(0);
        for (int i = 0; i < newSize; i++) {
            newArray[i] = new Tables();
        }
        arrayTables = newArray;
        // Clear Table ListView
        tablesListView.getItems().clear();
        // Recreating Tables ListView
        for (Tables arrayTable : arrayTables) {
            tablesListView.getItems().add(arrayTable.getTableNumberAsString());
        }
        setValue("tableCount", String.valueOf(newSize));
    }

    @FXML
    void openSettings() {
        settingsTab.setVisible(!settingsTab.isVisible());
        productsPane.setVisible(!productsPane.isVisible());
    }

    // "Crash" button
    @FXML
    void exitButton() {
        System.exit(0);
    }

    // Logout button
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
            systemNewCurrencySelector.setItems(FXCollections.observableArrayList(currencies));
            systemNewCurrencySelector.setValue(currencies.get(0));
            systemNewCurrencySelector.setConverter(new StringConverter<Currency>() {
                @Override
                public String toString(Currency currency) {
                    return currency.getSymbol();
                }

                @Override
                public Currency fromString(String string) {
                    return null;
                }
            });

        ScrollPaneProducts.setStyle("-fx-background-color:transparent;");
        ScrollPaneProducts.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        kellnerLabel.setText("Kellner: " + getLoggedInUserName());
        userSettings.setVisible(isAdmin);
        tablesSettings.setVisible(isAdmin);
        productsSettings.setVisible(isAdmin);

        // Hide Admin Settings Button if not admin
        if(!isAdmin){
            adminButton.setVisible(false);
        }

        dateSetter();
        addProductElementsToGrid(GridPaneProducts, productsList);

        // Generate Lists of Tables, Products and Users
        for (Tables arrayTable : arrayTables) {   //Parsing Tables
            tablesListView.getItems().add(arrayTable.getTableNumberAsString());
        }

        for (Product product : productsList) {
            productsListViewSettings.getItems().add(product.getProductTitle());
        }

        for (User user : usersList) {
            usersListView.getItems().add(user.getName());
        }

        // Check if ListView Selection changed (Tables)
        tablesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                updateBill();
            }
        });

        // Check if ListView Selection changed (Users)
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

        // Check if ListView Selection changed (Products)
        productsListViewSettings.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                int currentProduct = productsListViewSettings.getSelectionModel().getSelectedIndex();
                if (currentProduct >= 0 && currentProduct < productsListViewSettings.getItems().size()){
                    productsSettingsName.setText("Product Name: " + productsList.get(currentProduct).getProductTitle());
                    productsSettingsDescription.setText("Description: " + productsList.get(currentProduct).getProductDescription());
                    productsSettingsURL.setText("Image URL: " + productsList.get(currentProduct).getProductImageUrl());
                    productsSettingsPrice.setText("Price: " + productsList.get(currentProduct).getProductPrice());
                }
            }
        });

        // Create a Search Field Listener and update Products Grid
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            addProductElementsToGrid(GridPaneProducts, filterProductsByName(newValue));
        });

    }
}