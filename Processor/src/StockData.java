
/**
 * Class that encapsulates stock data
 * @author Kathleen
 *
 */
public class StockData extends Data{
    private StockMetaData meta;

    public StockData(StockMetaData meta, double pE, double rOE, double dY, double dE, double pB,
            double nPM, double pFCF) {
        super(pE, rOE, dY, dE, pB, nPM, pFCF);
        this.meta = meta;
    }
    
    public StockMetaData getMeta() {
        return meta;
    }
    
    public String getTicker() {
        return meta.getTicker();
    }
    
    public String getName() {
        return meta.getName();
    }
    
    public String getIndustry() {
        return meta.getIndustry();
    }
}