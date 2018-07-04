/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.Font;
import java.util.HashSet;
import java.util.LinkedHashMap;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import de.unistuttgart.iwb.multivalca.Flow;
import de.unistuttgart.iwb.multivalca.FlowValueMaps;
import de.unistuttgart.iwb.multivalca.MCAObject;
import de.unistuttgart.iwb.multivalca.ProcessModule;
import de.unistuttgart.iwb.multivalca.ProcessModuleGroup;
import de.unistuttgart.iwb.multivalca.ValueType;
import net.miginfocom.swing.MigLayout;

/**
 * @author HH, JS
 * @version 0.533
 */

public class ProcModListPanel extends MCAPanel{
	
	private JLabel lblP07n1 = new JLabel();
	private JTable pmTable 	= new JTable();
	private DefaultTableModel pmTableModel 	= new DefaultTableModel(0,4);

	protected ProcModListPanel(String key) {
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
		lblP07n1.setText(GuiStrings.getGS("mp42e", l));
		pmTableModel.setRowCount(0);
		pmTable.setModel(pmTableModel);
		TableColumnModel tcm = pmTable.getColumnModel();
		tcm.getColumn(0).setHeaderValue(GuiStrings.getGS("mp12", l));
		tcm.getColumn(1).setHeaderValue(GuiStrings.getGS("mp11", l));
		tcm.getColumn(2).setHeaderValue(GuiStrings.getGS("p01n3", l));
		tcm.getColumn(3).setHeaderValue(GuiStrings.getGS("p02n4", l));
		HashSet<String> modulListe = new HashSet<String>();
		LinkedHashMap<String, MCAObject> instanceListe = new LinkedHashMap<String, MCAObject>();
		modulListe.addAll(ProcessModule.getAllInstances().keySet());
		modulListe.addAll(ProcessModuleGroup.getAllInstances().keySet());
		instanceListe.putAll(ProcessModule.getAllInstances());
		instanceListe.putAll(ProcessModuleGroup.getAllInstances());
		for(String mn : modulListe) {
			FlowValueMaps akModul = (FlowValueMaps)instanceListe.get(mn);
			pmTableModel.addRow(new Object[] {mn, "", "", ""});
			for(Flow pf : akModul.getElementarflussvektor().keySet()){
				for (ValueType vt : akModul.getElementarflussvektor().get(pf).keySet()) {
					pmTableModel.addRow(new Object[] {"", pf.getName(), 
							ValueTypeStringMap.getFVTS(l).get(vt),
							akModul.getElementarflussvektor().get(pf).get(vt)});
				}
			}						
			for(Flow pf : akModul.getProduktflussvektor().keySet()){
				for (ValueType vt : akModul.getProduktflussvektor().get(pf).keySet()) {
					pmTableModel.addRow(new Object[] {"", pf.getName(), 
							ValueTypeStringMap.getFVTS(l).get(vt),
							akModul.getProduktflussvektor().get(pf).get(vt)});							
				}							
			}
		}			
	}

}
