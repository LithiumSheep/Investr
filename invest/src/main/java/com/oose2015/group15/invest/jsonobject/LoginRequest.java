package com.oose2015.group15.invest.jsonobject;

/**
 * Json object to wrap login info
 */
public class LoginRequest {
	
	private String username;
	private String password;
	
	public LoginRequest() {}
	
	/**
	 * test constructor
	 * @return
	 */
	public LoginRequest(String user, String pass) {
		this.username = user;
		this.password = pass;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
}
