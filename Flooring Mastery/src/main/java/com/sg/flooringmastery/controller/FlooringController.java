package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.service.FlooringServiceLayerImpl;
import com.sg.flooringmastery.service.OrderValidationException;
import com.sg.flooringmastery.ui.FlooringView;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;

public class FlooringController {
    private FlooringView view;
    private FlooringServiceLayerImpl service;

    public FlooringController(FlooringView view, FlooringServiceLayerImpl service){
        this.view = view;
        this.service = service;
    }

    public void run(){
        boolean isRunning = true;
        int menuSelection = 0;

        while(isRunning){
            menuSelection = getMenuSelection();

            switch (menuSelection){
                case 1:
                    displayOrders();
                    break;
                case 2:
                    addOrder();
                    break;
                case 3:
                    editOrder();
                    break;
                case 4:
                    removeOrder();
                    break;
                case 5:
                    exportAllData();
                    break;
                case 6:
                    isRunning = false;
                    break;
                default:
                    unknownCommand();
            }
        }

        exitMessage();
    }

    public int getMenuSelection(){
        return view.displayMenu();
    }

    public void displayOrders(){
        LocalDate date = getDate(false);

        List<Order> orders = service.getOrders(date);

        if (orders == null || orders.isEmpty()){
            view.displayErrorMessage("No orders from: " + date);
            return;
        }

        orders.forEach(order -> view.displayOrder(order));
    }

    public void addOrder(){
        view.displayAddOrderBanner();

        boolean addOrderComplete = false;
        LocalDate date;
        String customerName;
        String state;
        String productType;
        BigDecimal area;

        date = getDate(true);
        customerName = getCustomerName(false);
        state = getState(false);
        productType = getProductType(false);
        area = getArea(false);

        Order order = new Order(customerName, state, productType, area);

        if (view.confirmAddOrder()){
            service.addOrder(date, order);
            view.displayOrder(order);
        }
    }

    public void editOrder(){
        view.displayEditOrderBanner();

        LocalDate date = LocalDate.now();
        int orderNumber = 0;
        String customerName = "";
        String state = "";
        String productType = "";
        BigDecimal area = null;

        date = getDate(false);
        orderNumber = getOrderNumber();

        Order order = service.getOrder(date, orderNumber);

        if (order == null) {
            view.displayErrorMessage("No order was found");
            return;
        }

        customerName = getCustomerName(true);
        state = getState(true);
        productType = getProductType(true);
        area = getArea(true);

        Order editedOrder = new Order(orderNumber, customerName, state, productType, area);

        if (view.confirmEditOrder()){
            service.editOrder(date, editedOrder);
            service.calculateOrderCosts(order);
        }
    }

    public void removeOrder(){
        view.displayRemoveBanner();

        LocalDate date = LocalDate.now();
        int orderNumber = 0;

        date = getDate(false);
        orderNumber = getOrderNumber();

        Order order = service.getOrder(date, orderNumber);

        if (order == null) {
            view.displayErrorMessage("No order was found");
            return;
        }

        if (view.confirmRemoveOrder()){
            view.displayRemoveResult(service.removeOrder(date, orderNumber));
        }
    }

    public void exportAllData(){
        service.exportAllData();
        view.displaySaveSuccess("Order data has been saved to the file!");
    }

    public void unknownCommand(){
        view.displayUnknownCommand();
    }

    public void exitMessage(){
        view.displayExitMessage();
    }

    private LocalDate getDate(boolean addingOrder){
        boolean hasError = false;
        LocalDate date = LocalDate.now();

        do{
            try {
                date = view.getOrderDate();
                service.validateOrderDate(date, addingOrder);
                hasError = false;
            }
            catch(OrderValidationException | DateTimeParseException e){
                hasError = true;
                view.displayErrorMessage(e.getMessage());
            }
        } while(hasError);

        return date;
    }

    private int getOrderNumber(){
        boolean hasError = false;
        int orderNumber = 0;

        do{
            try {
                orderNumber = view.getOrderNumber();
                hasError = false;
            }
            catch(NumberFormatException | InputMismatchException e){
                hasError = true;
                view.displayErrorMessage(e.getMessage());
            }
        } while(hasError);

        return orderNumber;
    }

    private String getCustomerName(boolean isEditing){
        boolean hasError = false;
        String customerName = "";

        do{
            try{
                customerName = view.getCustomerName();
                if (isEditing && customerName.isEmpty()){
                    return "";
                }
                service.validateCustomerName(customerName);
                hasError = false;
            }
            catch(OrderValidationException e){
                hasError = true;
                view.displayErrorMessage(e.getMessage());
            }
        } while(hasError);

        return customerName;
    }

    private String getState(boolean isEditing){
        boolean hasError = false;
        String state = "";

        do{
            try{
                state = view.getCustomerState();
                if (isEditing && state.isEmpty()){
                    return "";
                }
                service.validateCustomerState(state);
                hasError = false;
            }
            catch(OrderValidationException e){
                hasError = true;
                view.displayErrorMessage(e.getMessage());
            }
        } while(hasError);
        return state;
    }

    private String getProductType(boolean isEditing){
        boolean hasError = false;
        String productType = "";

        do{
            try{
                view.displayProducts(service.getAllProducts());
                productType = view.getProdctType();
                service.validateProductType(productType);
                hasError = false;
            }
            catch(OrderValidationException e){
                if (isEditing && productType.isEmpty()){
                    return "";
                }
                hasError = true;
                view.displayErrorMessage(e.getMessage());
            }
        } while(hasError);

        return productType;
    }

    private BigDecimal getArea(boolean isEditing){
        boolean hasError = false;
        BigDecimal area = new BigDecimal(0);

        do{
            try{
                String input = view.getArea();
                if (isEditing && input.isEmpty()){
                    return null;
                }
                area = new BigDecimal(input);
                service.validateArea(area);
                hasError = false;
            }
            catch(NumberFormatException | OrderValidationException e){
                hasError = true;
                view.displayErrorMessage(e.getMessage());
            }
        } while(hasError);

        return area;
    }
}
