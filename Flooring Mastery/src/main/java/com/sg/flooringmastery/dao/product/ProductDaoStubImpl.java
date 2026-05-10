package com.sg.flooringmastery.dao.product;

import com.sg.flooringmastery.dto.Product;

import java.math.BigDecimal;
import java.util.List;

public class ProductDaoStubImpl implements  ProductDao{
    @Override
    public BigDecimal getProductLaborPerSquareFoot(String productType) {
        return null;
    }

    @Override
    public BigDecimal getProductCostPerSquareFoot(String productType) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public Product getProduct(String product) {
        return null;
    }
}
