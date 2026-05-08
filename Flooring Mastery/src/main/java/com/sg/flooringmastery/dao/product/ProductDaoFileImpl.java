package com.sg.flooringmastery.dao.product;

import com.sg.flooringmastery.dto.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ProductDaoFileImpl implements ProductDao{

    final String PRODUCT_FILE = "./Data/Products.txt";
    final String DELIMITER = "::";
    Map<String, Product> products;

    public ProductDaoFileImpl(){
        products = new HashMap<>();
        loadProducts();
    }

    private void loadProducts(){

    }

    @Override
    public BigDecimal getProductLaborPerSquareFoot(String productType) {
        /**
         *
         * Go through the map and compare string to products map
         * then get value form key and return desired value
         *
         */
        return null;
    }

    @Override
    public BigDecimal getProductCostPerSquareFoot(String productType) {
        /**
         *
         * Go through the map and compare string to products map
         * then get value form key and return desired value
         *
         */
        return null;
    }
}
