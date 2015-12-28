package com.oose2015.group15.invest.dataprocessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.oose2015.group15.invest.taggertools.TagMatcher;

/**
 * Process the user's preferences and suggest the appropriate types of securities.
 * @author Jeremy
 *
 */
public class ProfileProcessor {
    private ClientProfile client;
    private List<Security> securities = new ArrayList<Security>();
    private List<Security> toSuggest;

    /**
     * Creates ProfileProcessor instance
     * @param profile
     * @param secs
     */
    public ProfileProcessor(ClientProfile profile, List<Security> secs) {
        /* Note: I assume that the front end will check for the validity of inputs, such as
         *  investment objective, time horizon, etc., so that is not done here. This includes combinations 
         *  of investment objectives. 
         */
        this.client = profile;
        this.securities = secs; 
        this.toSuggest = new ArrayList<Security>();
        getInvestmentSuggestions();
    }

    /**
     * Given the computed investment suggestions, return the tickers that match
     * these properties
     * @return
     * @throws IOException 
     */
    public List<String> getMatchingTickers() throws IOException {
    	return TagMatcher.getMatches(this.toSuggest);
    }
    
    /**
     * Create a list of investments suitable for the user's needs.
     *  Note: this version does not differentiate between small, mid, and
     *  large-cap stocks, by bond duration, or between different types of 
     *  derivatives or annuities. 
     * @return 
     * @return      A list of the suggested investments for the user. 
     */
    public List<Security> getInvestmentSuggestions() {
        /*
         * 1. Make a list of all possible choices, given the investment objective
         * 2. Remove all the choices that don't fit within the time horizon
         *     (note: if someone indicates that they have a long time horizon but high 
         *     liquidity needs, treat them as if they only need short-term debt instruments)
         * 3. Remove all the choices that don't fit within the risk tolerance
         * 4. Make corrections to list based on liquidity need 
         * 5. Make corrections to list based on dependents (not implemented yet)
         * 6. Check for tax defer - if so, add municipal bonds to the front of the list
         */
        
        String timeHorizon = this.client.getTimeHorizon();

        // 1. Make a list of all possible choices, given the investment objective
        this.editListFromObjectives(this.client.getInvestmentObjectives());
        
        // 2. Remove all the choices that don't fit within the time horizon
        if (timeHorizon.equalsIgnoreCase("Long Term") && 
                this.client.getLiquidityNeeds().equalsIgnoreCase("High Liquidity")) {
            timeHorizon = "Very Short Term";
        } 
        
        //this.editListFromTLPreferences(timeHorizon);
        this.editListFromTLPref(timeHorizon);
        
        // 3. Remove all the choices that don't fit within the risk tolerance
        this.editListFromRisk(this.client.getTols());

        // 4. Make corrections to list based on liquidity need 
        this.editListFromTLPref(this.client.getLiquidityNeeds());
        
        // 5. Not yet implemented
        
        // 6. Check for tax defer - if so, add municipal bonds to the front of the list
        this.makeTaxCorrection();
        
        // return suggestions
        return getToSuggest();
    }
    
    
    /**
     * Remove securities from the list that don't match the 
     *  user's risk tolerance.
     * @param risks     the array list of risk tolerances (will
     *                   have either one or two values)
     */
    private void editListFromRisk(ArrayList<String> risks) {
        ArrayList<Security> dontRemove = new ArrayList<Security>();

        for (Security sec : this.toSuggest) {   
            for (String s : risks) {
                if (sec.getRiskTolerance().equalsIgnoreCase(s) && 
                        !dontRemove.contains(sec)) {
                    dontRemove.add(sec);
                }
            }
        }

        this.toSuggest.retainAll(dontRemove);
    }
    
    
    /**
     * Edit the suggestion list based on the user's
     *  time or liquidity preferences.
     * @param pref      a string representing time horizon or 
     *                   liquidity need
     */
    private void editListFromTLPref(String pref) {
        ArrayList<Security> toRemove = new ArrayList<Security>();
        
        for (Security sec : this.toSuggest) {
        //for (Security sec : this.securities) {
            if (!sec.tLPreferencesInclude(pref)) {
                toRemove.add(sec);
            }
        }
        
        this.toSuggest.removeAll(toRemove);
    }
    
    
    /**
     * If the user is in a high tax bracket (e.g. above 30%), 
     *  place municipal bonds at the top of their list of 
     *  suggested investments. 
     */
    private void makeTaxCorrection() {
        ArrayList<Security> toAppend = new ArrayList<Security>();
        
        // Find all the municipal bonds
        if (this.client.getTaxBracket() >= 30.0) {
            for (Security sec : this.toSuggest) {
                if (sec.isA("Municipal Bond")) {
                    toAppend.add(sec);
                }
            }
        }
        
        this.toSuggest.removeAll(toAppend);
        
        // Put the muni's at the front of the list
        for (Security sec : toAppend) {
            this.toSuggest.add(0, sec);
        }
    }

    
    /**
     * Find the appropriate investment objectives in the list of all objectives, 
     *  then add its securities to the toSuggest list.
     */
    private void editListFromObjectives(ArrayList<String> prefs) {
        for (String s : prefs) {
            for (Security sec : this.securities) {
                if (sec.objectivesInclude(s) && !this.toSuggest.contains(sec)) {
                    this.toSuggest.add(sec);
                }
            }
        }
    }
    
    /**
     * Give access to the client's profile.
     * @return      the client profile
     */
    public ClientProfile getClient() {
        return this.client;
    }
    
    /**
     * Give access to the list of suggested securities.
     * @return      the list of suggested securities
     */
    public List<Security> getToSuggest() {
        return this.toSuggest;
    }
}
