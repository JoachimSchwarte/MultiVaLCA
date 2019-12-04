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
 * @version 0.530
 */

public class ProductDeclaration extends MCAObject 
implements ImpactValueMaps {
	
	// Instanzvariablen:	

	private LCIAMethod bm;
	private FlowUnit einheit;
	private LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> wvAlle = new LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>>();
	
	// Konstruktor:

	private ProductDeclaration(String name, FlowUnit einheit) {
		super(name);
		this.einheit = einheit;
	}
	
	// Methoden:
	
	/**
	 * Überprüft, ob bereits eine Produktdeklaration
	 * des genannten Namens existiert.
	 * @param name
	 * ist der zu prüfende Name
	 * @return
	 * ... den Wahrheitswert, den die Überprüfung liefert
	 */
	
	public static boolean containsName(String name) {
		return getAllInstances().containsKey(name);
	}
	
	/**
	 * @return
	 * ... alle vorhandenen Produktdeklarationen
	 */
	
	public static HashMap<String, ProductDeclaration> getAllInstances() {
		LinkedHashMap<String,ProductDeclaration> a = new LinkedHashMap<String,ProductDeclaration>();
		for (String s : MCAObject.getAllNames(ProductDeclaration.class)) {
			a.put(s, (ProductDeclaration)MCAObject.getAllInstances(ProductDeclaration.class).get(s));			
		}
		return a;
	}
	
	/**
	 * Liefert eine Produktdeklaration
	 * @param name
	 * ist der Name der bilanzierten Produktdeklaration
	 * @return
	 * ... die gesuchte Produktdeklaration
	 */
	
	public static ProductDeclaration getInstance(String name) {
		return getAllInstances().get(name);		
	}
	
	/**
	 * Erzeugt eine benannte 
	 * Produktdeklaration, die noch keine Daten aus einer 
	 * Wirkungsabschätzung enthält.
	 * @param name
	 * ist der Name der bilanzierten Produktdeklaration.
	 * @param einheit
	 * ist die Einheit der bilanzierten Produktdeklaration.
	 * @return
	 * ... die Produktdeklaration
	 */
	
	public static ProductDeclaration instance(String name, FlowUnit einheit) {
		return new ProductDeclaration(name, einheit);
	}
	
	/**
	 * Fügt dem bilanzierten Produkt eine ImpactCategory und
	 * den dieser Kategorie zugeordneten Wert zu.
	 * @param wk
	 * Die hinzuzufügende ImpactCategory
	 * @param values
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
	
	/**
	 * @return
	 * ... die Einheit für das vorliegende
	 * bilanzierte Produkt (= Produktdeklaration)
	 */
	
	public FlowUnit getEinheit() {
		return einheit;
	}
	
	@Override
	public LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> getImpactValueMap(){
		return wvAlle;
	}
	
	/**
	 * Liefert den Wirkungsvektor der Produktdeklaration für
	 * die relevante Bewertungsmethode
	 * @param bm
	 * die relevante Bewertungsmethode 
	 * @return
	 * ... den zugehörigen Wirkungsvektor 
	 */	
	
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
	 * @param bm
	 * Die (wesentliche) LCIAMethod
	 */
	
	public void setBM(LCIAMethod bm) {
		this.bm = bm;
	}

}
