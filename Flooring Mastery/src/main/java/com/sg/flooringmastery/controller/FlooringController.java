package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.service.FlooringServiceLayerImpl;
import com.sg.flooringmastery.service.OrderValidationException;
import com.sg.flooringmastery.ui.FlooringView;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
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
        boolean hasError = false;
        LocalDate date = LocalDate.now();

        view.displayClearBuffer();

        do{
            try {
                date = view.getOrderDate();
                hasError = false;
            } catch (Exception e) {
                hasError = true;
                view.displayErrorMessage("Invalid date format");
            }
        }while(hasError);

        List<Order> orders = orders = service.getOrders(date);

        orders.forEach(order -> view.displayOrder(order));
    }

    public void addOrder(){
        view.displayAddOrderBanner();

        boolean hasError = false;
        boolean addOrderComplete = false;
        LocalDate date;
        String customerName;
        String state;
        String productType;
        BigDecimal area;

        view.displayClearBuffer();

        date = getDate();
        customerName = getCustomerName();
        state = getState();
        productType = getProductType();
        area = getArea();

        /**
         *
         * CALL SERVICE LAYER TO CREATE THE ORDER OBJECT NOW
         * ORDER IS ADDED TO THE DAO'S LIST AFTER THIS
         *
         */

        if (view.confirmAddOrder()){
            /**
             * CALL SERVICE LAYER TO STORE THE ORDER IN MEMORY
             */
        }
    }

    public void editOrder(){
        view.displayEditOrderBanner();
        view.displayClearBuffer();

        LocalDate date = LocalDate.now();
        int orderNumber = 0;
        boolean hasError = false;
        String customerName = "";
        String state = "";
        String productType = "";
        BigDecimal area = null;

        /**
         *
         * GET ORDER FROM SERVICE
         *
         */

        date = getDate();
        orderNumber = getOrderNumber();

        Order o1 = new Order(001, "Steve", "Ohio", new BigDecimal("10"), "Wood", new BigDecimal("10"), new BigDecimal("100"), new BigDecimal("51"));
        Order order = service.getOrder(date, orderNumber);

        if (o1 == null) {
            view.displayErrorMessage("No order was found");
            return;
        }

        view.displayClearBuffer();

        do{
            try{
                customerName = view.getCustomerName();
                hasError = false;
            }
            catch (OrderValidationException e){
                view.displayErrorMessage(e.getMessage());
                hasError = true;
            }
        }while(hasError || customerName.isEmpty());

        do{
            try{
                state = view.getCustomerState();
                hasError = false;
            }
            catch (OrderValidationException e){
                view.displayErrorMessage(e.getMessage());
                hasError = true;
            }
        }while(hasError || state.isEmpty());

        do{
            try{
                productType = view.getProdctType();
                hasError = false;
            }
            catch (OrderValidationException e){
                view.displayErrorMessage(e.getMessage());
                hasError = true;
            }
        }while(hasError || productType.isEmpty());

        do{
            try{
                area = view.getArea();
                hasError = false;
            }
            catch (OrderValidationException | NumberFormatException e){
                view.displayErrorMessage(e.getMessage());
                hasError = true;
            }
        }while(hasError || area == null);

        /**
         *
         * IF NO CHANGES MADE, EXIT
         *
         */

        /**
         *
         * TELL SERVICE LAYER TO RECALCULATE VALUES
         *
         */

        if (view.confirmEditOrder()){
            /**
             *
             * CALL EDIT ORDER IN SERVICE LAYER
             *
             */
        }
    }

    public void removeOrder(){
        view.displayRemoveBanner();
        view.displayClearBuffer();

        LocalDate date = LocalDate.now();
        int orderNumber = 0;
        boolean hasError = false;

        /**
         *
         * GET ORDER FROM SERVICE
         *
         */

        do{
            try {
                date = view.getOrderDate();
                // Validate value in service layer
                hasError = false;
            }
            catch(OrderValidationException | DateTimeParseException e){
                hasError = true;
                view.displayErrorMessage(e.getMessage());
            }
        }while(hasError);

        do{
            try {
                orderNumber = view.getOrderNumber();
                // Validate value in service layer
                hasError = false;
            }
            catch(InputMismatchException e){
                hasError = true;
                view.displayErrorMessage(e.getMessage());
            }
        }while(hasError);

        Order o1 = new Order(001, "Steve", "Ohio", new BigDecimal("10"), "Wood", new BigDecimal("10"), new BigDecimal("100"), new BigDecimal("51"));
        Order order = service.getOrder(date, orderNumber);

        view.displayClearBuffer();

        if (view.confirmRemoveOrder()){
            /**
             *
             * CALL SERVICE TO REMOVE THE DTO FROM THE DAO
             *
             */
            view.displayRemoveResult(order);
        }
    }

    public void exportAllData(){}

    public void unknownCommand(){
        view.displayUnknownCommand();
    }

    public void exitMessage(){
        view.displayExitMessage();
    }

    private LocalDate getDate(){
        boolean hasError = false;
        LocalDate date = LocalDate.now();

        do{
            try {
                date = view.getOrderDate();
                service.validateOrderDate(date);
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
                // Validate value in service layer
                hasError = false;
            }
            catch(InputMismatchException e){
                hasError = true;
                view.displayErrorMessage(e.getMessage());
            }
        } while(hasError);

        return orderNumber;
    }

    private String getCustomerName(){
        boolean hasError = false;
        String customerName = "";

        do{
            try{
                customerName = view.getCustomerName();
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

    private String getState(){
        boolean hasError = false;
        String state = "";

        do{
            try{
                state = view.getCustomerState();
                hasError = false;
            }
            catch(OrderValidationException e){
                hasError = true;
                view.displayErrorMessage(e.getMessage());
            }
        } while(hasError);
        return state;
    }

    private String getProductType(){
        boolean hasError = false;
        String productType = "";

        do{
            try{
                productType = view.getProdctType();
                hasError = false;
            }
            catch(OrderValidationException e){
                hasError = true;
                view.displayErrorMessage(e.getMessage());
            }
        } while(hasError);

        return productType;
    }

    private BigDecimal getArea(){
        boolean hasError = false;
        BigDecimal area = new BigDecimal(0);

        do{
            try{
                area = view.getArea();
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
