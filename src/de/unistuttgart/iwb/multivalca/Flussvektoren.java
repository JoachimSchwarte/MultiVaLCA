/*	
 * Beispielprojekt LCI (Sachbilanz)
 * Wintersemester 2016/2017
 */

package de.unistuttgart.iwb.multivalca;
import java.util.HashMap;

/**
 * Dieses Interface muss von all jenen Klassen implementiert werden,
 * zu deren Objekten jeweils ein Elementarflussvektor 
 * und ein Produktflussvektor angeben oder berechnet werden kann, so 
 * dass diese Objekte als Prozessmodule innerhalb eines Produktsystems
 * auftreten können.
 * 
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.1
 */

public interface Flussvektoren {
	
	/**
	 * @return
	 * ... den Elementarflussvektor des aktuellen Objekts.
	 */
	
	public HashMap<Fluss, Double> getElementarflussvektor();
	
	/**
	 * @return
	 * ... den Namen des Objektes vom Interface-Typ Flussvektoren
	 */
	
	public String getName();
	
	/**
	 * @return
	 * ... den Produktflussvektor des aktuellen Objekts.
	 */
	
	public HashMap<Fluss, Double> getProduktflussvektor();
}
