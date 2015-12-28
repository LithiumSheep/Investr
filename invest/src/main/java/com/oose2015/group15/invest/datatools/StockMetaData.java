package com.oose2015.group15.invest.datatools;

public class StockMetaData {
	private String ticker;
	private String name;
	private String industry;
	private String code;

	public StockMetaData(String ticker, String name, String industry, String code) {
		this.ticker = ticker;
		this.name = name;
		this.industry = industry;
		this.code = code;
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

	public String getCode() {
		return code;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StockMetaData other = (StockMetaData) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (industry == null) {
			if (other.industry != null)
				return false;
		} else if (!industry.equals(other.industry))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (ticker == null) {
			if (other.ticker != null)
				return false;
		} else if (!ticker.equals(other.ticker))
			return false;
		return true;
	}
}