package com.oose2015.group15.invest.datatools;

/**
 * Abstract data class that contains numerical stock data
 * @author Kathleen
 *
 */
public class Data {
	private double PE;			//Price/EPS ratio
    private double ROE;			//return on equity
    private double DY;			// Dividend/Price ratio - Dividend Yield
    private double DE;
    private double PB;			// price/book ratio
    private double NPM;			//net profit margin
    private double PFCF;
    
    
	public Data(double pE, double rOE, double dY, double dE, double pB,
				double nPM, double pFCF) {
		super();
		PE = pE;
		ROE = rOE;
		DY = dY;
		DE = dE;
		PB = pB;
		NPM = nPM;
		PFCF = pFCF;
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Data other = (Data) obj;
		if (Double.doubleToLongBits(DE) != Double.doubleToLongBits(other.DE))
			return false;
		if (Double.doubleToLongBits(DY) != Double.doubleToLongBits(other.DY))
			return false;
		if (Double.doubleToLongBits(NPM) != Double.doubleToLongBits(other.NPM))
			return false;
		if (Double.doubleToLongBits(PB) != Double.doubleToLongBits(other.PB))
			return false;
		if (Double.doubleToLongBits(PE) != Double.doubleToLongBits(other.PE))
			return false;
		if (Double.doubleToLongBits(PFCF) != Double
				.doubleToLongBits(other.PFCF))
			return false;
		if (Double.doubleToLongBits(ROE) != Double.doubleToLongBits(other.ROE))
			return false;
		return true;
	}
}
