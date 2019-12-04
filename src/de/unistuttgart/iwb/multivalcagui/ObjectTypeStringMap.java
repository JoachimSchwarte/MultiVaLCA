/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import de.unistuttgart.iwb.multivalca.ObjectType;

/**
 * Zusammenstellung der Textkonstanten f�r die
 * multilinguale Realisierung der Enumeration ObjectType
 * 
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.548
 */

public class ObjectTypeStringMap {
	
	public static LinkedHashMap<ObjectType, String> getOTS(Language l) {
		Locale locale = MultiVaLCA.LANGUAGES.get(l);
		String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
		LinkedHashMap<ObjectType, String> r = new LinkedHashMap<ObjectType, String>();
		r.put(ObjectType.ProcessModule, bundle.getString("mp12"));
		r.put(ObjectType.ProcessModuleGroup, bundle.getString("mp122"));
		r.put(ObjectType.SubSystemGroup, bundle.getString("mp23"));
		r.put(ObjectType.ProductSystem, bundle.getString("mp13"));
		r.put(ObjectType.ProductDeclaration, bundle.getString("mp17"));	
		r.put(ObjectType.ProductDeclarationGroup, bundle.getString("mp22"));
		return r;
	}
}
