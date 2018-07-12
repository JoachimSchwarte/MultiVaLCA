package de.unistuttgart.iwb.multivalcagui;

import java.awt.Font;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class CompositionPanel extends MCAPanel{

	private JLabel lblP19n1 = new JLabel();
	private JLabel lblP19n2 = new JLabel();				// "Name der Komposition"
	private JLabel lblP19n3 = new JLabel();				// "hinzuzufügende Komponente"
	private JLabel lblP19n4 = new JLabel();				// Status
	private JTextField txtP19n1 = new JTextField();		// Eingabefeld Name
	private JTextField txtP19n2 = new JTextField();		// Eingabefeld Komponente
	private JButton btnP19n1 = new JButton(); 			// "neue Komposition anlegen"
	private JButton btnP19n2 = new JButton(); 			// "Grenzwerte bestätigen"
	private JButton btnP19n3 = new JButton(); 			// "fertig"
	
	public CompositionPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		setLayout(new MigLayout("", "[108px,grow][108px][108px][108px,grow]", 
				"[20px][20px][20px][20px][20px][20px][20px,grow]"));	
		lblP19n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblP19n1, "cell 1 0 2 1,alignx center,aligny top");
		add(lblP19n2, "cell 1 1,grow");		
		txtP19n1.setText("");
		add(txtP19n1, "cell 2 1,grow");
		txtP19n1.setColumns(10);
		btnP19n1.setEnabled(true);
		add(btnP19n1, "cell 1 2 2 1,alignx center");
		add(lblP19n3, "cell 1 3,grow");		
		txtP19n2.setText("");
		add(txtP19n2, "cell 2 3,grow");
		txtP19n2.setColumns(10);
		txtP19n2.setEnabled(false);
		btnP19n2.setEnabled(false);
		add(btnP19n2, "cell 1 4,alignx center");
		btnP19n3.setEnabled(false);
		add(btnP19n3, "cell 2 4,alignx center");
		add(lblP19n4, "cell 0 5 4 1,alignx center");
		
	}

	@Override
	public void showSelf() {
		Language l = GUILanguage.getChosenLanguage();
		Locale locale = MultiVaLCA.LANGUAGES.get(l);
		String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
		lblP19n1.setText(bundle.getString("p19n1"));
		lblP19n2.setText(bundle.getString("p19n2"));
		lblP19n3.setText(bundle.getString("p19n3"));
		lblP19n4.setText(bundle.getString("stat01"));
		btnP19n1.setText(bundle.getString("bt14"));
		btnP19n2.setText(bundle.getString("bt15"));
		btnP19n3.setText(bundle.getString("bt04"));
		
	}

}
