package com.oose2015.group15.invest;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import com.oose2015.group15.invest.AppService.AppServiceException;
import com.oose2015.group15.invest.core.User;
import com.oose2015.group15.invest.core.User.UserException;
import com.oose2015.group15.invest.datatools.DbAccessor;
import com.oose2015.group15.invest.jsonobject.ClientProfileJson;
import com.oose2015.group15.invest.jsonobject.DeprecatedViewJson;
import com.oose2015.group15.invest.jsonobject.LoginRequest;
import com.oose2015.group15.invest.jsonobject.LoginResponse;
import com.oose2015.group15.invest.jsonobject.SignUpInfo;

public class ServiceTest {
	public static int numUser = 1;

	@Before
	public void setup() throws Exception {
		DbAccessor.clearUserDb();
	}

	@After
	public void tearDown() {
		DbAccessor.clearUserDb();
	}
	
	@Test
	public void userTest() throws Exception{
		AppService model = new AppService();
		ClientProfileJson p1 = new ClientProfileJson(true, true, false, false, false,
				1, 2, 1, false, 30);
		SignUpInfo s1 = new SignUpInfo("kk", "password", p1);
		
		ClientProfileJson p2 = new ClientProfileJson(true, true, false, false, false,
				1, 2, 1, true, 30);
		SignUpInfo s2 = new SignUpInfo("test", "testpassword", p2);
		
		SignUpInfo[] entries = new SignUpInfo[]{s1, s2};
		
		for(SignUpInfo s:entries) {
			LoginResponse result = model.createNewUser(s);
			assertEquals(result.getID(), numUser);
			numUser++;
		}
		
		// try signing up with an existing username
		// should throw correct exception
		try {
			LoginResponse result = model.createNewUser(s1);
		} catch(AppServiceException ex) {
			assertEquals(ex.getMessage(), "Username is already taken");
		}
		
		// try login	
		LoginRequest lq = new LoginRequest("kk", "password");
		LoginResponse lr = model.login(lq);
		assertEquals(lr.getID(), 1);
		
		lq = new LoginRequest("test", "testpassword");
		lr = model.login(lq);
		assertEquals(lr.getID(), 2);
		
		// try wrong login
		try {
			lq = new LoginRequest("wrong", "user doesn't exist");
			lr = model.login(lq);
			assertTrue(false);
		} catch(AppServiceException ex) {
			assertEquals(ex.getMessage(), "Failed to login: User does not exist.");
		}
	}
	
	@Test
	public void getLikedList() {
		try {
			AppService model = new AppService();
			ClientProfileJson p1 = new ClientProfileJson(true, true, false, false, false,
					1, 2, 1, false, 30);
			SignUpInfo s = new SignUpInfo("likeTest", "password", p1);

			model.createNewUser(s);
		
			int id = model.getUserID("likeTest");
			List<String> liked = model.getLikedList(id);
			assertTrue(liked.size() == 0);
			
			model.addToLikedList("AAPL", id);
			
			liked = model.getLikedList(id);
			assertTrue(liked.size() == 1);
			assertEquals(liked.get(0), "AAPL");
		}
		catch(AppService.AppServiceException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	@Test
	public void getRecTest() {
		try {
			AppService model = new AppService();
			ClientProfileJson p1 = new ClientProfileJson(true, true, false, false, false,
					1, 2, 0, false, 30);
			SignUpInfo s1 = new SignUpInfo("kk", "password", p1);

			model.createNewUser(s1);
			
			int id = model.getUserID("kk");
			
			List<DeprecatedViewJson> rec = model.getRec(id);
			assertTrue(rec.size() != 0);
		}
		catch(AppService.AppServiceException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	@Test
	public void deleteRecTest() {
		try {
			AppService model = new AppService();
			ClientProfileJson p1 = new ClientProfileJson(true, true, false, false, false,
					1, 2, 1, false, 30);
			SignUpInfo s1 = new SignUpInfo("kk", "password", p1);

			model.createNewUser(s1);
			
			int id = model.getUserID("kk");
			
			User user = new User(s1);
			List<String> recs = user.getRecList();
			
			for(String s: recs) {
				model.deleteFromRec(s, id);
				
				// make sure that the ticker is deleted
				Sql2o userDb = DbAccessor.getUserDb();
				Connection conn = userDb.open();
				String sql = "SELECT ticker FROM rec WHERE id = :id AND ticker = :ticker";
				String match = conn.createQuery(sql)
								   .addParameter("ticker", s)
								   .addParameter("id", id)
								   .executeAndFetchFirst(String.class);
				assertTrue(match == null);
			}
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
