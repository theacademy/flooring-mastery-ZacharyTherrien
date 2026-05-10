package com.example.flooringmastery.dao;

import com.sg.flooringmastery.dao.tax.TaxDaoFileImpl;
import org.junit.jupiter.api.*;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

public class FlooringMasteryTaxDaoFileImplTest {

    TaxDaoFileImpl testDao;

    public FlooringMasteryTaxDaoFileImplTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws Exception{
        String testFile = "./UnitTestData/Taxes.txt";
        // Use the FileWriter to quickly blank the file
        new FileWriter(testFile);
        testDao = new TaxDaoFileImpl(testFile);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetTaxRate() {

    }
}
