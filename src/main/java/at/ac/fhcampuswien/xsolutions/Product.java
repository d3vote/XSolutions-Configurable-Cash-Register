package at.ac.fhcampuswien.xsolutions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static at.ac.fhcampuswien.xsolutions.Configurator.getProductsListPath;

public class Product {
    public String productTitle;
    public double productPrice;
    public String productDescription;
    public String productImageUrl;
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
     */
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

    /** Adds product to productsList array and updates the JSON File
     */
    private void initializeProducts() {
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
}
