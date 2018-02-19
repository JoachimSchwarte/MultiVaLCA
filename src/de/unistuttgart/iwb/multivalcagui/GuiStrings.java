/*	
 * MultiVaLCA
 */
package de.unistuttgart.iwb.multivalcagui;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.21
 */

public class GuiStrings {
	public static String getGS(String s, Language l) {
		String r = "";
		if (l == Language.Deutsch) {
			switch (s) {
			case "mp1": r = "Neu"; break;
			case "mp11": r = "Fluss"; break;
			case "mp11e": r = "neues Flussobjekt erfassen"; break;
			case "mp12": r = "Prozessmodul"; break;
			case "mp12e": r = "neues Prozessmodul erfassen"; break;
			case "mp13": r = "Produktsystem"; break;
			case "mp13e": r = "neues Produktsystem erfassen"; break;

			}		
		}	
		if (l == Language.English) {
			switch (s) {
			case "mp1": r = "New"; break;
			case "mp11": r = "Flow"; break;
			case "mp11e": r = "create new Flow"; break;
			case "mp12": r = "Process Module"; break;
			case "mp12e": r = "create new Process Module"; break;
			case "mp13": r = "Product System"; break;
			case "mp13e": r = "create new Product System"; break;

			
			}		
		}
		return r;
	}
}
