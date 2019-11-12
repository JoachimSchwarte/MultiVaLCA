/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.Font;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import de.unistuttgart.iwb.multivalca.ImpactCategory;
import de.unistuttgart.iwb.multivalca.ImpactValueMaps;
import de.unistuttgart.iwb.multivalca.MCAObject;
import de.unistuttgart.iwb.multivalca.ProductDeclaration;
import de.unistuttgart.iwb.multivalca.ValueType;
import net.miginfocom.swing.MigLayout;

/**
 * @author HH, JS
 * @version 0.536
 */

public class DeklarationListPanel extends MCAPanel{

	private JLabel lblP20n1 = new JLabel();
	private JTable pdTable 		= new JTable();
	private DefaultTableModel pdTableModel 		= new DefaultTableModel(0,6);

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
		lblP20n1.setText(bundle.getString("mp47e"));
		pdTableModel.setRowCount(0);
		
		pdTable.setModel(pdTableModel);
		TableColumnModel tcm = pdTable.getColumnModel();
		tcm.getColumn(0).setHeaderValue(bundle.getString("p06n1"));
		tcm.getColumn(1).setHeaderValue(bundle.getString("p01n4"));
		tcm.getColumn(2).setHeaderValue(bundle.getString("mp16"));
		tcm.getColumn(3).setHeaderValue(bundle.getString("mp14"));
		tcm.getColumn(4).setHeaderValue(bundle.getString("p01n3"));
		tcm.getColumn(5).setHeaderValue(bundle.getString("p02n4"));

		pdTable.getColumnModel().getColumn(2).setPreferredWidth(120);
		pdTable.getColumnModel().getColumn(3).setPreferredWidth(110);


		HashSet<String> decListe = new HashSet<String>();
		LinkedHashMap<String, MCAObject> instanceListe = new LinkedHashMap<String, MCAObject>();
		decListe.addAll(ProductDeclaration.getAllInstances().keySet());
		instanceListe.putAll(ProductDeclaration.getAllInstances());

		for(String dec : decListe) {
			ImpactValueMaps wvDec = (ImpactValueMaps)instanceListe.get(dec); 
			
			ProductDeclaration pd = ProductDeclaration.getInstance(dec);			
			pdTableModel.addRow(new Object[] {pd.getName(), pd.getEinheit(), pd.getBM().getName()});			

			for(ImpactCategory ic : wvDec.getImpactValueMap(pd.getBM()).keySet()){
				for (ValueType vt : wvDec.getImpactValueMap(pd.getBM()).get(ic).keySet()) {
					pdTableModel.addRow(new Object[] {"","","", ic.getName(), 
							ValueTypeStringMap.getFVTS(l).get(vt),
							wvDec.getImpactValueMap(pd.getBM()).get(ic).get(vt)});
				}
				
			}}	
	}
}
