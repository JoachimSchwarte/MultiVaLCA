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
 * @version 0.26
 */

public class FlowValueTypeStrings {
	public static HashMap<String, FlowValueType> getFVTS(Language l) {
		HashMap<String, FlowValueType> r = new HashMap<String, FlowValueType>();
		if (l == Language.Deutsch) {
			r.put("Hauptwert", FlowValueType.MeanValue);
			r.put("Untergrenze", FlowValueType.LowerBound);
			r.put("Obergrenze", FlowValueType.UpperBound);
		}	
		if (l == Language.English) {
			r.put("Main Value", FlowValueType.MeanValue);
			r.put("Lower Bound", FlowValueType.LowerBound);
			r.put("Upper Bound", FlowValueType.UpperBound);		
		}
		return r;
	}

}
