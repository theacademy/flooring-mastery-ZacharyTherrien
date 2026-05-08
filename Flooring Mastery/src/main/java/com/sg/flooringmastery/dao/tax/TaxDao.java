package com.sg.flooringmastery.dao.tax;

import java.math.BigDecimal;

public interface TaxDao {
    public BigDecimal getTaxRate(String state);
}
