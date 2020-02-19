/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

import java.util.LinkedHashMap;

/**
 * Diese Klasse dient zur Erzeugung und Nutzung von Objekten des Typs
 * "Komposition".
 * 
 * @author Dr.-Ing. Joachim Schwarte, Helen Hein, Johannes Dippon
 * @version 0.700
 */

public class Composition extends MCAObject implements ImpactValueMaps {

	// Instanzvariablen:

	private LinkedHashMap<ImpactValueMaps,Integer> zusammensetzung = new LinkedHashMap<ImpactValueMaps,Integer>();

	// Konstruktor:

	private Composition(String name) {
		super(name);
	}

	// Methoden:

	/**
	 * Überprüft, ob bereits eine Komposition des genannten Namens existiert.
	 * 
	 * @param name
	 *            ist der zu prüfende Name
	 * @return ... den Wahrheitswert, den die Überprüfung liefert
	 */

	public static boolean containsName(String name) {
		return getAllInstances().containsKey(name);
	}

	/**
	 * @return ... alle vorhandenen Kompositionen
	 */

	public static LinkedHashMap<String, Composition> getAllInstances() {
		LinkedHashMap<String,Composition> a = new LinkedHashMap<String,Composition>();
		for (String s : MCAObject.getAllNames(Composition.class)) {
			a.put(s, (Composition)MCAObject.getAllInstances(Composition.class).get(s));			
		}
		return a;
	}

	/**
	 * Gibt eine bereits vorhandene Komposition zurück.
	 * @param name
	 * Name der gesuchten Komposition
	 * @return
	 * ... die gesuchte Komposition
	 */

	public static Composition getInstance(String name) { 
		return getAllInstances().get(name);
	}

	/**
	 * Erzeugt eine neue Komposition
	 * @param name
	 * Name der Komposition 
	 * @return
	 * ... die neue Komposition
	 */

	public static Composition instance(String name) {
		if (!getAllInstances().containsKey(name)) {
			new Composition(name);	
		} 
		return getAllInstances().get(name);
	}

	/**
	 * Ergänzt die Komposition um eine weitere Komponente.
	 * 
	 * @param teilprodukt
	 *            ... ist die zu ergänzende Komponente. Dies kann ein beliebiges
	 *            Objekt einer Klasse, die das Interface ImpactValueMaps
	 *            implementiert, sein.
	 * @param anzahl
	 * 	...ist die Anzahl der hinzuzufügenden Komponenten            
	 */

	public void addKomponente(ImpactValueMaps teilprodukt, Integer anzahl) {
		if(!getZusammensetzung().containsKey(teilprodukt)) {
			zusammensetzung.put(teilprodukt, anzahl);			
		} else  {
			zusammensetzung.put(teilprodukt, zusammensetzung.get(teilprodukt)+anzahl);
		}
	}

	/**
	 * @return ... die Anzahl der in der Komposition enthaltenden Komponenten
	 */

	public Integer getKompAnz() {
		Integer size = 0;
		for(ImpactValueMaps tp : zusammensetzung.keySet()) {
			size = size+zusammensetzung.get(tp);
		}
		return size;
	}

	@Override
	public LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> getImpactValueMap() {
		return null;
	}

	/**
	 * Liefert den Wirkungsvektor der Komposition für
	 * die relevante Bewertungsmethode
	 * @param bm
	 * die relevante Bewertungsmethode 
	 * @return
	 * ... den zugehörigen Wirkungsvektor 
	 */

	@Override
	public LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> getImpactValueMap(LCIAMethod bm) {
		LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> wv = new LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>>();
		for (String wkName : bm.categoryList().keySet()) {
			ImpactCategory wk = bm.categoryList().get(wkName);
			LinkedHashMap<ValueType, Double> values = new LinkedHashMap<ValueType, Double>();
			values.put(ValueType.MeanValue, 0.0);
			values.put(ValueType.LowerBound, 0.0);
			values.put(ValueType.UpperBound, 0.0);
			for (ImpactValueMaps wvKomponente : zusammensetzung.keySet()) {
				for (ValueType vt : values.keySet()) {
					values.put(vt, values.get(vt)+wvKomponente.getImpactValueMap(bm).get(wk).get(vt)*zusammensetzung.get(wvKomponente));
				}
			}	
			wv.put(wk, values);
		}
		return wv;
	}

	/**
	 * @return ... die Liste der Bestandteile, aus denen sich die Komposition
	 *         zusammensetzt
	 */

	public LinkedHashMap<ImpactValueMaps,Integer> getZusammensetzung() {
		return zusammensetzung;
	}

}