import java.util.ArrayList;
import java.sql.*;

/**
 * Take a list of securities from the user, and cross-match
 *  the stocks' tags with the stocks in the tag database.
 * @author Jeremy
 *
 */
public class TagsCrossMatcher {
    private static ArrayList<Stock> stocks = new ArrayList<Stock>();
    private static ArrayList<String> tickers = new ArrayList<String>();
    
    public static void main(String[] args) {
        
    }
    
    
    /**
     * Query the tag database.
     *  Adapted from http://www.tutorialspoint.com/sqlite/sqlite_java.htm
     */
    public static ArrayList<String> queryTagDB() {
        //connectToDB();
        
        Connection c = null;
        Statement stmt = null;
        
        try {
          Class.forName("org.sqlite.JDBC");
          c = DriverManager.getConnection("jdbc:sqlite:tag.db");
          c.setAutoCommit(false);
          System.out.println("Opened database successfully");

          stmt = c.createStatement();
          ResultSet rs = stmt.executeQuery("SELECT * FROM TAG;");
          
          while (rs.next()) {
             String symbol = rs.getString("symbol");
             int isValue  = rs.getInt("value");
             int isGrowth  = rs.getInt("growth");
             int isIncome  = rs.getInt("income");
             
             for (Stock s : stocks) {
                 if (s.getTag().equalsIgnoreCase("VALUE") && isValue == 1) {
                     tickers.add(symbol);
                 } else if (s.getTag().equalsIgnoreCase("GROWTH") && isGrowth == 1) {
                     tickers.add(symbol);
                 } else if (s.getTag().equalsIgnoreCase("Income") && isIncome == 1) {
                     tickers.add(symbol);
                 }
             }
             
             /*System.out.println("Ticker Symbol = " + symbol);
             System.out.println("Is Value: = " + isValue);
             System.out.println("Is Growth: = " + isGrowth);
             System.out.println("Is Income: = " + isIncome);
             System.out.println();*/
          }
          
          rs.close();
          stmt.close();
          c.close();
        } catch (Exception e) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
        
        System.out.println("Operation done successfully");

        return tickers;
    }
    
    
    /**
     * Connect to the tag database.
     */
    /*private static void connectToDB() {
        Connection c = null;
        
        try {
          Class.forName("org.sqlite.JDBC");
          c = DriverManager.getConnection("jdbc:sqlite:tag.db");
        } catch (Exception e) {
          System.err.println(e.getClass().getName() + ": " + e.getMessage());
          System.exit(0);
        }
    }*/
    
    
    /**
     * Save all of the stocks that were recommended to the user.
     * @param recommended   the list of all recommended securities
     */
    public static void setStocks(ArrayList<Security> recommended) {
        for (Security s : recommended) {
            if (s instanceof Stock) {
                stocks.add((Stock) s);
            }
        }
    }
    
    
    public static void printStocks() {
        for (Security s : stocks) {
            System.out.println(s.getSecurityType());
        }
    }
}
