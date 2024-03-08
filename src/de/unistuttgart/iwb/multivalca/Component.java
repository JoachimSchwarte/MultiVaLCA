/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

import java.util.LinkedHashMap;

/**
 * Diese Klasse dient zur Erzeugung und Nutzung
 * von Objekten des Typs "Komponente".
 * 
 * @author Dr.-Ing. Joachim Schwarte, Helen Hein, Johannes Dippon
 * @version 0.824
 */

public class Component extends MCAObject  
implements ImpactValueMaps {
	
	// Instanzvariablen:	
	
	private ImpactValueMaps bezugsKomponente;
	private LinkedHashMap<ValueType, Double> mengen = new LinkedHashMap<ValueType, Double>();
	private FlowUnit einheit;
	
	// Konstruktor:

	private Component(String name, ImpactValueMaps komponente) {
		super(name);
		this.bezugsKomponente = komponente;
	}

	// Methoden:

	/**
	 * Legt die Menge der Bezugskomponente fest.
	 * @param values
	 * die festzulegende Menge
	 */
	
	public void setValues(LinkedHashMap<ValueType, Double> values) {
		mengen.put(ValueType.MeanValue, values.get(ValueType.MeanValue));
		mengen.put(ValueType.LowerBound, values.get(ValueType.LowerBound));
		mengen.put(ValueType.UpperBound, values.get(ValueType.UpperBound));
	}
	
	/**
	 * @return
	 * die Menge der Bezugskomponente
	 */
	
	public LinkedHashMap<ValueType, Double> getValues() {
		return mengen;
	}

	/**
	 * Überprüft, ob bereits eine Komponente
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
	 * ... alle vorhandenen Komponenten
	 */
	
	public static LinkedHashMap<String, Component> getAllInstances() {
		LinkedHashMap<String,Component> a = new LinkedHashMap<String,Component>();
		for (String s : MCAObject.getAllNames(Component.class)) {
			a.put(s, (Component)MCAObject.getAllInstances(Component.class).get(s));			
		}
		return a;
	}
	
	/**
	 * Gibt eine bereits vorhandene Komponente zurück.
	 * @param name
	 * Name der gesuchten Komponente
	 * @return
	 * ... die gesuchte Komponente
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
		if (ImpactValueMapGroup.containsName(refName) && IVMGroupType.ProductDeclaration.equals(ImpactValueMapGroup.getInstance(refName).getType())) {
			komponente = ImpactValueMapGroup.getInstance(refName);
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
	 * Liefert den Wirkungsvektor der Komponente für
	 * die relevante Bewertungsmethode
	 * @param bm
	 * die relevante Bewertungsmethode 
	 * @return
	 * ... den zugehörigen Wirkungsvektor 
	 */

	@Override
	public LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> getImpactValueMap(LCIAMethod bm) {
		String kompoName = bezugsKomponente.getName();
		if (ProductSystem.containsName(kompoName)) {
			bezugsKomponente = ProductSystem.getInstance(kompoName);					
		}
		if (ProductDeclaration.containsName(kompoName)) {
			bezugsKomponente = ProductDeclaration.getInstance(kompoName);					
		}
		if (ImpactValueMapGroup.containsName(kompoName)) {
			bezugsKomponente = ImpactValueMapGroup.getInstance(kompoName);					
		}
		LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> wvKomponente = bezugsKomponente.getImpactValueMap(bm);
		for (String wkName : bm.categoryList().keySet()){
			ImpactCategory wk = bm.categoryList().get(wkName);
			LinkedHashMap<ValueType, Double> values = new LinkedHashMap<ValueType, Double>();
			values.put(ValueType.MeanValue, 0.0);
			values.put(ValueType.LowerBound, 0.0);
			values.put(ValueType.UpperBound, 0.0);
			for (ValueType vt : values.keySet()) {
				try {
					values.put(vt, wvKomponente.get(wk).get(vt)*mengen.get(vt));
				} catch (Exception e) {
					values.put(vt, 0.0);
				}
			}
			wvKomponente.put(wk, values);
		}
		return wvKomponente;
	}
	
	/**
	 * Legt die Einheit der Komponente fest.
	 * @param einheit
	 * die festzulegende Einheit
	 */
	
	public void setEinheit(FlowUnit einheit) {
		this.einheit = einheit;
	}
	
	/**
	 * @return
	 * die Einheit der Komponente
	 */
	
	public FlowUnit getEinheit() {
		return einheit;
	}
}