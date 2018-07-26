/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

import java.util.LinkedHashMap;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.532
 */

public class CategoryIndicator extends MCAObject {
	
	// Klassenvariable:
	
		private static LinkedHashMap<String, CategoryIndicator> allInstances = new LinkedHashMap<String, CategoryIndicator>();
	

	// Konstruktor basierend auf den Instanzvariablen:

	private CategoryIndicator(String name) {
		super(name);
		allInstances.put(name, this);
	}
	
	// Methoden:
	
	/**
	 * Löscht alle Klassenvariablen
	 */
	
	public static void clear() {
		allInstances.clear();
	}

	
	public static CategoryIndicator instance(String name) {
		if (!allInstances.containsKey(name)) {
			new CategoryIndicator(name);
		}
		return allInstances.get(name);
	}

}
