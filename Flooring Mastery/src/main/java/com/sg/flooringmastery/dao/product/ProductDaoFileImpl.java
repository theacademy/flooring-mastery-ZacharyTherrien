package com.sg.flooringmastery.dao.product;

import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;

public class ProductDaoFileImpl implements ProductDao{

    final String PRODUCT_FILE;
    final String DELIMITER = "::";
    Map<String, Product> products;

    public ProductDaoFileImpl(){
        PRODUCT_FILE = "./Data/Products.txt";
        products = new HashMap<>();
        loadProducts();
    }

    public ProductDaoFileImpl(String productsFile){
        PRODUCT_FILE = productsFile;
        products = new HashMap<>();
        loadProducts();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductDaoFileImpl that = (ProductDaoFileImpl) o;
        return Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(products);
    }

    private void loadProducts(){
        Scanner scanner;

        try {
            // Create a scanner to read the products file
            scanner = new Scanner(new BufferedReader(new FileReader(PRODUCT_FILE)));
        } catch (FileNotFoundException e) {
            throw new Error(e.getMessage());
        }

        String currentLine;
        Product Product;

        while (scanner.hasNextLine()) {
            // Read the current line from the product file
            currentLine = scanner.nextLine();
            // Get the product object from the string
            Product = unmarshallTax(currentLine);
            // Add the product to the products map
            products.put(Product.getProductType(), Product);
        }

        // Clean up
        scanner.close();
    }

    private Product unmarshallTax(String productAsText){
        // Parse the given product string by the designated delimiter
        String[] productTokens = productAsText.split(DELIMITER);

        // Create a new product
        Product product = new Product();

        // Set the values of the product to the parsed values
        product.setProductType(productTokens[0]);
        product.setCostPerSquareFoot(new BigDecimal(productTokens[1]));
        product.setLaborCostPerSquareFoot(new BigDecimal(productTokens[2]));

        // Return the product
        return product;
    }

    @Override
    public BigDecimal getProductLaborPerSquareFoot(String productType) {
        return products.get(productType).getLaborCostPerSquareFoot();
    }

    @Override
    public BigDecimal getProductCostPerSquareFoot(String productType) {
        return products.get(productType).getLaborCostPerSquareFoot();
    }

    @Override
    public List<Product> getAllProducts() {
        return products.values().stream().toList();
    }

    @Override
    public Product getProduct(String product) {
        return products.get(product);
    }
}
