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
 * @version 0.543
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
	
	public String getTextOld(LinkedHashMap<String, Set<String>> nameLists, JLabel status) {
		String r = input.getText();
		for (String message : nameLists.keySet()) {
			if (!nameLists.get(message).contains(r)) {
				status.setText(message);
				r="";
			}
		}
		return r;
	}
	
	public String getTextNew(LinkedHashMap<String, Set<String>> nameLists, JLabel status, String stat07, String stat03) {
		String r = input.getText();
		if ("".equals(r)) {
			status.setText(stat07);
		}
		if (MCAObject.containsName(r)) {
			status.setText(stat03);
		}
		for (String message : nameLists.keySet()) {
			if (nameLists.get(message).contains(r) && !"".equals(r)) {
				status.setText(message);
				r="";
			}
		}
		return r;
	}
}
