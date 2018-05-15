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
 * @version 0.495
 */

public interface ImpactValueMaps {
	
	public LinkedHashMap<ImpactCategory, LinkedHashMap<ValueType, Double>> getImpactValueMap(LCIAMethod bm);

}
