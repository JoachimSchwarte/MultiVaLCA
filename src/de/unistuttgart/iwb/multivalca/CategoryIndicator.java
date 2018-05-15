/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

import java.util.LinkedHashMap;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.495
 */

public class CategoryIndicator {
	
	// Klassenvariable:
	
		private static LinkedHashMap<String, CategoryIndicator> allInstances = new LinkedHashMap<String, CategoryIndicator>();
	
	// Instanzvariablen:
	
	private String name;
	
	// Konstruktor basierend auf den Instanzvariablen:

	private CategoryIndicator(String name) {
		super();
		this.setName(name);
		allInstances.put(name, this);
	}
	
	// Methoden:
	
	/**
	 * Löscht alle Klassenvariablen
	 */
	
	public static void clear() {
		allInstances.clear();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static CategoryIndicator instance(String name) {
		if (allInstances.containsKey(name) == false) {
			new CategoryIndicator(name);
		}
		return allInstances.get(name);
	}

}
