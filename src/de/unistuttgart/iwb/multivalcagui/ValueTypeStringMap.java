/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.util.LinkedHashMap;
import de.unistuttgart.iwb.multivalca.ValueType;

/**
 * Zusammenstellung der Textkonstanten für die
 * multilinguale Realisierung der Enumeration FlowValueType
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.495
 */

public class ValueTypeStringMap {
	public static LinkedHashMap<ValueType, String> getFVTS(Language l) {
		LinkedHashMap<ValueType, String> r = new LinkedHashMap<ValueType, String>();
		r.put(ValueType.MeanValue, GuiStrings.getGS("cob03",l));
		r.put(ValueType.LowerBound, GuiStrings.getGS("p02n5",l));
		r.put(ValueType.UpperBound, GuiStrings.getGS("p02n6",l));
		return r;
	}

}
