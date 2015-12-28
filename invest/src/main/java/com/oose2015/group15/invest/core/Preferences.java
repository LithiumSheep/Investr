package com.oose2015.group15.invest.core;

import java.util.Map;

/**
 * Preferences represents a user's preferences. It will be constructed when a JSON model is passed in.
 */
public class Preferences {
	private int age;
	private double discIncome;
	private Map<String, Integer> curInv;
	private double taxBracket;
	private int risk;
	private Map<String, Boolean> invObj;
	private int timeHor;
	private int liquidity;
	private boolean retirement;
	private boolean dependants;
	
	public int getAge() {
		return age;
	}
	
	public double getDiscIncome() {
		return discIncome;
	}
	
	public Map<String, Integer> getCurInv() {
		return curInv;
	}
	
	public double getTaxBracket() {
		return taxBracket;
	}
	
	public int getRisk() {
		return risk;
	}
	
	public Map<String, Boolean> getInvObj() {
		return invObj;
	}
	
	public int getTimeHor() {
		return timeHor;
	}
	
	public int getLiquidity() {
		return liquidity;
	}
	
	public boolean isRetirement() {
		return retirement;
	}
	
	public boolean isDependants() {
		return dependants;
	}
}
