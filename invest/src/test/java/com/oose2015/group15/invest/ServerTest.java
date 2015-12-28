package com.oose2015.group15.invest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oose2015.group15.invest.core.User;
import com.oose2015.group15.invest.core.User.UserException;
import com.oose2015.group15.invest.datatools.DbAccessor;
import com.oose2015.group15.invest.datatools.StockData;
import com.oose2015.group15.invest.datatools.YahooQuerier;
import com.oose2015.group15.invest.jsonobject.ClientProfileJson;
import com.oose2015.group15.invest.jsonobject.DeprecatedViewJson;
import com.oose2015.group15.invest.jsonobject.ExceptionResponse;
import com.oose2015.group15.invest.jsonobject.LoginRequest;
import com.oose2015.group15.invest.jsonobject.LoginResponse;
import com.oose2015.group15.invest.jsonobject.SignUpInfo;
import com.oose2015.group15.invest.jsonobject.DetailedViewJson;

import spark.Spark;
import spark.utils.IOUtils;

/**
 * Tests RESTful API communication
 * @author Kathleen
 */
public class ServerTest {
	public static int numUser = 1;

	/*
	 * Setup
	 */

	@Before
	public void setup() throws Exception {
		DbAccessor.clearUserDb();
		Bootstrap.main(null);
		Spark.awaitInitialization();
	}

	@After
	public void tearDown() {
		//stop server
		DbAccessor.clearUserDb();
		Spark.stop();
	}
	
	/**
	 * Tests sign up and login API communication
	 */
	@Test
	public void signupLoginTest() {
		// first user
		ClientProfileJson p1 = new ClientProfileJson(true, true, false, false, false,
				1, 2, 1, false, 30);
		SignUpInfo s1 = new SignUpInfo("kk", "password", p1);
		
		// second user
		ClientProfileJson p2 = new ClientProfileJson(true, true, false, false, false,
				1, 2, 1, true, 30);
		SignUpInfo s2 = new SignUpInfo("test", "testpassword", p2);
		
		SignUpInfo[] entries = new SignUpInfo[]{s1, s2};
		
		int userId = 1;
		for(SignUpInfo s: entries) {
			 Response rnew = request("POST", "/api/v1/new", s);
	         assertEquals(201, rnew.httpStatus);
	         LoginResponse lr = getLoginResponse(rnew);
	         assertEquals(lr.getID(), userId++);
		}	
		
		// try getting them back
		LoginRequest l1 = new LoginRequest("kk", "password");
		LoginRequest l2 = new LoginRequest("test", "testpassword");
		LoginRequest[] logins = new LoginRequest[]{l1, l2};
		
		userId = 1;
		for(LoginRequest l: logins) {
			Response rlogin = request("POST", "/api/v1/login", l);
			assertEquals(200, rlogin.httpStatus);
			LoginResponse lr = getLoginResponse(rlogin);
			assertEquals(lr.getID(), userId++);
		}
	}
	
	@Test
	public void getStockDataTest() {		
		String[] tickers = new String[]{"AAPL", "MSFT", "SYMC"};		
		YahooQuerier yq = new YahooQuerier();
		
		try {
			for(String s: tickers) {
				Response r = request("GET", "/api/v1/" + s, null);
				DetailedViewJson result = getDetailedViewJson(r);
				StockData expected = yq.getStockData(s);

				assertEquals(result.getName(), expected.getName());
				assertEquals(result.getIndustry(), expected.getIndustry());
				assertTrue(result.getPE() == expected.getPE());
				assertTrue(result.getROE() == expected.getROE());
				assertTrue(result.getDY() == expected.getDY());
				assertTrue(result.getDE() == expected.getDE());
				assertTrue(result.getPB() == expected.getPB());
				assertTrue(result.getNPM() == expected.getNPM());
				assertTrue(result.getPFCF() == expected.getPFCF());
				assertTrue(result.getLogo() != null);
				
				assertEquals(200, r.httpStatus);			
			}
		}
		catch(Exception ex) {
			System.out.println(ex);
		}
	}

	// Test for getting recommended securities
	@Test
	public void getRecsTest() throws UserException {
		// first user
		ClientProfileJson p1 = new ClientProfileJson(true, true, false, false, false,
				1, 2, 1, false, 30);
		SignUpInfo s1 = new SignUpInfo("kk", "password", p1);

		// second user
		ClientProfileJson p2 = new ClientProfileJson(true, true, false, false, false,
				1, 2, 1, true, 30);
		SignUpInfo s2 = new SignUpInfo("test", "testpassword", p2);
		
		SignUpInfo[] entries= new SignUpInfo[]{s2};
		
		for (SignUpInfo s : entries) {
			Response rNew = request("POST", "/api/v1/new", s);
			LoginResponse lR = getLoginResponse(rNew);
			Integer userId = lR.getID();
			
			// get expected values
			User user = new User(s);
			List<DeprecatedViewJson> expected = 
					AppService.convertTickerToDeprecatedViewJson(user.getRecList());
			
			Response rGet = request("GET", "/api/v1/" + userId + "/rec", null);
			assertEquals(rGet.httpStatus, 200);
			List<DeprecatedViewJson> result = getListDeprecatedViewJson(rGet);
			assertTrue(result.size() == expected.size());
			assertTrue(result.size() != 0);
			
			for (int i = 0; i < expected.size(); i++) {
				assertEquals(result.get(i).toString(), expected.get(i).toString());
			}
		}
	}
	
	@Test
	public void swipeRightTest() throws UserException, IOException {
		// first user
		ClientProfileJson p1 = new ClientProfileJson(true, true, false, false, false,
				1, 2, 1, false, 30);
		SignUpInfo s1 = new SignUpInfo("kk", "password", p1);
		
		// second user
		ClientProfileJson p2 = new ClientProfileJson(true, true, false, false, false,
				1, 2, 1, true, 30);
		SignUpInfo s2 = new SignUpInfo("test", "testpassword", p2);

		SignUpInfo[] entries= new SignUpInfo[]{s1, s2};

		for (SignUpInfo s : entries) {
			Response rNew = request("POST", "/api/v1/new", s);
			LoginResponse lR = getLoginResponse(rNew);
			Integer userId = lR.getID();
			
			// get expected values
			User user = new User(s);
			List<String> tickers = user.getRecList();
			
			// try adding all recommended stocks to the liked list
			for(String t : tickers) {
				HashMap<String, String> request = new HashMap<String, String>();
				request.put("ticker", t);

				Response rAdd = request("POST", "/api/v1/" + userId + "/swiperight", request);
				assertEquals(rAdd.httpStatus, 200);

				Sql2o userDb = DbAccessor.getUserDb();
				Connection conn = userDb.open();

				// confirm that stock is removed from recommendations table
				String sql = "SELECT ticker FROM rec WHERE id = :id AND ticker = :ticker";
				String match = conn.createQuery(sql)
								   .addParameter("id", userId)
								   .addParameter("ticker", t)
								   .executeAndFetchFirst(String.class);

				assertTrue(match == null);
				
				// confirm that stock is added to liked table
				sql = "SELECT ticker FROM liked WHERE id = :id AND ticker = :ticker";
				match = conn.createQuery(sql)
							.addParameter("id", userId)
							.addParameter("ticker", t)
							.executeAndFetchFirst(String.class);
				assertEquals(match, t);
			}
		}
	}

	@Test
	public void swipeLeftTest() throws UserException, IOException {
		// first user
		ClientProfileJson p1 = new ClientProfileJson(true, true, false, false, false,
				1, 2, 1, false, 30);
		SignUpInfo s1 = new SignUpInfo("kk", "password", p1);
		
		// second user
		ClientProfileJson p2 = new ClientProfileJson(true, true, false, false, false,
				1, 2, 1, true, 30);
		SignUpInfo s2 = new SignUpInfo("test", "testpassword", p2);

		SignUpInfo[] entries= new SignUpInfo[]{s1, s2};

		for (SignUpInfo s : entries) {
			Response rNew = request("POST", "/api/v1/new", s);
			LoginResponse lR = getLoginResponse(rNew);
			Integer userId = lR.getID();
			
			// get expected values
			User user = new User(s);
			List<String> tickers = user.getRecList();
			
			// try adding all recommended stocks to the liked list
			for(String t : tickers) {
				HashMap<String, String> request = new HashMap<String, String>();
				request.put("ticker", t);

				Response rDel = request("POST", "/api/v1/" + userId + "/swipeleft", request);
				assertEquals(rDel.httpStatus, 200);

				Sql2o userDb = DbAccessor.getUserDb();
				Connection conn = userDb.open();

				// confirm that stock is removed from recommendations table
				String sql = "SELECT ticker FROM rec WHERE id = :id AND ticker = :ticker";
				String match = conn.createQuery(sql)
								   .addParameter("id", userId)
								   .addParameter("ticker", t)
								   .executeAndFetchFirst(String.class);
			
				assertTrue(match == null);
				
				// confirm that stock is NOT added to liked table
				sql = "SELECT ticker FROM liked WHERE id = :id AND ticker = :ticker";
				match = conn.createQuery(sql)
							.addParameter("id", userId)
							.addParameter("ticker", t)
							.executeAndFetchFirst(String.class);
				assertEquals(match, null);
			}
		}
	}

	/**
	 * 
	 * Testing for getting liked securities
	 * @throws UserException 
	 * 
	 */
	@Test
	public void getLikedTest() throws UserException {
		
		// first profile
		ClientProfileJson p1 = new ClientProfileJson(true, true, false, false, false,
				1, 2, 1, false, 30);
		SignUpInfo s1 = new SignUpInfo("kk", "password", p1);
		//User u0 = new User(s1, liked);

		// second profile
		ClientProfileJson p2 = new ClientProfileJson(true, true, false, false, false,
				1, 2, 1, true, 30);
		SignUpInfo s2 = new SignUpInfo("test", "testpassword", p2);
				
		//Turn to array
		//SignUpInfo[] entries= new SignUpInfo[]{s1, s2};
		
		// Test with preset List of liked stocks using first profile
		Response rNew = request("POST", "/api/v1/new", s1);
		LoginResponse lR = getLoginResponse(rNew);
		Integer userId = lR.getID();
		
		// Like 3 stocks (i.e. swipe right 3 times)
		HashMap<String, String> request = new HashMap<String, String>();
		request.put("ticker", "test");
		Response rAdd = request("POST", "/api/v1/" + userId + "/swiperight", request);
		rAdd = request("POST", "/api/v1/" + userId + "/swiperight", request);
		rAdd = request("POST", "/api/v1/" + userId + "/swiperight", request);

		//Test if there are 3 stocks in the liked list
		Response rGet = request("GET", "/api/v1/" + userId + "/liked", null);
		assertEquals(rGet.httpStatus, 200);
		List <String> result = getListString(rGet);
		assertTrue(result.size() == 3);
		
	}
    //------------------------------------------------------------------------//
    // Generic Helper Methods and classes
    //------------------------------------------------------------------------//
	private Response request(String method, String path, Object content) {
		try {
			URL url = new URL("http", Bootstrap.IP_ADDRESS, Bootstrap.PORT, path);
			System.out.println(url);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod(method);
			http.setDoInput(true);
			if (content != null) {
				String contentAsJson = new Gson().toJson(content);
				http.setDoOutput(true);
				http.setRequestProperty("Content-Type", "application/json");
				OutputStreamWriter output = new OutputStreamWriter(http.getOutputStream());
				output.write(contentAsJson);
				output.flush();
				output.close();
			}

			String responseBody = IOUtils.toString(http.getInputStream());
			return new Response(http.getResponseCode(), responseBody);
		}	catch (IOException e) {
			e.printStackTrace();
			fail("Sending request failed: " + e.getMessage());
			return null;
		}
	}

	private static class Response {
		public String content;

		public int httpStatus;

		public Response(int httpStatus, String content) {
			this.content = content;
			this.httpStatus = httpStatus;
		}

		public <T> T getContentAsObject(Type type) {
			return new Gson().fromJson(content, type);
		}
	}
    
    private LoginResponse getLoginResponse(Response r) {
    	Type type = (new TypeToken<LoginResponse>() { }).getType();
    	return r.getContentAsObject(type);
    }
    
    private LoginResponse getExceptionResponse(Response r) {
    	Type type = (new TypeToken<ExceptionResponse>() { }).getType();
    	return r.getContentAsObject(type);
    }
    
    private DetailedViewJson getDetailedViewJson(Response r) {
    	Type type = (new TypeToken<DetailedViewJson>() { }).getType();
    	return r.getContentAsObject(type);
    }
    
    private List<DeprecatedViewJson> getListDeprecatedViewJson(Response r) {
    	Type type = (new TypeToken<List<DeprecatedViewJson>>() { }).getType();
    	return r.getContentAsObject(type);
    }
    
    private List<String> getListString(Response r) {
    	Type type = (new TypeToken<List<String>>() { }).getType();
    	return r.getContentAsObject(type);
    }
}
