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

import de.unistuttgart.iwb.multivalca.LCIAMethod;
import net.miginfocom.swing.MigLayout;

/**
 * @author HH, JS
 * @version 0.536
 */


public class EvalMListPanel extends MCAPanel {

	private JLabel lblP15n1 = new JLabel();
	private JTable bmTable 		= new JTable();
	private DefaultTableModel bmTableModel 		= new DefaultTableModel(0,3);

	protected EvalMListPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		setLayout(new MigLayout("", "[74px,grow]", "[14px][grow]"));		
		lblP15n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblP15n1, "cell 0 0,alignx center,aligny top");		
		add(new JScrollPane(bmTable), "cell 0 1,alignx center,aligny top");		
	}

	@Override
	public void showSelf() {
		Language l = GUILanguage.getChosenLanguage();
		Locale locale = MultiVaLCA.LANGUAGES.get(l);
		String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
		lblP15n1.setText(bundle.getString("mp46e"));
		bmTableModel.setRowCount(0);
		bmTable.setModel(bmTableModel);
		TableColumnModel tcm = bmTable.getColumnModel();
		tcm.getColumn(0).setHeaderValue(bundle.getString("mp16"));
		tcm.getColumn(1).setHeaderValue(bundle.getString("p08n1"));
		tcm.getColumn(2).setHeaderValue(bundle.getString("p08n2"));
		for(String bmName : LCIAMethod.getAllInstances().keySet()) {
			LCIAMethod bm = LCIAMethod.instance(bmName);
			bmTableModel.addRow(new Object[] {bm.getName(),"",""});
			for(String wkName : bm.categoryList().keySet()) {
				bmTableModel.addRow(new Object[] {"",bundle.getString("mp14"),wkName});
			}
			for(String cfName : bm.getFactorSet().keySet()) {
				bmTableModel.addRow(new Object[] {"",bundle.getString("mp15"),cfName});
			}				
		}			
	}
}
