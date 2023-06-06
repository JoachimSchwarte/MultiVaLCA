/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import de.unistuttgart.iwb.multivalca.FVMGroupType;
import de.unistuttgart.iwb.multivalca.Flow;
import de.unistuttgart.iwb.multivalca.FlowValueMapGroup;
import de.unistuttgart.iwb.multivalca.FlowValueMaps;
import de.unistuttgart.iwb.multivalca.MCAObject;
import de.unistuttgart.iwb.multivalca.ValueType;
import net.miginfocom.swing.MigLayout;

/**
 * @author Dr.-Ing. Joachim Schwarte, Helen Hein, Johannes Dippon
 * @version 0.804
 */

public class ProdSysGroupListPanel extends MCAPanel{

	private JLabel lblP07n1 = new JLabel();
	private JTable pmTable 	= new JTable();
	private DefaultTableModel pmTableModel 	= new DefaultTableModel(0,4);
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int height = (int) screen.getHeight();
	private int width = (int) screen.getWidth();
	private Font titlefont = new Font ("Tahoma", Font.BOLD, height *22/1000);
	private Font generalfont = new Font ("Tahoma", Font.LAYOUT_LEFT_TO_RIGHT, height *15/1000);

	protected ProdSysGroupListPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		setLayout(new MigLayout("", "[grow]", "[16%][grow]"));		
		lblP07n1.setFont(titlefont);
		add(lblP07n1, "cell 0 0,alignx center,aligny top");		
		add(new JScrollPane(pmTable), "cell 0 1,alignx center,aligny top");	

	}

	@Override
	public void showSelf() {
		Language l = GUILanguage.getChosenLanguage();
		Locale locale = MultiVaLCA.LANGUAGES.get(l);
		String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
		lblP07n1.setText(bundle.getString("mp432e"));
		pmTableModel.setRowCount(0);
		pmTable.setModel(pmTableModel);
		TableColumnModel tcm = pmTable.getColumnModel();
		pmTable.setFont(generalfont);
		pmTable.setRowHeight(height *22/1000);
		pmTable.setPreferredScrollableViewportSize(new Dimension(width*40/100, height*50/100));
		JTableHeader header = pmTable.getTableHeader();
		header.setFont(generalfont);

		tcm.getColumn(0).setHeaderValue(bundle.getString("p06n1"));
		tcm.getColumn(1).setHeaderValue(bundle.getString("mp11"));
		tcm.getColumn(2).setHeaderValue(bundle.getString("p01n3"));
		tcm.getColumn(3).setHeaderValue(bundle.getString("p02n4"));
		HashSet<String> ssListe = new HashSet<String>();
		LinkedHashMap<String, MCAObject> instanceListe = new LinkedHashMap<String, MCAObject>();
		ssListe.addAll(FlowValueMapGroup.getAllInstances().keySet());
		instanceListe.putAll(FlowValueMapGroup.getAllInstances());
		for(String ss : ssListe) {
			if (FVMGroupType.ProductSystem.equals(FlowValueMapGroup.getInstance(ss).getType())) {
				FlowValueMaps akSs = (FlowValueMaps)instanceListe.get(ss);
				LinkedHashSet<String> pml = new LinkedHashSet<String>();
				for (FlowValueMaps fvml : FlowValueMapGroup.getInstance(akSs.getName()).getFVMList()) {
					 pml.add(fvml.getName());
				}
				String[] str = new String[pml.size()];
				pml.toArray(str);
				String str1 = str[0];
				for (int i = 1; i < str.length; i++) {
					  str1 = str1 + ", " + str[i];
					}	
				pmTableModel.addRow(new Object[] {ss + " (" + str1 + ")", "", "", ""});
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
