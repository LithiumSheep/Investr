import java.util.ArrayList;

public class Bond extends Security {
    private boolean isInvestmentGrade;
    private boolean isJunk;


    public Bond(String sT, ArrayList<String> iO, String rT, boolean[] tH, 
            String lN, boolean isIG) {
        super(sT, iO, rT, tH, lN);
        this.isInvestmentGrade = isIG;
        this.isJunk = !isIG;
    }


    /** Determine the credit risk category of the bond. */
    public boolean isInvestmentGrade() {
        return isInvestmentGrade;
    }

    public boolean isJunk() {
        return isJunk;
    }
}
