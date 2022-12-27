package at.ac.fhcampuswien.xsolutions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Tables {
    private static int billNumber;
    public Map<Product, Integer> productCounter;
    private double amountAfterTaxes;
    private int tableNumber;
    final static double TAXES_MULTIPLIER = 1.20;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private static int count;
    private ArrayList<Product> usedProducts;

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

    public String getAmountAfterTaxes() {
        return String.valueOf(amountAfterTaxes);
    }

    public Map<Product, Integer> getProductCounter(){
        return productCounter;
    }

    public String getBill(){
        StringBuilder productsTotal = new StringBuilder();
        // Clear productsTotal List
        productsTotal.delete(0, productsTotal.length());

        // Recreate a new productsTotal List
        for (Product usedProduct : usedProducts){
            productsTotal.append("\n").append(usedProduct.getProductTitle()).append(" x ").append(productCounter.get(usedProduct)).append(" $").append(usedProduct.getProductPrice() * productCounter.get(usedProduct));
        }

        return "Table: " + tableNumber + System.lineSeparator() + productsTotal;
    }

    public void addUsedProducts(Product item) {
        if (!usedProducts.contains(item)){
            usedProducts.add(item);
        }
    }

    public void addToTotal(double num) {
        amountAfterTaxes += num;
    }
}
