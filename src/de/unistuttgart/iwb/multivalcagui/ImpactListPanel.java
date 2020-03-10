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
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int height = (int) screen.getHeight();
	private int width = (int) screen.getWidth();
	private Font titlefont = new Font ("Tahoma", Font.BOLD, height *22/1000);
	private Font generalfont = new Font ("Tahoma", Font.LAYOUT_LEFT_TO_RIGHT, height *15/1000);

	protected ImpactListPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		setLayout(new MigLayout("", "[grow]", "[16%][grow]"));		
		lblP11n1.setFont(titlefont);
		add(lblP11n1, "cell 0 0,alignx center,aligny center");		
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
		catTable.setFont(generalfont);
		catTable.setRowHeight(height *22/1000);
		catTable.setPreferredScrollableViewportSize(new Dimension(width*40/100, height*50/100));
		JTableHeader header = catTable.getTableHeader();
		header.setFont(generalfont);
		
		tcm.getColumn(0).setHeaderValue(bundle.getString("mp14"));
		tcm.getColumn(1).setHeaderValue(bundle.getString("p11n1"));
		for(String catName : ImpactCategory.getAllInstances().keySet()) {
			ImpactCategory ic = ImpactCategory.getInstance(catName);			
			catTableModel.addRow(new Object[] {ic.getName(), 
					ic.getEinheit().getName()});			
		}		
	}
}
