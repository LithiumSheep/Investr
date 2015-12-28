package com.oose2015.group15.invest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import com.oose2015.group15.invest.core.Security;
import com.oose2015.group15.invest.core.User;
import com.oose2015.group15.invest.core.User.UserException;
import com.oose2015.group15.invest.core.UserInfo;
import com.oose2015.group15.invest.dataprocessor.ClientProfile;
import com.oose2015.group15.invest.datatools.ClearBitQuerier;
import com.oose2015.group15.invest.datatools.ClearBitQuerier.ClearBitException;
import com.oose2015.group15.invest.datatools.DbAccessor;
import com.oose2015.group15.invest.datatools.StockData;
import com.oose2015.group15.invest.datatools.ToolsException;
import com.oose2015.group15.invest.datatools.YahooQuerier;
import com.oose2015.group15.invest.jsonobject.ClientProfileJson;
import com.oose2015.group15.invest.jsonobject.DeprecatedViewJson;
import com.oose2015.group15.invest.jsonobject.LoginRequest;
import com.oose2015.group15.invest.jsonobject.LoginResponse;
import com.oose2015.group15.invest.jsonobject.SignUpInfo;
import com.oose2015.group15.invest.jsonobject.DetailedViewJson;

/**
 * Class that controls app logic
 */
public class AppService {
	
	private Sql2o userDb;
	
	/**
	 * Default constructor, creates a user database in the data folder
	 * @throws IOException 
	 */
	public AppService() throws AppServiceException {
		try {
			userDb = DbAccessor.getUserDb();
			Connection conn = userDb.open();
			
			// create table for basic unmodifiable user info
			String sql = 
					"CREATE TABLE IF NOT EXISTS item (id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "username TEXT, password TEXT)";
			conn.createQuery(sql).executeUpdate();
			
			// create table for list of liked stocks per user
            sql = "CREATE TABLE IF NOT EXISTS liked(id INTEGER, "
                + "ticker TEXT)";
            conn.createQuery(sql).executeUpdate();
            
            // create table for user's investment preferences information
            sql = "CREATE TABLE IF NOT EXISTS profile(id INTEGER PRIMARY KEY,"
                + "capital BOOLEAN, growth BOOLEAN, income BOOLEAN, deferral BOOLEAN,"
                + "speculation BOOLEAN, horizon INTEGER, riskTol INTEGER,"
                + "liq INTEGER, dep BOOLEAN, taxBracket INTEGER);";
			conn.createQuery(sql).executeUpdate();
			
			// create table for user's list of recommended stocks
			sql = "CREATE TABLE IF NOT EXISTS rec(id INTEGER,"
					+ "ticker TEXT)";
			conn.createQuery(sql).executeUpdate();
		}
		catch(Exception ex) {
			throw new AppServiceException(ex.getMessage(), ex);
		}
	}

	/**
	 * Stores new user information
	 * @param SignUpInfo
	 * @throws TakenUserException
	 */
	public LoginResponse createNewUser(SignUpInfo signUp) throws AppServiceException {		
		if(isUserTaken(signUp.getUsername())) {
			throw new AppServiceException("Username is already taken", new TakenUserException());
		}
		
		try {
			Connection conn = userDb.open();
			
			// insert basic info
			String sql = "INSERT INTO item (username, password)"
						 + "VALUES (:username, :password)";
			conn.createQuery(sql)
				.addParameter("username", signUp.getUsername())
				.addParameter("password", signUp.getPassword())
				.executeUpdate();
			
			// get user ID in database
			sql = "SELECT id FROM item WHERE username = :username AND password = :password";
			Integer userID = conn.createQuery(sql)
								.addParameter("username", signUp.getUsername())
								.addParameter("password", signUp.getPassword())
								.executeAndFetchFirst(Integer.class);
			
			// store profile information
			sql = "INSERT INTO profile(id, capital, growth, income, deferral, speculation,"
					+ "horizon, riskTol, liq, dep, taxBracket)"
					+ "VALUES(:id, :capital, :growth, :income, :deferral, :speculation, "
					+ ":horizon, :riskTol, :liq, :dep, :taxBracket)";
			
			conn.createQuery(sql)
				.addParameter("id", userID)
				.addParameter("capital", signUp.wantCapital())
				.addParameter("growth", signUp.wantGrowth())
				.addParameter("income", signUp.wantIncome())
				.addParameter("deferral", signUp.wantDeferral())
				.addParameter("speculation", signUp.wantSpeculation())
				.addParameter("horizon", signUp.getTimeHorizon())
				.addParameter("riskTol", signUp.getRiskTolerance())
				.addParameter("liq", signUp.getLiquidityNeeds())
				.addParameter("dep", signUp.hasDependents())
				.addParameter("taxBracket", signUp.getTaxBracket())
				.executeUpdate();
			
			// get recommendations list, insert to database
			User user = getUser(signUp);
			List<String> recs = user.getRecList();
			
			sql = "INSERT INTO rec(id, ticker)"
					+ "VALUES(:id, :ticker)";
			
			for(String ticker : recs) {
				conn.createQuery(sql)
					.addParameter("id", userID)
					.addParameter("ticker", ticker)
					.executeUpdate();
			}
			
			return new LoginResponse(userID, signUp, new ArrayList<String>(), 
					convertTickerToDeprecatedViewJson(recs));
		} catch(Sql2oException ex) {
			throw new AppServiceException("AppService.createNewUser:"
					+ "Failed to create new user", ex);
		} catch(UserException ex) {
			throw new AppServiceException("AppService.createNewUser:"
					+ "Failed to construct user", ex);
		}
	}
	
	/**
	 * Checks if a valid login info exists.
	 * @param login		login info (username and password)
	 * @return			LoginResponse object
	 * @throws AppServiceException
	 */
	public LoginResponse login(LoginRequest login) throws AppServiceException {
		try {
			Connection conn = userDb.open();
			
			String sql = "SELECT id FROM item WHERE username = :username AND password = :password";
			Integer match = conn.createQuery(sql)
							   .addParameter("username", login.getUsername())
							   .addParameter("password", login.getPassword())
							   .executeAndFetchFirst(Integer.class);
			
			if(match == null)
				throw new AppServiceException("Failed to login: User does not exist.");
			else
				return new LoginResponse(match, login.getUsername(), getProfile(match),
					getLikedList(match), getRec(match));
		} catch(Sql2oException ex) {
			throw new AppServiceException("AppService.createNewUser: "
					+ "Failed to login", ex);
		}
	}
	
	/**
	 * Constructs a User based on SignUpInfo
	 * @return
	 * @throws UserException 
	 */
	private User getUser(SignUpInfo signUp) throws UserException {
		ClientProfile profile = new ClientProfile(signUp.getProfile());
		return new User(new UserInfo(signUp.getUsername(), signUp.getPassword(), profile));
	}
	
	private ClientProfileJson getProfile(int userID) {
		Connection conn = userDb.open();
		
		String sql = "SELECT capital, growth, income, deferral, speculation,"
					+ "horizon, riskTol, liq, dep, taxBracket "
					+ "FROM profile "
					+ "WHERE id = :id";
		ClientProfileJson profile = conn.createQuery(sql)
										.addParameter("id", userID)
										.executeAndFetchFirst(ClientProfileJson.class);
		return profile;
	}
	
	/**
	 * Gets user ID
	 * @param username
	 * @return
	 */
	public int getUserID(String username) {
		Connection conn = userDb.open();
		
		String sql = "SELECT id FROM item WHERE username = :username";
		
		Integer id = conn.createQuery(sql)
						 .addParameter("username", username)
						 .executeAndFetchFirst(Integer.class);
		return id;
	}
	
	/**
	 * Gets a user from the database with the matching user ID
	 * @param userID
	 * @return
	 * @throws UserException
	 */
	public User getUser(int userID) throws AppServiceException {
		Connection conn = userDb.open();
		
		String sql = "SELECT capital, growth, income, deferral, speculation,"
					+ "horizon, riskTol, liq, dep, taxBracket "
					+ "FROM profile "
					+ "WHERE id = :id";
		
		ClientProfileJson profileItem = conn.createQuery(sql)
											.addParameter("id", userID)
											.executeAndFetchFirst(ClientProfileJson.class);
		
		// throw exception if profile is missing
		if(profileItem == null) throw new AppServiceException("Profile is missing.");
		
		// convert to ClientProfile
		ClientProfile profile = new ClientProfile(profileItem);
		
		sql = "SELECT username FROM item WHERE id = :id";
		String username = conn.createQuery(sql)
							  .addParameter("id", userID)
							  .executeAndFetchFirst(String.class);
		
		sql = "SELECT password FROM item WHERE id = :id";
		String password = conn.createQuery(sql)
							  .addParameter("id", userID)
							  .executeAndFetchFirst(String.class);
		
		
		// throw exception if user is missing
		if(username == null) throw new AppServiceException("User doesn't exist.");
		
		UserInfo userInfo = new UserInfo(username, password, profile);
		
		// gets list of liked stocks
		List<String> liked = getLikedList(userID);
		
		// try creating User
		try {
			if(liked != null) return new User(userInfo, liked);
			else return new User(userInfo);
		}
		catch(UserException ex) {
			throw new AppServiceException(ex);
		}
	}
	
	/**
	 * Gets a list of stocks that have been liked by the user
	 * @param userID
	 * @return
	 * @throws AppServiceException 
	 */
	public List<String> getLikedList(int userID) throws AppServiceException {
		try {
			Connection conn = userDb.open();

			String sql = "SELECT ticker FROM liked WHERE id = :id";

			List<String> liked = conn.createQuery(sql)
					.addParameter("id", userID)
					.executeAndFetch(String.class);
			return liked;
		}
		catch(Exception ex) {
			throw new AppServiceException(ex);
		}
	}

	/**
	 * Checks if a username is taken
	 * @param username
	 * @return true if username is taken. False if not taken.
	 * @throws AppServiceException 
	 */
	private boolean isUserTaken(String username) throws AppServiceException {
		try {
			Connection conn = userDb.open();
			
			String sql = "SELECT username FROM item WHERE username = :username";
			String match = conn.createQuery(sql)
							   .addParameter("username", username)
							   .executeAndFetchFirst(String.class);
			if(match == null) return false;
			else return true;
		} catch(Sql2oException ex) {
			throw new AppServiceException("AppService.isUserTaken: "
					+ "Failed query database.", ex);
		}
	}
	
	/**
	 * Gets stock data for detailed view
	 * @param ticker
	 * @return
	 * @throws AppServiceException
	 */
	public DetailedViewJson getStockData(String ticker) throws AppServiceException {
		try {
			YahooQuerier yq = new YahooQuerier();
			ClearBitQuerier c = new ClearBitQuerier();
			
			StockData sd = yq.getStockData(ticker);
			
			//if logo exists, get it
			try {
				String logo = c.getLogo(sd.getName());
				return new DetailedViewJson(ticker, sd.getName(), sd.getIndustry(), sd.getPE(),
						sd.getROE(), sd.getDY(), sd.getDE(), sd.getPB(), sd.getNPM(), sd.getPFCF(), logo);
			}
			catch(ClearBitException ex) {
				return new DetailedViewJson(ticker, sd.getName(), sd.getIndustry(), sd.getPE(),
						sd.getROE(), sd.getDY(), sd.getDE(), sd.getPB(), sd.getNPM(), sd.getPFCF());
			}
		}
		catch(ToolsException ex) {
			throw new AppServiceException("AppService.getStockData: Failed to get stock data for "
										  + ticker, ex);
		}
	}

	/**
	 * Adds a stock to the liked list
	 * @param ticker
	 * @param userID
	 * @throws AppServiceException 
	 */
	public void addToLikedList(String ticker, int userID) throws AppServiceException {
		try {
			Connection conn = userDb.open();	
			
			// add to liked table
			String sql = "INSERT INTO liked(id, ticker)"
					+ "VALUES(:id, :ticker)";
			
			conn.createQuery(sql)
			.addParameter("id", userID)
			.addParameter("ticker", ticker)
			.executeUpdate();
			
			deleteFromRec(ticker, userID);			
		}
		catch(Sql2oException ex) {
			throw new AppServiceException(ex);
		}
	}
	
	/**
	 * Deletes a stock from a user's recommendations list
	 * @param ticker
	 * @param userID
	 * @throws AppServiceException
	 */
	public void deleteFromRec(String ticker, int userID) throws AppServiceException {
		try {
			Connection conn = userDb.open();
			
			// remove from rec table
			String sql = "DELETE FROM rec WHERE id = :id AND ticker = :ticker";

			conn.createQuery(sql)
				.addParameter("id", userID)
				.addParameter("ticker", ticker)
				.executeUpdate();
		}
		catch(Exception ex) {
			throw new AppServiceException(ex);
		}
	}

	/**
	 * Gets recommendation list	 *  
	 * @param userID
	 * @return List of security objects
	 * @throws AppServiceException 
	 */	
	public List<DeprecatedViewJson> getRec(int userID) throws AppServiceException {
		Connection conn = userDb.open();
		
		String sql = "SELECT ticker FROM rec WHERE id = :id";
		List<String> tickers = conn.createQuery(sql)
								   .addParameter("id", userID)
								   .executeAndFetch(String.class);
		return convertTickerToDeprecatedViewJson(tickers);
	}
	
	/*
	 * Helper methods and exception classes
	 */
	
	/**
	 * Converts a list of ticker codes to DeprecatedViewJson objects
	 * @param strList
	 * @return
	 */
	public static List<DeprecatedViewJson> convertTickerToDeprecatedViewJson(List<String> strList) {
    	List<DeprecatedViewJson> results = new ArrayList<DeprecatedViewJson>();
    	YahooQuerier yq = new YahooQuerier();
    	for(String s : strList) {
    		try {
    			StockData sd = yq.getStockData(s);
    			ClearBitQuerier c = new ClearBitQuerier();

    			//if logo exists, get it
    			try {
    				String logo = c.getLogo(sd.getName());
    				results.add(new DeprecatedViewJson(s, sd.getName(), sd.getIndustry(), logo)); 
    			}
    			catch(ClearBitException ex) {
    				results.add(new DeprecatedViewJson(s, sd.getName(), sd.getIndustry())); 
    			}  			
    		} catch (ToolsException ex) {
    			continue;
    		} catch (NullPointerException ex) {
    			continue;
    		}
    	}
    	return results;
    }
	
	public static class AppServiceException extends Exception {
		
		public AppServiceException(String message) {
			super(message);
		}
		
		public AppServiceException(Exception ex) {
			super(ex);
		}
		
		public AppServiceException(String message, Throwable cause) {
			super(message, cause);
		}
	}
	
	public static class TakenUserException extends Exception {
		public TakenUserException() {
			super();
		}
		
		public TakenUserException(String message) {
			super(message);
		}
		
		public TakenUserException(Exception ex) {
			super(ex);
		}
	}

}
