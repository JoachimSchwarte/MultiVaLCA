/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import de.unistuttgart.iwb.multivalca.CategoryIndicator;
import de.unistuttgart.iwb.multivalca.ImpactCategory;
import net.miginfocom.swing.MigLayout;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.536
 */

public class ImpactCatPanel extends MCAPanel {
	
	private JTextField txtP10n1 = new JTextField();		// Eingabefeld Kategorie
	private JTextField txtP10n2 = new JTextField();		// Eingabefeld Indikator
	private JLabel lblP10n1 = new JLabel(); 			// "Neue Wirkungskategorie"
	private JLabel lblP10n2 = new JLabel();				// "Name der Wirkungskategorie"
	private JLabel lblP10n3 = new JLabel();				// "Indikator"
	private JLabel lblP10n4 = new JLabel();				// Status
	private JButton btnP10n1 = new JButton(); 			// "speichern"
	private Language l = GUILanguage.getChosenLanguage();
	private Locale locale = MultiVaLCA.LANGUAGES.get(l);
	private String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
	private ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int height = (int) screen.getHeight();
	private Font titlefont = new Font ("Tahoma", Font.BOLD, height *22/1000);
	private Font generalfont = new Font ("Tahoma", Font.LAYOUT_LEFT_TO_RIGHT, height *15/1000);

	public ImpactCatPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		setLayout(new MigLayout("", "[grow][20%][20%][grow]", 
				"[8%]2%[4%]2%[4%]8%[4%]2%[4%][grow]"));		
		lblP10n1.setFont(titlefont);
		add(lblP10n1, "flowy,cell 1 0 2 1,alignx center,growy");	
		add(lblP10n2, "cell 1 1,grow");	
		txtP10n1.setText("");
		add(txtP10n1, "cell 2 1,grow");	
		txtP10n1.setColumns(10);
		add(lblP10n3, "cell 1 2,grow");	
		txtP10n2.setText("");
		add(txtP10n2, "cell 2 2,grow");	
		txtP10n2.setColumns(10);
		add(btnP10n1, "cell 1 3 2 1,alignx center");	
		btnP10n1.setEnabled(true);
		add(lblP10n4, "cell 0 4 4 1,alignx center");	
		
		btnP10n1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name = txtP10n1.getText();	
				String wi = txtP10n2.getText();
				boolean inputOK = true;
				if ("".equals(wi)) {
					lblP10n4.setText(bundle.getString("stat24"));
					inputOK = false;
				} 
				if ("".equals(name)) {
					lblP10n4.setText(bundle.getString("stat22"));
					inputOK = false;
				} 
				if (ImpactCategory.containsName(name)) {
					lblP10n4.setText(bundle.getString("stat23"));
					inputOK = false;
				}
				if (inputOK) {
					CategoryIndicator indi = CategoryIndicator.instance(wi);
					ImpactCategory.instance(name, indi);
					lblP10n4.setText(bundle.getString("stat25") + ImpactCategory.getAllInstances().size() + bundle.getString("stat05"));
					txtP10n1.setText("");
					txtP10n2.setText("");
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
		txtP10n1.setFont(generalfont);
		txtP10n2.setFont(generalfont);
		lblP10n1.setFont(titlefont);
		lblP10n2.setFont(generalfont);
		lblP10n3.setFont(generalfont);
		lblP10n4.setFont(generalfont);
		btnP10n1.setFont(generalfont);
		lblP10n1.setText(bundle.getString("p10n1"));
		lblP10n2.setText(bundle.getString("p10n2"));
		lblP10n3.setText(bundle.getString("p10n3"));
		btnP10n1.setText(bundle.getString("bt01"));	
		lblP10n4.setText(bundle.getString("stat01"));
	}
}
