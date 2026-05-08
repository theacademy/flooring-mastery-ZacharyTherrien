package com.sg.flooringmastery.dao.order;

import com.sg.flooringmastery.dto.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDaoFileImpl implements OrderDao {

    private final String ORDERS_FOLDER = "/Orders";
    private final String DELIMITER = "::";
    private Map<LocalDate, Map<Integer, Order>> orders;
    private int highestOrderNumber = 3;

    public OrderDaoFileImpl(){
        orders = new HashMap<>();
        loadOrders();

        /**
         *
         * FOR NOW, JUST AUTO POPULATE WITH OUR OWN ORDERS
         *
         */
    }

    private void loadOrders(){

        Map<Integer, Order> ordersList = new HashMap<>();

        Order o1 = new Order(1, "Steve", "Ohio", new BigDecimal("10"), "Wood", new BigDecimal("10"), new BigDecimal("100"), new BigDecimal("51"));
        Order o2 = new Order(2, "Steven", "Misshigan", new BigDecimal("1000"), "Rock", new BigDecimal("13"), new BigDecimal("100"), new BigDecimal("5"));
        Order o3 = new Order(3, "Stevens", "NY", new BigDecimal("1"), "Stone", new BigDecimal("11"), new BigDecimal("20"), new BigDecimal("3"));

        ordersList.put(o1.getOrderNumber(), o1);
        ordersList.put(o2.getOrderNumber(), o2);
        ordersList.put(o3.getOrderNumber(), o3);

        LocalDate date1 = LocalDate.parse("01-01-2222", DateTimeFormatter.ofPattern("MM-dd-yyyy"));

        orders.put(LocalDate.parse("01-01-2222", DateTimeFormatter.ofPattern("MM-dd-yyyy")), ordersList);

    }

    @Override
    public int getNextOrdersNumber() {
        return highestOrderNumber++;
    }

    @Override
    public List<Order> getOrdersByDate(LocalDate date) {
        if (!orders.containsKey(date)){
            return null;
        }

        return new ArrayList<>(orders.get(date).values());
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) {
        if (!orders.containsKey(date)){
            return null;
        }
        return orders.get(date).get(orderNumber);
    }

    @Override
    public Order addOrder(LocalDate date, Order order) {
        order.setOrderNumber(getNextOrdersNumber());
        if (!orders.containsKey(date)){
            orders.put(date, new HashMap<>());
        }
        orders.get(date).put(order.getOrderNumber(), order);
        return order;
    }

    @Override
    public Order editOrder(LocalDate date, Order order) {
        if (!orders.containsKey(date)){
            return null;
        }

        if(!orders.get(date).containsKey(order.getOrderNumber())){
            return null;
        }

        // Check if no changes were made, if so, exit without making editing
        if (order.getCustomerName().isEmpty() &&
            order.getState().isEmpty() &&
            order.getProductType().isEmpty() &&
                (order.getArea() == null))
        {
            return null;
        }

        orders.get(date).replace(order.getOrderNumber(), order);
        return order;
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) {
        if (!orders.containsKey(date)){
            return null;
        }
        return orders.get(date).remove(orderNumber);
    }

    @Override
    public void exportOrder() {

    }

    @Override
    public void exportDataToBackup() {

    }

    private Order unmarshallOrder(){
        return null;
    }

    private String marshallOrders(){
        return null;
    }
}
