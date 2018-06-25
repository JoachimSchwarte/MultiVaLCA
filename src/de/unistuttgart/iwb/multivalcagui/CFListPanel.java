package de.unistuttgart.iwb.multivalcagui;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import de.unistuttgart.iwb.multivalca.CharacFactor;
import de.unistuttgart.iwb.multivalca.ValueType;
import net.miginfocom.swing.MigLayout;

public class CFListPanel extends MCAPanel{
	
	private JLabel lblP13n1 = new JLabel();
	private JTable cfTable 		= new JTable();
	DefaultTableModel cfTableModel 		= new DefaultTableModel(0,5);

	public CFListPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		setLayout(new MigLayout("", "[74px,grow]", "[14px][grow]"));		
		lblP13n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblP13n1, "cell 0 0,alignx center,aligny top");		
		add(new JScrollPane(cfTable), "cell 0 1,alignx center,aligny top");
			
	}

	@Override
	public void showSelf() {
		Language l = GUILanguage.getChosenLanguage();
		lblP13n1.setText(GuiStrings.getGS("mp45e", l));
		cfTableModel.setRowCount(0);
		cfTable.setModel(cfTableModel);
		TableColumnModel tcm = cfTable.getColumnModel();
		tcm.getColumn(0).setHeaderValue(GuiStrings.getGS("p06n1", l));
		tcm.getColumn(1).setHeaderValue(GuiStrings.getGS("mp11", l));
		tcm.getColumn(2).setHeaderValue(GuiStrings.getGS("p13n1", l));
		tcm.getColumn(3).setHeaderValue(GuiStrings.getGS("p01n3", l));
		tcm.getColumn(4).setHeaderValue(GuiStrings.getGS("p12n3", l));
		for(String cfName : CharacFactor.getAllInstances().keySet()) {
			CharacFactor cf = CharacFactor.getInstance(cfName);
			cfTableModel.addRow(new Object[] {cf.getName(),
					cf.getFlow().getName(), cf.getWirkung().getName(),
					ValueTypeStringMap.getFVTS(l).get(ValueType.MeanValue),
					cf.getValues().get(ValueType.MeanValue)});
			cfTableModel.addRow(new Object[] {"",
					"", "", ValueTypeStringMap.getFVTS(l).get(ValueType.LowerBound),
					cf.getValues().get(ValueType.LowerBound)});
			cfTableModel.addRow(new Object[] {"",
					"", "", ValueTypeStringMap.getFVTS(l).get(ValueType.UpperBound),
					cf.getValues().get(ValueType.UpperBound)});
		}
		
	}

}
