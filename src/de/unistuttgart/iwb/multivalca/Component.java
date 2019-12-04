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
 * @version 0.540
 */

public class Component extends MCAObject  
implements ImpactValueMaps {
	
	// Instanzvariablen:	
	
	private ImpactValueMaps bezugsKomponente;
	private LinkedHashMap<ValueType, Double> mengen = new LinkedHashMap<ValueType, Double>();
	
	// Konstruktor:

	private Component(String name, ImpactValueMaps komponente) {
		super(name);
		this.bezugsKomponente = komponente;
	}

	// Methoden:
	
	public void setValues(LinkedHashMap<ValueType, Double> values) {
		mengen.put(ValueType.MeanValue, values.get(ValueType.MeanValue));
		mengen.put(ValueType.LowerBound, values.get(ValueType.LowerBound));
		mengen.put(ValueType.UpperBound, values.get(ValueType.UpperBound));
	}
	
	public LinkedHashMap<ValueType, Double> getValues() {
		return mengen;
	}

	/**
	 * �berpr�ft, ob bereits eine Component
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
	 * @return
	 * ... alle vorhandenen Componentn
	 */
	
	public static LinkedHashMap<String, Component> getAllInstances() {
		LinkedHashMap<String,Component> a = new LinkedHashMap<String,Component>();
		for (String s : MCAObject.getAllNames(Component.class)) {
			a.put(s, (Component)MCAObject.getAllInstances(Component.class).get(s));			
		}
		return a;
	}
	
	/**
	 * Gibt eine bereits vorhandene Component zur�ck.
	 * @param name
	 * Name der gesuchten Component
	 * @return
	 * ... die gesuchte Component
	 */
	
	public static Component getInstance(String name) { 
		return getAllInstances().get(name);
	}
	
	/**
	 * Erzeugt eine neue Komponente
	 * @param name
	 * Name der Komponente 
	 * @param refName
	 * ist ein bliebiges Objekt einer Klasse, die das Interface
	 * ImpactValueMaps implementiert.
	 * @return
	 * ... die neue Komponente
	 */
	
	public static Component newInstance(String name, String refName) {
		ImpactValueMaps komponente = null;
		if (ProductSystem.containsName(refName)) {
			komponente = ProductSystem.getInstance(refName);
		}
		if (ProductDeclaration.containsName(refName)) {
			komponente = ProductDeclaration.getInstance(refName);
		}
		if (!getAllInstances().containsKey(name)) {
			new Component(name, komponente);	
		} 
		return getAllInstances().get(name);
	}
	
	/**
	 * @return
	 * ... das Objekt des Interface-Typs ImpactValueMaps,
	 * dass durch die Component quantifiziert wird.
	 */

	public ImpactValueMaps getKomponente() {
		return bezugsKomponente;
	}
	
	@Override
	public LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> getImpactValueMap() {
		return null;
	}
	
	/**
	 * Liefert den Wirkungsvektor der Komponente f�r
	 * die relevante Bewertungsmethode
	 * @param bm
	 * die relevante Bewertungsmethode 
	 * @return
	 * ... den zugeh�rigen Wirkungsvektor 
	 */

	@Override
	public LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> getImpactValueMap(LCIAMethod bm) {
		String kompoName = bezugsKomponente.getName();
		if (ProcessModule.containsName(kompoName)) {
			bezugsKomponente = ProcessModule.getInstance(kompoName);					
		}
		if (ProductSystem.containsName(kompoName)) {
			bezugsKomponente = ProductSystem.getInstance(kompoName);					
		}
		if (ProductDeclaration.containsName(kompoName)) {
			bezugsKomponente = ProductDeclaration.getInstance(kompoName);					
		}
		if (Component.containsName(kompoName)) {
			bezugsKomponente = Component.getInstance(kompoName);					
		}
		if (Composition.containsName(kompoName)) {
			bezugsKomponente = Composition.getInstance(kompoName);					
		}
		LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> wvKomponente = bezugsKomponente.getImpactValueMap(bm);
		for (String wkName : bm.categoryList().keySet()){
			ImpactCategory wk = bm.categoryList().get(wkName);
			LinkedHashMap<ValueType, Double> values = new LinkedHashMap<ValueType, Double>();
			for (ValueType vt : values.keySet()) {
				values.put(vt, values.get(vt)*mengen.get(ValueType.MeanValue));
			}
			wvKomponente.put(wk, values);
		}
		return wvKomponente;
	}
}
