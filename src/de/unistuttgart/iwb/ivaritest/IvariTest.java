/*	
 * Ivari
 * (Intervallarithmetik
 * für MultiVaLCA)
 */

package de.unistuttgart.iwb.ivaritest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import de.unistuttgart.iwb.ivari.*;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.05
 */

class IvariTest {
	IvariScalar a = new IvariScalar(2.4, 3.6);
	IvariScalar b = new IvariScalar(4.1, 5.2);
	IvariScalar c = new IvariScalar(-1.1, 1.2);
	IvariScalar d = new IvariScalar(-3.5, -1.5);
	IvariScalar e = new IvariScalar(1., 2.);
	IvariVector v = new IvariVector(2);	
	IvariMatrix m = new IvariMatrix(2);	

	@Test
	void testAdd() {
		assertEquals(a.add(b).getLowerBound(), 6.5, .001);
		assertEquals(a.add(b).getUpperBound(), 8.8, .001);
		assertEquals(a.add(c).getLowerBound(), 1.3, .001);
		assertEquals(a.add(c).getUpperBound(), 4.8, .001);
		assertEquals(a.add(d).getLowerBound(), -1.1, .001);
		assertEquals(a.add(d).getUpperBound(), 2.1, .001);
	}
	
	@Test
	void testSub() {
		assertEquals(a.sub(b).getLowerBound(), -2.8, .001);
		assertEquals(a.sub(b).getUpperBound(), -0.5, .001);
		assertEquals(a.sub(c).getLowerBound(), 1.2, .001);
		assertEquals(a.sub(c).getUpperBound(), 4.7, .001);
		assertEquals(a.sub(d).getLowerBound(), 3.9, .001);
		assertEquals(a.sub(d).getUpperBound(), 7.1, .001);
	}
	
	@Test
	void testMult() {
		assertEquals(a.mult(b).getLowerBound(), 9.84, .001);
		assertEquals(a.mult(b).getUpperBound(), 18.72, .001);
		assertEquals(a.mult(c).getLowerBound(), -3.96, .001);
		assertEquals(a.mult(c).getUpperBound(), 4.32, .001);
		assertEquals(a.mult(d).getLowerBound(), -12.6, .001);
		assertEquals(a.mult(d).getUpperBound(), -3.6, .001);
	}
	
	@Test
	void testDiv() {
		try {
			assertEquals(a.div(b).getLowerBound(), .461538, .000001);
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("?"));
		}
		try {
			assertEquals(a.div(b).getUpperBound(), .878048, .000001);
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("?"));
		}
		try {
			assertEquals(a.div(c).getLowerBound(), -3.272727, .000001);
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("Fehler bei Kehrwertberechnung"));
		}
		try {
			assertEquals(a.div(c).getUpperBound(), 3., .000001);
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("Fehler bei Kehrwertberechnung"));
		}
		try {
			assertEquals(a.div(d).getLowerBound(), -2.4, .000001);
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("?"));
		}
		try {
			assertEquals(a.div(d).getUpperBound(), -.685714, .000001);
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("?"));
		}
	}
	
	@Test
	void testAbs() {
		try {
			assertEquals(a.abs2(), 8.64, .001);
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("?"));
		}
		try {
			assertEquals(c.abs2(), -1.32, .001);
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("Fehler bei der Absolutwertberechnung"));
		}
	}
	
	@Test
	void testVMult() {
		v.setValue(0, a);
		v.setValue(1, b);
		IvariVector w = v.mult(c);
		assertEquals(w.getValue(0).getLowerBound(), -3.96, .001);
		assertEquals(w.getValue(0).getUpperBound(), 4.32, .001);
		assertEquals(w.getValue(1).getLowerBound(), -5.72, .001);
		assertEquals(w.getValue(1).getUpperBound(), 6.24, .001);		
	}
	
	@Test
	void testMultLine() {
		m.setValue(0, 0, a);
		m.setValue(0, 1, b);
		m.setValue(1, 0, c);
		m.setValue(1, 1, d);
		IvariMatrix m1 = m.multLine(0, e);
		assertEquals(m.getValue(0, 0).getLowerBound(), 2.4, 0.001);
		assertEquals(m.getValue(0, 0).getUpperBound(), 3.6, 0.001);
		assertEquals(m.getValue(0, 1).getLowerBound(), 4.1, 0.001);
		assertEquals(m.getValue(0, 1).getUpperBound(), 5.2, 0.001);
		assertEquals(m1.getValue(0, 0).getLowerBound(), 2.4, 0.001);
		assertEquals(m1.getValue(0, 0).getUpperBound(), 7.2, 0.001);
		assertEquals(m1.getValue(0, 1).getLowerBound(), 4.1, 0.001);
		assertEquals(m1.getValue(0, 1).getUpperBound(), 10.4, 0.001);
		assertEquals(m1.getValue(1, 0).getLowerBound(), -1.1, 0.001);
		assertEquals(m1.getValue(1, 0).getUpperBound(), 1.2, 0.001);
		assertEquals(m1.getValue(1, 1).getLowerBound(), -3.5, 0.001);
		assertEquals(m1.getValue(1, 1).getUpperBound(), -1.5, 0.001);		
	}
	
	@Test
	void testMultVector() throws Exception {
		IvariScalar a11 = new IvariScalar(100., 200.);
		IvariScalar a12 = new IvariScalar(1., 1.);
		IvariScalar a21 = new IvariScalar(1., 1.);
		IvariScalar a22 = new IvariScalar(100., 300.);
		IvariScalar b1 = new IvariScalar(1., 2.);
		IvariScalar b2 = new IvariScalar(2., 6.);	
		IvariMatrix ma = new IvariMatrix(2);
		IvariVector vb = new IvariVector(2);
		IvariVector vx = new IvariVector(2);
		ma.setValue(0, 0, a11);
		ma.setValue(0, 1, a12);
		ma.setValue(1, 0, a21);
		ma.setValue(1, 1, a22);
		vb.setValue(0, b1);
		vb.setValue(1, b2);
		vx = ma.multVector(vb);
		assertEquals(vx.getValue(0).getLowerBound(), 102., .001);
		assertEquals(vx.getValue(0).getUpperBound(), 406., .001);
		assertEquals(vx.getValue(1).getLowerBound(), 201., .001);
		assertEquals(vx.getValue(1).getUpperBound(), 1802., .001);
		
	}
	
	@Test
	void testGauss01() throws Exception {
		IvariScalar a11 = new IvariScalar(5., 5.);
		IvariScalar a12 = new IvariScalar(4., 4.);
		IvariScalar a13 = new IvariScalar(3., 3.);
		IvariScalar a21 = new IvariScalar(2., 2.);
		IvariScalar a22 = new IvariScalar(7., 7.);
		IvariScalar a23 = new IvariScalar(1., 1.);
		IvariScalar a31 = new IvariScalar(4., 4.);
		IvariScalar a32 = new IvariScalar(3., 3.);
		IvariScalar a33 = new IvariScalar(2., 2.);
		IvariScalar b1 = new IvariScalar(22., 22.);
		IvariScalar b2 = new IvariScalar(19., 19.);
		IvariScalar b3 = new IvariScalar(16., 16.);			
		IvariMatrix ma = new IvariMatrix(3);
		IvariVector vb = new IvariVector(3);
		IvariVector vx = new IvariVector(3);
		ma.setValue(0, 0, a11);
		ma.setValue(0, 1, a12);
		ma.setValue(0, 2, a13);
		ma.setValue(1, 0, a21);
		ma.setValue(1, 1, a22);
		ma.setValue(1, 2, a23);
		ma.setValue(2, 0, a31);
		ma.setValue(2, 1, a32);
		ma.setValue(2, 2, a33);
		vb.setValue(0, b1);
		vb.setValue(1, b2);
		vb.setValue(2, b3);
		vx = ma.gauss(vb);
		assertEquals(vx.getValue(0).getLowerBound(), 1., .001);
		assertEquals(vx.getValue(0).getUpperBound(), 1., .001);
		assertEquals(vx.getValue(1).getLowerBound(), 2., .001);
		assertEquals(vx.getValue(1).getUpperBound(), 2., .001);
		assertEquals(vx.getValue(2).getLowerBound(), 3., .001);
		assertEquals(vx.getValue(2).getUpperBound(), 3., .001);
	}
	
	@Test
	void testGauss02() throws Exception {
		IvariScalar a11 = new IvariScalar(7., 7.);
		IvariScalar a12 = new IvariScalar(4., 4.);
		IvariScalar a13 = new IvariScalar(3., 3.);
		IvariScalar a21 = new IvariScalar(2., 2.);
		IvariScalar a22 = new IvariScalar(7., 7.);
		IvariScalar a23 = new IvariScalar(1., 1.);
		IvariScalar a31 = new IvariScalar(4., 4.);
		IvariScalar a32 = new IvariScalar(3., 3.);
		IvariScalar a33 = new IvariScalar(2., 2.);
		IvariScalar b1 = new IvariScalar(22., 22.);
		IvariScalar b2 = new IvariScalar(19., 19.);
		IvariScalar b3 = new IvariScalar(16., 16.);			
		IvariMatrix ma = new IvariMatrix(3);
		IvariVector vb = new IvariVector(3);
		IvariVector vx = new IvariVector(3);
		ma.setValue(0, 0, a11);
		ma.setValue(0, 1, a12);
		ma.setValue(0, 2, a13);
		ma.setValue(1, 0, a21);
		ma.setValue(1, 1, a22);
		ma.setValue(1, 2, a23);
		ma.setValue(2, 0, a31);
		ma.setValue(2, 1, a32);
		ma.setValue(2, 2, a33);
		vb.setValue(0, b1);
		vb.setValue(1, b2);
		vb.setValue(2, b3);
		vx = ma.gauss(vb);
		assertEquals(vx.getValue(0).getLowerBound(), -1., .001);
		assertEquals(vx.getValue(0).getUpperBound(), -1., .001);
		assertEquals(vx.getValue(1).getLowerBound(), 2, .001);
		assertEquals(vx.getValue(1).getUpperBound(), 2, .001);
		assertEquals(vx.getValue(2).getLowerBound(), 7, .001);
		assertEquals(vx.getValue(2).getUpperBound(), 7, .001);	
	}
	
	@Test
	void testGauss03() throws Exception {
		IvariScalar a11 = new IvariScalar(4., 4.);
		IvariScalar a12 = new IvariScalar(4., 4.);
		IvariScalar a13 = new IvariScalar(3., 3.);
		IvariScalar a21 = new IvariScalar(2., 2.);
		IvariScalar a22 = new IvariScalar(7., 7.);
		IvariScalar a23 = new IvariScalar(1., 1.);
		IvariScalar a31 = new IvariScalar(4., 4.);
		IvariScalar a32 = new IvariScalar(3., 3.);
		IvariScalar a33 = new IvariScalar(2., 2.);
		IvariScalar b1 = new IvariScalar(22., 22.);
		IvariScalar b2 = new IvariScalar(19., 19.);
		IvariScalar b3 = new IvariScalar(16., 16.);			
		IvariMatrix ma = new IvariMatrix(3);
		IvariVector vb = new IvariVector(3);
		IvariVector vx = new IvariVector(3);
		ma.setValue(0, 0, a11);
		ma.setValue(0, 1, a12);
		ma.setValue(0, 2, a13);
		ma.setValue(1, 0, a21);
		ma.setValue(1, 1, a22);
		ma.setValue(1, 2, a23);
		ma.setValue(2, 0, a31);
		ma.setValue(2, 1, a32);
		ma.setValue(2, 2, a33);
		vb.setValue(0, b1);
		vb.setValue(1, b2);
		vb.setValue(2, b3);
		vx = ma.gauss(vb);
		assertEquals(vx.getValue(0).getLowerBound(), 0.5, .001);
		assertEquals(vx.getValue(0).getUpperBound(), 0.5, .001);
		assertEquals(vx.getValue(1).getLowerBound(), 2, .001);
		assertEquals(vx.getValue(1).getUpperBound(), 2, .001);
		assertEquals(vx.getValue(2).getLowerBound(), 4, .001);
		assertEquals(vx.getValue(2).getUpperBound(), 4, .001);	
	}
	
	@Test
	void testGauss04() throws Exception {
		IvariScalar a11 = new IvariScalar(1., 2.);
		IvariScalar a12 = new IvariScalar(0., 0.);
		IvariScalar a21 = new IvariScalar(0., 0.);
		IvariScalar a22 = new IvariScalar(1., 3.);
		IvariScalar b1 = new IvariScalar(2., 2.);
		IvariScalar b2 = new IvariScalar(6., 6.);	
		IvariMatrix ma = new IvariMatrix(2);
		IvariVector vb = new IvariVector(2);
		IvariVector vx = new IvariVector(2);
		ma.setValue(0, 0, a11);
		ma.setValue(0, 1, a12);
		ma.setValue(1, 0, a21);
		ma.setValue(1, 1, a22);
		vb.setValue(0, b1);
		vb.setValue(1, b2);
		vx = ma.gauss(vb);
		assertEquals(vx.getValue(0).getLowerBound(), 1., .001);
		assertEquals(vx.getValue(0).getUpperBound(), 2., .001);
		assertEquals(vx.getValue(1).getLowerBound(), 2., .001);
		assertEquals(vx.getValue(1).getUpperBound(), 6., .001);
	}
	
	@Test
	void testGauss05() throws Exception {
		IvariScalar a11 = new IvariScalar(100., 200.);
		IvariScalar a12 = new IvariScalar(1., 1.);
		IvariScalar a21 = new IvariScalar(1., 1.);
		IvariScalar a22 = new IvariScalar(100., 300.);
		IvariScalar b1 = new IvariScalar(200., 200.);
		IvariScalar b2 = new IvariScalar(600., 600.);	
		IvariMatrix ma = new IvariMatrix(2);
		IvariVector vb = new IvariVector(2);
		IvariVector vx = new IvariVector(2);
		ma.setValue(0, 0, a11);
		ma.setValue(0, 1, a12);
		ma.setValue(1, 0, a21);
		ma.setValue(1, 1, a22);
		vb.setValue(0, b1);
		vb.setValue(1, b2);
		vx = ma.gauss(vb);
		assertEquals(vx.getValue(0).getLowerBound(), 0.97004, .001);
		assertEquals(vx.getValue(0).getUpperBound(), 1.98007, .001);
		assertEquals(vx.getValue(1).getLowerBound(), 1.99337, .001);
		assertEquals(vx.getValue(1).getUpperBound(), 5.99059, .001);
	}
	
	@Test
	void testGauss06() throws Exception {
		IvariScalar a11 = new IvariScalar(100., 200.);
		IvariScalar a12 = new IvariScalar(1., 1.);
		IvariScalar a21 = new IvariScalar(1., 1.);
		IvariScalar a22 = new IvariScalar(100., 300.);
		IvariScalar b1 = new IvariScalar(200., 200.);
		IvariScalar b2 = new IvariScalar(600., 600.);	
		IvariMatrix ma = new IvariMatrix(2);
		IvariVector vb = new IvariVector(2);
		IvariVector vx = new IvariVector(2);
		ma.setValue(1, 1, a11);
		ma.setValue(1, 0, a12);
		ma.setValue(0, 1, a21);
		ma.setValue(0, 0, a22);
		vb.setValue(1, b1);
		vb.setValue(0, b2);
		vx = ma.gauss(vb);
		assertEquals(vx.getValue(1).getLowerBound(), 0.97004, .001);
		assertEquals(vx.getValue(1).getUpperBound(), 1.98007, .001);
		assertEquals(vx.getValue(0).getLowerBound(), 1.99337, .001);
		assertEquals(vx.getValue(0).getUpperBound(), 5.99059, .001);
	}
}
