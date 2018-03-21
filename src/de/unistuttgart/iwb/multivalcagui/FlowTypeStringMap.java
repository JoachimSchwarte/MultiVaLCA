/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.util.HashMap;
import de.unistuttgart.iwb.multivalca.FlowType;

/**
 * Zusammenstellung der Textkonstanten für die
 * multilinguale Realisierung der Enumeration FlowType
 * 
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.485
 */

public class FlowTypeStringMap {
	public static HashMap<FlowType, String> getFTS(Language l) {
		HashMap<FlowType, String> r = new HashMap<FlowType, String>();
		r.put(FlowType.Elementary, GuiStrings.getGS("cob01",l));
		r.put(FlowType.Product, GuiStrings.getGS("cob02",l));	
		return r;
	}
}
