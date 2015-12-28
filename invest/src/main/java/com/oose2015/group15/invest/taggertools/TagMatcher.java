package com.oose2015.group15.invest.taggertools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import com.oose2015.group15.invest.dataprocessor.Security;
import com.oose2015.group15.invest.dataprocessor.Stock;
import com.oose2015.group15.invest.datatools.DbAccessor;

/**
 * Take a list of securities from the user, and cross-match
 *  the stocks' tags with the stocks in the tag database.
 * @author Jeremy
 *
 */
public class TagMatcher {

    /**
     * Query the tag database and get matches
     * @throws IOException 
     */
    public static List<String> getMatches(List<Security> toSuggest) throws IOException {
    	List<String> tickers = new ArrayList<String>();

    	for (Security s : toSuggest) {
    		// only query if Stock
    		if (s instanceof Stock) {
    			Stock stock = (Stock) s;

    			// unsupported tag
    			//System.out.println("THE STOCK IS: " + stock.getTag());
    			
    			if(!stock.getTag().equalsIgnoreCase("PREFERRED")) { 
    			    //System.out.println("ADDING STOCKS");
    			    
    				tickers.addAll(getMatches(stock.getTag().toLowerCase()));
    			}
    		}
    	}
    	
    	//System.out.println("THE SIZE OF tickers IS: " + tickers.size());
    	
    	return tickers;
    }

    
    /**
     * Given a tag, get all the ticker codes that match that one tag
     * @param tag
     * @return	list of ticker codes
     * @throws IOException
     */
    public static List<String> getMatches(String tag) throws IOException {
    	Sql2o tagDb = DbAccessor.getTagDb();

    	Connection conn = tagDb.open();

    	String sql = "SELECT ticker FROM tag WHERE %s = 1";
    	
    	// get all tickers where the tag's field is set to 1
    	List<String> tickers = conn.createQuery(String.format(sql, tag))
    							   .executeAndFetch(String.class);
    	return tickers;
    }
}
