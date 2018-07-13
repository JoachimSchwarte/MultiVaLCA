/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import de.unistuttgart.iwb.multivalca.CharacFactor;
import de.unistuttgart.iwb.multivalca.Flow;
import de.unistuttgart.iwb.multivalca.ImpactCategory;
import de.unistuttgart.iwb.multivalca.ValueType;
import net.miginfocom.swing.MigLayout;

/**
 * @author HH, JS
 * @version 0.542
 */

public class CharacFactorPanel extends MCAPanel{
	
	private JTextField txtP12n1 = new JTextField();		// Eingabefeld CF-Name
	private JTextField txtP12n2 = new JTextField();		// Eingabefeld Fluss-Name
	private JTextField txtP12n3 = new JTextField();		// Eingabefeld Wirkungskategorie
	private JTextField txtP12n4 = new JTextField();		// Eingabefeld Faktor
	private JTextField txtP12n5 = new JTextField();		// Eingabefeld Untergrenze
	private JTextField txtP12n6 = new JTextField();		// Eingabefeld Obergrenze
	private JLabel lblP12n1 = new JLabel(); 			// "Neuer Charakterisierungsfaktor"
	private JLabel lblP12n2 = new JLabel(); 			// "Name des Charakterisierungsfaktors"
	private JLabel lblP12n3 = new JLabel(); 			// "Name des Flusses"
	private JLabel lblP12n4 = new JLabel(); 			// "Name der Wirkungskategorie"
	private JLabel lblP12n5 = new JLabel(); 			// "Faktor"
	private JLabel lblP12n6 = new JLabel(); 			// "Untergrenze"
	private JLabel lblP12n7 = new JLabel(); 			// "Obergrenze"
	private JLabel lblP12n8 = new JLabel(); 			// Status
	private JButton btnP12n1 = new JButton(); 			// "speichern"
	private JButton btnP12n2 = new JButton(); 			// "Grenzwerte bestätigen"
	private Language l = GUILanguage.getChosenLanguage();
	private Locale locale = MultiVaLCA.LANGUAGES.get(l);
	private String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
	private ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
	private LowerUpperDialog lud = new LowerUpperDialog(lblP12n6, lblP12n7, txtP12n5, txtP12n6);
	
	protected CharacFactorPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {		
		setLayout(new MigLayout("", "[108px,grow][108px][108px][108px,grow]", 
				"[20px][20px][20px][20px][20px][20px][20px][20px][20px][20px][20px][20px][20px,grow]"));		
		lblP12n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		Integer pos=0;
		add(lblP12n1, "cell 1 "+pos.toString()+" 2 1,alignx center,aligny top");	
		add(lblP12n2, "cell 1 "+(++pos).toString()+",grow");		
		txtP12n1.setText("");
		add(txtP12n1, "cell 2 "+pos.toString()+",grow");
		txtP12n1.setColumns(10);					
		add(lblP12n3, "cell 1 "+(++pos).toString()+",grow");	
		txtP12n2.setText("");
		add(txtP12n2, "cell 2 "+pos.toString()+",grow");
		txtP12n2.setColumns(10);		
		add(lblP12n4, "cell 1 "+(++pos).toString()+",grow");
		txtP12n3.setText("");
		add(txtP12n3, "cell 2 "+pos.toString()+",grow");
		txtP12n3.setColumns(10);		
		add(lblP12n5, "cell 1 "+(++pos).toString()+",grow");
		txtP12n4.setText("");
		add(txtP12n4, "cell 2 "+pos.toString()+",grow");
		txtP12n4.setColumns(10);	
		add(btnP12n1, "cell 1 "+(++pos).toString()+" 2 1,alignx center");		
		pos = lud.draw(pos, this);	
		btnP12n2.setEnabled(false);
		add(btnP12n2, "cell 1 "+(++pos).toString()+" 2 1,alignx center");
		add(lblP12n8, "cell 0 "+(++pos).toString()+" 4 1,alignx center");	
		
		button1();		
		button2();	
	}

	private void button1() {
		btnP12n1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String nameCF = txtP12n1.getText();
				String nameFl = txtP12n2.getText();
				String nameWK = txtP12n3.getText();
				String facText = txtP12n4.getText();
				boolean inputOK = true;
				if ("".equals(nameCF) || "".equals(nameFl) || "".equals(nameWK) || "".equals(facText)) {
					lblP12n8.setText(bundle.getString("stat07"));
					inputOK = false;					
				}
				Double facVal = 0.0;
				try {
					facVal = Double.parseDouble(facText);
				} catch (NumberFormatException e){
					facVal = 0.0;
				}
				if (facVal <= 0.0) {
					lblP12n8.setText(bundle.getString("stat26"));
					inputOK = false;	
				}

				if (!ImpactCategory.containsName(nameWK)) {
					lblP12n8.setText(bundle.getString("stat27"));
					inputOK = false;
				}

				if (!Flow.containsName(nameFl)) {
					lblP12n8.setText(bundle.getString("stat11"));

					inputOK = false;
				}
				if (CharacFactor.containsName(nameCF)) {
					lblP12n8.setText(bundle.getString("stat03"));
					inputOK = false;
				}
				if (inputOK) {
					CharacFactor.instance(nameCF, Flow.getInstance(nameFl), ImpactCategory.getInstance(nameWK), facVal);
					txtP12n1.setEnabled(false);
					txtP12n2.setEnabled(false);
					txtP12n3.setEnabled(false);
					txtP12n4.setEnabled(false);
					lud.start(facText);
					btnP12n1.setEnabled(false);
					btnP12n2.setEnabled(true);
				}				
			}			
		});
	}

	private void button2() {
		btnP12n2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				LinkedHashMap<ValueType, Double> mengen = lud.getValues(txtP12n4, lblP12n8);
				if (mengen.size() == 3) {
					String nameCF = txtP12n1.getText();
					CharacFactor.setLowerBound(nameCF, mengen.get(ValueType.LowerBound));
					CharacFactor.setUpperBound(nameCF, mengen.get(ValueType.UpperBound));
					txtP12n1.setText("");
					txtP12n2.setText("");
					txtP12n3.setText("");
					txtP12n4.setText("");
					txtP12n1.setEnabled(true);
					txtP12n2.setEnabled(true);
					txtP12n3.setEnabled(true);
					txtP12n4.setEnabled(true);
					btnP12n1.setEnabled(true);
					btnP12n2.setEnabled(false);	
					lud.stop();
					lblP12n8.setText(bundle.getString("stat01"));
				}		
			}		
		});				
	}

	@Override
	public void showSelf() {
		l = GUILanguage.getChosenLanguage();
		locale = MultiVaLCA.LANGUAGES.get(l);
		baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		bundle = ResourceBundle.getBundle(baseName, locale);
		
		lblP12n1.setText(bundle.getString("p12n1"));
		lblP12n2.setText(bundle.getString("p12n2"));
		lblP12n3.setText(bundle.getString("p01n2"));
		lblP12n4.setText(bundle.getString("p10n2"));
		lblP12n5.setText(bundle.getString("p12n3"));
		lblP12n6.setText(bundle.getString("p02n5"));
		lblP12n7.setText(bundle.getString("p02n6"));
		lblP12n8.setText(bundle.getString("stat01"));
		btnP12n1.setText(bundle.getString("bt01"));
		btnP12n2.setText(bundle.getString("bt10"));
		
	}

}
