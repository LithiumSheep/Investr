package com.oose2015.group15.invest.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PriceData {
	List<Date> date;
	List<Double> open;
	List<Double> high;
	List<Double> low;
	List<Double> close;
	List<Double> volume;
	
	
	public PriceData() {
		this.date = new ArrayList<Date>();
		this.open = new ArrayList<Double>();
		this.high = new ArrayList<Double>();
		this.low = new ArrayList<Double>();
		this.close = new ArrayList<Double>();
		this.volume = new ArrayList<Double>();
	}

	public void addDate(Date d) {
		date.add(d);
	}
	
	public void addOpen(Double d) {
		open.add(d);
	}
	
	public void addHigh(Double d) {
		high.add(d);
	}
	
	public void addLow(Double d) {
		low.add(d);
	}
	
	public void addClose(Double d) {
		close.add(d);
	}
	
	public void addVolume(Double d) {
		volume.add(d);
	}

	public List<Date> getDate() {
		return date;
	}

	public List<Double> getOpen() {
		return open;
	}

	public List<Double> getHigh() {
		return high;
	}

	public List<Double> getLow() {
		return low;
	}

	public List<Double> getClose() {
		return close;
	}

	public List<Double> getVolume() {
		return volume;
	}		
	
	
}