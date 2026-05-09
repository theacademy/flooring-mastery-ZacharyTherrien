package com.sg.flooringmastery.dao.product;

import com.sg.flooringmastery.dto.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductDao {
    public BigDecimal getProductLaborPerSquareFoot(String productType);

    public BigDecimal getProductCostPerSquareFoot(String productType);

    public List<Product> getAllProducts();

    public Product getProduct(String product);
}
