import java.util.ArrayList;

public class Fund extends Security {

    //public Fund(String sT, String rT, String tH, String lN) {
    public Fund(String sT, ArrayList<String> iO, String rT, boolean[] tH, String lN) {
        super(sT, iO, rT, tH, lN);
    }

}
