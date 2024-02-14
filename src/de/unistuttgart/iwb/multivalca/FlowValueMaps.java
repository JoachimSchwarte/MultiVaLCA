/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;
import java.util.LinkedHashMap;

/**
 * Dieses Interface muss von all jenen Klassen implementiert werden,
 * zu deren Objekten jeweils ein Elementarflussvektor 
 * und ein Produktflussvektor angeben oder berechnet werden kann, so 
 * dass diese Objekte als Prozessmodule innerhalb eines Produktsystems
 * auftreten können.
 * 
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.813
 */

public interface FlowValueMaps {
	
	/**
	 * @return
	 * ... den Elementarflussvektor des aktuellen Objekts.
	 */
	
	public LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> getElementarflussvektor();
	
	/**
	 * @return
	 * ... den Namen des Objektes vom Interface-Typ Flussvektoren
	 */
	
	public String getName();
	
	/**
	 * @return
	 * ... den Produktflussvektor des aktuellen Objekts.
	 */
	
	public LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> getProduktflussvektor();

	public LinkedHashMap<ImpactValueMaps, LinkedHashMap<ValueType, Double>> getEPDFlussvektor();
}
