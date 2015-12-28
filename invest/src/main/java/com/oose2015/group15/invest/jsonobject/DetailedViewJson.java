package com.oose2015.group15.invest.jsonobject;

/**
 * This class wraps the information needed for a stock's detailed view
 * @author Kathleen
 *
 */
public class DetailedViewJson {
	private String ticker;
	private String name;
	private String industry;
	private double PE;			//Price/EPS ratio
    private double ROE;			//return on equity
    private double DY;			// Dividend/Price ratio - Dividend Yield
    private double DE;
    private double PB;			// price/book ratio
    private double NPM;			//net profit margin
    private double PFCF;
    private String logo;
    
    /**
     * Default constructor
     * @param ticker
     * @param name
     * @param industry
     * @param pE
     * @param rOE
     * @param dY
     * @param dE
     * @param pB
     * @param nPM
     * @param pFCF
     */
	public DetailedViewJson(String ticker, String name, String industry,
			double pE, double rOE, double dY, double dE, double pB, double nPM,
			double pFCF) {
		super();
		this.ticker = ticker;
		this.name = name;
		this.industry = industry;
		PE = pE;
		ROE = rOE;
		DY = dY;
		DE = dE;
		PB = pB;
		NPM = nPM;
		PFCF = pFCF;
	}
	
    /**
     * Default constructor
     * @param ticker
     * @param name
     * @param industry
     * @param pE
     * @param rOE
     * @param dY
     * @param dE
     * @param pB
     * @param nPM
     * @param pFCF
     * @param logo
     */
	public DetailedViewJson(String ticker, String name, String industry,
			double pE, double rOE, double dY, double dE, double pB, double nPM,
			double pFCF, String logo) {
		super();
		this.ticker = ticker;
		this.name = name;
		this.industry = industry;
		PE = pE;
		ROE = rOE;
		DY = dY;
		DE = dE;
		PB = pB;
		NPM = nPM;
		PFCF = pFCF;
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

	@Override
	public String toString() {
		return "StockDataJson [ticker=" + ticker + ", name=" + name
				+ ", industry=" + industry + ", PE=" + PE + ", ROE=" + ROE
				+ ", DY=" + DY + ", DE=" + DE + ", PB=" + PB + ", NPM=" + NPM
				+ ", PFCF=" + PFCF + "]";
	}

	public String getLogo() {
		return logo;
	}   
}
