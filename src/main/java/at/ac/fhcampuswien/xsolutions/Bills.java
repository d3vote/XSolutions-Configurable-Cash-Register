package at.ac.fhcampuswien.xsolutions;
import java.util.concurrent.ThreadLocalRandom;

public class Bills {
    private static int bill = ThreadLocalRandom.current().nextInt(12415, 28174);
    private static int amountBeforeTaxes;
    final static int TAXES = 20;
    private int totalAmount;

    public Bills(){
        amountBeforeTaxes = ThreadLocalRandom.current().nextInt(1, 3000);
        bill++;
    }
    public void getBill(){
        System.out.println("Bill #" + bill + System.lineSeparator() +
                "Amount: " + amountBeforeTaxes + "$" + System.lineSeparator() +
                "MWSt: " + TAXES + "%" + System.lineSeparator() +
                "Total amount: " + calculateAmountAfterTaxes() + "$" + System.lineSeparator());
    }

    public int calculateAmountAfterTaxes(){
        totalAmount = (amountBeforeTaxes * TAXES) / 100 + amountBeforeTaxes;
        return totalAmount;
    }

}