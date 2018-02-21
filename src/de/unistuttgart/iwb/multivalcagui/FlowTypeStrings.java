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
 * @version 0.24
 */

public class FlowTypeStrings {
	public static HashMap<String, FlowType> getFTS(Language l) {
		HashMap<String, FlowType> r = new HashMap<String, FlowType>();
		if (l == Language.Deutsch) {
			r.put("Elementarfluss", FlowType.Elementary);
			r.put("Produktfluss", FlowType.Product);			
		}	
		if (l == Language.English) {
			r.put("elementary Flow", FlowType.Elementary);
			r.put("Product", FlowType.Product);			
		}
		return r;
	}
}
