package com.oose2015.group15.invest.dataprocessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Create all the securities within the current investment domain.
 *  Note: previously called PPDriver2
 * @author Jeremy
 *
 */
public class InvestmentDomain {
    private static List<Security> securities;
    
    public static final String[] times = {"Very Short Term", "Short Term", "Medium Term", "Long Term", "Very Long Term"};
    public static final String[] risks = {"Very Low Risk", "Low Risk", "Low Moderate Risk", "Moderate High Risk", "Aggressive"};
    public static final String[] liquidities = {"Low Liquidity", "Moderate Liquidity", "High Liquidity"};
    
    
    private static void initSecs() {
    	securities = new ArrayList<Security>();
    	securities.addAll(createStocks());
    	securities.addAll(createBonds());
    	securities.addAll(createFunds());
    	securities.addAll(createDerivatives());
    	securities.addAll(createCD());
    }

    /**
     * Create all the stocks within the current investment domain.
     */
    private static List<Security> createStocks() {
    	List<Security> stocks = new ArrayList<Security>();
    	
        /** Value Stocks */
        // Small-Cap Value Stocks
        // setObjectives(new String[]{"Growth", "Growth and Income", "Speculation"});
        // times = new boolean[]{false, false, false, true, true};
        Security sCValueStock = new Stock("Small-Cap Value Stock",
        		Arrays.asList(new String[]{"Growth", "Growth and Income", "Speculation"}),
        		"Aggressive", new boolean[]{false, false, false, true, true},
        		"Moderate Liquidity", "Value");
        stocks.add(sCValueStock);
    
        // Mid-Cap Value Stocks
        // setObjectives(new String[]{"Growth", "Growth and Income"});
        // times = new boolean[]{false, false, false, true, true};
        Security mCValueStock = new Stock("Mid-Cap Value Stock", 
        		Arrays.asList(new String[]{"Growth", "Growth and Income"}), "Moderate High Risk", 
        		new boolean[]{false, false, false, true, true}, "Moderate Liquidity", "Value");
        stocks.add(mCValueStock);
        
        // Large-Cap Value Stocks
        // setObjectives(new String[]{"Growth", "Growth and Income"});
        // times = new boolean[]{false, true, true, true, true};
        Security lCValueStock = new Stock("Large-Cap Value Stock",
        		Arrays.asList(new String[]{"Growth", "Growth and Income"}), "Low Moderate Risk", 
        		new boolean[]{false, true, true, true, true}, "Moderate Liquidity", "Value");
        stocks.add(lCValueStock);
        
        /** Growth Stocks */
        // Small-Cap Growth Stocks
        // setObjectives(new String[]{"Growth", "Growth and Income", "Speculation"});
        // times = new boolean[]{false, false, false, true, true};
        Security sCGrowthStock = new Stock("Small-Cap Growth Stock",
        		Arrays.asList(new String[]{"Growth", "Growth and Income", "Speculation"}),
        		"Aggressive", new boolean[]{false, true, true, true, true},
        		"Moderate Liquidity", "Growth");
        stocks.add(sCGrowthStock);
        
        // Mid-Cap Growth Stocks
        // setObjectives(new String[]{"Growth", "Growth and Income"});
        // times = new boolean[]{false, false, false, true, true};
        Security mCGrowthStock = new Stock("Mid-Cap Growth Stock",
        		Arrays.asList(new String[]{"Growth", "Growth and Income"}), "Moderate High Risk", 
        		new boolean[]{false, false, false, true, true}, "Moderate Liquidity", "Growth");
        stocks.add(mCGrowthStock);
        
        // Large-Cap Growth Stocks
        // setObjectives(new String[]{"Growth", "Growth and Income"});
        // times = new boolean[]{false, false, true, true, true};
        Security lCGrowthStock = new Stock("Large-Cap Growth Stock",
        		Arrays.asList(new String[]{"Growth", "Growth and Income"}), "Low Moderate Risk", 
        		new boolean[]{false, false, true, true, true}, "Moderate Liquidity", "Growth");
        stocks.add(lCGrowthStock);
        
        /** Income Stocks */
        // setObjectives(new String[]{"Growth and Income", "Income"});
        // Mid-Cap Income Stocks
        // times = new boolean[]{false, false, false, true, true};
        Security mCIncomeStock = new Stock("Mid-Cap Income Stock",
        		Arrays.asList(new String[]{"Growth and Income", "Income"}), "Moderate High Risk", 
        		new boolean[]{false, false, false, true, true}, "Moderate Liquidity", "Income");
        stocks.add(mCIncomeStock);
        
        // Large-Cap Income Stocks
        // setObjectives(new String[]{"Growth", "Growth and Income"});
        // times = new boolean[]{false, false, true, true, true};
        Security lCIncomeStock = new Stock("Large-Cap Income Stock",
        		Arrays.asList(new String[]{"Growth", "Growth and Income"}), "Low Moderate Risk", 
        		new boolean[]{false, false, true, true, true}, "Moderate Liquidity", "Income");
        stocks.add(lCIncomeStock);
        
        /** Preferred Stocks */
        // Small-Cap Preferred Stocks
        //setObjectives(new String[]{"Growth", "Growth and Income", "Speculation"});
        //times = new boolean[]{false, false, false, true, true};
        Security sCPreferredStock = new Stock("Small-Cap Preferred Stock",
        		Arrays.asList(new String[]{"Growth", "Growth and Income", "Speculation"}), 
                "Aggressive", new boolean[]{false, false, false, true, true}, "Moderate Liquidity", "Preferred");
        stocks.add(sCPreferredStock);
        
        // Mid-Cap Preferred Stocks
        //setObjectives(new String[]{"Growth", "Growth and Income"});
        //times = new boolean[]{false, false, false, true, true};
        Security mCPreferredStock = new Stock("Mid-Cap Preferred Stock",
        		Arrays.asList(new String[]{"Growth", "Growth and Income"}), 
                "Moderate High Risk", new boolean[]{false, false, false, true, true},
                "Moderate Liquidity", "Preferred");
        stocks.add(mCPreferredStock);
        
        // Large-Cap Preferred Stocks
        // setObjectives(new String[]{"Growth", "Growth and Income"});
        // times = new boolean[]{false, true, true, true, true};
        Security lCPreferredStock = new Stock("Large-Cap Income Stock",
        		Arrays.asList(new String[]{"Growth", "Growth and Income"}), 
                "Low Moderate Risk", new boolean[]{false, true, true, true, true},
                "Moderate Liquidity", "Preferred");  
        stocks.add(lCPreferredStock);
        
        return stocks;
    }
    
    
    /**
     * Create all the bonds and treasuries within the current investment domain.
     */
    private static List<Security> createBonds() {
    	List<Security> bonds = new ArrayList<Security>();
    	
        /* Investment-Grade Bonds */
        //setObjectives(new String[]{"Capital Preservation", "Income", "Growth and Income"});
        //times = new boolean[]{false, true, true, true, true};
        Security investmentGradeBond = new Bond("Investment-Grade Bond",
        		Arrays.asList(new String[]{"Capital Preservation", "Income", "Growth and Income"}), 
                "Low Risk", new boolean[]{false, true, true, true, true},
                "Moderate Liquidity", true); 
        bonds.add(investmentGradeBond);
        
        
        /* Junk Bonds */
        //setObjectives(new String[]{"Income", "Growth and Income", "Speculation"});
        //times = new boolean[]{false, true, true, true, true};
        Security junkBond = new Bond("Junk Bond",
        		Arrays.asList(new String[]{"Income", "Growth and Income", "Speculation"}), 
                "Aggressive", new boolean[]{false, true, true, true, true},
                "Moderate Liquidity", false); 
        bonds.add(junkBond);
        
        
        /* Municipal Bonds */
        //setObjectives(new String[]{"Income", "Growth and Income", "Tax Deferral"});
        //times = new boolean[]{false, true, true, true, false};
        Security municipalBond = new Bond("Municipal Bond",
        		Arrays.asList(new String[]{"Income", "Growth and Income", "Tax Deferral"}), 
                "Low Risk", new boolean[]{false, true, true, true, false},
                "Moderate Liquidity", true); 
        bonds.add(municipalBond);
        
        
        /** Treasuries */
        // Treasury Bills
        //setObjectives(new String[]{"Capital Preservation"});
        //times = new boolean[]{true, false, false, false, false};
        Security treasuryBill = new Bond("Treasury Bill",
        		Arrays.asList(new String[]{"Capital Preservation"}), 
                "Very Low Risk", new boolean[]{true, false, false, false, false},
                "High Liquidity", true); 
        bonds.add(treasuryBill);
        
        // Treasury Notes
        //setObjectives(new String[]{"Capital Preservation", "Income", "Growth and Income"});
        //times = new boolean[]{false, true, true, false, false};
        Security treasuryNote = new Bond("Treasury Note",
        		Arrays.asList(new String[]{"Capital Preservation", "Income", "Growth and Income"}), 
                "Very Low Risk", new boolean[]{false, true, true, false, false},
                "Moderate Liquidity", true); 
        bonds.add(treasuryNote);
        
        // Treasury Bonds
        //setObjectives(new String[]{"Income", "Growth and Income"});
        //times = new boolean[]{false, false, false, true, true};
        Security treasuryBond = new Bond("Treasury Bond",
        		Arrays.asList(new String[]{"Income", "Growth and Income"}), 
                "Very Low Risk", new boolean[]{false, false, false, true, true},
                "Moderate Liquidity", true); 
        bonds.add(treasuryBond);
        
        return bonds;
    }
    
    
    /**
     * Create the mutual funds within the current investment domain 
     *  (note: several types of funds have not been included in this iteration). 
     */
    private static List<Security> createFunds() {
    	List<Security> funds = new ArrayList<Security>();
    	
        // Investment-Grade Bond Fund
        //setObjectives(new String[]{"Capital Preservation", "Income", "Growth and Income"});
        //times = new boolean[]{false, true, true, true, true};
        Security investmentGradeBondFund = new Fund("Investment-Grade Bond Fund",
        		Arrays.asList(new String[]{"Capital Preservation", "Income", "Growth and Income"}), 
                "Low Risk", new boolean[]{false, true, true, true, true}, "Moderate Liquidity");
        funds.add(investmentGradeBondFund);
        
        // Junk Bond Fund
        //setObjectives(new String[]{"Income", "Growth and Income", "Speculation"});
        //times = new boolean[]{false, true, true, true, true};
        Security junkBondFund = new Fund("Junk Bond Fund",
        		Arrays.asList(new String[]{"Income", "Growth and Income", "Speculation"}), 
                "Aggressive", new boolean[]{false, true, true, true, true}, "Moderate Liquidity");
        funds.add(junkBondFund);
        
        // Municipal Bond Fund
        //setObjectives(new String[]{"Income", "Growth and Income", "Tax Deferral"});
        //times = new boolean[]{false, true, true, true, false};
        Security municipalBondFund = new Fund("Municipal Bond Fund",
        		Arrays.asList(new String[]{"Income", "Growth and Income", "Tax Deferral"}), 
                "Low Risk", new boolean[]{false, true, true, true, false}, "Moderate Liquidity");
        funds.add(municipalBondFund);
        
        // Money Market Fund
        //setObjectives(new String[]{"Capital Preservation", "Income", "Growth and Income"});
        //times = new boolean[]{true, false, false, false, false};
        Security moneyMarketFund = new Fund("Money Market Fund",
        		Arrays.asList(new String[]{"Capital Preservation", "Income", "Growth and Income"}), 
                "Very Low Risk", new boolean[]{true, false, false, false, false}, "High Liquidity");
        funds.add(moneyMarketFund);
        
        // ETF
        //setObjectives(new String[]{"Growth", "Income", "Growth and Income"});
        //times = new boolean[]{false, true, true, true, true};
        Security eTF = new Fund("Exchange-Traded Fund",
        		Arrays.asList(new String[]{"Growth", "Income", "Growth and Income"}), 
                "Low Moderate Risk", new boolean[]{false, true, true, true, true},
                "Moderate Liquidity");
        funds.add(eTF);
        
        // Index Fund
        //setObjectives(new String[]{"Growth", "Growth and Income"});
        //times = new boolean[]{false, true, true, true, true};
        Security indexFund = new Fund("Index Fund", 
        		Arrays.asList(new String[]{"Growth", "Growth and Income"}), 
                "Low Moderate Risk", new boolean[]{false, true, true, true, true},
                "Moderate Liquidity");
        funds.add(indexFund);
        
        // Balanced Fund
        //setObjectives(new String[]{"Growth and Income"});
        //times = new boolean[]{false, false, true, true, true};
        Security balancedFund = new Fund("Balanced Fund",
        		Arrays.asList(new String[]{"Growth and Income"}), 
                "Low Moderate Risk", new boolean[]{false, false, true, true, true},
                "Moderate Liquidity");
        funds.add(balancedFund);
        
        // Mid-Cap Income Equity Fund
        //setObjectives(new String[]{"Income", "Growth and Income"});
        //times = new boolean[]{false, false, false, true, true};
        Security mCIncomeFund = new Fund("Mid-Cap Equity Income Fund",
        		Arrays.asList(new String[]{"Income", "Growth and Income"}), 
                "Moderate High Risk", new boolean[]{false, false, false, true, true},
                "Moderate Liquidity");
        funds.add(mCIncomeFund);
        
        // Large-Cap Income Equity Fund
        //setObjectives(new String[]{"Income", "Growth and Income"});
        //times = new boolean[]{false, false, true, true, true};
        Security lCIncomeFund = new Fund("Large-Cap Equity Income Fund",
        		Arrays.asList(new String[]{"Income", "Growth and Income"}), 
                "Low Moderate Risk", new boolean[]{false, false, true, true, true},
                "Moderate Liquidity");
        funds.add(lCIncomeFund);
        
        // Small-Cap Value Equity Fund
        //setObjectives(new String[]{"Growth", "Growth and Income", "Speculation"});
        //times = new boolean[]{false, false, false, true, true};
        Security sCValueFund = new Fund("Small-Cap Equity Value Fund",
        		Arrays.asList(new String[]{"Growth", "Growth and Income", "Speculation"}), 
                "Aggressive", new boolean[]{false, false, false, true, true},
                "Moderate Liquidity");
        funds.add(sCValueFund);
        
        // Mid-Cap Value Equity Fund
        //setObjectives(new String[]{"Growth", "Growth and Income"});
        //times = new boolean[]{false, false, false, true, true};
        Security mCValueFund = new Fund("Mid-Cap Equity Value Fund",
        		Arrays.asList(new String[]{"Growth", "Growth and Income"}), 
                "Moderate High Risk", new boolean[]{false, false, false, true, true},
                "Moderate Liquidity");
        funds.add(mCValueFund);
        
        // Large-Cap Value Equity Fund
        //setObjectives(new String[]{"Growth", "Growth and Income"});
        //times = new boolean[]{false, false, true, true, true};
        Security lCValueFund = new Fund("Large-Cap Equity Value Fund",
        		Arrays.asList(new String[]{"Growth", "Growth and Income"}), 
                "Low Moderate Risk", new boolean[]{false, false, true, true, true},
                "Moderate Liquidity");
        funds.add(lCValueFund);
        
        // Small-Cap Growth Equity Fund
        //setObjectives(new String[]{"Growth", "Growth and Income", "Speculation"});
        //times = new boolean[]{false, false, false, true, true};
        Security sCGrowthFund = new Fund("Small-Cap Equity Growth Fund", 
        		Arrays.asList(new String[]{"Growth", "Growth and Income", "Speculation"}), 
                "Aggressive", new boolean[]{false, false, false, true, true},
                "Moderate Liquidity");
        funds.add(sCGrowthFund);
        
        // Mid-Cap Growth Equity Fund
        //setObjectives(new String[]{"Growth", "Growth and Income"});
        //times = new boolean[]{false, false, false, true, true};
        Security mCGrowthFund = new Fund("Mid-Cap Equity Growth Fund",
        		Arrays.asList(new String[]{"Growth", "Growth and Income"}), 
                "Moderate High Risk", new boolean[]{false, false, false, true, true},
                "Moderate Liquidity");
        funds.add(mCGrowthFund);
        
        // Small-Cap Growth Equity Fund
        //setObjectives(new String[]{"Growth", "Growth and Income"});
        //times = new boolean[]{false, false, true, true, true};
        Security lCGrowthFund = new Fund("Large-Cap Equity Growth Fund",
        		Arrays.asList(new String[]{"Growth", "Growth and Income"}), 
                "Low Moderate Risk", new boolean[]{false, false, true, true, true},
                "Moderate Liquidity");
        funds.add(lCGrowthFund);
        
        return funds;
    }
    
    
    /**
     * Create a derivative.
     */
    private static List<Security> createDerivatives() {
    	List<Security> der = new ArrayList<Security>();
    	
        // For now, derivatives are not broken down into different types.
        // setObjectives(new String[]{"Speculation"});
        // times = new boolean[]{true, false, false, false, false};
        Security derivative = new Derivative("Derivatives",
        		Arrays.asList(new String[]{"Speculation"}),
                "Aggressive", new boolean[]{true, false, false, false, false},
                "Moderate Liquidity");
        der.add(derivative);
        
        return der;
    }
    
    
    /**
     * Create a certificate of deposit.
     */
    private static List<Security> createCD() {
    	List<Security> cert = new ArrayList<Security>();
        //setObjectives(new String[]{"Capital Preservationa", "Income", "Growth and Income"});
        //times = new boolean[]{false, true, false, false, false};
        Security cD = new CertificateOfDeposit("Certificate of Deposit",
        		Arrays.asList(new String[]{"Capital Preservationa", "Income", "Growth and Income"}), 
                "Very Low Risk", new boolean[]{false, true, false, false, false},
                "High Liquidity");
        cert.add(cD);
        
        return cert;
    }
    
    /**
     * Give access to all the securities within the current
     *  investment domain.
     * @return      an array list of securities
     */
    public static List<Security> getSecurities() {
    	if(securities == null) initSecs();
        return securities;
    }
}
