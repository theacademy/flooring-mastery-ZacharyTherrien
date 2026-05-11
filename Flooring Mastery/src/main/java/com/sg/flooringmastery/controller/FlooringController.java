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

        // Get the orders from the service layer only from a certain date
        List<Order> orders = service.getOrders(date);

        // If there are no orders or order file, exit
        if (orders == null || orders.isEmpty()){
            view.displayErrorMessage("No orders from: " + date);
            return;
        }

        orders.forEach(order -> view.displayOrder(order));
    }

    public void addOrder(){
        view.displayAddOrderBanner();

        // Declare variables to store the inputs for the order values first
        LocalDate date;
        String customerName;
        String state;
        String productType;
        BigDecimal area;

        // Get all the data using helper methods to contact the view
        date = getDate(true);
        customerName = getCustomerName(false);
        state = getState(false);
        productType = getProductType(false);
        area = getArea(false);

        Order order = new Order(service.getNextOrderNumber() , customerName, state, productType, area);
        service.calculateOrderCosts(order);

        // Display the order before prompting to add it
        view.displayOrder(order);

        // Ask the user to add the order, and if they confirm, add it to the dao
        if (view.confirmAddOrder()){
            service.addOrder(date, order);
        }
    }

    public void editOrder(){
        view.displayEditOrderBanner();

        // Declare variables to store the inputs for the order values first
        LocalDate date = LocalDate.now();
        int orderNumber = 0;
        String customerName = null;
        String state = null;
        String productType = null;
        BigDecimal area = null;

        // Use the helper methods to get the date and number of the order edit
        date = getDate(false);
        orderNumber = getOrderNumber();

        Order editedOrder = service.getOrder(date, orderNumber);

        // If the order does not exist, exit
        if (editedOrder == null) {
            view.displayErrorMessage("No order was found");
            return;
        }

        // Get all the data using helper methods to contact the view
        // Send true to the helper method to enable editing: where empty values are accepted
        customerName = getCustomerName(true);

        if(customerName != null){
            editedOrder.setCustomerName(customerName);
        }

        state = getState(true);

        if (state != null){
            editedOrder.setState(state);
        }

        productType = getProductType(true);

        if (productType != null){
            editedOrder.setProductType(productType);
        }

        area = getArea(true);

        if (area != null){
            editedOrder.setArea(area);
        }

        // If nothing was changed, exit
        if (customerName == null && state == null & productType == null && area == null){
            view.displayErrorMessage("No fields to edit inputted, returning to menu");
            return;
        }

        // Calculate the order's costs
        service.calculateOrderCosts(editedOrder);
        // Display the newly updated order
        view.displayOrder(editedOrder);

        // Ask the user to replace the original order, and if they confirm, replace it in the dao
        if (view.confirmEditOrder()){
            service.editOrder(date, editedOrder);
        }
    }

    public void removeOrder(){
        view.displayRemoveBanner();

        // Declare the variables to store the user's date and number of the order to delete
        LocalDate date = LocalDate.now();
        int orderNumber = 0;

        // Get the date and number of the order to delete from the user
        date = getDate(false);
        orderNumber = getOrderNumber();

        Order order = service.getOrder(date, orderNumber);

        // If the order doesn't exist, exit without removing
        if (order == null) {
            view.displayErrorMessage("No order was found");
            return;
        }

        // Display the order
        view.displayOrder(order);

        // If the user chooses that to remove the order, remove it from the dao
        if (view.confirmRemoveOrder()){
            view.displayRemoveResult(service.removeOrder(date, orderNumber));
        }
    }

    public void exportAllData(){
        // Call the service to export all the in-memory order data to persistence storage
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
                    return null;
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
                    return null;
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
                    return null;
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
