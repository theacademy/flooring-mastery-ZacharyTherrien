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
            scanner = new Scanner(new BufferedReader(new FileReader(TAX_FILE)));
        } catch (FileNotFoundException e) {
            throw new Error(e.getMessage());
        }

        String currentLine;
        Tax currentTax;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTax = unmarshallTax(currentLine);
            taxes.put(currentTax.getStateAbbreviation(), currentTax);
        }

        scanner.close();
    }

    private Tax unmarshallTax(String taxAsText){
        String[] taxTokens = taxAsText.split(DELIMITER);
        Tax tax = new Tax();

        tax.setStateAbbreviation(taxTokens[0]);
        tax.setStateName(taxTokens[1]);
        tax.setTaxRate(new BigDecimal(taxTokens[2]));

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
