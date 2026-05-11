package com.example.flooringmastery.dao;

import com.sg.flooringmastery.dao.product.ProductDaoFileImpl;
import com.sg.flooringmastery.dao.tax.TaxDaoFileImpl;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FlooringMasteryProductDaoFileImplTest {

    ProductDaoFileImpl testDao;

    public FlooringMasteryProductDaoFileImplTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws Exception{
        String testFile = "./UnitTestData/Products.txt";
        testDao = new ProductDaoFileImpl(testFile);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetProductLaborPerSquareFoot() {
        assertEquals(new BigDecimal("4.75"), testDao.getProductLaborPerSquareFoot("Wood"),
                "Should be the same value");
    }

    @Test
    public void testGetProductCostPerSquareFoot() {
        assertEquals(new BigDecimal("5.15"), testDao.getProductCostPerSquareFoot("Wood"),
                "Should be the same value");
    }

    @Test
    public void testGetAllProducts() {
        // Make sure list isn't null
        assertNotNull(testDao.getAllProducts(), "List should not be null");

        // Get size of list
        assertEquals(1, testDao.getAllProducts().size(),
                "There is currently only one object in the list");
    }

    @Test
    public void testGetProduct() {
        // Create the value in the dao
        Product product = new Product();
        product.setProductType("Wood");
        product.setCostPerSquareFoot(new BigDecimal("5.15"));
        product.setLaborCostPerSquareFoot(new BigDecimal("4.75"));

        // Assure that the dao returns the object
        assertEquals(product, testDao.getProduct(product.getProductType()), "Products should be the same");
    }
}
