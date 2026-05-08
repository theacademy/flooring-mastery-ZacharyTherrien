package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dao.order.OrderDao;
import com.sg.flooringmastery.dto.Order;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FlooringView {
    private UserIO io;

    public FlooringView(UserIO io){
        this.io = io;
    }

    public int displayMenu(){
        System.out.println("\n  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *" +
                "\n  * <<Flooring Program>>" +
                "\n  * 1. Display Orders" +
                "\n  * 2. Add an Order" +
                "\n  * 3. Edit an Order" +
                "\n  * 4. Remove an Order" +
                "\n  * 5. Export All Data" +
                "\n  * 6. Quit" +
                "\n  *" +
                "\n  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");

        return io.readInt("Please select from the choices above.");
    }

    public void displayExitMessage(){
        System.out.println("\nExiting Program.");
    }

    public int getOrderNumber(){
        return io.readInt("Please enter the order number");
    }

    public LocalDate getOrderDate(){
        return io.readLocalDate("Please enter the date of the order");
    }

    public String getCustomerName(){
        return io.readString("Please enter the customer's name");
    }

    public String getCustomerState(){
        return io.readString("Please enter the State that the customer is in");
    }

    public String getProdctType(){
        return io.readString("Please enter the type of flooring");
    }

    public BigDecimal getArea(){
        return io.readBigDecimal("Please enter the area needed for your order");
    }

    public void displayOrder(Order order){
        io.print(order.toString());
    }

    public void displayErrorMessage(String message){
        io.print(message);
    }

    public void displaySaveSuccess(String message){
        io.print(message);
    }

    public boolean confirmAddOrder(){
        String input;
        while(true) {
            input = io.readString("Confirm adding the inputted data as an order? \nPlease enter Y\\N");
            if (input.equals("Y")){
                return true;
            } else if (input.equals("N")) {
                return false;
            }
            else{
                displayErrorMessage("Invalid input, please try again.");
            }
        }
    }

    public boolean confirmEditOrder(){
        String input;
        while(true) {
            input = io.readString("Confirm the changes made to the order? \nPlease enter Y\\N");
            if (input.equals("Y")){
                return true;
            } else if (input.equals("N")) {
                return false;
            }
            else{
                displayErrorMessage("Invalid input, please try again.");
            }
        }
    }

    public boolean confirmRemoveOrder(){
        String input;
        while(true) {
            input = io.readString("Confirm removing the order? \nPlease enter Y\\N");
            if (input.equals("Y")){
                return true;
            } else if (input.equals("N")) {
                return false;
            }
            else{
                displayErrorMessage("Invalid input, please try again.");
            }
        }
    }

    public void displayAddOrderBanner(){
        io.print("=== Add Order ===");
    }

    public void displayEditOrderBanner(){
        io.print("=== Edit Orders ===");
    }

    public void displayRemoveBanner(){
        io.print("=== Remove Orders ===");
    }

    public void displayRemoveResult(Order order){
        if(order != null){
            io.print("Order removed successfully");
        }
        else {
            io.print("Invalid order");
        }
        io.print("Please press enter to continue");
    }

    public void displayUnknownCommand() { io.print("Unknown command");}

    public void displayClearBuffer() { io.readString(""); }
}
