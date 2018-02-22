/*	
 * MultiVaLCA
 */
package de.unistuttgart.iwb.multivalcatest;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.26
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
		Modul1.addFluss(r1, FlowValueType.MeanValue, -1000.);
		Modul1.addFluss(r1, FlowValueType.UpperBound, -950.);
		Modul1.addFluss(r1, FlowValueType.LowerBound, -1050.);
		Modul1.addFluss(r2, FlowValueType.MeanValue, -100.);
		Modul1.addFluss(r2, FlowValueType.UpperBound, -10.);
		Modul1.addFluss(r2, FlowValueType.LowerBound, -250.);
		Modul1.addFluss(e1, FlowValueType.MeanValue, 1000.);
		Modul1.addFluss(e1, FlowValueType.UpperBound, 1200.);
		Modul1.addFluss(e1, FlowValueType.LowerBound, 800.);
		Modul1.addFluss(e2, FlowValueType.MeanValue, 200.);
		Modul1.addFluss(e2, FlowValueType.UpperBound, 250.);
		Modul1.addFluss(e2, FlowValueType.LowerBound, 150.);
		Modul1.addFluss(p1, FlowValueType.MeanValue, 100.);
		Modul2.addFluss(r1, FlowValueType.MeanValue, -300.);
		Modul2.addFluss(r1, FlowValueType.UpperBound, -200.);
		Modul2.addFluss(r1, FlowValueType.LowerBound, -400.);
		Modul2.addFluss(r2, FlowValueType.MeanValue, -800.);
		Modul2.addFluss(r2, FlowValueType.UpperBound, -750.);
		Modul2.addFluss(r2, FlowValueType.LowerBound, -900.);
		Modul2.addFluss(e1, FlowValueType.MeanValue, 150.);
		Modul2.addFluss(e1, FlowValueType.UpperBound, 200.);
		Modul2.addFluss(e1, FlowValueType.LowerBound, 100.);
		Modul2.addFluss(e2, FlowValueType.MeanValue, 500.);
		Modul2.addFluss(e2, FlowValueType.UpperBound, 520.);
		Modul2.addFluss(e2, FlowValueType.LowerBound, 480.);
		Modul2.addFluss(p1, FlowValueType.MeanValue, -300.);
		Modul2.addFluss(p1, FlowValueType.UpperBound, -290.);
		Modul2.addFluss(p1, FlowValueType.LowerBound, -310.);
		Modul2.addFluss(p2, FlowValueType.MeanValue, 1.);
		f.put(p2, 1.);
		ProductP2 = ProductSystem.instance("PS1", f, vkp);
		ProductP2.addProzessmodul(Modul1);
		ProductP2.addProzessmodul(Modul2);
		
	};


	@Test
	void test01() {
		initialize();
		HashMap<Flow, HashMap<FlowValueType, Double>> efv = Modul1.getElementarflussvektor();
		HashMap<FlowValueType, Double> vv = efv.get(r1);
		assertEquals(-1000., vv.get(FlowValueType.MeanValue), .001);
		assertEquals(-950., vv.get(FlowValueType.UpperBound), .001);
		assertEquals(-1050., vv.get(FlowValueType.LowerBound), .001);
	}
	
	@Test
	void test02() {
		initialize();
		HashMap<Flow, HashMap<FlowValueType, Double>> efv = ProductP2.getElementarflussvektor();
		HashMap<FlowValueType, Double> vv = efv.get(r1);
		assertEquals(-3300., vv.get(FlowValueType.MeanValue), .001);
		vv = efv.get(r2);
		assertEquals(-1100., vv.get(FlowValueType.MeanValue), .001);
		vv = efv.get(e1);
		assertEquals(3150., vv.get(FlowValueType.MeanValue), .001);
		vv = efv.get(e2);
		assertEquals(1100., vv.get(FlowValueType.MeanValue), .001);

	}

}
