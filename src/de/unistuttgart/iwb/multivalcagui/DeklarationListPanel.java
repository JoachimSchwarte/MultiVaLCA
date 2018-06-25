package de.unistuttgart.iwb.multivalcagui;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import de.unistuttgart.iwb.multivalca.ProductDeclaration;
import net.miginfocom.swing.MigLayout;

public class DeklarationListPanel extends MCAPanel{
	
	private JLabel lblP20n1 = new JLabel();
	private JTable pdTable 		= new JTable();
	DefaultTableModel pdTableModel 		= new DefaultTableModel(0,3);

	protected DeklarationListPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		setLayout(new MigLayout("", "[74px,grow]", "[14px][gro"
				+ "w]"));
		lblP20n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblP20n1, "cell 0 0,alignx center,aligny top");
		add(new JScrollPane(pdTable), "cell 0 1,alignx center,aligny top");
		

	}

	@Override
	public void showSelf() {
		Language l = GUILanguage.getChosenLanguage();
		lblP20n1.setText(GuiStrings.getGS("mp47e", l));
		pdTableModel.setRowCount(0);
		pdTable.setModel(pdTableModel);
		TableColumnModel tcm = pdTable.getColumnModel();
		tcm.getColumn(0).setHeaderValue(GuiStrings.getGS("p06n1", l));
		tcm.getColumn(1).setHeaderValue(GuiStrings.getGS("p01n4", l));
		tcm.getColumn(2).setHeaderValue(GuiStrings.getGS("mp16", l));
		for(String pdName : ProductDeclaration.getAllInstances().keySet()) {
			ProductDeclaration pd = ProductDeclaration.getInstance(pdName);			
			pdTableModel.addRow(new Object[] {pd.getName(), pd.getEinheit(), pd.getBM().getName()});			
		}
		
	}

}
