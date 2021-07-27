/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import de.unistuttgart.iwb.ivari.IvariScalar;

/**
 * Diese Klasse dient zur Erzeugung und Nutzung
 * von Objekten des Typs "Flussvektorgruppe".
 * Mit dieser Klasse können alle Objekte, welche das Interface "FlowValueMaps" implementieren,
 * zu Gruppen zusammengefasst werden.
 * 
 * @author Dr.-Ing. Joachim Schwarte, Helen Hein, Johannes Dippon
 * @version 0.715
 */

public class FlowValueMapGroup extends MCAObject  
implements FlowValueMaps, ImpactValueMaps {

	// Instanzvariablen:

	private LinkedHashSet<FlowValueMaps> fvmList = new LinkedHashSet<FlowValueMaps>();
	
	private LinkedHashMap<FlowValueMaps, Boolean> fvmActive 
	= new LinkedHashMap<FlowValueMaps, Boolean>();

	private LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> efv 
	= new LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>>();
	private LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> pfv 
	= new LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>>();

	private FVMGroupType type;
	private Flow refFlow;
	private Double refValue;

	// Konstruktor:

	private FlowValueMapGroup(String name, FVMGroupType type) {
		super(name);
		this.setType(type);
	}
	
	/**
	 * Überprüft, ob bereits eine Flussvektorgruppe
	 * des genannten Namens existiert.
	 * @param name
	 * ist der zu prüfende Name
	 * @return
	 * ... den Wahrheitswert, den die Überprüfung liefert
	 */

	public static boolean containsName(String name) {
		return getAllInstances().containsKey(name);
	}
	
	@Override
	public LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> getImpactValueMap() {
		return null;
	}
	
	/**
	 * Liefert alle Wirkungskategorien und 
	 * deren zugeordneten Wert für
	 * die relevante Bewertungsmethode
	 * @param bm
	 * Die relevante Bewertungsmethode 
	 * @return
	 * ... die der Bewertungsmethode zugehörigen 
	 * Wirkungskategorien und deren zugeordneten Wert 
	 */

	@Override
	public LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> getImpactValueMap(LCIAMethod bm) {
		LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> wv =
				new LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>>();					
		LinkedHashMap<ValueType, Double> values0 = new LinkedHashMap<ValueType, Double>();	
		values0.put(ValueType.MeanValue, 0.);	
		values0.put(ValueType.LowerBound, 0.);	
		values0.put(ValueType.UpperBound, 0.);	
		for (String wk : bm.categoryList().keySet()){	
			wv.put(bm.categoryList().get(wk), values0);	
		}	
		for (String cfName : bm.getFactorSet().keySet()){	
			CharacFactor cf = bm.getFactorSet().get(cfName);	
			if (efv.containsKey(cf.getFlow())) {	
				LinkedHashMap<ValueType, Double> values = new LinkedHashMap<ValueType, Double>();					
				double mv0 = wv.get(cf.getWirkung()).get(ValueType.MeanValue);	
				IvariScalar iv0 = new IvariScalar();	
				iv0.setLowerBound(wv.get(cf.getWirkung()).get(ValueType.LowerBound));	
				iv0.setUpperBound(wv.get(cf.getWirkung()).get(ValueType.UpperBound));	
				double mvc = cf.getValue(ValueType.MeanValue);	
				IvariScalar ivc = new IvariScalar();	
				ivc.setLowerBound(cf.getValue(ValueType.LowerBound));	
				ivc.setUpperBound(cf.getValue(ValueType.UpperBound));	
				double mvf = efv.get(cf.getFlow()).get(ValueType.MeanValue);	
				IvariScalar ivf = new IvariScalar();	
				ivf.setLowerBound(efv.get(cf.getFlow()).get(ValueType.LowerBound));	
				ivf.setUpperBound(efv.get(cf.getFlow()).get(ValueType.UpperBound));	
				double mvr = mvc * mvf;	
				IvariScalar ivr = ivc.mult(ivf);	
				values.put(ValueType.MeanValue, mv0 + mvr);	
				values.put(ValueType.LowerBound, iv0.getLowerBound() + ivr.getLowerBound());	
				values.put(ValueType.UpperBound, iv0.getUpperBound() + ivr.getUpperBound());	
				wv.put(cf.getWirkung(), values);	
			}			
		}		
		return wv;
	}

	/**
	 * @return
	 * den Elementarflussvektor der Flussvektorgruppe
	 */

	@Override
	public LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> getElementarflussvektor() {
		LinkedHashSet<String> flList = new LinkedHashSet<String>();
		for (FlowValueMaps fvm : getActiveList()) {
			for (Flow fl : fvm.getElementarflussvektor().keySet()) {
				flList.add(fl.getName());	
			}
		}	
		for (String fName : flList) {
			Flow f = Flow.getInstance(fName);
			LinkedHashMap<ValueType, Double> valueMap = new LinkedHashMap<ValueType, Double>();
			valueMap.put(ValueType.MeanValue, 0.0);
			valueMap.put(ValueType.LowerBound, Double.POSITIVE_INFINITY);
			valueMap.put(ValueType.UpperBound, Double.NEGATIVE_INFINITY);
			efv.put(f, valueMap);		
			for (FlowValueMaps fvm : getActiveList()) {
				if (fvm.getElementarflussvektor().containsKey(f)) {
					Double mv = efv.get(f).get(ValueType.MeanValue);
					mv = mv + fvm.getElementarflussvektor().get(f).get(ValueType.MeanValue)/
							fvm.getProduktflussvektor().get(refFlow).get(ValueType.MeanValue)*refValue/getActiveList().size();
					efv.get(f).put(ValueType.MeanValue, mv);
					Double lb1 = efv.get(f).get(ValueType.LowerBound);
					Double lb2 = fvm.getElementarflussvektor().get(f).get(ValueType.LowerBound)/
							fvm.getProduktflussvektor().get(refFlow).get(ValueType.LowerBound)*refValue;
					if (lb2 < lb1) {
						efv.get(f).put(ValueType.LowerBound, lb2);
					}
					Double ub1 = efv.get(f).get(ValueType.UpperBound);
					Double ub2 = fvm.getElementarflussvektor().get(f).get(ValueType.UpperBound)/
							fvm.getProduktflussvektor().get(refFlow).get(ValueType.UpperBound)*refValue;
					if (ub2 > ub1) {
						efv.get(f).put(ValueType.UpperBound, ub2);
					} 							
				} else {
					Double lb1 = efv.get(f).get(ValueType.LowerBound);
					Double lb2 = 0.0;
					if (lb2 < lb1) {
						efv.get(f).put(ValueType.LowerBound, lb2);
					}
					Double ub1 = efv.get(f).get(ValueType.UpperBound);
					Double ub2 = 0.0;
					if (ub2 > ub1) {
						efv.get(f).put(ValueType.UpperBound, ub2);
					} 
				}
			}
		}
		return efv;
	}

	/**
	 * @return
	 * den Produktflussvektor der Flussvektorgruppe
	 */

	@Override
	public LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> getProduktflussvektor() {
		LinkedHashSet<String> flList = new LinkedHashSet<String>();
		for (FlowValueMaps fvm : getActiveList()) {
			for (Flow fl : fvm.getProduktflussvektor().keySet()) {
				flList.add(fl.getName());	
			}
		}
		for (String fName : flList) {
			Flow f = Flow.getInstance(fName);
			LinkedHashMap<ValueType, Double> valueMap = new LinkedHashMap<ValueType, Double>();
			valueMap.put(ValueType.MeanValue, 0.0);
			valueMap.put(ValueType.LowerBound, Double.POSITIVE_INFINITY);
			valueMap.put(ValueType.UpperBound, Double.NEGATIVE_INFINITY);
			pfv.put(f, valueMap);
			for (FlowValueMaps fvm : getActiveList()) {
				
				if (fvm.getProduktflussvektor().containsKey(f)) {
					Double mv = pfv.get(f).get(ValueType.MeanValue);
					mv = mv + fvm.getProduktflussvektor().get(f).get(ValueType.MeanValue)/
							fvm.getProduktflussvektor().get(refFlow).get(ValueType.MeanValue)*refValue/getActiveList().size();
					pfv.get(f).put(ValueType.MeanValue, mv);
					Double lb1 = pfv.get(f).get(ValueType.LowerBound);
					Double lb2 = fvm.getProduktflussvektor().get(f).get(ValueType.LowerBound)/
							fvm.getProduktflussvektor().get(refFlow).get(ValueType.LowerBound)*refValue;
					if (lb2 < lb1) {
						pfv.get(f).put(ValueType.LowerBound, lb2);
					}
					Double ub1 = pfv.get(f).get(ValueType.UpperBound);
					Double ub2 = fvm.getProduktflussvektor().get(f).get(ValueType.UpperBound)/
							fvm.getProduktflussvektor().get(refFlow).get(ValueType.UpperBound)*refValue;
					if (ub2 > ub1) {
						pfv.get(f).put(ValueType.UpperBound, ub2);
					} 							
				} else {
					Double lb1 = pfv.get(f).get(ValueType.LowerBound);
					Double lb2 = 0.0;
					if (lb2 < lb1) {
						pfv.get(f).put(ValueType.LowerBound, lb2);
					}
					Double ub1 = pfv.get(f).get(ValueType.UpperBound);
					Double ub2 = 0.0;
					if (ub2 > ub1) {
						pfv.get(f).put(ValueType.UpperBound, ub2);
					} 
				}
			}
		}
		return pfv;
	}

	/**
	 * Erzeugt eine benannte Flussvektorgruppe
	 * @param name
	 * ist der Name der Flussvektorgruppe
	 * @param type
	 * ist der Objekttyp der Flussvektorgruppe
	 * @param f
	 * ist der relevante Produktfluss
	 * @param r
	 * ist die Menge/Wert des relevanten Produktflusses
	 */

	public static void createInstance(String name, FVMGroupType type, Flow f, double r) {
		new FlowValueMapGroup(name, type);
		getAllInstances().get(name).refFlow = f;
		getAllInstances().get(name).refValue = r;
	}

	/**
	 * @return
	 * ... alle vorhandenen Flussvektorgruppen
	 */

	public static LinkedHashMap<String, FlowValueMapGroup> getAllInstances() {
		LinkedHashMap<String,FlowValueMapGroup> a = new LinkedHashMap<String,FlowValueMapGroup>();
		for (String s : MCAObject.getAllNames(FlowValueMapGroup.class)) {
			a.put(s, (FlowValueMapGroup)MCAObject.getAllInstances(FlowValueMapGroup.class).get(s));			
		}
		return a;
	}

	/**
	 * Ergänzt die Flussvektorgruppe um ein weiteres Element
	 * @param fvm
	 * ... ist das zu ergänzende Element. Dies kann ein beliebiges
	 * Objekt einer Klasse, die das Interface FLowValueMaps
	 * implementiert, sein.
	 */

	public void addFlowValueMap(FlowValueMaps fvm) {
		fvmList.add(fvm);
		fvmActive.put(fvm, true);
	}
	
	public LinkedHashSet<FlowValueMaps> getActiveList(){
		LinkedHashSet<FlowValueMaps> returnList = new LinkedHashSet<FlowValueMaps>();
		for (FlowValueMaps fvm : fvmList) {
			if (fvmActive.get(fvm).equals(true)) {
				returnList.add(fvm);
			}
		}
		return returnList;		
	}
	
	public void enableEntry(FlowValueMaps fvm) {
		if (fvmList.contains(fvm)) {
			fvmActive.put(fvm, true);
		}				
	}
	
	public void disableEntry(FlowValueMaps fvm) {
		if (fvmList.contains(fvm)) {
			fvmActive.put(fvm, false);
		}				
	}

	/**
	 * @return 
	 * ... die Liste der Bestandteile, aus denen sich die Flussvektorgruppe
	 * zusammensetzt
	 */

	public LinkedHashSet<FlowValueMaps> getFVMList(){
		return fvmList;
	}

	/**
	 * Liefert eine Flussvektorgruppe
	 * @param name
	 * Name der Flussvektorgruppe
	 * @return
	 * ... die gesuchte Flussvektorgruppe
	 */

	public static FlowValueMapGroup getInstance(String name) {
		return getAllInstances().get(name);		
	}

	/**
	 * @return
	 * ... den relevanten Produktfluss
	 */

	public Flow getRefFlow() {
		return refFlow;
	}

	/**
	 * @return
	 * ... den Wert des relevanten Produktflusses
	 */

	public Double getRefValue() {
		return refValue;
	}
	
	/**
	 * @return 
	 * ... den Objekttyp der Gruppe
	 */

	public FVMGroupType getType() {
		return type;
	}
	
	/**
	 * Legt den Objekttyp der Gruppe fest 
	 * @param type
	 * ... ist der Objekttyp der Gruppe. 
	 */

	public void setType(FVMGroupType type) {
		this.type = type;
	}
}
