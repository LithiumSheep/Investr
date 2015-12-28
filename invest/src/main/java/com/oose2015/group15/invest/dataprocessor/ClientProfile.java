package com.oose2015.group15.invest.dataprocessor;

import java.util.ArrayList;

import com.oose2015.group15.invest.jsonobject.ClientProfileJson;

public class ClientProfile {
    // private String id;
    private ArrayList<String> investmentObjectives = new ArrayList<String>();
    private ArrayList<String> tols = new ArrayList<String>();
    private String timeHorizon;
    private String riskTolerance;
    private String liquidityNeeds;
    private boolean hasDependents;
    private int taxBracket;
    
    /**
     * Default constructor
     * @param iO
     * @param tH
     * @param rT
     * @param lN
     * @param hD
     * @param tB
     */
    public ClientProfile(ArrayList<String> iO, String tH, String rT, 
            String lN, boolean hD, int tB) {
        // this.id = clientID;
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

    /**
     * Converts a ClientProfileJson object to ClientProfile
     * @param profile
     */
    public ClientProfile(ClientProfileJson profile) {
    	if(profile.wantGrowth() && profile.wantIncome())
    		investmentObjectives.add("Growth and Income");
    	else if(profile.wantGrowth()) investmentObjectives.add("Growth");
    	else if(profile.wantIncome()) investmentObjectives.add("Income");
    	
    	if(profile.wantSpeculation()) investmentObjectives.add("Speculation");
    	if(profile.wantDeferral()) investmentObjectives.add("Tax Deferral");
    	if(profile.wantCapital()) investmentObjectives.add("Capital Preservation");
    	
    	this.timeHorizon = convertTimeHorizon(profile.getTimeHorizon());
    	this.riskTolerance = convertRiskTol(profile.getRiskTolerance());
    	this.liquidityNeeds = convertLiqNeeds(profile.getLiquidityNeeds());
    	this.taxBracket = profile.getTaxBracket();
    	this.hasDependents = profile.hasDependents();
    	
    	this.adjustRiskTols();
    }
    
    /**
     * Converts time horizon to its String representation
     * @param horizon
     * @return
     */
    private String convertTimeHorizon(int horizon) {
    	return InvestmentDomain.times[horizon];
    }
    
    /**
     * Converts risk tolerance to its String representation
     * @param riskTol
     * @return
     */
    private String convertRiskTol(int riskTol) {
    	return InvestmentDomain.risks[riskTol];
    }
    
    /**
     * Converts liquidity needs to its String representation
     * @param liq
     * @return
     */
    private String convertLiqNeeds(int liq) {
    	return InvestmentDomain.liquidities[liq];
    }
    
    /**
     * Adjusts risk tolerance
     */
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

	@Override
	public String toString() {
		return "ClientProfile [investmentObjectives=" + investmentObjectives
				+ ", tols=" + tols + ", timeHorizon=" + timeHorizon
				+ ", riskTolerance=" + riskTolerance + ", liquidityNeeds="
				+ liquidityNeeds + ", hasDependents=" + hasDependents
				+ ", taxBracket=" + taxBracket + "]";
	}
}
