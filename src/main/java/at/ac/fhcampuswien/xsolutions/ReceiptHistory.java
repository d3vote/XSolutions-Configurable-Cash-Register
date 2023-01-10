package at.ac.fhcampuswien.xsolutions;

import java.io.*;
import java.util.HashMap;

public class ReceiptHistory implements Serializable {
    private static HashMap<Integer, SimpleReceipt> receiptsHistory = new HashMap<>();

    /** All Receipts (SimpleReceipts) are serialized to a .dat file, so it can be found later in the Receipt Search Tab*/
    public static void loadReceiptHistory() {
        try (FileInputStream fis = new FileInputStream("receipts.dat");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            receiptsHistory = (HashMap<Integer, SimpleReceipt>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Adds a new Receipt to the .dat file */
    public static void addToReceiptHistory(SimpleReceipt receipt) {
        receiptsHistory.put(Integer.valueOf(receipt.getCount()), receipt);
        try (FileOutputStream fos = new FileOutputStream("receipts.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(receiptsHistory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** @return Receipt by its index (key)*/
    public static SimpleReceipt getFromReceiptHistory(int index){
        if (receiptsHistory.containsKey(index)) return receiptsHistory.get(index);
        return null;
    }
}
