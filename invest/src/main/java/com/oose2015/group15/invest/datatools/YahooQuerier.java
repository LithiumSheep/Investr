package com.oose2015.group15.invest.datatools;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import com.opencsv.CSVParser;
import com.opencsv.CSVReader;

/**
 * Fetches data from overview database and finds specific stock, industry and sector data through
 * csv files in Yahoo Finance
 * @author Kathleen
 *
 */
public class YahooQuerier {
	private static final String BASE_URL = "http://biz.yahoo.com/p/csv/%dconameu.csv";
	Sql2o stockDb;
	private Logger logger = LoggerFactory.getLogger(YahooQuerier.class);
	private URLReader urlReader = new URLReader();
	
	/**
	 * Default constructor
	 */
	public YahooQuerier() {
		try {
			stockDb = DbAccessor.getMetaDb();
		}
		catch(IOException ex) {
			logger.error("Failed to connect to db", ex);
		}
	}
	
	/**
	 * Gets only the stock data, given a ticker code
	 * @param ticker
	 * @return StockData
	 * @throws ToolsException
	 */
	public StockData getStockData(String ticker) throws ToolsException {
		try {
			return (StockData) getCompleteStockData(ticker).get(2);
		} catch(ToolsException ex) {
			throw new ToolsException(ex.getMessage());
		}
	}
	
	/**
	 * Get stock data along with its sector and industry data, given a ticker code
	 * @param ticker
	 * @return	arraylist of sector data, industry data and stock data (in that order)
	 * @throws ToolsException
	 */
	public ArrayList<Data> getCompleteStockData(String ticker) throws ToolsException {
		Connection conn = stockDb.open();
		String sql = "SELECT * FROM item WHERE ticker = :ticker ";
		StockMetaData meta = conn.createQuery(sql)
				.addParameter("ticker", ticker)
				.executeAndFetchFirst(StockMetaData.class);		
		
		if(meta == null) throw new ToolsException("No such ticker found.");
		
		ArrayList<Data> data = null;
		
		try {
			data = getFromCSV(ticker, meta);
		}
		catch(ToolsException ex) {
			logger.error("Error occured", ex);
			throw new ToolsException(ex);
		}
		
		return data;
	}
	
	/**
	 * Processes and reads from the Yahoo Finance CSV file
	 * @param ticker	ticker code of searched stock
	 * @param meta		meta data of stock
	 * @return			arraylist of sector, industry and stock data (in that order)
	 * @throws ToolsException
	 */
	private ArrayList<Data> getFromCSV(String ticker, StockMetaData meta) throws ToolsException  {
		// read from online csv file, skipping the header
		try {
			String csv = urlReader.read(String.format(BASE_URL, Integer.parseInt(meta.getCode())));		
			CSVReader csvReader = new CSVReader(new StringReader(csv), 1, new CSVParser());

			// initialize
			IndustryData sector = null;
			IndustryData industry = null;
			StockData stock = null;

			// read from CSV file
			String[] nextLine;
			int row = 1;
			while ((nextLine = csvReader.readNext()) != null) {
				// first row is sector data
				if(row == 1) sector = getIndustryData(nextLine);

				// second row is industry data
				if(row == 2) industry = getIndustryData(nextLine);

				// find stock data
				if(nextLine[0].equals(meta.getName()) && isValidData(nextLine)) 
					stock = getStockData(nextLine, meta);
				row++;
			}

			csvReader.close();

			if(stock == null) throw new ToolsException("Stock data not found.");

			// bundle data into a list
			ArrayList<Data> data = bundleData(sector, industry, stock);
			return data;
		}
		catch(Exception ex) {
			throw new ToolsException(ex);
		}
	}
	
	/**
	 * Given a stock name and the industry code, return the entire row of data that matches it
	 * @param name
	 * @param code
	 * @return row of String or null (if not found)
	 * @throws IOException
	 */
	public boolean validDataExists(String name, String code) throws IOException  {		
		// read from online csv file, skipping the header
		String csv = urlReader.read(String.format(BASE_URL, Integer.parseInt(code)));
	
		CSVReader csvReader = new CSVReader(new StringReader(csv), 1, new CSVParser());
		String[] nextLine;
		while ((nextLine = csvReader.readNext()) != null) {
			// if found valid data, return true
			if(nextLine[0].equals(name) && isValidData(nextLine)) {
				csvReader.close();
				return true;
			}
		}
		csvReader.close();
		return false;
	}	
	
	/**
	 * A data is invalid if any of its data fields is N/A
	 * @return True if data is complete
	 */
	private boolean isValidData(String[] line) {
		for(String s: line) {
			if(s.equals("NA")) return false;
		}
		return true;		
	}
		
	/**
	 * Parse an array of Strings to an ArrayList of Double objects
	 * @param line	array of Strings
	 * @return		ArrayList of Double objects
	 */
	private ArrayList<Double> parseAsDoubleList(String[] line) {
		ArrayList<Double> result = new ArrayList<Double>();
		for(String s: line) {
			result.add(Double.parseDouble(s));
		}
		return result;
	}
	
	/**
	 * Bundle Data objects into an ArrayList
	 * @param d1	first Data object
	 * @param d2	second Data object
	 * @param d3	third Data object
	 * @return		ArrayList of Data
	 */
	private ArrayList<Data> bundleData(Data d1, Data d2, Data d3) {
		ArrayList<Data> data = new ArrayList<Data>();
		data.add(d1);
		data.add(d2);
		data.add(d3);
		return data;
	}
	
	/**
	 * Parses industry data from a row in a CSV file
	 * @param line		array of Strings
	 * @return
	 */
	private IndustryData getIndustryData(String[] line) {
		ArrayList<Double> data = parseAsDoubleList(Arrays.copyOfRange(line, 3, 10));
		return new IndustryData(line[0], data.get(0), data.get(1), data.get(2), data.get(3), 
								data.get(4), data.get(5), data.get(6));
	}
	
	/**
	 * Parses stock data from a row in a CSV file
	 * @param line
	 * @param meta
	 * @return
	 */
	private StockData getStockData(String[] line, StockMetaData meta) {
		ArrayList<Double> data = parseAsDoubleList(Arrays.copyOfRange(line, 3, 10));
		return new StockData(meta, data.get(0), data.get(1), data.get(2), data.get(3), 
								data.get(4), data.get(5), data.get(6));
	}
}

/*double pe = Double.parseDouble(nextLine[3]);
double roe = Double.parseDouble(nextLine[4]);
double dy = Double.parseDouble(nextLine[5]);
double de = Double.parseDouble(nextLine[6]);
double pb = Double.parseDouble(nextLine[7]);
double npm = Double.parseDouble(nextLine[8]);
double pfcf = Double.parseDouble(nextLine[9]);*/
