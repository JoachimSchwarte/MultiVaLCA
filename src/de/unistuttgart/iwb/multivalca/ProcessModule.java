/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;
import java.util.LinkedHashMap;

import de.unistuttgart.iwb.ivari.IvariScalar;

/**
 * Diese Klasse dient zur Erzeugung und Nutzung
 * von Objekten des Typs "Prozessmodul".
 * 
 * @author Dr.-Ing. Joachim Schwarte, Helen Hein, Johannes Dippon
 * @version 0.813
 */

public class ProcessModule extends MCAObject 
implements FlowValueMaps, ImpactValueMaps {
	
	// Instanzvariablen:
	
	private LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> efv = 
			new LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>>(); //Elementarflüsse
	private LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> pfv = 
			new LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>>(); //Produktflüsse
	private LinkedHashMap<ImpactValueMaps, LinkedHashMap<ValueType, Double>> dfv = 
			new LinkedHashMap<ImpactValueMaps, LinkedHashMap<ValueType, Double>>(); //Deklarierte Flüsse
	
	// Konstruktor:
			
	private ProcessModule(String name) {
		super(name);
	}
	
	// Methoden:
	
	/**
	 * @return
	 * ... alle vorhandenen Prozessmodule
	 */

	public static LinkedHashMap<String, ProcessModule> getAllInstances() {
		LinkedHashMap<String,ProcessModule> a = new LinkedHashMap<String,ProcessModule>();
		for (String s : MCAObject.getAllNames(ProcessModule.class)) {
			a.put(s, (ProcessModule)MCAObject.getAllInstances(ProcessModule.class).get(s));			
		}
		return a;
	}
	
	/**
	 * Überprüft, ob bereits ein Prozessmodul
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
	 * Liefert ein Prozessmodul
	 * @param name
	 * Name des Porzessmoduls
	 * @return
	 * ... das gesuchte Prozessmodul
	 */
	
	public static ProcessModule getInstance(String name) {
		return getAllInstances().get(name);		
	}

	/**
	 * Erzeugt ein benanntes Prozessmodul
	 * @param name
	 * ist der Name des Prozessmoduls
	 * @return
	 * ... das Prozessmodul
	 */
	
	public static ProcessModule instance(String name) {
		if (!getAllInstances().containsKey(name)) {
			new ProcessModule(name);
		}
		return getAllInstances().get(name);
	}

	/**
	 * Löscht ein benanntes Prozessmodul
	 * @param name
	 * ist der Name des Prozessmoduls
	 */
	
	public static void removeInstance(String name) {
		getAllInstances().remove(name);
	}
	
	/**
	 * Fügt dem Prozessmodul
	 * einen Fluss und den diesem Fluss zugeordneten Wert hinzu
	 * @param fluss
	 * ist der hinzuzufügende Fluss
	 * @param fvt
	 * ist der Werttyp
	 * @param wert
	 * ist der dem Werttyp zugeordnete Wert
	 */
	
	public void addFluss(Flow fluss, ValueType fvt, Double wert) {	
		if (fluss.getType() == FlowType.Elementary) {
			LinkedHashMap<ValueType, Double> valueMap = efv.get(fluss);
			if (valueMap == null) {
				valueMap = new LinkedHashMap<ValueType, Double>();
			}
			valueMap.put(fvt, wert);
			efv.put(fluss, valueMap);
		} else { 
			LinkedHashMap<ValueType, Double> valueMap = pfv.get(fluss);
			if (valueMap == null) {
				valueMap = new LinkedHashMap<ValueType, Double>();
			}
			valueMap.put(fvt, wert);
			pfv.put(fluss, valueMap);
		}
	}
	
	public void addFluss(ImpactValueMaps fluss, ValueType fvt, Double wert) {
		LinkedHashMap<ValueType, Double> valueMap = dfv.get(fluss);
		if (valueMap == null) {
			valueMap = new LinkedHashMap<ValueType, Double>();
		}
		valueMap.put(fvt, wert);
		dfv.put(fluss, valueMap);
	}
	
	
	/**
	 * @return
	 * den Elementarflussvektor des Prozessmoduls
	 */
	
	@Override
	public LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> getElementarflussvektor() {
		return efv; 
	}	
	
	/**
	 * @return
	 * den Produktflussvektor des Prozessmoduls
	 */
		
	@Override
	public LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> getProduktflussvektor() {
		return pfv; 
	}
	
	
	@Override
	public LinkedHashMap<ImpactValueMaps, LinkedHashMap<ValueType, Double>> getEPDFlussvektor() {
		return dfv; 
	}
	
	/**
	 * Entfernt einen Fluss des Prozessmoduls
	 * @param fluss
	 * ist der Name des Flusses
	 */
	
	public void removeFluss(Flow fluss) {
		efv.remove(fluss);
		pfv.remove(fluss);
	}
	
	@Override
	public LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> getImpactValueMap() {
		return null;
	}
	
	/**
	 * Liefert den Wirkungsvektor des Prozessmoduls für
	 * die relevante Bewertungsmethode
	 * @param bm
	 * die relevante Bewertungsmethode 
	 * @return
	 * ... den zugehörigen Wirkungsvektor 
	 */
	
	@Override	
	public LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> getImpactValueMap(LCIAMethod bm) {	
		LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> wv =	
				new LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>>();	
		LinkedHashMap<ValueType, Double> values0 = new LinkedHashMap<ValueType, Double>();	
		values0.put(ValueType.MeanValue, 0.);	
		values0.put(ValueType.LowerBound, 0.);	
		values0.put(ValueType.UpperBound, 0.);	
		for (String wk : bm.categoryList().keySet()){	
			wv.put(bm.categoryList().get(wk), values0);	
		}	
		for (String cfName : bm.getFactorSet().keySet()){	
			CharacFactor cf = bm.getFactorSet().get(cfName);	
			if (efv.containsKey(cf.getFlow())) {	
				LinkedHashMap<ValueType, Double> values = new LinkedHashMap<ValueType, Double>();					
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
				IvariScalar ivr = ivc.mult(ivf);	
				values.put(ValueType.MeanValue, mv0 + mvr);	
				values.put(ValueType.LowerBound, iv0.getLowerBound() + ivr.getLowerBound());	
				values.put(ValueType.UpperBound, iv0.getUpperBound() + ivr.getUpperBound());	
				wv.put(cf.getWirkung(), values);	
			}			
		}	
		return wv;	
	}
}

