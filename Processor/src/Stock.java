import java.util.ArrayList;

public class Stock extends Security {
    private String tag;

    
    public Stock(String sT, ArrayList<String> iO, String rT, 
            boolean[] tH, String lN, String t) {
        super(sT, iO, rT, tH, lN);
        
        assert(t.equalsIgnoreCase("Value") || 
                t.equalsIgnoreCase("Growth") || 
                t.equalsIgnoreCase("Income") ||
                t.equalsIgnoreCase("Preferred"));
        this.tag = t;
    }

    /** Get the stock's tag. */
    public String getTag() {
        return this.tag;
    }
}
