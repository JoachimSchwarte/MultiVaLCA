/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;
import java.util.LinkedHashMap;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.554
 */

public class ProcessModule extends GenPM  
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
		if (!getAllInstances().containsKey(name)) {
			new ProcessModule(name);
		}
		return getAllInstances().get(name);
	}

	public static void removeInstance(String string) {
		getAllInstances().remove(string);
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
}

