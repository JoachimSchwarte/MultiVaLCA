/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

import java.util.LinkedHashMap;

/**
 * Diese Klasse dient zur Erzeugung und Nutzung
 * von Objekten des Typs "Fluss".
 * 
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.532
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
	
	/**
	 * @return
	 * ... alle vorhandenen Flüssen
	 */
	
	public static LinkedHashMap<String,Flow> getAllInstances() {
		LinkedHashMap<String,Flow> a = new LinkedHashMap<String,Flow>();
		for (String s : MCAObject.getAllNames(Flow.class)) {
			a.put(s, (Flow)MCAObject.getAllInstances(Flow.class).get(s));			
		}
		return a;
	} 
	
	/**
	 * Liefert einen Fluss
	 * @param name
	 * ist der Name des Flusses
	 * @return
	 * ... den gesuchten Fluss
	 */

	public static Flow getInstance(String name) {
		return getAllInstances().get(name);
	}
	
	/**
	 * Erzeugt einen neuen Fluss
	 * @param name
	 * der Name des neuen Flusses
	 * @param typ
	 * legt den Typ des Flusses fest (Elementar- oder Produktfluss)
	 * @param einheit
	 * legt die Einheit, in der der Fluss quantifiziert wird, fest
	 * @return
	 * ... der neue Fluss
	 */

	public static Flow instance(String name, FlowType typ, FlowUnit einheit) {
		if (!getAllInstances().containsKey(name)) {
			new Flow(name, typ, einheit);
		}
		return getAllInstances().get(name);
	}
	
	/**
	 * @return
	 * ... die Einheit, in der der Fluss
	 * quantifiziert wird.
	 */
	
	public FlowUnit getEinheit() {
		return einheit;
	}
	
	/**
	 * @return
	 * ... den Typ des Flusses
	 */
	
	public FlowType getType() {
		return typ;
	}
}
