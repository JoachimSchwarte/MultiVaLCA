package de.unistuttgart.iwb.multivalcagui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.unistuttgart.iwb.multivalca.CategoryIndicator;
import de.unistuttgart.iwb.multivalca.ImpactCategory;
import net.miginfocom.swing.MigLayout;

public class ImpactCatPanel extends MCAPanel {
	
	private JTextField txtP10n1 = new JTextField();		// Eingabefeld Kategorie
	private JTextField txtP10n2 = new JTextField();		// Eingabefeld Indikator
	private JLabel lblP10n1 = new JLabel(); 			// "Neue Wirkungskategorie"
	private JLabel lblP10n2 = new JLabel();				// "Name der Wirkungskategorie"
	private JLabel lblP10n3 = new JLabel();				// "Indikator"
	private JLabel lblP10n4 = new JLabel();				// Status
	private JButton btnP10n1 = new JButton(); 			// "speichern"

	public ImpactCatPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		setLayout(new MigLayout("", "[108px,grow][108px][108px][108px,grow]", 
				"[20px][20px][20px][20px][20px][20px][20px][20px][20px][20px][20px][20px,grow]"));		
		lblP10n1.setFont(new Font("Tahoma", Font.BOLD, 14));
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
				Language l = GUILanguage.getChosenLanguage();
				String name = txtP10n1.getText();	
				String wi = txtP10n2.getText();
				boolean inputOK = true;
				if (wi.equals("")) {
					lblP10n4.setText(GuiStrings.getGS("stat24",l));
					inputOK = false;
				} 
				if (name.equals("")) {
					lblP10n4.setText(GuiStrings.getGS("stat22",l));
					inputOK = false;
				} 
				if (ImpactCategory.containsName(name)) {
					lblP10n4.setText(GuiStrings.getGS("stat23",l));
					inputOK = false;
				}
				if (inputOK == true) {
					CategoryIndicator indi = CategoryIndicator.instance(wi);
					ImpactCategory.instance(name, indi);
					lblP10n4.setText(GuiStrings.getGS("stat25",l) + ImpactCategory.getAllInstances().size() + GuiStrings.getGS("stat05",l));
					txtP10n1.setText("");
					txtP10n2.setText("");
				}		
			}
		});
		
	}

	@Override
	public void showSelf() {
		Language l = GUILanguage.getChosenLanguage();
		lblP10n1.setText(GuiStrings.getGS("p10n1", l));
		lblP10n2.setText(GuiStrings.getGS("p10n2", l));
		lblP10n3.setText(GuiStrings.getGS("p10n3", l));
		btnP10n1.setText(GuiStrings.getGS("bt01", l));	
		lblP10n4.setText(GuiStrings.getGS("stat01", l));
		
		
	}
	

}
