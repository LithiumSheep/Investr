package com.oose2015.group15.invest.jsonobject;

import java.util.List;
import java.util.Random;

/**
 * For now, LoginResponse contains the userID (primary key in user database)
 * 
 * -- Extended? -- 
 * This class is supposed to be the authorization id that keeps track
 * of the client's session.
 * 
 * A new LoginResponse object is generated every time a client logs in
 * and should be deleted everytime the client logs off.
 * authID is <username><some integer between 0 and 1000 inclusive>
 * ex: gideonhou134
 */
public class LoginResponse {

	private int userID;
	private String username;
	private ClientProfileJson profile;
	private List<String> liked;
	private List<DeprecatedViewJson> recs;
	
	//public LoginResponse(int userID) {
	//	this.userID = userID;
	//}
	
	public LoginResponse(int userID, String username, ClientProfileJson profile,
			List<String> liked, List<DeprecatedViewJson> recs) {
		this.userID = userID;
		this.username = username;
		this.profile = profile;
		this.liked = liked;
		this.recs = recs;
	}
	
	public LoginResponse(int userID, SignUpInfo info, List<String> liked,
			List<DeprecatedViewJson> recs) {
		this.userID = userID;
		this.username = info.getUsername();
		this.profile = info.getProfile();
		this.liked = liked;
		this.recs = recs;
	}

	public int getID() {
		return userID;
	}
	
/*	public LoginResponse(String user) {
		Random rand = new Random();
		StringBuilder str = new StringBuilder(user);
		
		Integer numId = rand.nextInt(1000);
		
		str.append(numId.toString());
		
		this.authId = str.toString();
	}
	
	public String getAuthId() {
		return this.authId;
	}*/
}
