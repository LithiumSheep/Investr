public class IndustryData extends Data{
    public String name;

    public IndustryData(String name, double pE, double rOE, double dY, double dE, double pB,
            double nPM, double pFCF) {
        super(pE, rOE, dY, dE, pB, nPM, pFCF);
        this.name = name;
    }
    
    public String getName() {
        return name;
    }   
}