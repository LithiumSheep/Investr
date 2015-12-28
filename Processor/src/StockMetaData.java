public class StockMetaData {
    public String ticker;
    public String name;
    public String industry;
    public String code;

    public StockMetaData(String ticker, String name, String industry, String code) {
        this.ticker = ticker;
        this.name = name;
        this.industry = industry;
        this.code = code;
    }

    public String getTicker() {
        return ticker;
    }

    public String getName() {
        return name;
    }

    public String getIndustry() {
        return industry;
    }

    public String getCode() {
        return code;
    }
}