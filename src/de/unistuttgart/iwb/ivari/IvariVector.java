/*	
 * Ivari
 * (Intervallarithmetik
 * für MultiVaLCA)
 */

package de.unistuttgart.iwb.ivari;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.02
 */

public class IvariVector {
	private IvariScalar[] v;
	private int size;
	
	public IvariVector(int n) {
		v = new IvariScalar[n];
		size = n;
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

}
