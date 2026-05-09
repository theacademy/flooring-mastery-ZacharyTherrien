package com.sg.flooringmastery.dao.order;

import com.sg.flooringmastery.dto.Order;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderDaoFileImpl implements OrderDao {

    private final String ORDERS_FOLDER = "./Orders";
    private final String DELIMITER = "::";
    private Map<LocalDate, Map<Integer, Order>> orders;
    private int highestOrderNumber = 3;

    public OrderDaoFileImpl(){
        orders = new HashMap<>();
        loadOrders();
    }

    private void loadOrders(){
        Scanner scanner;

        File[] fileNames = new File(ORDERS_FOLDER).listFiles();

        if (fileNames == null){
            return;
        }

        // Loop over each file and add each order to Map of respective date in map
        for (int i = 0; i < fileNames.length; i++){
            String filename = fileNames[i].getName();

            String filenameSplit = filename.split("\\.")[0].split("_")[1];
            String dateString = "" +
                    filenameSplit.charAt(0) + filenameSplit.charAt(1) + "-" +
                    filenameSplit.charAt(2) + filenameSplit.charAt(3) + "-" +
                    filenameSplit.charAt(4) + filenameSplit.charAt(5) + filenameSplit.charAt(6) + filenameSplit.charAt(7);

            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
            Map<Integer, Order> ordersOnDate = new HashMap<>();
            orders.put(date, ordersOnDate);

            try {
                // Create Scanner for reading the file
                scanner = new Scanner(new BufferedReader(new FileReader(ORDERS_FOLDER + "/" + filename)));
            } catch (FileNotFoundException e) {
                throw new OrderPersistenceException(e.getMessage());
            }

            String currentLine;
            Order currentOrder;

            while (scanner.hasNextLine()) {
                currentLine = scanner.nextLine();
                // Parse the line into an order object
                currentOrder = unmarshallOrder(currentLine);
                // After data has been parsed, add it to its respective order map
                ordersOnDate.put(currentOrder.getOrderNumber(), currentOrder);
                // Set the value of the highest order number found
                if (currentOrder.getOrderNumber() > highestOrderNumber){
                    highestOrderNumber = currentOrder.getOrderNumber();
                }
            }

            // close scanner
            scanner.close();
        }

    }

    @Override
    public int getNextOrdersNumber() {
        highestOrderNumber++;
        return highestOrderNumber;
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
        PrintWriter out;

        for (LocalDate date : orders.keySet()){
            String month = String.valueOf(date.getMonthValue());
            if (month.length() < 2){
                month = "0" + month;
            }
            String day = String.valueOf(date.getDayOfMonth());
            if (day.length() < 2){
                day = "0" + day;
            }
            String filename = "Orders_" +month + day + date.getYear();

            try {
                out = new PrintWriter(new FileWriter(ORDERS_FOLDER + "/" + filename + ".txt"));
            } catch (IOException e) {
                throw new OrderPersistenceException(e.getMessage());
            }
                String orderAsText;
                List<Order> orderList = orders.get(date).values().stream().toList();
                for (Order order : orderList) {
                    // turn a Student into a String
                    orderAsText = marshallOrders(order);
                    // write the Student object to the file
                    out.println(orderAsText);
                    // force PrintWriter to write line to the file
                    out.flush();
                }

                // Clean up
                out.close();
        }
    }

    @Override
    public void exportDataToBackup() {

    }

    private Order unmarshallOrder(String orderLine){
        String[] orderTokens = orderLine.split(DELIMITER);

        Order order = new Order();

        order.setOrderNumber(Integer.parseInt(orderTokens[0]));
        order.setCustomerName(orderTokens[1]);
        order.setState(orderTokens[2]);
        order.setTaxRate(new BigDecimal(orderTokens[3]));
        order.setProductType(orderTokens[4]);
        order.setArea(new BigDecimal(orderTokens[5]));
        order.setCostPerSquareFoot(new BigDecimal(orderTokens[6]));
        order.setLaborCostPerSquareFoot(new BigDecimal(orderTokens[7]));
        order.setMaterialCost(new BigDecimal(orderTokens[8]));
        order.setLaborCost(new BigDecimal(orderTokens[9]));
        order.setTax(new BigDecimal(orderTokens[10]));
        order.setTotal(new BigDecimal(orderTokens[11]));

        return order;
    }

    private String marshallOrders(Order order){
        String orderAsText = order.getOrderNumber() + DELIMITER;
        orderAsText += order.getCustomerName() + DELIMITER;
        orderAsText += order.getState() + DELIMITER;
        orderAsText += order.getTaxRate() + DELIMITER;
        orderAsText += order.getProductType() + DELIMITER;
        orderAsText += order.getArea() + DELIMITER;
        orderAsText += order.getCostPerSquareFoot() + DELIMITER;
        orderAsText += order.getLaborCostPerSquareFoot() + DELIMITER;
        orderAsText += order.getMaterialCost() + DELIMITER;
        orderAsText += order.getLaborCost() + DELIMITER;
        orderAsText += order.getTax() + DELIMITER;
        orderAsText += order.getTotal() + DELIMITER;
        return orderAsText;
    }
}
