package com.oose2015.group15.invest.taggertools;

/**
 * Represents an item in the tag database. Contains ticker and integers that
 * determine if they are value/income/growth stocks
 * @author Kathleen
 *
 */
public class TagItem {
	private String ticker;
	private int value;
	private int growth;
	private int income;
	
	public TagItem(String ticker, int value, int growth, int income) {
		super();
		this.ticker = ticker;
		this.value = value;
		this.growth = growth;
		this.income = income;
	}
}
