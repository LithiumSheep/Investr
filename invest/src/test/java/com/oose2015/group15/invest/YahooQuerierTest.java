package com.oose2015.group15.invest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.Test;

import com.oose2015.group15.invest.datatools.Data;
import com.oose2015.group15.invest.datatools.IndustryData;
import com.oose2015.group15.invest.datatools.StockData;
import com.oose2015.group15.invest.datatools.StockMetaData;
import com.oose2015.group15.invest.datatools.ToolsException;
import com.oose2015.group15.invest.datatools.YahooQuerier;

/**
 * Tests if the YahooQuerier class is fetching the same data as our test cases.
 * 
 * NOTE:
 * These tests fail at some point because data is always changing, so we need to constantly update our 
 * hard-coded test data here. Will think of a better way of testing YahooQuerier
 * 
 *
 */
public class YahooQuerierTest {
	final int SECTOR_INDEX = 0;
	final int INDUSTRY_INDEX = 1;
	final int STOCK_INDEX = 2;
	YahooQuerier yq = new YahooQuerier();
	LinkedHashMap<String, StockData> stocks = new LinkedHashMap<String, StockData>();
	ArrayList<IndustryData> sectors = new ArrayList<IndustryData>();
	ArrayList<IndustryData> industries = new ArrayList<IndustryData>();
	
	@Before
	public void setup() {
		// SYMANTEC
		StockData SYMC = initTestCase("SYMC", "Symantec Corporation", "Security Software & Services",
									  "823", 20.433,11.383, 2.93, 29.531, 2.29, 10.414, 96.856);
		stocks.put(SYMC.getTicker(), SYMC);
		industries.add(new IndustryData("Security Software & Services", 25.8, 10.2, 2.951, 30.181, 10.12,
										11.1, 43.9));
		sectors.add(new IndustryData("Technology", 21.339, 18.155, 2.155, 78.560, 3.189, 12.567, -61.842));
		
		// APPLE
		StockData AAPL = initTestCase("AAPL", "Apple Inc.", "Electronic Equipment", "314",
									  12.551, 46.248, 1.84, 54.02, 5.409, 21.6, 68.741);
		stocks.put(AAPL.getTicker(), AAPL);
		industries.add(new IndustryData("Electronic Equipment", 13.5, 34.9, 1.728, 52.056, 5.29,
										17.3, 11.5));
		sectors.add(new IndustryData("Consumer Goods", 12.634,13.754,3.370,109.211,1.108,5.442,52.585));
	}
	
	@Test
	public void getCompleteStockDataTest() {
		YahooQuerier yq = new YahooQuerier();
		try {
			int cur = 0;
			for(String key: stocks.keySet()) {
				ArrayList<Data> fetched = yq.getCompleteStockData(key);
				assertFalse(String.format("Test case stock data for %s doesn't match fetched data.", key),
						   stocks.get(key).equals(fetched.get(STOCK_INDEX)));
				assertFalse(String.format("Test case industry data for %s doesn't match fetched data.", key),
							industries.get(cur).equals(fetched.get(INDUSTRY_INDEX)));
				assertFalse(String.format("Test case sector data for %s doesn't match fetched data.", key),
							sectors.get(cur).equals(fetched.get(SECTOR_INDEX)));
				cur++;
			}
		}
		catch(ToolsException ex) {
			assertTrue("ToolsException thrown: " + ex.getMessage(), false);
		}
		catch(Exception ex) {
			assertTrue("Runtime exception thrown: " + ex.getMessage(), false);
		}
	}

	/**
	 * Creates a StockData object to be tested against
	 * @param ticker
	 * @param name
	 * @param industry
	 * @param code
	 * @param pE
	 * @param rOE
	 * @param dY
	 * @param dE
	 * @param pB
	 * @param nPM
	 * @param pFCF
	 * @return
	 */
	public static StockData initTestCase(String ticker, String name, String industry, String code,
										 double pE, double rOE, double dY, double dE, double pB,
										 double nPM, double pFCF) {
		return new StockData(new StockMetaData(ticker, name, industry, code), pE, rOE, dY, dE, pB,
							 nPM, pFCF);
	}
}
