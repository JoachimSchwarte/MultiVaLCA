/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

import java.util.LinkedHashMap;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.511
 */

public class ImpactCategory extends MCAObject {
	
	// Klassenvariable:
	
	private static LinkedHashMap<String, ImpactCategory> allInstances = new LinkedHashMap<String, ImpactCategory>();
	
	// Instanzvariablen:
	
	private CategoryIndicator einheit;
	
	// Konstruktor basierend auf den Instanzvariablen:
	
	private ImpactCategory(String name, CategoryIndicator einheit) {
		super(name);
		this.einheit = einheit;
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
	 * Überprüft, ob bereits eine Wirkungskategorie
	 * des gegebenen Namens existiert.
	 * @param string
	 * ist der zu prüfende Name
	 * @return
	 * ... den Wahrheitswert, den die Überprüfung liefert
	 */
	
	public static boolean containsName(String string) {
		return allInstances.keySet().contains(string);
	}
	/**
	 * @return
	 * ... alle vorhandenen Wirkungskategorien
	 */
	
	public static LinkedHashMap<String, ImpactCategory> getAllInstances() {
		return allInstances;
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
	
	public static ImpactCategory getInstance(String name) {
		return allInstances.get(name);
	}

}
