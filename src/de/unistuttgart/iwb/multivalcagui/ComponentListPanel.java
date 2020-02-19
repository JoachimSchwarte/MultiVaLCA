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

import de.unistuttgart.iwb.multivalca.Component;
import de.unistuttgart.iwb.multivalca.MCAObject;
import de.unistuttgart.iwb.multivalca.ValueType;
import net.miginfocom.swing.MigLayout;

/**
 * @author Dr.-Ing. Joachim Schwarte, Helen Hein, Johannes Dippon
 * @version 0.700
 */

public class ComponentListPanel extends MCAPanel{

	private JLabel lblP21n1 = new JLabel();
	private JTable pdTable 		= new JTable();
	private DefaultTableModel pdTableModel 		= new DefaultTableModel(0,5);


	protected ComponentListPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		setLayout(new MigLayout("", "[grow][555][grow]", "[14px][grow]"));
		lblP21n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblP21n1, "cell 1 0,alignx center,aligny top");
		add(new JScrollPane(pdTable), "cell 1 1,growx,aligny top");

	}

	@Override
	public void showSelf() {
		Language l = GUILanguage.getChosenLanguage();
		Locale locale = MultiVaLCA.LANGUAGES.get(l);
		String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
		lblP21n1.setText(bundle.getString("mp481e"));
		pdTableModel.setRowCount(0);
		pdTable.setModel(pdTableModel);
		TableColumnModel tcm = pdTable.getColumnModel();
		tcm.getColumn(0).setHeaderValue(bundle.getString("p06n1"));
		tcm.getColumn(1).setHeaderValue(bundle.getString("p01n4"));		
		tcm.getColumn(2).setHeaderValue(bundle.getString("p18n3"));
		tcm.getColumn(3).setHeaderValue(bundle.getString("p01n3"));
		tcm.getColumn(4).setHeaderValue(bundle.getString("p02n4"));

		LinkedHashSet<String> kompListe = new LinkedHashSet<String>();
		LinkedHashMap<String, MCAObject> instanceListe = new LinkedHashMap<String, MCAObject>();
		kompListe.addAll(Component.getAllInstances().keySet());
		instanceListe.putAll(Component.getAllInstances());

		for(String komp : kompListe) {		
			Component co = (Component) instanceListe.get(komp);	
			pdTableModel.addRow(new Object[] {co.getName(), co.getEinheit().toString(), co.getKomponente().getName()});
			for (ValueType vt : co.getValues().keySet()) {
				pdTableModel.addRow(new Object[] {"", "", "", 
						ValueTypeStringMap.getFVTS(l).get(vt),
						co.getValues().get(vt)});
			}

		}}	

}
