package com.sg.flooringmastery.dao.tax;

import com.sg.flooringmastery.dto.Tax;

import java.math.BigDecimal;

public class TaxDaoStubImpl implements TaxDao{
    @Override
    public BigDecimal getTaxRate(String state) {
        return null;
    }

    @Override
    public Tax getTax(String stateAbbreviation) {
        return null;
    }
}
