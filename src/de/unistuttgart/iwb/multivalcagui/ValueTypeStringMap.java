/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import de.unistuttgart.iwb.multivalca.ValueType;

/**
 * Zusammenstellung der Textkonstanten für die
 * multilinguale Realisierung der Enumeration FlowValueType
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.495
 */

public class ValueTypeStringMap {
	
	private static Language l = GUILanguage.getChosenLanguage();
	private static Locale locale = MultiVaLCA.LANGUAGES.get(l);
	private static String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
	private static ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
	
	public static LinkedHashMap<ValueType, String> getFVTS(Language l) {
		locale = MultiVaLCA.LANGUAGES.get(l);
		baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		bundle = ResourceBundle.getBundle(baseName, locale);
		LinkedHashMap<ValueType, String> r = new LinkedHashMap<ValueType, String>();
		r.put(ValueType.MeanValue, bundle.getString("cob03"));
		r.put(ValueType.LowerBound, bundle.getString("p02n5"));
		r.put(ValueType.UpperBound, bundle.getString("p02n6"));
		return r;
	}

}
