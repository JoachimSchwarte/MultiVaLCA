package de.unistuttgart.iwb.multivalcagui;

import java.awt.Font;
import java.util.Locale;
import java.util.ResourceBundle;

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
		Locale locale = MultiVaLCA.LANGUAGES.get(l);
		String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
		lblP20n1.setText(bundle.getString("mp47e"));
		pdTableModel.setRowCount(0);
		pdTable.setModel(pdTableModel);
		TableColumnModel tcm = pdTable.getColumnModel();
		tcm.getColumn(0).setHeaderValue(bundle.getString("p06n1"));
		tcm.getColumn(1).setHeaderValue(bundle.getString("p01n4"));
		tcm.getColumn(2).setHeaderValue(bundle.getString("mp16"));
		for(String pdName : ProductDeclaration.getAllInstances().keySet()) {
			ProductDeclaration pd = ProductDeclaration.getInstance(pdName);			
			pdTableModel.addRow(new Object[] {pd.getName(), pd.getEinheit(), pd.getBM().getName()});			
		}
		
	}

}