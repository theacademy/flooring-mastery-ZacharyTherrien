package com.sg.flooringmastery.dao.product;

import com.sg.flooringmastery.dto.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoStubImpl implements  ProductDao{

    Product onlyProduct;

    public ProductDaoStubImpl(){
        onlyProduct = new Product();
        onlyProduct.setProductType("Tile");
        onlyProduct.setCostPerSquareFoot(new BigDecimal("3.50"));
        onlyProduct.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
    }

    public ProductDaoStubImpl(Product product){
        this.onlyProduct = product;
    }

    @Override
    public BigDecimal getProductLaborPerSquareFoot(String productType) {
        if (onlyProduct.getProductType().equals(productType)){
            return new BigDecimal("4.75");
        }
        else{
            return null;
        }
    }

    @Override
    public BigDecimal getProductCostPerSquareFoot(String productType) {
        if (onlyProduct.getProductType().equals(productType)){
            return new BigDecimal("5.15");
        }
        else{
            return null;
        }
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        products.add(onlyProduct);
        return products;
    }

    @Override
    public Product getProduct(String product) {
        if (onlyProduct.getProductType().equals(product)){
            return onlyProduct;
        }
        else{
            return null;
        }
    }
}
