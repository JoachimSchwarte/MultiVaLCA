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

public class IvariMatrix {
	private IvariScalar[][] m;
	private int zeilAnz;
	private int spalAnz;
	
	public IvariMatrix(int n) {
		m = new IvariScalar[n][n];
		zeilAnz = n;
		spalAnz = n;
	}
	
	public IvariMatrix(double[][] a, double[][] b) {
		zeilAnz = a.length;
		spalAnz = a[0].length;
		System.out.println("size= "+zeilAnz);
		m = new IvariScalar[zeilAnz][spalAnz];
		for (int i=0; i<zeilAnz; i++) {
			for (int j=0; j<spalAnz; j++) {
				System.out.println("i= "+i+"  j= "+j+"  l= "+a[i][j]+"  u= "+ b[i][j]);
				IvariScalar s = new IvariScalar(a[i][j], b[i][j]);
				m[i][j] = s;
			}
		}
	}
	
	public IvariMatrix(IvariMatrix im) {
		zeilAnz = im.zeilAnz;
		spalAnz = im.spalAnz;
		m = new IvariScalar[zeilAnz][zeilAnz];
		for (int j=0; j<zeilAnz; j++) {
			for (int k=0; k<zeilAnz; k++) {
				m[j][k] = im.getValue(j, k);
			}
		}
	}
	
	public int getSize() {
		return this.zeilAnz;	
	}
	
	public void setValue(int i, int j, IvariScalar s) {
		m[i][j] = s;
	}
	
	public IvariScalar getValue(int i, int j) {
		return m[i][j];
	}
	
	public IvariMatrix multLine(int i, IvariScalar s) {
		IvariMatrix r = new IvariMatrix(this); 
		for (int j=0; j<zeilAnz; j++) {
			r.setValue(i, j, m[i][j].mult(s));
		}
		return r;
	}
	
	public IvariVector multVector(IvariVector v) {
		IvariVector r = new IvariVector(spalAnz); 
		for (int j=0; j<zeilAnz; j++) {
			r.setValue(j, new IvariScalar(0., 0.));
			for (int k=0; k<spalAnz; k++) {
				r.setValue(j, r.getValue(j).add(getValue(j,k).mult(v.getValue(k))));
			}
		}
		return r;
	}
	
	public IvariMatrix changeRows(int i, int j) {
		IvariMatrix r = new IvariMatrix(this);
		for (int k=0; k<zeilAnz; k++) {
			IvariScalar h = r.getValue(i, k);
			r.setValue(i, k, r.getValue(j, k));
			r.setValue(j, k, h);
		}
		return r;	
	}
	
	// s. https://introcs.cs.princeton.edu/java/95linear/GaussianElimination.java.html
	public IvariVector gauss(IvariVector s) throws Exception {
		IvariVector r = new IvariVector(zeilAnz);
		for (int p = 0; p < zeilAnz; p++) {
            // find pivot row and swap
            int max = p;
            for (int i = p + 1; i < zeilAnz; i++) {
                if (getValue(i,p).abs2() > getValue(max,p).abs2()) {
                    max = i;
                }
            }
            changeRows(p, max);
            s.changeValues(p, max);
            // pivot within A and b
            for (int i = p + 1; i < zeilAnz; i++) {
            	IvariScalar alpha = getValue(i,p).div(getValue(p,p));
            	s.setValue(i, s.getValue(i).sub(alpha.mult(s.getValue(p))));
                for (int j = p; j < zeilAnz; j++) {
                	setValue(i, j, getValue(i,j).sub(alpha.mult(getValue(p,j))));
                }
            }
		}	
        // back substitution
        for (int i = zeilAnz - 1; i >= 0; i--) {
        	IvariScalar sum = new IvariScalar(.0, .0);
            for (int j = i + 1; j < zeilAnz; j++) {
            	sum = sum.add(getValue(i,j).mult(r.getValue(j)));
            }
            r.setValue(i, s.getValue(i).sub(sum).div(getValue(i,i)));
        }	
		return r;
	}

}
