/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import Jama.Matrix;
import de.unistuttgart.iwb.ivari.*;

/**
 *  * Diese Klasse dient zur Erzeugung von Produktsystemen.
 * 
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.495
 */

public class ProductSystem 
implements FlowValueMaps, ImpactValueMaps {
	
	
	// Klassenvariable:
	
	private static LinkedHashMap<String, ProductSystem> allInstances = new LinkedHashMap<String, ProductSystem>();
	
	// Instanzvariablen:
	
	private String name;
	private LinkedList<FlowValueMaps> modulliste 
			= new LinkedList<FlowValueMaps>();
	private LinkedHashMap<Flow, Double> bedarfsvektor 
			= new LinkedHashMap<Flow, Double>();
	private LinkedList<Flow> vorUndKoppelProdukte 
			= new LinkedList<Flow>();
	private LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> efv 
			= new LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>>();
	private LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> pfv 
			= new LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>>();
	
	// Konstruktor:

	private ProductSystem(String string, 
			LinkedHashMap<Flow, Double> f,
			LinkedList<Flow> vk) {
		name = string;
		bedarfsvektor = f;
		vorUndKoppelProdukte = vk;
		NameCheck.getInstance().addFVName(name);
		NameCheck.getInstance().addWVName(name);
		allInstances.put(name, this);
	}
	
	// Methoden:
	
	/**
	 * Löscht alle Klassenvariablen
	 */
	
	public static void clear() {
		allInstances.clear();
	}
	
	/**
	 * Überprüft, ob bereits ein Produktsystem
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
	 * ... alle vorhandenen Produktsysteme
	 */
	
	public static LinkedHashMap<String, ProductSystem> getAllInstances() {
		return allInstances;
	}
	
	/**
	 * Liefert ein Produktsystem
	 * @param string
	 * Name des Produktsystems
	 * @return
	 * ... das gesuchte Produktsystem
	 */
	
	public static ProductSystem getInstance(String string) {
		return allInstances.get(string);		
	}
	
	/**
	 * Erzeugt ein neues oder überschreibt ein bestehendes Produktsystem
	 * @param name
	 * übergibt der Namen des Produktsystems. Dieser kann frei 
	 * gewählt werden.
	 * Auf Anwendungsebene ist Namenseindeutigkeit anzustreben. 
	 * @param f
	 * ist der Bedarfsvektor
	 * @param vk
	 * ist die Liste der Vor- und Koppelprodukte
	 * @return
	 * ... das neue oder bearbeitete Produktsystem
	 */
	
	public static ProductSystem instance(String name, 
			LinkedHashMap<Flow, Double> f,
			LinkedList<Flow> vk) {
		if (allInstances.containsKey(name) == false) {
			new ProductSystem(name, f, vk);
		} else {
			allInstances.get(name).setBedarfsvektor(f);
			allInstances.get(name).setVorUndKoppelProdukte(vk);
		}
		return allInstances.get(name);
	}

	/**
	 * Fügt dem Bedarfsvektor einen Eintrag hinzu
	 * @param fluss
	 * Name des Flusses
	 * @param wert
	 * benötigte Menge im Bedarfsvektor
	 */
	
	public void addBedarf(Flow fluss, Double wert) {
		bedarfsvektor.put(fluss, wert);
	}
	
	/**
	 * Fügt dem Produktsystem ein weiteres Modul hinzu.
	 * Dies kann ein Prozessmodul oder ein anderes Produktsystem
	 * (Subsystem) sein.
	 * @param modul
	 * ist ein Objekt einer Klasse, die das Interface 
	 * Flussvektoren implementiert.
	 */
	
	public void addProzessmodul(FlowValueMaps modul) {
		modulliste.add(modul);
	}
	
	/**
	 * Fügt der Liste der Vor- und Koppelprodukte einen Eintrag
	 * hinzu.
	 * @param fluss
	 * Der Fluss, der der Liste hinzugefügt werden soll
	 */
	
	public void addVuK(Flow fluss) {
		vorUndKoppelProdukte.add(fluss);
	}
	
	private void aktualisiere() throws Exception {
		LinkedList<Flow> produktFlussliste = new LinkedList<Flow>();
		for(FlowValueMaps m : modulliste){
			LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> modulVektor = m.getProduktflussvektor();
			for (Flow key : modulVektor.keySet()) {		
				if ((produktFlussliste.contains(key) == false) &&
					(vorUndKoppelProdukte.contains(key) == false) &&
					(m.getProduktflussvektor().containsKey(key)) &&
					(m.getProduktflussvektor().get(key).get(ValueType.MeanValue) != 0)
					)	{
					produktFlussliste.add(key);	
				}				
			}
		}
		if (produktFlussliste.size() != modulliste.size()) {
			throw new ArithmeticException("Matrix nicht quadratisch");
		}
		double[][] arrayA = new double[produktFlussliste.size()][modulliste.size()];
		double[][] arrayAl = new double[produktFlussliste.size()][modulliste.size()];
		double[][] arrayAu = new double[produktFlussliste.size()][modulliste.size()];
		for(FlowValueMaps m : modulliste){
			LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> modulVektor = m.getProduktflussvektor();
			for (Flow key : modulVektor.keySet()) {
 				if (produktFlussliste.contains(key)) {
					arrayA[produktFlussliste.indexOf(key)][modulliste.indexOf(m)]=modulVektor.get(key).get(ValueType.MeanValue);
					arrayAl[produktFlussliste.indexOf(key)][modulliste.indexOf(m)]=modulVektor.get(key).get(ValueType.LowerBound);
					arrayAu[produktFlussliste.indexOf(key)][modulliste.indexOf(m)]=modulVektor.get(key).get(ValueType.UpperBound);
 				} 
			}
		}
		LinkedList<Flow> elementarFlussliste = new LinkedList<Flow>();
		for(FlowValueMaps m : modulliste){
			LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> modulVektor = m.getElementarflussvektor();
			for (Flow key : modulVektor.keySet()) {
				if (elementarFlussliste.contains(key) == false){
					elementarFlussliste.add(key);					
				}				
			}
		}
		double[][] arrayB = new double[elementarFlussliste.size()][modulliste.size()];
		double[][] arrayBl = new double[elementarFlussliste.size()][modulliste.size()];
		double[][] arrayBu = new double[elementarFlussliste.size()][modulliste.size()];
		for(FlowValueMaps m : modulliste){
			LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> modulVektor = m.getElementarflussvektor();
			for (Flow key : modulVektor.keySet()) {
				arrayB[elementarFlussliste.indexOf(key)][modulliste.indexOf(m)]=modulVektor.get(key).get(ValueType.MeanValue);		
				arrayBl[elementarFlussliste.indexOf(key)][modulliste.indexOf(m)]=modulVektor.get(key).get(ValueType.LowerBound);	
				arrayBu[elementarFlussliste.indexOf(key)][modulliste.indexOf(m)]=modulVektor.get(key).get(ValueType.UpperBound);	
			}
		}
		double[][] arrayF = new double[produktFlussliste.size()][1];
		for(Flow pf : produktFlussliste) {
			if (bedarfsvektor.containsKey(pf)){
				arrayF[produktFlussliste.indexOf(pf)][0]=bedarfsvektor.get(pf);
			} 
			else {
				arrayF[produktFlussliste.indexOf(pf)][0]=0;
			}		
		}
		Matrix matrixA = new Matrix(arrayA);
		IvariMatrix imA = new IvariMatrix(arrayAl, arrayAu);
		Matrix matrixB = new Matrix(arrayB);
		IvariMatrix imB = new IvariMatrix(arrayBl, arrayBu);
		Matrix matrixF = new Matrix(arrayF);
		double[] harry = new double[arrayF.length];
		for (int i=0; i<arrayF.length; i++) {
			harry[i] = arrayF[i][0];
		}
		IvariVector ivF = new IvariVector(harry, harry);
		Matrix matrixS = matrixA.solve(matrixF);
		IvariVector ivS = new IvariVector(arrayA.length);
		try {
			ivS = imA.gauss(ivF);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Matrix matrixG = matrixB.times(matrixS);
		IvariVector ivG = imB.multVector(ivS);
		double[][] arrayS = matrixS.getArray();
		for (Integer i=0; i<arrayS.length; i++){
			if (arrayS[i][0]<0){
				throw new ArithmeticException("Vorzeichenfehler im Skalierungsvektor");			
			}
		}

		for(Flow ef : elementarFlussliste) {
			LinkedHashMap<ValueType, Double> valueMap = new LinkedHashMap<ValueType, Double>();
			valueMap.put(ValueType.MeanValue, matrixG.get(elementarFlussliste.indexOf(ef),0));
			valueMap.put(ValueType.LowerBound, ivG.getValue(elementarFlussliste.indexOf(ef)).getLowerBound());
			valueMap.put(ValueType.UpperBound, ivG.getValue(elementarFlussliste.indexOf(ef)).getUpperBound());
			efv.put(ef, valueMap);
		}	
		produktFlussliste.addAll(vorUndKoppelProdukte);
		double[][] arrayA1 = new double[produktFlussliste.size()][modulliste.size()];
		double[][] arrayA1l = new double[produktFlussliste.size()][modulliste.size()];
		double[][] arrayA1u = new double[produktFlussliste.size()][modulliste.size()];
		for(FlowValueMaps m : modulliste){
			LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> modulVektor = m.getProduktflussvektor();
			for (Flow key : produktFlussliste) {
				if (modulVektor.containsKey(key)) {
					arrayA1[produktFlussliste.indexOf(key)][modulliste.indexOf(m)]=modulVektor.get(key).get(ValueType.MeanValue);
					arrayA1l[produktFlussliste.indexOf(key)][modulliste.indexOf(m)]=modulVektor.get(key).get(ValueType.LowerBound);
					arrayA1u[produktFlussliste.indexOf(key)][modulliste.indexOf(m)]=modulVektor.get(key).get(ValueType.UpperBound);
				} else {
					arrayA1[produktFlussliste.indexOf(key)][modulliste.indexOf(m)]=0;
					arrayA1l[produktFlussliste.indexOf(key)][modulliste.indexOf(m)]=0;
					arrayA1u[produktFlussliste.indexOf(key)][modulliste.indexOf(m)]=0;
				}
								
			}
		}
		Matrix matrixA1 = new Matrix(arrayA1);
		IvariMatrix imA1 = new IvariMatrix(arrayA1l, arrayA1u);
		Matrix matrixG1 = matrixA1.times(matrixS);
		IvariVector ivG1 = imA1.multVector(ivS);
		for(Flow pf : produktFlussliste) {
			LinkedHashMap<ValueType, Double> valueMap = new LinkedHashMap<ValueType, Double>();
			valueMap.put(ValueType.MeanValue, matrixG1.get(produktFlussliste.indexOf(pf),0));
			valueMap.put(ValueType.LowerBound, ivG1.getValue(produktFlussliste.indexOf(pf)).getLowerBound());
			valueMap.put(ValueType.UpperBound, ivG1.getValue(produktFlussliste.indexOf(pf)).getUpperBound());
			pfv.put(pf, valueMap);
		}			
	}
	
	/**
	 * @return
	 * den Bedarfsvektor des Produktsystems
	 */
	
	public LinkedHashMap<Flow, Double> getBedarfsvektor() {
		return bedarfsvektor;
	}

	/**
	 * @throws ArithmeticException
	 * wenn im Skalierungsvektor ein Element mit negativem
	 * Vorzeichen auftritt. Fehlertext: "Vorzeichenfehler 
	 * im Skalierungsvektor"
	 */

	@Override
	public LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> getElementarflussvektor() throws ArithmeticException {
		try {
			aktualisiere();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return efv;
	}
	
	/**
	 * @return
	 * ... die Anzahl der Module (Prozessmodule und Subsysteme) 
	 * aus denen sich das Produktsystem zusammensetzt.
	 */
	
	public int getModulAnzahl() {
		return modulliste.size();
	}
	
	/**
	 * @return
	 * die Liste der im Produktsystem enthaltenden Module
	 */
	
	public LinkedList<FlowValueMaps> getModulliste() {
		return modulliste;
	}
	
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @throws ArithmeticException
	 * wenn im Skalierungsvektor ein Element mit negativem
	 * Vorzeichen auftritt. Fehlertext: "Vorzeichenfehler 
	 * im Skalierungsvektor"
	 */

	@Override
	public LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> getProduktflussvektor() throws ArithmeticException {
		try {
			aktualisiere();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return pfv;
	}
	
	/**
	 * @return
	 * ... die Liste der Vor- und Koppleprodukte
	 */

	public LinkedList<Flow> getVorUndKoppelprodukte() {
		return vorUndKoppelProdukte;
	}
	

	
	/**
	 * Überschreibt den vorhandenen Bedarfsvektor
	 * @param bv
	 * ist der neue Bedarfsvektor
	 */
	
	public void setBedarfsvektor(LinkedHashMap<Flow, Double> bv) {
		bedarfsvektor = bv;
	}
	

	public void setName(String string) {
		NameCheck.removeFVName(this.name);
		NameCheck.removeWVName(this.name);
		NameCheck.getInstance().addFVName(string);
		NameCheck.getInstance().addWVName(string);
		this.name = string;			
	}
	
	/**
	 * Überschreibt die vorhandene Liste der Vor- und
	 * Koppelprodukte
	 * @param vk
	 * ist die neue Liste der Vor- und Koppelprodukte
	 */
	
	public void setVorUndKoppelProdukte(LinkedList<Flow> vk) {
		vorUndKoppelProdukte = vk;
	}

	@Override
	public LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> getImpactValueMap(LCIAMethod bm) {
		try {
			aktualisiere();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
