package de.unistuttgart.iwb.multivalca;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 * Diese Klasse dient zur Erzeugung und Nutzung
 * von Objekten des Typs "Wirkungsvektorgruppe".
 * Mit dieser Klasse können alle Objekte, welche das Interface "ImpactValueMaps" implementieren,
 * zu Gruppen zusammengefasst werden.
 * 
 * @author Dr.-Ing. Joachim Schwarte, Helen Hein, Johannes Dippon
 * @version 0.700
 */

public class ImpactValueMapGroup extends MCAObject 
implements ImpactValueMaps {


	// Instanzvariablen:	

	private IVMGroupType type;
	private LinkedHashSet<ImpactValueMaps> ivmList = new LinkedHashSet<ImpactValueMaps>();


	// Konstruktor:

	private ImpactValueMapGroup(String name, IVMGroupType type) {
		super(name);
		this.type = type;
	}

	// Methoden:


	/**
	 * Überprüft, ob bereits eine Wirkungsvektorgruppe
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
	 * ... alle vorhandenen Wirkungsvektorgruppen
	 */

	public static LinkedHashMap<String, ImpactValueMapGroup> getAllInstances() {
		LinkedHashMap<String,ImpactValueMapGroup> a = new LinkedHashMap<String,ImpactValueMapGroup>();
		for (String s : MCAObject.getAllNames(ImpactValueMapGroup.class)) {
			a.put(s, (ImpactValueMapGroup)MCAObject.getAllInstances(ImpactValueMapGroup.class).get(s));			
		}
		return a;
	}

	/**
	 * Liefert eine Wirkungsvektorgruppe
	 * @param name
	 * ist der Name der Wirkungsvektorgruppe
	 * @return 
	 * ... die gesuchte Wirkungsvektorgruppe
	 */

	public static ImpactValueMapGroup getInstance(String name) {
		return getAllInstances().get(name);		
	}

	/**
	 * Erzeugt eine benannte Wirkungsvektorgruppe
	 * @param name
	 * ist der Name der  Wirkungsvektorgruppe
	 * @param type
	 * ist der Objekttyp der Wirkungsvektorgruppe
	 * @return
	 * ... die Wirkungsvektorgruppe
	 */

	public static ImpactValueMapGroup instance(String name, IVMGroupType type) {
		return new ImpactValueMapGroup(name, type);
	}

	/**
	 * Liefert alle Wirkungskategorien der Gruppe und 
	 * deren zugeordneten Wert 
	 * @return
	 * ... alle Wirkungskategorien der Gruppe und deren zugeordneten Wert 
	 */

	public LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> getImpactValueMap() {
		LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> wv =
				new LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>>();
		LinkedHashSet<String> wkList = new LinkedHashSet<String>();
		
		for (ImpactValueMaps ivm : ivmList) {
			for (ImpactCategory wk : ivm.getImpactValueMap().keySet()) {
				wkList.add(wk.getName());	
			}
		}		

		for (String wkName : wkList) {
			ImpactCategory wk = ImpactCategory.getInstance(wkName); 
			LinkedHashMap<ValueType, Double> valueMap = new LinkedHashMap<ValueType, Double>();
			valueMap.put(ValueType.MeanValue, 0.0);
			valueMap.put(ValueType.LowerBound, Double.POSITIVE_INFINITY);
			valueMap.put(ValueType.UpperBound, Double.NEGATIVE_INFINITY);
			wv.put(wk, valueMap);	


			for (ImpactValueMaps ivm : ivmList) {
				if (ivm.getImpactValueMap().containsKey(wk)) {
					Double mv = wv.get(wk).get(ValueType.MeanValue);
					mv = mv + ivm.getImpactValueMap().get(wk).get(ValueType.MeanValue)/ivmList.size();
					wv.get(wk).put(ValueType.MeanValue, mv);
					Double lb1 = wv.get(wk).get(ValueType.LowerBound);
					Double lb2 = ivm.getImpactValueMap().get(wk).get(ValueType.LowerBound);
					if (lb2 < lb1) {
						wv.get(wk).put(ValueType.LowerBound, lb2);
					}
					Double ub1 = wv.get(wk).get(ValueType.UpperBound);
					Double ub2 = ivm.getImpactValueMap().get(wk).get(ValueType.UpperBound);
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


			for (ImpactValueMaps ivm : ivmList) {
				if (ivm.getImpactValueMap(bm).containsKey(wk)) {
					Double mv = wv.get(wk).get(ValueType.MeanValue);
					mv = mv + ivm.getImpactValueMap(bm).get(wk).get(ValueType.MeanValue)/ivmList.size();
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
	 * Ergänzt die Wirkungsvektorgruppe um eine weiteres Element
	 * @param ivm
	 * ... ist das zu ergänzende Element. Dies kann ein beliebiges
	 * Objekt einer Klasse, die das Interface ImpactValueMaps
	 * implementiert, sein.
	 */

	public void addImpactValueMap(ImpactValueMaps ivm) {
		ivmList.add(ivm);
	}

	/**
	 * @return 
	 * ... die Liste der Bestandteile, aus denen sich die Wirkungsvektorgruppe
	 * zusammensetzt
	 */

	public LinkedHashSet<ImpactValueMaps> getIVMList(){
		return ivmList;
	}

	/**
	 * Legt den Objekttyp der Gruppe fest 
	 * @param type
	 * ... ist der Objekttyp der Gruppe. 
	 */

	public void setType(IVMGroupType type) {
		this.type = type;
	}

	/**
	 * @return 
	 * ... den Objekttyp der Gruppe
	 */

	public IVMGroupType getType() {
		return type;
	}

}
