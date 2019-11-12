package de.unistuttgart.iwb.multivalca;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Diese Klasse dient zur Erzeugung und Nutzung
 * von Objekten des Typs "Produktdeklarationgruppe".
 * 
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.530
 */

public class ProductDeclarationGroup extends MCAObject 
implements ImpactValueMaps {


	// Instanzvariablen:	

	private LinkedList<ImpactValueMaps> decList = new LinkedList<ImpactValueMaps>();


	// Konstruktor:

	private ProductDeclarationGroup(String name) {
		super(name);		
	}

	// Methoden:

	/**
	 * Überprüft, ob bereits eine Produktdeklarationgruppe
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
	 * ... alle vorhandenen Produktdeklarationengruppen
	 */

	public static HashMap<String, ProductDeclarationGroup> getAllInstances() {
		LinkedHashMap<String,ProductDeclarationGroup> a = new LinkedHashMap<String,ProductDeclarationGroup>();
		for (String s : MCAObject.getAllNames(ProductDeclarationGroup.class)) {
			a.put(s, (ProductDeclarationGroup)MCAObject.getAllInstances(ProductDeclarationGroup.class).get(s));			
		}
		return a;
	}

	/**
	 * Liefert eine Produktdeklarationsgruppe	 * 
	 * @param name
	 * ist der Name der Produktdeklarationsgruppe
	 * @return 
	 * ... die gesuchte Produktdeklarationsgruppe
	 */

	public static ProductDeclarationGroup getInstance(String name) {
		return getAllInstances().get(name);		
	}

	/**
	 * Erzeugt ein benanntes Produktdeklarationsgruppenobjekt
	 * @param name
	 * ist der Name der bilanzierten Produktdeklarationsgruppe
	 * @return
	 * ... das Produktdeklarationsgruppenobjekt
	 */

	public static ProductDeclarationGroup instance(String name) {
		return new ProductDeclarationGroup(name);
	}

	/**
	 * Liefert alle Wirkungskategorien und 
	 * deren zugeordneten Wert für
	 * die relevante Bewertungsmethode
	 * @param bm
	 * Die relevante Bewertungsmethode 
	 * @return
	 * ... die der Bewertungsmethode zugehörigen 
	 * Wirkungskategorien und deren zugeordneten Wert 
	 */

	@Override
	public LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> getImpactValueMap(LCIAMethod bm) {
		LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> wv =
				new LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>>();

		for (String wkName : bm.categoryList().keySet()) {
			ImpactCategory wk = bm.categoryList().get(wkName); 
			LinkedHashMap<ValueType, Double> valueMap = new LinkedHashMap<ValueType, Double>();
			valueMap.put(ValueType.MeanValue, 0.0);
			valueMap.put(ValueType.LowerBound, Double.POSITIVE_INFINITY);
			valueMap.put(ValueType.UpperBound, Double.NEGATIVE_INFINITY);
			wv.put(wk, valueMap);	


			for (ImpactValueMaps ivm : decList) {
				if (ivm.getImpactValueMap(bm).containsKey(wk)) {
					Double mv = wv.get(wk).get(ValueType.MeanValue);
					mv = mv + ivm.getImpactValueMap(bm).get(wk).get(ValueType.MeanValue)/decList.size();
					wv.get(wk).put(ValueType.MeanValue, mv);
					Double lb1 = wv.get(wk).get(ValueType.LowerBound);
					Double lb2 = ivm.getImpactValueMap(bm).get(wk).get(ValueType.LowerBound);
					if (lb2 < lb1) {
						wv.get(wk).put(ValueType.LowerBound, lb2);
					}
					Double ub1 = wv.get(wk).get(ValueType.UpperBound);
					Double ub2 = ivm.getImpactValueMap(bm).get(wk).get(ValueType.UpperBound);
					if (ub2 > ub1) {
						wv.get(wk).put(ValueType.UpperBound, ub2);
					} 							
				} else {
					Double lb1 = wv.get(wk).get(ValueType.LowerBound);
					Double lb2 = 0.0;
					if (lb2 < lb1) {
						wv.get(wk).put(ValueType.LowerBound, lb2);
					}
					Double ub1 = wv.get(wk).get(ValueType.UpperBound);
					Double ub2 = 0.0;
					if (ub2 > ub1) {
						wv.get(wk).put(ValueType.UpperBound, ub2);
					} 
				}
			}


		}

		return wv;
	}

	/**
	 * Ergänzt die Produktdeklarationsgruppe um eine weitere Produktdeklaration	 * 
	 * @param dec
	 * ... ist die zu ergänzende Produktdeklaration. Dies kann ein beliebiges
	 * Objekt einer Klasse, die das Interface ImpactValueMaps
	 * implementiert, sein.
	 */

	public void addDeclaration(ImpactValueMaps dec) {
		decList.add(dec);
	}

	/**
	 * @return 
	 * ... die Liste der Bestandteile, aus denen sich die Produktdeklarationsgruppe
	 * zusammensetzt
	 */

	public LinkedList<ImpactValueMaps> getDecList(){
		return decList;
	}
}
