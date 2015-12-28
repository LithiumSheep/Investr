package com.oose2015.group15.invest.datatools;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.util.List;
import java.util.ArrayList;

import com.oose2015.group15.invest.jsonobject.ClearBitResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ClearBitQuerier {
	private final String BASE_URL = "https://autocomplete.clearbit.com/v1/companies/suggest?query=%s";
	private URLReader urlReader;
	
	public ClearBitQuerier() {
		urlReader = new URLReader();
	}
	
	public String getLogo(String name) throws ClearBitException {
		try {
			String jsonResponse = urlReader.read(String.format(BASE_URL, name));
			
			// deserialize list of ClearBitResponse objects
			Type listType = new TypeToken<ArrayList<ClearBitResponse>>() {}.getType();
			List<ClearBitResponse> response = new Gson().fromJson(jsonResponse, listType);
			
			return response.get(0).getLogo();
		}
		catch(Exception ex) {
			throw new ClearBitException(ex);
		}
	}
	
	public static class ClearBitException extends Exception {
		public ClearBitException() {
			super("Failed to fetch logo.");
		}
		
		public ClearBitException(Throwable cause) {
			super(cause);
		}
	}
}
