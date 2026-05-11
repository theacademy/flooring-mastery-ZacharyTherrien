package com.example.flooringmastery.service;

import com.sg.flooringmastery.dao.order.OrderPersistenceException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.service.OrderValidationException;
import com.sg.flooringmastery.service.ServiceLayer;
import org.junit.jupiter.api.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FlooringMasteryServiceLayerImplTest {

    private ServiceLayer service;

    public FlooringMasteryServiceLayerImplTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        service = ctx.getBean("serviceLayer", ServiceLayer.class);
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    void testGetOrder(){
        // Arrange
        LocalDate date = LocalDate.now();
        int orderNumber = 1;
        // Act & Assert
        assertNotNull(service.getOrder(date, orderNumber), "Order 1 should exist and be retrievable");
    }

    @Test void testGetNextOrders(){
        // Arrange
        LocalDate date = LocalDate.now();
        com.sg.flooringmastery.dto.Order order = new Order();
        order.setOrderNumber(1);
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

        // Act & Assert
        assertNotNull(service.getOrders(date) , "Order 1 should exist and a list containing it should be retrievable");
        assertEquals(order, service.getOrders(date).get(0), "Order 1 should be retrieved from the list");
    }

    @Test void testGetNextOrderNumber(){
        // Arrange
        int nextOrderNumber = 2;
        // Act & Assert
        assertEquals(nextOrderNumber, service.getNextOrderNumber(), "DAO contains one order, therefore next number should be 2");
    }

    @Test
    public void testAddOrder() {
        // Arrange
        LocalDate date = LocalDate.now();
        com.sg.flooringmastery.dto.Order order = new Order();
        order.setOrderNumber(1);
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

        // Act & Assert
        assertEquals(order, service.addOrder(date, order), "Order and the order added should be the same");
    }

    @Test
    public void testRemoveOrder() {
        // Arrange
        LocalDate date = LocalDate.now();
        com.sg.flooringmastery.dto.Order order = new Order();
        order.setOrderNumber(1);
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

        // Act & Assert
        assertEquals(order, service.removeOrder(date, order.getOrderNumber()), "Order and the removedorder should be the same");
    }

    @Test
    public void testCalculateOrder() {
        // Arrange
        LocalDate date = LocalDate.now();
        com.sg.flooringmastery.dto.Order order = new Order();
        order.setOrderNumber(1);
        order.setCustomerName("Ada");
        order.setState("CA");
        order.setTaxRate(new BigDecimal("25"));
        order.setProductType("Tile");
        order.setArea(new BigDecimal("249"));
        order.setCostPerSquareFoot(new BigDecimal("3.50"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.15"));

        // Act & Assert
        assertEquals(order, service.calculateOrderCosts(order), "Order and the order added should be the same");
    }

    @Test
    public void testValidateOrderDate() {
        // Arrange
        LocalDate date = LocalDate.now().minusDays(1);

        // Act
        try{
            service.validateOrderDate(date, true);
            // Assert
            fail("Expected error not thrown");
        }
        catch (OrderValidationException ignored){
        }

        // Arrange
        date = LocalDate.now().plusDays(1);

        // Act
        try{
            service.validateOrderDate(date, true);
        }
        catch (OrderValidationException e){
            // Assert
            fail("Unexpected error not thrown");
        }
    }

    @Test
    public void testValidateCustomerName(){
        // Arrange
        String name = "";

        // Act
        try{
            service.validateCustomerName(name);
            // Assert
            fail("Expected error not thrown");
        }
        catch (OrderValidationException ignored){
        }

        // Arrange
        name = "  ";

        // Act
        try{
            service.validateCustomerName(name);
            // Assert
            fail("Expected error not thrown");
        }
        catch (OrderValidationException ignored){
        }

        // Arrange
        name = "Able to-pass.test,";

        // Act
        try{
            service.validateCustomerName(name);
        }
        catch (OrderValidationException e){
            // Assert
            fail("Unexpected error not thrown");
        }
    }

    @Test
    public void testValidateCustomerState(){
        // Arrange
        String state = "";

        // Act
        try{
            service.validateCustomerState(state);
            // Assert
            fail("Expected error not thrown");
        }
        catch (OrderValidationException ignored){
        }

        // Arrange
        state = " ";

        // Act
        try{
            service.validateCustomerState(state);
            // Assert
            fail("Expected error not thrown");
        }
        catch (OrderValidationException ignored){
        }

        // Arrange
        state = "CA";

        // Act
        try{
            service.validateCustomerState(state);
        }
        catch (OrderValidationException ignored){
            // Assert
            fail("Unexpected error not thrown");
        }
    }

    @Test
    public void testValidateProductType(){
        // Arrange
        String product = "";

        // Act
        try{
            service.validateProductType(product);
            // Assert
            fail("Expected error not thrown");
        }
        catch (OrderValidationException ignored){
        }

        // Arrange
        product = " ";

        // Act
        try{
            service.validateProductType(product);
            // Assert
            fail("Expected error not thrown");
        }
        catch (OrderValidationException ignored){
        }

        // Arrange
        product = "Tile";

        // Act
        try{
            service.validateProductType(product);
        }
        catch (OrderValidationException ignored){
            // Assert
            fail("Unexpected error not thrown");
        }
    }

    @Test
    public void testValidateArea(){
        BigDecimal area = new BigDecimal("99");

        // Act
        try{
            service.validateArea(area);
            // Asses
            fail("Expected exception");
        }
        catch (OrderValidationException ignore){
        }

        // Arrange
        area = new BigDecimal("100");

        // Act
        try{
            service.validateArea(area);
        }
        catch (OrderValidationException e){
            // Asses
            fail("Unexpected exception");
        }
    }

    @Test
    public void testgetAllProducts(){
        Product product = new Product();
        product.setProductType("Tile");
        product.setCostPerSquareFoot(new BigDecimal("3.50"));
        product.setLaborCostPerSquareFoot(new BigDecimal("4.15"));

        List<Product> products = new ArrayList<>();
        products.add(product);

        assertNotNull(service.getAllProducts(), "List of products should exist");
        assertEquals(products, service.getAllProducts());
    }
}
