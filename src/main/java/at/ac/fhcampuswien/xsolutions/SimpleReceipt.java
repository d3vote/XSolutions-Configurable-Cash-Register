package at.ac.fhcampuswien.xsolutions;

import java.io.Serializable;
import java.util.Objects;

import static at.ac.fhcampuswien.xsolutions.ReceiptHistory.addToReceiptHistory;

public class SimpleReceipt implements Serializable {
    private String count;
    private String date;
    private String time;
    private String productsList;
    private String total;
    private String payed;
    private String changeMoney;

    /** Constructor of SimpleReceipt exists to simplify the Receipt saving and make it quicker.
     * @param count - Receipt number
     * @param date - Date of receipt creation
     * @param time - Time of receipt creation
     * @param productsList - Short list of products
     * @param total - Total receipt price
     */
    public SimpleReceipt(String count, String date, String time, String productsList, String total, String payed, String changeMoney) {
        this.count = count;
        this.date = date;
        this.time = time;
        this.productsList = productsList;
        this.total = total;

        if (!Objects.equals(payed, "0")) {
            this.payed = total;
        } else {
            this.payed = payed;
        }

        if (Objects.equals(changeMoney,null)) {
            this.changeMoney = "0.00";
        } else {
            this.changeMoney = changeMoney;
        }

        addToReceiptHistory(this);
    }

    public String getCount() {
        return count;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getProductsList() {
        return productsList;
    }

    public String getTotal() {
        return total;
    }

    public String getPayed() {
        return payed;
    }

    public String getChangeMoney() {
        return changeMoney;
    }
}
