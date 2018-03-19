/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

import java.util.HashMap;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.481
 */

public class LCIAMethod {
	
	// Klassenvariable:
	
	private static HashMap<String, LCIAMethod> allInstances = new HashMap<String, LCIAMethod>();
	
	// Instanzvariablen:
	
	private String name;
	private HashMap<String, CharacFactor> faktorSet = 
			new HashMap<String, CharacFactor>();
	private HashMap<String, ImpactCategory> wkl = 
			new HashMap<String, ImpactCategory>();

	// Konstruktor:
	
	private LCIAMethod(String name) {
		super();
		this.name = name;
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
	 * Überprüft, ob bereits eine Bewertungsmethode
	 * des genannten Namens existiert.
	 * @param string
	 * ist der zu prüfende Name
	 * @return
	 * ... den Wahrheitswert, den die Überprüfung liefert
	 */
	
	public static boolean containsName(String string) {
		return allInstances.containsKey(string);
	}
	
	/**
	 * @return
	 * ... alle vorhandenen Bewertungsmethoden
	 */
	 
	public static HashMap<String, LCIAMethod> getAllLMs() {
		return allInstances;
	}
	
	/**
	 * Die instance-Methode erzeugt unter Verwendung des
	 * privaten Konstruktors eine neue Bewertungsmethode
	 * oder gibt eine bereits existierende Bewertungsmethode
	 * zurück.
	 * @param name
	 * ist der Name der Bewertungsmethode.
	 * @return
	 * ... neue oder bereits zuvor existierende
	 * Bewertungsmethode
	 */
	
	public static LCIAMethod instance(String name) {
		if (allInstances.containsKey(name) == false) {
			new LCIAMethod(name);
		} 
		return allInstances.get(name);
	}
	
	/**
	 * Fügt der Bewertungsmethode einen weiteren
	 * Charakterisierungsfaktor hinzu.
	 * @param cv
	 * Der Charakterisierungsfaktor, der der Bewertungsmethode 
	 * hinzugefügt werden soll
	 */
	
	public void addFactor(CharacFactor cv) {
		faktorSet.put(cv.getName(), cv);
		wkl.put(cv.getWirkung().getName(), cv.getWirkung());	
	}

	/**
	 * Fügt der Liste der Wirkungskategorien einen Eintrag
	 * hinzu.
	 * @param wk
	 * Die Wirkungskategorie, die der Liste hinzugefügt werden 
	 * soll.
	 */
	
	public void addCategory(ImpactCategory wk) {
		wkl.put(wk.getName(), wk);
	}
	
	/**
	 * @return
	 * ... Liste aller vorhandenen Charakterisierungsfaktoren
	 */
	
	public HashMap<String, CharacFactor> getFactorSet() {
		return faktorSet;
	}
	
	/**
	 * @return
	 * ... den Namen der Bewertugsmethode
	 */
	
	public String getName() {
		return name;
	}
		
	/**
	 * @return
	 * ... Liste derjenigen Wirkungskategorien, denen durch die
	 * vorhandenen Charakterisierungsfaktoren Flüsse quantifiziert
	 * zugeordnet sind oder die durch Verwendung der Methode
	 * addWK(...) explizit der Liste hinzugefügt wurden.
	 */
	
	public HashMap<String, ImpactCategory> categoryList() {	
		return wkl;
	}
	
	public static HashMap<String, LCIAMethod> getAllInstances() {
		return allInstances;
	}
	
		

}
