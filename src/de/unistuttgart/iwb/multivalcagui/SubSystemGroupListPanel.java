/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.Font;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.HashSet;
import java.util.LinkedHashMap;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import de.unistuttgart.iwb.multivalca.FVMGroupType;
import de.unistuttgart.iwb.multivalca.Flow;
import de.unistuttgart.iwb.multivalca.FlowValueMaps;
import de.unistuttgart.iwb.multivalca.MCAObject;
import de.unistuttgart.iwb.multivalca.FlowValueMapGroup;
//import de.unistuttgart.iwb.multivalca.SubSystemGroup;
import de.unistuttgart.iwb.multivalca.ValueType;
import net.miginfocom.swing.MigLayout;

/**
 * @author HH, JS
 * @version 0.533
 */

public class SubSystemGroupListPanel extends MCAPanel{

	private JLabel lblP07n1 = new JLabel();
	private JTable pmTable 	= new JTable();
	private DefaultTableModel pmTableModel 	= new DefaultTableModel(0,4);

	protected SubSystemGroupListPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		setLayout(new MigLayout("", "[74px,grow]", "[14px][grow]"));		
		lblP07n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblP07n1, "cell 0 0,alignx center,aligny top");		
		add(new JScrollPane(pmTable), "cell 0 1,alignx center,aligny top");	

	}

	@Override
	public void showSelf() {
		Language l = GUILanguage.getChosenLanguage();
		Locale locale = MultiVaLCA.LANGUAGES.get(l);
		String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
		lblP07n1.setText(bundle.getString("mp50e"));
		pmTableModel.setRowCount(0);
		pmTable.setModel(pmTableModel);
		TableColumnModel tcm = pmTable.getColumnModel();

		tcm.getColumn(0).setHeaderValue(bundle.getString("mp23"));
		tcm.getColumn(1).setHeaderValue(bundle.getString("mp11"));
		tcm.getColumn(2).setHeaderValue(bundle.getString("p01n3"));
		tcm.getColumn(3).setHeaderValue(bundle.getString("p02n4"));
		HashSet<String> ssListe = new HashSet<String>();
		LinkedHashMap<String, MCAObject> instanceListe = new LinkedHashMap<String, MCAObject>();
		ssListe.addAll(FlowValueMapGroup.getAllInstances().keySet());
		instanceListe.putAll(FlowValueMapGroup.getAllInstances());
		for(String ss : ssListe) {
			if (FVMGroupType.Subsystem.equals(FlowValueMapGroup.getInstance(ss).getType())) {

				FlowValueMaps akSs = (FlowValueMaps)instanceListe.get(ss);

				pmTableModel.addRow(new Object[] {ss, "", "", ""});
				for(Flow pf : akSs.getElementarflussvektor().keySet()){
					for (ValueType vt : akSs.getElementarflussvektor().get(pf).keySet()) {
						pmTableModel.addRow(new Object[] {"", pf.getName(), 
								ValueTypeStringMap.getFVTS(l).get(vt),
								akSs.getElementarflussvektor().get(pf).get(vt)});
					}
				}						
				for(Flow pf : akSs.getProduktflussvektor().keySet()){
					for (ValueType vt : akSs.getProduktflussvektor().get(pf).keySet()) {
						pmTableModel.addRow(new Object[] {"", pf.getName(), 
								ValueTypeStringMap.getFVTS(l).get(vt),
								akSs.getProduktflussvektor().get(pf).get(vt)});							
					}							
				}
			}		
		}
	}

}
