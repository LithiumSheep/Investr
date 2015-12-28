package com.oose2015.group15.invest.jsonobject;


public class ClientProfileJson {
	// private List<String> investmentObjectives;
	// objectives
    private boolean capital;
    private boolean growth;
    private boolean income;
    private boolean deferral;
    private boolean speculation;
    
	private int horizon;
    private int riskTol;
    private int liq;
    private boolean dep;
    private int taxBracket;
	
    public ClientProfileJson(boolean objCapital, boolean objGrowth,
			boolean objIncome, boolean objDeferral, boolean objSpeculation,
			int timeHorizon, int riskTolerance,
			int liquidityNeeds, boolean hasDependents, int taxBracket) {
		super();
		this.capital = objCapital;
		this.growth = objGrowth;
		this.income = objIncome;
		this.deferral = objDeferral;
		this.speculation = objSpeculation;
		this.horizon = timeHorizon;
		this.riskTol = riskTolerance;
		this.liq = liquidityNeeds;
		this.dep = hasDependents;
		this.taxBracket = taxBracket;
	}

/*	public boolean wantGrowth() {
		return investmentObjectives.contains("growth");
	}
	
	public boolean wantCapital() {
		return investmentObjectives.contains("capital preservation");
	}
	
	public boolean wantIncome() {
		return investmentObjectives.contains("income");
	}
	
	public boolean wantDeferral() {
		return investmentObjectives.contains("tax deferral");
	}
	
	public boolean wantSpeculation() {
		return investmentObjectives.contains("speculation");
	}*/

	public boolean wantCapital() {
		return capital;
	}

	public boolean wantGrowth() {
		return growth;
	}

	public boolean wantIncome() {
		return income;
	}

	public boolean wantDeferral() {
		return deferral;
	}

	public boolean wantSpeculation() {
		return speculation;
	}

	public int getTimeHorizon() {
		return horizon;
	}

	public int getRiskTolerance() {
		return riskTol;
	}

	public int getLiquidityNeeds() {
		return liq;
	}
	
	public boolean hasDependents() {
		return dep;
	}

	public int getTaxBracket() {
		return taxBracket;
	}	
}
