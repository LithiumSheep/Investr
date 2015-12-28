package com.oose2015.group15.invest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import com.oose2015.group15.invest.datatools.DbAccessor;
import com.oose2015.group15.invest.datatools.ToolsException;

/**
 * Tests if Tagger gives appropriate tags to stocks and loads them properly into a database
 * @author Kathleen
 *
 */
public class TaggerTest {
	private ArrayList<String> testCases = new ArrayList<String>();
	private Sql2o tagDb;
	
	@Before
	public void setup() throws IOException {
		testCases.add("AAPL");
		tagDb = new Sql2o(DbAccessor.configureDataSource(DbAccessor.TAG_DB_PATH));
	}
	
	@Test
	public void tagStockTest() throws SQLException, ToolsException {
		Connection conn = tagDb.open();
		String sql = "SELECT value, growth, income FROM tag WHERE ticker = :ticker";
		
		// AAPL
		Tag tag = conn.createQuery(sql)
					  .addParameter("ticker", "AAPL")
					  .executeAndFetchFirst(Tag.class);
		assertEquals("GROWTH", tag.getType());
		
		// SYMC
		tag = conn.createQuery(sql)
				  .addParameter("ticker", "SYMC")
				  .executeAndFetchFirst(Tag.class);
		assertEquals("GROWTH", tag.getType());
		
		// MSFT
		tag = conn.createQuery(sql)
				  .addParameter("ticker", "SYMC")
				  .executeAndFetchFirst(Tag.class);
		assertEquals("GROWTH", tag.getType());
		
		// CHRW
		tag = conn.createQuery(sql)
				  .addParameter("ticker", "CHRW")
				  .executeAndFetchFirst(Tag.class);
		assertEquals("VALUE", tag.getType());
	}
	
	public static class Tag {
		int value;
		int growth;
		int income;
		
		public Tag(int value, int growth, int income) {
			this.value = value;
			this.growth = growth;
			this.income = income;
		}

		public int getValue() {
			return value;
		}

		public int getGrowth() {
			return growth;
		}

		public int getIncome() {
			return income;
		}
		
		public String getType() {
			if(value == 1) return "VALUE";
			if(growth == 1) return "GROWTH";
			if(income == 1) return "INCOME";
			return "NONE";
		}
	}
}
