/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

/**
 * Zusammenstellung der Textkonstanten für die
 * multilinguale Beschriftung aller statischen GUI-Elemente
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.522
 */

public class GuiStrings {
	public static String getGS(String s, Language l) {
		String r = "";
		String vn = " 0.522";
		String date = "25.06.2018";
		if (l == Language.Deutsch) {
			switch (s) {
			case "head1": r = "MultiVaLCA"; break;
			case "head2": r = "Version"+vn; break;
			case "date": r = date; break;
			case "info1": r = "Dr.-Ing. Joachim Schwarte"; break;
			case "info2": r = "Insitut für Werkstoffe im Bauwesen"; break;
			case "info3": r = "Universität Stuttgart"; break;
			case "mp1": r = "Neu/Bearbeiten"; break;
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
			case "stat02": r = ">>> Es wurde kein Name angegeben. <<<"; break;
			case "stat03": r = ">>> Der angegebene Name ist bereits vorhanden. <<<"; break;
			case "stat04": r = ">>> Anzahl Flussobjekte: "; break;
			case "stat05": r = " <<<"; break;
			case "stat06": r = ">>> Anzahl Prozessmodule: "; break;
			case "stat07": r = ">>> unvollständige Eingabe <<<"; break;
			case "stat08": r = ">>> Prozessmodul "; break;
			case "stat09": r = " besitzt "; break;
			case "stat10": r = " Flüsse <<<"; break;
			case "stat11": r = ">>> unbekannter Flussname <<<"; break;
			case "stat12": r = ">>> Anzahl Produktsysteme: "; break;
			case "stat13": r = ">>> unzulässige Rekursion <<<"; break;
			case "stat14": r = ">>> Produktsystem "; break;
			case "stat15": r = " besteht aus "; break;
			case "stat16": r = " Elementen <<<"; break;
			case "stat17": r = ">>> Name ist weder Prozessmodul noch Produktsystem <<<"; break;
			case "stat18": r = ">>> Der Bedarfsvektor enthält "; break;
			case "stat19": r = ">>> Der VK-Vektor enthält "; break;
			case "stat20": r = ">>> Bitte keine Leerzeichen verwenden! <<<"; break;
			case "mp4": r = "Liste"; break;
			case "mp41": r = "Flüsse"; break;
			case "mp41e": r = "Liste aller Flüsse"; break;
			case "mp42": r = "Prozessmodule"; break;
			case "mp42e": r = "Liste aller Prozessmodule"; break;
			case "mp43": r = "Produktsysteme"; break;
			case "mp43e": r = "Liste aller Produktsysteme"; break;
			case "p02n5": r = "Untergrenze"; break;
			case "p02n6": r = "Obergrenze"; break;
			case "bt10": r = "Grenzwerte bestätigen"; break;
			case "stat21": r = ">>> unzulässige Eingabe <<<"; break;
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
			case "mp15": r = "Charakterisierungsfaktor"; break;
			case "mp15e": r = "neuen Charakterisierungsfaktor erfassen"; break;
			case "p12n1": r = "Neuer Charakterisierungsfaktor"; break;
			case "p12n2": r = "Name des Charakterisierungsfaktors"; break;
			case "p12n3": r = "Faktor"; break;
			case "stat26": r = ">>> unzulässiger Faktor <<<"; break;
			case "stat27": r = ">>> Die angegebene Wirkungskategorie existiert nicht. <<<"; break;
			case "mp45": r = "Charakterisierungsfaktoren"; break;
			case "mp45e": r = "Liste aller Charakterisierungsfaktoren"; break;
			case "p13n1": r = "Kategorie"; break;
			case "mp16": r = "Bewertungsmethode"; break;
			case "mp16e": r = "neue Bewertungsmethode erfassen"; break;
			case "mp46": r = "Bewertungsmethoden"; break;
			case "mp46e": r = "Liste der Bewertungsmethoden"; break;
			case "mp52": r = "Wirkungsabschätzung berechnen"; break;
			case "mp52e": r = "Wirkungsabschätzung für einzelne Objekte"; break;
			case "p14n1": r = "Neue Bewertungsmethode"; break;
			case "p14n2": r = "Name der Bewertungsmethode"; break;
			case "bt11": r = "hinzufügen"; break;
			case "stat28": r = ">>> Kategorien: "; break;
			case "stat29": r = " <<< >>> Charakterisierungsfaktoren: "; break;
			case "stat30": r = ">>> Der angegebene Charakterisierungsfaktor existiert nicht. <<<"; break;
			case "p16n1": r = "Wirkungsabschätzung"; break;
			case "p16n2": r = "Objekttyp"; break;
			case "bt12": r = "Berechnungsergebnisse anzeigen"; break;
			case "cob01": r = "Elementarfluss"; break;
			case "cob02": r = "Produktfluss"; break;
			case "cob03": r = "Hauptwert"; break;
			case "p16n3": r = "Objektname"; break;
			case "p16n4": r = "Werttyp"; break;
			case "p16n5": r = "Wert"; break;
			case "stat31": r = ">>> Das Modul ist bereits Bestandteil des Produktsystems. <<<"; break;
			case "mp17": r = "Produktdeklaration"; break;
			case "mp18": r = "Komponente"; break;
			case "mp19": r = "Komposition"; break;
			case "p17n1": r = "Neue Produktdeklaration"; break;
			case "p18n1": r = "Neue Komponente"; break;
			case "p19n1": r = "Neue Komposition"; break;
			case "mp17e": r = "neue Produktdeklaration erfassen"; break;
			case "mp18e": r = "neue Komponente erfassen"; break;
			case "mp19e": r = "neue Komposition erfassen"; break;
			case "mp47": r = "Produktdeklarationen"; break;
			case "mp48": r = "Komponenten"; break;
			case "mp49": r = "Kompositionen"; break;
			case "mp47e": r = "Liste der Produktdeklarationen"; break;
			case "mp48e": r = "Liste der Komponenten"; break;
			case "mp49e": r = "Liste der Kompositionen"; break;
			case "p17n2": r = "Name des zu deklarierenden Produkts"; break;
			case "stat32": r = ">>> Es wurde keine Bewertungsmethode angegeben. <<<"; break;
			case "stat33": r = ">>> Die angegebene Bewertungsmethode existiert nicht. <<<"; break;
			case "stat34": r = ">>> Anzahl Produktdeklarationen: "; break;
			case "stat35": r = ">>> Unkorrekte Wirkungskategorie <<<"; break;
			case "p18n2": r = "Name der Komponente"; break;
			case "p18n3": r = "Bezug"; break;
			case "bt13": r = "neue Komponente anlegen"; break;
			case "p19n2": r = "Name der Komposition"; break;
			case "p19n3": r = "hinzuzufügende Komponente"; break;
			case "bt14": r = "neue Komposition anlegen"; break;
			case "bt15": r = "Komponente hinzufügen"; break;
			case "stat36": r = ">>> Der Name ist anderweitig vergeben. <<<"; break;
			case "mp121": r = "Einzelmodul"; break;
			case "mp122": r = "Prozessmodulgruppe"; break;
			case "mp121e": r = "einzelnes Prozessmodul erfassen"; break;
			case "mp122e": r = "Gruppe von Prozessmodul erfassen"; break;
			case "p20n1": r = "Neue Prozessmodulgruppe"; break;
			case "p20n2": r = "Name der Gruppe"; break;
			case "p20n3": r = "relevanter Produktfluss"; break;
			case "p20n4": r = "Name des hinzuzufügenden Moduls"; break;
			case "bt16": r = "neue Gruppe anlegen"; break;
			case "bt17": r = "Modul zur Gruppe hinzufügen"; break;
			case "stat37": r = ">>> Der angegebene Fluss existiert nicht. <<<"; break;
			case "stat38": r = ">>> Das angegebene Prozessmodul existiert nicht. <<<"; break;
			case "stat39": r = ">>> Das angegebene Prozessmodul ist inkompatibel. <<<"; break;
			case "stat40": r = ">>> Die Gruppe umfasst "; break;
			case "stat41": r = " Prozessmodule. <<<"; break;

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
			case "mp1": r = "New/Edit"; break;
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
			case "p02n5": r = "Lower Bound"; break;
			case "p02n6": r = "Upper Bound"; break;
			case "bt10": r = "Confirm Bounds"; break;
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
			case "mp15": r = "Characterization Factor"; break;
			case "mp15e": r = "Create new Characterization Factor"; break;
			case "p12n1": r = "New Characterization Factor"; break;
			case "p12n2": r = "Name of the Characterization Factor"; break;
			case "p12n3": r = "Factor"; break;
			case "stat26": r = ">>> Illegal Factor <<<"; break;
			case "stat27": r = ">>> The entered Impact Category does not exist. <<<"; break;
			case "mp45": r = "Characterization Factors"; break;
			case "mp45e": r = "List of all Characterization Factors"; break;
			case "p13n1": r = "Category"; break;
			case "mp16": r = "LCIA Method"; break;
			case "mp16e": r = "Create new LCIA Method"; break;
			case "mp46": r = "LCIA Methods"; break;
			case "mp46e": r = "List of LCIA Methods"; break;
			case "mp52": r = "LCIA Evaluation"; break;
			case "mp52e": r = "LCIA for single Objects"; break;
			case "p14n1": r = "New LCIA Method"; break;
			case "p14n2": r = "Name of the LCIA Method"; break;
			case "bt11": r = "Add"; break;
			case "stat28": r = ">>> Categories: "; break;
			case "stat29": r = " <<< >>> Characterization Factors: "; break;
			case "stat30": r = ">>> The entered Characterization Factor does not exist. <<<"; break;
			case "p16n1": r = "LCIA Evaluation"; break;
			case "p16n2": r = "Object Type"; break;
			case "bt12": r = "Display Results"; break;
			case "cob01": r = "elementary Flow"; break;
			case "cob02": r = "Product"; break;
			case "cob03": r = "Main Value"; break;
			case "p16n3": r = "Object Name"; break;
			case "p16n4": r = "Type of Value"; break;
			case "p16n5": r = "Value"; break;
			case "stat31": r = ">>> The Module is already present in the Product System. <<<"; break;
			case "mp17": r = "Product Declaration"; break;
			case "mp18": r = "Component"; break;
			case "mp19": r = "Composition"; break;
			case "p17n1": r = "New Product Declaration"; break;
			case "p18n1": r = "New Component"; break;
			case "p19n1": r = "New Composition"; break;
			case "mp17e": r = "Create new Product Declaration"; break;
			case "mp18e": r = "Create new Component"; break;
			case "mp19e": r = "Create new Composition"; break;
			case "mp47": r = "Product Declarations"; break;
			case "mp48": r = "Components"; break;
			case "mp49": r = "Compositions"; break;
			case "mp47e": r = "List of Product Declarations"; break;
			case "mp48e": r = "List of Components"; break;
			case "mp49e": r = "List of Compositions"; break;
			case "p17n2": r = "Name of the Product to declare"; break;
			case "stat32": r = ">>> No LCIA Method was entered. <<<"; break;
			case "stat33": r = ">>> LCIA Method does not exist. <<<"; break;
			case "stat34": r = ">>> Number of Product Declarations: "; break;
			case "stat35": r = ">>> Incorrect Impact Category <<<"; break;
			case "p18n2": r = "Name of the Component"; break;
			case "p18n3": r = "Reference"; break;
			case "bt13": r = "Create new Component"; break;
			case "p19n2": r = "Name of the Composition"; break;
			case "p19n3": r = "Component to add"; break;
			case "bt14": r = "Create new Composition"; break;
			case "bt15": r = "Add Component"; break;
			case "stat36": r = ">>> The name is in use otherwise. <<<"; break;
			case "mp121": r = "Single Module"; break;
			case "mp122": r = "Group of Process Modules"; break;
			case "mp121e": r = "Create single Process Module"; break;
			case "mp122e": r = "Create Group of Process Modules"; break;
			case "p20n1": r = "New Process Module Group"; break;
			case "p20n2": r = "Name of the Group"; break;
			case "p20n3": r = "Relevant Product Flow"; break;
			case "p20n4": r = "Name of the Module to add"; break;
			case "bt16": r = "Create new Group"; break;
			case "bt17": r = "Add Module to Group"; break;
			case "stat37": r = ">>> The Flow does not exist. <<<"; break;
			case "stat38": r = ">>> The Process Module does not exist. <<<"; break;
			case "stat39": r = ">>> The Process Module is not compatible. <<<"; break;
			case "stat40": r = ">>> The Group contains "; break;
			case "stat41": r = " Process Modules. <<<"; break;
		
			}		
		}
		return r;
	}
}
