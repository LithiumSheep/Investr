package com.oose2015.group15.invest.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.oose2015.group15.invest.dataprocessor.ClientProfile;
import com.oose2015.group15.invest.dataprocessor.InvestmentDomain;
import com.oose2015.group15.invest.dataprocessor.ProfileProcessor;
import com.oose2015.group15.invest.jsonobject.SignUpInfo;

/**
 * User is a class to represent each user's account and contains basic info (username/password), user's
 * preferences and their recommended/liked list of securities
 *
 */
public class User {
	private UserInfo info;
	private ProfileProcessor processor;
	private List<String> likedList;
	private List<String> recList;
	private int cur = 0;
	
	/**
	 * Initializes user based on JSON object (NewUserInfo) 
	 * 
	 * Used for testing
	 * 
	 * @param SignUpInfo signUp
	 * @throws UserException 
	 */
	public User(SignUpInfo signUp) throws UserException {
		try {
			ClientProfile profile = new ClientProfile(signUp.getProfile());
			UserInfo info = new UserInfo(signUp.getUsername(), signUp.getPassword(), profile);
			
			this.info = info;
			processor = new ProfileProcessor(info.getProfile(), InvestmentDomain.getSecurities());		//todo
			likedList = new ArrayList<String>();
			recList = generateRecList();
		}
		catch(IOException ex) {
			throw new UserException("Failed to generate recommendations - tagDb not found.");
		}
	}	
	
	/**
	 * Initializes user based on JSON object (signUp) and list of liked stocks
	 * 
	 * Used for testing
	 * 
	 * @param SignUpInfo signUp
	 * @throws UserException 
	 */
	public User(SignUpInfo signUp, List<String> liked) throws UserException {
		try {
			ClientProfile profile = new ClientProfile(signUp.getProfile());
			UserInfo info = new UserInfo(signUp.getUsername(), signUp.getPassword(), profile);
			
			this.info = info;
			processor = new ProfileProcessor(info.getProfile(), InvestmentDomain.getSecurities());		//todo
			likedList = liked;
			recList = generateRecList();
		}
		catch(IOException ex) {
			throw new UserException("Failed to generate recommendations - tagDb not found.");
		}
	}
	
	/**
	 * Constructs User based on UserInfo and a list of liked stocks
	 * @param info
	 * @param likedList
	 * @throws UserException
	 */
	public User(UserInfo info, List<String> likedList) throws UserException {
		try {
			this.info = info;
			this.likedList = likedList;
			processor = new ProfileProcessor(info.getProfile(), InvestmentDomain.getSecurities());		//todo
			recList = generateRecList();
		}
		catch(IOException ex) {
			throw new UserException("Failed to generate recommendations - tagDb not found.");
		}		
	}
	
	/**
	 * Constructs User based on Userinfo
	 * @param info
	 * @throws UserException
	 */
	public User(UserInfo info) throws UserException {
		try {
			this.info = info;
			processor = new ProfileProcessor(info.getProfile(), InvestmentDomain.getSecurities());		//todo
			likedList = new ArrayList<String>();
			recList = generateRecList();
		}
		catch(IOException ex) {
			throw new UserException("Failed to generate recommendations - tagDb not found.");
		}
	}
	
	/**
	 * Test constructor for testing purposes - recommended list is passed in as a parameter
	 * @param rec
	 */
	public User(List<String> rec) {
		recList = new ArrayList<String>();
		for(String r: rec) {
			recList.add(r);
		}
		likedList = new ArrayList<String>();
	}
	
	/**
	 * Sets user info
	 * @param info
	 */
	public void setUserInfo(UserInfo info) {
		this.info = info;
	}
	
	/**
	 * Initialize recommended list for user
	 * @return
	 * @throws UserException 
	 * @throws IOException 
	 */
	public List<String> generateRecList() throws IOException {	
		return processor.getMatchingTickers();		
	}
	
	/**
	 * Moves current security object from recList to likedList
	 */
	public void swipeRight() {
		String s = recList.get(cur);
		recList.remove(s);
		likedList.add(s);
	}
	
	/**
	 * Deletes security object from recList
	 */
	public void swipeLeft() {
		String s = recList.get(cur);
		recList.remove(s);
	}
	
	/**
	 * Gets next entry in recommended list
	 * @return Security object
	 */
	public String getNextRec() {
		return recList.get(cur++);
	}

	/**
	 * Gets user info
	 * @return
	 */
	public UserInfo getInfo() {
		return info;
	}

	/**
	 * Gets liked list
	 * @return
	 */
	public List<String> getLikedList() {
		return this.likedList;
	}

	/**
	 * Gets current index
	 * @return
	 */
	public int getCur() {
		return cur;
	}
	
	/**
	 * Get recommendations list
	 * @return
	 */
	public List<String> getRecList() {
		return recList;
	}
	
	
	@Override
	public String toString() {
		return "User [info=" + info + ", processor=" + processor
				+ ", likedList=" + likedList + ", recList=" + recList
				+ ", cur=" + cur + "]";
	}

	public static class UserException extends Exception {
		public UserException(String message) {
			super(message);
		}
	}
}
