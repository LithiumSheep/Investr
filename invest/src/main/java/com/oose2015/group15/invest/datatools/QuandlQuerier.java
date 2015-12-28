package com.oose2015.group15.invest.datatools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.oose2015.group15.invest.core.PriceData;

/**
 * A class that interacts with the API - aka sends URL requests and processes the json responses
 */
public class QuandlQuerier {
	private final String API_KEY = "xBrbZGzyVkidGvGScF42";
	private final String baseURL = "https://quandl.com/api/v3/datasets/%s.%s" ;
	
	/**
	 * Gets price data for one security only
	 * @param tickerCode
	 * @return		PriceData object
	 * @throws Exception 
	 */
	public PriceData getPriceData(String tickerCode) throws Exception {
		String response = (sendRequest(buildURL(tickerCode, "json")));
		return parseData(response);		
	}
	
	/**
	 * Parses price data from response string - only gets the first six columns
	 * @param response
	 * @return	PriceData object
	 * @throws ParseException
	 */
	private PriceData parseData(String response) throws ParseException {
		// convert response to JsonObject
		Gson gson = new Gson();
		JsonObject dataset = (JsonObject) (gson.fromJson(response, JsonObject.class)).get("dataset");
		
		// get the price data as a list
		List<?> data = gson.fromJson(dataset.get("data"), ArrayList.class);
		
		// parse through each row and append to the correct list in PriceData
		DateFormat df = new SimpleDateFormat("y-M-d");
		PriceData pd = new PriceData();
		for(Object o: data) {
			List<?> item = (List<?>) o;
			pd.addDate(df.parse((String)item.get(0)));
			pd.addOpen((Double)item.get(1));
			pd.addHigh((Double)item.get(2));
			pd.addLow((Double)item.get(3));
			pd.addClose((Double)item.get(4));
			pd.addVolume((Double)item.get(5));			
		}
		return pd;
	}
	
	/**
	 * Gets data for multiple securities
	 * @param names	securities
	 * @return		List of Security objects
	 * @throws Exception 
	 */
	public List<PriceData> getMultiplePriceData(List<String> tickerCodes) throws Exception {
		List<PriceData> pd = new ArrayList<PriceData>();
		for(String t: tickerCodes) {
			String response = (sendRequest(buildURL(t, "json")));
			pd.add(parseData(response));
		}
		return pd;
	}
	
	/**
	 * Builds a URL to send to API
	 * 
	 * @param name		name of security
	 * @return			String URL
	 */
	private String buildURL(String tickerCode, String returnType) {
		return String.format(baseURL + "?auth_token=" + API_KEY, tickerCode, returnType);
	}
	
	/**
	 * Reads JSON response from API - code taken from StackOverflow
	 * @param url	API request url
	 * @return		response String
	 * @throws Exception
	 */
	private String sendRequest(String urlString) throws Exception {
		BufferedReader reader = null;
	    try {
	        URL url = new URL(urlString);
	        reader = new BufferedReader(new InputStreamReader(url.openStream()));
	        StringBuffer buffer = new StringBuffer();
	        int read;
	        char[] chars = new char[1024];
	        while ((read = reader.read(chars)) != -1)
	            buffer.append(chars, 0, read); 

	        return buffer.toString();
	    } finally {
	        if (reader != null)
	            reader.close();
	    }
	}	
}
