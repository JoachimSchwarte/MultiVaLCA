/*	
 * MultiVaLCA
 */
package de.unistuttgart.iwb.multivalcagui;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.22
 */

public class GuiStrings {
	public static String getGS(String s, Language l) {
		String r = "";
		String vn = " 0.22";
		String date = "20.02.2018";
		if (l == Language.Deutsch) {
			switch (s) {
			case "head1": r = "MulitVaLCA"; break;
			case "head2": r = "Version"+vn; break;
			case "date": r = date; break;
			case "info1": r = "Dr.-Ing. Joachim Schwarte"; break;
			case "info2": r = "Insitut für Werkstoffe im Bauwesen"; break;
			case "info3": r = "Universität Stuttgart"; break;
			case "mp1": r = "Neu"; break;
			case "mp11": r = "Fluss"; break;
			case "mp11e": r = "neues Flussobjekt erfassen"; break;
			case "mp12": r = "Prozessmodul"; break;
			case "mp12e": r = "neues Prozessmodul erfassen"; break;
			case "mp13": r = "Produktsystem"; break;
			case "mp13e": r = "neues Produktsystem erfassen"; break;
			case "mp2": r = "Hilfe"; break;
			case "mp21": r = "Über"; break;
			case "mp21e": r = "Info"; break;
			case "mp3": r = "Einstellungen"; break;
			case "mp31": r = "Sprache"; break;
			case "mp31e": r = "Sprachauswahl"; break;
			case "bt1": r = "speichern"; break;
			case "p1l1": r = "Neuer Fluss"; break;
			case "p1l2": r = "Name des Flusses"; break;
			case "p1l3": r = "Typ"; break;
			case "p1l4": r = "Einheit"; break;
			case "p1l5": r = ">>> ... <<<"; break;

			}		
		}	
		if (l == Language.English) {
			switch (s) {
			case "head1": r = "MulitVaLCA"; break;
			case "head2": r = "Version"+vn; break;
			case "date": r = date; break;
			case "info1": r = "Dr.-Ing. Joachim Schwarte"; break;
			case "info2": r = "Institute for Construction Materials"; break;
			case "info3": r = "University of Stuttgart"; break;
			case "mp1": r = "New"; break;
			case "mp11": r = "Flow"; break;
			case "mp11e": r = "create new Flow"; break;
			case "mp12": r = "Process Module"; break;
			case "mp12e": r = "create new Process Module"; break;
			case "mp13": r = "Product System"; break;
			case "mp13e": r = "create new Product System"; break;
			case "mp2": r = "Help"; break;
			case "mp21": r = "about"; break;
			case "mp21e": r = "Info"; break;
			case "mp3": r = "Preferences"; break;
			case "mp31": r = "Language"; break;
			case "mp31e": r = "Language selection"; break;
			case "bt1": r = "store"; break;
			case "p1l1": r = "new Flow"; break;
			case "p1l2": r = "Name of the Flow"; break;
			case "p1l3": r = "Type"; break;
			case "p1l4": r = "Unit"; break;
			case "p1l5": r = ">>> ... <<<"; break;

			
			}		
		}
		return r;
	}
}
