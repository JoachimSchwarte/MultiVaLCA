/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;
import java.util.HashMap;
import java.util.LinkedList;
import Jama.Matrix;
import de.unistuttgart.iwb.ivari.*;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.27
 */

public class ProductSystem 
implements FlowValueMaps {
	
	
	// Klassenvariable:
	
	private static HashMap<String, ProductSystem> allInstances = new HashMap<String, ProductSystem>();
	
	// Instanzvariablen:
	
	private String name;
	private LinkedList<FlowValueMaps> modulliste 
			= new LinkedList<FlowValueMaps>();
	private HashMap<Flow, Double> bedarfsvektor 
			= new HashMap<Flow, Double>();
	private LinkedList<Flow> vorUndKoppelProdukte 
			= new LinkedList<Flow>();
	private HashMap<Flow, HashMap<FlowValueType, Double>> efv 
			= new HashMap<Flow, HashMap<FlowValueType, Double>>();
	private HashMap<Flow, HashMap<FlowValueType, Double>> pfv 
			= new HashMap<Flow, HashMap<FlowValueType, Double>>();
	
	// Konstruktor:

	private ProductSystem(String string, 
			HashMap<Flow, Double> f,
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
	
	public static HashMap<String, ProductSystem> getAllInstances() {
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
			HashMap<Flow, Double> f,
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
	
	private void aktualisiere() {
		LinkedList<Flow> produktFlussliste = new LinkedList<Flow>();
		for(FlowValueMaps m : modulliste){
			HashMap<Flow, HashMap<FlowValueType, Double>> modulVektor = m.getProduktflussvektor();
			for (Flow key : modulVektor.keySet()) {		
				if ((produktFlussliste.contains(key) == false) &&
					(vorUndKoppelProdukte.contains(key) == false) &&
					(m.getProduktflussvektor().get(key) != null))	{
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
			HashMap<Flow, HashMap<FlowValueType, Double>> modulVektor = m.getProduktflussvektor();
			for (Flow key : modulVektor.keySet()) {
 				if (produktFlussliste.contains(key)) {
					arrayA[produktFlussliste.indexOf(key)][modulliste.indexOf(m)]=modulVektor.get(key).get(FlowValueType.MeanValue);
					arrayAl[produktFlussliste.indexOf(key)][modulliste.indexOf(m)]=modulVektor.get(key).get(FlowValueType.LowerBound);
					arrayAu[produktFlussliste.indexOf(key)][modulliste.indexOf(m)]=modulVektor.get(key).get(FlowValueType.UpperBound);
 				} 
			}
		}
		LinkedList<Flow> elementarFlussliste = new LinkedList<Flow>();
		for(FlowValueMaps m : modulliste){
			HashMap<Flow, HashMap<FlowValueType, Double>> modulVektor = m.getElementarflussvektor();
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
			HashMap<Flow, HashMap<FlowValueType, Double>> modulVektor = m.getElementarflussvektor();
			for (Flow key : modulVektor.keySet()) {
				arrayB[elementarFlussliste.indexOf(key)][modulliste.indexOf(m)]=modulVektor.get(key).get(FlowValueType.MeanValue);		
				arrayBl[elementarFlussliste.indexOf(key)][modulliste.indexOf(m)]=modulVektor.get(key).get(FlowValueType.LowerBound);	
				arrayBu[elementarFlussliste.indexOf(key)][modulliste.indexOf(m)]=modulVektor.get(key).get(FlowValueType.UpperBound);	
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
		System.out.println("ivF:");
		System.out.println(arrayF[0][0]);
		System.out.println(arrayF[1][0]);
		double[] harry = new double[arrayF.length];
		for (int i=0; i<arrayF.length; i++) {
			harry[i] = arrayF[i][0];
		}
		IvariVector ivF = new IvariVector(harry, harry);
		Matrix matrixS = matrixA.solve(matrixF);
		IvariVector ivS = new IvariVector(arrayA.length);
		System.out.println(imA.getValue(0, 0).getLowerBound()+ " " +
				imA.getValue(0, 1).getLowerBound()+ " " +
				imA.getValue(1, 0).getLowerBound()+ " " +
				imA.getValue(1, 1).getLowerBound());
		System.out.println(imA.getValue(0, 0).getUpperBound()+ " " +
				imA.getValue(0, 1).getUpperBound()+ " " +
				imA.getValue(1, 0).getUpperBound()+ " " +
				imA.getValue(1, 1).getUpperBound());
		System.out.println("Fl=  "+ivF.getValue(0).getLowerBound()+ " " +
				ivF.getValue(1).getLowerBound());
		System.out.println("Fu=  "+ivF.getValue(0).getUpperBound()+ " " +
				ivF.getValue(1).getUpperBound());
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
			System.out.println("s"+i+"=  "+arrayS[i]+ivS.getValue(i).getLowerBound()+
					arrayS[i]+ivS.getValue(i).getUpperBound());
			if (arrayS[i][0]<0){
				throw new ArithmeticException("Vorzeichenfehler im Skalierungsvektor");			
			}
		}

		for(Flow ef : elementarFlussliste) {
			HashMap<FlowValueType, Double> valueMap = new HashMap<FlowValueType, Double>();
			valueMap.put(FlowValueType.MeanValue, matrixG.get(elementarFlussliste.indexOf(ef),0));
			valueMap.put(FlowValueType.LowerBound, ivG.getValue(elementarFlussliste.indexOf(ef)).getLowerBound());
			valueMap.put(FlowValueType.UpperBound, ivG.getValue(elementarFlussliste.indexOf(ef)).getUpperBound());
			efv.put(ef, valueMap);
		}	
		produktFlussliste.addAll(vorUndKoppelProdukte);
		double[][] arrayA1 = new double[produktFlussliste.size()][modulliste.size()];
		double[][] arrayA1l = new double[produktFlussliste.size()][modulliste.size()];
		double[][] arrayA1u = new double[produktFlussliste.size()][modulliste.size()];
		for(FlowValueMaps m : modulliste){
			HashMap<Flow, HashMap<FlowValueType, Double>> modulVektor = m.getProduktflussvektor();
			for (Flow key : produktFlussliste) {
				if (modulVektor.containsKey(key)) {
					arrayA1[produktFlussliste.indexOf(key)][modulliste.indexOf(m)]=modulVektor.get(key).get(FlowValueType.MeanValue);
					arrayA1l[produktFlussliste.indexOf(key)][modulliste.indexOf(m)]=modulVektor.get(key).get(FlowValueType.LowerBound);
					arrayA1u[produktFlussliste.indexOf(key)][modulliste.indexOf(m)]=modulVektor.get(key).get(FlowValueType.UpperBound);
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
			HashMap<FlowValueType, Double> valueMap = new HashMap<FlowValueType, Double>();
			valueMap.put(FlowValueType.MeanValue, matrixG1.get(produktFlussliste.indexOf(pf),0));
			valueMap.put(FlowValueType.LowerBound, ivG1.getValue(produktFlussliste.indexOf(pf)).getLowerBound());
			valueMap.put(FlowValueType.UpperBound, ivG1.getValue(produktFlussliste.indexOf(pf)).getUpperBound());
			pfv.put(pf, valueMap);
		}			
	}
	
	/**
	 * @return
	 * den Bedarfsvektor des Produktsystems
	 */
	
	public HashMap<Flow, Double> getBedarfsvektor() {
		return bedarfsvektor;
	}

	/**
	 * @throws ArithmeticException
	 * wenn im Skalierungsvektor ein Element mit negativem
	 * Vorzeichen auftritt. Fehlertext: "Vorzeichenfehler 
	 * im Skalierungsvektor"
	 */

	@Override
	public HashMap<Flow, HashMap<FlowValueType, Double>> getElementarflussvektor() throws ArithmeticException {
		aktualisiere();		
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
	public HashMap<Flow, HashMap<FlowValueType, Double>> getProduktflussvektor() throws ArithmeticException {
		aktualisiere();		
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
	
	public void setBedarfsvektor(HashMap<Flow, Double> bv) {
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
}
