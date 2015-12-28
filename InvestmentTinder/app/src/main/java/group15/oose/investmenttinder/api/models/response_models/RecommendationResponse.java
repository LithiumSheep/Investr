package group15.oose.investmenttinder.api.models.response_models;

public class RecommendationResponse {

    private String ticker;
    private String name;
    private String industry;
    private String logo;

    public RecommendationResponse(String ticker, String name, String industry, String logo) {
        this.ticker = ticker;
        this.name = name;
        this.industry = industry;
        this.logo = logo;
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

    public String getLogo() { return logo; }
}
