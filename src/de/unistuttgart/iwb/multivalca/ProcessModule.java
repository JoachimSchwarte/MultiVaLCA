/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;
import java.util.HashMap;

import de.unistuttgart.iwb.ivari.IvariScalar;

/**
 * Diese Klasse dient zur Erzeugung von Prozessmodulen.
 * 
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.487
 */

public class ProcessModule 
implements FlowValueMaps, ImpactValueMaps {
	
	// Klassenvariable:
	
	private static HashMap<String, ProcessModule> allInstances = new HashMap<String, ProcessModule>();
	
	// Instanzvariablen:
	
	private String name;
	private HashMap<Flow, HashMap<ValueType, Double>> efv = 
			new HashMap<Flow, HashMap<ValueType, Double>>(); //Elementarflüsse
	private HashMap<Flow, HashMap<ValueType, Double>> pfv = 
			new HashMap<Flow, HashMap<ValueType, Double>>(); //Produktflüsse
	
	// Konstruktor:
			
	private ProcessModule(String name) {
		super();
		setName(name);
		NameCheck.getInstance().addFVName(getName());
		NameCheck.getInstance().addWVName(getName());
		allInstances.put(getName(), this);
	}
	
	// Methoden:
	
	/**
	 * Löscht alle Klassenvariablen
	 */
	
	public static void clear() {
		allInstances.clear();
	}
	
	/**
	 * Überprüft, ob bereits ein Prozessmodul
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
	 * ... alle vorhandenen Prozessmodule
	 */
	
	public static HashMap<String, ProcessModule> getAllInstances() {
		return allInstances;
	}
	
	/**
	 * Liefert ein Prozessmodul
	 * @param string
	 * Name des Prozessmoduls
	 * @return
	 * ... das gesuchte Prozessmodul
	 */
	
	public static ProcessModule getInstance(String string) {
		return allInstances.get(string);		
	}
	
	/**
	 * Erzeugt ein leeres benanntes Prozessmodul durch Aufruf des
	 * privaten Konstruktors sofern noch kein Prozessmodul gleichem
	 * Namens existiert. Ansonsten wird das existierende Prozessmodul
	 * zurückgegeben.
	 * @param name
	 * Der Name des Prozessmoduls
	 * @return
	 * ... das Prozessmodul
	 */
	
	public static ProcessModule instance(String name) {
		if (allInstances.containsKey(name) == false) {
			new ProcessModule(name);
		}
		return allInstances.get(name);
	}
	
	/**
	 * Löscht ein Prozessmodul
	 * @param string
	 * Name des zu löschenden Prozessmoduls
	 */
	
	public static void removeInstance(String string) {
		allInstances.remove(string);
		NameCheck.removeFVName(string);
		NameCheck.removeWVName(string);
	}
	
	/**
	 * Fügt dem Prozessmodul einen quantifizierten Fluss hinzu.
	 * Die Methode kann für alle Flusstypen verwendet werden.
	 * @param fluss
	 * Der Fluss, der dem Prozessmodul hinzugefügt werden soll
	 * @param wert
	 * Die Menge, die in das Prozessmodul hinein (negatives
	 * Vorzeichen) oder aus dem Prozessmodul hinaus (positives 
	 * Vorzeichen) fließt.
	 */
	
	public void addFluss(Flow fluss, ValueType fvt, Double wert) {	
		if (fluss.getType() == FlowType.Elementary) {
			HashMap<ValueType, Double> valueMap = efv.get(fluss);
			if (valueMap == null) {
				valueMap = new HashMap<ValueType, Double>();
			}
			valueMap.put(fvt, wert);
			efv.put(fluss, valueMap);
		} else { 
			HashMap<ValueType, Double> valueMap = pfv.get(fluss);
			if (valueMap == null) {
				valueMap = new HashMap<ValueType, Double>();
			}
			valueMap.put(fvt, wert);
			pfv.put(fluss, valueMap);
		}
	}
	
	@Override
	public HashMap<Flow, HashMap<ValueType, Double>> getElementarflussvektor() {
		return efv; 
	}	
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public HashMap<Flow, HashMap<ValueType, Double>> getProduktflussvektor() {
		return pfv; 
	}
	

	
	/**
	 * Entfernt einen Fluss aus dem betroffenen Flussvektor.
	 * 
	 * @param fluss
	 * der zu entfernende Fluß
	 */
	
	public void removeFluss(Flow fluss) {
		efv.remove(fluss);
		pfv.remove(fluss);
	}

	public void setName(String string) {
		NameCheck.removeFVName(this.name);
		NameCheck.removeWVName(this.name);
		NameCheck.getInstance().addFVName(string);
		NameCheck.getInstance().addWVName(string);
		this.name = string;	
	}

	@Override
	public HashMap<ImpactCategory, HashMap<ValueType, Double>> getImpactValueMap(LCIAMethod bm) {
		HashMap<ImpactCategory, HashMap<ValueType, Double>> wv =
				new HashMap<ImpactCategory, HashMap<ValueType, Double>>();
		HashMap<ValueType, Double> values = new HashMap<ValueType, Double>();
		values.put(ValueType.MeanValue, 0.);
		values.put(ValueType.LowerBound, 0.);
		values.put(ValueType.UpperBound, 0.);
		for (String wk : bm.categoryList().keySet()){
			System.out.println(wk);
			System.out.println(bm.categoryList().get(wk));
			wv.put(bm.categoryList().get(wk), values);
		}
		for (String cfName : bm.getFactorSet().keySet()){
			CharacFactor cf = bm.getFactorSet().get(cfName);
			if (efv.containsKey(cf.getFlow())) {
				double mv0 = wv.get(cf.getWirkung()).get(ValueType.MeanValue);
				IvariScalar iv0 = new IvariScalar();
				iv0.setLowerBound(wv.get(cf.getWirkung()).get(ValueType.LowerBound));
				iv0.setUpperBound(wv.get(cf.getWirkung()).get(ValueType.UpperBound));
				double mvc = cf.getValue(ValueType.MeanValue);
				IvariScalar ivc = new IvariScalar();
				ivc.setLowerBound(cf.getValue(ValueType.LowerBound));
				ivc.setUpperBound(cf.getValue(ValueType.UpperBound));
				double mvf = efv.get(cf.getFlow()).get(ValueType.MeanValue);
				IvariScalar ivf = new IvariScalar();
				ivf.setLowerBound(efv.get(cf.getFlow()).get(ValueType.LowerBound));
				ivf.setUpperBound(efv.get(cf.getFlow()).get(ValueType.UpperBound));
				double mvr = mvc * mvf;
				System.out.println(cfName + " " + mv0 + " " + mvc + " " + mvf + " " + mvr);
				IvariScalar ivr = ivc.mult(ivf);
				values.put(ValueType.MeanValue, mv0 + mvr);
				values.put(ValueType.LowerBound, values.get(ValueType.LowerBound) + ivr.getLowerBound());
				values.put(ValueType.UpperBound, values.get(ValueType.UpperBound) + ivr.getUpperBound());
				wv.put(cf.getWirkung(), values);
				System.out.println(values);
			}			
		}
		System.out.println(wv);
		return wv;
	}
}

