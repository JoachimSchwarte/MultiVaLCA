/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.HashSet;
import java.util.LinkedHashMap;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
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
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int height = (int) screen.getHeight();
	private int width = (int) screen.getWidth();
	private Font titlefont = new Font ("Tahoma", Font.BOLD, height *22/1000);
	private Font generalfont = new Font ("Tahoma", Font.LAYOUT_LEFT_TO_RIGHT, height *15/1000);

	protected ProcModListPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		setLayout(new MigLayout("", "[grow]", "[16%][grow]"));		
		lblP07n1.setFont(titlefont);
		add(lblP07n1, "cell 0 0,alignx center,aligny center");		
		add(new JScrollPane(pmTable), "cell 0 1,alignx center,aligny top");	
		
	}

	@Override
	public void showSelf() {
		Language l = GUILanguage.getChosenLanguage();
		Locale locale = MultiVaLCA.LANGUAGES.get(l);
		String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
		lblP07n1.setText(bundle.getString("mp42e"));
		pmTableModel.setRowCount(0);
		pmTable.setModel(pmTableModel);
		TableColumnModel tcm = pmTable.getColumnModel();
		pmTable.setFont(generalfont);
		pmTable.setRowHeight(height *22/1000);
		pmTable.setPreferredScrollableViewportSize(new Dimension(width*40/100, height*50/100));
		JTableHeader header = pmTable.getTableHeader();
		header.setFont(generalfont);
		tcm.getColumn(0).setHeaderValue(bundle.getString("mp12"));
		tcm.getColumn(1).setHeaderValue(bundle.getString("mp11"));
		tcm.getColumn(2).setHeaderValue(bundle.getString("p01n3"));
		tcm.getColumn(3).setHeaderValue(bundle.getString("p02n4"));
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
