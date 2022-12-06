package at.ac.fhcampuswien.xsolutions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Product {
    public String productTitle;
    public double productPrice;
    public String productDescription;
    public String productImageUrl;
    static File productsListPath = new File("src/main/java/productsList.json");
    public static List<Product> productsList = new ArrayList<>();    // ProductList
    private static int count;


    public Product() {
    }

    public Product(String productTitle, double productPrice, String productDescription, String productImageUrl) throws IOException {
        count++;
        this.productImageUrl = productImageUrl;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        initializeProducts();
    }

    public Product(String productTitle, double productPrice, String productDescription) throws IOException {
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        initializeProducts();
    }

    public Product(String productTitle, double productPrice) throws IOException {
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        initializeProducts();
    }

    private void initializeProducts() throws IOException {
        productsList.add(this);
        productToJSON();
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

    public static int getCount() {
        return count;
    }

    public static void productToJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            java.nio.file.Path path = Paths.get("src/main/java/productsList.json");
            objectMapper.writeValue(path.toFile(), productsList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Convert the JSON File back to Objects in a List
    public static void JSONtoProductList() throws IOException {
        if (productsList != null){
            ObjectMapper objectMapper = new ObjectMapper();
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            productsList = objectMapper.readValue(productsListPath, typeFactory.constructCollectionType(List.class, Product.class));
        }
    }
}
