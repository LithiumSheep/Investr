package com.oose2015.group15.invest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.oose2015.group15.invest.datatools.ClearBitQuerier;
import com.oose2015.group15.invest.datatools.ClearBitQuerier.ClearBitException;

public class ClearBitTest {
	private ClearBitQuerier c = new ClearBitQuerier();

	@Test
	public void getLogoTest() {
		try {
			String logo = c.getLogo("uber");
			assertEquals(logo, "https://logo.clearbit.com/uber.com");
		}
		catch(ClearBitException ex) {
			System.out.println("Exception thrown: " + ex.getMessage());
		}
	}
}
