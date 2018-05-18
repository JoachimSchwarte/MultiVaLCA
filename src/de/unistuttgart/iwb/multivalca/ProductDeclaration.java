/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Diese Klasse dient zur Erzeugung und Nutzung
 * von Objekten des Typs "Produktdeklaration".
 * 
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.502
 */

public class ProductDeclaration 
implements ImpactValueMaps {
	
	// Klassenvariable:
	
	private static LinkedHashMap<String,ProductDeclaration> allInstances = new LinkedHashMap<String,ProductDeclaration>();
	
	// Instanzvariablen:	

	private String name;
	private LCIAMethod bm;
	private LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> wvAlle = new LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>>();
	
	// Konstruktor:

	private ProductDeclaration(String name) {
		super();
		this.name = name;
		NameCheck.getInstance().addWVName(name);
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
	 * Überprüft, ob bereits eine Produktdeklaration
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
	 * ... alle vorhandenen Produktdeklarationen
	 */
	
	public static HashMap<String, ProductDeclaration> getAllInstances() {
		return allInstances;
	}
	
	/**
	 * Liefert eine Produktdeklaration
	 * @param string
	 * Name des bilanzierten Produkts
	 * @return
	 * ... die gesuchte Produktdeklaration
	 */
	
	public static ProductDeclaration getInstance(String string) {
		return allInstances.get(string);		
	}
	
	/**
	 * Erzeugt ein benanntes 
	 * Produktobjekt, das noch keine Daten aus einer 
	 * Wirkungsabschätzung enthält.
	 * @param name
	 * ist der Name des bilanzierten Produkts.
	 * @return
	 * ... das Produktobjekt
	 */
	
	public static ProductDeclaration instance(String name) {
		return new ProductDeclaration(name);
	}
	
	/**
	 * Fügt dem bilanzierten Produkt eine ImpactCategory und
	 * den dieser Kategorie zugeordneten Wert zu.
	 * @param wk
	 * Die hinzuzufügende ImpactCategory
	 * @param wert
	 * Der zur ImpactCategory gehörige Wert
	 */
	
	public void addWirkung(ImpactCategory wk, LinkedHashMap<ValueType, Double> values){
		wvAlle.put(wk, values);
	}
	
	/**
	 * @return
	 * ... die (wesentliche) LCIAMethod für das vorliegende
	 * bilanzierte Produkt (= Produktdeklaration)
	 */
	
	public LCIAMethod getBM() {
		return bm;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> getImpactValueMap(LCIAMethod bm) {
		LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> wvAktuell =
				new LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>>();
		for(ImpactCategory key : wvAlle.keySet()) {
			if (bm.categoryList().keySet().contains(key.getName())) {
				wvAktuell.put(key, wvAlle.get(key));
			}
		}
		return wvAktuell;	
	}
	
	/**
	 * Legt die (wesentliche) LCIAMethod fest.
	 * @param bm1
	 * Die (wesentliche) LCIAMethod
	 */
	
	public void setBM(LCIAMethod bm1) {
		bm = bm1;
	}

	@Override
	public void setName(String string) {
		NameCheck.removeWVName(this.name);
		NameCheck.getInstance().addWVName(string);
		this.name = string;		

	}
}
