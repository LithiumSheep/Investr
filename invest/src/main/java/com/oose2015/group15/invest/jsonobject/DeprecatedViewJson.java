package com.oose2015.group15.invest.jsonobject;

/**
 * Encapsulates details for the swipe card view
 * @author Kathleen
 *
 */
public class DeprecatedViewJson {
	private String ticker;
	private String name;
	private String industry;
	private String logo;
	
	public DeprecatedViewJson(String ticker, String name, String industry) {
		super();
		this.ticker = ticker;
		this.name = name;
		this.industry = industry;
	}
	
	public DeprecatedViewJson(String ticker, String name, String industry, String logo) {
		super();
		this.ticker = ticker;
		this.name = name;
		this.industry = industry;
		this.logo = logo;
	}

	@Override
	public String toString() {
		return "DeprecatedViewJson [ticker=" + ticker + ", name=" + name
				+ ", industry=" + industry + "]";
	}
}
