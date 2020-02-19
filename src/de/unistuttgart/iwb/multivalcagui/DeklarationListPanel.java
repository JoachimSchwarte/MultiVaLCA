/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.Font;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import de.unistuttgart.iwb.multivalca.ImpactCategory;
//import de.unistuttgart.iwb.multivalca.ImpactValueMaps;
import de.unistuttgart.iwb.multivalca.MCAObject;
import de.unistuttgart.iwb.multivalca.ProductDeclaration;
import de.unistuttgart.iwb.multivalca.ValueType;
import net.miginfocom.swing.MigLayout;

/**
 * @author Dr.-Ing. Joachim Schwarte, Helen Hein, Johannes Dippon
 * @version 0.700
 */

public class DeklarationListPanel extends MCAPanel{

	private JLabel lblP20n1 = new JLabel();
	private JTable pdTable 		= new JTable();
	private DefaultTableModel pdTableModel 		= new DefaultTableModel(0,5);

	protected DeklarationListPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		setLayout(new MigLayout("", "[grow][555][grow]", "[14px][grow]"));
		lblP20n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblP20n1, "cell 1 0,alignx center,aligny top");
		add(new JScrollPane(pdTable), "cell 1 1,growx,aligny top");

	}

	@Override
	public void showSelf() {
		Language l = GUILanguage.getChosenLanguage();
		Locale locale = MultiVaLCA.LANGUAGES.get(l);
		String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
		lblP20n1.setText(bundle.getString("mp471e"));
		pdTableModel.setRowCount(0);
		
		pdTable.setModel(pdTableModel);
		TableColumnModel tcm = pdTable.getColumnModel();
		tcm.getColumn(0).setHeaderValue(bundle.getString("p06n1"));
		tcm.getColumn(1).setHeaderValue(bundle.getString("p01n4"));		
		tcm.getColumn(2).setHeaderValue(bundle.getString("mp14"));
		tcm.getColumn(3).setHeaderValue(bundle.getString("p01n3"));
		tcm.getColumn(4).setHeaderValue(bundle.getString("p02n4"));

		pdTable.getColumnModel().getColumn(2).setPreferredWidth(110);


		LinkedHashSet<String> decListe = new LinkedHashSet<String>();
		LinkedHashMap<String, MCAObject> instanceListe = new LinkedHashMap<String, MCAObject>();
		decListe.addAll(ProductDeclaration.getAllInstances().keySet());
		instanceListe.putAll(ProductDeclaration.getAllInstances());
		for(String dec : decListe) {		
			ProductDeclaration pd = ProductDeclaration.getInstance(dec);	
			pdTableModel.addRow(new Object[] {pd.getName(), pd.getEinheit()});			

			for(ImpactCategory ic : pd.getImpactValueMap().keySet()){
				for (ValueType vt : pd.getImpactValueMap().get(ic).keySet()) {
					pdTableModel.addRow(new Object[] {"","", ic.getName(), 
							ValueTypeStringMap.getFVTS(l).get(vt),
							pd.getImpactValueMap().get(ic).get(vt)});
				}
				
			}}	
	}
}
