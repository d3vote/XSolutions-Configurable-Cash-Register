package at.ac.fhcampuswien.xsolutions;

import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static at.ac.fhcampuswien.xsolutions.App.*;
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
    private static final ArrayList<Node> tablePaneCollector = new ArrayList<>();

    @FXML
    private Button resetBill;

    @FXML
    private ScrollPane tableScrollPane;

    @FXML
    private Label totalPrice;

    @FXML
    private GridPane tablesGridPane;

    @FXML
    private ListView<String> usersListView;

    @FXML
    private ListView<String> productsListViewSettings;

    @FXML
    private ListView<String> tablesListView;                // Left Panel

    @FXML
    private ImageView adminButton;

    @FXML
    private Text time;

    @FXML
    private Label billText;

    @FXML
    private TextField searchField;

    @FXML
    private Text kellnerLabel;

    @FXML
    private GridPane GridPaneProducts;

    @FXML
    private ScrollPane ScrollPaneProducts;

    @FXML
    private AnchorPane settingsTab;

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
    private ChoiceBox<String> categoryBoxSettings;

    @FXML
    private Label paymentTotalBeforeAllLabel;

    @FXML
    private Label paymentTipLabel;

    @FXML
    private Label paymentTotalLabel;

    @FXML
    private Pane popupPane;

    @FXML
    private Text popupText;

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

    @FXML
    private ChoiceBox<String> categoryBoxMain;

    @FXML
    private Button resetCategoryMain;
    @FXML
    private Button resetCategorySettings;
    @FXML
    private Button resetCategoryTrash;

    @FXML
    private Button resetPreview;

    @FXML
    private Button categorySettings;

    @FXML
    private Pane categorySettingPane;

    @FXML
    private ListView<String> categoryListView;

    @FXML
    private Label categoryLabelSettings;

    @FXML
    private TextField categorySettingsField;

    @FXML
    private TextField categoryNameField;

    @FXML
    private Label tipSuccessful;

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
        datum.setText(getShortDate());
        time.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
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
            transitionToGreen();
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
                transitionToBlackSingle();
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
    private void addAnimation(Label ID){
        ID.setVisible(true);
        PauseTransition pt = new PauseTransition(Duration.seconds(1.5));
        FadeTransition ft = new FadeTransition(Duration.seconds(1), ID);
        SequentialTransition sequentialTransition = new SequentialTransition(pt, ft);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        sequentialTransition.play();
        ft.setOnFinished(event -> ID.setVisible(false));
    }

    @FXML
    void setTipButton() {
        Receipt currentReceipt = getCurrentReceipt();
        //Checks if tipField are Numbers or not
        if (tipField.getText().matches("[0-9]+")){
            tipSuccessful.setText("Erfolgreich angenommen!");
            tipSuccessful.setStyle("-fx-text-fill: #4fc62b");
            addAnimation(tipSuccessful);
        }else {
            try {
                tipSuccessful.setText("Bitte geben Sie eine Zahl ein!");
                tipSuccessful.setStyle("-fx-text-fill: #a82b2b");
                addAnimation(tipSuccessful);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        currentReceipt.setTip(Double.parseDouble(tipField.getText()));
    }

    @FXML
    void payCard() {
        Receipt currentReceipt = getCurrentReceipt();
        String payed = df.format((Double.parseDouble(currentReceipt.getTotal()) + currentReceipt.getTip()));

        receiptPayAmount.setText(payed + getCurrency());
        restMoneyLabelSuccess.setVisible(false);
        currentReceipt.setChangeMoney((double) 0);
        paymentSuccessfulPane.setVisible(true);
        transitionToBlackAll();
    }

    @FXML
    void payCash() {
        Receipt currentReceipt = getCurrentReceipt();

        paymentTotalBeforeAllLabel.setText("Gesamtsumme inkl. MWSt: " + currentReceipt.getTotal() + getCurrency());
        paymentTipLabel.setText("Trinkgeld: " + currentReceipt.getTip() + getCurrency());
        paymentTotalLabel.setText("Gesamtsumme inkl. Trinkgeld u. MWSt: " + currentReceipt.getTotalWithTip() + getCurrency());

        restMoneyLabelSuccess.setVisible(true);
        payCashPane.setVisible(true);
    }

    @FXML
    void confirmPaymentCash() {
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
                transitionToBlackAll();
            }

            paymentSuccessfulPane.setVisible(true);

        } else {
            addAnimation(errorLabel);
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
        transitionToBlackAll();
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
            imagePane.setStyle(String.format("-fx-background-image: url(\"%s\");", item.getProductImageUrl()));
            Label productTitleLabel = new Label(item.getProductTitle());
            Label productPriceInGrid = new Label(df.format(item.getProductPrice()) + " " + getCurrency());
            Button addButton = new Button();
            Button removeButton = new Button();

            addButton.getStyleClass().add("plus");
            removeButton.getStyleClass().add("minus");
            addButton.getStyleClass().add("cartOptions-l");
            removeButton.getStyleClass().add("cartOptions-r");

            // Sets size of new elements
            imagePane.setMinSize(240, 135);
            imagePane.setMaxSize(240, 135);
            addButton.setMinSize(35, 40);
            removeButton.setMinSize(35, 40);
            productTitleLabel.setMinHeight(30);
            productTitleLabel.setAlignment(Pos.CENTER_LEFT);

            // sets positions of new elements
            HBox buttonBox = new HBox();
            HBox emptyHBox = new HBox();
            buttonBox.getChildren().addAll(addButton, removeButton);
            buttonBox.setMinSize(50,40);
            addButton.setAlignment(Pos.CENTER);
            removeButton.setAlignment(Pos.CENTER);
            buttonBox.setAlignment(Pos.BASELINE_CENTER);
            VBox productPane = new VBox();
            productPane.setAlignment(Pos.CENTER);
            productPriceInGrid.setStyle("-fx-font-size: 15;-fx-font-weight: 700;-fx-text-fill: black");
            productTitleLabel.setStyle("-fx-font-size: 17;-fx-font-weight: 600;-fx-text-fill: black");
            HBox.setHgrow(emptyHBox, Priority.ALWAYS);
            HBox hbox = new HBox(productTitleLabel);
            HBox hbox2 = new HBox(productPriceInGrid, emptyHBox, buttonBox);
            hbox2.setAlignment(Pos.CENTER_LEFT);
            VBox vbox = new VBox(hbox, hbox2);
            vbox.setPadding(new Insets(0,0,0,5));
            productPane.getChildren().addAll(imagePane, vbox);
            productTitleLabel.setTooltip(createToolTip(item.productDescription));
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
            for (int i = 0;i<productsList.size(); i++) {
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
        categorySettingPane.setVisible(false);
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
    void setupBill() {
        setAllSettingsPanesInvisible();
        billSettingsPane.setVisible(true);
        updateBillInfo();
    }

    @FXML
    void saveBillinfo() throws IOException {
        if (!newAdressField.getText().isEmpty()){
            setAddress(newAdressField.getText());
        }
        if (!newTelField.getText().isEmpty()){
            setTel(newTelField.getText());
        }
        if (!newMessageField.getText().isEmpty()){
            setMessage(newMessageField.getText());
        }

        newAdressField.clear();
        newTelField.clear();
        newMessageField.clear();

        if (Objects.equals(getAddress(), "Adresse: ")){
            setAddress("");
        }
        if (Objects.equals(getTel(), "Telefon: ")){
            setTel("");
        }
        showPopup("Belegdaten wurden aktualisiert");
        updateBillInfo();
    }

    void updateBillInfo() {
        previewAdress.setText("Adresse: " + getAddress());
        previewTel.setText("Telefon: " + getTel());
        previewMessage.setText(getMessage());
    }

    // SYSTEM SETTINGS TAB METHODS
    @FXML
    void systemSettingsChangeCurrency() throws IOException {
        String newCurrency = String.valueOf(systemNewCurrencySelector.getValue().getSymbol());
        setCurrency(newCurrency);
        setValue("currency", String.valueOf(newCurrency));
        showPopup("Währung wurde auf \"" + systemNewCurrencySelector.getValue().getSymbol() + "\" aktualisiert");
        updateReceiptPane();
        updateBill();
        addProductElementsToGrid(GridPaneProducts, productsList);
    }

    @FXML
    void systemSettingsChangeBillNr() throws IOException {
        setReceiptNumber(Integer.parseInt(systemNewBillNrField.getText()));
        setValue("bill_nr", systemNewBillNrField.getText());
        showPopup("Belegnummer wurde auf \"" + systemNewBillNrField.getText() + "\" umgesetzt");
        updateBill();
        systemNewBillNrField.clear();
    }

    void updateBill(){
        Receipt currentReceipt = getCurrentReceipt();

        if (currentReceipt != null){
            billText.setText(currentReceipt.getFullReceipt());
            totalPrice.setText(currentReceipt.getTotal() + getCurrency());
            tableNumberText.setText(getCurrentTable().getTableNumberAsString());
            subTotalLabel.setText(currentReceipt.getSubtotal() + getCurrency());
            taxesTitleLabel.setText("Steuer (" + getTaxes() + "%)");
            totalTaxesBill.setText(currentReceipt.calculateTaxesAmount() + getCurrency());

            emptyReceiptPane.setVisible(currentReceipt.getFullReceipt().equals(""));
            billScroll.setVisible(!currentReceipt.getFullReceipt().equals(""));
        }
    }

    @FXML
    void systemSettingsChangeTaxes() throws IOException {
        setTaxes(Double.parseDouble(systemNewTaxesField.getText()));
        setValue("taxes", systemNewTaxesField.getText());
        showPopup("Steuersatz wurde auf \"" + systemNewTaxesField.getText() + "%\" akutalisiert");
        updateBill();
    }

    // PRODUCT SETTINGS TAB METHODS
    @FXML
    void productsSettingsChangeName() {
        int currentProduct = productsListViewSettings.getSelectionModel().getSelectedIndex();
        String text = newProductName.getText();
        showPopup("Name vom Produkt \"" + productsListViewSettings.getSelectionModel().getSelectedItem() + "\" wurde akutalisiert");
        if (!Objects.equals(text, ""))  {
            productsList.get(currentProduct).setProductTitle(text);
        }
        updateProductsList(currentProduct);
        newProductName.clear();
    }

    @FXML
    void productsSettingsChangeURL() {
        int currentProduct = productsListViewSettings.getSelectionModel().getSelectedIndex();
        String text = newURL.getText();
        showPopup("Bild vom Produkt \"" + productsListViewSettings.getSelectionModel().getSelectedItem() + "\" wurde akutalisiert");
        if (!Objects.equals(text, ""))  {
            productsList.get(currentProduct).setProductImageUrl(text);
        }
        updateProductsList(currentProduct);
        newURL.clear();
    }

    @FXML
    void productsSettingsChangeDescription() {
        int currentProduct = productsListViewSettings.getSelectionModel().getSelectedIndex();
        String text = newProductDescription.getText();
        showPopup("Beschreibung vom Produkt \"" + productsListViewSettings.getSelectionModel().getSelectedItem() + "\" wurde akutalisiert");
        if (!Objects.equals(text, ""))  {
            productsList.get(currentProduct).setProductDescription(text);
        }
        updateProductsList(currentProduct);
        newProductDescription.clear();
    }

    @FXML
    void productsSettingsChangePrice() {
        int currentProduct = productsListViewSettings.getSelectionModel().getSelectedIndex();
        double textFromNewProductPrice = Double.parseDouble(newProductPrice.getText());
        showPopup("Preis vom Produkt \"" + productsListViewSettings.getSelectionModel().getSelectedItem() + "\" wurde auf \"" + textFromNewProductPrice + "\" akutalisiert");
        productsList.get(currentProduct).setProductPrice(textFromNewProductPrice);
        updateProductsList(currentProduct);
        newProductPrice.clear();
    }

    @FXML
    void productsSettingsAddCategory() {
        int currentProduct = productsListViewSettings.getSelectionModel().getSelectedIndex();
        String text = categoryBoxSettings.getValue();
        showPopup("Kategorie\"" + text + "\"wurde zum Produkt \"" + productsListViewSettings.getSelectionModel().getSelectedItem() + "\" hinzugefügt.");
        if (!Objects.equals(text, ""))  {
            productsList.get(currentProduct).setCategory(text);
        }
        updateProductsList(currentProduct);
    }
    @FXML
    void productsSettingsRemoveCategory() {
        int currentProduct = productsListViewSettings.getSelectionModel().getSelectedIndex();
        String text = categoryBoxSettings.getValue();
        showPopup("Kategorie\"" + text + "\"wurde aus dem Produkt \"" + productsListViewSettings.getSelectionModel().getSelectedItem() + "\" entfernt.");
        productsList.get(currentProduct).removeCategory(text);
        updateProductsList(currentProduct);
    }

    @FXML
    void clearCategoryInSettings(){
        categoryListView.getSelectionModel().clearSelection();
    }
    @FXML
    void productsSettingsCreateNew() throws IOException {
        int currentProduct = productsListViewSettings.getSelectionModel().getSelectedIndex();

        String productName = newProductName.getText();
        String productPrice = newProductPrice.getText();
        String productDescription = newProductDescription.getText();
        String productURL = newURL.getText();
        String productCategory = categoryBoxSettings.getValue();

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
        showPopup("Neues Produkt \"" + productName + "\" wurde erstellt.");
        updateProductsList(currentProduct);
    }

    @FXML
    void productsSettingsDelete() {
        int currentProduct = productsListViewSettings.getSelectionModel().getSelectedIndex();
        showPopup("Produkt \"" + productsListViewSettings.getSelectionModel().getSelectedItem() +"\" wurde entfernt.");
        productsList.remove(currentProduct);
        updateProductsList(currentProduct);
    }

    private void updateProductsList(int currentProduct) {
        sortProducts();
        productsListViewSettings.getItems().clear();

        addProductElementsToGrid(GridPaneProducts, filterProductsByName(""));

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
        showPopup("Name vom Benutzer \"" + usersListView.getSelectionModel().getSelectedItem() +"\" wurde auf \"" + text + "\" akutalisiert.");
        if (!Objects.equals(text, ""))  {
            usersList.get(currentUser).setFullName(text);
        }
        updateUsersList(currentUser);
        newFullNameField.clear();
    }

    @FXML
    void userSettingsChangePassword() {
        int currentUser = usersListView.getSelectionModel().getSelectedIndex();
        String text = newPasswordField.getText();
        showPopup("Passwort vom Benutzer \"" + usersListView.getSelectionModel().getSelectedItem() +"\" wurde akutalisiert.");
        if (!Objects.equals(text, ""))  {
            usersList.get(currentUser).setPassword(text);
        }
        updateUsersList(currentUser);
        newPasswordField.clear();
    }

    @FXML
    void userSettingsChangeUsername() {
        int currentUser = usersListView.getSelectionModel().getSelectedIndex();
        String text = newUsernameField.getText();
        showPopup("Benutzername vom Benutzer \"" + usersListView.getSelectionModel().getSelectedItem() +"\" wurde auf \"" + text + "\" akutalisiert.");
        if (!Objects.equals(text, ""))  {
            usersList.get(currentUser).setUserName(text);
        }
        updateUsersList(currentUser);
        newUsernameField.clear();
    }

    @FXML
    void userSettingsToggleAdminRights() {
        int currentUser = usersListView.getSelectionModel().getSelectedIndex();
        usersList.get(currentUser).setAdmin(!usersList.get(currentUser).getIsAdmin());
        showPopup("Administrator-Rechte vom Benutzer \"" + usersListView.getSelectionModel().getSelectedItem() +"\" wurden akutalisiert.");
        updateUsersList(currentUser);
    }

    @FXML
    void userSettingsDeleteUser() {
        int currentUser = usersListView.getSelectionModel().getSelectedIndex();
        usersList.remove(currentUser);
        showPopup("Benutzer \"" + usersListView.getSelectionModel().getSelectedItem() +"\" wurde entfernt");
        updateUsersList(currentUser);
    }

    @FXML
    void resetPreview(){
        previewMessage.setText("");
        previewAdress.setText("Adresse: ");
        previewTel.setText("Telefon: ");
        try {
            setAddress(previewAdress.getText());
            setTel(previewTel.getText());
            setMessage(previewMessage.getText());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        newAdressField.clear();
        newTelField.clear();
        newMessageField.clear();

    }
    @FXML
    void userSettingsCreateNewUser() throws IOException {
        int currentUser = usersListView.getSelectionModel().getSelectedIndex();
        new User("Neuer Benutzer", false, "NeuerBenutzer");
        showPopup("Neuer Benutzer \"Neuer Benutzer\" wurde erstellt");
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
        arrayReceipts = new ArrayList<>();
        for (int i = 0; i < newSize; i++) {
            newArray[i] = new Tables();
        }
        arrayTables = newArray;
        // Clear Table ListView
        tablesListView.getItems().clear();
        initTables();
        // Recreating Tables ListView
        for (Tables arrayTable : arrayTables) {
            tablesListView.getItems().add(arrayTable.getTableNumberAsString());
        }
        setValue("tableCount", String.valueOf(newSize));
        showPopup("Tisch-Anzahl wurde auf \"" + newSize + "\" akutalisiert.");
        settingsInputField.clear();
    }

    @FXML
    void openSettings() {
        settingsTab.setVisible(!settingsTab.isVisible());
        productsPane.setVisible(!productsPane.isVisible());
        categoryBoxSettings.setValue("Kategorie");
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
    private void resetCategory(){
        addProductElementsToGrid(GridPaneProducts, productsList);
        categoryBoxMain.getSelectionModel().clearSelection();
        categoryBoxMain.setValue("Kategorie");
    }

    @FXML
    private void openCategorySettings(){
        setAllSettingsPanesInvisible();
        categorySettingPane.setVisible(true);
    }

    @FXML
    private void newCategoryAdd() throws IOException {
        String categoryName = categorySettingsField.getText();
        addCategory(categoryName);
        categorySettingsField.clear();
        showPopup("Neue Kategorie \"" + categoryName + "\" wurde erstellt");
        updateNewProductChoiceBox();
    }

    @FXML
    private void newCategoryRemove() throws IOException {
        String category = categoryListView.getSelectionModel().getSelectedItem();
        showPopup("Kategorie \"" + category + "\" wurde entfernt");
        deleteCategory(category);
        updateNewProductChoiceBox();
    }

    @FXML
    private void changeCategoryName() throws IOException {
        String previousName = categoryListView.getSelectionModel().getSelectedItem();
        getCategories().set(categoryListView.getSelectionModel().getSelectedIndex(), categoryNameField.getText());
        showPopup("Name von Kategorie \"" + previousName + "\" wurde auf " + categoryNameField.getText() + " akutalisiert");
        categoryNameField.clear();
        updateNewProductChoiceBox();
    }

    private void updateNewProductChoiceBox() throws IOException {
        categoriesToJSON();
        categoryBoxSettings.getItems().clear();
        categoryBoxSettings.getItems().addAll(getCategories());

        categoryBoxMain.getItems().clear();
        categoryBoxMain.getItems().addAll(getCategories());

        categoryListView.getItems().clear();
        categoryListView.getItems().addAll(getCategories());
    }

    private static Tooltip createToolTip(String text){
        Tooltip tt = new Tooltip();
        tt.setText(text);
        tt.setShowDelay(Duration.millis(100));
        tt.setHideDelay(Duration.ZERO);
        tt.setStyle("-fx-font: normal 14 Langdon; "
                + "-fx-base: black; "
                + "-fx-text-fill: white;");
        return tt;
    }

    private void transitionToGreen(){
        FadeTransition ft = new FadeTransition(Duration.millis(500), tablePaneCollector.get(getCurrentTable().getTableName() - 1));
        ft.setFromValue(0.75);
        ft.setToValue(1);
        if (!tablePaneCollector.get(getCurrentTable().getTableName() - 1).getStyleClass().contains("green")) {
            ft.play();
        }
        tablePaneCollector.get(getCurrentTable().getTableName() - 1).getStyleClass().add("green");
    }

    private void transitionToBlackSingle(){
        FadeTransition ft = new FadeTransition(Duration.millis(500), tablePaneCollector.get(getCurrentTable().getTableName() - 1));
        ft.setFromValue(0.75);
        ft.setToValue(1);
        if (tablePaneCollector.get(getCurrentTable().getTableName() - 1).getStyleClass().contains("green")){
            ft.play();
        }
        tablePaneCollector.get(getCurrentTable().getTableName() - 1).getStyleClass().remove("green");
    }

    private void showPopup(String text){
        popupText.setText(text);
        popupPane.setVisible(true);
    }

    @FXML
    void closePopup(ActionEvent event) {
        popupPane.setVisible(false);
    }

    private void transitionToBlackAll(){
        FadeTransition ft = new FadeTransition(Duration.millis(500), tablePaneCollector.get(getCurrentTable().getTableName() - 1));
        ft.setFromValue(0.75);
        ft.setToValue(1);
        if (tablePaneCollector.get(getCurrentTable().getTableName() - 1).getStyleClass().contains("green")){
            ft.play();
        }
        tablePaneCollector.get(getCurrentTable().getTableName() - 1).getStyleClass().removeAll("green");
    }

    private void initTables() {
        if (run) {
            int col = 0;
            int row = 0;
            tablesGridPane.getChildren().clear();
            tablePaneCollector.clear();
            for (Tables arrayTable : arrayTables) {   //Parsing Tables
                Pane tablePane = new Pane();
                Label tableTitle = new Label("Tisch " + arrayTable.getTableName());
                Label tableVisitors = new Label("1");
                Pane visitorIcon = new Pane();

                tablePane.getStyleClass().add("tablePane");
                tableTitle.getStyleClass().add("tableText");
                visitorIcon.getStyleClass().add("visitorIcon");
                tableVisitors.getStyleClass().add("tableText");

                tablePane.setMinWidth(150);
                tablePane.setMinHeight(150);

                tableTitle.setLayoutX(20);
                tableTitle.setLayoutY(20);

                visitorIcon.setMinHeight(13.5);
                visitorIcon.setMinWidth(16);

                visitorIcon.setLayoutX(20);
                visitorIcon.setLayoutY(110);

                tableVisitors.setLayoutX(42);
                tableVisitors.setLayoutY(106);


                tablePane.getChildren().addAll(tableTitle, visitorIcon, tableVisitors);

                //Adds Animation to button when clicked
                ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), tablePane);
                scaleTransition.setFromX(1);
                scaleTransition.setFromY(1);
                scaleTransition.setToX(0.95);
                scaleTransition.setToY(0.95);
                scaleTransition.setDuration(Duration.millis(100));
                scaleTransition.setOnFinished(event -> {
                    tablePane.setScaleX(1);
                    tablePane.setScaleY(1);
                });
                tablePane.setOnMouseClicked(event -> {
                    scaleTransition.play();
                    tablesListView.getSelectionModel().select(arrayTable.getTableName()-1);
                    updateBill();
                });

                tablePaneCollector.add(tablePane);
                tablesGridPane.add(tablePane, col,row);
                tablesListView.getItems().add(arrayTable.getTableNumberAsString());
                //System.out.println("[" + row + "," + col + "]");
                col++;
                if (col == 2){
                    row++;
                    col = 0;
                }
                tablesGridPane.setMinHeight(170 * (row+1));
            }
        }
    }

    @FXML
    public void initialize(URL arg0, ResourceBundle arg1){
        updateReceiptPane();
        updateBillInfo();
        updateBill();
        dateSetter();
        tableScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e ->
                dateSetter()
        ),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();


        categoryBoxMain.getItems().addAll(getCategories());
        categoryBoxMain.setValue("Kategorie");
        categoryBoxMain.getSelectionModel().selectedIndexProperty().addListener((observableValue, old_selection, new_selection) -> {
            addProductElementsToGrid(GridPaneProducts, filterProductsByCategory(categoryBoxMain.getItems().get((Integer) new_selection)));
            System.out.println(categoryBoxMain.getItems().get((Integer) new_selection));
        });


        //Creates ToolTips
        resetBill.setTooltip(createToolTip("Löscht die aktuelle Rechnung!"));
        resetCategorySettings.setTooltip(createToolTip("Refreshed Kategorien & Auswahl"));
        resetCategoryTrash.setTooltip(createToolTip("Löscht alle Kategorien!"));
        resetPreview.setTooltip(createToolTip("Löscht Eingabe und Ansicht!"));
        resetCategoryMain.setTooltip(createToolTip("Setzt Produkt-Sortierung und Kategorie-Auswahl zurück!"));

        //Category ChoiceBox in Products-Settings
        categoryBoxSettings.setValue("Kategorien");
        categoryBoxSettings.getItems().addAll(getCategories());

        //CategorySettings
        categoryListView.getItems().addAll(getCategories());

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
        kellnerLabel.setText(getLoggedInUserName());
        userSettings.setVisible(isAdmin);
        tablesSettings.setVisible(isAdmin);
        productsSettings.setVisible(isAdmin);

        // Hide Admin Settings Button if not admin
        if(!isAdmin){
            adminButton.setVisible(false);
        }

        dateSetter();
        addProductElementsToGrid(GridPaneProducts, productsList);

       initTables();

        for (Product product : productsList) {
            productsListViewSettings.getItems().add(product.getProductTitle());
        }

        for (User user : usersList) {
            usersListView.getItems().add(user.getName());
        }



        // Listens if categoryBoxMain has changed
        resetCategoryMain.setVisible(false);
        categoryBoxMain.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            resetCategoryMain.setVisible(!Objects.equals(newValue, "Kategorie"));
        });

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
                productImagePreview.setStyle(String.format("-fx-background-image: url(\"%s\"); -fx-background-size: contain; -fx-background-repeat: no-repeat; -fx-background-position: center center;", productsList.get(currentProduct).getProductImageUrl()));
            }
        });

        // Create a Search Field Listener and update Products Grid
        searchField.textProperty().addListener((observable, oldValue, newValue) -> addProductElementsToGrid(GridPaneProducts, filterProductsByName(newValue)));
    }
}