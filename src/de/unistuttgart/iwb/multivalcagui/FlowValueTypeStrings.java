/*	
 * MultiVaLCA
 */
package de.unistuttgart.iwb.multivalcagui;

import java.util.HashMap;
import de.unistuttgart.iwb.multivalca.ValueType;

/**
 * Zusammenstellung der Textkonstanten für die
 * multilinguale Realisierung der Enumeration FlowValueType
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.291
 */

public class FlowValueTypeStrings {
	public static HashMap<ValueType, String> getFVTS(Language l) {
		HashMap<ValueType, String> r = new HashMap<ValueType, String>();
		if (l == Language.Deutsch) {
			r.put(ValueType.MeanValue, "Hauptwert");
			r.put(ValueType.LowerBound, "Untergrenze");
			r.put(ValueType.UpperBound, "Obergrenze");
		}	
		if (l == Language.English) {
			r.put(ValueType.MeanValue, "Main Value");
			r.put(ValueType.LowerBound, "Lower Bound");
			r.put(ValueType.UpperBound, "Upper Bound");		
		}
		return r;
	}

}
