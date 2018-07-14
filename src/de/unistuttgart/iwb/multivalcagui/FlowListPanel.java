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
import net.miginfocom.swing.MigLayout;

/**
 * @author HH, JS
 * @version 0.536
 */

public class FlowListPanel extends MCAPanel {
	
	private JLabel lblP06n1 = new JLabel();
	private JTable flowsTable 		= new JTable();
	private DefaultTableModel flowsTableModel 		= new DefaultTableModel(0,3);
	
	protected FlowListPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		setLayout(new MigLayout("", "[74px,grow]", "[14px][grow]"));			
		lblP06n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblP06n1, "cell 0 0,alignx center,aligny top");		
		add(new JScrollPane(flowsTable), "cell 0 1,alignx center,aligny top");			
	}

	@Override
	public void showSelf() {
		Language l = GUILanguage.getChosenLanguage();
		Locale locale = MultiVaLCA.LANGUAGES.get(l);
		String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
		lblP06n1.setText(bundle.getString("mp41e"));
		flowsTableModel.setRowCount(0);
		flowsTable.setModel(flowsTableModel);
		TableColumnModel tcm = flowsTable.getColumnModel();
		tcm.getColumn(0).setHeaderValue(bundle.getString("p06n1"));
		tcm.getColumn(1).setHeaderValue(bundle.getString("p01n3"));
		tcm.getColumn(2).setHeaderValue(bundle.getString("p01n4"));
		for(String flussname : Flow.getAllInstances().keySet()) {
			Flow fluss = Flow.getInstance(flussname);			
			flowsTableModel.addRow(new Object[] {fluss.getName(), 
					FlowTypeStringMap.getFTS(l).get(fluss.getType()), fluss.getEinheit()});			
		}		
	}
}
