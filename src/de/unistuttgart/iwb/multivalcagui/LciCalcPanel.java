/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.Font;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import de.unistuttgart.iwb.multivalca.Flow;
import de.unistuttgart.iwb.multivalca.ProductSystem;
import de.unistuttgart.iwb.multivalca.ValueType;
import net.miginfocom.swing.MigLayout;

/**
 * @author HH, JS
 * @version 0.547
 */

public class LciCalcPanel extends MCAPanel {
	
	private JLabel lblP09n1 = new JLabel();
	private JTable lciTable = new JTable();
	private DefaultTableModel lciTableModel = new DefaultTableModel(0,4);


	protected LciCalcPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		setLayout(new MigLayout("", "[74px,grow]", "[14px][grow]"));		
		lblP09n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblP09n1, "cell 0 0,alignx center,aligny top");		
		add(new JScrollPane(lciTable), "cell 0 1,alignx center,aligny top");
		
	}

	@Override
	public void showSelf() {
		Language l = GUILanguage.getChosenLanguage();
		Locale locale = MultiVaLCA.LANGUAGES.get(l);
		String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
		lblP09n1.setText(bundle.getString("mp51e"));
		lciTableModel.setRowCount(0);
		lciTable.setModel(lciTableModel);
		TableColumnModel tcm = lciTable.getColumnModel();
		tcm.getColumn(0).setHeaderValue(bundle.getString("mp13"));
		tcm.getColumn(1).setHeaderValue(bundle.getString("mp11"));
		tcm.getColumn(2).setHeaderValue(bundle.getString("p01n3"));
		tcm.getColumn(3).setHeaderValue(bundle.getString("p02n4"));
		if (ProductSystem.getAllInstances().size() > 0) {
			for(String sysName : ProductSystem.getAllInstances().keySet()) {			
				lciTableModel.addRow(new Object[] {sysName,"","",""});
				ProductSystem sysAktuell = ProductSystem.getAllInstances().get(sysName);
				LinkedHashMap<Flow,LinkedHashMap<ValueType,Double>> efvAktuell = sysAktuell.getElementarflussvektor();
				LinkedHashMap<Flow,LinkedHashMap<ValueType,Double>> pfvAktuell = sysAktuell.getProduktflussvektor();
				try {
					if (efvAktuell.size() > 0) {
						for(Flow sysFluss : efvAktuell.keySet()){
							for (ValueType vt : efvAktuell.get(sysFluss).keySet()) {
								lciTableModel.addRow(new Object[] {"",sysFluss.getName(),"" + 
										ValueTypeStringMap.getFVTS(l).get(vt),								
										efvAktuell.get(sysFluss).get(vt) + " " + sysFluss.getEinheit() + ""});
							}
						}
					}
					if (pfvAktuell.size() > 0) {
						for(Flow sysFluss : pfvAktuell.keySet()){
							boolean ausgabe = false;
							if (sysAktuell.getVorUndKoppelprodukte().contains(sysFluss)) {
								ausgabe = true;
							}
							if (ausgabe) {
								for (ValueType vt : pfvAktuell.get(sysFluss).keySet()) {
									lciTableModel.addRow(new Object[] {"",sysFluss.getName(),"" + 
											ValueTypeStringMap.getFVTS(l).get(vt),								
											pfvAktuell.get(sysFluss).get(vt) + " " + sysFluss.getEinheit() + ""});
								}
							}
						}
					} 
				} catch (ArithmeticException vz) {
						lciTableModel.addRow(new Object[] 
								{"",vz.getMessage(),"",""});					
				}					 
			}
		}	
	}
}
