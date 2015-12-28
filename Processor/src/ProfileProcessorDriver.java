import java.util.ArrayList;

/**
 * Create all the securities within the current investment domain.
 *  Note: previously called PPDriver2
 * @author Jeremy
 *
 */
public class ProfileProcessorDriver {
    private static ArrayList<Security> securities = new ArrayList<Security>();
    private static boolean[] times;
    private static ArrayList<String> objectives;

    
    public static void main() {
        createStocks();
        createBonds();
        createFunds();
        createDerivatives();
        createCD();
    }
    
    
    /**
     * Create all the stocks within the current investment domain.
     */
    private static void createStocks() {
        /** Value Stocks */
        // Small-Cap Value Stocks
        setObjectives(new String[]{"Growth", "Growth and Income", "Speculation"});
        times = new boolean[]{false, false, false, true, true};
        Security sCValueStock = new Stock("Small-Cap Value Stock", objectives, "Aggressive", 
                times, "Moderate Liquidity", "Value");
        securities.add(sCValueStock);
    
        // Mid-Cap Value Stocks
        setObjectives(new String[]{"Growth", "Growth and Income"});
        times = new boolean[]{false, false, false, true, true};
        Security mCValueStock = new Stock("Mid-Cap Value Stock", objectives, "Moderate High Risk", 
                times, "Moderate Liquidity", "Value");
        securities.add(mCValueStock);
        
        // Large-Cap Value Stocks
        setObjectives(new String[]{"Growth", "Growth and Income"});
        times = new boolean[]{false, true, true, true, true};
        Security lCValueStock = new Stock("Large-Cap Value Stock", objectives, "Low Moderate Risk", 
                times, "Moderate Liquidity", "Value");
        securities.add(lCValueStock);
        
        /** Growth Stocks */
        // Small-Cap Growth Stocks
        setObjectives(new String[]{"Growth", "Growth and Income", "Speculation"});
        times = new boolean[]{false, false, false, true, true};
        Security sCGrowthStock = new Stock("Small-Cap Growth Stock", objectives, "Aggressive", 
                times, "Moderate Liquidity", "Growth");
        securities.add(sCGrowthStock);
        
        // Mid-Cap Growth Stocks
        setObjectives(new String[]{"Growth", "Growth and Income"});
        times = new boolean[]{false, false, false, true, true};
        Security mCGrowthStock = new Stock("Mid-Cap Growth Stock", objectives, "Moderate High Risk", 
                times, "Moderate Liquidity", "Growth");
        securities.add(mCGrowthStock);
        
        // Large-Cap Growth Stocks
        setObjectives(new String[]{"Growth", "Growth and Income"});
        times = new boolean[]{false, false, true, true, true};
        Security lCGrowthStock = new Stock("Large-Cap Growth Stock", objectives, "Low Moderate Risk", 
                times, "Moderate Liquidity", "Growth");
        securities.add(lCGrowthStock);
        
        /** Income Stocks */
        setObjectives(new String[]{"Growth and Income", "Income"});
        // Mid-Cap Income Stocks
        times = new boolean[]{false, false, false, true, true};
        Security mCIncomeStock = new Stock("Mid-Cap Income Stock", objectives, "Moderate High Risk", 
                times, "Moderate Liquidity", "Income");
        securities.add(mCIncomeStock);
        
        // Large-Cap Income Stocks
        setObjectives(new String[]{"Growth", "Growth and Income"});
        times = new boolean[]{false, false, true, true, true};
        Security lCIncomeStock = new Stock("Large-Cap Income Stock", objectives, "Low Moderate Risk", 
                times, "Moderate Liquidity", "Income");
        securities.add(lCIncomeStock);
        
        /** Preferred Stocks */
        // Small-Cap Preferred Stocks
        setObjectives(new String[]{"Growth", "Growth and Income", "Speculation"});
        times = new boolean[]{false, false, false, true, true};
        Security sCPreferredStock = new Stock("Small-Cap Preferred Stock", objectives, 
                "Aggressive", times, "Moderate Liquidity", "Preferred");
        securities.add(sCPreferredStock);
        
        // Mid-Cap Preferred Stocks
        setObjectives(new String[]{"Growth", "Growth and Income"});
        times = new boolean[]{false, false, false, true, true};
        Security mCPreferredStock = new Stock("Mid-Cap Preferred Stock", objectives, 
                "Moderate High Risk", times, "Moderate Liquidity", "Preferred");
        securities.add(mCPreferredStock);
        
        // Large-Cap Preferred Stocks
        setObjectives(new String[]{"Growth", "Growth and Income"});
        times = new boolean[]{false, true, true, true, true};
        Security lCPreferredStock = new Stock("Large-Cap Income Stock", objectives, 
                "Low Moderate Risk", times, "Moderate Liquidity", "Preferred");  
        securities.add(lCPreferredStock);
    }
    
    
    /**
     * Create all the bonds and treasuries within the current investment domain.
     */
    private static void createBonds() {
        /** Investment-Grade Bonds */
        setObjectives(new String[]{"Capital Preservation", "Income", "Growth and Income"});
        times = new boolean[]{false, true, true, true, true};
        Security investmentGradeBond = new Bond("Investment-Grade Bond", objectives, 
                "Low Risk", times, "Moderate Liquidity", true); 
        securities.add(investmentGradeBond);
        
        
        /** Junk Bonds */
        setObjectives(new String[]{"Income", "Growth and Income", "Speculation"});
        times = new boolean[]{false, true, true, true, true};
        Security junkBond = new Bond("Junk Bond", objectives, 
                "Aggressive", times, "Moderate Liquidity", false); 
        securities.add(junkBond);
        
        
        /** Municipal Bonds */
        setObjectives(new String[]{"Income", "Growth and Income", "Tax Deferral"});
        times = new boolean[]{false, true, true, true, false};
        Security municipalBond = new Bond("Municipal Bond", objectives, 
                "Low Risk", times, "Moderate Liquidity", true); 
        securities.add(municipalBond);
        
        
        /** Treasuries */
        // Treasury Bills
        setObjectives(new String[]{"Capital Preservation"});
        times = new boolean[]{true, false, false, false, false};
        Security treasuryBill = new Bond("Treasury Bill", objectives, 
                "Very Low Risk", times, "High Liquidity", true); 
        securities.add(treasuryBill);
        
        // Treasury Notes
        setObjectives(new String[]{"Capital Preservation", "Income", "Growth and Income"});
        times = new boolean[]{false, true, true, false, false};
        Security treasuryNote = new Bond("Treasury Note", objectives, 
                "Very Low Risk", times, "Moderate Liquidity", true); 
        securities.add(treasuryNote);
        
        // Treasury Bonds
        setObjectives(new String[]{"Income", "Growth and Income"});
        times = new boolean[]{false, false, false, true, true};
        Security treasuryBond = new Bond("Treasury Bond", objectives, 
                "Very Low Risk", times, "Moderate Liquidity", true); 
        securities.add(treasuryBond);
    }
    
    
    /**
     * Create the mutual funds within the current investment domain 
     *  (note: several types of funds have not been included in this iteration). 
     */
    private static void createFunds() {
        // Investment-Grade Bond Fund
        setObjectives(new String[]{"Capital Preservation", "Income", "Growth and Income"});
        times = new boolean[]{false, true, true, true, true};
        Security investmentGradeBondFund = new Fund("Investment-Grade Bond Fund", objectives, 
                "Low Risk", times, "Moderate Liquidity");
        securities.add(investmentGradeBondFund);
        
        // Junk Bond Fund
        setObjectives(new String[]{"Income", "Growth and Income", "Speculation"});
        times = new boolean[]{false, true, true, true, true};
        Security junkBondFund = new Fund("Junk Bond Fund", objectives, 
                "Aggressive", times, "Moderate Liquidity");
        securities.add(junkBondFund);
        
        // Municipal Bond Fund
        setObjectives(new String[]{"Income", "Growth and Income", "Tax Deferral"});
        times = new boolean[]{false, true, true, true, false};
        Security municipalBondFund = new Fund("Municipal Bond Fund", objectives, 
                "Low Risk", times, "Moderate Liquidity");
        securities.add(municipalBondFund);
        
        // Money Market Fund
        setObjectives(new String[]{"Capital Preservation", "Income", "Growth and Income"});
        times = new boolean[]{true, false, false, false, false};
        Security moneyMarketFund = new Fund("Money Market Fund", objectives, 
                "Very Low Risk", times, "High Liquidity");
        securities.add(moneyMarketFund);
        
        // ETF
        setObjectives(new String[]{"Growth", "Income", "Growth and Income"});
        times = new boolean[]{false, true, true, true, true};
        Security eTF = new Fund("Exchange-Traded Fund", objectives, 
                "Low Moderate Risk", times, "Moderate Liquidity");
        securities.add(eTF);
        
        // Index Fund
        setObjectives(new String[]{"Growth", "Growth and Income"});
        times = new boolean[]{false, true, true, true, true};
        Security indexFund = new Fund("Index Fund", objectives, 
                "Low Moderate Risk", times, "Moderate Liquidity");
        securities.add(indexFund);
        
        // Balanced Fund
        setObjectives(new String[]{"Growth and Income"});
        times = new boolean[]{false, false, true, true, true};
        Security balancedFund = new Fund("Balanced Fund", objectives, 
                "Low Moderate Risk", times, "Moderate Liquidity");
        securities.add(balancedFund);
        
        // Mid-Cap Income Equity Fund
        setObjectives(new String[]{"Income", "Growth and Income"});
        times = new boolean[]{false, false, false, true, true};
        Security mCIncomeFund = new Fund("Mid-Cap Equity Income Fund", objectives, 
                "Moderate High Risk", times, "Moderate Liquidity");
        securities.add(mCIncomeFund);
        
        // Large-Cap Income Equity Fund
        setObjectives(new String[]{"Income", "Growth and Income"});
        times = new boolean[]{false, false, true, true, true};
        Security lCIncomeFund = new Fund("Large-Cap Equity Income Fund", objectives, 
                "Low Moderate Risk", times, "Moderate Liquidity");
        securities.add(lCIncomeFund);
        
        // Small-Cap Value Equity Fund
        setObjectives(new String[]{"Growth", "Growth and Income", "Speculation"});
        times = new boolean[]{false, false, false, true, true};
        Security sCValueFund = new Fund("Small-Cap Equity Value Fund", objectives, 
                "Aggressive", times, "Moderate Liquidity");
        securities.add(sCValueFund);
        
        // Mid-Cap Value Equity Fund
        setObjectives(new String[]{"Growth", "Growth and Income"});
        times = new boolean[]{false, false, false, true, true};
        Security mCValueFund = new Fund("Mid-Cap Equity Value Fund", objectives, 
                "Moderate High Risk", times, "Moderate Liquidity");
        securities.add(mCValueFund);
        
        // Large-Cap Value Equity Fund
        setObjectives(new String[]{"Growth", "Growth and Income"});
        times = new boolean[]{false, false, true, true, true};
        Security lCValueFund = new Fund("Large-Cap Equity Value Fund", objectives, 
                "Low Moderate Risk", times, "Moderate Liquidity");
        securities.add(lCValueFund);
        
        // Small-Cap Growth Equity Fund
        setObjectives(new String[]{"Growth", "Growth and Income", "Speculation"});
        times = new boolean[]{false, false, false, true, true};
        Security sCGrowthFund = new Fund("Small-Cap Equity Growth Fund", objectives, 
                "Aggressive", times, "Moderate Liquidity");
        securities.add(sCGrowthFund);
        
        // Mid-Cap Growth Equity Fund
        setObjectives(new String[]{"Growth", "Growth and Income"});
        times = new boolean[]{false, false, false, true, true};
        Security mCGrowthFund = new Fund("Mid-Cap Equity Growth Fund", objectives, 
                "Moderate High Risk", times, "Moderate Liquidity");
        securities.add(mCGrowthFund);
        
        // Small-Cap Growth Equity Fund
        setObjectives(new String[]{"Growth", "Growth and Income"});
        times = new boolean[]{false, false, true, true, true};
        Security lCGrowthFund = new Fund("Large-Cap Equity Growth Fund", objectives, 
                "Low Moderate Risk", times, "Moderate Liquidity");
        securities.add(lCGrowthFund);
    }
    
    
    /**
     * Create a derivative.
     */
    private static void createDerivatives() {
        // For now, derivatives are not broken down into different types.
        setObjectives(new String[]{"Speculation"});
        times = new boolean[]{true, false, false, false, false};
        Security derivative = new Derivative("Derivatives", objectives,
                "Aggressive", times, "Moderate Liquidity");
    }
    
    
    /**
     * Create a certificate of deposit.
     */
    private static void createCD() {
        setObjectives(new String[]{"Capital Preservationa", "Income", "Growth and Income"});
        times = new boolean[]{false, true, false, false, false};
        Security cD = new CertificateOfDeposit("Certificate of Deposit", objectives, 
                "Very Low Risk", times, "High Liquidity");
        securities.add(cD);
    }
    
    
    /**
     * Set a security's investment objectives.
     * @param iO    a list of the objectives.
     */
    private static void setObjectives(String[] iO) {
        objectives = new ArrayList<String>();
        
        for (String s : iO) {
            objectives.add(s);
        }
    }
    
    
    /**
     * Give access to all the securities within the current
     *  investment domain.
     * @return      an array list of securities
     */
    public static ArrayList<Security> getSecurities() {
        return securities;
    }
}
