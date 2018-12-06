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
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.559
 */

public interface ImpactValueMaps {
	
	/**
	 * Kommentar im Interface ImpactValueMaps
	 * @param bm
	 * @return
	 */
	public LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> getImpactValueMap(LCIAMethod bm);
		
	/**
	 * gets the name of a specific member 
	 * finalized in MCAObject
	 * @return
	 * the name of the member
	 */
	public String getName();
	
	/**
	 * overwrites the given object name
	 * finalized in MCAObject
	 * @param name
	 * the name of the respective member
	 */
	public void setName(String name);
}
