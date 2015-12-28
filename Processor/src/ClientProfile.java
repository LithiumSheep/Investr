import java.util.ArrayList;

public class ClientProfile {
    private String id;
    private ArrayList<String> investmentObjectives = new ArrayList<String>();
    private ArrayList<String> tols = new ArrayList<String>();
    private String timeHorizon;
    private String riskTolerance;
    private String liquidityNeeds;
    private boolean hasDependents;
    private int taxBracket;
    
    
    public ClientProfile(String clientID, ArrayList<String> iO, String tH, String rT, 
            String lN, boolean hD, int tB) {
        this.id = clientID;
        this.timeHorizon = tH;
        this.riskTolerance = rT;
        this.liquidityNeeds = lN;
        this.taxBracket = tB;
        
        // For now assume only valid objectives could be inputed.
        for (String s: iO) {
            investmentObjectives.add(s);
        }
        
        // Adjust the risk tolerances so that low-mod = low & low-mod, and 
        //  mod-high = mod-high & aggressive
        this.adjustRiskTols();
    }

    
    private void adjustRiskTols() {
        this.tols.add(this.riskTolerance);
        
        if (this.riskTolerance.equalsIgnoreCase("Low Risk")) {
            this.tols.add("Very Low Risk");
        }
        
        if (this.riskTolerance.equalsIgnoreCase("Low Moderate Risk")) {
            this.tols.add("Low Risk");
        }
        
        if (this.riskTolerance.equalsIgnoreCase("Moderate High Risk")) {
            this.tols.add("Aggressive");
        }
    }

    
    public ArrayList<String> getTols() {
        return tols;
    }
    
    public ArrayList<String> getInvestmentObjectives() {
        return investmentObjectives;
    }


    public String getTimeHorizon() {
        return timeHorizon;
    }


    public String getRiskTolerance() {
        return riskTolerance;
    }


    public String getLiquidityNeeds() {
        return liquidityNeeds;
    }


    public boolean hasDependents() {
        return hasDependents;
    }


    public int getTaxBracket() {
        return taxBracket;
    }
}
