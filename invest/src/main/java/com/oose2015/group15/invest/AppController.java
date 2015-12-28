package com.oose2015.group15.invest;

import com.google.gson.Gson;
import com.oose2015.group15.invest.jsonobject.*;
import com.oose2015.group15.invest.core.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import spark.Request;
import spark.Response;
import spark.Route;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


import static spark.Spark.*;

/**
 * Class that controls endpoints and interface with front end
 * 
 * TODO
 */
public class AppController {
    private static final String API_CONTEXT = "/api/v1";
    
    private final AppService appService;

    //private final Logger logger = LoggerFactory.getLogger(HareAndHoundsController.class);
 
    /**
     * constructor
     * @param AppService
     * @throws Exception 
     */
    public AppController(AppService appService) throws Exception {
        this.appService = appService;
        setupEndpoints();
    }   
 
    /**
     * Set up end points
     */
    private void setupEndpoints() {
    	
    	/**
    	 * this post method is for creating a new user
    	 */
    	post(API_CONTEXT + "/new", "application/json", (request, response) -> {
    		try {
    			SignUpInfo signup = new Gson().fromJson(request.body(), SignUpInfo.class);
			
    			LoginResponse result = appService.createNewUser(signup);
    			response.status(201);
    			return result;
    		} catch (AppService.AppServiceException ex) {
    			response.status(401);
    			return new ExceptionResponse(ex.getMessage());
    		}
    	}, new JsonTransformer());
    	
    	/**
    	 * this post method is used by the client to login to the app.
    	 */
        post(API_CONTEXT + "/login", "application/json", (request, response) -> {
            try {
            	//create a new LoginInfo object that captures username and password
            	LoginRequest login = new Gson().fromJson(request.body(), LoginRequest.class);
            	LoginResponse result = appService.login(login);
            	
            	response.status(200);
            	return result;
            } catch (AppService.AppServiceException ex) {
            	response.status(401);            	
            	return new ExceptionResponse(ex.getMessage());
            }
        }, new JsonTransformer());
        
        /**
         * Gets stock data given a ticker code
         */
        get(API_CONTEXT + "/:ticker", "application/json", (request, response) -> {
        	try {
        		DetailedViewJson ret = appService.getStockData(request.params(":ticker"));
        		response.status(200);
        		return ret;
        	}
        	catch(AppService.AppServiceException ex) {
        		response.status(404);
        		return new ExceptionResponse(ex.getMessage());
        	}
        }, new JsonTransformer());

        /**
         * gets a list of stock recommendations
         */
        get(API_CONTEXT + "/:userId/rec", "application/json", (request, response) -> {
        	try {
        		List<DeprecatedViewJson> ret = 
        				appService.getRec(Integer.parseInt(request.params(":userId")));
        		response.status(200);
        		
        		return ret;
        	} catch(Exception e) {
        		response.status(404);
        		return new ExceptionResponse(e.getMessage());
        	}
        }, new JsonTransformer());
        
        /**
         * this post method is for the swipe left/dislike
         */
        post(API_CONTEXT + "/:userId/swipeleft", "application/json", (request, response) -> {
        	try {
        		HashMap<String, String> del = new Gson().fromJson(request.body(),
						  											HashMap.class);
        		appService.deleteFromRec(del.get("ticker"),
        				Integer.parseInt(request.params(":userId")));
        		response.status(200);
        		return Collections.EMPTY_MAP;
        	} catch(Exception e) {
        		System.out.println(e.getMessage());
        		response.status(400);
        		return new ExceptionResponse(e.getMessage());
        	}
        }, new JsonTransformer());
        
        /**
         * Swipes right / likes a stock
         */
        post(API_CONTEXT + "/:userId/swiperight", "application/json", (request, response) -> {
        	try {
        		HashMap<String, String> add = new Gson().fromJson(request.body(),
        														  HashMap.class);
        		appService.addToLikedList(add.get("ticker"),
        				Integer.parseInt(request.params(":userId")));
        		response.status(200);
        		return Collections.EMPTY_MAP;
        	} catch(AppService.AppServiceException ex) {
        		response.status(400);
        		return new ExceptionResponse(ex.getMessage());
        	}
        }, new JsonTransformer());
        
        /**
         * Gets a list of securities that the client has liked
         */
        get(API_CONTEXT + "/:userId/liked", "application/json", (request, response) -> {
        	try {
        		List<String> liked = appService.getLikedList(
        				Integer.parseInt(request.params(":userId")));
        		response.status(200);
        		return liked;
        	} catch(AppService.AppServiceException ex) {
        		response.status(400);
        		return new ExceptionResponse(ex.getMessage());
        	}
        }, new JsonTransformer());
    }
}

