/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

import java.util.LinkedHashMap;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.558
 */

public class Flow extends MCAObject {	
		
	// Instanzvariablen:	

	/**
	 * the FlowType as given in the respective enumeration
	 */
	private FlowType type; 
	
	/**
	 * the FlowUnit as given in the respective enumeration
	 */
	private FlowUnit unit;
	
	// Konstruktor:
	
	/**
	 * Constructor using the superclass constructor MCAObject(...)
	 * @param name
	 * the name of the new flow
	 * @param type
	 * the FlowType as given in the respective enumeration
	 * @param unit
	 * the FlowUnit as given in the respective enumeration
	 */
	private Flow(String name, FlowType type, FlowUnit unit) {
		super(name);
		this.type = type;
		this.unit = unit;
	}
	
	// Methoden:
	
	/**
	 * gets a map of flow names and the corresponding objects
	 * @return
	 * a LinkedHashMap that contains all existing names of flows
	 * and the mapped objects
	 */
	public static LinkedHashMap<String,Flow> getAllInstances() {
		LinkedHashMap<String,Flow> a = new LinkedHashMap<String,Flow>();
		for (String s : MCAObject.getAllNames(Flow.class)) {
			a.put(s, (Flow)MCAObject.getAllInstances(Flow.class).get(s));			
		}
		return a;
	} 

	/**
	 * gets a single flow object
	 * @param name
	 * the name of the flow
	 * @return
	 * a flow object
	 */
	public static Flow getInstance(String name) {
		return getAllInstances().get(name);
	}

	/**
	 * creates a new instance of the class flow
	 * @param name
	 * the name of the new flow
	 * @param type
	 * the FlowType as given in the respective enumeration
	 * @param unit
	 * the FlowUnit as given in the respective enumeration
	 * @return
	 * the new flow object
	 */
	public static Flow instance(String name, FlowType type, FlowUnit unit) {
		if (!getAllInstances().containsKey(name)) {
			new Flow(name, type, unit);
		}
		return getAllInstances().get(name);
	}
	
	/**
	 * the FlowUnit as given in the respective enumeration
	 * @return
	 * a FlowUnit
	 */
	public FlowUnit getUnit() {
		return unit;
	}
	
	/**
	 * the FlowType as given in the respective enumeration
	 * @return
	 * a FlowType
	 */
	public FlowType getType() {
		return type;
	}
}
