/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;
import java.util.HashMap;
import java.util.LinkedList;
import Jama.Matrix;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.2
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
	private HashMap<Flow, Double> efv 
			= new HashMap<Flow, Double>();
	private HashMap<Flow, Double> pfv 
			= new HashMap<Flow, Double>();
	
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
	
	private void aktualisiere() throws ArithmeticException {
		LinkedList<Flow> produktFlussliste = new LinkedList<Flow>();
		for(FlowValueMaps m : modulliste){
			HashMap<Flow, Double> modulVektor = m.getProduktflussvektor();
			for (Flow key : modulVektor.keySet()) {		
				if ((produktFlussliste.contains(key) == false) &&
					(vorUndKoppelProdukte.contains(key) == false) &&
					(m.getProduktflussvektor().get(key) != 0))	{
					produktFlussliste.add(key);	
				}				
			}
		}
		if (produktFlussliste.size() != modulliste.size()) {
			throw new ArithmeticException("Matrix nicht quadratisch");
		}
		double[][] arrayA = new double[produktFlussliste.size()][modulliste.size()];
		for(FlowValueMaps m : modulliste){
			HashMap<Flow, Double> modulVektor = m.getProduktflussvektor();
			for (Flow key : modulVektor.keySet()) {
 				if (produktFlussliste.contains(key)) {
					arrayA[produktFlussliste.indexOf(key)][modulliste.indexOf(m)]=modulVektor.get(key);	
 				}							
			}
		}
		LinkedList<Flow> elementarFlussliste = new LinkedList<Flow>();
		for(FlowValueMaps m : modulliste){
			HashMap<Flow, Double> modulVektor = m.getElementarflussvektor();
			for (Flow key : modulVektor.keySet()) {
				if (elementarFlussliste.contains(key) == false){
					elementarFlussliste.add(key);					
				}				
			}
		}
		double[][] arrayB = new double[elementarFlussliste.size()][modulliste.size()];
		for(FlowValueMaps m : modulliste){
			HashMap<Flow, Double> modulVektor = m.getElementarflussvektor();
			for (Flow key : modulVektor.keySet()) {
				arrayB[elementarFlussliste.indexOf(key)][modulliste.indexOf(m)]=modulVektor.get(key);				
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
		Matrix matrixB = new Matrix(arrayB);
		Matrix matrixF = new Matrix(arrayF);
		Matrix matrixS = matrixA.solve(matrixF);
		Matrix matrixG = matrixB.times(matrixS);
		double[][] arrayS = matrixS.getArray();
		for (Integer i=0; i<arrayS.length; i++){
			if (arrayS[i][0]<0){
				throw new ArithmeticException("Vorzeichenfehler im Skalierungsvektor");			
			}
		}

		for(Flow ef : elementarFlussliste) {
			efv.put(ef, matrixG.get(elementarFlussliste.indexOf(ef),0));
		}	
		produktFlussliste.addAll(vorUndKoppelProdukte);
		double[][] arrayA1 = new double[produktFlussliste.size()][modulliste.size()];
		for(FlowValueMaps m : modulliste){
			HashMap<Flow, Double> modulVektor = m.getProduktflussvektor();
			for (Flow key : produktFlussliste) {
				if (modulVektor.containsKey(key)) {
					arrayA1[produktFlussliste.indexOf(key)][modulliste.indexOf(m)]=modulVektor.get(key);
				} else {
					arrayA1[produktFlussliste.indexOf(key)][modulliste.indexOf(m)]=0;
				}
								
			}
		}
		Matrix matrixA1 = new Matrix(arrayA1);
		Matrix matrixG1 = matrixA1.times(matrixS);
		for(Flow pf : produktFlussliste) {
			pfv.put(pf, matrixG1.get(produktFlussliste.indexOf(pf),0));
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
	public HashMap<Flow, Double> getElementarflussvektor() throws ArithmeticException {
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
	public HashMap<Flow, Double> getProduktflussvektor() throws ArithmeticException {
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
