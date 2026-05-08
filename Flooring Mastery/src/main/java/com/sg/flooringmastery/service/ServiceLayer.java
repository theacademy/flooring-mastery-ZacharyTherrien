package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dto.Order;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

public interface ServiceLayer {
    public List<Order> getOrders(LocalDate date);

    public Order getOrder(LocalDate date, int orderNumber);

    public Order addOrder(LocalDate date, Order order);

    public void editOrder(LocalDate date, Order order);

    public Order removeOrder(LocalDate date, int orderNumber);

    public void exportAllData();

    public void saveData();
}
