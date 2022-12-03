package at.ac.fhcampuswien.xsolutions;

import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;

import static at.ac.fhcampuswien.xsolutions.App.arrayBills;

public class Tables {
    private static int billNumber = ThreadLocalRandom.current().nextInt(12415, 28174);
    private double randomDouble = ThreadLocalRandom.current().nextInt(1, 3000);
    private static double amountBeforeTaxes;
    final static int TAXES = 20;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private static int count;
    public static String[] tablesArray = new String[count];

    public Tables(){
        count++;
        Bills bill = new Bills();

        System.out.println(getTableNumberAsString());
        getBill();
    }

    public static Bills[] generateBill(){
        arrayBills = new Bills[count];
        arrayBills[count] = new Bills();
        return arrayBills;
    }

    public static double getAmountBeforeTaxes() {
        System.out.println(amountBeforeTaxes);
        return amountBeforeTaxes;
    }

    public String reformatAmountBeforeTaxes() {
        if (amountBeforeTaxes % 1 == 0) {
            return String.format("%.0f", amountBeforeTaxes) + "$";
        } else {
            return Double.toString(amountBeforeTaxes) + "$";
        }
    }

    private static int getTableNumberAsInt(){
        return count;
    }

    private static String getTableNumberAsString(){
        return "Tisch " + getTableNumberAsInt();
    }

    public static String getTableNumberAsString(int number){
        return "Tisch " + number;
    }

    public static int getCount() {
        return count;
    }

    public String getBill() {
        return Bills.getBill();
    }

}