/*	
 * MultiVaLCA
 */
package de.unistuttgart.iwb.multivalcatest;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.28
 */

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import de.unistuttgart.iwb.multivalca.*;

class ProductSystemTest {
	Flow e1 = Flow.instance("E1", FlowType.Elementary, FlowUnit.kg);
	Flow e2 = Flow.instance("E2", FlowType.Elementary, FlowUnit.kg);
	Flow r1 = Flow.instance("R1", FlowType.Elementary, FlowUnit.kg);
	Flow r2 = Flow.instance("R2", FlowType.Elementary, FlowUnit.kg);
	Flow p1 = Flow.instance("P1", FlowType.Product, FlowUnit.kg);
	Flow p2 = Flow.instance("P2", FlowType.Product, FlowUnit.Items);
	ProcessModule Modul1 = ProcessModule.instance("M1");
	ProcessModule Modul2 = ProcessModule.instance("M2");
	HashMap<Flow, Double> f = new HashMap<Flow, Double>();
	LinkedList<Flow> vkp = new LinkedList<Flow>();
	ProductSystem ProductP2 = ProductSystem.instance("PS1", f, vkp);
	private void initialize() {
		ProductSystem.clear();
		Modul1.addFluss(r1, ValueType.MeanValue, -1000.);
		Modul1.addFluss(r1, ValueType.UpperBound, -950.);
		Modul1.addFluss(r1, ValueType.LowerBound, -1050.);
		Modul1.addFluss(r2, ValueType.MeanValue, -100.);
		Modul1.addFluss(r2, ValueType.UpperBound, -10.);
		Modul1.addFluss(r2, ValueType.LowerBound, -250.);
		Modul1.addFluss(e1, ValueType.MeanValue, 1000.);
		Modul1.addFluss(e1, ValueType.UpperBound, 1200.);
		Modul1.addFluss(e1, ValueType.LowerBound, 800.);
		Modul1.addFluss(e2, ValueType.MeanValue, 200.);
		Modul1.addFluss(e2, ValueType.UpperBound, 250.);
		Modul1.addFluss(e2, ValueType.LowerBound, 150.);
		Modul1.addFluss(p1, ValueType.MeanValue, 100.);
		Modul1.addFluss(p1, ValueType.LowerBound, 100.);
		Modul1.addFluss(p1, ValueType.UpperBound, 100.);
		Modul2.addFluss(r1, ValueType.MeanValue, -300.);
		Modul2.addFluss(r1, ValueType.UpperBound, -200.);
		Modul2.addFluss(r1, ValueType.LowerBound, -400.);
		Modul2.addFluss(r2, ValueType.MeanValue, -800.);
		Modul2.addFluss(r2, ValueType.UpperBound, -750.);
		Modul2.addFluss(r2, ValueType.LowerBound, -900.);
		Modul2.addFluss(e1, ValueType.MeanValue, 150.);
		Modul2.addFluss(e1, ValueType.UpperBound, 200.);
		Modul2.addFluss(e1, ValueType.LowerBound, 100.);
		Modul2.addFluss(e2, ValueType.MeanValue, 500.);
		Modul2.addFluss(e2, ValueType.UpperBound, 520.);
		Modul2.addFluss(e2, ValueType.LowerBound, 480.);
		Modul2.addFluss(p1, ValueType.MeanValue, -300.);
		Modul2.addFluss(p1, ValueType.UpperBound, -290.);
		Modul2.addFluss(p1, ValueType.LowerBound, -310.);
		Modul2.addFluss(p2, ValueType.MeanValue, 1.);
		Modul2.addFluss(p2, ValueType.LowerBound, 1.);
		Modul2.addFluss(p2, ValueType.UpperBound, 1.);
		f.put(p2, 1.);
		ProductP2 = ProductSystem.instance("PS1", f, vkp);
		ProductP2.addProzessmodul(Modul1);
		ProductP2.addProzessmodul(Modul2);
		
	};


	@Test
	void test01() {
		initialize();
		HashMap<Flow, HashMap<ValueType, Double>> efv = Modul1.getElementarflussvektor();
		HashMap<ValueType, Double> vv = efv.get(r1);
		assertEquals(-1000., vv.get(ValueType.MeanValue), .001);
		assertEquals(-950., vv.get(ValueType.UpperBound), .001);
		assertEquals(-1050., vv.get(ValueType.LowerBound), .001);
	}
	
	@Test
	void test02() {
		initialize();
		HashMap<Flow, HashMap<ValueType, Double>> efv = ProductP2.getElementarflussvektor();
		HashMap<ValueType, Double> vv = efv.get(r1);
		assertEquals(-3300., vv.get(ValueType.MeanValue), .001);
		assertEquals(-3655., vv.get(ValueType.LowerBound), .001);
		assertEquals(-2955., vv.get(ValueType.UpperBound), .001);
		vv = efv.get(r2);
		assertEquals(-1100., vv.get(ValueType.MeanValue), .001);
		assertEquals(-1675., vv.get(ValueType.LowerBound), .001);
		assertEquals(-779., vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e1);
		assertEquals(3150., vv.get(ValueType.MeanValue), .001);
		assertEquals(2420., vv.get(ValueType.LowerBound), .001);
		assertEquals(3920., vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e2);
		assertEquals(1100., vv.get(ValueType.MeanValue), .001);
		assertEquals(915., vv.get(ValueType.LowerBound), .001);
		assertEquals(1295., vv.get(ValueType.UpperBound), .001);

	}

}
