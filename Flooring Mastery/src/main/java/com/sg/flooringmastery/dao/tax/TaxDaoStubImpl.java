package com.sg.flooringmastery.dao.tax;

import com.sg.flooringmastery.dto.Tax;

import java.math.BigDecimal;

public class TaxDaoStubImpl implements TaxDao{

    public Tax onlyTax;

    public TaxDaoStubImpl(){
        onlyTax = new Tax();
        onlyTax.setStateAbbreviation("CA");
        onlyTax.setStateName("California");
        onlyTax.setTaxRate(new BigDecimal("25"));
    }

    public TaxDaoStubImpl(Tax tax){
        this.onlyTax = tax;
    }

    @Override
    public BigDecimal getTaxRate(String stateAbbreviation) {
        if (onlyTax.getStateAbbreviation().equals(stateAbbreviation)){
            return new BigDecimal("4.45");
        }
        else{
            return null;
        }
    }

    @Override
    public Tax getTax(String stateAbbreviation) {
        if (onlyTax.getStateAbbreviation().equals(stateAbbreviation)){
            return onlyTax;
        }
        else{
            return null;
        }
    }
}
