/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import de.unistuttgart.iwb.multivalca.Flow;
import de.unistuttgart.iwb.multivalca.FlowValueMaps;
import de.unistuttgart.iwb.multivalca.ProcessModule;
import de.unistuttgart.iwb.multivalca.ProductSystem;
import net.miginfocom.swing.MigLayout;

/**
 * @author HH, JS
 * @version 0.524
 */

public class ProductSystemListPanel extends MCAPanel {
	
	private JLabel lblP08n1 = new JLabel();
	private JTable psTable 		= new JTable();
	DefaultTableModel psTableModel 		= new DefaultTableModel(0,3);

	protected ProductSystemListPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		setLayout(new MigLayout("", "[74px,grow]", "[14px][grow]"));		
		lblP08n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblP08n1, "cell 0 0,alignx center,aligny top");		
		add(new JScrollPane(psTable), "cell 0 1,alignx center,aligny top");	
		
	}

	@Override
	public void showSelf() {
		Language l = GUILanguage.getChosenLanguage();
		lblP08n1.setText(GuiStrings.getGS("mp43e", l));
		psTableModel.setRowCount(0);
		psTable.setModel(psTableModel);
		TableColumnModel tcm = psTable.getColumnModel();
		tcm.getColumn(0).setHeaderValue(GuiStrings.getGS("mp13", l));
		tcm.getColumn(1).setHeaderValue(GuiStrings.getGS("p08n1", l));
		tcm.getColumn(2).setHeaderValue(GuiStrings.getGS("p08n2", l));
		for(String psn : ProductSystem.getAllInstances().keySet()) {
			psTableModel.addRow(new Object[] {psn, "", ""});
			for (FlowValueMaps mnif : ProductSystem.getInstance(psn).getModulliste()){
				String mni = mnif.getName();
				boolean typmod = false;
				for(String modn2 : ProcessModule.getAllInstances().keySet()) {
					if (mni.equals(modn2)) {
						typmod = true;
					}
				}
				if (typmod == true){
					psTableModel.addRow(new Object[] {"",GuiStrings.getGS("mp12", l), mni});							
				} else {
					psTableModel.addRow(new Object[] {"",GuiStrings.getGS("p08n3", l), mni});	
				}					
			}
			for (Flow bvf : ProductSystem.getInstance(psn).getBedarfsvektor().keySet()) {
				psTableModel.addRow(new Object[] {"" ,GuiStrings.getGS("p08n4", l) 
						,"" + bvf.getName() + " (" + 
								ProductSystem.getInstance(psn).getBedarfsvektor().get(bvf) + 
						" " + bvf.getEinheit()+")"});
			}
			for (Flow vk : ProductSystem.getInstance(psn).getVorUndKoppelprodukte()) {
				psTableModel.addRow(new Object[] {"" ,GuiStrings.getGS("p03n6", l) 
						,vk.getName() });		
			}
		}
		
	}

}
