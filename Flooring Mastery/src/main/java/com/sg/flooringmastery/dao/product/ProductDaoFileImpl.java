package com.sg.flooringmastery.dao.product;

import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ProductDaoFileImpl implements ProductDao{

    final String PRODUCT_FILE = "./Data/Products.txt";
    final String DELIMITER = "::";
    Map<String, Product> products;

    public ProductDaoFileImpl(){
        products = new HashMap<>();
        loadProducts();
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
