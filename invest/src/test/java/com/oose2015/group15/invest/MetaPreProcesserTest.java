package com.oose2015.group15.invest;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import com.oose2015.group15.invest.datatools.DbAccessor;
import com.oose2015.group15.invest.datatools.MetaPreProcessor;
import com.oose2015.group15.invest.datatools.StockMetaData;
import com.oose2015.group15.invest.datatools.YahooQuerier;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;

/**
 * Tests whether or not the correct information is loaded from Yahoo Finance's stocks meta data list.
 * Some data are omitted from the database because they have n/a values. Therefore,
 * we can't test all cases from the CSV file. We have to pick certain valid cases and verify that they're 
 * loaded into the database.
 * @author Kathleen
 *
 */
public class MetaPreProcesserTest {
	private ArrayList<StockMetaData> testCases = new ArrayList<StockMetaData>();
	
	@Before
	public void setup() throws Exception {
		// Northrop Grumman
		StockMetaData t1 = new StockMetaData("NOC", "Northrop Grumman Corporation",
											  "Aerospace/Defense - Major Diversified", "610");
		testCases.add(t1);
		
		// Apple
		StockMetaData t2 = new StockMetaData("AAPL", "Apple Inc.",
											 "Electronic Equipment", "314");
		testCases.add(t2);
		
		// FedEx Corporation
		StockMetaData t3 = new StockMetaData("FDX", "FedEx Corporation",
											 "Air Delivery & Freight Services", "773");
		testCases.add(t3);
		
		// Microsoft
		StockMetaData t4 = new StockMetaData("MSFT", "Microsoft Corporation",
											 "Business Software & Services", "826");
		testCases.add(t4);
	}
	
	@Test
	public void testDb() {
		try {
			String sql = "SELECT * FROM item WHERE ticker = :ticker ";
			Sql2o db = DbAccessor.getMetaDb();
			Connection conn = db.open();
			
			for(StockMetaData test: testCases) {
				StockMetaData s = conn.createQuery(sql)
						.addParameter("ticker", test.getTicker())
						.executeAndFetchFirst(StockMetaData.class);
				assertTrue(s != null);
				assertEquals(s.getTicker(), test.getTicker());
				assertEquals(s.getName(), test.getName());
				assertEquals(s.getIndustry(), test.getIndustry());
				assertEquals(s.getCode(), test.getCode());
			}
		} catch(Exception ex) {
			assertFalse(true);
		}
	}    
}
