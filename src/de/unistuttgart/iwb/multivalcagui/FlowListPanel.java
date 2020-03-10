/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
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
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int height = (int) screen.getHeight();
	private int width = (int) screen.getWidth();
	private Font titlefont = new Font ("Tahoma", Font.BOLD, height *22/1000);
	private Font generalfont = new Font ("Tahoma", Font.LAYOUT_LEFT_TO_RIGHT, height *15/1000);
	
	protected FlowListPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		setLayout(new MigLayout("", "[grow]", "[16%][grow]"));			
		lblP06n1.setFont(titlefont);
		add(lblP06n1, "cell 0 0,alignx center,aligny center");		
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
		flowsTable.setFont(generalfont);
		flowsTable.setRowHeight(height *22/1000);
		flowsTable.setPreferredScrollableViewportSize(new Dimension(width*40/100, height*50/100));
		TableColumnModel tcm = flowsTable.getColumnModel();
		JTableHeader header = flowsTable.getTableHeader();
		header.setFont(generalfont);		
		
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
