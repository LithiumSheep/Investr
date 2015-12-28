package com.oose2015.group15.invest.jsonobject;

/**
 * Simple confirmation token class
 * used for Json request/response
 */
public class SecAuthToken {

	private String authId;
	private String securityId;
	
	public SecAuthToken() {}
	
	public String getAuthId() {
		return this.authId;
	}
	
	public String getSecId() {
		return this.securityId;
	}
}
