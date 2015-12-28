package com.oose2015.group15.invest.dataprocessor;

import java.util.List;

public class Stock extends Security {
    private String tag;
    
    public Stock(String sT, List<String> iO, String rT, 
            boolean[] tH, String lN, String t) {
        super(sT, iO, rT, tH, lN);
        
        assert(t.equalsIgnoreCase("Value") || 
                t.equalsIgnoreCase("Growth") || 
                t.equalsIgnoreCase("Income") ||
                t.equalsIgnoreCase("Preferred"));
        this.tag = t;
    }

    /** Get the stock's tag. */
    public String getTag() {
        return this.tag;
    }
}
