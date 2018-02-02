/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

import java.util.HashSet;

/**
 * Diese Klasse ist ein Singleton und dient
 * zur Verwaltung aller vergebenen Objekt-Namen,
 * für die Eindeutigkeit gewährleistet werden
 * muss.
 * 
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.1
 */

public class NameCheck {	

	private static NameCheck instance;
	private static HashSet<String>fvNames = new HashSet<String>();
	private static HashSet<String>wvNames = new HashSet<String>();
	
	// Konstruktor:
	
	private NameCheck() {}
	
	// Methoden:
	
	/**
	 * Löscht alle Klassenvariablen
	 */
	
	public static void clear() {
		fvNames.clear();
		wvNames.clear();
	}
	
	/**
	 * Überprüft, ob der Namen eines Objekts vom Interfacetyp
	 * Flussvektoren in der entsprechenden Liste vorhanden ist.
	 * @param name
	 * Name eines Objekts vom Interfacetyp
	 * Flussvektoren
	 * @return
	 * ... den Wahrheitswert, den die Überprüfung liefert
	 */
	
	public static boolean containsFVName(String name) {
	    return fvNames.contains(name);		
	}
	
	/**
	 * Überprüft, ob der Namen eines Objekts vom Interfacetyp
	 * Wirkungsvektor in der entsprechenden Liste vorhanden ist.
	 * @param name
	 * Name eines Objekts vom Interfacetyp
	 * Wirkungsvektor
	 * @return
	 * ... den Wahrheitswert, den die Überprüfung liefert
	 */
	
	public static boolean containsWVName(String name) {
	    return wvNames.contains(name);			    
	}
	
	/**
	 * Erzeugt eine Instanz der Klasse, sofern diese nicht
	 * bereits existiert.
	 * @return
	 * ... Instanz der Klasse NameCheck
	 */
	
	public static NameCheck getInstance() {
	    if (NameCheck.instance == null) {
	    	NameCheck.instance = new NameCheck();
	    }
	    return NameCheck.instance;	  
	}
	
	/**
	 * Entfernt den Namen eines Objekts vom Interfacetyp
	 * Flussvektoren aus der entsprechenden Liste.
	 * @param name
	 * Name eines Objekts vom Interfacetyp
	 * Flussvektoren
	 */

	public static void removeFVName(String name) {
	    fvNames.remove(name);		
	}
	
	/**
	 * Entfernt den Namen eines Objekts vom Interfacetyp
	 * Wirkungsvektor aus der entsprechenden Liste.
	 * @param name
	 * Name eines Objekts vom Interfacetyp
	 * Wirkungsvektor
	 */
	
	public static void removeWVName(String name) {
	    wvNames.remove(name);			    
	}
	
	/**
	 * Fügt den Namen eines Objekts vom Interfacetyp
	 * Flussvektoren der entsprechenden Liste hinz.
	 * @param name
	 * Name eines Objekts vom Interfacetyp
	 * Flussvektoren
	 */
	
	public void addFVName(String name) {
	    fvNames.add(name);		
	}
	
	/**
	 * Fügt den Namen eines Objekts vom Interfacetyp
	 * Wirkungsvektor der entsprechenden Liste hinzu.
	 * @param name
	 * Name eines Objekts vom Interfacetyp
	 * Wirkungsvektor
	 */
	
	public void addWVName(String name) {
	    wvNames.add(name);		
	}
}
