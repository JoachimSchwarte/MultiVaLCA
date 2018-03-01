/*	
 * Ivari
 * (Intervallarithmetik
 * für MultiVaLCA)
 */

package de.unistuttgart.iwb.ivari;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.04
 */

public class IvariScalar {
	private double lowerBound;
	private double upperBound;
	
	public IvariScalar() {
		super();
	}
	
	public IvariScalar(double lowerBound, double upperBound) {
		super();
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}
	
	public IvariScalar add(IvariScalar s) {
		IvariScalar r = new IvariScalar();
		r.lowerBound = this.lowerBound + s.lowerBound;
		r.upperBound = this.upperBound + s.upperBound;
		return r;
	}
	
	public IvariScalar sub(IvariScalar s) {
		IvariScalar r = new IvariScalar();
		r.lowerBound = this.lowerBound - s.upperBound;
		r.upperBound = this.upperBound - s.lowerBound;
		return r;
	}
	
	public IvariScalar mult(double f) {
		IvariScalar r = new IvariScalar();
		if (f>0) {
			r.lowerBound = this.lowerBound*f;
			r.upperBound = this.upperBound*f;
		} else {
			r.lowerBound = this.upperBound*f;
			r.upperBound = this.lowerBound*f;
		}		
		return r;
	}
	
	public IvariScalar mult(IvariScalar s) {
		IvariScalar r = new IvariScalar();
		IvariScalar p = this.mult(s.lowerBound);
		IvariScalar q = this.mult(s.upperBound);
		r=q;
		if (p.lowerBound < q.lowerBound) {
			r.setLowerBound(p.lowerBound);			
		}
		if (p.upperBound > q.upperBound) {
			r.setUpperBound(p.upperBound);			
		}
		return r;		
	}
	
	public IvariScalar inv() throws Exception {
		IvariScalar r = new IvariScalar(1.,1.);
		if (this.upperBound*this.lowerBound <= 0) {
			throw new Exception("Fehler bei Kehrwertberechnung");
		} else {
			r.setLowerBound(1/this.upperBound);
			r.setUpperBound(1/this.lowerBound);
		}		
		return r;
	}
	
	public IvariScalar div(IvariScalar s) throws Exception  {
		IvariScalar r = this.mult(s.inv());
		return r;
	}
	
	public double abs2() throws Exception  {
		double r = this.lowerBound*this.upperBound;
		if (r < 0) {
			throw new Exception("Fehler bei der Absolutwertberechnung");
		}
		return r;	
	}

	public double getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(double lowerBound) {
		this.lowerBound = lowerBound;
	}

	public double getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(double upperBound) {
		this.upperBound = upperBound;
	}
		
}