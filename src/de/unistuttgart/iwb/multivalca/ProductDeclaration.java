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
	 * �berpr�ft, ob bereits eine Produktdeklaration
	 * des genannten Namens existiert.
	 * @param string
	 * ist der zu pr�fende Name
	 * @return
	 * ... den Wahrheitswert, den die �berpr�fung liefert
	 */
	
	public static boolean containsName(String string) {
		return getAllInstances().containsKey(string);
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
	 * @param string
	 * Name des bilanzierten Produkts
	 * @return
	 * ... die gesuchte Produktdeklaration
	 */
	
	public static ProductDeclaration getInstance(String string) {
		return getAllInstances().get(string);		
	}
	
	/**
	 * Erzeugt ein benanntes 
	 * Produktobjekt, das noch keine Daten aus einer 
	 * Wirkungsabsch�tzung enth�lt.
	 * @param name
	 * ist der Name des bilanzierten Produkts.
	 * @return
	 * ... das Produktobjekt
	 */
	
	public static ProductDeclaration instance(String name, FlowUnit einheit) {
		return new ProductDeclaration(name, einheit);
	}
	
	/**
	 * F�gt dem bilanzierten Produkt eine ImpactCategory und
	 * den dieser Kategorie zugeordneten Wert zu.
	 * @param wk
	 * Die hinzuzuf�gende ImpactCategory
	 * @param wert
	 * Der zur ImpactCategory geh�rige Wert
	 */
	
	public void addWirkung(ImpactCategory wk, LinkedHashMap<ValueType, Double> values){
		wvAlle.put(wk, values);
	}
	
	/**
	 * @return
	 * ... die (wesentliche) LCIAMethod f�r das vorliegende
	 * bilanzierte Produkt (= Produktdeklaration)
	 */
	
	public LCIAMethod getBM() {
		return bm;
	}
	
	public FlowUnit getEinheit() {
		return einheit;
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

}
