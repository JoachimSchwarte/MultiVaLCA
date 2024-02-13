/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalca;

import java.util.LinkedHashMap;

/**
 * Dieses Interface muss von all jenen Klassen implementiert werden,
 * zu deren Objekten jeweils ein Wirkungsvektor angegeben oder 
 * berechnet werden kann, so dass diese Objekte als Komponenten 
 * innerhalb einer Produktkomposition auftreten können.
 * 
 * @author Dr.-Ing. Joachim Schwarte, Helen Hein, Johannes Dippon
 * @version 0.812
 */

public interface ImpactValueMaps {
	
	public LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> getImpactValueMap();
	
	/**
	 * Liefert den Wirkungsvektor des aktuellen Objekts für
	 * die relevante Bewertungsmethode
	 * @param bm
	 * die relevante Bewertungsmethode 
	 * @return
	 * ... den zugehörigen Wirkungsvektor 
	 */
	
	public LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> getImpactValueMap(LCIAMethod bm);
	
	/**
	 * @return
	 * ... den Namen des Objektes vom Interface-Typ Wirkungsvektor
	 */
	
	public String getName();
	
	/**
	 * Setzt den Name des Objekts vom Interface-Typ Wirkungsvektor
	 * @param name 
	 * ... der Namen des Objektes 
	 */
	
	public void setName(String name);

}
