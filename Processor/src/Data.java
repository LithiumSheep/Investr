public class Data {
    private double PE;
    private double ROE;
    private double DY;
    private double DE;
    private double PB;
    private double NPM;
    private double PFCF;
    
    
    public Data(double pE, double rOE, double dY, double dE, double pB,
            double nPM, double pFCF) {
        super();
        PE = pE;
        ROE = rOE;
        DY = dY;
        DE = dE;
        PB = pB;
        NPM = nPM;
        PFCF = pFCF;
    }
    
    public double getPE() {
        return PE;
    }
    
    public double getROE() {
        return ROE;
    }
    
    public double getDY() {
        return DY;
    }
    
    public double getDE() {
        return DE;
    }
    
    public double getPB() {
        return PB;
    }
    
    public double getNPM() {
        return NPM;
    }
    
    public double getPFCF() {
        return PFCF;
    }
}