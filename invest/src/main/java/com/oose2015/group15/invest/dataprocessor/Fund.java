package com.oose2015.group15.invest.dataprocessor;

import java.util.List;

public class Fund extends Security {

    //public Fund(String sT, String rT, String tH, String lN) {
    public Fund(String sT, List<String> iO, String rT, boolean[] tH, String lN) {
        super(sT, iO, rT, tH, lN);
    }

}
