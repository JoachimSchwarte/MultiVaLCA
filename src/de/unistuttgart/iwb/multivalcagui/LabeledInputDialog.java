/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import javax.swing.JLabel;
import javax.swing.JTextField;

import de.unistuttgart.iwb.multivalca.MCAObject;

import java.util.LinkedHashMap;
import java.util.Set;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.552
 */

public class LabeledInputDialog {
	
	private JLabel label = new JLabel();				// Bezeichner
	private JTextField input = new JTextField();		// Eingabefeld 
	
	public LabeledInputDialog(JLabel label, JTextField input) {
		super();
		this.label = label;
		this.input = input;
	}
	
	/**
	 * Anzeige der Dialogelemenet
	 * @param pos0
	 * Letzte Ausgabezeile
	 * @param panel
	 * Panel, in dem die Ausgabe erfolgt
	 * @return
	 * Aktuelle Ausgabezeile
	 */
	
	public Integer draw(Integer pos0, MCAPanel panel) {
		Integer pos = pos0;
		panel.add(label, "cell 1 "+(++pos).toString()+",grow");		
		input.setText("");		
		panel.add(input, "cell 2 "+pos.toString()+",grow");
		input.setColumns(10);
		return pos;
	}
	
	/**
	 * Abrage eines Textelementes, von dem erwartet wird, dass es bereits
	 * im System bekannt ist
	 * @param message
	 * (spezifische) Fehlermeldung "Name existiert nicht in ..." o. ä.
	 * @param nameList
	 * Liste der Textelemente, in der das neue Element vorhanden sein soll
	 * @param status
	 * Bezeichner der Statuszeile
	 * @param emptyString
	 * Fehlermeldung "Es wurde kein Text eingeben" o. ä.
	 * @return
	 * das ausgelesene Textelement
	 */
	
	public String getTextOld(String message, Set<String> nameList, JLabel status, String emptyString) {
		String r = input.getText();
		if ("".equals(r)) {
			status.setText(emptyString);
		}
		else {
			if (!nameList.contains(r)) {
				status.setText(message);
				r="";				
			}			
		}		
		return r;
	}
	
	/**
	 * Abrage eines Textelementes, von dem erwartet wird, dass es noch nicht
	 * im System verwendet wird
	 * @param nameLists
	 * Tabelle mit Textelementlisten, in denen das neue Element nicht enthalte
	 * sein darf, und zugehörigen Fehlermeldungen
	 * @param status
	 * Bezeichner der Statuszeile
	 * @param emptyString
	 * Fehlermeldung "Es wurde kein Text eingeben" o. ä.
	 * @param existsString
	 * Fehlermeldung "Textelement ist anderweitig vergeben" o. ä.
	 * @return
	 * das ausgelesene Textelement
	 */
	
	public String getTextNew(LinkedHashMap<String, Set<String>> nameLists, JLabel status, String emptyString, String existsString) {
		String r = input.getText();
		if ("".equals(r)) {
			status.setText(emptyString);
		}
		for (String message : nameLists.keySet()) {
			if (nameLists.get(message).contains(r) && !"".equals(r)) {
				status.setText(message);
				r="";
			}
		}
		if (MCAObject.containsName(r)) {
			status.setText(existsString);
			r="";
		}
		return r;
	}
	
	/**
	 * Abfrage eines Zahlenwertes
	 * @param status
	 * Bezeichner der Statuszeile
	 * @param emptyString
	 * Fehlermeldung "Es wurde nichts eingeben" o. ä.
	 * @param wrongString
	 * Fehlermeldung "Es wurde keine zulässige Zahl eingeben" o. ä.
	 * @return
	 * den ausgelesenen Zahlenwert
	 */
	
	public Double getNum(JLabel status, String emptyString, String wrongString) {
		Double r = null;
		String inputString = input.getText();
		if ("".equals(inputString)) {
			status.setText(emptyString); 				
		}
		else {				
			try {
				r = Double.parseDouble(inputString);
			} catch (NumberFormatException e){
				r = 0.0;
			}
			if (r <= 0.0) {
				status.setText(wrongString); // >>> unzulässiger Faktor <<<
			}
		}
		return r;
	}
	
	public boolean isPosNum(JLabel status, String emptyString, String wrongString) {
		boolean r = false;
		if (getNum(status, emptyString, wrongString) != null && getNum(status, emptyString, wrongString)>0.0) {
			r = true;
		}
		return r;
	}	
}
