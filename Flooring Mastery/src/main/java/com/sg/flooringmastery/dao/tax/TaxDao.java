package com.sg.flooringmastery.dao.tax;

import com.sg.flooringmastery.dto.Tax;

import java.math.BigDecimal;

public interface TaxDao {

    public BigDecimal getTaxRate(String stateAbbreviation);

    public Tax getTax(String stateAbbreviation);
}
