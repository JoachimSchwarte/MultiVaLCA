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

public class IvariMatrix {
	private IvariScalar[][] m;
	private int size;
	
	public IvariMatrix(int n) {
		m = new IvariScalar[n][n];
		size = n;
	}
	
	public void setValue(int i, int j, IvariScalar s) {
		m[i][j] = s;
	}
	
	public IvariScalar getValue(int i, int j) {
		return m[i][j];
	}

}
