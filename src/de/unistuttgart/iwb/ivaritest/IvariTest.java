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
 * @version 0.01
 */

class IvariTest {
	IvariScalar a = new IvariScalar(2.4, 3.6);
	IvariScalar b = new IvariScalar(4.1, 5.2);
	IvariScalar c = new IvariScalar(-1.1, 1.2);
	IvariScalar d = new IvariScalar(-3.5, -1.5);	
	IvariVector v = new IvariVector(2);	

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
		assertEquals(a.div(b).getLowerBound(), .461538, .000001);
		assertEquals(a.div(b).getUpperBound(), .878048, .000001);
		assertEquals(a.div(c).getLowerBound(), -3.272727, .000001);
		assertEquals(a.div(c).getUpperBound(), 3., .000001);
		assertEquals(a.div(d).getLowerBound(), -2.4, .000001);
		assertEquals(a.div(d).getUpperBound(), -.685714, .000001);
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



}
