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

import de.unistuttgart.iwb.multivalca.CharacFactor;
import de.unistuttgart.iwb.multivalca.ValueType;
import net.miginfocom.swing.MigLayout;

/**
 * @author HH, JS
 * @version 0.536
 */

public class CFListPanel extends MCAPanel{
	
	private JLabel lblP13n1 = new JLabel();
	private JTable cfTable 		= new JTable();
	private DefaultTableModel cfTableModel 		= new DefaultTableModel(0,5);
	private Language l = GUILanguage.getChosenLanguage();
	private Locale locale = MultiVaLCA.LANGUAGES.get(l);
	private String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
	private ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);

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
		l = GUILanguage.getChosenLanguage();
		locale = MultiVaLCA.LANGUAGES.get(l);
		baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		bundle = ResourceBundle.getBundle(baseName, locale);

		lblP13n1.setText(bundle.getString("mp45e"));
		cfTableModel.setRowCount(0);
		cfTable.setModel(cfTableModel);
		TableColumnModel tcm = cfTable.getColumnModel();
		tcm.getColumn(0).setHeaderValue(bundle.getObject("p06n1"));
		tcm.getColumn(1).setHeaderValue(bundle.getObject("mp11"));
		tcm.getColumn(2).setHeaderValue(bundle.getObject("p13n1"));
		tcm.getColumn(3).setHeaderValue(bundle.getObject("p01n3"));
		tcm.getColumn(4).setHeaderValue(bundle.getObject("p12n3"));
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
