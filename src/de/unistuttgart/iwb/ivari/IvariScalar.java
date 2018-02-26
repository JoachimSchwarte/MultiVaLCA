package de.unistuttgart.iwb.ivari;

public class IvariScalar {
	private double lowerBound;
	private double upperBound;
	
	public IvariScalar(double lowerBound, double upperBound) {
		super();
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}
	
	public IvariScalar multiply(IvariScalar s, double f) {
		s.lowerBound = s.lowerBound*f;
		s.upperBound = s.upperBound*f;
		return s;
	}
	
}
