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
    private int highestOrderNumber = 0;

    public OrderDaoFileImpl(){
        orders = new HashMap<>();
        loadOrders();
    }

    private void loadOrders(){
        Scanner scanner;

        // First, get all the order files from the order folder
        File[] fileNames = new File(ORDERS_FOLDER).listFiles();

        // If there are no files, exit
        if (fileNames == null){
            return;
        }

        // Loop over each file and add the order to map for the date
        for (int i = 0; i < fileNames.length; i++){
            // Get the current file name
            String filename = fileNames[i].getName();

            // Reformat the filename to the proper LocalDate format
            String filenameSplit = filename.split("\\.")[0].split("_")[1];
            String dateString = "" +
                    filenameSplit.charAt(0) + filenameSplit.charAt(1) + "-" +
                    filenameSplit.charAt(2) + filenameSplit.charAt(3) + "-" +
                    filenameSplit.charAt(4) + filenameSplit.charAt(5) + filenameSplit.charAt(6) + filenameSplit.charAt(7);

            // Parse the formatted filename into LocalDate
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
        // Get what will be the next order number, without incrementing it
        return highestOrderNumber + 1;
    }

    @Override
    public List<Order> getOrdersByDate(LocalDate date) {
        // Exit if no date was found
        if (!orders.containsKey(date)){
            return null;
        }

        // Return the list of object from the map
        return new ArrayList<>(orders.get(date).values());
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) {
        // If order doesn't exist then exit
        if (!orders.containsKey(date)){
            return null;
        }

        // Return the order from the respective map representing the date
        return orders.get(date).get(orderNumber);
    }

    @Override
    public Order addOrder(LocalDate date, Order order) {
        // Set the given order to the next oroder number
        order.setOrderNumber(getNextOrdersNumber());
        // Increment next order number only here when adding orders
        highestOrderNumber = highestOrderNumber + 1;
        // If a new date is given, create a hashmap to represent it
        if (!orders.containsKey(date)){
            orders.put(date, new HashMap<>());
        }
        // Add the date to its respective date
        orders.get(date).put(order.getOrderNumber(), order);
        return order;
    }

    @Override
    public Order editOrder(LocalDate date, Order order) {
        // Exit if orders does not contain the given date
        if (!orders.containsKey(date)){
            return null;
        }

        // Exit if the orders does not contain the given order within the given date
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

        // Replace the original order with the edited order
        orders.get(date).replace(order.getOrderNumber(), order);
        return order;
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) {
        // Exit if the date does not exist
        if (!orders.containsKey(date)){
            return null;
        }

        return orders.get(date).remove(orderNumber);
    }

    @Override
    public void exportOrder() {
        PrintWriter out;

        // Loop over each date in the orders map
        for (LocalDate date : orders.keySet()){
            // Get the value of the LocalDate and format the values to match Orders_ddMMyyyy
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
                // Create PrintWriter to the respective order file
                out = new PrintWriter(new FileWriter(ORDERS_FOLDER + "/" + filename + ".txt"));
            } catch (IOException e) {
                throw new OrderPersistenceException(e.getMessage());
            }
                String orderAsText;
                List<Order> orderList = orders.get(date).values().stream().toList();
                for (Order order : orderList) {
                    // Turn the order into a String
                    orderAsText = marshallOrders(order);
                    // Write the order object to the file
                    out.println(orderAsText);
                    // Write line to the file
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
        // Parse the string by the designated delimiter for each of its properties
        String[] orderTokens = orderLine.split(DELIMITER);

        // Create a new order object
        Order order = new Order();

        // Set the data from the parsed order string
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

        // Return the order object
        return order;
    }

    private String marshallOrders(Order order){
        // Create a string and add each property of the order to it
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
