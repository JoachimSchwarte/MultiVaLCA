package de.unistuttgart.iwb.multivalcagui;

import java.awt.Font;
import java.util.LinkedHashMap;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import de.unistuttgart.iwb.multivalca.Flow;
import de.unistuttgart.iwb.multivalca.ProductSystem;
import de.unistuttgart.iwb.multivalca.ValueType;
import net.miginfocom.swing.MigLayout;

public class LciCalcPanel extends MCAPanel {
	
	private JLabel lblP09n1 = new JLabel();
	private JTable lciTable 		= new JTable();
	DefaultTableModel lciTableModel 		= new DefaultTableModel(0,4);

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
		lblP09n1.setText(GuiStrings.getGS("mp51e", l));
		lciTableModel.setRowCount(0);
		lciTable.setModel(lciTableModel);
		TableColumnModel tcm = lciTable.getColumnModel();
		tcm.getColumn(0).setHeaderValue(GuiStrings.getGS("mp13", l));
		tcm.getColumn(1).setHeaderValue(GuiStrings.getGS("mp11", l));
		tcm.getColumn(2).setHeaderValue(GuiStrings.getGS("p01n3", l));
		tcm.getColumn(3).setHeaderValue(GuiStrings.getGS("p02n4", l));
		LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> sysErgebnis = new LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>>();
		if (ProductSystem.getAllInstances().size() > 0) {
			for(String sysName : ProductSystem.getAllInstances().keySet()) {
				lciTableModel.addRow(new Object[] {sysName,"","",""});
				ProductSystem sysAktuell = ProductSystem.getAllInstances().get(sysName);
				try {
					if (sysAktuell.getElementarflussvektor().size() > 0) {
						sysErgebnis = sysAktuell.getElementarflussvektor();
						for(Flow sysFluss : sysErgebnis.keySet()){
							for (ValueType vt : sysAktuell.getElementarflussvektor().get(sysFluss).keySet()) {
								lciTableModel.addRow(new Object[] {"",sysFluss.getName(),"" + 
										ValueTypeStringMap.getFVTS(l).get(vt),								
										sysErgebnis.get(sysFluss).get(vt) + " " + sysFluss.getEinheit() + ""});
							}
						}
					}
					if (sysAktuell.getProduktflussvektor().size() > 0) {
						sysErgebnis = sysAktuell.getProduktflussvektor();
						for(Flow sysFluss : sysErgebnis.keySet()){
							boolean ausgabe = false;
							if (sysAktuell.getVorUndKoppelprodukte().contains(sysFluss)) {
								ausgabe = true;
							}
							if (ausgabe == true) {
								for (ValueType vt : sysAktuell.getProduktflussvektor().get(sysFluss).keySet()) {
									lciTableModel.addRow(new Object[] {"",sysFluss.getName(),"" + 
											ValueTypeStringMap.getFVTS(l).get(vt),								
											sysErgebnis.get(sysFluss).get(vt) + " " + sysFluss.getEinheit() + ""});
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
