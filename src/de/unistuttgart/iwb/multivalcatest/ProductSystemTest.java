/*	
 * MultiVaLCA
 */
package de.unistuttgart.iwb.multivalcatest;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.24
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
		Modul1.addFluss(r1, FlowValueType.UpperBound, -1050.);
		Modul1.addFluss(r1, FlowValueType.LowerBound, -950.);
		
	};


	@Test
	void test01() {
		initialize();
		HashMap<Flow, HashMap<FlowValueType, Double>> efv = Modul1.getElementarflussvektor();
		HashMap<FlowValueType, Double> vv = efv.get(r1);
		assertEquals(-1000., vv.get(FlowValueType.MeanValue), .001);
		assertEquals(-1050., vv.get(FlowValueType.UpperBound), .001);
		assertEquals(-950., vv.get(FlowValueType.LowerBound), .001);
	}

}
