package com.example.flooringmastery.dao;

import com.sg.flooringmastery.dao.tax.TaxDaoFileImpl;
import com.sg.flooringmastery.dto.Tax;
import org.junit.jupiter.api.*;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

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
        testDao = new TaxDaoFileImpl(testFile);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetTaxRate() {
        assertEquals(new BigDecimal("4.45"), testDao.getTaxRate("TX"),
                "Should return 4.45 for the Texas tax rate");
    }

    @Test
    public void testGetTax(){
        String stateAbbreviation = "TX";
        // Assure a none null value is returned
        assertNotNull(testDao.getTax(stateAbbreviation));
        //Create a new tax to ensure the right tax object is returned
        Tax tax = new Tax();
        tax.setStateAbbreviation("TX");
        tax.setStateName("Texas");
        tax.setTaxRate(new BigDecimal("4.45"));
        assertEquals(tax, testDao.getTax(stateAbbreviation));
    }
}
