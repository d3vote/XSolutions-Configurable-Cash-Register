package at.ac.fhcampuswien.xsolutions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static at.ac.fhcampuswien.xsolutions.Receipt.*;

public class Configurator {
    private static final String CONFIG_FILE = "src/main/resources/config.txt";
    private static int tableCount;

    public static void readConfig() throws IOException {
        // Read the contents of the config.txt file
        List<String> lines = Files.readAllLines(Paths.get(CONFIG_FILE));
        // Iterate through the lines and split each line into a key-value pair
        for (String line : lines) {
            String[] parts = line.split("=");
            String key = parts[0].trim();
            String value = parts[1].trim();

            if (key.equals("tableCount")) {
                tableCount = Integer.parseInt(value);
            }
            if (key.equals("currency")) {
                setCurrency(value);
            }
            if (key.equals("taxes")) {
                setTaxes(Double.parseDouble(value));
            }
            if (key.equals("bill_msg")) {
                setMessage(value);
            }
            if (key.equals("bill_tel")) {
                setTel(value);
            }
            if (key.equals("bill_address")) {
                setAddress(value);
            }
            if (key.equals("bill_nr")) {
                setReceiptNumber(Integer.parseInt(value));
            }
        }
    }

    public static void setValue(String key, String value) throws IOException {
        // Read the contents of the config.txt file
        List<String> lines = Files.readAllLines(Paths.get(CONFIG_FILE));

        // Iterate through the lines and split each line into a key-value pair
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split("=");
            String k = parts[0].trim();
            String v = parts[1].trim();

            // If the key is "tableCount" or "currency", update the value
            if (k.equals(key)) {
                lines.set(i, key + " = " + value);
            }
        }

        // Write the updated list of lines back to the config.txt file
        Files.write(Paths.get(CONFIG_FILE), lines);
    }

    public static int getTableConfig() {
        return tableCount;
    }
}