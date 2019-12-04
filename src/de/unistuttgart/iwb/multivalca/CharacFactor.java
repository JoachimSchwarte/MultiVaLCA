/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

import java.util.LinkedHashMap;

/**
 * Diese Klasse dient zur Erzeugung und Nutzung
 * von Objekten des Typs "Charakterisierungsfaktor".
 * 
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.532
 */

public class CharacFactor extends MCAObject {
	
	// Instanzvariablen:
	
	private Flow fluss;
	private ImpactCategory wirkung;
	private LinkedHashMap<ValueType, Double> werte;
	
	// Konstruktoren:

	private CharacFactor(String name, Flow fluss, ImpactCategory wirkung, Double wert) {
		super(name);
		this.fluss = fluss;
		this.wirkung = wirkung;
		this.werte = new LinkedHashMap<ValueType, Double>();
		this.werte.put(ValueType.MeanValue, wert);
		this.werte.put(ValueType.LowerBound, wert);
		this.werte.put(ValueType.UpperBound, wert);
	}
	
	private CharacFactor(String name, Flow fluss, ImpactCategory wirkung, LinkedHashMap<ValueType, Double> werte) {
		super(name);
		this.fluss = fluss;
		this.wirkung = wirkung;
		this.werte = werte;	
	}
	
	// Methoden:
	
	/**
	 * Überprüft, ob bereits ein Charakterisierungsfaktor
	 * des gegebenen Namens existiert.
	 * @param name
	 * ist der zu prüfende Name
	 * @return
	 * ... den Wahrheitswert, den die Überprüfung liefert
	 */
	
	public static boolean containsName(String name) {
		return getAllInstances().containsKey(name);
	}
	
	/**
	 * @return
	 * ... alle vorhandenen Charakterisierungsfaktoren
	 */
	
	public static LinkedHashMap<String, CharacFactor> getAllInstances() {
		LinkedHashMap<String,CharacFactor> a = new LinkedHashMap<String,CharacFactor>();
		for (String s : MCAObject.getAllNames(CharacFactor.class)) {
			a.put(s, (CharacFactor)MCAObject.getAllInstances(CharacFactor.class).get(s));			
		}
		return a;
	}
	
	/**
	 * Liefert einen Charakterisierungsfaktor zurück
	 * @param name
	 * Name des gewünschten Charakterisierungsfaktors
	 * @return
	 * ... den gesuchten Charakterisierungsfaktor
	 */
	
	public static CharacFactor getInstance(String name) {
		return getAllInstances().get(name);
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
	 * ... den Zahlenwert der Wirkung des Flusses bzgl. der 
	 * angegebenen Kategorie.
	 */

	public Double getValue(ValueType vt) {
		return werte.get(vt);
	}
	
	public LinkedHashMap<ValueType, Double> getValues() {
		return werte;
	}

	/**
	 * @return
	 * ... die Wirkungskategorie.
	 */

	public ImpactCategory getWirkung() {
		return wirkung;
	}
	
	/**
	 * Erzeugt einen neuen Charakterisierungsfaktor
	 * @param name
	 * der Name des Charakterisierungsfaktors
	 * @param fluss
	 * der Name des zugeordneten Flusses
	 * @param wirkung
	 * der Name der zugeordneten Wirkungskategorie
	 * @param wert
	 * der Hauptwert des Charakterisierungsfaktors
	 * @return
	 * ... den neuen Charakterisierungsfaktor
	 */
	
	public static CharacFactor instance(String name, Flow fluss, ImpactCategory wirkung, Double wert) {
		if (!getAllInstances().containsKey(name)) {
			new CharacFactor(name, fluss, wirkung, wert);
		}
		return getAllInstances().get(name);
	}
	
	/**
	 * Setzt den Wert für die Untergrenze des Charakterisierungsfaktors 
	 * @param name
	 * der Name des Charakterisierungsfaktors
	 * @param lv
	 * der Wert für die Untergrenze 
	 */
	
	public static void setLowerBound(String name, Double lv) {
		LinkedHashMap<ValueType, Double> values = getInstance(name).getValues();
		values.put(ValueType.LowerBound, lv);
		new CharacFactor(name, getInstance(name).getFlow(), 
				getInstance(name).getWirkung(), values);	
	}
	
	/**
	 * Setzt den Wert für die Obergrenze des Charakterisierungsfaktors 
	 * @param name
	 * der Name des Charakterisierungsfaktors
	 * @param lv
	 * der Wert für die Obergrenze 
	 */
	
	public static void setUpperBound(String name, Double lv) {
		LinkedHashMap<ValueType, Double> values = getInstance(name).getValues();
		values.put(ValueType.UpperBound, lv);
		new CharacFactor(name, getInstance(name).getFlow(), 
				getInstance(name).getWirkung(), values);		
	}
}
