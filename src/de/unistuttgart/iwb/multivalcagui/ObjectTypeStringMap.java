/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.util.HashMap;

import de.unistuttgart.iwb.multivalca.ObjectType;

/**
 * Zusammenstellung der Textkonstanten für die
 * multilinguale Realisierung der Enumeration ObjectType
 * 
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.485
 */

public class ObjectTypeStringMap {
	public static HashMap<ObjectType, String> getOTS(Language l) {
		HashMap<ObjectType, String> r = new HashMap<ObjectType, String>();
		r.put(ObjectType.ProcessModule, GuiStrings.getGS("mp12",l));
		r.put(ObjectType.ProductSystem, GuiStrings.getGS("mp13",l));	
		return r;
	}
}
