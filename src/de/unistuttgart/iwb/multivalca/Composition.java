/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

import java.util.LinkedHashMap;

/**
 * Diese Klasse dient zur Erzeugung und Nutzung
 * von Objekten des Typs "Komposition".
 * 
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.501
 */

public class Composition {
	
	// Klassenvariable:
	
	private static LinkedHashMap<String,Composition> allInstances = new LinkedHashMap<String,Composition>();
	
	// Instanzvariablen:	

	private String name;

}
