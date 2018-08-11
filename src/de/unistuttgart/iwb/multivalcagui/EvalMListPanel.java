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
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int height = (int) screen.getHeight();
	private int width = (int) screen.getWidth();
	private Font titlefont = new Font ("Tahoma", Font.BOLD, height *22/1000);
	private Font generalfont = new Font ("Tahoma", Font.LAYOUT_LEFT_TO_RIGHT, height *15/1000);

	protected EvalMListPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		setLayout(new MigLayout("", "[grow]", "[16%][grow]"));		
		lblP15n1.setFont(titlefont);
		add(lblP15n1, "cell 0 0,alignx center,aligny center");		
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
		bmTable.setFont(generalfont);
		bmTable.setRowHeight(height *22/1000);
		bmTable.setPreferredScrollableViewportSize(new Dimension(width*40/100, height*50/100));
		JTableHeader header = bmTable.getTableHeader();
		header.setFont(generalfont);
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
