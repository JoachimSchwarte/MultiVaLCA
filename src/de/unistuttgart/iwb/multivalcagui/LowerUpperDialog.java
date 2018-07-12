/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JTextField;

import de.unistuttgart.iwb.multivalca.ValueType;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.541
 */

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
	
	public Integer draw(Integer pos, ComponentPanel panel) {
		LabeledInputDialog lowerDi = new LabeledInputDialog(lblLower, txtLower);
		LabeledInputDialog upperDi = new LabeledInputDialog(lblUpper, txtUpper);
		pos = lowerDi.draw(pos, panel);
		pos = upperDi.draw(pos, panel);
		return pos;
	}
	
	public LinkedHashMap<ValueType, Double> getValues(JTextField mainValue, JLabel statusline) {
		Language l = GUILanguage.getChosenLanguage();
		Locale locale = MultiVaLCA.LANGUAGES.get(l);
		String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);	
		LinkedHashMap<ValueType, Double> values = new LinkedHashMap<ValueType, Double>();
		String fug = txtLower.getText();
		String fog = txtUpper.getText();
		Double fugv;
		Double fogv;
		try {
			fugv = Double.parseDouble(fug);
		} catch (NumberFormatException e){
			fugv = 0.0;
		}
		try {
			fogv = Double.parseDouble(fog);
		} catch (NumberFormatException e){
			fogv = 0.0;
		}
		String fmenge = mainValue.getText();
		Double menge;
		try {
			menge = Double.parseDouble(fmenge);
		} catch (NumberFormatException e){
			menge = 0.0;
		}
		if ((fugv > menge) || (fogv < menge)) {
			txtLower.setText(mainValue.getText());
			txtUpper.setText(mainValue.getText());
			statusline.setText(bundle.getString("stat21"));
		} else {	
			values.put(ValueType.MeanValue, menge);
			values.put(ValueType.LowerBound, fugv);
			values.put(ValueType.UpperBound, fogv);
		}
		return values;
	}
}
