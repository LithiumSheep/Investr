import java.util.ArrayList;
import java.sql.*;

public class Tagger {
    private String PB;
    private String PE;
    private String PFCF;
    private String DY;
    private String ROE;
    
    private ArrayList<Data> data = new ArrayList<Data>();
    private final int STOCK_INDEX = 2;
    private final int INDUSTRY_INDEX = 1;
    
    private Connection connection = null;
    private PreparedStatement statement = null;
    
    
    /**
     *  This is a dummy value for now. It would be calculated
     *   by comparing the trend in the stock price over a period.
     */
    private boolean stablePriceTrend;
    
    
    /** Empty for now. */
    public Tagger(ArrayList<Data> dataFromQuery) {
        // Copy the stock and industry data over from the input arraylist.
        for (Data d: dataFromQuery) {
            this.data.add(d);
        }
        
        this.createDB();
    }
    
    
    private void createDB() {
        // Create the stock tag database, or connect to an existing one.
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:tag.db");
            connection.setAutoCommit(false);
            
            // Create a table for the database.
            String sql = "CREATE TABLE IF NOT EXISTS TAGS " +
                       "(SYMBOL TEXT PRIMARY KEY NOT NULL," +
                       " VALUE INT NOT NULL, "
                       + "GROWTH INT NOT NULL, "
                       + "INCOME INT NOT NULL)";
            
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        
        //System.out.println("Opened tag database successfully");
    }

    
    /**
     * Determine if a stock should be classified as growth, value, or income.
     * @param stock     the stock data object
     * @param industry  the industry data object
     * @return  growth, value, or income as appropriate
     * @throws SQLException 
     */
    //public String tagStock(boolean priceTrend) {
    public void tagStock(boolean priceTrend) throws SQLException {
        /*
         * Value:
         *  Low PB
         *  Low PE
         *  Low PFCF
         *  High DY
         *  Average to high ROE
         */
        
        StockData stock = (StockData) this.data.get(STOCK_INDEX);
        IndustryData industry = (IndustryData) this.data.get(INDUSTRY_INDEX);
        
        this.stablePriceTrend = priceTrend;
        String tag = "";
        String ticker = stock.getTicker();
        
        
        tag = "INSERT OR IGNORE INTO TAGS VALUES (?, ?, ?, ?)";
        this.statement = connection.prepareStatement(tag);
        
        // Determine if the stock's valuation rations are below, at, or above industry values
        this.PE = compareToIndustry(stock.getPE(), industry.getPE());
        this.ROE = compareToIndustry(stock.getROE(), industry.getROE());
        this.DY = compareToIndustry(stock.getDY(), industry.getDY());
        this.PB = compareToIndustry(stock.getPB(), industry.getPB());
        this.PFCF = compareToIndustry(stock.getPFCF(), industry.getPFCF());
        
        // Determine the tag to give the stock.
        if (this.PE.equals("LOW") && this.PB.equals("LOW") && this.PFCF.equals("LOW")) {
            if (this.DY.equals("HIGH") && (this.ROE.equals("EQUAL") || this.ROE.equals("HIGH"))) {
                this.statement.setString(1, ticker);
                this.statement.setInt(2, 1);
                this.statement.setInt(3, 0);
                this.statement.setInt(4, 0);
                
                
                //System.out.println("Successfully inserted into the database - VALUE"); 
            } else {
                double percentDifDY = getPercentDif(stock.getDY(), industry.getDY());
                double percentDifROE = getPercentDif(stock.getROE(), industry.getROE());
                
                if (percentDifDY >= -0.05 && percentDifROE >= -0.05) {
                    this.statement.setString(1, ticker);
                    this.statement.setInt(2, 1);
                    this.statement.setInt(3, 0);
                    this.statement.setInt(4, 0);
                    
                    
                    //System.out.println("Successfully inserted into the database - VALUE");
                } else {
                    this.statement.setString(1, ticker);
                    this.statement.setInt(2, 0);
                    this.statement.setInt(3, 1);
                    this.statement.setInt(4, 0);
                    
                    
                    //System.out.println("Successfully inserted into the database - GROWTH");
                }
            } 
        } else if (this.stablePriceTrend && (this.DY.equals("HIGH") || stock.getDY() > 0.05)) {
            this.statement.setString(1, ticker);
            this.statement.setInt(2, 0);
            this.statement.setInt(3, 0);
            this.statement.setInt(4, 1);
            
            
            //System.out.println("Successfully inserted into the database - INCOME");
        } else {
            this.statement.setString(1, ticker);
            this.statement.setInt(2, 0);
            this.statement.setInt(3, 1);
            this.statement.setInt(4, 0);
            
            
            //System.out.println("Successfully inserted into the database - GROWTH");
        } 
        
        // Add the tag to the table in the database.
        this.statement.executeUpdate();
        this.connection.commit();
        this.statement.close();
        this.connection.close();
    }
    
    
    /**
     * Determine the percent difference between two doubles.
     *  This method is used to determine if two DY's or ROE's are within 5%
     *  of the industry values.
     * @param a the first double
     * @param b the second double
     * @return  the percent difference between a and b
     */
    private double getPercentDif(double a, double b) {
        return (a - b) / b;
    }
    
    
    /**
     * Determine if a stock's data value is below, at, or above the 
     *  industry's value
     * @param s the stock data value
     * @param i the industry data value
     * @return  low, equal, or high depending on the difference
     */
    private String compareToIndustry(double s, double i) {
        if (s < i) return "LOW";
        else if (s == i) return "EQUAL";
        else return "HIGH";
    }
}
