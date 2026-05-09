package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ServiceLayer {
    public List<Order> getOrders(LocalDate date);

    public Order getOrder(LocalDate date, int orderNumber);

    public int getNextOrderNumber();

    public Order addOrder(LocalDate date, Order order);

    public void editOrder(LocalDate date, Order order);

    public Order removeOrder(LocalDate date, int orderNumber);

    public void exportAllData();

    public void saveData();

    public Order calculateOrderCosts(Order order);

    public void validateOrderDate(LocalDate date, boolean isAdding);

    public void validateCustomerName(String name);

    public void validateCustomerState(String state);

    public void validateProductType(String productType);

    public void validateArea(BigDecimal area);

    public List<Product> getAllProducts();
}
