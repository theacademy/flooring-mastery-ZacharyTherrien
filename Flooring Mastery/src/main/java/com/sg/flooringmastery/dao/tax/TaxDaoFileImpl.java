package com.sg.flooringmastery.dao.tax;

import com.sg.flooringmastery.dto.Tax;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class TaxDaoFileImpl implements TaxDao{

    final String TAX_FILE = "./Data/Taxes.txt";
    final String DELIMITER = "::";
    Map<String, Tax> taxes;

    public TaxDaoFileImpl(){
        taxes = new HashMap<>();
        loadTaxes();
    }

    private void loadTaxes(){

    }

    @Override
    public BigDecimal getTaxRate(String state) {
        /**
         *
         * COMPARE AGAINST MAP TO FIND STATE'S RESPECTIVE TAX RATE
         * FIND STATE BY STATE'S FULL NAME OR ABBREVIATRION
         *
         */
        return null;
    }
}
