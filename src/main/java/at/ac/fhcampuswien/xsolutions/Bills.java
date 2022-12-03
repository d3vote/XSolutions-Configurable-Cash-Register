package at.ac.fhcampuswien.xsolutions;
import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;

public class Bills {
    private static int billNumber = ThreadLocalRandom.current().nextInt(12415, 28174);
    private double randomDouble = ThreadLocalRandom.current().nextInt(1, 3000);
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private static double amountBeforeTaxes;
    private static double amountAfterTaxes;


    final static int TAXES = 20;

    public Bills(){
        amountBeforeTaxes = Double.parseDouble(df.format(randomDouble));
        billNumber++;
    }
    public static String getBill(){
        System.out.println(billNumberToString() + System.lineSeparator() +
                amountBeforeTaxesToString() + System.lineSeparator() +
                taxesToString() + System.lineSeparator() +
                totalAmountToString() + System.lineSeparator());

        return (billNumberToString() + System.lineSeparator() +
                amountBeforeTaxesToString() + System.lineSeparator() +
                taxesToString() + System.lineSeparator() +
                totalAmountToString() + System.lineSeparator());
    }

    public static double getBillNumber() {
        return billNumber;
    }

    public static double getAmountBeforeTaxes() {
        return amountBeforeTaxes;
    }

    public static double getAmountAfterTaxes() {
        return amountAfterTaxes;
    }

    public static double calculateAmountAfterTaxes(){
        return (amountBeforeTaxes * TAXES) / 100 + amountBeforeTaxes;
    }

    public static String billNumberToString(){
        return "Bill #" + billNumber;
    }

    public static String amountBeforeTaxesToString() {
        return "Amount: " + amountBeforeTaxes + "$";
    }

    public static String taxesToString(){
        return  "MWSt: " + TAXES + "%";
    }

    public static String totalAmountToString(){
        return "Total amount: " + calculateAmountAfterTaxes() + "$";

    }

}