package group15.oose.investmenttinder.api.models.request_models;

import com.google.gson.annotations.SerializedName;

public class ClientProfile {

    private boolean capital;
    private boolean growth;
    private boolean income;
    private boolean deferral;
    private boolean speculation;
    private int horizon;
    @SerializedName("riskTol")
    private int riskTol;
    private int liq;
    private boolean dep;
    private int taxBracket;

    public ClientProfile(boolean capital, boolean growth, boolean income, boolean deferral, boolean speculation, int horizon, int riskTol, int liq, boolean dep, int taxBracket) {
        this.capital = capital;
        this.growth = growth;
        this.income = income;
        this.deferral = deferral;
        this.speculation = speculation;
        this.horizon = horizon;
        this.riskTol = riskTol;
        this.liq = liq;
        this.dep = dep;
        this.taxBracket = taxBracket;
    }

    public boolean isCapital() {
        return capital;
    }

    public boolean isGrowth() {
        return growth;
    }

    public boolean isIncome() {
        return income;
    }

    public boolean isDeferral() {
        return deferral;
    }

    public boolean isSpeculation() {
        return speculation;
    }

    public int getHorizon() {
        return horizon;
    }

    public int getRiskTol() {
        return riskTol;
    }

    public int getLiq() {
        return liq;
    }

    public boolean isDep() {
        return dep;
    }

    public int getTaxBracket() {
        return taxBracket;
    }
}
