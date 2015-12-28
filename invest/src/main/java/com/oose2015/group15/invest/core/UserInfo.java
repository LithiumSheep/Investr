package com.oose2015.group15.invest.core;

import com.oose2015.group15.invest.dataprocessor.ClientProfile;

/**
 * User info - username, password and preferences
 */
public class UserInfo {
	String username;
	String password;
	ClientProfile profile;
	
	public UserInfo(String username, String password, ClientProfile profile) {
		super();
		this.username = username;
		this.password = password;
		this.profile = profile;
	}

	/**
	 * Changes password
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public ClientProfile getProfile() {
		return profile;
	}

	/**
	 * Changes username
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Set profile
	 * @param profile	new profile
	 */
	public void setProfile(ClientProfile profile) {
		this.profile = profile;
	}

	@Override
	public String toString() {
		return "UserInfo [username=" + username + ", password=" + password
				+ ", profile=" + profile + "]";
	}	
}
