package de.unistuttgart.iwb.multivalcagui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import de.unistuttgart.iwb.multivalca.FlowUnit;
import de.unistuttgart.iwb.multivalca.ImpactCategory;
import de.unistuttgart.iwb.multivalca.LCIAMethod;
import de.unistuttgart.iwb.multivalca.NameCheck;
import de.unistuttgart.iwb.multivalca.ProductDeclaration;
import de.unistuttgart.iwb.multivalca.ValueType;
import net.miginfocom.swing.MigLayout;

public class DeklarationPanel extends MCAPanel {
	Language l = GUILanguage.getChosenLanguage();
	private JLabel lblP17n1 = new JLabel();				// "neue Produktdeklaration"
	private JLabel lblP17n2 = new JLabel();				// "Name des ..."
	private JLabel lblP17n3 = new JLabel();				// "Einheit"
	private JLabel lblP17n4 = new JLabel();				// "Bewertungsmethode"
	private JLabel lblP17n5 = new JLabel();				// "Wirkungskategorie"
	private JLabel lblP17n6 = new JLabel();				// "Menge"
	private JLabel lblP17n7 = new JLabel();				// "Untergrenze"
	private JLabel lblP17n8 = new JLabel();				// "Obergrenze"
	private JLabel lblP17n9 = new JLabel();				// Status
	private JButton btnP17n1 = new JButton(); 			// "neue Produktdeklaration anlegen"
	private JButton btnP17n2 = new JButton(); 			// "Wirkungsabschätzung hinzufügen"
	private JButton btnP17n3 = new JButton(); 			// "Grenzwerte bestätigen"
	private JButton btnP17n4 = new JButton(); 			// "fertig"
	private JTextField txtP17n1 = new JTextField();		// Eingabefeld Produkt-Name
	private JTextField txtP17n2 = new JTextField();		// Eingabefeld LCIA-Method
	private JTextField txtP17n3 = new JTextField();		// Eingabefeld Wirkungskategorie
	private JTextField txtP17n4 = new JTextField();		// Eingabefeld LCIA-Name
	private JTextField txtP17n5 = new JTextField();		// Eingabefeld Untergrenze
	private JTextField txtP17n6 = new JTextField();		// Eingabefeld Obergrenze

	protected DeklarationPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		setLayout(new MigLayout("", "[108px,grow][108px][108px][108px,grow]", 
				"[20px][20px][20px][20px][20px][20px][20px][20px][20px][20px][20px][20px][20px,grow]"));
		lblP17n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblP17n1, "flowy,cell 1 0 2 1,alignx center,growy");			
		add(lblP17n2, "cell 1 1,grow");		
		txtP17n1.setText("");
		add(txtP17n1, "cell 2 1,grow");
		txtP17n1.setColumns(10);
		add(lblP17n3, "cell 1 2,grow");	
		JComboBox<FlowUnit> comboBox_2 = new JComboBox<FlowUnit>();
		comboBox_2.setModel(new DefaultComboBoxModel<FlowUnit>(FlowUnit.values()));
		add(comboBox_2, "cell 2 2,grow");	
		add(lblP17n4, "cell 1 3,grow");		
		txtP17n2.setText("");
		add(txtP17n2, "cell 2 3,grow");
		txtP17n2.setColumns(10);		
		add(btnP17n1, "cell 1 4 2 1,alignx center");		
		add(lblP17n5, "cell 1 5,grow");	
		txtP17n3.setText("");
		add(txtP17n3, "cell 2 5,grow");
		txtP17n3.setColumns(10);
		txtP17n3.setEnabled(false);	
		add(lblP17n6, "cell 1 6,grow");
		txtP17n4.setText("");
		add(txtP17n4, "cell 2 6,grow");
		txtP17n4.setColumns(10);
		txtP17n4.setEnabled(false);				
		btnP17n2.setEnabled(false);
		add(btnP17n2, "cell 1 7 2 1,alignx center");		
		add(lblP17n7, "cell 1 8,grow");	
		txtP17n5.setText("");
		add(txtP17n5, "cell 2 8,grow");
		txtP17n5.setColumns(10);
		txtP17n5.setEnabled(false);	
		add(lblP17n8, "cell 1 9,grow");
		txtP17n6.setText("");
		add(txtP17n6, "cell 2 9,grow");
		txtP17n6.setColumns(10);
		txtP17n6.setEnabled(false);		
		btnP17n3.setEnabled(false);
		add(btnP17n3, "cell 1 10,alignx center");
		btnP17n4.setEnabled(false);
		add(btnP17n4, "cell 2 10,alignx center");
		add(lblP17n9, "cell 0 11 4 1,alignx center");	
		
		btnP17n1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String nameProd = txtP17n1.getText();
				String nameLCIA = txtP17n2.getText();
				FlowUnit einheit = comboBox_2.getItemAt(comboBox_2.getSelectedIndex());
				boolean inputOK = true;
				if (ProductDeclaration.containsName(nameProd) == true) {
					lblP17n9.setText(GuiStrings.getGS("stat03",l));
					inputOK = false;					
				}
				if (NameCheck.contains(nameProd) == true  && inputOK == true) {
					lblP17n9.setText(GuiStrings.getGS("stat36",l));
					inputOK = false;					
				}
				if (LCIAMethod.containsName(nameLCIA) == false && inputOK == true) {
					lblP17n9.setText(GuiStrings.getGS("stat33",l));
					inputOK = false;					
				}
				if (nameProd.equals("") && inputOK == true) {
					lblP17n9.setText(GuiStrings.getGS("stat02",l));
					inputOK = false;
				}
				if (nameLCIA.equals("") && inputOK == true) {
					lblP17n9.setText(GuiStrings.getGS("stat32",l));
					inputOK = false;
				}
				if (inputOK == true) {
					LCIAMethod bm = LCIAMethod.instance(nameLCIA);
					ProductDeclaration.instance(nameProd, einheit).setBM(bm);
					btnP17n1.setEnabled(false);	
					txtP17n1.setEnabled(false);
					comboBox_2.setEnabled(false);
					txtP17n2.setEnabled(false);
					btnP17n2.setEnabled(true);
					txtP17n3.setEnabled(true);
					txtP17n4.setEnabled(true);
					lblP17n9.setText(GuiStrings.getGS("stat34",l) + 
							ProductDeclaration.getAllInstances().size() +
							GuiStrings.getGS("stat05",l));
				}			
			}		
		});
		
		btnP17n2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String nameProd = txtP17n1.getText();
				String nameLCIA = txtP17n2.getText();
				String fname = txtP17n3.getText();
				String fmenge = txtP17n4.getText();
				Double menge;
				try {
					menge = Double.parseDouble(fmenge);
				} catch (NumberFormatException e){
					menge = 0.0;
				}
				if (fname.equals("") || (menge == 0.0)) {
					lblP17n9.setText(GuiStrings.getGS("stat07", l));
				} else {
					if (LCIAMethod.instance(nameLCIA).categoryList().containsKey(fname)) {
						ImpactCategory ic = ImpactCategory.getInstance(fname);
						LinkedHashMap<ValueType, Double> values = new LinkedHashMap<ValueType, Double>();
						values.put(ValueType.MeanValue, menge);
						values.put(ValueType.LowerBound, menge);
						values.put(ValueType.UpperBound, menge);
						ProductDeclaration.getInstance(nameProd).addWirkung(ic, values);
						txtP17n3.setEnabled(false);
						txtP17n4.setEnabled(false);
						btnP17n2.setEnabled(false);
						txtP17n5.setText(txtP17n4.getText());
						txtP17n5.setEnabled(true);
						txtP17n6.setText(txtP17n4.getText());
						txtP17n6.setEnabled(true);
						btnP17n3.setEnabled(true);
						lblP17n9.setText(GuiStrings.getGS("stat01", l));
						
					} else {
						lblP17n9.setText(GuiStrings.getGS("stat35", l));
					}					
				}
			}
		});
		
		btnP17n3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String fug = txtP17n5.getText();
				String fog = txtP17n6.getText();
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
				String fmenge = txtP17n4.getText();
				Double menge;
				try {
					menge = Double.parseDouble(fmenge);
				} catch (NumberFormatException e){
					menge = 0.0;
				}
				if ((fugv > menge) || (fogv < menge)) {
					txtP17n5.setText(txtP17n4.getText());
					txtP17n6.setText(txtP17n4.getText());
					lblP17n9.setText(GuiStrings.getGS("stat21", l));
				} else {
					String nameProd = txtP17n1.getText();
					String fname = txtP17n3.getText();
					ImpactCategory ic = ImpactCategory.getInstance(fname);
					LinkedHashMap<ValueType, Double> values = new LinkedHashMap<ValueType, Double>();
					values.put(ValueType.MeanValue, menge);
					values.put(ValueType.LowerBound, fugv);
					values.put(ValueType.UpperBound, fogv);
					ProductDeclaration.getInstance(nameProd).addWirkung(ic, values);
					txtP17n3.setText("");
					txtP17n4.setText("");
					txtP17n5.setText("");
					txtP17n6.setText("");
					txtP17n3.setEnabled(true);
					txtP17n4.setEnabled(true);
					txtP17n5.setEnabled(false);
					txtP17n6.setEnabled(false);
					btnP17n2.setEnabled(true);				
					btnP17n3.setEnabled(false);
					btnP17n4.setEnabled(true);
					lblP17n9.setText(GuiStrings.getGS("stat01", l));				
				}
			}			
		});
		
		btnP17n4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnP17n1.setEnabled(true);
				txtP17n1.setText("");
				txtP17n2.setText("");
				txtP17n3.setText("");
				txtP17n4.setText("");
				txtP17n5.setText("");
				txtP17n5.setText("");
				btnP17n2.setEnabled(false);
				btnP17n4.setEnabled(false);
				btnP17n3.setEnabled(false);
				txtP17n1.setEnabled(true);
				comboBox_2.setEnabled(true);
				comboBox_2.setSelectedIndex(0);
				txtP17n2.setEnabled(true);
				txtP17n3.setEnabled(false);
				txtP17n4.setEnabled(false);
				txtP17n5.setEnabled(false);
				txtP17n6.setEnabled(false);
				lblP17n9.setText(GuiStrings.getGS("stat01", l));
			}
		});	
	}

	@Override
	public void showSelf() {
		Language l = GUILanguage.getChosenLanguage();
		lblP17n1.setText(GuiStrings.getGS("p17n1", l));
		lblP17n2.setText(GuiStrings.getGS("p17n2", l));
		lblP17n3.setText(GuiStrings.getGS("p01n4", l));
		lblP17n4.setText(GuiStrings.getGS("mp16", l));
		btnP17n1.setText(GuiStrings.getGS("mp17e", l));
		lblP17n5.setText(GuiStrings.getGS("mp14", l));
		lblP17n6.setText(GuiStrings.getGS("p02n4", l));
		btnP17n2.setText(GuiStrings.getGS("bt11", l));
		lblP17n7.setText(GuiStrings.getGS("p02n5", l));
		lblP17n8.setText(GuiStrings.getGS("p02n6", l));
		btnP17n3.setText(GuiStrings.getGS("bt10", l));
		btnP17n4.setText(GuiStrings.getGS("bt04", l));
		lblP17n9.setText(GuiStrings.getGS("stat01", l));	
	}
}
