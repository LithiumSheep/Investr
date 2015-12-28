package com.oose2015.group15.invest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;
/**
 * Preliminary bootstrap class for back-end
 * @author gideon
 */
public class Bootstrap {
	
	/*
	 *  The following field declarations are copied from the Todo app
	 *  We'll probably need these fields but IP_ADDRESS and PORT will probably
	 *  be changed to something else in the final version
	 */
	
	public static final String IP_ADDRESS = "192.168.1.228";
	public static final int PORT = 8080;
    private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);
	
	public static void main(String[] args) {		
		// Specify the IP address and Port at which the server should be run
		ipAddress(IP_ADDRESS);
		port(PORT);

		// Specify the sub-directory from which to serve static resources (like html and css)
		staticFileLocation("/public");

		// Create the model instance and then configure and start the web service
		try {
			AppService model = new AppService();
			new AppController(model);
		} catch (Exception ex) {
			logger.error("Failed to create a AppService instance. Aborting");
		}
	}
}
