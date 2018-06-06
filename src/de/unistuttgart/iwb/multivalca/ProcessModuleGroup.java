/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

import java.util.LinkedHashMap;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.516
 */

public class ProcessModuleGroup extends MCAObject  
implements FlowValueMaps, ImpactValueMaps {
	
	// Instanzvariablen:
	
	private LinkedHashMap<Flow, Double> modList = new LinkedHashMap<Flow, Double>();

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

}
