/*	
 * MultiVaLCA
 */
package de.unistuttgart.iwb.multivalcagui;

import java.util.HashMap;
import de.unistuttgart.iwb.multivalca.FlowType;

/**
 * Zusammenstellung der Textkonstanten für die
 * multilinguale Realisierung der Enumeration FlowType
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.291
 */

public class FlowTypeStrings {
	public static HashMap<FlowType, String> getFTS(Language l) {
		HashMap<FlowType, String> r = new HashMap<FlowType, String>();
		if (l == Language.Deutsch) {
			r.put(FlowType.Elementary, "Elementarfluss");
			r.put(FlowType.Product, "Produktfluss");			
		}	
		if (l == Language.English) {
			r.put(FlowType.Elementary, "elementary Flow");
			r.put(FlowType.Product, "Product");			
		}
		return r;
	}
}
