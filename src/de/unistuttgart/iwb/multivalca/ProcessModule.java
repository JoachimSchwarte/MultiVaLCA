/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;
import java.util.LinkedHashMap;

import de.unistuttgart.iwb.ivari.IvariScalar;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.513
 */

public class ProcessModule extends MCAObject  
implements FlowValueMaps, ImpactValueMaps {
	
	// Instanzvariablen:
	
	private LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> efv = 
			new LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>>(); //Elementarflüsse
	private LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> pfv = 
			new LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>>(); //Produktflüsse
	
	// Konstruktor:
			
	private ProcessModule(String name) {
		super(name);
	}
	
	// Methoden:

	public static LinkedHashMap<String, ProcessModule> getAllInstances() {
		LinkedHashMap<String,ProcessModule> a = new LinkedHashMap<String,ProcessModule>();
		for (String s : MCAObject.getAllNames(ProcessModule.class)) {
			a.put(s, (ProcessModule)MCAObject.getAllInstances(ProcessModule.class).get(s));			
		}
		return a;
	}
	
	public static boolean containsName(String string) {
		return getAllInstances().containsKey(string);
	}

	public static ProcessModule getInstance(String string) {
		return getAllInstances().get(string);		
	}

	public static ProcessModule instance(String name) {
		if (getAllInstances().containsKey(name) == false) {
			new ProcessModule(name);
		}
		return getAllInstances().get(name);
	}

	public static void removeInstance(String string) {
		getAllInstances().remove(string);
		NameCheck.remove(string);
	}
	
	public void addFluss(Flow fluss, ValueType fvt, Double wert) {	
		if (fluss.getType() == FlowType.Elementary) {
			LinkedHashMap<ValueType, Double> valueMap = efv.get(fluss);
			if (valueMap == null) {
				valueMap = new LinkedHashMap<ValueType, Double>();
			}
			valueMap.put(fvt, wert);
			efv.put(fluss, valueMap);
		} else { 
			LinkedHashMap<ValueType, Double> valueMap = pfv.get(fluss);
			if (valueMap == null) {
				valueMap = new LinkedHashMap<ValueType, Double>();
			}
			valueMap.put(fvt, wert);
			pfv.put(fluss, valueMap);
		}
	}
	
	@Override
	public LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> getElementarflussvektor() {
		return efv; 
	}	
	
	@Override
	public LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> getProduktflussvektor() {
		return pfv; 
	}
	
	public void removeFluss(Flow fluss) {
		efv.remove(fluss);
		pfv.remove(fluss);
	}

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
}

