/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

import java.util.LinkedHashMap;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.513
 */

public class Flow extends MCAObject {	
		
	// Instanzvariablen:	

	private FlowType typ; 
	private FlowUnit einheit;
	
	// Konstruktor:
	
	private Flow(String name, FlowType typ, FlowUnit einheit) {
		super(name);
		this.typ = typ;
		this.einheit = einheit;
	}
	
	// Methoden:
	
	public static LinkedHashMap<String,Flow> getAllInstances() {
		LinkedHashMap<String,Flow> a = new LinkedHashMap<String,Flow>();
		for (String s : MCAObject.getAllNames(Flow.class)) {
			a.put(s, (Flow)MCAObject.getAllInstances(Flow.class).get(s));			
		}
		return a;
	} 

	public static Flow getInstance(String name) {
		return getAllInstances().get(name);
	}

	public static Flow instance(String name, FlowType typ, FlowUnit einheit) {
		if (getAllInstances().containsKey(name) == false) {
			new Flow(name, typ, einheit);
		}
		return getAllInstances().get(name);
	}
	
	public FlowUnit getEinheit() {
		return einheit;
	}
	
	public FlowType getType() {
		return typ;
	}
}
