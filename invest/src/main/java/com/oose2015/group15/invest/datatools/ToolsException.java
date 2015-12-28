package com.oose2015.group15.invest.datatools;

/**
 * Exception to be thrown by classes in tools package
 * @author Kathleen
 *
 */
public class ToolsException extends Exception {
	public ToolsException(String message) {
		super(message);
	}
	
	public ToolsException(Exception ex) {
		super(ex);
	}
}
