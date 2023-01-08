package at.ac.fhcampuswien.xsolutions;

import java.io.*;
import java.util.HashMap;

public class ReceiptHistory implements Serializable {

    private static HashMap<Integer, SimpleReceipt> receiptsHistory = new HashMap<>();

    public static void loadReceiptHistory() {
        try (FileInputStream fis = new FileInputStream("receipts.dat");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            receiptsHistory = (HashMap<Integer, SimpleReceipt>) ois.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addToReceiptHistory(SimpleReceipt receipt) {
        receiptsHistory.put(Integer.valueOf(receipt.getCount()), receipt);
        try (FileOutputStream fos = new FileOutputStream("receipts.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(receiptsHistory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SimpleReceipt getFromReceiptHistory(int index){
        if (receiptsHistory.containsKey(index)) return receiptsHistory.get(index);
        return null;
    }
}
