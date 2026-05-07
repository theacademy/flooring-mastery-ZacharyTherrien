package com.sg.flooringmastery.dto;

import java.math.BigDecimal;

public class Order{
    private int orderNumber;
    private String customerName;
    private String state;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal area;
    private BigDecimal costPerSquareFoot;
    private BigDecimal materialCost;
    private BigDecimal laborCostPerSquareFoot;
    private BigDecimal tax;
    private BigDecimal total;

    public Order(){}

    public Order(int orderNumber, String customerName, String state, BigDecimal taxRate, String productType, BigDecimal area, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot){
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.state = state;
        this.taxRate = taxRate;
        this.productType = productType;
        this.area = area;
        this.costPerSquareFoot = costPerSquareFoot;
        this.materialCost = getMaterialCost();
        this.laborCostPerSquareFoot = getLaborCostPerSquareFoot();
        this.tax = getTax();
        this.total = getTotal();
    }


    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
