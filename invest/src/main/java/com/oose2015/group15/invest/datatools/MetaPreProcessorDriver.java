package com.oose2015.group15.invest.datatools;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.sql2o.Connection;
import org.sql2o.Sql2o;


/**
 * Runs MetaPreProcessor to load meta data
 * @author Kathleen
 *
 */
public class MetaPreProcessorDriver {
	public static void main(String[] args) {
		try {
			clearDB();
			MetaPreProcessor p = new MetaPreProcessor();
			p.loadToDb();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Helper method to clear database
	 * @throws IOException 
	 */
    private static void clearDB() throws IOException {
    	Sql2o db = new Sql2o(DbAccessor.configureDataSource(DbAccessor.META_DB_PATH));
    	Connection conn = db.open();
    	String sql = "DROP TABLE IF EXISTS item" ;
    	conn.createQuery(sql).executeUpdate();
    }
}
