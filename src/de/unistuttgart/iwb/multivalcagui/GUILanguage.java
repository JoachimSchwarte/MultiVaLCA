/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.514
 */

public class GUILanguage {
	
	private static Language chosenLanguage = Language.Deutsch;

	public static Language getChosenLanguage() {
		return chosenLanguage;
	}

	public static void setChosenLanguage(Language someLanguage) {
		chosenLanguage = someLanguage;
	}
}
