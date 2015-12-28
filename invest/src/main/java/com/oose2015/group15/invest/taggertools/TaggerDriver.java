package com.oose2015.group15.invest.taggertools;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import com.oose2015.group15.invest.datatools.*;

public class TaggerDriver {
	private static YahooQuerier yq = new YahooQuerier();
	private static Sql2o metaDb;
	private static final boolean IS_FIXED_PRICE = false;
	//private static final Path TAG_PATH = Paths.get(".", "data", "tag.db");
	
	public static void main(String[] args) throws IOException, ToolsException, SQLException {
		metaDb = new Sql2o(DbAccessor.configureDataSource(DbAccessor.META_DB_PATH));
		//tagDb = new Sql2o(MetaPreProcessor.configureDataSource(TAG_PATH));
		
		Connection meta = metaDb.open();
		String sql = "SELECT ticker FROM item";
		List<String> tickers = meta.createQuery(sql).executeAndFetch(String.class);
		
		for(String ticker: tickers) {
			System.out.println(ticker);
			Tagger tagger = new Tagger(yq.getCompleteStockData(ticker));
			tagger.tagStock(IS_FIXED_PRICE);
		}
	}
}
