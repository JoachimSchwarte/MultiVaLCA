/*	
 * MultiVaLCA
 */
package de.unistuttgart.iwb.multivalcatest;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.716
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

class ModuleGroupTest {
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
//	private ProductSystem S123m2 = ProductSystem.instance("S123m2", f, vkp);
//	private ProductSystem S123m3 = ProductSystem.instance("S123m3", f, vkp);
//	private ProductSystem S123m12 = ProductSystem.instance("S123m12", f, vkp);
//	private ProductSystem S123m13 = ProductSystem.instance("S123m13", f, vkp);
//	private ProductSystem S123m23 = ProductSystem.instance("S123m23", f, vkp);
	
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
		Modul3.addFluss(r1, ValueType.MeanValue, -300.);
		Modul3.addFluss(r1, ValueType.UpperBound, -299.);
		Modul3.addFluss(r1, ValueType.LowerBound, -301.);
		Modul3.addFluss(r2, ValueType.MeanValue, -120.);
		Modul3.addFluss(r2, ValueType.UpperBound, -119.);
		Modul3.addFluss(r2, ValueType.LowerBound, -121.);
		Modul3.addFluss(e1, ValueType.MeanValue, 150.);
		Modul3.addFluss(e1, ValueType.UpperBound, 151.);
		Modul3.addFluss(e1, ValueType.LowerBound, 149.);
		Modul3.addFluss(e2, ValueType.MeanValue, 450.);
		Modul3.addFluss(e2, ValueType.UpperBound, 451.);
		Modul3.addFluss(e2, ValueType.LowerBound, 449.);
		Modul3.addFluss(p1, ValueType.MeanValue, 150.);
		Modul3.addFluss(p1, ValueType.LowerBound, 150.);
		Modul3.addFluss(p1, ValueType.UpperBound, 150.);
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
	}

	@Test
	void testS1() {
		init1();
		S1.addProzessmodul(Modul1);
		S1.addProzessmodul(Modul4);	
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
		S2.addProzessmodul(Modul2);
		S2.addProzessmodul(Modul4);
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
	
	@Test
	void testS3() {
		init1();
		S3.addProzessmodul(Modul3);
		S3.addProzessmodul(Modul4);
		LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> efv = S3.getElementarflussvektor();
		LinkedHashMap<ValueType, Double> vv = efv.get(r1);
		assertEquals(-700., vv.get(ValueType.MeanValue), .001);
		assertEquals(-703.6733, vv.get(ValueType.LowerBound), .001);
		assertEquals(-696.34, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(r2);
		assertEquals(-380., vv.get(ValueType.MeanValue), .001);
		assertEquals(-382.4733, vv.get(ValueType.LowerBound), .001);
		assertEquals(-377.54, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e1);
		assertEquals(700., vv.get(ValueType.MeanValue), .001);
		assertEquals(697.34, vv.get(ValueType.LowerBound), .001);
		assertEquals(702.6733, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e2);
		assertEquals(700., vv.get(ValueType.MeanValue), .001);
		assertEquals(695.34, vv.get(ValueType.LowerBound), .001);
		assertEquals(704.6733, vv.get(ValueType.UpperBound), .001);
	}
	
	@Test
	void testS12() {
		init1();
		FlowValueMapGroup.createInstance("G12", FVMGroupType.ProcessModule, p1, 1);
		FlowValueMapGroup Group12 = FlowValueMapGroup.getInstance("G12");
		Group12.addFlowValueMap(Modul1);
		Group12.addFlowValueMap(Modul2);
		S12.addProzessmodul(Group12);
		S12.addProzessmodul(Modul4);	
		LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> efv = S12.getElementarflussvektor();
		LinkedHashMap<ValueType, Double> vv = efv.get(r1);
		assertEquals(-725., vv.get(ValueType.MeanValue), .001);
		assertEquals(-754.51, vv.get(ValueType.LowerBound), .001);
		assertEquals(-695.02, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(r2);
		assertEquals(-475., vv.get(ValueType.MeanValue), .001);
		assertEquals(-505.02, vv.get(ValueType.LowerBound), .001);
		assertEquals(-446.51, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e1);
		assertEquals(780., vv.get(ValueType.MeanValue), .001);
		assertEquals(755.42, vv.get(ValueType.LowerBound), .001);
		assertEquals(804.01, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e2);
		assertEquals(750., vv.get(ValueType.MeanValue), .001);
		assertEquals(695.01, vv.get(ValueType.LowerBound), .001);
		assertEquals(807.02, vv.get(ValueType.UpperBound), .001);
	}
	
	@Test
	void testS13() {
		init1();
		FlowValueMapGroup.createInstance("G13", FVMGroupType.ProcessModule, p1, 1);
		FlowValueMapGroup Group13 = FlowValueMapGroup.getInstance("G13");
		Group13.addFlowValueMap(Modul1);
		Group13.addFlowValueMap(Modul3);
		S13.addProzessmodul(Group13);
		S13.addProzessmodul(Modul4);	
		LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> efv = S13.getElementarflussvektor();
		LinkedHashMap<ValueType, Double> vv = efv.get(r1);
		assertEquals(-725., vv.get(ValueType.MeanValue), .001);
		assertEquals(-754.51, vv.get(ValueType.LowerBound), .001);
		assertEquals(-696.34, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(r2);
		assertEquals(-415., vv.get(ValueType.MeanValue), .001);
		assertEquals(-453.51, vv.get(ValueType.LowerBound), .001);
		assertEquals(-377.54, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e1);
		assertEquals(750., vv.get(ValueType.MeanValue), .001);
		assertEquals(697.34, vv.get(ValueType.LowerBound), .001);
		assertEquals(804.01, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e2);
		assertEquals(700., vv.get(ValueType.MeanValue), .001);
		assertEquals(695.01, vv.get(ValueType.LowerBound), .001);
		assertEquals(705.01, vv.get(ValueType.UpperBound), .001);
	}
	
	@Test
	void testS23() {
		init1();
		FlowValueMapGroup.createInstance("G23", FVMGroupType.ProcessModule, p1, 1);
		FlowValueMapGroup Group23 = FlowValueMapGroup.getInstance("G23");
		Group23.addFlowValueMap(Modul2);
		Group23.addFlowValueMap(Modul3);
		S23.addProzessmodul(Group23);
		S23.addProzessmodul(Modul4);	
		LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> efv = S23.getElementarflussvektor();
		LinkedHashMap<ValueType, Double> vv = efv.get(r1);
		assertEquals(-700., vv.get(ValueType.MeanValue), .001);
		assertEquals(-705.02, vv.get(ValueType.LowerBound), .001);
		assertEquals(-695.02, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(r2);
		assertEquals(-440., vv.get(ValueType.MeanValue), .001);
		assertEquals(-505.02, vv.get(ValueType.LowerBound), .001);
		assertEquals(-377.54, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e1);
		assertEquals(730., vv.get(ValueType.MeanValue), .001);
		assertEquals(697.34, vv.get(ValueType.LowerBound), .001);
		assertEquals(764.62, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e2);
		assertEquals(750., vv.get(ValueType.MeanValue), .001);
		assertEquals(695.3399, vv.get(ValueType.LowerBound), .001);
		assertEquals(807.02, vv.get(ValueType.UpperBound), .001);
	}
	
	@Test
	void testS123() {
		init1();
		FlowValueMapGroup.createInstance("G123", FVMGroupType.ProcessModule, p1, 1);
		FlowValueMapGroup Group123 = FlowValueMapGroup.getInstance("G123");
		Group123.addFlowValueMap(Modul1);
		Group123.addFlowValueMap(Modul2);
		Group123.addFlowValueMap(Modul3);
		S123.addProzessmodul(Group123);
		S123.addProzessmodul(Modul4);	
		LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> efv = S123.getElementarflussvektor();
		LinkedHashMap<ValueType, Double> vv = efv.get(r1);
		assertEquals(-716.6666, vv.get(ValueType.MeanValue), .001);
		assertEquals(-754.51, vv.get(ValueType.LowerBound), .001);
		assertEquals(-695.02, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(r2);
		assertEquals(-443.3333, vv.get(ValueType.MeanValue), .001);
		assertEquals(-505.02, vv.get(ValueType.LowerBound), .001);
		assertEquals(-377.54, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e1);
		assertEquals(753.3333, vv.get(ValueType.MeanValue), .001);
		assertEquals(697.34, vv.get(ValueType.LowerBound), .001);
		assertEquals(804.01, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e2);
		assertEquals(733.3333, vv.get(ValueType.MeanValue), .001);
		assertEquals(695.01, vv.get(ValueType.LowerBound), .001);
		assertEquals(807.02, vv.get(ValueType.UpperBound), .001);
	}
	
	@Test
	void testS12m1() {
		init1();
		FlowValueMapGroup.createInstance("G12", FVMGroupType.ProcessModule, p1, 1);
		FlowValueMapGroup Group12 = FlowValueMapGroup.getInstance("G12");
		Group12.addFlowValueMap(Modul1);
		Group12.addFlowValueMap(Modul2);
		S12m1.addProzessmodul(Group12);
		S12m1.addProzessmodul(Modul4);	
		Group12.disableEntry(Modul1);
		LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> efv = S12m1.getElementarflussvektor();
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
		Group12.enableEntry(Modul1);
		efv = S12m1.getElementarflussvektor();
		vv = efv.get(r1);
		assertEquals(-725., vv.get(ValueType.MeanValue), .001);
		assertEquals(-754.51, vv.get(ValueType.LowerBound), .001);
		assertEquals(-695.02, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(r2);
		assertEquals(-475., vv.get(ValueType.MeanValue), .001);
		assertEquals(-505.02, vv.get(ValueType.LowerBound), .001);
		assertEquals(-446.51, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e1);
		assertEquals(780., vv.get(ValueType.MeanValue), .001);
		assertEquals(755.42, vv.get(ValueType.LowerBound), .001);
		assertEquals(804.01, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e2);
		assertEquals(750., vv.get(ValueType.MeanValue), .001);
		assertEquals(695.01, vv.get(ValueType.LowerBound), .001);
		assertEquals(807.02, vv.get(ValueType.UpperBound), .001);
	}
	
	@Test
	void testS12m2() {
		init1();
		FlowValueMapGroup.createInstance("G12", FVMGroupType.ProcessModule, p1, 1);
		FlowValueMapGroup Group12 = FlowValueMapGroup.getInstance("G12");
		Group12.addFlowValueMap(Modul1);
		Group12.addFlowValueMap(Modul2);
		S12m2.addProzessmodul(Group12);
		S12m2.addProzessmodul(Modul4);	
		Group12.disableEntry(Modul2);
		LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> efv = S12m2.getElementarflussvektor();
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
		Group12.enableEntry(Modul2);
		efv = S12m2.getElementarflussvektor();
		vv = efv.get(r1);
		assertEquals(-725., vv.get(ValueType.MeanValue), .001);
		assertEquals(-754.51, vv.get(ValueType.LowerBound), .001);
		assertEquals(-695.02, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(r2);
		assertEquals(-475., vv.get(ValueType.MeanValue), .001);
		assertEquals(-505.02, vv.get(ValueType.LowerBound), .001);
		assertEquals(-446.51, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e1);
		assertEquals(780., vv.get(ValueType.MeanValue), .001);
		assertEquals(755.42, vv.get(ValueType.LowerBound), .001);
		assertEquals(804.01, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e2);
		assertEquals(750., vv.get(ValueType.MeanValue), .001);
		assertEquals(695.01, vv.get(ValueType.LowerBound), .001);
		assertEquals(807.02, vv.get(ValueType.UpperBound), .001);
	}
	
	@Test
	void testS13m1() {
		init1();
		FlowValueMapGroup.createInstance("G13", FVMGroupType.ProcessModule, p1, 1);
		FlowValueMapGroup Group13 = FlowValueMapGroup.getInstance("G13");
		Group13.addFlowValueMap(Modul1);
		Group13.addFlowValueMap(Modul3);
		S13m1.addProzessmodul(Group13);
		S13m1.addProzessmodul(Modul4);	
		Group13.disableEntry(Modul1);
		LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> efv = S13m1.getElementarflussvektor();
		LinkedHashMap<ValueType, Double> vv = efv.get(r1);
		assertEquals(-700., vv.get(ValueType.MeanValue), .001);
		assertEquals(-703.6733, vv.get(ValueType.LowerBound), .001);
		assertEquals(-696.34, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(r2);
		assertEquals(-380., vv.get(ValueType.MeanValue), .001);
		assertEquals(-382.4733, vv.get(ValueType.LowerBound), .001);
		assertEquals(-377.54, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e1);
		assertEquals(700., vv.get(ValueType.MeanValue), .001);
		assertEquals(697.34, vv.get(ValueType.LowerBound), .001);
		assertEquals(702.6733, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e2);
		assertEquals(700., vv.get(ValueType.MeanValue), .001);
		assertEquals(695.34, vv.get(ValueType.LowerBound), .001);
		assertEquals(704.6733, vv.get(ValueType.UpperBound), .001);
		Group13.enableEntry(Modul1);
		efv = S13m1.getElementarflussvektor();
		vv = efv.get(r1);
		assertEquals(-725., vv.get(ValueType.MeanValue), .001);
		assertEquals(-754.51, vv.get(ValueType.LowerBound), .001);
		assertEquals(-696.34, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(r2);
		assertEquals(-415., vv.get(ValueType.MeanValue), .001);
		assertEquals(-453.51, vv.get(ValueType.LowerBound), .001);
		assertEquals(-377.54, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e1);
		assertEquals(750., vv.get(ValueType.MeanValue), .001);
		assertEquals(697.34, vv.get(ValueType.LowerBound), .001);
		assertEquals(804.01, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e2);
		assertEquals(700., vv.get(ValueType.MeanValue), .001);
		assertEquals(695.01, vv.get(ValueType.LowerBound), .001);
		assertEquals(705.01, vv.get(ValueType.UpperBound), .001);
	}
	
	@Test
	void testS13m3() {
		init1();
		FlowValueMapGroup.createInstance("G13", FVMGroupType.ProcessModule, p1, 1);
		FlowValueMapGroup Group13 = FlowValueMapGroup.getInstance("G13");
		Group13.addFlowValueMap(Modul1);
		Group13.addFlowValueMap(Modul3);
		S13m3.addProzessmodul(Group13);
		S13m3.addProzessmodul(Modul4);	
		Group13.disableEntry(Modul3);
		LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> efv = S13m3.getElementarflussvektor();
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
		Group13.enableEntry(Modul3);
		efv = S13m3.getElementarflussvektor();
		vv = efv.get(r1);
		assertEquals(-725., vv.get(ValueType.MeanValue), .001);
		assertEquals(-754.51, vv.get(ValueType.LowerBound), .001);
		assertEquals(-696.34, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(r2);
		assertEquals(-415., vv.get(ValueType.MeanValue), .001);
		assertEquals(-453.51, vv.get(ValueType.LowerBound), .001);
		assertEquals(-377.54, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e1);
		assertEquals(750., vv.get(ValueType.MeanValue), .001);
		assertEquals(697.34, vv.get(ValueType.LowerBound), .001);
		assertEquals(804.01, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e2);
		assertEquals(700., vv.get(ValueType.MeanValue), .001);
		assertEquals(695.01, vv.get(ValueType.LowerBound), .001);
		assertEquals(705.01, vv.get(ValueType.UpperBound), .001);
	}
	
	@Test
	void testS23m2() {
		init1();
		FlowValueMapGroup.createInstance("G23", FVMGroupType.ProcessModule, p1, 1);
		FlowValueMapGroup Group23 = FlowValueMapGroup.getInstance("G23");
		Group23.addFlowValueMap(Modul2);
		Group23.addFlowValueMap(Modul3);
		S23m2.addProzessmodul(Group23);
		S23m2.addProzessmodul(Modul4);	
		Group23.disableEntry(Modul2);
		LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> efv = S23m2.getElementarflussvektor();
		LinkedHashMap<ValueType, Double> vv = efv.get(r1);
		assertEquals(-700., vv.get(ValueType.MeanValue), .001);
		assertEquals(-703.6733, vv.get(ValueType.LowerBound), .001);
		assertEquals(-696.34, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(r2);
		assertEquals(-380., vv.get(ValueType.MeanValue), .001);
		assertEquals(-382.4733, vv.get(ValueType.LowerBound), .001);
		assertEquals(-377.54, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e1);
		assertEquals(700., vv.get(ValueType.MeanValue), .001);
		assertEquals(697.34, vv.get(ValueType.LowerBound), .001);
		assertEquals(702.6733, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e2);
		assertEquals(700., vv.get(ValueType.MeanValue), .001);
		assertEquals(695.34, vv.get(ValueType.LowerBound), .001);
		assertEquals(704.6733, vv.get(ValueType.UpperBound), .001);
		Group23.enableEntry(Modul2);
		efv = S23m2.getElementarflussvektor();
		vv = efv.get(r1);
		assertEquals(-700., vv.get(ValueType.MeanValue), .001);
		assertEquals(-705.02, vv.get(ValueType.LowerBound), .001);
		assertEquals(-695.02, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(r2);
		assertEquals(-440., vv.get(ValueType.MeanValue), .001);
		assertEquals(-505.02, vv.get(ValueType.LowerBound), .001);
		assertEquals(-377.54, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e1);
		assertEquals(730., vv.get(ValueType.MeanValue), .001);
		assertEquals(697.34, vv.get(ValueType.LowerBound), .001);
		assertEquals(764.62, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e2);
		assertEquals(750., vv.get(ValueType.MeanValue), .001);
		assertEquals(695.3399, vv.get(ValueType.LowerBound), .001);
		assertEquals(807.02, vv.get(ValueType.UpperBound), .001);
	}
	
	@Test
	void testS23m3() {
		init1();
		FlowValueMapGroup.createInstance("G123", FVMGroupType.ProcessModule, p1, 1);
		FlowValueMapGroup Group23 = FlowValueMapGroup.getInstance("G23");
		Group23.addFlowValueMap(Modul2);
		Group23.addFlowValueMap(Modul3);
		S23m3.addProzessmodul(Group23);
		S23m3.addProzessmodul(Modul4);	
		Group23.disableEntry(Modul3);
		LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> efv = S23m3.getElementarflussvektor();
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
		Group23.enableEntry(Modul3);
		efv = S23m3.getElementarflussvektor();
		vv = efv.get(r1);
		assertEquals(-700., vv.get(ValueType.MeanValue), .001);
		assertEquals(-705.02, vv.get(ValueType.LowerBound), .001);
		assertEquals(-695.02, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(r2);
		assertEquals(-440., vv.get(ValueType.MeanValue), .001);
		assertEquals(-505.02, vv.get(ValueType.LowerBound), .001);
		assertEquals(-377.54, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e1);
		assertEquals(730., vv.get(ValueType.MeanValue), .001);
		assertEquals(697.34, vv.get(ValueType.LowerBound), .001);
		assertEquals(764.62, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e2);
		assertEquals(750., vv.get(ValueType.MeanValue), .001);
		assertEquals(695.3399, vv.get(ValueType.LowerBound), .001);
		assertEquals(807.02, vv.get(ValueType.UpperBound), .001);
	}
	
	@Test
	void testS123m1() {
		init1();
		FlowValueMapGroup.createInstance("G123", FVMGroupType.ProcessModule, p1, 1);
		FlowValueMapGroup Group123 = FlowValueMapGroup.getInstance("G123");
		Group123.addFlowValueMap(Modul1);
		Group123.addFlowValueMap(Modul2);
		Group123.addFlowValueMap(Modul3);
		S123m1.addProzessmodul(Group123);
		S123m1.addProzessmodul(Modul4);	
		Group123.disableEntry(Modul1);
		LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> efv = S123m1.getElementarflussvektor();
		LinkedHashMap<ValueType, Double> vv = efv.get(r1);
		assertEquals(-700., vv.get(ValueType.MeanValue), .001);
		assertEquals(-705.02, vv.get(ValueType.LowerBound), .001);
		assertEquals(-695.02, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(r2);
		assertEquals(-440., vv.get(ValueType.MeanValue), .001);
		assertEquals(-505.02, vv.get(ValueType.LowerBound), .001);
		assertEquals(-377.54, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e1);
		assertEquals(730., vv.get(ValueType.MeanValue), .001);
		assertEquals(697.34, vv.get(ValueType.LowerBound), .001);
		assertEquals(764.62, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e2);
		assertEquals(750., vv.get(ValueType.MeanValue), .001);
		assertEquals(695.3399, vv.get(ValueType.LowerBound), .001);
		assertEquals(807.02, vv.get(ValueType.UpperBound), .001);
		Group123.enableEntry(Modul1);
		efv = S123m1.getElementarflussvektor();
		vv = efv.get(r1);
		assertEquals(-716.6666, vv.get(ValueType.MeanValue), .001);
		assertEquals(-754.51, vv.get(ValueType.LowerBound), .001);
		assertEquals(-695.02, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(r2);
		assertEquals(-443.3333, vv.get(ValueType.MeanValue), .001);
		assertEquals(-505.02, vv.get(ValueType.LowerBound), .001);
		assertEquals(-377.54, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e1);
		assertEquals(753.3333, vv.get(ValueType.MeanValue), .001);
		assertEquals(697.34, vv.get(ValueType.LowerBound), .001);
		assertEquals(804.01, vv.get(ValueType.UpperBound), .001);
		vv = efv.get(e2);
		assertEquals(733.3333, vv.get(ValueType.MeanValue), .001);
		assertEquals(695.01, vv.get(ValueType.LowerBound), .001);
		assertEquals(807.02, vv.get(ValueType.UpperBound), .001);
	}
}
