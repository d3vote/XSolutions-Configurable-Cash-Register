package at.ac.fhcampuswien.xsolutions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static at.ac.fhcampuswien.xsolutions.Tables.setCurrency;

public class Configurator {
    private static final String CONFIG_FILE = "src/main/resources/config.txt";
    private static int tableCount;
    public static void readConfigTableCount() throws IOException {
        // Read the contents of the config.txt file
        List<String> lines = Files.readAllLines(Paths.get(CONFIG_FILE));
        // Iterate through the lines and split each line into a key-value pair
        for (String line : lines) {
            String[] parts = line.split("=");
            String key = parts[0].trim();
            String value = parts[1].trim();

            // If the key is "tableCount", return the corresponding value as an integer
            if (key.equals("tableCount")) {
                tableCount = Integer.parseInt(value);
            }
        }
    }

    public static void readConfigCurrency() throws IOException {
        // Read the contents of the config.txt file
        List<String> lines = Files.readAllLines(Paths.get(CONFIG_FILE));
        // Iterate through the lines and split each line into a key-value pair
        for (String line : lines) {
            String[] parts = line.split("=");
            String key = parts[0].trim();
            String value = parts[1].trim();

            // If the key is "currency", return the corresponding value
            if (key.equals("currency")) {
                setCurrency(value);
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

    public static int getTableCount() {
        return tableCount;
    }
}