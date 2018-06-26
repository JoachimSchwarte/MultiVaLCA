/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

import java.util.HashSet;
import java.util.LinkedHashMap;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.523
 */

public class ProcessModuleGroup extends MCAObject  
implements FlowValueMaps, ImpactValueMaps {
	
	// Instanzvariablen:
	
	private HashSet<ProcessModule> modList = new HashSet<ProcessModule>();
	private Flow refFlow;
	private double refValue;

	// Konstruktor:
	
	private ProcessModuleGroup(String name) {
		super(name);
	}

	@Override
	public LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> getImpactValueMap(LCIAMethod bm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> getElementarflussvektor() {
		LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> localEFV = new LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>>(); 
		HashSet<Flow> flowlist = new HashSet<Flow>();
		for (ProcessModule pm : modList) {
			flowlist.addAll(pm.getElementarflussvektor().keySet());
		}
		for (Flow f : flowlist) {
			LinkedHashMap<ValueType, Double> valueMap = new LinkedHashMap<ValueType, Double>();
			valueMap.put(ValueType.MeanValue, 0.0);
			valueMap.put(ValueType.LowerBound, Double.POSITIVE_INFINITY);
			valueMap.put(ValueType.UpperBound, Double.NEGATIVE_INFINITY);
			localEFV.put(f, valueMap);		
			for (ProcessModule pm : modList) {
				if (pm.getElementarflussvektor().containsKey(f)) {
					Double mv = localEFV.get(f).get(ValueType.MeanValue);
					mv = mv + pm.getElementarflussvektor().get(f).get(ValueType.MeanValue)/
							pm.getProduktflussvektor().get(refFlow).get(ValueType.MeanValue)*refValue/modList.size();
					localEFV.get(f).put(ValueType.MeanValue, mv);
					Double lb1 = localEFV.get(f).get(ValueType.LowerBound);
					Double lb2 = pm.getElementarflussvektor().get(f).get(ValueType.LowerBound)/
							pm.getProduktflussvektor().get(refFlow).get(ValueType.LowerBound)*refValue;
					if (lb2 < lb1) {
						localEFV.get(f).put(ValueType.LowerBound, lb2);
					}
					Double ub1 = localEFV.get(f).get(ValueType.UpperBound);
					Double ub2 = pm.getElementarflussvektor().get(f).get(ValueType.UpperBound)/
							pm.getProduktflussvektor().get(refFlow).get(ValueType.UpperBound)*refValue;
					if (ub2 > ub1) {
						localEFV.get(f).put(ValueType.UpperBound, ub2);
					} 							
				} else {
					Double lb1 = localEFV.get(f).get(ValueType.LowerBound);
					Double lb2 = 0.0;
					if (lb2 < lb1) {
						localEFV.get(f).put(ValueType.LowerBound, lb2);
					}
					Double ub1 = localEFV.get(f).get(ValueType.UpperBound);
					Double ub2 = 0.0;
					if (ub2 > ub1) {
						localEFV.get(f).put(ValueType.UpperBound, ub2);
					} 
				}
			}
		}
		return localEFV;
	}

	@Override
	public LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> getProduktflussvektor() {
		LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> localPFV = new LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>>(); 
		HashSet<Flow> flowlist = new HashSet<Flow>();
		for (ProcessModule pm : modList) {
			flowlist.addAll(pm.getProduktflussvektor().keySet());
		}
		for (Flow f : flowlist) {
			LinkedHashMap<ValueType, Double> valueMap = new LinkedHashMap<ValueType, Double>();
			valueMap.put(ValueType.MeanValue, 0.0);
			valueMap.put(ValueType.LowerBound, Double.POSITIVE_INFINITY);
			valueMap.put(ValueType.UpperBound, Double.NEGATIVE_INFINITY);
			localPFV.put(f, valueMap);
			for (ProcessModule pm : modList) {
				if (pm.getProduktflussvektor().containsKey(f)) {
					Double mv = localPFV.get(f).get(ValueType.MeanValue);
					mv = mv + pm.getProduktflussvektor().get(f).get(ValueType.MeanValue)/
							pm.getProduktflussvektor().get(refFlow).get(ValueType.MeanValue)*refValue/modList.size();
					localPFV.get(f).put(ValueType.MeanValue, mv);
					Double lb1 = localPFV.get(f).get(ValueType.LowerBound);
					Double lb2 = pm.getProduktflussvektor().get(f).get(ValueType.LowerBound)/
							pm.getProduktflussvektor().get(refFlow).get(ValueType.LowerBound)*refValue;
					if (lb2 < lb1) {
						localPFV.get(f).put(ValueType.LowerBound, lb2);
					}
					Double ub1 = localPFV.get(f).get(ValueType.UpperBound);
					Double ub2 = pm.getProduktflussvektor().get(f).get(ValueType.UpperBound)/
							pm.getProduktflussvektor().get(refFlow).get(ValueType.UpperBound)*refValue;
					if (ub2 > ub1) {
						localPFV.get(f).put(ValueType.UpperBound, ub2);
					} 							
				} else {
					Double lb1 = localPFV.get(f).get(ValueType.LowerBound);
					Double lb2 = 0.0;
					if (lb2 < lb1) {
						localPFV.get(f).put(ValueType.LowerBound, lb2);
					}
					Double ub1 = localPFV.get(f).get(ValueType.UpperBound);
					Double ub2 = 0.0;
					if (ub2 > ub1) {
						localPFV.get(f).put(ValueType.UpperBound, ub2);
					} 
				}
			}
		}
		return localPFV;
	}
	
	public static void createInstance(String name, Flow f, double r) {
		new ProcessModuleGroup(name);
		getAllInstances().get(name).refFlow = f;
		getAllInstances().get(name).refValue = r;
	}
	
	public static LinkedHashMap<String, ProcessModuleGroup> getAllInstances() {
		LinkedHashMap<String,ProcessModuleGroup> a = new LinkedHashMap<String,ProcessModuleGroup>();
		for (String s : MCAObject.getAllNames(ProcessModuleGroup.class)) {
			a.put(s, (ProcessModuleGroup)MCAObject.getAllInstances(ProcessModuleGroup.class).get(s));			
		}
		return a;
	}

	public void addModule(ProcessModule pm) {
		modList.add(pm);
	}
	
	public HashSet<ProcessModule> getModList(){
		return modList;
	}
	
	public static ProcessModuleGroup getInstance(String string) {
		return getAllInstances().get(string);		
	}

}
