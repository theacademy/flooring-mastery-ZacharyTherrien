package com.sg.flooringmastery.dao.order;

import com.sg.flooringmastery.dto.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class OrderDaoFileImpl implements OrderDao {

    private final String ORDERS_FOLDER = "/Orders";
    private final String DELIMITER = "::";
    private Map<LocalDate, Map<Integer, Order>> orders;

    public OrderDaoFileImpl(){
        loadOrders();
    }

    private void loadOrders(){

    }

    private Order unmarshallOrder(){
        return null;
    }

    private String marshallOrders(){
        return null;
    }

    @Override
    public int getNextOrders() {
        return 0;
    }

    @Override
    public List<Order> getOrdersByDate(LocalDate date) {
        return List.of();
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) {
        return null;
    }

    @Override
    public Order addOrder(LocalDate date, Order order) {
        return null;
    }

    @Override
    public Order editOrder(LocalDate date, Order order) {
        return null;
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) {
        return null;
    }

    @Override
    public void exportOrder() {

    }

    @Override
    public void exportDataToBackup() {

    }
}
