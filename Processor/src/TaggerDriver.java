import java.sql.SQLException;
import java.util.ArrayList;

public class TaggerDriver {
    public static void main(String[] args) throws SQLException {
        StockMetaData appleMeta = new StockMetaData("AAPL", "Apple", "Electronic Equipment", "314");
        StockData appleStock = new StockData(appleMeta, 13.08, 46.25, 1.84, 54.02, 5.63, 21.60, 71.62);
        IndustryData electronicsIndustry = new IndustryData("Electronic Equipment", 13.50, 34.90, 1.73, 52.10, 5.29, 17.30, 11.50);
        IndustryData electronicsSector = new IndustryData("SECTOR", 1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 7.7);
        ArrayList<Data> data = new ArrayList<Data>();
        boolean stablePrice = false;
        
        data.add(electronicsSector);
        data.add(electronicsIndustry);
        data.add(appleStock);
        
        Tagger testTagger1 = new Tagger(data);
        testTagger1.tagStock(stablePrice);
        
    }
}
