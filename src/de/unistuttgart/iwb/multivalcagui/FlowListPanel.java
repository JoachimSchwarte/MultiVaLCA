package de.unistuttgart.iwb.multivalcagui;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import de.unistuttgart.iwb.multivalca.Flow;
import net.miginfocom.swing.MigLayout;

public class FlowListPanel extends MCAPanel {
	
	private JLabel lblP06n1 = new JLabel();
	private JTable flowsTable 		= new JTable();
	DefaultTableModel flowsTableModel 		= new DefaultTableModel(0,3);

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
		lblP06n1.setText(GuiStrings.getGS("mp41e", l));
		flowsTableModel.setRowCount(0);
		flowsTable.setModel(flowsTableModel);
		TableColumnModel tcm = flowsTable.getColumnModel();
		tcm.getColumn(0).setHeaderValue(GuiStrings.getGS("p06n1", l));
		tcm.getColumn(1).setHeaderValue(GuiStrings.getGS("p01n3", l));
		tcm.getColumn(2).setHeaderValue(GuiStrings.getGS("p01n4", l));
		for(String flussname : Flow.getAllInstances().keySet()) {
			Flow fluss = Flow.getInstance(flussname);			
			flowsTableModel.addRow(new Object[] {fluss.getName(), 
					FlowTypeStringMap.getFTS(l).get(fluss.getType()), fluss.getEinheit()});			
		}
		
	}

}
