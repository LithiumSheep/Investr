import java.util.ArrayList;

public class ProfileSubComp {
    private String id;
    //private ArrayList<String> comp = new ArrayList<String>();
    private ArrayList<Security> comp = new ArrayList<Security>();
    
    public ProfileSubComp(String compID, ArrayList<Security> c) {
        this.id = compID;
        for (Security s : c) this.comp.add(s);
    }

    public String getId() {
        return id;
    }

    public ArrayList<Security> getComp() {
        return comp;
    }
    
    public String[] getCompArray() {
        return (String[]) comp.toArray();
    }
}
