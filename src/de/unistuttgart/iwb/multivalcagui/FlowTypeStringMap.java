/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import de.unistuttgart.iwb.multivalca.FlowType;

/**
 * Zusammenstellung der Textkonstanten für die
 * multilinguale Realisierung der Enumeration FlowType
 * 
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.495
 */

public class FlowTypeStringMap {
	
	private static Language l = GUILanguage.getChosenLanguage();
	private static Locale locale = MultiVaLCA.LANGUAGES.get(l);
	private static String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
	private static ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
	
	public static LinkedHashMap<FlowType, String> getFTS(Language l) {
		locale = MultiVaLCA.LANGUAGES.get(l);
		baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		bundle = ResourceBundle.getBundle(baseName, locale);
		LinkedHashMap<FlowType, String> r = new LinkedHashMap<FlowType, String>();
		r.put(FlowType.Elementary, bundle.getString("cob01"));
		r.put(FlowType.Product, bundle.getString("cob02"));	
		return r;
	}
}
