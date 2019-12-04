/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Diese Klasse dient zur Erzeugung und Nutzung von Objekten des Typs
 * "Komposition".
 * 
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.536
 */

public class Composition extends MCAObject implements ImpactValueMaps {

	// Klassenvariable:

	private static LinkedHashMap<String, Composition> allInstances = new LinkedHashMap<String, Composition>();

	// Instanzvariablen:

	private LinkedList<ImpactValueMaps> zusammensetzung = new LinkedList<ImpactValueMaps>();

	// Konstruktor:

	private Composition(String name) {
		super(name);
		allInstances.put(name, this);
	}

	// Methoden:

	/**
	 * L�scht alle Klassenvariablen
	 */

	public static void clear() {
		allInstances.clear();
	}

	/**
	 * �berpr�ft, ob bereits eine Composition des genannten Namens existiert.
	 * 
	 * @param name
	 *            ist der zu pr�fende Name
	 * @return ... den Wahrheitswert, den die �berpr�fung liefert
	 */

	public static boolean containsName(String name) {
		return allInstances.containsKey(name);
	}

	/**
	 * @return ... alle vorhandenen Compositionen
	 */

	public static LinkedHashMap<String, Composition> getAllInstances() {
		return allInstances;
	}

	/**
	 * Liefert eine Composition
	 * 
	 * @param name
	 *            Name der Composition
	 * @return ... die gesuchte Composition
	 */

	public static Composition getInstance(String name) {
		return allInstances.get(name);
	}

	/**
	 * Die instance-Methode erzeugt unter Verwendung des privaten Konstruktors eine
	 * neue Composition oder gibt eine bereits existierende Composition zur�ck.
	 * 
	 * @param name
	 *            ist der Name der Composition.
	 * @return ... neue oder bereits zuvor existierende Composition
	 */

	public static Composition instance(String name) {
		if (!allInstances.containsKey(name)) {
			new Composition(name);
		}
		return allInstances.get(name);
	}

	/**
	 * Erg�nzt die Composition um eine weitere Komponente.
	 * 
	 * @param teilprodukt
	 *            ... ist die zu erg�nzende Komponente. Dies kann ein beliebiges
	 *            Objekt einer Klasse, die das Interface ImpactValueMaps
	 *            implementiert, sein.
	 */

	public void addKomponente(ImpactValueMaps teilprodukt) {
		zusammensetzung.add(teilprodukt);
	}

	/**
	 * @return ... die Anzahl der in der Composition enthaltenden Komponenten
	 */

	public Integer getKompAnz() {
		return zusammensetzung.size();
	}
	
	@Override
	public LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> getImpactValueMap() {
		return null;
	}
	
	/**
	 * Liefert den Wirkungsvektor der Komposition f�r
	 * die relevante Bewertungsmethode
	 * @param bm
	 * die relevante Bewertungsmethode 
	 * @return
	 * ... den zugeh�rigen Wirkungsvektor 
	 */

	@Override
	public LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> getImpactValueMap(LCIAMethod bm) {
		LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> wv = new LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>>();
		for (String wkName : bm.categoryList().keySet()) {
			ImpactCategory wk = bm.categoryList().get(wkName);
			LinkedHashMap<ValueType, Double> values = new LinkedHashMap<ValueType, Double>();
			for (ValueType vt : values.keySet()) {
				values.put(vt, 0.);
			}
			wv.put(wk, values);
		}
		for (ImpactValueMaps wvKomponente : zusammensetzung) {
			for (ImpactCategory kategorie : wvKomponente.getImpactValueMap(bm).keySet()) {
				LinkedHashMap<ValueType, Double> values = new LinkedHashMap<ValueType, Double>();
				for (ValueType vt : values.keySet()) {
					values.put(vt,
							wv.get(kategorie).get(vt) + wvKomponente.getImpactValueMap(bm).get(kategorie).get(vt));
				}
				wv.put(kategorie, values);
			}
		}
		return wv;
	}

	/**
	 * @return ... die Liste der Bestandteile, aus denen sich die Composition
	 *         zusammensetzt
	 */

	public LinkedList<ImpactValueMaps> getZusammensetzung() {
		return zusammensetzung;
	}

}
