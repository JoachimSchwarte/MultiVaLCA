/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import de.unistuttgart.iwb.multivalca.Flow;
import de.unistuttgart.iwb.multivalca.FlowType;
import de.unistuttgart.iwb.multivalca.FlowUnit;
import de.unistuttgart.iwb.multivalca.MCAObject;
import net.miginfocom.swing.MigLayout;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.804
 */

public class FlowPanel extends MCAPanel{
	
	private JTextField txtP01n1 = new JTextField(); 	// Eingabefeld Flussname
	private JLabel lblP01n1 = new JLabel();				// "Neuer Fluss"
	private JLabel lblP01n2 = new JLabel();				// "Name des Flusses"
	private JLabel lblP01n3 = new JLabel();				// "Typ"
	private JLabel lblP01n4 = new JLabel();				// "Einheit"
	private JLabel lblP01n5 = new JLabel();				// Status
	private JButton btnP01n1 = new JButton();			// "speichern" 
	private JComboBox<String> cbbP01n1 = new JComboBox<String>();
	private JComboBox<FlowUnit> cbbP01n2 = new JComboBox<FlowUnit>();
	
	public FlowPanel(String key) {
		super(key);
		initUI();
	}
	
	private void initUI( ) {
		
		setLayout(new MigLayout("", "[grow][20%][20%][grow]", "      [8%]2%[4%]2%[4%]2%[4%]8%[4%]2%[4%]2%[4%][grow]"));	
		add(lblP01n1, "flowy, cell 1 0 2 1, alignx center,aligny center");						//Spalte 3, Zeile 1
		add(lblP01n2, "cell 1 1,grow");								//Spalte 2, Zeile 2
		//txtP01n1.setText("");
		add(txtP01n1, "cell 2 1,grow");								//Spalte 3, Zeile 3
		txtP01n1.setColumns(5);	
		add(lblP01n3, "cell 1 2,grow");	
		add(cbbP01n1, "cell 2 2,grow");	
		add(lblP01n4, "cell 1 3,grow");		
		cbbP01n2.setModel(new DefaultComboBoxModel<FlowUnit>(FlowUnit.values()));
		add(cbbP01n2, "cell 2 3,grow");			
		add(btnP01n1, "cell 1 4 2 1, center");			//Spalte 1, Zeile 4, Spalte 2, Zeile 1
		add(lblP01n5, "cell 1 5 2 1, center");	
		
		btnP01n1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name = txtP01n1.getText();
				LinkedHashMap<FlowType, String> ft = FlowTypeStringMap.getFTS(l);
				String typs = cbbP01n1.getItemAt(cbbP01n1.getSelectedIndex());
				FlowType typ = FlowType.Elementary;
				for (FlowType fte : ft.keySet()) {
					if (ft.get(fte) == typs) {
						typ = fte;
					}
				}

				FlowUnit einheit = cbbP01n2.getItemAt(cbbP01n2.getSelectedIndex());
				boolean nameOk = true;
				if ("".equals(name)) {
					nameOk = false;
					lblP01n5.setText(bundle.getString("stat02"));
				}
				if (name != name.replaceAll(" ","")) {
					nameOk = false;
					lblP01n5.setText(bundle.getString("stat20"));
					txtP01n1.setText("");
				}								
				if (nameOk) {
					if (Flow.containsName(name)) {
						lblP01n5.setText(bundle.getString("stat03"));
					} else {
						Flow.instance(name, typ, einheit);
						lblP01n5.setText(bundle.getString("stat04") + 
								MCAObject.getAllNames(Flow.class).size() + bundle.getString("stat05"));
						txtP01n1.setText("");
						cbbP01n1.setSelectedIndex(0);
						cbbP01n2.setSelectedIndex(0);
					}	
				} 		
			}
		});
	}
	
	@Override
	public void showSelf() {
		Language l = GUILanguage.getChosenLanguage();
		Locale locale = MultiVaLCA.LANGUAGES.get(l);
		String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
		
		lblP01n1.setFont(titlefont);
		txtP01n1.setFont(generalfont);
		btnP01n1.setFont(generalfont);
		lblP01n2.setFont(generalfont);
		lblP01n3.setFont(generalfont);
		lblP01n4.setFont(generalfont);
		lblP01n5.setFont(generalfont);
		cbbP01n1.setFont(generalfont);
		cbbP01n2.setFont(generalfont);
		
		lblP01n1.setText(bundle.getString("p01n1"));
		lblP01n2.setText(bundle.getString("p01n2"));
		lblP01n3.setText(bundle.getString("p01n3"));
		lblP01n4.setText(bundle.getString("p01n4"));
		lblP01n5.setText(bundle.getString("stat01"));
		btnP01n1.setText(bundle.getString("bt01"));
		LinkedHashMap<FlowType, String> ft = FlowTypeStringMap.getFTS(l);
		String[] fta = new String[ft.size()];
		int i=0;
		for (FlowType f : FlowType.values()) {
			String ftl = ft.get(f);
			fta[i] = ftl;
			i++;
		}
		cbbP01n1.setModel(new DefaultComboBoxModel<String>(fta));
	}
}
