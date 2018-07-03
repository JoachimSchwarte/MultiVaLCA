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
 * @version 0.530
 */

public class Component extends MCAObject  
implements ImpactValueMaps {
	
	// Instanzvariablen:	
	
	private ImpactValueMaps bezugsKomponente;
	private LinkedHashMap<ValueType, Double> mengen = new LinkedHashMap<ValueType, Double>();
	
	// Konstruktor:

	private Component(String name, ImpactValueMaps komponente, double menge) {
		super(name);
		this.bezugsKomponente = komponente;
		mengen.put(ValueType.MeanValue, menge);
		mengen.put(ValueType.LowerBound, menge);
		mengen.put(ValueType.UpperBound, menge);
	}

	// Methoden:
	
	/**
	 * �berpr�ft, ob bereits eine Component
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
	 * Erzeugt eine Component
	 * @param name
	 * �bergibt der Namen der Component. 
	 * Dieser muss eindeutig sein.
	 * @param kompoName
	 * ist der Name eines Objekt einer Klasse, die das Interface
	 * ImpactValueMaps implementiert.
	 * @param menge
	 * ist die zugeh�rige Mengenangabe.
	 * @return
	 * ... die neue Component
	 */
	
	public static Component newInstance(String name, String kompoName, double menge) {
		ImpactValueMaps kompo = ProcessModule.instance("dummy"); 
		ProcessModule.removeInstance("dummy");
		kompo.setName(kompoName);
		if (getAllInstances().containsKey(name) == false) {
			new Component(name, kompo, menge);	
		} 
		return getAllInstances().get(name);
	}
	
	/**
	 * Die dreiparametrige Methode newInstance erzeugt 
	 * unter Verwendung des privaten Konstruktors eine vollst�ndige
	 * Component.
	 * @param name
	 * �bergibt der Namen der Component. 
	 * Dieser muss eindeutig sein.
	 * @param komponente
	 * ist ein bliebiges Objekt einer Klasse, die das Interface
	 * ImpactValueMaps implementiert.
	 * @param menge
	 * ist die zugeh�rige Mengenangabe. 
	 * @return
	 * ... die neue Component
	 */
	
	public static Component newInstance(String name, ImpactValueMaps komponente, double menge) {
		if (getAllInstances().containsKey(name) == false) {
			new Component(name, komponente, menge);	
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

/*	@Override
	public void setName(String string) {
		NameCheck.removeWVName(this.name);
		NameCheck.getInstance().addWVName(string);
		this.name = string;		
	}*/
}
