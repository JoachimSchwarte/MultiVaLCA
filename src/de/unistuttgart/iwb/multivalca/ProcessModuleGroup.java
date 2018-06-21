/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

import java.util.HashSet;
import java.util.LinkedHashMap;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.519
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> getProduktflussvektor() {
		// TODO Auto-generated method stub
		return null;
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

}
