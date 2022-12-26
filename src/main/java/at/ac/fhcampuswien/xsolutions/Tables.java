package at.ac.fhcampuswien.xsolutions;

import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;


public class Tables {
    private static int billNumber = ThreadLocalRandom.current().nextInt(12415, 28174);
    private double randomDouble = ThreadLocalRandom.current().nextInt(1, 3000);
    private double amountBeforeTaxes;
    private double amountAfterTaxes;
    private int tableNumber;
    final static double TAXES_MULTIPLIER = 1.20;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private static int count;

    public Tables(){
        count++;
        billNumber++;
        this.tableNumber = count;
        this.amountBeforeTaxes = Double.parseDouble(df.format(randomDouble));
        this.amountAfterTaxes = Double.parseDouble(df.format(amountBeforeTaxes * TAXES_MULTIPLIER));
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

    public String getBill(){
        return "Amount before Taxes: " + amountBeforeTaxes + "$" + System.lineSeparator()
                + "Amount after Taxes: " + amountAfterTaxes + "$" + System.lineSeparator();
    }
}