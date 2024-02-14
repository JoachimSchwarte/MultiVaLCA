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
 * @version 0.813
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
			new LinkedHashMap<ImpactValueMaps, LinkedHashMap<ValueType, Double>>(); //Deklarierte Flüsse
	
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
	 * Wählt einen anderen Gleichhungslöser aus.
	 * @param soNew
	 * ist der Solver, der den bisherigen Solver ersetzt.
	 */
	
	public static void setSolver(Solver soNew) {
		so = soNew;
	}
	
	/**
	 * Überprüft, ob bereits ein Produktsystem
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
		if (!getAllInstances().containsKey(name)) {
			new ProductSystem(name, f, vk);
		} else {
			getAllInstances().get(name).setBedarfsvektor(f);
			getAllInstances().get(name).setVorUndKoppelProdukte(vk);
		}
		return getAllInstances().get(name);
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
/*			System.out.println();
			System.out.println("A[1,1] = "+imA.getValue(0, 0).getLowerBound()+" , "+imA.getValue(0, 0).getUpperBound());
			System.out.println("A[1,2] = "+imA.getValue(0, 1).getLowerBound()+" , "+imA.getValue(0, 1).getUpperBound());
			System.out.println("A[2,1] = "+imA.getValue(1, 0).getLowerBound()+" , "+imA.getValue(1, 0).getUpperBound());
			System.out.println("A[2,2] = "+imA.getValue(1, 1).getLowerBound()+" , "+imA.getValue(1, 1).getUpperBound());
			System.out.println("B[1]   = "+ivF.getValue(0).getLowerBound()+" , "+ivF.getValue(0).getUpperBound());
			System.out.println("B[2]   = "+ivF.getValue(1).getLowerBound()+" , "+ivF.getValue(1).getUpperBound());         */
			ivS = imA.solve(so, ivF);
/*			System.out.println("S[1]   = "+ivS.getValue(0).getLowerBound()+" , "+ivS.getValue(0).getUpperBound());
			System.out.println("S[2]   = "+ivS.getValue(1).getLowerBound()+" , "+ivS.getValue(1).getUpperBound());         */
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
	
	@Override
	public LinkedHashMap<ImpactValueMaps, LinkedHashMap<ValueType, Double>> getEPDFlussvektor() throws ArithmeticException {
		try {
			aktualisiere();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> getImpactValueMap() {
		return null;
	}
	
	/**
	 * Liefert den Wirkungsvektor des Produktsystems für
	 * die relevante Bewertungsmethode
	 * @param bm
	 * die relevante Bewertungsmethode 
	 * @return
	 * ... den zugehörigen Wirkungsvektor 
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
