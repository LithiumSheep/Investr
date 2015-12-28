package com.oose2015.group15.invest.datatools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.sql.DataSource;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sqlite.SQLiteDataSource;

public class DbAccessor {
	static final String META_DB_NAME = "stock_overview_yahoo.db";
	public static final Path META_DB_PATH = Paths.get(".", "data", META_DB_NAME);
	public static final Path META_CSV_PATH = Paths.get(".", "data", "stock_overview_yahoo.csv");
	public static final Path TAG_DB_PATH = Paths.get(".", "data", "tag.db");
	public static final Path USER_DB_PATH = Paths.get(".", "data", "user.db");
	
	private static Sql2o tagDb;
	private static Sql2o userDb;
	private static Sql2o metaDb;
	
	/**
	 * Gets the tag database
	 * @return
	 * @throws IOException
	 */
	public static Sql2o getTagDb() throws IOException {
		if(tagDb == null) tagDb = new Sql2o(configureDataSource(TAG_DB_PATH));
		return tagDb;
	}
	
	/**
	 * Gets the user database
	 * @return
	 * @throws IOException
	 */
	public static Sql2o getUserDb() throws IOException{
		if(userDb == null) userDb = new Sql2o(configureDataSource(USER_DB_PATH));
		return userDb;
	}
	
	/**
	 * Gets the meta database
	 * @return
	 * @throws IOException
	 */
	public static Sql2o getMetaDb() throws IOException {
		if(metaDb == null) metaDb = new Sql2o(configureDataSource(META_DB_PATH));
		return metaDb;
	}
	
	/**
	 * Clears user database
	 */
	public static void clearUserDb() {
		try {
    		Sql2o db = new Sql2o(DbAccessor.configureDataSource(DbAccessor.USER_DB_PATH));
    		Connection conn = db.open();
    				String sql = "DROP TABLE IF EXISTS item;";
    				conn.createQuery(sql).executeUpdate();
    				
    				sql = "DROP TABLE IF EXISTS liked";
    				conn.createQuery(sql).executeUpdate();
    				
    				sql = "DROP TABLE IF EXISTS profile";
    				conn.createQuery(sql).executeUpdate();
    				
    				sql = "DROP TABLE IF EXISTS rec";
    				conn.createQuery(sql).executeUpdate();
    	}
    	catch (IOException ex) {
    		System.exit(1);
        }
	}
	
	/**
	 * Check if the database file exists in the current directory. If it does
	 * create a DataSource instance for the file and return it.
	 * @return javax.sql.DataSource corresponding to the todo database
	 * @throws IOException 
	 */
	public static DataSource configureDataSource(Path path) throws IOException {
	    if ( !(Files.exists(path) )) {
	        try {
	        	Files.createFile(path);
	        }
	        catch(java.io.IOException ex) {
	        	System.out.println(ex.getMessage());
	        }
	    }
	
	    SQLiteDataSource dataSource = new SQLiteDataSource();
	    dataSource.setUrl(String.format("jdbc:sqlite:%s", path.toAbsolutePath().normalize()));
	    return dataSource;
	}
}
