package com.sg.flooringmastery.service;

import com.sg.flooringmastery.controller.FlooringController;
import com.sg.flooringmastery.dao.order.OrderDao;
import com.sg.flooringmastery.dao.product.ProductDao;
import com.sg.flooringmastery.dao.tax.TaxDao;
import com.sg.flooringmastery.dto.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlooringServiceLayerImpl implements ServiceLayer{

    private OrderDao orderDao;
    private TaxDao taxDao;
    private ProductDao productDao;

    public FlooringServiceLayerImpl(OrderDao orderDao, TaxDao taxDao, ProductDao productDao){
        this.orderDao = orderDao;
        this.taxDao = taxDao;
        this.productDao = productDao;
    }

    @Override
    public List<Order> getOrders(LocalDate date) {
        List<Order> orders = new ArrayList<>();

        Order o1 = new Order(001, "Steve", "Ohio", new BigDecimal("10"), "Wood", new BigDecimal("10"), new BigDecimal("100"), new BigDecimal("51"));
        Order o2 = new Order(002, "Steven", "Misshigan", new BigDecimal("1000"), "Rock", new BigDecimal("13"), new BigDecimal("100"), new BigDecimal("5"));
        Order o3 = new Order(003, "Stevens", "NY", new BigDecimal("1"), "Stone", new BigDecimal("11"), new BigDecimal("20"), new BigDecimal("3"));

        orders.add(o1);
        orders.add(o2);
        orders.add(o3);

        return orders;
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
    public void editOrder(LocalDate date, Order order) {

    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) {
        return null;
    }

    @Override
    public void exportAllData() {

    }

    @Override
    public void saveData() {

    }

    public Order calculateOrderCosts(Order order){
        return null;
    }

    public void validateOrderDate(LocalDate date){
        if (!date.isAfter(LocalDate.now())){
            throw new OrderValidationException("Order Date must be placed in the future");
        }
    }

    public void validateCustomerName(String name){
        if (name.matches("\\s*")){
            throw new OrderValidationException("Name must not be blank");
        }
        if (!name.matches("[a-zA-A0-9,.-]*")){
            throw new OrderValidationException("Only characters [a-z][0-9] as well as periods and comma characters are allowed for customer name.");
        }
    }

    public void validateCustomerState(String state){

    }

    public void validateProductType(String productType){

    }

    public void validateArea(BigDecimal area){
        final BigDecimal MIN_SIZE = new BigDecimal(100);
        if (area.compareTo(MIN_SIZE) <= 0) {
            throw new OrderValidationException("The area must be a positive decimal. Minimum order size is 100 sq ft.");
        }
    }
}
