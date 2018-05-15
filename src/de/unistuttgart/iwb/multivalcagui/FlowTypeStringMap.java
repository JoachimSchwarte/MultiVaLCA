/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.util.LinkedHashMap;
import de.unistuttgart.iwb.multivalca.FlowType;

/**
 * Zusammenstellung der Textkonstanten für die
 * multilinguale Realisierung der Enumeration FlowType
 * 
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.495
 */

public class FlowTypeStringMap {
	public static LinkedHashMap<FlowType, String> getFTS(Language l) {
		LinkedHashMap<FlowType, String> r = new LinkedHashMap<FlowType, String>();
		r.put(FlowType.Elementary, GuiStrings.getGS("cob01",l));
		r.put(FlowType.Product, GuiStrings.getGS("cob02",l));	
		return r;
	}
}
