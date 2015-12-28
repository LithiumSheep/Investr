package com.oose2015.group15.invest.core;

/**
 * Security class contains related market data and profile collected from API
 */
public class Security {
	private String name;
	private String symbol;
	private String sector;
	private double beta;
	private PriceData priceData;
	private Graph priceTrend = null;
	private double curPrice;
	
	public Security(String name, String symbol, String sector, double beta,
			PriceData priceData, Graph priceTrend, double curPrice) {
		this.name = name;
		this.symbol = symbol;
		this.sector = sector;
		this.beta = beta;
		this.priceData = priceData;
		this.priceTrend = priceTrend;
		this.curPrice = curPrice;
	}
	
	public Security(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public String getSector() {
		return sector;
	}
	
	public double getBeta() {
		return beta;
	}
	
	public PriceData getPriceData() {
		return priceData;
	}
	
	public Graph getPriceTrend() {
		return priceTrend;
	}
	
	public double getCurPrice() {
		return curPrice;
	}
}
