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
import de.unistuttgart.iwb.multivalca.FlowValueMaps;
import de.unistuttgart.iwb.multivalca.ProcessModule;
import de.unistuttgart.iwb.multivalca.ProcessModuleGroup;
import de.unistuttgart.iwb.multivalca.ProductSystem;
import net.miginfocom.swing.MigLayout;

/**
 * @author HH, JS
 * @version 0.545
 */

public class ProductSystemListPanel extends MCAPanel {
	
	private JLabel lblP08n1 = new JLabel();
	private JTable psTable 		= new JTable();
	private DefaultTableModel psTableModel 		= new DefaultTableModel(0,3);
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int height = (int) screen.getHeight();
	private int width = (int) screen.getWidth();
	private Font titlefont = new Font ("Tahoma", Font.BOLD, height *22/1000);
	private Font generalfont = new Font ("Tahoma", Font.LAYOUT_LEFT_TO_RIGHT, height *15/1000);

	protected ProductSystemListPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		setLayout(new MigLayout("", "[grow]", "[16%][grow]"));		
		lblP08n1.setFont(titlefont);
		add(lblP08n1, "cell 0 0,alignx center,aligny center");		
		add(new JScrollPane(psTable), "cell 0 1,alignx center,aligny top");		
	}

	@Override
	public void showSelf() {
		Language l = GUILanguage.getChosenLanguage();
		Locale locale = MultiVaLCA.LANGUAGES.get(l);
		String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
		lblP08n1.setText(bundle.getString("mp43e"));
		psTableModel.setRowCount(0);
		psTable.setModel(psTableModel);
		TableColumnModel tcm = psTable.getColumnModel();
		psTable.setFont(generalfont);
		psTable.setRowHeight(height *22/1000);
		psTable.setPreferredScrollableViewportSize(new Dimension(width*40/100, height*50/100));
		JTableHeader header = psTable.getTableHeader();
		header.setFont(generalfont);		
		tcm.getColumn(0).setHeaderValue(bundle.getString("mp13"));
		tcm.getColumn(1).setHeaderValue(bundle.getString("p08n1"));
		tcm.getColumn(2).setHeaderValue(bundle.getString("p08n2"));
		for(String psn : ProductSystem.getAllInstances().keySet()) {
			psTableModel.addRow(new Object[] {psn, "", ""});
			for (FlowValueMaps mnif : ProductSystem.getInstance(psn).getModulliste()){
				String mni = mnif.getName();
				if (mnif.getClass().equals(ProcessModule.class)) {
					psTableModel.addRow(new Object[] {"",bundle.getString("mp12"), mni});
				}
				if (mnif.getClass().equals(ProductSystem.class)) {
					psTableModel.addRow(new Object[] {"",bundle.getString("p08n3"), mni});
				}
				if (mnif.getClass().equals(ProcessModuleGroup.class)) {
					psTableModel.addRow(new Object[] {"",bundle.getString("mp122"), mni});
				}				
			}
			for (Flow bvf : ProductSystem.getInstance(psn).getBedarfsvektor().keySet()) {
				psTableModel.addRow(new Object[] {"" ,bundle.getString("p08n4") 
						,"" + bvf.getName() + " (" + 
								ProductSystem.getInstance(psn).getBedarfsvektor().get(bvf) + 
						" " + bvf.getEinheit()+")"});
			}
			for (Flow vk : ProductSystem.getInstance(psn).getVorUndKoppelprodukte()) {
				psTableModel.addRow(new Object[] {"" ,bundle.getString("p03n6") 
						,vk.getName() });		
			}
		}		
	}
}
