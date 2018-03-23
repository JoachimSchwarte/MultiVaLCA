/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

import java.util.HashMap;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.488
 */

public class CharacFactor {
	
	// Klassenvariable:
	
	private static HashMap<String, CharacFactor> allInstances = new HashMap<String, CharacFactor>();
	
	// Instanzvariablen:
	
	private String name;
	private Flow fluss;
	private ImpactCategory wirkung;
	private HashMap<ValueType, Double> werte;
	
	// Konstruktoren:

	private CharacFactor(String name, Flow fluss, ImpactCategory wirkung, Double wert) {
		super();
		this.name = name;
		this.fluss = fluss;
		this.wirkung = wirkung;
		this.werte = new HashMap<ValueType, Double>();
		this.werte.put(ValueType.MeanValue, wert);
		this.werte.put(ValueType.LowerBound, wert);
		this.werte.put(ValueType.UpperBound, wert);
		allInstances.put(name, this);
	}
	
	private CharacFactor(String name, Flow fluss, ImpactCategory wirkung, HashMap<ValueType, Double> werte) {
		super();
		this.name = name;
		this.fluss = fluss;
		this.wirkung = wirkung;
		this.werte = werte;	
		allInstances.put(name, this);
	}
	
	// Methoden:
	
	/**
	 * Löscht alle Klassenvariablen
	 */
	
	public static void clear() {
		allInstances.clear();
	}
	
	/**
	 * Überprüft, ob bereits ein Charakterisierungsfaktor
	 * des gegebenen Namens existiert.
	 * @param string
	 * ist der zu prüfende Name
	 * @return
	 * ... den Wahrheitswert, den die Überprüfung liefert
	 */
	
	public static boolean containsName(String string) {
		return allInstances.containsKey(string);
	}
	
	/**
	 * @return
	 * ... alle vorhandenen Charakterisierungsfaktoren
	 */
	
	public static HashMap<String, CharacFactor> getAllInstances() {
		return allInstances;
	}
	
	/**
	 * Liefert einen Charakterisierungsfaktor zurück
	 * @param string
	 * Name des gewünschten Charakterisierungsfaktors
	 * @return
	 * ... den gesuchten Charakterisierungsfaktor
	 */
	
	public static CharacFactor getInstance(String string) {
		return allInstances.get(string);
	}
	
	/**
	 * @return
	 * ... das Flussobjekt.
	 */

	public Flow getFlow() {
		return fluss;
	}
	
	/**
	 * @return
	 * ... den Namen des CharakterFaktor-Objekts.
	 */

	public String getName() {
		return name;
	}
	
	/**
	 * @return
	 * ... den Zahlenwert der Wirkung des Flusses bzgl. der 
	 * angegebenen Kategorie.
	 */

	public Double getValue(ValueType vt) {
		return werte.get(vt);
	}
	
	public HashMap<ValueType, Double> getValues() {
		return werte;
	}

	/**
	 * @return
	 * ... die Wirkungskategorie.
	 */

	public ImpactCategory getWirkung() {
		return wirkung;
	}
	
	public static CharacFactor instance(String name, Flow fluss, ImpactCategory wirkung, Double wert) {
		if (allInstances.containsKey(name) == false) {
			new CharacFactor(name, fluss, wirkung, wert);
		}
		return allInstances.get(name);
	}
	
	public static void setLowerBound(String name, Double lv) {
		HashMap<ValueType, Double> values = getInstance(name).getValues();
		values.put(ValueType.LowerBound, lv);
		new CharacFactor(name, getInstance(name).getFlow(), 
				getInstance(name).getWirkung(), values);	
	}
	
	public static void setUpperBound(String name, Double lv) {
		HashMap<ValueType, Double> values = getInstance(name).getValues();
		values.put(ValueType.UpperBound, lv);
		new CharacFactor(name, getInstance(name).getFlow(), 
				getInstance(name).getWirkung(), values);		
	}
}
