package at.ac.fhcampuswien.xsolutions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;

import static at.ac.fhcampuswien.xsolutions.Configurator.getCategoriesListPath;
import static at.ac.fhcampuswien.xsolutions.Configurator.getProductsListPath;

public class Product {
    public String productTitle;
    public double productPrice;
    public String productDescription;
    public String productImageUrl;
    public ArrayList<String> category = new ArrayList<>();
    public static ArrayList<String> categories = new ArrayList<>();
    public static List<Product> productsList = new ArrayList<>();    // ProductList
    private static int count;

    public Product() {
        super();
    }

    /** Constructor of Product object
     * @param productTitle - Name of the product
     * @param productPrice - Price of the product
     * @param productDescription - Description of the product
     * @param productImageUrl - Image URL
     * @param category - Product Category
     */
    public Product(String productTitle, double productPrice, String productDescription, String category, String productImageUrl) {
        count++;
        this.productImageUrl = productImageUrl;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        if ((Objects.equals(this.category, "") || Objects.equals(this.category, "Nicht zugeordnet") || Objects.equals(this.category, "null"))){
            this.category.add("Nicht zugeordnet");
        } else {
            this.category.addAll(Arrays.asList(category.split(",")));
        }
        initializeProducts();
    }
    public Product(String productTitle, double productPrice, String productDescription, String productImageUrl) {
        count++;
        this.productImageUrl = productImageUrl;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.category.add("Nicht zugeordnet");
        initializeProducts();
    }
    public Product(String productTitle, double productPrice, String productDescription) {
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.category.add("Nicht zugeordnet");
        initializeProducts();
    }
    public Product(String productTitle, double productPrice) throws IOException {
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.category.add("Nicht zugeordnet");
        initializeProducts();
    }

    /** Adds product to productsList array and updates the JSON File
     */
    private void initializeProducts() {
        productsList.add(this);
        productToJSON();
    }

    public void setCategory(String category) {
        if (!(Objects.equals(this.category, "") || Objects.equals(this.category, "Nicht zugeordnet") || Objects.equals(this.category, "null"))) {
            this.category.add(category);
        } else {
            this.category.clear();
            this.category.add(category);
        }
    }

    public static void addCategory(String category){
        categories.add(category);
    }

    public void removeCategory(String category) {
        this.category.remove(category);
    }
    public void clearCategory(){
        this.category.clear();
    }
    public static void sortProducts(){
        //Sort Products List by their Category
        productsList.sort(Comparator.comparingInt(p -> getCategoryOrder(p.getCategory())));
    }

    public static void deleteCategory(String name){
        categories.remove(name);
    }

    public String getCategory() {
        return Arrays.toString(category.toArray()).replace("[", "").replace("]", "");
    }

    public static ArrayList<String> getCategories() {
        return categories;
    }



    public static int getCategoryOrder(String category) {
        return categories.indexOf(category);
    }

    public String getProductTitle() {
        return productTitle;
    }
    public double getProductPrice() {
        return productPrice;
    }
    public String getProductDescription() {
        return productDescription;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    /**
     * productsList (List of all parsed products) is being put in a single JSON File.
     */
    public static void productToJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            java.nio.file.Path path = Paths.get(getProductsListPath().toURI());
            objectMapper.writeValue(path.toFile(), productsList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * productsList is being recreated from a JSON File.
     */
    public static void JSONtoProductList() throws IOException {
        if (productsList != null){
            ObjectMapper objectMapper = new ObjectMapper();
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            productsList = objectMapper.readValue(getProductsListPath(), typeFactory.constructCollectionType(List.class, Product.class));
        }
    }

    public static void categoriesToJSON() throws IOException {
        FileWriter writer = new FileWriter(getCategoriesListPath());
        for(String str: categories) {
            writer.write(str + System.lineSeparator());
        }
        writer.close();
    }

    public static void JSONtoCategories() {
        ArrayList<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(getCategoriesListPath()), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        categories = list;
    }


    /**
     * Search bar: shows a new array of sorted products by the search term
     */
    public static List<Product> filterProductsByName(String searchTerm) {
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : productsList) {
            if (product.getProductTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    public static List<Product> filterProductsByCategory(String searchTerm) {
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : productsList) {
            if (product.getCategory().contains(searchTerm)) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }
}
