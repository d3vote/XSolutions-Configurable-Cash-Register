package at.ac.fhcampuswien.xsolutions;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

import static at.ac.fhcampuswien.xsolutions.App.getDate;
import static at.ac.fhcampuswien.xsolutions.Configurator.readConfigCurrency;


public class Tables {
    private static int billNumber;
    public Map<Product, Integer> productCounter;
    private double subtotal;
    private int tableNumber;
    private static double taxes = 20;
    private static String currency;

    static {
        try {
            readConfigCurrency();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final DecimalFormat df = new DecimalFormat("0.00");
    private static int count;
    private ArrayList<Product> usedProducts;
    private String serverName;
    private boolean serverSet = false;

    public Tables(){
        count++;
        billNumber++;
        this.tableNumber = count;
        productCounter = new HashMap<>();
        usedProducts = new ArrayList<>();
    }

    public static void setTablesCount(int count) {
        Tables.count = count;
    }

    public static int getCount(){
        return count;
    }

    public String getTableNumberAsString(){
        return "Tisch " + tableNumber;
    }

    public String getSubtotal() {
        return df.format(subtotal);
    }

    public Map<Product, Integer> getProductCounter(){
        return productCounter;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        if (serverName != null && !serverSet){
            serverSet = true;
            this.serverName = serverName;
        }
    }

    public String getBill(){
        StringBuilder productsTotal = new StringBuilder();
        // Clear productsTotal List
        productsTotal.delete(0, productsTotal.length());

        // Recreate a new productsTotal List
        for (Product usedProduct : usedProducts){
            productsTotal.append("\n").append(usedProduct.getProductTitle()).append(" x ").append(productCounter.get(usedProduct)).append(": ").append(currency).append(df.format(usedProduct.getProductPrice() * productCounter.get(usedProduct)));
        }

        if (getServerName() != null){
            return "Datum: " + getDate() + System.lineSeparator() +
                    "Tisch: " + tableNumber + System.lineSeparator() +
                    "Kellner: " + getServerName() + System.lineSeparator() +

                    System.lineSeparator() +
                    "Produkte bestellt: " + System.lineSeparator() +
                    productsTotal + System.lineSeparator() +
                    System.lineSeparator() +

                    "Zwischensumme: " + getSubtotal() + currency + System.lineSeparator() +
                    "Steuer (" + taxes + "%): " + calcuateTaxesAmount() + currency + System.lineSeparator() +
                    "Gesamtsumme: " + getTotal() + currency + System.lineSeparator();
        } else
            return "Rechnung ist leer";
    }

    public void addUsedProducts(Product item) {
        if (!usedProducts.contains(item)){
            usedProducts.add(item);
        }
    }

    public void addToSubtotal(double num) {
        df.format(subtotal += num);
    }

    public String calcuateTaxesAmount(){
        double taxesAmount = subtotal * (taxes /100);
        return df.format(taxesAmount);
    }

    public static void setTaxes(double taxes) {
        Tables.taxes = taxes;
    }

    public static String getCurrency() {
        return currency;
    }

    public static void setCurrency(String currency) {
        Tables.currency = currency;
    }

    public String getTotal() {
        return df.format(subtotal * (taxes / 100) + subtotal);
    }
}