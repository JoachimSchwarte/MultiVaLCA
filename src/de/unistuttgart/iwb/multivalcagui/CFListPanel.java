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
	
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int height = (int) screen.getHeight();
	private int width = (int) screen.getWidth();
	private Font titlefont = new Font ("Tahoma", Font.BOLD, height *22/1000);
	private Font generalfont = new Font ("Tahoma", Font.LAYOUT_LEFT_TO_RIGHT, height *15/1000);
	
	public CFListPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		setLayout(new MigLayout("", "[grow]", "[16%][grow]"));		
		lblP13n1.setFont(titlefont);
		add(lblP13n1, "cell 0 0,alignx center, aligny center");		
		add(new JScrollPane(cfTable), "cell 0 1,alignx center,aligny top");
			
	}

	@Override
	public void showSelf() {
		Language l = GUILanguage.getChosenLanguage();
		Locale locale = MultiVaLCA.LANGUAGES.get(l);
		String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);

		lblP13n1.setText(bundle.getString("mp45e"));
		cfTableModel.setRowCount(0);
		cfTable.setModel(cfTableModel);
		TableColumnModel tcm = cfTable.getColumnModel();
		
		cfTable.setFont(generalfont);
		cfTable.setRowHeight(height *22/1000);
		cfTable.setPreferredScrollableViewportSize(new Dimension(width*40/100, height*50/100));
		JTableHeader header = cfTable.getTableHeader();
		header.setFont(generalfont);
		
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
