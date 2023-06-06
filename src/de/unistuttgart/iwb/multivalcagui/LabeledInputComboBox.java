/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import javax.swing.JComboBox;
import javax.swing.JLabel;

/**
 * @author Dr.-Ing. Joachim Schwarte, Helen Hein, Johannes Dippon
 * @version 0.805
 */

public class LabeledInputComboBox<Type> {
	
	private JLabel label = new JLabel();				    // Bezeichner
	private JComboBox<Type> combo = new JComboBox<Type>();	// Kombobox
	
	public LabeledInputComboBox (JLabel label, JComboBox<Type> combo) {
		super();
		this.label = label;
		this.combo = combo;
	}
	
	/**
	 * Anzeige der Dialogelemente
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
		panel.add(combo, "cell 2 "+pos.toString()+",grow");
		return pos;
	}
	
	
}
