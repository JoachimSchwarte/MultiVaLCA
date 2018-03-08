/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

import java.util.HashMap;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.41
 */

public class ImpactCategory {
	
	// Klassenvariable:
	
	private static HashMap<String, ImpactCategory> allInstances = new HashMap<String, ImpactCategory>();
	
	// Instanzvariablen:
	
	private String name;
	private CategoryIndicator einheit;
	
	// Konstruktor basierend auf den Instanzvariablen:
	
	private ImpactCategory(String name, CategoryIndicator einheit) {
		super();
		this.name = name;
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
	
	public static HashMap<String, ImpactCategory> getAllInstances() {
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
	
	/**
	 * @return
	 * ... den Namen der Wirkungskategorie.
	 */
	
	public String getName() {
		return name;
	}
	
	public static ImpactCategory getInstance(String name) {
		return allInstances.get(name);
	}

}
