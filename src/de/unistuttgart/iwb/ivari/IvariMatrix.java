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

public class IvariMatrix {
	private IvariScalar[][] m;
	private int size;
	
	public IvariMatrix(int n) {
		m = new IvariScalar[n][n];
		size = n;
	}
	
	public IvariMatrix(IvariMatrix im) {
		size = im.size;
		m = new IvariScalar[size][size];
		for (int j=0; j<size; j++) {
			for (int k=0; k<size; k++) {
				m[j][k] = im.getValue(j, k);
			}
		}
	}
	
	public int getSize() {
		return this.size;	
	}
	
	public void setValue(int i, int j, IvariScalar s) {
		m[i][j] = s;
	}
	
	public IvariScalar getValue(int i, int j) {
		return m[i][j];
	}
	
	public IvariMatrix multLine(int i, IvariScalar s) {
		IvariMatrix r = new IvariMatrix(this); 
		for (int j=0; j<size; j++) {
			r.setValue(i, j, m[i][j].mult(s));
		}
		return r;
	}
	
	public IvariMatrix changeRows(int i, int j) {
		IvariMatrix r = new IvariMatrix(this);
		for (int k=0; k<size; k++) {
			IvariScalar h = r.getValue(i, k);
			r.setValue(i, k, r.getValue(j, k));
			r.setValue(j, k, h);
		}
		return r;	
	}
	
	// s. https://introcs.cs.princeton.edu/java/95linear/GaussianElimination.java.html
	public IvariVector gauss(IvariVector s) throws Exception {
		IvariVector r = new IvariVector(size);
		for (int p = 0; p < size; p++) {
            // find pivot row and swap
            int max = p;
            for (int i = p + 1; i < size; i++) {
                if (getValue(i,p).abs2() > getValue(max,p).abs2()) {
                    max = i;
                }
            }
            changeRows(p, max);
            s.changeValues(p, max);
            // pivot within A and b
            for (int i = p + 1; i < size; i++) {
            	IvariScalar alpha = getValue(i,p).div(getValue(p,p));
            	s.setValue(i, s.getValue(i).sub(alpha.mult(s.getValue(p))));
                for (int j = p; j < size; j++) {
                	setValue(i, j, getValue(i,j).sub(alpha.mult(getValue(p,j))));
                }
            }
		}	
        // back substitution
        for (int i = size - 1; i >= 0; i--) {
        	IvariScalar sum = new IvariScalar(.0, .0);
            for (int j = i + 1; j < size; j++) {
            	sum = sum.add(getValue(i,j).mult(r.getValue(j)));
            }
            r.setValue(i, s.getValue(i).sub(sum).div(getValue(i,i)));
        }	
		return r;
	}

}
