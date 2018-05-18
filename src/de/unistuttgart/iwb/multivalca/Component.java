/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

import java.util.LinkedHashMap;
import java.util.LinkedHashMap;

/**
 * Diese Klasse dient zur Erzeugung und Nutzung
 * von Objekten des Typs "Komponente".
 * 
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.502
 */

public class Component 
implements ImpactValueMaps {
	
	// Klassenvariable:
	
	private static LinkedHashMap<String,Component> allInstances = new LinkedHashMap<String,Component>();
	
	// Instanzvariablen:	

	private String name;	
	private ImpactValueMaps komponente;
	private Double menge;
	
	// Konstruktor:

	private Component(String name, ImpactValueMaps komponente, double menge) {
		super();
		this.name = name;
		this.komponente = komponente;
		this.menge = menge;
		allInstances.put(name, this);
		NameCheck.getInstance().addWVName(name);
	}

	// Methoden:
	
	/**
	 * Löscht alle Klassenvariablen
	 */
	
	public static void clear() {
		allInstances.clear();
	}
	
	/**
	 * Überprüft, ob bereits eine Component
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
	 * ... alle vorhandenen Componentn
	 */
	
	public static LinkedHashMap<String, Component> getAllInstances() {
		return allInstances;
	}
	
	/**
	 * Gibt eine bereits vorhandene Component zurück.
	 * @param name
	 * Name der gesuchten Component
	 * @return
	 * ... die gesuchte Component
	 */
	
	public static Component getInstance(String name) { 
		return allInstances.get(name);
	}
	
	/**
	 * Erzeugt eine Component
	 * @param name
	 * übergibt der Namen der Component. 
	 * Dieser muss eindeutig sein.
	 * @param kompoName
	 * ist der Name eines Objekt einer Klasse, die das Interface
	 * ImpactValueMaps implementiert.
	 * @param menge
	 * ist die zugehörige Mengenangabe.
	 * @return
	 * ... die neue Component
	 */
	
	public static Component newInstance(String name, String kompoName, double menge) {
		ImpactValueMaps kompo = ProcessModule.instance("dummy"); 
		ProcessModule.removeInstance("dummy");
		kompo.setName(kompoName);
		if (allInstances.containsKey(name) == false) {
			new Component(name, kompo, menge);	
		} 
		return allInstances.get(name);
	}
	
	/**
	 * Die dreiparametrige Methode newInstance erzeugt 
	 * unter Verwendung des privaten Konstruktors eine vollständige
	 * Component.
	 * @param name
	 * übergibt der Namen der Component. 
	 * Dieser muss eindeutig sein.
	 * @param komponente
	 * ist ein bliebiges Objekt einer Klasse, die das Interface
	 * ImpactValueMaps implementiert.
	 * @param menge
	 * ist die zugehörige Mengenangabe. 
	 * @return
	 * ... die neue Component
	 */
	
	public static Component newInstance(String name, ImpactValueMaps komponente, double menge) {
		if (allInstances.containsKey(name) == false) {
			new Component(name, komponente, menge);	
		} 
		return allInstances.get(name);
	}
	
	/**
	 * @return
	 * ... das Objekt des Interface-Typs ImpactValueMaps,
	 * dass durch die Component quantifiziert wird.
	 */

	public ImpactValueMaps getKomponente() {
		return komponente;
	}
	
	/**
	 * @return
	 * ... die Mengenangabe.
	 */

	public Double getMenge() {
		return menge;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> getImpactValueMap(LCIAMethod bm) {
		String kompoName = komponente.getName();
		if (ProcessModule.containsName(kompoName)) {
			komponente = ProcessModule.getInstance(kompoName);					
		}
		if (ProductSystem.containsName(kompoName)) {
			komponente = ProductSystem.getInstance(kompoName);					
		}
		if (ProductDeclaration.containsName(kompoName)) {
			komponente = ProductDeclaration.getInstance(kompoName);					
		}
		if (Component.containsName(kompoName)) {
			komponente = Component.getInstance(kompoName);					
		}
		if (Composition.containsName(kompoName)) {
			komponente = Composition.getInstance(kompoName);					
		}
		LinkedHashMap<ImpactCategory, Double> wvKomponente = komponente.getImpactValueMap(bm);
		for (String wkName : bm.kategorieListe().keySet()){
			ImpactCategory wk = bm.kategorieListe().get(wkName);
			wvKomponente.put(wk, wvKomponente.get(wk)*menge);
		}
		return wvKomponente;
	}

	@Override
	public void setName(String string) {
		NameCheck.removeWVName(this.name);
		NameCheck.getInstance().addWVName(string);
		this.name = string;		
	}
}
