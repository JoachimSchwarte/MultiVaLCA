/*	
 * Ivari
 * (Intervallarithmetik
 * für MultiVaLCA)
 */

package de.unistuttgart.iwb.ivari;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.03
 */

public class IvariVector {
	private IvariScalar[] v;
	private int size;
	
	public IvariVector(int n) {
		v = new IvariScalar[n];
		size = n;
		for (int j=0; j<size; j++) {
			v[j] = new IvariScalar(.0, .0);
		}
	}
	
	public IvariVector(IvariVector iv) {		
		size = iv.size;
		v = new IvariScalar[size];
		for (int j=0; j<size; j++) {
			v[j] = iv.getValue(j);
		}
	}
	
	public int getSize() {
		return this.size;	
	}
	
	public void setValue(int i, IvariScalar s) {
		v[i] = s;
	}
	
	public IvariScalar getValue(int i) {
		return v[i];
	}

	public IvariVector mult(IvariScalar s) {
		IvariVector r = new IvariVector(size); 
		for (int i=0; i<size; i++) {
			r.setValue(i,v[i].mult(s));
		}
		return r;
	}
	
	public IvariVector changeValues(int i, int j) {
		IvariVector r = new IvariVector(this);
		IvariScalar h = r.getValue(i);
		r.setValue(i, r.getValue(j));
		r.setValue(j, h);		
		return r;
	}

}
