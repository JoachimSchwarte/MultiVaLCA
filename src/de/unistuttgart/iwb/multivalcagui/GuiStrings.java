/*	
 * MultiVaLCA
 */
package de.unistuttgart.iwb.multivalcagui;

/**
 * Zusammenstellung der Textkonstanten für die
 * multilinguale Beschriftung aller statischen GUI-Elemente
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.24
 */

public class GuiStrings {
	public static String getGS(String s, Language l) {
		String r = "";
		String vn = " 0.24";
		String date = "21.02.2018";
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
			case "bt01": r = "speichern"; break;
			case "p01n1": r = "Neuer Fluss"; break;
			case "p01n2": r = "Name des Flusses"; break;
			case "p01n3": r = "Typ"; break;
			case "p01n4": r = "Einheit"; break;
			case "stat01": r = ">>> ... <<<"; break;
			case "p02n1": r = "Neues Prozessmodul"; break;
			case "p02n2": r = "Name des Prozessmoduls"; break;
			case "p02n4": r = "Menge"; break;
			case "bt02": r = "neues Prozessmodul anlegen"; break;
			case "bt03": r = "Fluss hinzufügen"; break;
			case "bt04": r = "fertig"; break;
			case "p03n1": r = "Neues Produktsystem"; break;
			case "p03n2": r = "Name des Produktsystems"; break;
			case "p03n3": r = "Prozessmodul/Subsystem"; break;
			case "p03n4": r = "Produkt im Bedarfsvektor"; break;
			case "p03n6": r = "Vor- oder Koppelprodukt"; break;
			case "bt05": r = "neues Produktsystem anlegen"; break;
			case "bt06": r = "Modul/System hinzufügen"; break;
			case "bt07": r = "weiter"; break;
			case "bt08": r = "Bedarfsvektor ergänzen"; break;
			case "bt09": r = "VK-Fluss hinzufügen"; break;


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
			case "bt01": r = "store"; break;
			case "p01n1": r = "new Flow"; break;
			case "p01n2": r = "Name of the Flow"; break;
			case "p01n3": r = "Type"; break;
			case "p01n4": r = "Unit"; break;
			case "stat01": r = ">>> ... <<<"; break;
			case "p02n1": r = "new Process Module"; break;
			case "p02n2": r = "Name of the Process Module"; break;
			case "p02n4": r = "Amount"; break;
			case "bt02": r = "create new Process Module"; break;
			case "bt03": r = "add Flow"; break;
			case "bt04": r = "finished"; break;
			case "p03n1": r = "new Product System"; break;
			case "p03n2": r = "Name of the Product System"; break;
			case "p03n3": r = "Process Module/Subsystem"; break;
			case "p03n4": r = "Product in demand vector"; break;
			case "p03n6": r = "Pre- or Coproduct"; break;
			case "bt05": r = "create new Product System"; break;
			case "bt06": r = "add Module or System"; break;
			case "bt07": r = "next"; break;
			case "bt08": r = "add Product to demand vector"; break;
			case "bt09": r = "add Pre- or Coproduct Flow"; break;


			
			}		
		}
		return r;
	}
}
