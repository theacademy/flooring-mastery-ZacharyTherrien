package com.sg.flooringmastery.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    String productType;
    BigDecimal costPerSquareFoot;
    BigDecimal laborCostPerSquareFoot;

    public Product(){}

    public Product(String productType, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot) {
        this.productType = productType;
        this.costPerSquareFoot = costPerSquareFoot;
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productType, product.productType) && Objects.equals(costPerSquareFoot, product.costPerSquareFoot) && Objects.equals(laborCostPerSquareFoot, product.laborCostPerSquareFoot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productType, costPerSquareFoot, laborCostPerSquareFoot);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productType='" + productType + '\'' +
                ", costPerSquareFoot=" + costPerSquareFoot +
                ", laborCostPerSquareFoot=" + laborCostPerSquareFoot +
                '}';
    }
}
