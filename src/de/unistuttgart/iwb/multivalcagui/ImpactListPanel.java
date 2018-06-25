package de.unistuttgart.iwb.multivalcagui;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import de.unistuttgart.iwb.multivalca.ImpactCategory;
import net.miginfocom.swing.MigLayout;

public class ImpactListPanel extends MCAPanel{

	private JLabel lblP11n1 = new JLabel();
	private JTable catTable 		= new JTable();
	DefaultTableModel catTableModel 		= new DefaultTableModel(0,2);

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
		lblP11n1.setText(GuiStrings.getGS("mp44e", l));
		catTableModel.setRowCount(0);
		catTable.setModel(catTableModel);
		TableColumnModel tcm = catTable.getColumnModel();
		tcm.getColumn(0).setHeaderValue(GuiStrings.getGS("mp14", l));
		tcm.getColumn(1).setHeaderValue(GuiStrings.getGS("p11n1", l));
		for(String catName : ImpactCategory.getAllInstances().keySet()) {
			ImpactCategory ic = ImpactCategory.getInstance(catName);			
			catTableModel.addRow(new Object[] {ic.getName(), 
					ic.getEinheit().getName()});			
		}	
		
	}

}
