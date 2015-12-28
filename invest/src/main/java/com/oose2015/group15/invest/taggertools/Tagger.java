package com.oose2015.group15.invest.taggertools;

import java.util.ArrayList;


import org.sql2o.Connection;
import org.sql2o.Sql2o;

import com.oose2015.group15.invest.datatools.Data;
import com.oose2015.group15.invest.datatools.DbAccessor;
import com.oose2015.group15.invest.datatools.IndustryData;
import com.oose2015.group15.invest.datatools.StockData;

public class Tagger {
    private String PB;
    private String PE;
    private String PFCF;
    private String DY;
    private String ROE;
    
    static final String[] tags = {"VALUE", "GROWTH", "INCOME"};
    static final String VALUE = tags[0];
    static final String GROWTH = tags[1];
    static final String INCOME = tags[2];
    
    private ArrayList<Data> data = new ArrayList<Data>();
    private final int STOCK_INDEX = 2;
    private final int INDUSTRY_INDEX = 1;
    
    private Sql2o tagDb;
    
    /**
     *  This is a dummy value for now. It would be calculated
     *   by comparing the trend in the stock price over a period.
     */
    private boolean stablePriceTrend;
    
    
    /** Empty for now. */
    public Tagger(ArrayList<Data> dataFromQuery) {
        // Copy the stock and industry data over from the input array list.
        for (Data d: dataFromQuery) {
            this.data.add(d);
        }
        
        this.createDB();
    }
    
    private void createDB() {
        // Create the stock tag database, or connect to an existing one.
        try {
        	tagDb = new Sql2o(DbAccessor.configureDataSource(DbAccessor.TAG_DB_PATH));
        	Connection conn = tagDb.open();
    		
            String sql = "CREATE TABLE IF NOT EXISTS tag (ticker TEXT, value INT, growth INT, income INT)";
            conn.createQuery(sql).executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
    
    /**
     * Determine if a stock should be classified as growth, value, or income.
     * @param stock     the stock data object
     * @param industry  the industry data object
     * @return  growth, value, or income as appropriate
     * @throws SQLException 
     */
    //public String tagStock(boolean priceTrend) {
    public void tagStock(boolean priceTrend) {
        /*
         * Value:
         *  Low PB
         *  Low PE
         *  Low PFCF
         *  High DY
         *  Average to high ROE
         */
        
        StockData stock = (StockData) this.data.get(STOCK_INDEX);
        IndustryData industry = (IndustryData) this.data.get(INDUSTRY_INDEX);
        
        this.stablePriceTrend = priceTrend;
        Connection conn = tagDb.open();
        String ticker = stock.getTicker();
        
        String sql = "INSERT INTO tag (ticker, value, growth, income) " +
				"             VALUES (:ticker, :value, :growth, :income)";
        
        // Determine if the stock's valuation rations are below, at, or above industry values
        this.PE = compareToIndustry(stock.getPE(), industry.getPE());
        this.ROE = compareToIndustry(stock.getROE(), industry.getROE());
        this.DY = compareToIndustry(stock.getDY(), industry.getDY());
        this.PB = compareToIndustry(stock.getPB(), industry.getPB());
        this.PFCF = compareToIndustry(stock.getPFCF(), industry.getPFCF());
        
        // Determine the tag to give the stock.
        if (this.PE.equals("LOW") && this.PB.equals("LOW") && this.PFCF.equals("LOW")) {
            if (this.DY.equals("HIGH") && (this.ROE.equals("EQUAL") || this.ROE.equals("HIGH"))) {
            	conn.createQuery(sql)
            		.addParameter("ticker", ticker)
            		.addParameter("value", 1)
            		.addParameter("growth", 0)
            		.addParameter("income", 0)
            		.executeUpdate();
                
                
                //System.out.println("Successfully inserted into the database - VALUE"); 
            } else {
                double percentDifDY = getPercentDif(stock.getDY(), industry.getDY());
                double percentDifROE = getPercentDif(stock.getROE(), industry.getROE());
                
                if (percentDifDY >= -0.05 && percentDifROE >= -0.05) {                	
                	conn.createQuery(sql)
            		.addParameter("ticker", ticker)
            		.addParameter("value", 1)
            		.addParameter("growth", 0)
            		.addParameter("income", 0)
            		.executeUpdate();
                	
                    //System.out.println("Successfully inserted into the database - VALUE");
                    
                } else {
                	conn.createQuery(sql)
            		.addParameter("ticker", ticker)
            		.addParameter("value", 0)
            		.addParameter("growth", 1)
            		.addParameter("income", 0)
            		.executeUpdate();
                    
                    //System.out.println("Successfully inserted into the database - GROWTH");
                }
            } 
        } else if (this.stablePriceTrend && (this.DY.equals("HIGH") || stock.getDY() > 0.05)) {
        	conn.createQuery(sql)
    		.addParameter("ticker", ticker)
    		.addParameter("value", 0)
    		.addParameter("growth", 0)
    		.addParameter("income", 1)
    		.executeUpdate();
            
            
            //System.out.println("Successfully inserted into the database - INCOME");
        } else {
        	conn.createQuery(sql)
    		.addParameter("ticker", ticker)
    		.addParameter("value", 0)
    		.addParameter("growth", 1)
    		.addParameter("income", 0)
    		.executeUpdate();
        	
            //System.out.println("Successfully inserted into the database - GROWTH");
        } 
    }
    
    
    /**
     * Determine the percent difference between two doubles.
     *  This method is used to determine if two DY's or ROE's are within 5%
     *  of the industry values.
     * @param a the first double
     * @param b the second double
     * @return  the percent difference between a and b
     */
    private double getPercentDif(double a, double b) {
        return (a - b) / b;
    }
    
    
    /**
     * Determine if a stock's data value is below, at, or above the 
     *  industry's value
     * @param s the stock data value
     * @param i the industry data value
     * @return  low, equal, or high depending on the difference
     */
    private String compareToIndustry(double s, double i) {
        if (s < i) return "LOW";
        else if (s == i) return "EQUAL";
        else return "HIGH";
    }
}
