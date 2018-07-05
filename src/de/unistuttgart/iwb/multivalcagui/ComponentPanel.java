package de.unistuttgart.iwb.multivalcagui;

import java.awt.Font;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class ComponentPanel extends MCAPanel{
	
	private JLabel lblP18n1 = new JLabel();				// "neue Komponente"
	private JLabel lblP18n2 = new JLabel();				// "Name der Komponente"
	private JLabel lblP18n3 = new JLabel();				// "Bezug"
	private JLabel lblP18n4 = new JLabel();				// "Menge"
	private JLabel lblP18n5 = new JLabel();				// "Untergrenze"
	private JLabel lblP18n6 = new JLabel();				// "Obergrenze"
	private JLabel lblP18n7 = new JLabel();				// Status
	private JTextField txtP18n1 = new JTextField();		// Eingabefeld Name
	private JTextField txtP18n2 = new JTextField();		// Eingabefeld Bezug
	private JTextField txtP18n3 = new JTextField();		// Eingabefeld Menge
	private JTextField txtP18n4 = new JTextField();		// Eingabefeld Untergrenze
	private JTextField txtP18n5 = new JTextField();		// Eingabefeld Obergrenze
	private JButton btnP18n1 = new JButton(); 			// "neue Komponente anlegen"
	private JButton btnP18n2 = new JButton(); 			// "fertig"

	protected ComponentPanel(String key) {
		super(key);
		setLayout(new MigLayout("", "[108px,grow][108px][108px][108px,grow]", 
				"[20px][20px][20px][20px][20px][20px][20px][20px][20px][20px,grow]"));	
		lblP18n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblP18n1, "cell 1 0 2 1,alignx center,aligny top");
		add(lblP18n2, "cell 1 1,grow");		
		txtP18n1.setText("");
		add(txtP18n1, "cell 2 1,grow");
		txtP18n1.setColumns(10);
		add(lblP18n3, "cell 1 2,grow");		
		txtP18n2.setText("");
		add(txtP18n2, "cell 2 2,grow");
		txtP18n2.setColumns(10);
		add(lblP18n4, "cell 1 3,grow");		
		txtP18n3.setText("");
		add(txtP18n3, "cell 2 3,grow");
		txtP18n3.setColumns(10);
		btnP18n1.setEnabled(true);
		add(btnP18n1, "cell 1 4 2 1,alignx center");
		add(lblP18n5, "cell 1 5,grow");		
		txtP18n4.setText("");
		txtP18n4.setEnabled(false);
		add(txtP18n4, "cell 2 5,grow");
		txtP18n4.setColumns(10);
		add(lblP18n6, "cell 1 6,grow");		
		txtP18n5.setText("");
		add(txtP18n5, "cell 2 6,grow");
		txtP18n5.setColumns(10);
		txtP18n5.setEnabled(false);
		btnP18n2.setEnabled(false);
		add(btnP18n2, "cell 1 7 2 1,alignx center");
		add(lblP18n7, "cell 0 8 4 1,alignx center");
		
		
	}

	@Override
	public void showSelf() {
		Language l = GUILanguage.getChosenLanguage();
		Locale locale = MultiVaLCA.LANGUAGES.get(l);
		String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
		lblP18n1.setText(bundle.getString("p18n1"));
		lblP18n2.setText(bundle.getString("p18n2"));
		lblP18n3.setText(bundle.getString("p18n3"));
		lblP18n4.setText(bundle.getString("p02n4"));
		lblP18n5.setText(bundle.getString("p02n5"));
		lblP18n6.setText(bundle.getString("p02n6"));
		lblP18n7.setText(bundle.getString("stat01"));
		btnP18n1.setText(bundle.getString("bt13"));
		btnP18n2.setText(bundle.getString("bt04"));
		
	}

}