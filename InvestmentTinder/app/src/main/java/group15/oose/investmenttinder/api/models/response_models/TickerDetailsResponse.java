package group15.oose.investmenttinder.api.models.response_models;

import com.google.gson.annotations.SerializedName;

public class TickerDetailsResponse {

    private String ticker;
    private String name;
    private String industry;
    private String logo;
    @SerializedName("PE")
    private double PE;
    @SerializedName("ROE")
    private double ROE;
    @SerializedName("DY")
    private double DY;
    @SerializedName("DE")
    private double DE;
    @SerializedName("PB")
    private double PB;
    @SerializedName("NPM")
    private double NPM;
    @SerializedName("PFCF")
    private double PFCF;

    public TickerDetailsResponse(String ticker, String name, String industry, double PE, double ROE, double DY, double DE, double PB, double NPM, double PFCF, String logo) {
        this.ticker = ticker;
        this.name = name;
        this.industry = industry;
        this.PE = PE;
        this.ROE = ROE;
        this.DY = DY;
        this.DE = DE;
        this.PB = PB;
        this.NPM = NPM;
        this.PFCF = PFCF;
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

    public double getPE() {
        return PE;
    }

    public double getROE() {
        return ROE;
    }

    public double getDY() {
        return DY;
    }

    public double getDE() {
        return DE;
    }

    public double getPB() {
        return PB;
    }

    public double getNPM() {
        return NPM;
    }

    public double getPFCF() {
        return PFCF;
    }

    public String getLogo() {return logo; }
}
