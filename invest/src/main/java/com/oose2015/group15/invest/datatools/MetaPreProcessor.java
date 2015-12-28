package com.oose2015.group15.invest.datatools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import com.opencsv.CSVParser;
import com.opencsv.CSVReader;

/**
 * Loads CSV file of Yahoo Finance's stock meta data to a database
 * @author Kathleen
 *
 */
public class MetaPreProcessor {
	/* Specific indexes of data in the CSV file */
	private static final int TICKER_INDEX = 0;
	static final int NAME_INDEX = 1;
	static final int INDUSTRY_INDEX = 3;
	static final int CODE_INDEX = 4;
	
	private Sql2o db;
	private YahooQuerier yq = new YahooQuerier();
	private Logger logger = LoggerFactory.getLogger(MetaPreProcessor.class);
	
	public MetaPreProcessor() {
		createDb();
	}
	
	/**
	 * Creates database of meta data
	 */
	private void createDb() {
		try  {
			db = new Sql2o(DbAccessor.configureDataSource(DbAccessor.META_DB_PATH));
			Connection conn = db.open();
		
            String sql = "CREATE TABLE IF NOT EXISTS item (ticker TEXT, name TEXT,"
            		  +	"								   industry TEXT, code TEXT)" ;
            conn.createQuery(sql).executeUpdate();
        } catch(Sql2oException ex) {
        	logger.error("Failed to create schema at startup", ex);
        } catch(IOException ex) {
        	logger.error("Failed to configure data source", ex);
        }
	}
	
	/**
	 * Loads all data into database
	 */
	public void loadToDb() {
		try {
			CSVReader csvReader = new CSVReader(new FileReader(
					DbAccessor.META_CSV_PATH.toString()), 1, new CSVParser());
			
			String[] nextLine;
			while ((nextLine = csvReader.readNext()) != null) {
				if(yq.validDataExists(nextLine[NAME_INDEX], nextLine[CODE_INDEX])) {
					System.out.println(nextLine[TICKER_INDEX]);
					storeData(nextLine[TICKER_INDEX], nextLine[NAME_INDEX],
							  nextLine[INDUSTRY_INDEX], nextLine[CODE_INDEX]);
				}
			}
			csvReader.close();
		}
		catch(Exception ex) {
			logger.error("Error occured", ex);
		}
	}
	
	/**
	 * Stores a row of data into database
	 * @param ticker		ticker code
	 * @param name			stock name
	 * @param industry		industry name
	 * @param industryCode	industry code - used to identify files in Yahoo Finance
	 */
	private void storeData(String ticker, String name, String industry, String industryCode) {
		String sql = "INSERT INTO item (ticker, name, industry, code) " +
				"             VALUES (:ticker, :name, :industry, :code)";
		try {
			Connection conn = db.open();
			conn.createQuery(sql)
			.addParameter("ticker", ticker)
			.addParameter("name", name)
			.addParameter("industry", industry)
			.addParameter("code", industryCode)
			.executeUpdate();
		} catch(Sql2oException ex) {
			System.out.println(ex.getMessage());
			logger.error("Failed to create new entry", ex);
		}
	}
	
/*	*//**
	 * Given a list of the 'hit' rows, determine if a valid data exists
	 * @param name
	 * @param code
	 * @return	true if a valid data exists, false if not
	 * @throws IOException
	 *//*
	public String[] getValidData (String name, String code) throws IOException {
		ArrayList<String[]> data = yq.getMatches(name, code);
		if(data.size() == 0) return null;
		for(String[] row: data) {
			if(isValidData(row)) return row;
		}
		return null;
	}*/
}
