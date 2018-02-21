/*	
 * MultiVaLCA
 */
package de.unistuttgart.iwb.multivalcagui;

/**
 * Zusammenstellung der Textkonstanten f�r die
 * multilinguale Beschriftung aller statischen GUI-Elemente
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.25
 */

public class GuiStrings {
	public static String getGS(String s, Language l) {
		String r = "";
		String vn = " 0.25";
		String date = "21.02.2018";
		if (l == Language.Deutsch) {
			switch (s) {
			case "head1": r = "MulitVaLCA"; break;
			case "head2": r = "Version"+vn; break;
			case "date": r = date; break;
			case "info1": r = "Dr.-Ing. Joachim Schwarte"; break;
			case "info2": r = "Insitut f�r Werkstoffe im Bauwesen"; break;
			case "info3": r = "Universit�t Stuttgart"; break;
			case "mp1": r = "Neu"; break;
			case "mp11": r = "Fluss"; break;
			case "mp11e": r = "neues Flussobjekt erfassen"; break;
			case "mp12": r = "Prozessmodul"; break;
			case "mp12e": r = "neues Prozessmodul erfassen"; break;
			case "mp13": r = "Produktsystem"; break;
			case "mp13e": r = "neues Produktsystem erfassen"; break;
			case "mp2": r = "Hilfe"; break;
			case "mp21": r = "�ber"; break;
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
			case "bt03": r = "Fluss hinzuf�gen"; break;
			case "bt04": r = "fertig"; break;
			case "p03n1": r = "Neues Produktsystem"; break;
			case "p03n2": r = "Name des Produktsystems"; break;
			case "p03n3": r = "Prozessmodul/Subsystem"; break;
			case "p03n4": r = "Produkt im Bedarfsvektor"; break;
			case "p03n6": r = "Vor- oder Koppelprodukt"; break;
			case "bt05": r = "neues Produktsystem anlegen"; break;
			case "bt06": r = "Modul/System hinzuf�gen"; break;
			case "bt07": r = "weiter"; break;
			case "bt08": r = "Bedarfsvektor erg�nzen"; break;
			case "bt09": r = "VK-Fluss hinzuf�gen"; break;
			case "stat02": r = ">>> Es wurde kein Name angegeben. <<<"; break;
			case "stat03": r = ">>> Der angegebene Name ist bereits vorhanden. <<<"; break;
			case "stat04": r = ">>> Anzahl Flussobjekte: "; break;
			case "stat05": r = " <<<"; break;
			case "stat06": r = ">>> Anzahl Prozessmodule: "; break;
			case "stat07": r = ">>> unvollst�ndige Eingabe <<<"; break;
			case "stat08": r = ">>> Prozessmodul "; break;
			case "stat09": r = " besitzt "; break;
			case "stat10": r = " Fl�sse <<<"; break;
			case "stat11": r = ">>> unbekannter Flussname <<<"; break;
			case "stat12": r = ">>> Anzahl Produktsysteme: "; break;
			case "stat13": r = ">>> unzul�ssige Rekursion <<<"; break;
			case "stat14": r = ">>> Produktsystem "; break;
			case "stat15": r = " besteht aus "; break;
			case "stat16": r = " Elementen <<<"; break;
			case "stat17": r = ">>> Name ist weder Prozessmodul noch Produktsystem <<<"; break;
			case "stat18": r = ">>> Der Bedarfsvektor enth�lt "; break;
			case "stat19": r = ">>> Der VK-Vektor enth�lt "; break;



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
			case "mp11e": r = "Create new Flow"; break;
			case "mp12": r = "Process Module"; break;
			case "mp12e": r = "Create new Process Module"; break;
			case "mp13": r = "Product System"; break;
			case "mp13e": r = "Create new Product System"; break;
			case "mp2": r = "Help"; break;
			case "mp21": r = "About"; break;
			case "mp21e": r = "Info"; break;
			case "mp3": r = "Preferences"; break;
			case "mp31": r = "Language"; break;
			case "mp31e": r = "Language selection"; break;
			case "bt01": r = "Store"; break;
			case "p01n1": r = "New Flow"; break;
			case "p01n2": r = "Name of the Flow"; break;
			case "p01n3": r = "Type"; break;
			case "p01n4": r = "Unit"; break;
			case "stat01": r = ">>> ... <<<"; break;
			case "p02n1": r = "New Process Module"; break;
			case "p02n2": r = "Name of the Process Module"; break;
			case "p02n4": r = "Amount"; break;
			case "bt02": r = "Create new Process Module"; break;
			case "bt03": r = "Add Flow"; break;
			case "bt04": r = "Finished"; break;
			case "p03n1": r = "New Product System"; break;
			case "p03n2": r = "Name of the Product System"; break;
			case "p03n3": r = "Process Module/Subsystem"; break;
			case "p03n4": r = "Product in demand vector"; break;
			case "p03n6": r = "Pre- or Coproduct"; break;
			case "bt05": r = "Create new Product System"; break;
			case "bt06": r = "Add Module or System"; break;
			case "bt07": r = "Next"; break;
			case "bt08": r = "Add Product to demand vector"; break;
			case "bt09": r = "Add Pre- or Coproduct Flow"; break;
			case "stat02": r = ">>> No Name has been entered. <<<"; break;
			case "stat03": r = ">>> The Name is already in use. <<<"; break;
			case "stat04": r = ">>> Number of Flow Objects: "; break;
			case "stat05": r = " <<<"; break;
			case "stat06": r = ">>> Number of Process Modules: "; break;
			case "stat07": r = ">>> Incomplete Entry <<<"; break;
			case "stat08": r = ">>> Process Module "; break;
			case "stat09": r = " owns "; break;
			case "stat10": r = " Flows <<<"; break;
			case "stat11": r = ">>> Unknown Flow Name <<<"; break;
			case "stat12": r = ">>> Number of Product Systems: "; break;
			case "stat13": r = ">>> Illegal Recursion <<<"; break;
			case "stat14": r = ">>> Product System "; break;
			case "stat15": r = " contains "; break;
			case "stat16": r = " Elements <<<"; break;
			case "stat17": r = ">>> The Name does not belong to a Process Module or a Product System <<<"; break;
			case "stat18": r = ">>> The demand vector contains "; break;
			case "stat19": r = ">>> The vector of Pre- and Coproducts contains "; break;


			
			}		
		}
		return r;
	}
}