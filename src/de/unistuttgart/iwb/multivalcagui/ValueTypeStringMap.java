/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import de.unistuttgart.iwb.multivalca.ValueType;

/**
 * Zusammenstellung der Textkonstanten f�r die
 * multilinguale Realisierung der Enumeration FlowValueType
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.495
 */

public class ValueTypeStringMap {
	
	public static LinkedHashMap<ValueType, String> getFVTS(Language l) {
		Locale locale = MultiVaLCA.LANGUAGES.get(l);
		String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
		LinkedHashMap<ValueType, String> r = new LinkedHashMap<ValueType, String>();
		r.put(ValueType.MeanValue, bundle.getString("cob03"));
		r.put(ValueType.LowerBound, bundle.getString("p02n5"));
		r.put(ValueType.UpperBound, bundle.getString("p02n6"));
		return r;
	}
}
