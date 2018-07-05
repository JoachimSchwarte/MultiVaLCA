/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.Font;
import java.util.Locale;
import java.util.ResourceBundle;

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
 * @version 0.536
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
		Locale locale = MultiVaLCA.LANGUAGES.get(l);
		String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
		lblP08n1.setText(bundle.getString("mp43e"));
		psTableModel.setRowCount(0);
		psTable.setModel(psTableModel);
		TableColumnModel tcm = psTable.getColumnModel();
		tcm.getColumn(0).setHeaderValue(bundle.getString("mp13"));
		tcm.getColumn(1).setHeaderValue(bundle.getString("p08n1"));
		tcm.getColumn(2).setHeaderValue(bundle.getString("p08n2"));
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
					psTableModel.addRow(new Object[] {"",bundle.getString("mp12"), mni});							
				} else {
					psTableModel.addRow(new Object[] {"",bundle.getString("p08n3"), mni});	
				}					
			}
			for (Flow bvf : ProductSystem.getInstance(psn).getBedarfsvektor().keySet()) {
				psTableModel.addRow(new Object[] {"" ,bundle.getString("p08n4") 
						,"" + bvf.getName() + " (" + 
								ProductSystem.getInstance(psn).getBedarfsvektor().get(bvf) + 
						" " + bvf.getEinheit()+")"});
			}
			for (Flow vk : ProductSystem.getInstance(psn).getVorUndKoppelprodukte()) {
				psTableModel.addRow(new Object[] {"" ,bundle.getString("p03n6") 
						,vk.getName() });		
			}
		}
		
	}

}
