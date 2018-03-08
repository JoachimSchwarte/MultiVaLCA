/*	
 * MultiVaLCA
 */
package de.unistuttgart.iwb.multivalcagui;

/**
 * Zusammenstellung der Textkonstanten f�r die
 * multilinguale Beschriftung aller statischen GUI-Elemente
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.42
 */

public class GuiStrings {
	public static String getGS(String s, Language l) {
		String r = "";
		String vn = " 0.42";
		String date = "08.03.2018";
		if (l == Language.Deutsch) {
			switch (s) {
			case "head1": r = "MultiVaLCA"; break;
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
			case "stat20": r = ">>> Bitte keine Leerzeichen verwenden! <<<"; break;
			case "mp4": r = "Liste"; break;
			case "mp41": r = "Fl�sse"; break;
			case "mp41e": r = "Liste aller Fl�sse"; break;
			case "mp42": r = "Prozessmodule"; break;
			case "mp42e": r = "Liste aller Prozessmodule"; break;
			case "mp43": r = "Produktsysteme"; break;
			case "mp43e": r = "Liste aller Produktsysteme"; break;
			case "p02n5": r = "Untergrenze"; break;
			case "p02n6": r = "Obergrenze"; break;
			case "bt10": r = "Grenzwerte best�tigen"; break;
			case "stat21": r = ">>> unzul�ssige Eingabe <<<"; break;
			case "p06n1": r = "Name"; break;
			case "p08n1": r = "Elementtyp"; break;
			case "p08n2": r = "Elementname"; break;
			case "p08n3": r = "Subsystem"; break;
			case "p08n4": r = "Bedarf"; break;
			case "mp5": r = "Berechnen"; break;
			case "mp51": r = "Sachbilanz berechnen"; break;
			case "mp51e": r = "Sachbilanzen aller Produktsysteme"; break;
			case "mp6": r = "Datei"; break;
			case "mp61": r = "XML-Export"; break;
			case "mp61e": r = "Exportieren des Objektbestandes in eine XML-Datei"; break;
			case "mp62": r = "XML-Import"; break;
			case "mp62e": r = "Importieren eines Objektbestandes aus einer XML-Datei"; break;
			case "mp14": r = "Wirkungskategorie"; break;
			case "mp14e": r = "neue Wirkungskategorie erfassen"; break;
			case "p10n1": r = "Neue Wirkungskategorie"; break;
			case "p10n2": r = "Name der Wirkungskategorie"; break;
			case "p10n3": r = "Indikator"; break;
			case "stat22": r = ">>> Es wurde keine Kategorie angegeben. <<<"; break;
			case "stat23": r = ">>> Die angegebene Kategorie ist bereits vorhanden. "; break;
			case "stat24": r = ">>> Es wurde kein Indikator angegeben. <<<"; break;
			case "stat25": r = ">>> Anzahl Wirkungskategorien: "; break;
			case "mp44": r = "Wirkungskategorien"; break;
			case "mp44e": r = "Liste aller Wirkungskategorien"; break;
			case "p11n1": r = "Wirkungsindikator"; break;



			}		
		}	
		if (l == Language.English) {
			switch (s) {
			case "head1": r = "MultiVaLCA"; break;
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
			case "stat20": r = ">>> Space is an illegal character! <<<"; break;
			case "mp4": r = "List"; break;
			case "mp41": r = "Flows"; break;
			case "mp41e": r = "List of all Flows"; break;
			case "mp42": r = "Process Modules"; break;
			case "mp42e": r = "List of all Process Modules"; break;
			case "mp43": r = "Product Systems"; break;
			case "mp43e": r = "List of all Product Systems"; break;
			case "p02n5": r = "lower Bound"; break;
			case "p02n6": r = "upper Bound"; break;
			case "bt10": r = "confirm Bounds"; break;
			case "stat21": r = ">>> Illegal Entry <<<"; break;
			case "p06n1": r = "Name"; break;
			case "p08n1": r = "Type of Element"; break;
			case "p08n2": r = "Name of Element"; break;
			case "p08n3": r = "Subsystem"; break;
			case "p08n4": r = "Demand"; break;
			case "mp5": r = "Evaluation"; break;
			case "mp51": r = "LCI Evaluation"; break;
			case "mp51e": r = "LCIs for all Product Systems"; break;
			case "mp6": r = "File"; break;
			case "mp61": r = "XML-Export"; break;
			case "mp61e": r = "Database export into XML-file"; break;
			case "mp62": r = "XML-Import"; break;
			case "mp62e": r = "Database import from XML-file"; break;
			case "mp14": r = "Impact Category"; break;
			case "mp14e": r = "Create new Impact Category"; break;
			case "p10n1": r = "New Impact Category"; break;
			case "p10n2": r = "Name of the Impact Category"; break;
			case "p10n3": r = "Indicator"; break;
			case "stat22": r = ">>> No Category has been entered. <<<"; break;
			case "stat23": r = ">>> The Category is already existing. <<<"; break;
			case "stat24": r = ">>> No Indicator has been entered. <<<"; break;
			case "stat25": r = ">>> Number of Impact Categories: "; break;
			case "mp44": r = "Impact Categories"; break;
			case "mp44e": r = "List of all Impact Categories"; break;
			case "p11n1": r = "Category Indicator"; break;



			
			}		
		}
		return r;
	}
}
