/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import de.unistuttgart.iwb.multivalca.ObjectType;

/**
 * Zusammenstellung der Textkonstanten für die
 * multilinguale Realisierung der Enumeration ObjectType
 * 
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.506
 */

public class ObjectTypeStringMap {
	public static LinkedHashMap<ObjectType, String> getOTS(Language l) {
		Locale locale = MultiVaLCA.LANGUAGES.get(l);
		String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
		LinkedHashMap<ObjectType, String> r = new LinkedHashMap<ObjectType, String>();
		r.put(ObjectType.ProcessModule, bundle.getString("mp12"));
		r.put(ObjectType.ProductSystem, bundle.getString("mp13"));
		r.put(ObjectType.ProductDeclaration, bundle.getString("mp17"));	
		return r;
	}
}
