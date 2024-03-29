/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import Jama.Matrix;
import de.unistuttgart.iwb.ivari.IvariMatrix;
import de.unistuttgart.iwb.ivari.IvariScalar;
import de.unistuttgart.iwb.ivari.IvariVector;
import de.unistuttgart.iwb.ivari.Solver;

/**
 * Diese Klasse dient zur Erzeugung und Nutzung
 * von Objekten des Typs "Produktsystem".
 * 
 * @author Dr.-Ing. Joachim Schwarte, Helen Hein, Johannes Dippon
 * @version 0.827
 */

public class ProductSystem extends MCAObject
implements FlowValueMaps, ImpactValueMaps {
	
	// Klassenvariablen:
	
	static private Solver so = Solver.siga;
	
	// Instanzvariablen:
	
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
	private LinkedHashMap<ImpactValueMaps, LinkedHashMap<ValueType, Double>> dfv = 
			new LinkedHashMap<ImpactValueMaps, LinkedHashMap<ValueType, Double>>(); //Deklarierte Fl�sse
	
	// Konstruktor:

	private ProductSystem(String name, 
			LinkedHashMap<Flow, Double> f,
			LinkedList<Flow> vk) {
		super(name);
		this.bedarfsvektor = f;
		this.vorUndKoppelProdukte = vk;
	}
	
	// Methoden:
	
	/**
	 * W�hlt einen anderen Gleichhungsl�ser aus.
	 * @param soNew
	 * ist der Solver, der den bisherigen Solver ersetzt.
	 */
	
	public static void setSolver(Solver soNew) {
		so = soNew;
	}
	
	/**
	 * �berpr�ft, ob bereits ein Produktsystem
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
	 * ... alle vorhandenen Produktsysteme
	 */
	
	public static LinkedHashMap<String, ProductSystem> getAllInstances() {
		LinkedHashMap<String,ProductSystem> a = new LinkedHashMap<String,ProductSystem>();
		for (String s : MCAObject.getAllNames(ProductSystem.class)) {
			a.put(s, (ProductSystem)MCAObject.getAllInstances(ProductSystem.class).get(s));			
		}
		return a;
	}
	
	/**
	 * Liefert ein Produktsystem
	 * @param name
	 * Name des Produktsystems
	 * @return
	 * ... das gesuchte Produktsystem
	 */
	
	public static ProductSystem getInstance(String name) {
		return getAllInstances().get(name);		
	}
	
	/**
	 * Erzeugt ein neues oder �berschreibt ein bestehendes Produktsystem
	 * @param name
	 * �bergibt der Namen des Produktsystems. Dieser kann frei 
	 * gew�hlt werden.
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
		if (!getAllInstances().containsKey(name)) {
			new ProductSystem(name, f, vk);
		} else {
			getAllInstances().get(name).setBedarfsvektor(f);
			getAllInstances().get(name).setVorUndKoppelProdukte(vk);
		}
		return getAllInstances().get(name);
	}

	/**
	 * F�gt dem Bedarfsvektor einen Eintrag hinzu
	 * @param fluss
	 * Name des Flusses
	 * @param wert
	 * ben�tigte Menge im Bedarfsvektor
	 */
	
	public void addBedarf(Flow fluss, Double wert) {
		bedarfsvektor.put(fluss, wert);
	}
	
	/**
	 * F�gt dem Produktsystem ein weiteres Modul hinzu.
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
	 * F�gt der Liste der Vor- und Koppelprodukte einen Eintrag
	 * hinzu.
	 * @param fluss
	 * Der Fluss, der der Liste hinzugef�gt werden soll
	 */
	
	public void addVuK(Flow fluss) {
		vorUndKoppelProdukte.add(fluss);
	}
	
	private void aktualisiere() throws Exception {
		LinkedList<Flow> produktFlussliste = new LinkedList<Flow>();
		for(FlowValueMaps m : modulliste){
			LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> modulVektor = m.getProduktflussvektor();
			for (Flow key : modulVektor.keySet()) {		
				if (!produktFlussliste.contains(key) &&
					(!vorUndKoppelProdukte.contains(key)) &&
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
				if (!elementarFlussliste.contains(key)){
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
		LinkedList<ImpactValueMaps> EPDFlussliste = new LinkedList<ImpactValueMaps>();
		for(FlowValueMaps m : modulliste){
			LinkedHashMap<ImpactValueMaps, LinkedHashMap<ValueType, Double>> modulVektor = m.getEPDFlussvektor();
			for (ImpactValueMaps key : modulVektor.keySet()) {
				if (!EPDFlussliste.contains(key)){
					EPDFlussliste.add(key);					
				}				
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
			ivS = imA.solve(so, ivF);
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
		if (EPDFlussliste.size()!=0) {
			double[][] arrayC = new double[EPDFlussliste.size()][modulliste.size()];
			double[][] arrayCl = new double[EPDFlussliste.size()][modulliste.size()];
			double[][] arrayCu = new double[EPDFlussliste.size()][modulliste.size()];
			for(FlowValueMaps m : modulliste){
				LinkedHashMap<ImpactValueMaps, LinkedHashMap<ValueType, Double>> modulVektor = m.getEPDFlussvektor();
				for (ImpactValueMaps key : modulVektor.keySet()) {
					arrayC[EPDFlussliste.indexOf(key)][modulliste.indexOf(m)]=modulVektor.get(key).get(ValueType.MeanValue);		
					arrayCl[EPDFlussliste.indexOf(key)][modulliste.indexOf(m)]=modulVektor.get(key).get(ValueType.LowerBound);	
					arrayCu[EPDFlussliste.indexOf(key)][modulliste.indexOf(m)]=modulVektor.get(key).get(ValueType.UpperBound);	
				}
			}
			Matrix matrixC = new Matrix(arrayC);
			IvariMatrix imC = new IvariMatrix(arrayCl, arrayCu);
			Matrix matrixC2 = matrixC.times(matrixS);
			IvariVector imC2 = imC.multVector(ivS);
			for(ImpactValueMaps ef : EPDFlussliste) {
				LinkedHashMap<ValueType, Double> valueMap = new LinkedHashMap<ValueType, Double>();
				valueMap.put(ValueType.MeanValue, matrixC2.get(EPDFlussliste.indexOf(ef),0));
				valueMap.put(ValueType.LowerBound, imC2.getValue(EPDFlussliste.indexOf(ef)).getLowerBound());
				valueMap.put(ValueType.UpperBound, imC2.getValue(EPDFlussliste.indexOf(ef)).getUpperBound());
				dfv.put(ef, valueMap);
			}
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
		// Rundung (ab Version 0.826):
		for(Flow fl : efv.keySet()) {
			for(ValueType vt : efv.get(fl).keySet()){
				efv.get(fl).put(vt, Math.round(efv.get(fl).get(vt)*100000)/100000.0);
			}
		
		}
		return efv;
	}
	
	@Override
	public LinkedHashMap<ImpactValueMaps, LinkedHashMap<ValueType, Double>> getEPDFlussvektor() throws ArithmeticException {
		try {
			aktualisiere();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Rundung (ab Version 0.827):
		for(ImpactValueMaps fl : dfv.keySet()) {
			for(ValueType vt : dfv.get(fl).keySet()){
				dfv.get(fl).put(vt, Math.round(dfv.get(fl).get(vt)*100000)/100000.0);
			}
		
		}
		return dfv;
	}
	
	/**
	 * @return
	 * ... die Anzahl der Module (Prozessmodule/-gruppen und Subsysteme/-gruppen) 
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
		// Rundung (ab Version 0.827):
		for(Flow fl : pfv.keySet()) {
			for(ValueType vt : pfv.get(fl).keySet()){
				pfv.get(fl).put(vt, Math.round(pfv.get(fl).get(vt)*100000)/100000.0);
			}
		
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
	 * �berschreibt den vorhandenen Bedarfsvektor
	 * @param bv
	 * ist der neue Bedarfsvektor
	 */
	
	public void setBedarfsvektor(LinkedHashMap<Flow, Double> bv) {
		bedarfsvektor = bv;
	}
	
	/**
	 * �berschreibt die vorhandene Liste der Vor- und
	 * Koppelprodukte
	 * @param vk
	 * ist die neue Liste der Vor- und Koppelprodukte
	 */
	
	public void setVorUndKoppelProdukte(LinkedList<Flow> vk) {
		vorUndKoppelProdukte = vk;
	}
	
	@Override
	public LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> getImpactValueMap() {
		return null;
	}
	
	/**
	 * Liefert den Wirkungsvektor des Produktsystems f�r
	 * die relevante Bewertungsmethode
	 * @param bm
	 * die relevante Bewertungsmethode 
	 * @return
	 * ... den zugeh�rigen Wirkungsvektor 
	 */

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
		LinkedList<ImpactCategory> kategorienListe = new LinkedList<ImpactCategory>();
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
			for (ImpactValueMaps epd : dfv.keySet()) {
				if (!kategorienListe.contains(cf.getWirkung())) {
					kategorienListe.add(cf.getWirkung());
					LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> wvKomponente = epd.getImpactValueMap(bm);
					LinkedHashMap<ValueType, Double> values = new LinkedHashMap<ValueType, Double>();
					values.put(ValueType.MeanValue, 0.0);
					values.put(ValueType.LowerBound, 0.0);
					values.put(ValueType.UpperBound, 0.0);
					for (ValueType vt : values.keySet()) {
						values.put(vt, wv.get(cf.getWirkung()).get(vt) + wvKomponente.get(cf.getWirkung()).get(vt) * dfv.get(epd).get(vt));
					}
					wv.put(cf.getWirkung(), values);
				}
			} 
		}		
		return wv;
	}

}
