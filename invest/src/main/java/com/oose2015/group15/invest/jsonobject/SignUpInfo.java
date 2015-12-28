package com.oose2015.group15.invest.jsonobject;

public class SignUpInfo {

	String username;
	String password;
	//String firstName;
	//String lastName;
	//String birthday;
	//String emailAddress;
	
	ClientProfileJson profile;

	/**
	 * Constructor used for testing
	 * @param user
	 * @param pass
	 * @param first
	 * @param last
	 * @param birth
	 * @param email
	 */
	public SignUpInfo(String user, String pass, String first, String last, String birth, String email) {
		this.username = user;
		this.password = pass;
		//this.firstName = first;
		//this.lastName = last;
		//this.birthday = birth;
		//this.emailAddress = email;
	}
	
	/**
	 * Constructs basic SignUpInfo - as of Iteration 5
	 * @param username
	 * @param password
	 * @param profile
	 */
	public SignUpInfo(String username, String password,
			ClientProfileJson profile) {
		super();
		this.username = username;
		this.password = password;
		this.profile = profile;
	}

	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	/*public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}*/
	
	public ClientProfileJson getProfile() {
		return profile;
	}
	
	public boolean wantGrowth() {
		return profile.wantGrowth();
	}
	
	public boolean wantCapital() {
		return profile.wantCapital();
	}
	
	public boolean wantIncome() {
		return profile.wantIncome();
	}
	
	public boolean wantDeferral() {
		return profile.wantDeferral();
	}
	
	public boolean wantSpeculation() {
		return profile.wantSpeculation();
	}
	
	public int getTimeHorizon() {
		return profile.getTimeHorizon();
	}

	public int getRiskTolerance() {
		return profile.getRiskTolerance();
	}

	public int getLiquidityNeeds() {
		return profile.getLiquidityNeeds();
	}
	
	public boolean hasDependents() {
		return profile.hasDependents();
	}

	public int getTaxBracket() {
		return profile.getTaxBracket();
	}	
}
