package com.oose2015.group15.invest.dataprocessor;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Security {
    private String securityType;
    private List<String> objectives;
    private String riskTolerance;
    private boolean[] timeHorizons;
    private String liquidity;
    private Map<String, Boolean> timeMap = new HashMap<String, Boolean>();
    private final String[] VALID_TIMES = {"Very Short Term", "Short Term", 
            "Medium Term", "Long Term", "Very Long Term"};
    
    
    public Security(String sT, List<String> iO, String rT, boolean[] tH, String lN) {
        assert(tH.length == 5);
        
        this.objectives = iO;
        this.securityType = sT;
        this.riskTolerance = rT;
        this.timeHorizons = tH;
        this.liquidity = lN;
        
        assert(this.VALID_TIMES.length == this.timeHorizons.length);
        
        for (int i = 0; i < this.timeHorizons.length; i++) {
            this.timeMap.put(this.VALID_TIMES[i], this.timeHorizons[i]);
        }
    }

    
    public boolean tLPreferencesInclude(String s) {
        // Determine if the string that was inputed was for a time horizon.
        //  If so, return the corresponding boolean for that time period.
        if (this.timeMap.containsKey(s)) {
            return this.timeMap.get(s);
        }
        
        // Determine if the string is equivalent to the user's liquidity need
        //return this.liquidity.equalsIgnoreCase(s);
        return true;
    }
    
    public boolean objectivesInclude(String s) {
        return this.objectives.contains(s);
    }
    
    
    public List<String> getInvestmentObjectives() {
        return this.objectives;
    }
    
    
    /** Determine which time horizons this security can have. */
    public boolean canBeVeryShortTerm() {
        return this.timeHorizons[0];
    }
    
    public boolean canBeShortTerm() {
        return this.timeHorizons[1];
    }
    
    public boolean canBeMediumTerm() {
        return this.timeHorizons[2];
    }
    
    public boolean canBeLongTerm() {
        return this.timeHorizons[3];
    }
    
    public boolean canBeVeryLongTerm() {
        return this.timeHorizons[4];
    }
    
    public boolean isA(String type) {
        return this.securityType.equalsIgnoreCase(type);
    }
    
    public String getSecurityType() {
        return this.securityType;
    }

    public String getRiskTolerance() {
        return riskTolerance;
    }

    public boolean[] getTimeHorizon() {
        return timeHorizons;
    }

    public String getLiquidity() {
        return liquidity;
    }
}
