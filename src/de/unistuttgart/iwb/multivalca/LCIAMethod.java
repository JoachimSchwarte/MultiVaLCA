/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

import java.util.LinkedHashMap;

/**
 * Diese Klasse dient zur Erzeugung und Nutzung
 * von Objekten des Typs "Bewertungsmethode".
 * 
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.536
 */

public class LCIAMethod extends MCAObject {

	// Instanzvariablen:
	
	private LinkedHashMap<String, CharacFactor> faktorSet = 
			new LinkedHashMap<String, CharacFactor>();
	private LinkedHashMap<String, ImpactCategory> wkl = 
			new LinkedHashMap<String, ImpactCategory>();

	// Konstruktor:
	
	private LCIAMethod(String name) {
		super(name);
	}
	
	// Methoden:
	
	/**
	 * �berpr�ft, ob bereits eine Bewertungsmethode
	 * des genannten Namens existiert.
	 * @param name
	 * ist der zu pr�fende Name
	 * @return
	 * ... den Wahrheitswert, den die �berpr�fung liefert
	 */
	
	public static boolean containsName(String name) {
		return getAllInstances().containsKey(name);
	}
	
	/**
	 * Die instance-Methode erzeugt unter Verwendung des
	 * privaten Konstruktors eine neue Bewertungsmethode
	 * oder gibt eine bereits existierende Bewertungsmethode
	 * zur�ck.
	 * @param name
	 * ist der Name der Bewertungsmethode.
	 * @return
	 * ... neue oder bereits zuvor existierende
	 * Bewertungsmethode
	 */
	
	public static LCIAMethod instance(String name) {
		if (!getAllInstances().containsKey(name)) {
			new LCIAMethod(name);
		} 
		return getAllInstances().get(name);
	}
	
	/**
	 * F�gt der Bewertungsmethode einen weiteren
	 * Charakterisierungsfaktor hinzu.
	 * @param cv
	 * Der Charakterisierungsfaktor, der der Bewertungsmethode 
	 * hinzugef�gt werden soll
	 */
	
	public void addFactor(CharacFactor cv) {
		faktorSet.put(cv.getName(), cv);
		wkl.put(cv.getWirkung().getName(), cv.getWirkung());	
	}

	/**
	 * F�gt der Liste der Wirkungskategorien einen Eintrag
	 * hinzu.
	 * @param wk
	 * Die Wirkungskategorie, die der Liste hinzugef�gt werden 
	 * soll.
	 */
	
	public void addCategory(ImpactCategory wk) {
		wkl.put(wk.getName(), wk);
	}
	
	/**
	 * @return
	 * ... Liste aller vorhandenen Charakterisierungsfaktoren
	 */
	
	public LinkedHashMap<String, CharacFactor> getFactorSet() {
		return faktorSet;
	}

		
	/**
	 * @return
	 * ... Liste derjenigen Wirkungskategorien, denen durch die
	 * vorhandenen Charakterisierungsfaktoren Fl�sse quantifiziert
	 * zugeordnet sind oder die durch Verwendung der Methode
	 * addWK(...) explizit der Liste hinzugef�gt wurden.
	 */
	
	public LinkedHashMap<String, ImpactCategory> categoryList() {	
		return wkl;
	}
	
	/**
	 * @return
	 * ... alle vorhandenen Bewertungsmethoden
	 */
	
	public static LinkedHashMap<String, LCIAMethod> getAllInstances() {
		LinkedHashMap<String,LCIAMethod> a = new LinkedHashMap<String,LCIAMethod>();
		for (String s : MCAObject.getAllNames(LCIAMethod.class)) {
			a.put(s, (LCIAMethod)MCAObject.getAllInstances(LCIAMethod.class).get(s));			
		}
		return a;
	}
	
		

}
