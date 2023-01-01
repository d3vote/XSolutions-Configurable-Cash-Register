package at.ac.fhcampuswien.xsolutions;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static at.ac.fhcampuswien.xsolutions.App.getDate;
import static at.ac.fhcampuswien.xsolutions.Configurator.setValue;

public class Receipt {
    public Map<Product, Integer> productCounter;
    private double subtotal;
    private static int initialReceiptNumber;
    private static double taxes;
    private static String currency;
    private double tip;
    private static String address;
    private static String tel;
    private static String message;
    public static final DecimalFormat df = new DecimalFormat("0.00");
    private ArrayList<Product> usedProducts;
    private Tables table;
    public static List<Receipt> arrayReceipts = new ArrayList<>();

    public Receipt(Tables table){
        this.table = table;
        productCounter = new HashMap<>();
        usedProducts = new ArrayList<>();

        arrayReceipts.add(this);
    }

    public String getSubtotal() {
        return df.format(subtotal);
    }

    public Map<Product, Integer> getProductCounter(){
        return productCounter;
    }


    public String getFullReceipt(){
        StringBuilder productsTotal = new StringBuilder();
        // Clear productsTotal List
        productsTotal.delete(0, productsTotal.length());

        // Recreate a new productsTotal List
        for (Product usedProduct : usedProducts){
            productsTotal.append("\n").append(usedProduct.getProductTitle()).append(" x ").append(productCounter.get(usedProduct)).append(": ").append(currency).append(df.format(usedProduct.getProductPrice() * productCounter.get(usedProduct)));
        }

        if (table.getServersName() != null){
            return "Datum: " + getDate() + System.lineSeparator() +
                    "Tisch: " + table.getTableNumberAsString() + System.lineSeparator() +
                    "Kellner: " + table.getServersName() + System.lineSeparator() +

                    System.lineSeparator() +
                    "Produkte bestellt: " + System.lineSeparator() +
                    productsTotal + System.lineSeparator() +
                    System.lineSeparator() +

                    "Zwischensumme: " + getSubtotal() + currency + System.lineSeparator() +
                    "Steuer (" + taxes + "%): " + calculateTaxesAmount() + currency + System.lineSeparator() +
                    "Gesamtsumme inkl. Trinkgeld: " + getTotalWithTip() + currency + System.lineSeparator();
        } else
            return "Rechnung ist leer";
    }

    public String getShortReceipt() throws IOException {
        initialReceiptNumber++;
        setValue("bill_nr", String.valueOf(initialReceiptNumber));

        StringBuilder productsTotal = new StringBuilder();
        // Clear productsTotal List
        productsTotal.delete(0, productsTotal.length());

        // Recreate a new productsTotal List
        for (Product usedProduct : usedProducts){
            productsTotal.append(usedProduct.getProductTitle()).append(" x ").append(productCounter.get(usedProduct)).append(": ").append(currency).append(df.format(usedProduct.getProductPrice() * productCounter.get(usedProduct))).append("\n");
        }

        return productsTotal.toString();
    }

    public void addUsedProducts(Product item) {
        if (!usedProducts.contains(item)){
            usedProducts.add(item);
        }
    }

    public void addToSubtotal(double num) {
        df.format(subtotal += num);
    }

    public String calculateTaxesAmount(){
        double taxesAmount = subtotal * (taxes /100);
        return df.format(taxesAmount);
    }

    public ArrayList<Product> getUsedProducts() {
        return usedProducts;
    }

    public void removeUsedProducts(Product item) {
        usedProducts.remove(item);
    }

    public void subtractFromSubtotal(double num) {
        // Check if subtracting num from subtotal would result in a negative value
        if (subtotal - num >= 0) {
            df.format(subtotal -= num);
        }
    }

    public static void setTaxes(double taxes) {
        Receipt.taxes = taxes;
    }

    public static String getCurrency() {
        return currency;
    }

    public static void setCurrency(String currency) {
        Receipt.currency = currency;
    }

    public String getTotal() {
        return df.format(subtotal * (taxes / 100) + subtotal);
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTip() {
        return tip;
    }

    public void setTip(double tip) {
        this.tip = tip;
    }

    public int getInitialReceiptNumber() {
        return initialReceiptNumber;
    }

    private void resetUsedProducts() {
        usedProducts = new ArrayList<>();
        productCounter = new HashMap<>();
    }

    public static void setReceiptNumber(int value) {
        initialReceiptNumber = value;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String newAddress) throws IOException {
        address = newAddress;
        setValue("bill_address", newAddress);
    }

    public static String getTel() {
        return tel;
    }

    public static void setTel(String newTel) throws IOException {
        tel = newTel;
        setValue("bill_tel", newTel);
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String newMessage) throws IOException {
        message = newMessage;
        setValue("bill_msg", newMessage);
    }

    public void closeReceipt() {
        tip = 0;
        table.resetServer();
        resetUsedProducts();
        setSubtotal(0);
    }

    public String getTotalWithTip(){
        return df.format(Double.parseDouble(getTotal()) + getTip());
    }
}
