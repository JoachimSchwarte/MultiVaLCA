/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.util.LinkedHashMap;

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
		LinkedHashMap<ObjectType, String> r = new LinkedHashMap<ObjectType, String>();
		r.put(ObjectType.ProcessModule, GuiStrings.getGS("mp12",l));
		r.put(ObjectType.ProductSystem, GuiStrings.getGS("mp13",l));
		r.put(ObjectType.ProductDeclaration, GuiStrings.getGS("mp17",l));	
		return r;
	}
}
