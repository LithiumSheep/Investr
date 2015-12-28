import static org.junit.Assert.*;

import org.junit.Test;

/**
 * A basic test class for the Tagger algorithm.
 * @author Jeremy
 *
 */
public class TaggerTest {
    private static String tag;
    private static boolean stablePriceTrend;
    
    @Test
    public void test() {
     // Test arrays that simulate possible JSON objects for a stock and its industry
        // symbol, name, PE, ROE, DY, DE, PB, NPM, PFCF
        Object[] symc = {"SYMC", "Symantec Corporation", 18.92, 0.1294, 0.0291, 35.43, 2.39, 0.078, 51.42};
        Object[] secSoftInd = {"SS&S", "Security Software & Services", 25.2, 0.115, 0.0292, 35.63, 11.35, 0.1260, 85.0};
        
        Object[] att = {"ATT", "AT&T, Inc.", 35.52, 0.0506, 0.0557, 103.37, 1.7, 0.0766, 34.99};
        Object[] telcomServInd = {"TELCOM", "Security Software & Services", 24.7, 0.145, 0.0526, 372.49, -2.39, 0.064, -19.3};
    
        Object[] aapl = {"AAPL", "Apple Inc", 13.77, 0.4115, 0.0175, 0.433, 5.41, 0.2152, 55.39};
        Object[] elecEqInd = {"ELEC_EQ", "Electronic Equipment", 17.1, 0.312, 0.018, 0.4294, 5.9, 0.163, 15.4};

        Tagger tagger = new Tagger();
        
        // Symantec should be a value stock
        StockData stock1 = new StockData(symc);
        StockData industry1 = new StockData(secSoftInd);
        stablePriceTrend = false;
        
        tag = tagger.tagStock(stock1, industry1, stablePriceTrend);
        
        assertTrue("Therefore, our analysis indicates that " + stock1.getName() 
                + " represents an " + tag + " opportunity. \n", tag.equals("VALUE"));
        
        
        // AT&T should be an income stock
        StockData stock2 = new StockData(att);
        StockData industry2 = new StockData(telcomServInd);
        stablePriceTrend = true;
        
        tag = tagger.tagStock(stock2, industry2, stablePriceTrend);
        
        assertTrue("Therefore, our analysis indicates that " + stock2.getName() 
                + " represents an " + tag + " opportunity. \n", tag.equals("INCOME"));
        
        
        // Apple should be a growth stock
        StockData stock3 = new StockData(aapl);
        StockData industry3 = new StockData(elecEqInd);
        stablePriceTrend = false;
        
        tag = tagger.tagStock(stock3, industry3, stablePriceTrend);
        
        assertTrue("Therefore, our analysis indicates that " + stock3.getName() 
                + " represents an " + tag + " opportunity. \n", tag.equals("GROWTH"));
    }

}
