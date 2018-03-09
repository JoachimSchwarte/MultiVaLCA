/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

import java.util.HashMap;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.43
 */

public class CharacFactor {
	
	// Klassenvariable:
	
	private static HashMap<String, CharacFactor> allInstances = new HashMap<String, CharacFactor>();
	
	// Instanzvariablen:
	
	private String name;
	private Flow fluss;
	private ImpactCategory wirkung;
	private HashMap<ValueType, Double> werte;
	
	// Konstruktor:

	private CharacFactor(String name, Flow fluss, ImpactCategory wirkung, Double wert) {
		super();
		this.name = name;
		this.fluss = fluss;
		this.wirkung = wirkung;
		this.werte.put(ValueType.MeanValue, wert);
		this.werte.put(ValueType.LowerBound, wert);
		this.werte.put(ValueType.UpperBound, wert);
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

	/**
	 * @return
	 * ... die Wirkungskategorie.
	 */

	public ImpactCategory getWirkung() {
		return wirkung;
	}

}
