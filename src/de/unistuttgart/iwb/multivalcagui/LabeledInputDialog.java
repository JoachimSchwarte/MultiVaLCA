/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.541
 */

public class LabeledInputDialog {
	
	private JLabel label = new JLabel();				// Bezeichner
	private JTextField input = new JTextField();		// Eingabefeld 
	
	public LabeledInputDialog(JLabel label, JTextField input) {
		super();
		this.label = label;
		this.input = input;
	}
	
	public Integer draw(Integer pos0, MCAPanel panel) {
		Integer pos = pos0;
		panel.add(label, "cell 1 "+(++pos).toString()+",grow");		
		input.setText("");		
		panel.add(input, "cell 2 "+pos.toString()+",grow");
		input.setColumns(10);
		return pos;
	}
}
