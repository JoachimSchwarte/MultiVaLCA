/*	
 * MultiVaLCA
 */
package de.unistuttgart.iwb.multivalcatest;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.714
 */

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import de.unistuttgart.iwb.multivalca.FVMGroupType;
import de.unistuttgart.iwb.multivalca.Flow;
import de.unistuttgart.iwb.multivalca.FlowType;
import de.unistuttgart.iwb.multivalca.FlowUnit;
import de.unistuttgart.iwb.multivalca.FlowValueMapGroup;
import de.unistuttgart.iwb.multivalca.MCAObject;
import de.unistuttgart.iwb.multivalca.ProcessModule;
import de.unistuttgart.iwb.multivalca.ProductSystem;
import de.unistuttgart.iwb.multivalca.ValueType;

class ModulGruppenText {
	private Flow e1 = Flow.instance("E1", FlowType.Elementary, FlowUnit.kg);
	private Flow e2 = Flow.instance("E2", FlowType.Elementary, FlowUnit.kg);
	private Flow r1 = Flow.instance("R1", FlowType.Elementary, FlowUnit.kg);
	private Flow r2 = Flow.instance("R2", FlowType.Elementary, FlowUnit.kg);
	private Flow p1 = Flow.instance("P1", FlowType.Product, FlowUnit.kg);
	private Flow p2 = Flow.instance("P2", FlowType.Product, FlowUnit.Items);
	private ProcessModule Modul1 = ProcessModule.instance("M1");
	private ProcessModule Modul2 = ProcessModule.instance("M2");
	private ProcessModule Modul3 = ProcessModule.instance("M3");
	private ProcessModule Modul4 = ProcessModule.instance("M4");
//	private FlowValueMapGroup Group12 = FlowValueMapGroup.getInstance("G12", FVMGroupType.ProcessModule, p1, 1);
	private LinkedHashMap<Flow, Double> f = new LinkedHashMap<Flow, Double>();
	private LinkedList<Flow> vkp = new LinkedList<Flow>();
	private ProductSystem S1 = ProductSystem.instance("S1", f, vkp);
	private ProductSystem S2 = ProductSystem.instance("S2", f, vkp);
	private ProductSystem S3 = ProductSystem.instance("S3", f, vkp);
	private ProductSystem S12 = ProductSystem.instance("S12", f, vkp);
	private ProductSystem S13 = ProductSystem.instance("S13", f, vkp);
	private ProductSystem S23 = ProductSystem.instance("S23", f, vkp);
	private ProductSystem S123 = ProductSystem.instance("S123", f, vkp);
	private ProductSystem S12m1 = ProductSystem.instance("S12m1", f, vkp);
	private ProductSystem S12m2 = ProductSystem.instance("S12m2", f, vkp);
	private ProductSystem S13m1 = ProductSystem.instance("S13m1", f, vkp);
	private ProductSystem S13m3 = ProductSystem.instance("S13m3", f, vkp);
	private ProductSystem S23m2 = ProductSystem.instance("S23m2", f, vkp);
	private ProductSystem S23m3 = ProductSystem.instance("S23m3", f, vkp);
	private ProductSystem S123m1 = ProductSystem.instance("S123m1", f, vkp);
	private ProductSystem S123m2 = ProductSystem.instance("S123m2", f, vkp);
	private ProductSystem S123m3 = ProductSystem.instance("S123m3", f, vkp);
	private ProductSystem S123m12 = ProductSystem.instance("S123m12", f, vkp);
	private ProductSystem S123m13 = ProductSystem.instance("S123m13", f, vkp);
	private ProductSystem S123m23 = ProductSystem.instance("S123m23", f, vkp);
	private ProductSystem S123m123 = ProductSystem.instance("S123m123", f, vkp);
	
	private void init1() {
		MCAObject.clear(ProductSystem.class);
		Modul1.addFluss(r1, ValueType.MeanValue, -250.);
		Modul1.addFluss(r1, ValueType.UpperBound, -249.);
		Modul1.addFluss(r1, ValueType.LowerBound, -251.);
		Modul1.addFluss(r2, ValueType.MeanValue, -150.);
		Modul1.addFluss(r2, ValueType.UpperBound, -149.);
		Modul1.addFluss(r2, ValueType.LowerBound, -151.);
		Modul1.addFluss(e1, ValueType.MeanValue, 200.);
		Modul1.addFluss(e1, ValueType.UpperBound, 201.);
		Modul1.addFluss(e1, ValueType.LowerBound, 199.);
		Modul1.addFluss(e2, ValueType.MeanValue, 300.);
		Modul1.addFluss(e2, ValueType.UpperBound, 301.);
		Modul1.addFluss(e2, ValueType.LowerBound, 299.);
		Modul1.addFluss(p1, ValueType.MeanValue, 100.);
		Modul1.addFluss(p1, ValueType.LowerBound, 100.);
		Modul1.addFluss(p1, ValueType.UpperBound, 100.);
		Modul2.addFluss(r1, ValueType.MeanValue, -100.);
		Modul2.addFluss(r1, ValueType.UpperBound, -99.);
		Modul2.addFluss(r1, ValueType.LowerBound, -101.);
		Modul2.addFluss(r2, ValueType.MeanValue, -100.);
		Modul2.addFluss(r2, ValueType.UpperBound, -99.);
		Modul2.addFluss(r2, ValueType.LowerBound, -101.);
		Modul2.addFluss(e1, ValueType.MeanValue, 80.);
		Modul2.addFluss(e1, ValueType.UpperBound, 81.);
		Modul2.addFluss(e1, ValueType.LowerBound, 79.);
		Modul2.addFluss(e2, ValueType.MeanValue, 200.);
		Modul2.addFluss(e2, ValueType.UpperBound, 201.);
		Modul2.addFluss(e2, ValueType.LowerBound, 199.);
		Modul2.addFluss(p1, ValueType.MeanValue, 50.);
		Modul2.addFluss(p1, ValueType.LowerBound, 50.);
		Modul2.addFluss(p1, ValueType.UpperBound, 50.);
		Modul4.addFluss(r1, ValueType.MeanValue, -500.);
		Modul4.addFluss(r1, ValueType.UpperBound, -499.);
		Modul4.addFluss(r1, ValueType.LowerBound, -501.);
		Modul4.addFluss(r2, ValueType.MeanValue, -300.);
		Modul4.addFluss(r2, ValueType.UpperBound, -299.);
		Modul4.addFluss(r2, ValueType.LowerBound, -301.);
		Modul4.addFluss(e1, ValueType.MeanValue, 600.);
		Modul4.addFluss(e1, ValueType.UpperBound, 601.);
		Modul4.addFluss(e1, ValueType.LowerBound, 599.);
		Modul4.addFluss(e2, ValueType.MeanValue, 400.);
		Modul4.addFluss(e2, ValueType.UpperBound, 401.);
		Modul4.addFluss(e2, ValueType.LowerBound, 399.);
		Modul4.addFluss(p1, ValueType.MeanValue, -100.);
		Modul4.addFluss(p1, ValueType.UpperBound, -99.);
		Modul4.addFluss(p1, ValueType.LowerBound, -101.);
		Modul4.addFluss(p2, ValueType.MeanValue, 1.);
		Modul4.addFluss(p2, ValueType.LowerBound, 1.);
		Modul4.addFluss(p2, ValueType.UpperBound, 1.);
		f.put(p2, 1.);
		S1 = ProductSystem.instance("S1", f, vkp);
		S1.addProzessmodul(Modul1);
		S1.addProzessmodul(Modul4);	
		S2 = ProductSystem.instance("S2", f, vkp);
		S2.addProzessmodul(Modul2);
		S2.addProzessmodul(Modul4);
	}

	@Test
	void testS1() {
		init1();
		LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> efv = S1.getElementarflussvektor();
		LinkedHashMap<ValueType, Double> vv = efv.get(r1);
		assertEquals(-750., vv.get(ValueType.MeanValue), .001);
		assertEquals(-754.51, vv.get(ValueType.LowerBound), .001);
		assertEquals(-745.51, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(r2);
		assertEquals(-450., vv.get(ValueType.MeanValue), .001);
		assertEquals(-453.51, vv.get(ValueType.LowerBound), .001);
		assertEquals(-446.51, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e1);
		assertEquals(800., vv.get(ValueType.MeanValue), .001);
		assertEquals(796.01, vv.get(ValueType.LowerBound), .001);
		assertEquals(804.01, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e2);
		assertEquals(700., vv.get(ValueType.MeanValue), .001);
		assertEquals(695.01, vv.get(ValueType.LowerBound), .001);
		assertEquals(705.01, vv.get(ValueType.UpperBound), .001);
	}
	
	@Test
	void testS2() {
		init1();
		LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> efv = S2.getElementarflussvektor();
		LinkedHashMap<ValueType, Double> vv = efv.get(r1);
		assertEquals(-700., vv.get(ValueType.MeanValue), .001);
		assertEquals(-705.02, vv.get(ValueType.LowerBound), .001);
		assertEquals(-695.02, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(r2);
		assertEquals(-500., vv.get(ValueType.MeanValue), .001);
		assertEquals(-505.02, vv.get(ValueType.LowerBound), .001);
		assertEquals(-495.02, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e1);
		assertEquals(760., vv.get(ValueType.MeanValue), .001);
		assertEquals(755.42, vv.get(ValueType.LowerBound), .001);
		assertEquals(764.62, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e2);
		assertEquals(800., vv.get(ValueType.MeanValue), .001);
		assertEquals(793.02, vv.get(ValueType.LowerBound), .001);
		assertEquals(807.02, vv.get(ValueType.UpperBound), .001);
	}

}
