/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

import java.util.LinkedHashMap;

/**
 * Diese Klasse dient zur Erzeugung und Nutzung
 * von Objekten des Typs "Wirkungskategorie".
 * 
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.530
 */

public class ImpactCategory extends MCAObject {
	
	// Instanzvariablen:
	
	private CategoryIndicator einheit;
	
	// Konstruktor basierend auf den Instanzvariablen:
	
	private ImpactCategory(String name, CategoryIndicator einheit) {
		super(name);
		this.einheit = einheit;
	}
	
	// Methoden:
	
	/**
	 * �berpr�ft, ob bereits eine Wirkungskategorie
	 * des gegebenen Namens existiert.
	 * @param name
	 * ist der zu pr�fende Name
	 * @return
	 * ... den Wahrheitswert, den die �berpr�fung liefert
	 */
	
	public static boolean containsName(String name) {
		return getAllInstances().keySet().contains(name);
	}
	
	/**
	 * @return
	 * ... alle vorhandenen Wirkungskategorien
	 */
	
	public static LinkedHashMap<String, ImpactCategory> getAllInstances() {
		LinkedHashMap<String,ImpactCategory> a = new LinkedHashMap<String,ImpactCategory>();
		for (String s : MCAObject.getAllNames(ImpactCategory.class)) {
			a.put(s, (ImpactCategory)MCAObject.getAllInstances(ImpactCategory.class).get(s));			
		}
		return a;
	}
	
	/**
	 * Erzeugt eine neue Wirkungskategorie.
	 * @param name
	 * der Name der neuen Wirkungskategorie.
	 * @param einheit
	 * legt die Einheit fest, in der die Wirkungskategorie
	 * quantifiziert wird.
	 * @return
	 * ... die neue Wirkungskategorie
	 */
	
	public static ImpactCategory instance(String name, CategoryIndicator einheit) {
		ImpactCategory wk = new ImpactCategory(name, einheit);
		return wk;	
	}
	
	/**
	 * @return
	 * ... die Einheit, in der die Wirkungskategorie
	 * quantifiziert wird.
	 */

	public CategoryIndicator getEinheit() {
		return einheit;
	}
	
	/**
	 * Liefert eine Wirkungskategorie
	 * @param name
	 * ist der Name der Wirkungskategorie
	 * @return
	 * ... die gesuchte Wirkungskategorie
	 */
	
	public static ImpactCategory getInstance(String name) {
		return getAllInstances().get(name);
	}

}
