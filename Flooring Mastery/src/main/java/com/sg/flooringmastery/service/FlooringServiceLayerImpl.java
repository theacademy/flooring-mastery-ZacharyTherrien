package com.sg.flooringmastery.service;

import com.sg.flooringmastery.controller.FlooringController;
import com.sg.flooringmastery.dao.order.OrderDao;
import com.sg.flooringmastery.dao.product.ProductDao;
import com.sg.flooringmastery.dao.tax.TaxDao;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        // Call the dao to get all the orders on a date and return the list
        return orderDao.getOrdersByDate(date);
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) {
        // Get a singular order by its number and the date it was placed
        return orderDao.getOrder(date, orderNumber);
    }

    @Override
    public int getNextOrderNumber() {
        return orderDao.getNextOrdersNumber();
    }

    @Override
    public Order addOrder(LocalDate date, Order order) {
        orderDao.addOrder(date, order);
        calculateOrderCosts(order);
        return order;
    }

    @Override
    public void editOrder(LocalDate date, Order order) {
        orderDao.editOrder(date, order);
        calculateOrderCosts(order);
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) {
        return orderDao.removeOrder(date, orderNumber);
    }

    @Override
    public void exportAllData() {
        orderDao.exportOrder();
    }

    @Override
    public void saveData() {

    }

    @Override
    public Order calculateOrderCosts(Order order){
        // Set the tax and product values from the respective types into the order
        Tax tax = taxDao.getTax(order.getState());
        order.setTaxRate(tax.getTaxRate().setScale(2, RoundingMode.HALF_UP));

        Product product = productDao.getProduct(order.getProductType());
        order.setCostPerSquareFoot(product.getCostPerSquareFoot());
        order.setLaborCostPerSquareFoot(product.getLaborCostPerSquareFoot());

        // Calculate the remaining values.
        order.setMaterialCost(order.getArea().multiply(order.getCostPerSquareFoot()));
        order.setLaborCost(order.getArea().multiply(order.getLaborCostPerSquareFoot()));

        BigDecimal materialPlusLaborCost = order.getMaterialCost().add(order.getLaborCost());
        BigDecimal divisor = new BigDecimal("100");
        BigDecimal taxRateDivision = order.getTaxRate().divide(divisor).setScale(2, RoundingMode.HALF_UP);

        order.setTax(materialPlusLaborCost.multiply(taxRateDivision));
        order.setTotal(materialPlusLaborCost.add(order.getTax()));

        return order;
    }

    @Override
    public void validateOrderDate(LocalDate date, boolean isAdding){
        if (isAdding && !date.isAfter(LocalDate.now())){
            throw new OrderValidationException("Order Date must be placed in the future");
        }
    }

    @Override
    public void validateCustomerName(String name){
        if (name.matches("\\s*")){
            throw new OrderValidationException("Name must not be blank");
        }
        if (!name.matches("[\\sa-zA-Z0-9,.-]*")){
            throw new OrderValidationException("Only characters [a-z][0-9] as well as periods and comma characters are allowed for customer name.");
        }
    }

    @Override
    public void validateCustomerState(String state){
        if (state.matches("\\s*")){
            throw new OrderValidationException("State must not be blank");
        }

        //Verify that user inputs the state abbreviation
        Tax tax= taxDao.getTax(state);

        if (tax == null){
            throw new OrderValidationException("Invalid state abbreviation entered");
        }
    }

    @Override
    public void validateProductType(String productType){
        boolean stateFound = false;

        if (productType.matches("\\s*")){
            throw new OrderValidationException("product type must not be blank");
        }

        //Verify that user inputs the state abbreviation
        Product product= productDao.getProduct(productType);

        if (product == null){
            throw new OrderValidationException("Invalid state abbreviation entered");
        }
    }

    @Override
    public void validateArea(BigDecimal area){
        final BigDecimal MIN_SIZE = new BigDecimal(100);
        if (area.compareTo(MIN_SIZE) < 0) {
            throw new OrderValidationException("The area must be a positive decimal. Minimum order size is 100 sq ft.");
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }
}
