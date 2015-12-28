package com.oose2015.group15.invest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.oose2015.group15.invest.dataprocessor.ClientProfile;
import com.oose2015.group15.invest.dataprocessor.ProfileProcessor;
import com.oose2015.group15.invest.dataprocessor.InvestmentDomain;
import com.oose2015.group15.invest.dataprocessor.Security;
import com.oose2015.group15.invest.datatools.Data;
import com.oose2015.group15.invest.datatools.IndustryData;
import com.oose2015.group15.invest.datatools.StockData;
import com.oose2015.group15.invest.datatools.StockMetaData;
import com.oose2015.group15.invest.taggertools.Tagger;
import com.oose2015.group15.invest.taggertools.TagMatcher;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProfileProcessorTest {
    //private ProfileProcessorDriver driver = new ProfileProcessorDriver();
    private List<Security> suggestions = new ArrayList<Security>();
    
    /**
     * Test the processor to see that it can generate investment suggestions
     *  based on the preferences in a completed client profile.
     * @throws SQLException 
     */
    @Test
    public void test() throws SQLException {
        // Create a test client for the profile processor. 
        ArrayList<String> objectives1 = new ArrayList<String>();
   
        objectives1.add("Growth");
        
        String id1 = "Client1";
        String time1 = "Very Long Term";
        String risk1 = "Moderate High Risk";
        String liquidity1 = "Moderate Liquidity";
        boolean dependents1 = false;
        int tax1 = 30;
        
        // InvestmentDomain.init();
        ClientProfile client1 = new ClientProfile(objectives1, time1, risk1, liquidity1, dependents1, tax1);
        ProfileProcessor pP1 = new ProfileProcessor(client1, InvestmentDomain.getSecurities());

        
        // Get the list of suggested securities. 
        // pP1.getInvestmentSuggestions();
        this.suggestions = pP1.getInvestmentSuggestions();

        
        // Test that the processor actually generates investment suggestions. 
        assertTrue(this.suggestions.size() != 0);
        
        
        
        // This is the code from TaggerDriver. This will create the tag DB, 
        //  tag Apple's stock, and insert the tag into the DB
        StockMetaData appleMeta = new StockMetaData("AAPL", "Apple", "Electronic Equipment", "314");
        StockData appleStock = new StockData(appleMeta, 13.08, 46.25, 1.84, 54.02, 5.63, 21.60, 71.62);
        IndustryData electronicsIndustry = new IndustryData("Electronic Equipment", 13.50, 34.90, 1.73, 52.10, 5.29, 17.30, 11.50);
        IndustryData electronicsSector = new IndustryData("SECTOR", 1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 7.7);
        ArrayList<Data> data = new ArrayList<Data>();
        boolean stablePrice = false;
        
        data.add(electronicsSector);
        data.add(electronicsIndustry);
        data.add(appleStock);
        
        Tagger testTagger1 = new Tagger(data);
        testTagger1.tagStock(stablePrice);
        
        // Cross-match the stocks in the securities list with those 
        //  in the tags database
        //TagMatcher.setStocks(this.suggestions);
        try {
        	List<String> tickers = TagMatcher.getMatches(suggestions);
        	assertTrue(tickers.size() != 0);
        }
        catch(IOException ex) {
        	System.out.println(ex);
        }
        
        
    }

}
