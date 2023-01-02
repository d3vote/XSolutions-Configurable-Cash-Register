package at.ac.fhcampuswien.xsolutions;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static at.ac.fhcampuswien.xsolutions.Configurator.setValue;


public class Receipt{
    private double subtotal;
    private static int initialReceiptNumber;
    private int count;
    private static double taxes;
    private static String currency;
    private double tip;
    private static String address;
    private static String tel;
    private static String message;
    private Tables table;
    private String date;
    private String time;
    private double changeMoney;
    private double amountPayed;
    public static final DecimalFormat df = new DecimalFormat("0.00");
    private ArrayList<Product> usedProducts;
    public static List<Receipt> arrayReceipts = new ArrayList<>();
    private Map<Product, Integer> productCounter;


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
            productsTotal.append(usedProduct.getProductTitle()).append(" x ").append(productCounter.get(usedProduct)).append(": ").append(currency).append(df.format(usedProduct.getProductPrice() * productCounter.get(usedProduct))).append("\n");
        }

        if (table.getServersName() != null){
            return "Produkte bestellt: " + System.lineSeparator() + productsTotal;
        } else
            return "";
    }

    public String getShortReceipt() {

        date = App.getDate();
        time = java.time.format.DateTimeFormatter.ofPattern("HH:mm").format(java.time.LocalTime.now());

        StringBuilder productsTotal = new StringBuilder();
        // Clear productsTotal List
        productsTotal.delete(0, productsTotal.length());

        // Recreate a new productsTotal List
        for (Product usedProduct : usedProducts){
            productsTotal.append(usedProduct.getProductTitle()).append(" x ").append(productCounter.get(usedProduct)).append(": ").append(df.format(usedProduct.getProductPrice() * productCounter.get(usedProduct))).append(currency).append("\n");
        }

        return productsTotal.toString();
    }

    public void increaseBillNumber() throws IOException {
        initialReceiptNumber++;
        count = initialReceiptNumber;
        setValue("bill_nr", String.valueOf(initialReceiptNumber));
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

    public int getCount() {
        return count;
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

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public double getAmountPayed() {
        return amountPayed;
    }

    public double getChangeAmount() {
        return changeMoney;
    }

    public void setChangeMoney(Double changeMoney) {
        this.changeMoney = changeMoney;
    }

    public void setAmountPayed(Double amountPayed) {
        this.amountPayed = amountPayed;
    }

    public static double getTaxes() {
        return taxes;
    }
}
