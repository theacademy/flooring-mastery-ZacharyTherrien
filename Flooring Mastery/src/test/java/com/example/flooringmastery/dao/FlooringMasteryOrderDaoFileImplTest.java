package com.example.flooringmastery.dao;

import com.sg.flooringmastery.dao.order.OrderDao;
import com.sg.flooringmastery.dao.order.OrderDaoFileImpl;
import com.sg.flooringmastery.dto.Order;
import org.junit.jupiter.api.*;
import org.springframework.cglib.core.Local;

import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FlooringMasteryOrderDaoFileImplTest {

    OrderDaoFileImpl testDao;
    String TEST_FOLDER = "./UnitTestData/Orders";

    public FlooringMasteryOrderDaoFileImplTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        // Remove all order files from the unit test folder
        File[] fileNames = new File(TEST_FOLDER).listFiles();
        if (fileNames != null){
            for (File fileName : fileNames) {
                fileName.delete();
            }
        }
        testDao = new OrderDaoFileImpl(TEST_FOLDER);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetNextOrdersNumber() {
        // Since there are no orders, the first, and next, order should have an ID of 1.
        assertEquals(1, testDao.getNextOrdersNumber(), "Next order number should be 1 if empty");
        // Add an emprt order to increment the count
        testDao.addOrder(LocalDate.now().plusDays(1), new Order());
        assertEquals(2, testDao.getNextOrdersNumber(), "Next order number should now be 2 after adding an order");
    }

    @Test
    public void testAddOrder() {
        // Create a date
        LocalDate date = LocalDate.now().plusDays(1);

        // Create a new order
        Order order = new Order();
        order.setCustomerName("Ada");
        order.setState("CA");
        order.setTaxRate(new BigDecimal("25"));
        order.setProductType("Tile");
        order.setArea(new BigDecimal("249"));
        order.setCostPerSquareFoot(new BigDecimal("3.50"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        order.setMaterialCost(new BigDecimal("871.5"));
        order.setLaborCost(new BigDecimal("1033.35"));
        order.setTax(new BigDecimal("476.2125"));
        order.setTotal(new BigDecimal("2381.0625"));

        // Add the order
        testDao.addOrder(date, order);

        // Get the student from the dao
        Order retrievedOrder = testDao.getOrder(date, order.getOrderNumber());

        // Check equality
        assertEquals(order, retrievedOrder);
        ;

        // Add a new order from a different date
        LocalDate dateTwo = LocalDate.now().plusDays(2);

        Order orderTwo = new Order();
        orderTwo.setCustomerName("Adam");
        orderTwo.setState("CA");
        orderTwo.setTaxRate(new BigDecimal("253"));
        orderTwo.setProductType("Tile");
        orderTwo.setArea(new BigDecimal("2491"));
        orderTwo.setCostPerSquareFoot(new BigDecimal("33.50"));
        orderTwo.setLaborCostPerSquareFoot(new BigDecimal("42.15"));
        orderTwo.setMaterialCost(new BigDecimal("8714.5"));
        orderTwo.setLaborCost(new BigDecimal("10323.35"));
        orderTwo.setTax(new BigDecimal("476.2125"));
        orderTwo.setTotal(new BigDecimal("9810.25"));

        testDao.addOrder(dateTwo, orderTwo);

        // Check that there are now two different dates in the orders map
        assertEquals(2, testDao.getOrdersByDate(date).size() +
                        testDao.getOrdersByDate(dateTwo).size(),
                "There are now two different order dates in the orders map");
    }

    @Test
    public void testGetOrdersByDate() {
        // Create a date
        LocalDate date = LocalDate.now().plusDays(1);

        // Create new rders
        Order order = new Order();
        order.setCustomerName("Ada");
        order.setState("CA");
        order.setTaxRate(new BigDecimal("25"));
        order.setProductType("Tile");
        order.setArea(new BigDecimal("249"));
        order.setCostPerSquareFoot(new BigDecimal("3.50"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        order.setMaterialCost(new BigDecimal("871.5"));
        order.setLaborCost(new BigDecimal("1033.35"));
        order.setTax(new BigDecimal("476.2125"));
        order.setTotal(new BigDecimal("2381.0625"));

        Order orderTwo = new Order();
        orderTwo.setCustomerName("Adam");
        orderTwo.setState("CA");
        orderTwo.setTaxRate(new BigDecimal("253"));
        orderTwo.setProductType("Tile");
        orderTwo.setArea(new BigDecimal("2491"));
        orderTwo.setCostPerSquareFoot(new BigDecimal("33.50"));
        orderTwo.setLaborCostPerSquareFoot(new BigDecimal("42.15"));
        orderTwo.setMaterialCost(new BigDecimal("8714.5"));
        orderTwo.setLaborCost(new BigDecimal("10323.35"));
        orderTwo.setTax(new BigDecimal("476.2125"));
        orderTwo.setTotal(new BigDecimal("9810.25"));

        // Add the orders
        testDao.addOrder(date, order);
        testDao.addOrder(date, orderTwo);

        // Retrieve orders by the date
        List<Order> orders = testDao.getOrdersByDate(date);

        // Check nullity, count, and contents of the list
        assertNotNull(order, "Given that orders were added, it should not be null");
        assertEquals(2, orders.size(), "Order size should be 2 given that we added 2 orders");
        assertTrue(testDao.getOrdersByDate(date).contains(order));
        assertTrue(testDao.getOrdersByDate(date).contains(orderTwo));
    }

    @Test
    public void testEditOrder(){
        // Create a date
        LocalDate date = LocalDate.now().plusDays(1);

        // Create a new order
        Order order = new Order();
        order.setCustomerName("Ada");
        order.setState("CA");
        order.setTaxRate(new BigDecimal("25"));
        order.setProductType("Tile");
        order.setArea(new BigDecimal("249"));
        order.setCostPerSquareFoot(new BigDecimal("3.50"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        order.setMaterialCost(new BigDecimal("871.5"));
        order.setLaborCost(new BigDecimal("1033.35"));
        order.setTax(new BigDecimal("476.2125"));
        order.setTotal(new BigDecimal("2381.0625"));

        // Add the order
        testDao.addOrder(date, order);

        // Create a new order to replace the original order, set the new order's ID to old order's
        Order orderEdit = new Order();
        orderEdit.setOrderNumber(order.getOrderNumber());
        orderEdit.setCustomerName("Adam");
        orderEdit.setState("CA");
        orderEdit.setTaxRate(new BigDecimal("253"));
        orderEdit.setProductType("Tile");
        orderEdit.setArea(new BigDecimal("2491"));
        orderEdit.setCostPerSquareFoot(new BigDecimal("33.50"));
        orderEdit.setLaborCostPerSquareFoot(new BigDecimal("42.15"));
        orderEdit.setMaterialCost(new BigDecimal("8714.5"));
        orderEdit.setLaborCost(new BigDecimal("10323.35"));
        orderEdit.setTax(new BigDecimal("476.2125"));
        orderEdit.setTotal(new BigDecimal("9810.25"));

        // Edit the order, send the first order's ID
        testDao.editOrder(date, orderEdit);

        assertNotEquals(order, testDao.getOrder(date, order.getOrderNumber()),
                "The order created and the one fetched from the dao using that order's ID are not the same");
    }

    @Test
    public void testRemoveOrder(){
        // Create a date
        LocalDate date = LocalDate.now().plusDays(1);

        // Create new orders
        Order order = new Order();
        order.setCustomerName("Ada");
        order.setState("CA");
        order.setTaxRate(new BigDecimal("25"));
        order.setProductType("Tile");
        order.setArea(new BigDecimal("249"));
        order.setCostPerSquareFoot(new BigDecimal("3.50"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        order.setMaterialCost(new BigDecimal("871.5"));
        order.setLaborCost(new BigDecimal("1033.35"));
        order.setTax(new BigDecimal("476.2125"));
        order.setTotal(new BigDecimal("2381.0625"));

        Order orderTwo = new Order();
        orderTwo.setCustomerName("Adam");
        orderTwo.setState("CA");
        orderTwo.setTaxRate(new BigDecimal("253"));
        orderTwo.setProductType("Tile");
        orderTwo.setArea(new BigDecimal("2491"));
        orderTwo.setCostPerSquareFoot(new BigDecimal("33.50"));
        orderTwo.setLaborCostPerSquareFoot(new BigDecimal("42.15"));
        orderTwo.setMaterialCost(new BigDecimal("8714.5"));
        orderTwo.setLaborCost(new BigDecimal("10323.35"));
        orderTwo.setTax(new BigDecimal("476.2125"));
        orderTwo.setTotal(new BigDecimal("9810.25"));

        // Add the orders
        testDao.addOrder(date, order);
        testDao.addOrder(date, orderTwo);

        // Remove the first order
        Order removedOrder = testDao.removeOrder(date, order.getOrderNumber());

        // Check the order was removed
        assertEquals(removedOrder, order, "The order under Ada's name should be removed");

        // Retrieve orders by the date
        List<Order> orders = testDao.getOrdersByDate(date);

        // Now check the dao to ensure the first order was removed but the second one remained
        assertFalse(orders.contains(order), "The list should not include Ada");
        assertTrue(orders.contains(orderTwo), "The list should include Adam");

        // Remove the second order
        removedOrder = testDao.removeOrder(date, orderTwo.getOrderNumber());

        // Check the order was removed
        assertEquals(removedOrder, orderTwo, "The order under Ada's name should be removed");

        // Retrieve orders by the date
        orders = testDao.getOrdersByDate(date);

        // Check that the list is now empty
        assertTrue(orders.isEmpty(), "All orders should be removed, therefore list should be empty");

        assertNull(testDao.getOrder(date, order.getOrderNumber()), "Ada was removed, should return null");
        assertNull(testDao.getOrder(date, orderTwo.getOrderNumber()), "Adam was removed, should return null");
    }

    @Test
    public void testExportOrder(){
        // Create a date
        LocalDate date = LocalDate.now().plusDays(1);

        // Create new orders
        Order order = new Order();
        order.setCustomerName("Ada");
        order.setState("CA");
        order.setTaxRate(new BigDecimal("25"));
        order.setProductType("Tile");
        order.setArea(new BigDecimal("249"));
        order.setCostPerSquareFoot(new BigDecimal("3.50"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        order.setMaterialCost(new BigDecimal("871.5"));
        order.setLaborCost(new BigDecimal("1033.35"));
        order.setTax(new BigDecimal("476.2125"));
        order.setTotal(new BigDecimal("2381.0625"));

        Order orderTwo = new Order();
        orderTwo.setCustomerName("Adam");
        orderTwo.setState("CA");
        orderTwo.setTaxRate(new BigDecimal("253"));
        orderTwo.setProductType("Tile");
        orderTwo.setArea(new BigDecimal("2491"));
        orderTwo.setCostPerSquareFoot(new BigDecimal("33.50"));
        orderTwo.setLaborCostPerSquareFoot(new BigDecimal("42.15"));
        orderTwo.setMaterialCost(new BigDecimal("8714.5"));
        orderTwo.setLaborCost(new BigDecimal("10323.35"));
        orderTwo.setTax(new BigDecimal("476.2125"));
        orderTwo.setTotal(new BigDecimal("2381.0625"));

        // Add the orders
        testDao.addOrder(date, order);
        testDao.addOrder(date, orderTwo);

        // Export the orders to a date file
        testDao.exportOrder();

        // Check that a file has been created
        File[] files = new File(TEST_FOLDER).listFiles();
        assertEquals(1, files.length, "Since the orders share the same date, there should be one order file");

        // Create a new dao that will load in the new values from the file
        OrderDao testDaoTwo = new OrderDaoFileImpl(TEST_FOLDER);

        // Check that it has read files from the file
        assertFalse(testDaoTwo.getOrdersByDate(date).isEmpty(), "The dao that has loaded in the files should not be empty");
        assertEquals(2, testDaoTwo.getOrdersByDate(date).size(), "Since we added 2 orders to the file, there should be 2 in this dao");

        // Assert that the values form the original, and the new dao that read from the file are the same
        assertEquals(testDao.getOrder(date, order.getOrderNumber()), testDaoTwo.getOrder(date, order.getOrderNumber()),
                "Order one from both files should be the same");

        // Create a new date
        LocalDate dateTwo = LocalDate.now().plusDays(2);

        // Add order two to this new date and export it
        testDaoTwo.addOrder(dateTwo, orderTwo);
        testDaoTwo.exportOrder();

        // Get read from the file again
        testDaoTwo = new OrderDaoFileImpl(TEST_FOLDER);

        // Check that a new file has been created
        File[] filesTwo = new File(TEST_FOLDER).listFiles();
        assertEquals(2, filesTwo.length, "Since an order has been created on a new date, there should be 2 files now");

        // Verify that the orders from both files have been added
        assertEquals(3, testDaoTwo.getOrdersByDate(date).size() + testDaoTwo.getOrdersByDate(dateTwo).size(),
                "From both files, it should read 3 different orders");

        // Now edit edit order 1 and export it

        // Create a new order to replace the original order, set the new order's ID to old order's
        Order orderEdit = new Order();
        orderEdit.setOrderNumber(orderTwo.getOrderNumber());
        orderEdit.setCustomerName("Apple");
        orderEdit.setState("TX");
        orderEdit.setTaxRate(new BigDecimal("253"));
        orderEdit.setProductType("Tile");
        orderEdit.setArea(new BigDecimal("2491"));
        orderEdit.setCostPerSquareFoot(new BigDecimal("33.50"));
        orderEdit.setLaborCostPerSquareFoot(new BigDecimal("42.15"));
        orderEdit.setMaterialCost(new BigDecimal("8714.5"));
        orderEdit.setLaborCost(new BigDecimal("10323.35"));
        orderEdit.setTax(new BigDecimal("476.2125"));
        orderEdit.setTotal(new BigDecimal("9810.25"));

        // Edit the order, send the first order's ID
        testDaoTwo.editOrder(date, orderEdit);
        testDaoTwo.exportOrder();

        // Read again from the file
        testDaoTwo = new OrderDaoFileImpl(TEST_FOLDER);

        // Test that the order has been edited
        assertNotEquals(orderTwo, testDaoTwo.getOrder(date, orderTwo.getOrderNumber()),
                "The order in the file should now contain the edited order");

        //Now remove the order
        testDaoTwo.removeOrder(date, orderEdit.getOrderNumber());
        testDaoTwo.exportOrder();

        // Read again from the file
        testDaoTwo = new OrderDaoFileImpl(TEST_FOLDER);
        assertNull(testDaoTwo.getOrder(date, orderEdit.getOrderNumber()),
                "Order removal was written to the file, therefore the order shouldn't exist");
    }
}
