/**
 * The stock data class, which will hold a deserialized JSON object.
 * @author Jeremy
 *
 */
public class StockData {
    private String symbol;
    private String name;
    private double PE;
    private double ROE;
    private double DY;
    private double DE;
    private double PB;
    private double NPM;
    private double PFCF;
    
    
    /**
     * Note: Normally this class would be instantiated with a call like 
     *  StockData data = new Gson().fromJson(json, StockData.class), rather
     *  than with this constructor
     * @param json  an array of doubles that is a placeholder for 
     *              the values in a JSON string
     */
    public StockData(Object[] json) {
        this.symbol = (String)json[0];
        this.name = (String)json[1];
        this.PE = (double)json[2];
        this.ROE = (double)json[3];
        this.DY = (double)json[4];
        this.DE = (double)json[5];
        this.PB = (double)json[6];
        this.NPM = (double)json[7];
        this.PFCF = (double)json[8];
    }
    
    public String getSymbol() {
        return this.symbol;
    }
    
    public String getName() {
        return this.name;
    }
    
    public double getPE() {
        return this.PE;
    }
    
    public double getROE() {
        return this.ROE;
    }
    
    public double getDY() {
        return this.DY;
    }
    
    public double getDE() {
        return this.DE;
    }
    
    public double getPB() {
        return this.PB;
    }
    
    public double getNPM() {
        return this.NPM;
    }
    
    public double getPFCF() {
        return this.PFCF;
    }
}
