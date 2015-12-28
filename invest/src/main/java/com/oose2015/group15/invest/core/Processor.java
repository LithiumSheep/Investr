package com.oose2015.group15.invest.core;

import java.util.ArrayList;
import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import javax.sql.DataSource;

/**
 * Processor computes tags that match a user's preferences, cross-checks them against our tag database,
 * gets back the list of securities that match, and send these to the APIQuerier to get their full
 * market data/profile
 */
public class Processor {
	private APIQuerier querier;
	private Preferences prefs;
	
	/**A pre-processed database that lists all securities with matching tags*/
	private Sql2o tagDb;
	
	public Processor(Preferences prefs, DataSource source) {
		this.prefs = prefs;
		tagDb = new Sql2o(source);
	}
	
	/**
	 * Matches user's preferences with a list of securities
	 * @return list of Security objects
	 */
	public List<Security> match() {
		return null;
	}
	
	/**
	 * Computes tags that match a user's preferences (tags are categories of securities that
	 * fit
	 * @return
	 */
	private List<String> computeTags() {
		return null;
	}
	
	/**
	 * Provided a list of tags, return the name of securities that match those tags
	 * @param tags	list of tags
	 * @return list of ticker codes that match
	 */
	public List<String> getMatches(List<String> tags) {
		List<String> result = new ArrayList<String>();
		
		Connection conn = tagDb.open();		
		String sql = "SELECT ticker FROM tags WHERE tag = \"%s\"";
		
		for(String tag: tags) {
			List<String> matches = conn.createQuery(String.format(sql, tag))
	                .executeAndFetch(String.class);
		
			// add to resulting matches
			for(String m: matches) {
				if(!result.contains(m)) result.add(m);
			}
		}
		return result;
	}	
}
