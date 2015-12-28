package com.oose2015.group15.invest.datatools;

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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		IndustryData other = (IndustryData) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}	
}
