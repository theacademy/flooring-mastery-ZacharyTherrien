package com.sg.flooringmastery.dao.tax;

import com.sg.flooringmastery.dto.Tax;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;

public class TaxDaoFileImpl implements TaxDao{

    final String TAX_FILE = "./Data/Taxes.txt";
    final String DELIMITER = "::";
    Map<String, Tax> taxes;

    public TaxDaoFileImpl(){
        taxes = new HashMap<>();
        loadTaxes();
    }

    private void loadTaxes(){
        Scanner scanner;

        try {
            // Create a scanner to read the tax file
            scanner = new Scanner(new BufferedReader(new FileReader(TAX_FILE)));
        } catch (FileNotFoundException e) {
            throw new Error(e.getMessage());
        }

        String currentLine;
        Tax currentTax;

        while (scanner.hasNextLine()) {
            // Read the current line from the tax file
            currentLine = scanner.nextLine();
            // Get the tax object from the string
            currentTax = unmarshallTax(currentLine);
            // Add the tax object to the taxes map
            taxes.put(currentTax.getStateAbbreviation(), currentTax);
        }

        // Clean up
        scanner.close();
    }

    private Tax unmarshallTax(String taxAsText){
        // Parse the give tax string by the designated delimiter
        String[] taxTokens = taxAsText.split(DELIMITER);

        // Create a new tax object
        Tax tax = new Tax();

        // Set the values of the tax object to the parsed values
        tax.setStateAbbreviation(taxTokens[0]);
        tax.setStateName(taxTokens[1]);
        tax.setTaxRate(new BigDecimal(taxTokens[2]));

        // Return the tax object
        return tax;
    }

    @Override
    public BigDecimal getTaxRate(String stateAbbreviation) {
        return taxes.get(stateAbbreviation).getTaxRate();
    }

    @Override
    public Tax getTax(String stateAbbreviation) {
        return taxes.get(stateAbbreviation);
    }
}
