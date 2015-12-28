public class Tagger {
    private String PB;
    private String PE;
    private String PFCF;
    private String DY;
    private String ROE;
    
    /**
     *  This is a dummy value for now. It would be calculated
     *   by comparing the trend in the stock price over a period.
     */
    private boolean stablePriceTrend;
    
    
    /** Empty for now. */
    public Tagger() {}

    
    /**
     * Determine if a stock should be classified as growth, value, or income.
     * @param stock     the stock data object
     * @param industry  the industry data object
     * @return  growth, value, or income as appropriate
     */
    public String tagStock(StockData stock, StockData industry, boolean priceTrend) {
        /*
         * Value:
         *  Low PB
         *  Low PE
         *  Low PFCF
         *  High DY
         *  Average to high ROE
         */
        
        this.stablePriceTrend = priceTrend;
        
        // Determine if the stock's valuation rations are below, at, or above industry values
        this.PE = compareToIndustry(stock.getPE(), industry.getPE());
        this.ROE = compareToIndustry(stock.getROE(), industry.getROE());
        this.DY = compareToIndustry(stock.getDY(), industry.getDY());
        this.PB = compareToIndustry(stock.getPB(), industry.getPB());
        this.PFCF = compareToIndustry(stock.getPFCF(), industry.getPFCF());
        
        // Determine the tag to give the stock.
        if (this.PE.equals("LOW") && this.PB.equals("LOW") && this.PFCF.equals("LOW")) {
            //System.out.println(stock.getName() + " has a low PE, a low PB, and a low PFCF");
            
            if (this.DY.equals("HIGH") && (this.ROE.equals("EQUAL") || this.ROE.equals("HIGH"))) {
                //System.out.println(stock.getName() + " has a high DY and an average or high ROE");
                return "VALUE";
            } else {
                double percentDifDY = getPercentDif(stock.getDY(), industry.getDY());
                double percentDifROE = getPercentDif(stock.getROE(), industry.getROE());
                
                if (percentDifDY >= -0.05 && percentDifROE >= -0.05) {
                    //System.out.println(stock.getName() + " has a roughly average DY and ROE");
                    return "VALUE";
                } else {
                    //System.out.println(stock.getName() + " has a low DY and ROE");
                    return "GROWTH";
                }
            } 
        } else if (this.stablePriceTrend && (this.DY.equals("HIGH") || stock.getDY() > 0.05)) {
            //System.out.println(stock.getName() + " has a consistant price history and a high DY");
            return "INCOME";
        } else {
            //System.out.println(stock.getName() + " does not have a consistant price history and or a high DY");
            return "GROWTH";
        } 
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
