import java.util.ArrayList;

public class ProfileComponent {
    private String id;
    private ArrayList<ProfileSubComp> compList = new ArrayList<ProfileSubComp>();
    
    public ProfileComponent(String name, ArrayList<ProfileSubComp> ls) {
        this.id = name;
        for (ProfileSubComp pSC : ls) this.compList.add(pSC);
    }

    public String getId() {
        return id;
    }

    public ArrayList<ProfileSubComp> getCompList() {
        return compList;
    }

    public String[] getCompListArray() {
        return (String[]) compList.toArray();
    }
}
