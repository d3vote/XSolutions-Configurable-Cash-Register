package at.ac.fhcampuswien.xsolutions;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

import static at.ac.fhcampuswien.xsolutions.App.arrayTables;
import static at.ac.fhcampuswien.xsolutions.App.getDate;
import static at.ac.fhcampuswien.xsolutions.Configurator.setValue;
import static at.ac.fhcampuswien.xsolutions.LoginController.getLoggedInUserName;
import static at.ac.fhcampuswien.xsolutions.LoginController.isAdmin;
import static at.ac.fhcampuswien.xsolutions.Product.*;
import static at.ac.fhcampuswien.xsolutions.Receipt.*;
import static at.ac.fhcampuswien.xsolutions.ReceiptHistory.getFromReceiptHistory;
import static at.ac.fhcampuswien.xsolutions.Tables.setTablesCount;
import static at.ac.fhcampuswien.xsolutions.User.userToJson;
import static at.ac.fhcampuswien.xsolutions.User.usersList;


public class AppController implements Initializable {
    List<Currency> currencies = Arrays.asList(Currency.getInstance(Locale.US),
            Currency.getInstance(Locale.GERMANY),
            Currency.getInstance(Locale.UK),
            Currency.getInstance(Locale.JAPAN));

    @FXML
    private Button resetBill;
    @FXML
    private Label totalPrice;

    @FXML
    private ListView<String> usersListView;

    @FXML
    private ListView<String> productsListViewSettings;

    @FXML
    private ListView<String> tablesListView;                // Left Panel

    @FXML
    private ImageView adminButton;

    @FXML
    private Label billText;

    @FXML
    private TextField searchField;

    @FXML
    private Label kellnerLabel;

    @FXML
    private GridPane GridPaneProducts;

    @FXML
    private ScrollPane ScrollPaneProducts;

    @FXML
    private Pane settingsTab;

    @FXML
    private Pane findReceiptPane;

    @FXML
    private FlowPane receiptPreview;

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
    private VBox productsPane;

    @FXML
    private ScrollPane billScroll;

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
    private Label productsSettingsCategory;

    @FXML
    private TextField newProductDescription;

    @FXML
    private TextField newProductName;

    @FXML
    private TextField newProductPrice;

    @FXML
    private TextField newTelField;
    @FXML
    private TextField newAdressField;
    @FXML
    private TextField newMessageField;

    @FXML
    private Text previewAdress;

    @FXML
    private Text previewTel;

    @FXML
    private Text previewMessage;

    @FXML
    private TextField newURL;

    @FXML
    private Text datum;

    @FXML
    private Pane systemSettingsPane;

    @FXML
    private Pane billSettingsPane;

    @FXML
    private TextField systemNewCurrencyField;

    @FXML
    private TextField systemNewTaxesField;

    @FXML
    private TextField systemNewBillNrField;

    @FXML
    private Button systemSettings;

    @FXML
    private FlowPane paymentMethodsPane;

    @FXML
    private Pane paymentSuccessfulPane;

    @FXML
    private Pane paymentSelectionPane;

    @FXML
    private ChoiceBox<Currency> systemNewCurrencySelector;

    @FXML
    private ChoiceBox<String> newProductCategory;

    @FXML
    private Label paymentTotalBeforeAllLabel;

    @FXML
    private Label paymentTipLabel;

    @FXML
    private Label paymentTotalLabel;

    @FXML
    private Label restMoneyLabel;

    @FXML
    private TextField paymentAmountPayedField;

    @FXML
    private Button printBillButton;

    @FXML
    private Pane payCashPane;

    @FXML
    private Label restMoneyLabelSuccess;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField tipField;
    @FXML
    private Pane productImagePreview;

    @FXML
    private AnchorPane receiptPane;
    @FXML
    private Label dateInReceipt;
    @FXML
    private Label timeInReceipt;
    @FXML
    private Text receiptBill;
    @FXML
    private Text receiptAddress;
    @FXML
    private Text receiptBillNumber;
    @FXML
    private Label receiptPayAmount;
    @FXML
    private Text receiptMessage;
    @FXML
    private Label receiptPayAmountText;
    @FXML
    private Label receiptRestMoney;
    @FXML
    private Label receiptRestMoneyText;
    @FXML
    private Text receiptTelefonNumber;
    @FXML
    private Label receiptTotal;

    @FXML
    private TextField receiptNumberField;
    @FXML
    private Text receiptAddressFound;
    @FXML
    private Label receiptPrevierwTitle;
    @FXML
    private Text receiptTelefonNumberFound;
    @FXML
    private Text receiptBillNumberFound;
    @FXML
    private Label dateInReceiptFound;
    @FXML
    private Label timeInReceiptFound;
    @FXML
    private Text receiptBillFound;
    @FXML
    private Label receiptTotalFound;
    @FXML
    private Label receiptPayAmountFound;
    @FXML
    private Label receiptRestMoneyFound;
    @FXML
    private Text receiptMessageFound;
    @FXML
    private Label receiptTotalText;
    @FXML
    private VBox emptyReceiptPane;
    @FXML
    private Text tableNumberText;
    @FXML
    private Label subTotalLabel;
    @FXML
    private Label taxesTitleLabel;
    @FXML
    private Label totalTaxesBill;

    private Tables getCurrentTable() {
        int currentTableIndex;
        if (tablesListView.getSelectionModel().getSelectedIndex() != -1){
            currentTableIndex = tablesListView.getSelectionModel().getSelectedIndex();
        } else {
            currentTableIndex = 0;
        }

        return arrayTables[currentTableIndex];
    }

    private Receipt getCurrentReceipt() {
        int currentReceiptIndex;
        if (tablesListView.getSelectionModel().getSelectedIndex() != -1){
            currentReceiptIndex = tablesListView.getSelectionModel().getSelectedIndex();
        } else {
            currentReceiptIndex = 0;
        }
        return arrayReceipts.get(currentReceiptIndex);
    }

    // Set date in the Bill
    @FXML
    void dateSetter(){
        datum.setText(getDate());
    }


    // Add Product into Bill of selected Table
    @FXML
    void addToBillButton(Product item){
        Receipt currentReceipt = getCurrentReceipt();
        Tables currentTable = getCurrentTable();

        // If the product is already in the map, increase its quantity by 1
        if (currentReceipt.getProductCounter().containsKey(item)) {
            int currentQuantity = currentReceipt.getProductCounter().get(item);
            currentReceipt.getProductCounter().put(item, currentQuantity + 1);
        }
        // Otherwise, add the product to the map with a quantity of 1
        else {
            currentReceipt.getProductCounter().put(item, 1);
        }

        // Add product to usedProducts list
        currentReceipt.addUsedProducts(item);
        // Update Total Price and Bill
        currentReceipt.addToSubtotal(item.getProductPrice());
        currentTable.setServerName(getLoggedInUserName());
        updateBill();
    }

    @FXML
    void removeFromBillButton(Product item) {
        Receipt currentReceipt = getCurrentReceipt();

        // Check if the product is in the map
        if (currentReceipt.getProductCounter().containsKey(item) && currentReceipt.getUsedProducts().contains(item)) {
            // If the product is in the map, decrease its quantity by 1
            int currentQuantity = currentReceipt.getProductCounter().get(item);
            if (currentQuantity != 0) {
                currentQuantity--;
            }

            // Update the map with the updated quantity of the product
            currentReceipt.getProductCounter().put(item, currentQuantity);

            // If the product's quantity is 0, remove the product from the usedProducts list and update the bill
            if (currentQuantity == 0) {
                currentReceipt.removeUsedProducts(item);
            }
            currentReceipt.subtractFromSubtotal(item.getProductPrice());
            updateBill();
        }
        if (currentReceipt.getUsedProducts().size() == 0) {
            resetBill();
        }
        billText.setText(currentReceipt.getFullReceipt());
    }

    @FXML
    void printReceipt() throws IOException {
        Receipt currentReceipt = getCurrentReceipt();

        paymentSelectionPane.setVisible(false);
        paymentSuccessfulPane.setVisible(false);
        receiptPane.setVisible(true);

        currentReceipt.increaseBillNumber();
        timeInReceipt.setText(getStaticTime());

        dateInReceipt.setText(getDate());
        receiptBill.setText(currentReceipt.getShortReceipt());
        receiptAddress.setText("Adresse: " + getAddress());
        receiptTelefonNumber.setText("Telefon: " + getTel());
        receiptBillNumber.setText("Belegnummer: " + currentReceipt.getInitialReceiptNumber());
        receiptMessage.setText(getMessage());
        receiptTotal.setText(currentReceipt.getTotalWithTip() + getCurrency());
        receiptRestMoney.setText(df.format(currentReceipt.getChangeAmount()) + getCurrency());

        SimpleReceipt refactoredReceipt = new SimpleReceipt(String.valueOf(currentReceipt.getCount()), currentReceipt.getDate(), currentReceipt.getTime(), currentReceipt.getShortReceipt(), currentReceipt.getTotalWithTip(), String.valueOf(currentReceipt.getAmountPayed()), df.format(currentReceipt.getChangeAmount()));

        resetBill();
    }
    @FXML
    void closeReceipt(){
        paymentMethodsPane.setVisible(false);
        receiptPane.setVisible(false);
        resetBill();
    }

    @FXML
    void showPaymentPane() {
        Receipt currentReceipt = getCurrentReceipt();

        if (Double.parseDouble(currentReceipt.getTotal()) > 0){
            paymentMethodsPane.setVisible(true);
            paymentSelectionPane.setVisible(true);
            paymentSuccessfulPane.setVisible(false);
        }

    }

    @FXML
    void closePaymentPane() {
        if (paymentSuccessfulPane.isVisible()) {
            Receipt currentReceipt = getCurrentReceipt();
            SimpleReceipt refactoredReceipt = new SimpleReceipt(String.valueOf(currentReceipt.getCount()), currentReceipt.getDate(), currentReceipt.getTime(), currentReceipt.getShortReceipt(), currentReceipt.getTotalWithTip(), String.valueOf(currentReceipt.getAmountPayed()), df.format(currentReceipt.getChangeAmount()));
            resetBill();
        }

        paymentMethodsPane.setVisible(false);
        paymentSuccessfulPane.setVisible(false);
        payCashPane.setVisible(false);
        restMoneyLabelSuccess.setText(" ");
    }

    @FXML
    void setTipButton(ActionEvent event) {
        Receipt currentReceipt = getCurrentReceipt();

        currentReceipt.setTip(Double.parseDouble(tipField.getText()));
    }

    @FXML
    void payCard(ActionEvent event) {
        Receipt currentReceipt = getCurrentReceipt();
        String payed = df.format((Double.parseDouble(currentReceipt.getTotal()) + currentReceipt.getTip()));

        receiptPayAmount.setText(payed + getCurrency());
        restMoneyLabelSuccess.setVisible(false);
        currentReceipt.setChangeMoney((double) 0);
        paymentSuccessfulPane.setVisible(true);
    }

    @FXML
    void payCash(ActionEvent event) {
        Receipt currentReceipt = getCurrentReceipt();

        paymentTotalBeforeAllLabel.setText("Gesamtsumme inkl. MWSt: " + currentReceipt.getTotal() + getCurrency());
        paymentTipLabel.setText("Trinkgeld: " + currentReceipt.getTip() + getCurrency());
        paymentTotalLabel.setText("Gesamtsumme inkl. Trinkgeld u. MWSt: " + currentReceipt.getTotalWithTip() + getCurrency());

        restMoneyLabelSuccess.setVisible(true);
        payCashPane.setVisible(true);
    }

    @FXML
    void confirmPaymentCash(ActionEvent event) {
        Receipt currentReceipt = getCurrentReceipt();

        double totalAsDouble = Double.parseDouble(currentReceipt.getTotal());
        double tip = currentReceipt.getTip();
        double payed = Double.parseDouble(paymentAmountPayedField.getText());
        double amountWithTip = totalAsDouble + tip;

        if (payed >= amountWithTip) {
            errorLabel.setVisible(false);
            currentReceipt.setChangeMoney(payed - amountWithTip);
            double restMoney = currentReceipt.getChangeAmount();
            payCashPane.setVisible(false);

            if (restMoney != 0) {
                restMoneyLabelSuccess.setText("Restgeld: " + df.format(restMoney) + getCurrency());
                restMoneyLabelSuccess.setVisible(true);
            }

            paymentSuccessfulPane.setVisible(true);

        } else {
            errorLabel.setVisible(true);
        }
        currentReceipt.setAmountPayed(Double.valueOf(paymentAmountPayedField.getText()));
        receiptPayAmount.setText(currentReceipt.getAmountPayed() + getCurrency());
    }

    @FXML
    void resetBill() {
        emptyReceiptPane.setVisible(true);
        billScroll.setVisible(false);
        Receipt currentReceipt = getCurrentReceipt();
        currentReceipt.closeReceipt();
        updateBill();
    }

    // Parse all Products into Grid

    @FXML
    private void addProductElementsToGrid(GridPane grid, List<Product> productsList) {
        DecimalFormat df = new DecimalFormat("#.00");
        grid.getChildren().clear();
        int row = 0;
        int col = 0;
        for (Product item : productsList) {
            // Create the elements
            Pane imagePane = new Pane();
            imagePane.setStyle("-fx-background-image: url(\"" + item.getProductImageUrl() + "\");");
            Label productTitleLabel = new Label(item.getProductTitle());
            Label productPriceInGrid = new Label(String.valueOf(df.format(item.getProductPrice()) + " " +getCurrency()));
            Button addButton = new Button();
            Button removeButton = new Button();

            addButton.getStyleClass().add("plus");
            removeButton.getStyleClass().add("minus");

            // creates Tooltip that shows productDescription
            Tooltip tt = new Tooltip();
            tt.setText(item.productDescription);
            tt.setShowDelay(Duration.millis(100));
            tt.setHideDelay(Duration.ZERO);
            tt.setStyle("-fx-font: normal bold 12 Langdon; "
                    + "-fx-base: #AE3522; "
                    + "-fx-text-fill: orange;");
            productTitleLabel.setTooltip(tt);

            addButton.getStyleClass().add("cartOptions-l");
            removeButton.getStyleClass().add("cartOptions-r");

            // Sets size of new elements
            imagePane.setMinSize(180, 100);
            imagePane.setMaxSize(220, 135);
            productTitleLabel.setMinSize(150, 30);
            addButton.setMinSize(65, 40);
            removeButton.setMinSize(65, 40);

            // sets positions of new elements
            HBox buttonBox = new HBox();
            buttonBox.getChildren().addAll(addButton, removeButton);
            buttonBox.setMinSize(180,40);
            addButton.setAlignment(Pos.CENTER);
            removeButton.setAlignment(Pos.CENTER);
            buttonBox.setAlignment(Pos.BASELINE_CENTER);
            VBox productPane = new VBox();
            productPriceInGrid.setStyle("-fx-font-size: 15;-fx-font-weight: 600;-fx-text-fill: black");
            productTitleLabel.setStyle("-fx-font-size: 17;-fx-font-weight: 600;-fx-text-fill: black");
            productTitleLabel.setAlignment(Pos.CENTER);
            productPane.setAlignment(Pos.CENTER);
            productPane.getChildren().addAll(productTitleLabel, imagePane, buttonBox, productPriceInGrid);
            productPane.getStyleClass().addAll("product-vbox");
            imagePane.getStyleClass().addAll("productImage");

            RowConstraints rowConstraints = grid.getRowConstraints().get(0);
            rowConstraints.setMinHeight(200);

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
        grid.setVgap(20);
        grid.setHgap(20);
    }

    private void setAllSettingsPanesInvisible() {
        findReceiptPane.setVisible(false);
        usersSettingsPane.setVisible(false);
        tablesSettingPane.setVisible(false);
        productsSettingsPane.setVisible(false);
        systemSettingsPane.setVisible(false);
        billSettingsPane.setVisible(false);
        receiptPreview.setVisible(false);
    }

    // Tab Switching
    @FXML
    void setupSystem() {
        setAllSettingsPanesInvisible();
        systemSettingsPane.setVisible(true);
    }

    @FXML
    void setupProducts() {
        setAllSettingsPanesInvisible();
        productsSettingsPane.setVisible(true);
    }

    @FXML
    void setupTables() {
        setAllSettingsPanesInvisible();
        tablesSettingPane.setVisible(true);
    }

    @FXML
    void setupUsers() {
        setAllSettingsPanesInvisible();
        usersSettingsPane.setVisible(true);
    }

    @FXML
    void showFindReceiptPane() {
        setAllSettingsPanesInvisible();
        findReceiptPane.setVisible(true);
        receiptPreview.setVisible(true);
    }

    @FXML
    void startSearchReceipt() {
        SimpleReceipt foundReceipt = getFromReceiptHistory(Integer.parseInt(receiptNumberField.getText()));

        if (foundReceipt != null) {
            receiptPreview.setVisible(true);

            receiptAddressFound.setText("Adresse: " + getAddress());
            receiptTelefonNumberFound.setText("Telefon: " + getTel());
            receiptBillNumberFound.setText("Belegnummer: " + foundReceipt.getCount());
            dateInReceiptFound.setText("Datum: " +foundReceipt.getDate());
            timeInReceiptFound.setText(foundReceipt.getTime());
            receiptBillFound.setText(foundReceipt.getProductsList());
            receiptTotalFound.setText(foundReceipt.getTotal() + getCurrency());
            receiptPayAmountFound.setText(foundReceipt.getPayed() + getCurrency());
            receiptRestMoneyFound.setText(foundReceipt.getChangeMoney() + getCurrency());
            receiptMessageFound.setText(getMessage());
        }
    }

    @FXML
    void setupBill(ActionEvent event) {
        setAllSettingsPanesInvisible();
        billSettingsPane.setVisible(true);
        updateBillInfo();
    }

    @FXML
    void saveBillinfo(ActionEvent event) throws IOException {
        setAddress(newAdressField.getText());
        setTel(newTelField.getText());
        setMessage(newMessageField.getText());

        updateBillInfo();
    }

    void updateBillInfo() {
        newAdressField.setText(getAddress());
        newTelField.setText(getTel());
        newMessageField.setText(getMessage());

        previewAdress.setText("Addresse: " + getAddress());
        previewTel.setText("Telefon: " + getTel());
        previewMessage.setText(getMessage());
    }

    // SYSTEM SETTINGS TAB METHODS
    @FXML
    void systemSettingsChangeCurrency() throws IOException {
        String newCurrency = String.valueOf(systemNewCurrencySelector.getValue().getSymbol());
        setCurrency(newCurrency);
        setValue("currency", String.valueOf(newCurrency));
        updateReceiptPane();
        updateBill();
        addProductElementsToGrid(GridPaneProducts, productsList);
    }

    @FXML
    void systemSettingsChangeBillNr(ActionEvent event) throws IOException {
        setReceiptNumber(Integer.parseInt(systemNewBillNrField.getText()));
        setValue("bill_nr", systemNewBillNrField.getText());
        updateBill();
    }

    void updateBill(){
        Receipt currentReceipt = getCurrentReceipt();

        if (currentReceipt != null){
            billText.setText(currentReceipt.getFullReceipt());
            totalPrice.setText(currentReceipt.getTotal() + getCurrency());
            tableNumberText.setText(getCurrentTable().getTableNumberAsString());
            subTotalLabel.setText(currentReceipt.getSubtotal() + getCurrency());
            taxesTitleLabel.setText("Steuer(" + getTaxes() + "%)");
            totalTaxesBill.setText(currentReceipt.calculateTaxesAmount() + getCurrency());

            emptyReceiptPane.setVisible(currentReceipt.getFullReceipt().equals(""));
            billScroll.setVisible(!currentReceipt.getFullReceipt().equals(""));
        }
    }

    @FXML
    void systemSettingsChangeTaxes() throws IOException {
        setTaxes(Double.parseDouble(systemNewTaxesField.getText()));
        setValue("taxes", systemNewTaxesField.getText());
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
        productsList.get(currentProduct).setProductPrice(Double.parseDouble(text + getCurrency()));
        updateProductsList(currentProduct);
    }

    @FXML
    void productsSettingsChangeCategory() {
        int currentProduct = productsListViewSettings.getSelectionModel().getSelectedIndex();
        String text = newProductCategory.getValue();
        if (!Objects.equals(text, ""))  {
            productsList.get(currentProduct).setCategory(text);
        }
        updateProductsList(currentProduct);
    }

    @FXML
    void productsSettingsCreateNew() throws IOException {
        int currentProduct = productsListViewSettings.getSelectionModel().getSelectedIndex();

        String productName = newProductName.getText();
        String productPrice = newProductPrice.getText();
        String productDescription = newProductDescription.getText();
        String productURL = newURL.getText();
        String productCategory = newProductCategory.getValue();

        if (productName.isEmpty()) {
            productName = "Neues Produkt";
        }
        if (productPrice.isEmpty()) {
            productPrice = "0";
        }
        if (productURL.isEmpty()) {
            productURL = "Kein";
        }
        if (productDescription.isEmpty()) {
            productDescription = "Keine";
        }
        if (productCategory == null) {
            productCategory = "Keine";
        }

        new Product(productName, Double.parseDouble(productPrice), productDescription, productCategory, productURL);
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

        addProductElementsToGrid(GridPaneProducts, filterProductsByName(""));
        productsList.sort((p1, p2) -> p2.getProductTitle().compareTo(p1.getProductTitle()));

        for (Product product : productsList) {
            productsListViewSettings.getItems().add(product.getProductTitle());
        }
        if (currentProduct >= 0 && currentProduct < productsListViewSettings.getItems().size()){
            productsSettingsName.setText("Produkt Name: " + productsList.get(currentProduct).getProductTitle());
            productsSettingsDescription.setText("Beschreibung: " + productsList.get(currentProduct).getProductDescription());
            productsSettingsURL.setText("Bild URL: " + productsList.get(currentProduct).getProductImageUrl());
            productsSettingsCategory.setText("Kategorie: " + productsList.get(currentProduct).getCategory());
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
        new User("Neuer Benutzer", false, "NeuerBenutzer");
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
            userSettingsFullName.setText("Name: " + usersList.get(currentUser).getName());
            userSettingsUsername.setText("Benutzername: " + usersList.get(currentUser).getUserName());
            userSettingsAdminRights.setText("Admin-Rechte: " + usersList.get(currentUser).getIsAdmin());
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
            tablesListView.getItems().add(arrayTable.getTableName());
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
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")))));
        stage.setFullScreenExitHint("");
        stage.setFullScreen(true); // Set to Full Screen Mode
        stage.show();
    }

    private void updateReceiptPane() {
        totalPrice.setText("0.00" + getCurrency());
        subTotalLabel.setText("0.00" + getCurrency());
        totalTaxesBill.setText("0.00" + getCurrency());
        taxesTitleLabel.setText("Steuer(" + getTaxes() + "%)");
    }

    @FXML
    public void initialize(URL arg0, ResourceBundle arg1){
        updateReceiptPane();
        updateBillInfo();


        //Creates ToolTip for Reset Button
        Tooltip tt = new Tooltip();
        tt.setText("Löscht die aktuelle Rechnung!");
        tt.setShowDelay(Duration.millis(100));
        tt.setHideDelay(Duration.ZERO);
        tt.setStyle("-fx-font: normal bold 12 Langdon; "
                + "-fx-base: #AE3522; "
                + "-fx-text-fill: orange;");
        resetBill.setTooltip(tt);

        newProductCategory.getItems().addAll(getCategories());

        systemNewCurrencySelector.setItems(FXCollections.observableArrayList(currencies));
        systemNewCurrencySelector.setValue(currencies.get(0));
        systemNewCurrencySelector.setConverter(new StringConverter<>() {
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
            tablesListView.getItems().add(arrayTable.getTableName());
        }

        for (Product product : productsList) {
            productsListViewSettings.getItems().add(product.getProductTitle());
        }

        for (User user : usersList) {
            usersListView.getItems().add(user.getName());
        }

        // Check if ListView Selection changed (Tables)
        tablesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateBill());

        // Check if ListView Selection changed (Users)
        usersListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            int currentUser = usersListView.getSelectionModel().getSelectedIndex();
            if (currentUser >= 0 && currentUser < usersListView.getItems().size()){
                userSettingsFullName.setText("Name: " + usersList.get(currentUser).getName());
                userSettingsUsername.setText("Benutzername: " + usersList.get(currentUser).getUserName());
                userSettingsAdminRights.setText("Admin-Rechte: " + usersList.get(currentUser).getIsAdmin());
            }
        });

        // Check if ListView Selection changed (Products)
        productsListViewSettings.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            int currentProduct = productsListViewSettings.getSelectionModel().getSelectedIndex();
            if (currentProduct >= 0 && currentProduct < productsListViewSettings.getItems().size()){
                productsSettingsName.setText("Produkt Name: " + productsList.get(currentProduct).getProductTitle());
                productsSettingsDescription.setText("Beschreibung: " + productsList.get(currentProduct).getProductDescription());
                productsSettingsURL.setText("Bild URL: " + productsList.get(currentProduct).getProductImageUrl());
                productsSettingsPrice.setText("Preis: " + productsList.get(currentProduct).getProductPrice() + getCurrency());
                productsSettingsCategory.setText("Kategorie: " + productsList.get(currentProduct).getCategory());
                productImagePreview.setStyle("-fx-background-image: url(\"" + productsList.get(currentProduct).getProductImageUrl() + "\"); -fx-background-size: contain; -fx-background-repeat: no-repeat; -fx-background-position: center center;");
            }
        });

        // Create a Search Field Listener and update Products Grid
        searchField.textProperty().addListener((observable, oldValue, newValue) -> addProductElementsToGrid(GridPaneProducts, filterProductsByName(newValue)));
    }
}