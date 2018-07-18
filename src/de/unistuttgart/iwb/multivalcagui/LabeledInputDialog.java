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
 * @version 0.545
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
