package com.sg.flooringmastery.dao.order;

import com.sg.flooringmastery.dto.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderDao {
    public int getNextOrdersNumber();

    public List<Order> getOrdersByDate(LocalDate date);

    public Order getOrder(LocalDate date, int orderNumber);

    public Order addOrder(LocalDate date, Order order);

    public Order editOrder(LocalDate date, Order order);

    public Order removeOrder(LocalDate date, int orderNumber);

    public void exportOrder();

    public void exportDataToBackup();
}
