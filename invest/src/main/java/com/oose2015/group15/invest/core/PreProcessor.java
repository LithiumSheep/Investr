package com.oose2015.group15.invest.core;

import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

/**
 * A preprocessor that processes all securities in the free database from Quandl API and labels each
 * with tags. It also fetches their sector names and stores these data in an external database.
 */
public class PreProcessor {
	/**A pre-processed database that lists a security's ticker code, name and sector*/
	private Sql2o overviewDb;
	
	/**A pre-processed database that lists tags that match each security*/
	private Sql2o tagDb;
	
	/**A file that lists all securities and their sector*/
	private String overviewFilename = "SP500.csv"; 
	
	/**List of tags - TODO, incomplete, only for testing*/
	List<String> tagList = Arrays.asList("growth", "ETF", "UIT");
	
	private Logger logger = LoggerFactory.getLogger(PreProcessor.class);
	
	public PreProcessor(DataSource overviewSource, DataSource tagSource) {
		createOverviewDb(overviewSource);
		createTagDb(tagSource);
	}
	
	private void createOverviewDb(DataSource source) {
		overviewDb = new Sql2o(source);
		Connection conn = overviewDb.open();
		try  {
            String sql = "CREATE TABLE IF NOT EXISTS item (ticker TEXT, name TEXT, sector TEXT)" ;
            conn.createQuery(sql).executeUpdate();
        } catch(Sql2oException ex) {
            logger.error("Failed to create schema at startup", ex);
        }
	}
	
	private void createTagDb(DataSource source) {
		tagDb = new Sql2o(source);
		Connection conn = overviewDb.open();
		try  {
            String sql = "CREATE TABLE IF NOT EXISTS tags (ticker TEXT, tag TEXT)" ;
            conn.createQuery(sql).executeUpdate();
        } catch(Sql2oException ex) {
            logger.error("Failed to create schema at startup", ex);
        }
	}
	
	/**
	 * Given CSV file(s) that contains ticker codes and sectors for each company, 
	 * load these information to a database
	 * @throws Exception 
	 */
	public void loadToOverviewDb() throws Exception {
		// read CSV
		String curDir = System.getProperty("user.dir");
		CSVReader reader = new CSVReader(new FileReader(curDir + "\\data\\" + overviewFilename));
		String[] nextLine;
		reader.readNext();
		
		// store each row to database
		while ((nextLine = reader.readNext()) != null) {
			storeOverviewData(nextLine[0], nextLine[2], nextLine[3]);
		}
		
		// close reader
		reader.close();
	}
	
	/**
	 * Loads ticker codes and their corresponding tags to tagDb
	 * 
	 * TODO - still a dummy method
	 */
	public void loadToTagDb() {
		for(String tag: tagList) {
			storeTagData("DUMMY", tag);
		}
	}
	
	/**
	 * Computes tags to label a certain security
	 * @param s	security to be labeled
	 * @return list of tags
	 */
	//TODO
	private List<String> computeTags(String s) {
		return null;
	}

	/**
	 * Writes data to the database
	 * @param name		name of security
	 * @param sector	name of sector
	 * @param tags		list of tags
	 */
	public void storeOverviewData(String ticker, String name, String sector) {
		StockItem stock = new StockItem(ticker, name, sector);
		String sql = "INSERT INTO item (ticker, name, sector) " +
                     "             VALUES (:ticker, :name, :sector)";
				
		try {
			Connection conn = overviewDb.open();
			conn.createQuery(sql)
			.bind(stock)
			.executeUpdate();
		} catch(Sql2oException ex) {
			logger.error("Failed to create new entry", ex);
		}
	}
	
	public void storeTagData(String ticker, String tag) {
		TagItem tagItem = new TagItem(ticker, tag);
		String sql = "INSERT INTO tags (ticker, tag) " +
                "             VALUES (:ticker, :tag)";
		
		try {
			Connection conn = tagDb.open();
			conn.createQuery(sql)
			.bind(tagItem)
			.executeUpdate();
		} catch(Sql2oException ex) {
			logger.error("Failed to create new entry", ex);
		}
	}
	
	public String getOverviewFilename() {
		return overviewFilename;
	}
	
	public Sql2o getDb() {
		return overviewDb;
	}
	
	/**
	 * Helper class to wrap stock overview row from the CSV file
	 */
	public static class StockItem {
		String ticker;
		String name;
		String sector;		
		
		StockItem(String ticker, String name, String sector) {
			this.ticker = ticker;
			this.name = name;
			this.sector = sector;
		}

		public String getTicker() {
			return ticker;
		}

		public String getName() {
			return name;
		}

		public String getSector() {
			return sector;
		}
		
		public String toString() {
			return "ticker: " + ticker + "\nname: " + name + "\nsector: " + sector;
		}
	}
	
	/**
	 * Helper class to represent a tag row in tagDb
	 */
	public static class TagItem {
		String ticker;
		String tag;
		
		TagItem(String ticker, String tag) {
			this.ticker = ticker;
			this.tag = tag;
		}

		public String getTicker() {
			return ticker;
		}

		public String getTag() {
			return tag;
		}
	}
	
}
