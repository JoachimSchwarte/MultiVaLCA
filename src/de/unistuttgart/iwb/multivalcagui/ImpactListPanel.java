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

import de.unistuttgart.iwb.multivalca.ImpactCategory;
import net.miginfocom.swing.MigLayout;

/**
 * @author HH, JS
 * @version 0.536
 */

public class ImpactListPanel extends MCAPanel{

	private JLabel lblP11n1 = new JLabel();
	private JTable catTable 		= new JTable();
	private DefaultTableModel catTableModel 		= new DefaultTableModel(0,2);

	protected ImpactListPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		setLayout(new MigLayout("", "[74px,grow]", "[14px][grow]"));		
		lblP11n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblP11n1, "cell 0 0,alignx center,aligny top");		
		add(new JScrollPane(catTable), "cell 0 1,alignx center,aligny top");
		

	}

	@Override
	public void showSelf() {
		Language l = GUILanguage.getChosenLanguage();
		Locale locale = MultiVaLCA.LANGUAGES.get(l);
		String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
		lblP11n1.setText(bundle.getString("mp44e"));
		catTableModel.setRowCount(0);
		catTable.setModel(catTableModel);
		TableColumnModel tcm = catTable.getColumnModel();
		tcm.getColumn(0).setHeaderValue(bundle.getString("mp14"));
		tcm.getColumn(1).setHeaderValue(bundle.getString("p11n1"));
		for(String catName : ImpactCategory.getAllInstances().keySet()) {
			ImpactCategory ic = ImpactCategory.getInstance(catName);			
			catTableModel.addRow(new Object[] {ic.getName(), 
					ic.getEinheit().getName()});			
		}	
		
	}

}
