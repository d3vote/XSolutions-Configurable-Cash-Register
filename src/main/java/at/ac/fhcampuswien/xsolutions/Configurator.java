package at.ac.fhcampuswien.xsolutions;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static at.ac.fhcampuswien.xsolutions.Receipt.*;

public class Configurator {
    private static String appDataPath = System.getenv("APPDATA");
    private static File xSolutionsPosFolder = new File(appDataPath + File.separator + "XSolutions-POS");
    private static File CONFIG_FILE = new File(xSolutionsPosFolder + File.separator + "config.txt");
    private static File productsListPath = new File(xSolutionsPosFolder + File.separator + "productsList.json");
    private static File usersListPath = new File(xSolutionsPosFolder + File.separator + "usersList.json");
    private static int tableCount;

    /** Get AppData depending on the System OS.
     * If macOS: Path should be User/username/Library/Application Support
     * If Windows: Path should be %appdata%
     */
    static void getAppDataPath(){
        String systemName = System.getProperty("os.name");
        if(!(systemName.equals("Windows 11") || systemName.equals("Windows 10"))){
            appDataPath = System.getProperty("user.home") + "/Library/Application Support";
            xSolutionsPosFolder = new File(appDataPath + File.separator + "XSolutions-POS");
            CONFIG_FILE = new File(xSolutionsPosFolder + File.separator + "config.txt");
            productsListPath = new File(xSolutionsPosFolder + File.separator + "productsList.json");
            usersListPath = new File(xSolutionsPosFolder + File.separator + "usersList.json");
        }
        System.out.println(appDataPath);
    }

    /** This code is responsible for creating files in the "AppData" directory. The data for config.txt is hardcoded and for productsList and usersList it is being pulled from a raw pastebin link.
     */
    public static void createAppData() throws IOException {
        getAppDataPath();
        if (!xSolutionsPosFolder.exists()) {

            xSolutionsPosFolder.mkdir();
            CONFIG_FILE.createNewFile();
            usersListPath.createNewFile();
            productsListPath.createNewFile();

            BufferedWriter writerConfigFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(CONFIG_FILE), StandardCharsets.UTF_8));
            writerConfigFile.write("tableCount = 30");
            writerConfigFile.newLine();
            writerConfigFile.write("currency = \u20AC");
            writerConfigFile.newLine();
            writerConfigFile.write("taxes = 20");
            writerConfigFile.newLine();
            writerConfigFile.write("bill_nr = 0");
            writerConfigFile.newLine();
            writerConfigFile.write("bill_address = Favoritenstra\u00DFe 226");
            writerConfigFile.newLine();
            writerConfigFile.write("bill_tel = 01 60668776600");
            writerConfigFile.newLine();
            writerConfigFile.write("bill_msg = Danke f\u00FCr Ihren Besuch!");
            writerConfigFile.close();


            URL urlProductsList = new URL("https://pastebin.com/raw/uaTdJJXf");
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlProductsList.openStream(), StandardCharsets.UTF_8));
            BufferedWriter writerProducts = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(productsListPath), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                writerProducts.write(line);
                writerProducts.newLine();
            }
            reader.close();
            writerProducts.close();


            URL urlUserList = new URL("https://pastebin.com/raw/1TTGBvmp");
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(urlUserList.openStream(), StandardCharsets.UTF_8));
            BufferedWriter writerUsers = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(usersListPath), StandardCharsets.UTF_8));
            while ((line = reader2.readLine()) != null) {
                writerUsers.write(line);
                writerUsers.newLine();
            }

            reader2.close();
            writerUsers.close();
        }
    }

    /** Parses the config.txt file and sets values.
     */
    public static void readConfig() throws IOException {
        // Read the contents of the config.txt file
        List<String> lines = Files.readAllLines(CONFIG_FILE.toPath(), StandardCharsets.UTF_8);
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

    /** Sets values into the config.txt
     */
    public static void setValue(String key, String value) throws IOException {
        // Read the contents of the config.txt file
        List<String> lines = Files.readAllLines(Paths.get(CONFIG_FILE.toURI()));

        // Iterate through the lines and split each line into a key-value pair
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split("=");
            String k = parts[0].trim();

            // If the key is "tableCount" or "currency", update the value
            if (k.equals(key)) {
                lines.set(i, key + " = " + value);
            }
        }

        // Write the updated list of lines back to the config.txt file
        Files.write(Paths.get(CONFIG_FILE.toURI()), lines);
    }

    public static int getTableConfig() {
        return tableCount;
    }

    public static File getProductsListPath() {
        return productsListPath;
    }

    public static File getUsersListPath() {
        return usersListPath;
    }

}