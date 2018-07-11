package de.unistuttgart.iwb.multivalcagui;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class LowerUpperDialog {

	private JLabel lblLower = new JLabel();				// "Untergrenze"
	private JLabel lblUpper = new JLabel();				// "Obergrenze"
	private JTextField txtLower = new JTextField();		// Eingabefeld Untergrenze
	private JTextField txtUpper = new JTextField();		// Eingabefeld Obergrenze
	
	public LowerUpperDialog(JLabel lblLower, JLabel lblUpper, JTextField txtLower, JTextField txtUpper) {
		super();
		this.lblLower = lblLower;
		this.lblUpper = lblUpper;
		this.txtLower = txtLower;
		this.txtUpper = txtUpper;
	}
	
	public void drawLowUpDialog(Integer pos, ComponentPanel panel) {
		Integer pos1 = pos+1;
		panel.add(lblLower, "cell 1 "+pos.toString()+",grow");		
		txtLower.setText("");		
		panel.add(txtLower, "cell 2 "+pos.toString()+",grow");
		txtLower.setColumns(10);
		txtLower.setEnabled(false);
		panel.add(lblUpper, "cell 1 "+pos1.toString()+",grow");		
		txtUpper.setText("");
		panel.add(txtUpper, "cell 2 "+pos1.toString()+",grow");
		txtUpper.setColumns(10);
		txtUpper.setEnabled(false);
	}
}
