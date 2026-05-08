package com.sg.flooringmastery.dao.product;

import java.math.BigDecimal;

public interface ProductDao {
    public BigDecimal getProductLaborPerSquareFoot(String productType);

    public BigDecimal getProductCostPerSquareFoot(String productType);
}
