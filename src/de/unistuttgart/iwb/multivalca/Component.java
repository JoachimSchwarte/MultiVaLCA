/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

import java.util.LinkedHashMap;

/**
 * Diese Klasse dient zur Erzeugung und Nutzung
 * von Objekten des Typs "Komponente".
 * 
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.501
 */

public class Component {
	
	// Klassenvariable:
	
	private static LinkedHashMap<String,Component> allInstances = new LinkedHashMap<String,Component>();
	
	// Instanzvariablen:	

	private String name;

}
