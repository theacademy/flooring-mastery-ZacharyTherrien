package com.sg.flooringmastery.dao.order;

import com.sg.flooringmastery.dto.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoStubImpl implements OrderDao{

    public final LocalDate DATE = LocalDate.now();
    public Order onlyOrder;

    public OrderDaoStubImpl(){
        onlyOrder = new Order();
        onlyOrder.setOrderNumber(1);
        onlyOrder.setCustomerName("Ada");
        onlyOrder.setState("CA");
        onlyOrder.setTaxRate(new BigDecimal("25"));
        onlyOrder.setProductType("Tile");
        onlyOrder.setArea(new BigDecimal("249"));
        onlyOrder.setCostPerSquareFoot(new BigDecimal("3.50"));
        onlyOrder.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        onlyOrder.setMaterialCost(new BigDecimal("871.5"));
        onlyOrder.setLaborCost(new BigDecimal("1033.35"));
        onlyOrder.setTax(new BigDecimal("476.2125"));
        onlyOrder.setTotal(new BigDecimal("2381.0625"));
    }

    public OrderDaoStubImpl(Order order){
        this.onlyOrder = order;
    }

    @Override
    public int getNextOrdersNumber() {
        return 2;
    }

    @Override
    public List<Order> getOrdersByDate(LocalDate date) {
        List<Order> ordersList = new ArrayList<>();
        ordersList.add(onlyOrder);
        return ordersList;
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) {
        if (DATE.equals(date) && onlyOrder.getOrderNumber() == orderNumber){
            return onlyOrder;
        }
        else{
            return null;
        }
    }

    @Override
    public Order addOrder(LocalDate date, Order order) {
        if (DATE.equals(date) && onlyOrder.equals(order)){
            return onlyOrder;
        }
        else{
            return null;
        }
    }

    @Override
    public Order editOrder(LocalDate date, Order order) {
        if (DATE.equals(date) && onlyOrder.equals(order)){
            return onlyOrder;
        }
        else{
            return null;
        }
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) {
        if (DATE.equals(date) && onlyOrder.getOrderNumber() == orderNumber){
            return onlyOrder;
        }
        else{
            return null;
        }
    }

    @Override
    public void exportOrder() throws OrderPersistenceException{

    }

    @Override
    public void exportDataToBackup() throws OrderPersistenceException{

    }
}
