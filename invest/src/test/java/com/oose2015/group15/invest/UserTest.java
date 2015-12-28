package com.oose2015.group15.invest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.oose2015.group15.invest.core.Security;
import com.oose2015.group15.invest.core.User;
import com.oose2015.group15.invest.core.User.UserException;
import com.oose2015.group15.invest.core.UserInfo;
import com.oose2015.group15.invest.dataprocessor.ClientProfile;

public class UserTest {
	User user;
	User user1;
	User user2;
	int initRecSize = 3;
	
	@Before
	public void setup() throws UserException {
		//test ticker codes
		user = new User(Arrays.asList("YELP", "MSFT", "AAPL"));
		
		/*
		 * Create another User object using 2nd constructor
		 * 
		 * Create test Client Profile
		 */
		ArrayList<String> testIO = new ArrayList<String>();
		testIO.add("Growth");
		testIO.add("Speculation");
		
		/*String testTH = "Long Term";
		String testRT = "Moderate High Risk";
		String testLN = "Moderate Liquidity";*/
		String testTH = "Very Long Term";
        String testRT = "Moderate High Risk";
        String testLN = "Low Liquidity";
		boolean testHD = false;
		int testTB = 2;
		
		ClientProfile testProfile = new ClientProfile(testIO, testTH, testRT, testLN, testHD, testTB);
		
		/*
		 * Create UserInfo object
		 */
		UserInfo testInfo = new UserInfo("ghou", "pswd", testProfile);
		
		/*
		 * Create user object
		 */
		user1 = new User(testInfo);
		
		/*
		 * Create another user object using 3rd constructor: User(UserInfo, List<String) 
		 */
		user2 = new User(testInfo, Arrays.asList("AAPL", "MCD", "SGA", "SNS"));
	}
	
	
	/*
	 * Test for 2nd constructor
 	 * Testing User(UserInfo info) constructor
	 */
	@Test
	public void userConstructorTest() throws UserException {		
		/*
		 * Test initialized fields
		 */
		assertTrue(user1.getLikedList().size() == 0);
		assertTrue(user1.getRecList().size() != 0);
	}
	
	@Test
	public void setUserInfoTest() {
		user.setUserInfo(user1.getInfo());
		assertEquals(user.getInfo(), user1.getInfo());
	}
	
	
	@Test
	public void swipeLeftTest() {
		List<String> recList = user.getRecList();
		List<String> likedList = user.getLikedList();
		
		assertTrue(recList.size() == initRecSize);
		assertTrue(likedList.size() == 0);
		
		user.swipeLeft();
		
		recList = user.getRecList();
		likedList = user.getLikedList();
		
		assertTrue(recList.size() == initRecSize - 1);
		assertTrue(likedList.size() == 0);
		
		assertTrue(user.getNextRec().equals("MSFT"));
	}
	
	@Test
	public void swipeRightTest() {
		List<String> recList = user.getRecList();
		List<String> likedList = user.getLikedList();
		
		assertTrue(recList.size() == initRecSize);
		assertTrue(likedList.size() == 0);
		
		user.swipeRight();
		
		recList = user.getRecList();
		likedList = user.getLikedList();
		
		assertTrue(recList.size() == initRecSize - 1);
		assertTrue(likedList.size() == 1);
		
		assertTrue(user.getNextRec().equals("MSFT"));
	}
	

}
