package com.oose2015.group15.invest.datatools;


/**
 * Class that encapsulates stock data
 * @author Kathleen
 *
 */
public class StockData extends Data {
	private StockMetaData meta;

	public StockData(StockMetaData meta, double pE, double rOE, double dY, double dE, double pB,
			double nPM, double pFCF) {
		super(pE, rOE, dY, dE, pB, nPM, pFCF);
		this.meta = meta;
	}
	
	public StockMetaData getMeta() {
		return meta;
	}
	
	public String getTicker() {
		return meta.getTicker();
	}
	
	public String getName() {
		return meta.getName();
	}
	
	public String getIndustry() {
		return meta.getIndustry();
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		StockData other = (StockData) obj;
		if (meta == null) {
			if (other.meta != null)
				return false;
		} else if (!meta.equals(other.meta))
			return false;
		return true;
	}
}
