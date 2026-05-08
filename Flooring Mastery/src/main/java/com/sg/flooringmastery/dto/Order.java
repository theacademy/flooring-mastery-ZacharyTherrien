package com.sg.flooringmastery.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class Order{
    private int orderNumber;
    private String customerName;
    private String state;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal area;
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal tax;
    private BigDecimal total;

    public Order(){}

    public Order(String customerName, String state, String productType, BigDecimal area){
        this.customerName = customerName;
        this.state = state;
        this.productType = productType;
        this.area = area;
    }

    public Order(int orderNumber, String customerName, String state, String productType, BigDecimal area){
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.state = state;
        this.productType = productType;
        this.area = area;
    }

    public Order(int orderNumber, String customerName, String state, BigDecimal taxRate, String productType, BigDecimal area, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot){
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.state = state;
        this.taxRate = taxRate;
        this.productType = productType;
        this.area = area;
        this.costPerSquareFoot = costPerSquareFoot;
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
        this.materialCost = getMaterialCost();
        this.laborCost = getLaborCost();
        this.tax = getTax();
        this.total = getTotal();
    }

    public void recalculateValues(){
        this.materialCost = getMaterialCost();
        this.laborCost = getLaborCost();
        this.tax = getTax();
        this.total = getTotal();
    }

    public BigDecimal getMaterialCost() {
        return area.multiply(costPerSquareFoot);
    }

    public BigDecimal getLaborCost() {
        return area.multiply(laborCostPerSquareFoot);
    }

    public BigDecimal getTax() {
        BigDecimal divisor = new BigDecimal(100);
        return materialCost.add(laborCost).multiply(taxRate.divide(divisor));
    }

    public BigDecimal getTotal() {
        return materialCost.add(laborCost).add(tax);
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber, customerName, state, taxRate, productType, area, costPerSquareFoot, laborCostPerSquareFoot, materialCost, laborCost, tax, total);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderNumber == order.orderNumber && Objects.equals(customerName, order.customerName) && Objects.equals(state, order.state) && Objects.equals(productType, order.productType) && Objects.equals(area, order.area);
    }

    @Override
    public String toString() {
        return "Order{" +
                "Order Number=" + orderNumber +
                ", Customer Name='" + customerName + '\'' +
                ", State='" + state + '\'' +
                ", Tax Rate=" + taxRate +
                ", Product Type='" + productType + '\'' +
                ", Area=" + area +
                ", CostPerSquareFoot=" + costPerSquareFoot +
                ", LaborCostPerSquareFoot=" + laborCostPerSquareFoot +
                ", Material Cost=" + materialCost +
                ", Labor Cost=" + laborCost +
                ", Tax=" + tax +
                ", Total=" + total +
                '}';
    }
}
