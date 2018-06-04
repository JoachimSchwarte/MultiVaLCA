/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

import java.util.LinkedHashMap;

/**
 * Diese Klasse dient zur Erzeugung von Flussobjekten.
 * Es stehen Zugriffsmethoden zur Abfrage der privaten
 * Instanzvariablen zur Verfügung.
 * 
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.511
 */

public class Flow extends MCAObject {	
	
	// Klassenvariable:
	
	 private static LinkedHashMap<String,Flow> allInstances = new LinkedHashMap<String,Flow>();
	
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
	 * ... alle vorhandenen Flussobjekte
	 */
	
	public static LinkedHashMap<String,Flow> getAllInstances() {
	
		return allInstances;
	}
	
	/**
	 * @param name
	 * Name des gesuchten Flusses
	 * @return
	 * ... ein Flussobjekt
	 */
	
	public static Flow getInstance(String name) {
		return allInstances.get(name);
	}
	
	/**
	 * Erzeugt ein vollständiges Flussobjekt durch Aufruf des
	 * privaten Konstruktors sofern noch kein Fluss gleichem
	 * Namens existiert. Ansonsten wird der existierende Fluss
	 * zurückgegeben.
	 * @param name
	 * kann frei gewählt werden.
	 * Auf Anwendungsebene ist Namenseindeutigkeit anzustreben. 
	 * @param typ
	 * dient vorrangig zur Unterscheidung von Elementar- 
	 * und Produktflüssen.
	 * @param einheit
	 * legt die physikalische Einheit fest, in der der
	 * Fluss quantifiziert wird.
	 * @return
	 * ... das Flussobjekt
	 */
	
	public static Flow instance(String name, FlowType typ, FlowUnit einheit) {
		if (allInstances.containsKey(name) == false) {
			new Flow(name, typ, einheit);
		}
		return allInstances.get(name);
	}
	
	/**
	 * @return
	 * ... die physikalische Einheit, in der die zugehörigen
	 * Flüsse quantifiziert werden.
	 */
	
	public FlowUnit getEinheit() {
		return einheit;
	}
	
	/**
	 * @return
	 * ... den Typ des Flussobjektes.
	 */
	
	public FlowType getType() {
		return typ;
	}
}
