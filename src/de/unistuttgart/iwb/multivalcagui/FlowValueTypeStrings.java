/*	
 * MultiVaLCA
 */
package de.unistuttgart.iwb.multivalcagui;

import java.util.HashMap;
import de.unistuttgart.iwb.multivalca.FlowValueType;

/**
 * Zusammenstellung der Textkonstanten für die
 * multilinguale Realisierung der Enumeration FlowValueType
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.291
 */

public class FlowValueTypeStrings {
	public static HashMap<FlowValueType, String> getFVTS(Language l) {
		HashMap<FlowValueType, String> r = new HashMap<FlowValueType, String>();
		if (l == Language.Deutsch) {
			r.put(FlowValueType.MeanValue, "Hauptwert");
			r.put(FlowValueType.LowerBound, "Untergrenze");
			r.put(FlowValueType.UpperBound, "Obergrenze");
		}	
		if (l == Language.English) {
			r.put(FlowValueType.MeanValue, "Main Value");
			r.put(FlowValueType.LowerBound, "Lower Bound");
			r.put(FlowValueType.UpperBound, "Upper Bound");		
		}
		return r;
	}

}
