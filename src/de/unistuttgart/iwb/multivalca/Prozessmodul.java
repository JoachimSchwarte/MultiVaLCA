/*	
 * Beispielprojekt LCI (Sachbilanz)
 * Wintersemester 2016/2017
 */

package de.unistuttgart.iwb.multivalca;
import java.util.HashMap;

/**
 * Diese Klasse dient zur Erzeugung von Prozessmodulen.
 * 
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.1
 */

public class Prozessmodul 
implements Flussvektoren {
	
	// Klassenvariable:
	
	private static HashMap<String, Prozessmodul> allInstances = new HashMap<String, Prozessmodul>();
	
	// Instanzvariablen:
	
	private String name;
	private HashMap<Fluss, Double> efv = 
			new HashMap<Fluss, Double>(); //Elementarflüsse
	private HashMap<Fluss, Double> pfv = 
			new HashMap<Fluss, Double>(); //Produktflüsse
	
	// Konstruktor:
			
	private Prozessmodul(String name) {
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
	
	public static HashMap<String, Prozessmodul> getAllInstances() {
		return allInstances;
	}
	
	/**
	 * Liefert ein Prozessmodul
	 * @param string
	 * Name des Prozessmoduls
	 * @return
	 * ... das gesuchte Prozessmodul
	 */
	
	public static Prozessmodul getInstance(String string) {
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
	
	public static Prozessmodul instance(String name) {
		if (allInstances.containsKey(name) == false) {
			new Prozessmodul(name);
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
	 * Vorzeichen) fliest.
	 */
	
	public void addFluss(Fluss fluss,Double wert) {
		if (fluss.getTyp() == FlussTyp.Elementar) {
			efv.put(fluss, wert);
		} else {
			pfv.put(fluss, wert);
		}
	}
	
	@Override
	public HashMap<Fluss, Double> getElementarflussvektor() {
		return efv; 
	}	
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public HashMap<Fluss, Double> getProduktflussvektor() {
		return pfv; 
	}
	

	
	/**
	 * Entfernt einen Fluss aus dem betroffenen Flussvektor.
	 * 
	 * @param fluss
	 * der zu entfernende Fluß
	 */
	
	public void removeFluss(Fluss fluss) {
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
}

