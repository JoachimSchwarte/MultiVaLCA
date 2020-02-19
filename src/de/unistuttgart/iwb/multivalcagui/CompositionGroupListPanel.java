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

import de.unistuttgart.iwb.multivalca.Composition;
import de.unistuttgart.iwb.multivalca.IVMGroupType;
import de.unistuttgart.iwb.multivalca.ImpactValueMapGroup;
import de.unistuttgart.iwb.multivalca.ImpactValueMaps;
import de.unistuttgart.iwb.multivalca.MCAObject;
import net.miginfocom.swing.MigLayout;

/**
 * @author Dr.-Ing. Joachim Schwarte, Helen Hein, Johannes Dippon
 * @version 0.700
 */

public class CompositionGroupListPanel extends MCAPanel{

	private JLabel lblP21n1 = new JLabel();
	private JTable pdTable 		= new JTable();
	private DefaultTableModel pdTableModel 		= new DefaultTableModel(0,2);


	protected CompositionGroupListPanel(String key) {
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
		lblP21n1.setText(bundle.getString("mp492e"));
		pdTableModel.setRowCount(0);
		pdTable.setModel(pdTableModel);
		TableColumnModel tcm = pdTable.getColumnModel();
		tcm.getColumn(0).setHeaderValue(bundle.getString("p06n1"));
		tcm.getColumn(1).setHeaderValue(bundle.getString("mp19"));	

		LinkedHashSet<String> imvgListe = new LinkedHashSet<String>();
		LinkedHashMap<String, MCAObject> instanceListe = new LinkedHashMap<String, MCAObject>();
		imvgListe.addAll(ImpactValueMapGroup.getAllInstances().keySet());
		instanceListe.putAll(ImpactValueMapGroup.getAllInstances());

		for(String imvg : imvgListe) {
			if (IVMGroupType.Composition.equals(ImpactValueMapGroup.getInstance(imvg).getType())) {			
			ImpactValueMaps im = (ImpactValueMaps)instanceListe.get(imvg);	
			pdTableModel.addRow(new Object[] {im.getName()});		
			LinkedHashSet<ImpactValueMaps> kompList = ImpactValueMapGroup.getInstance(imvg).getIVMList();
			for(ImpactValueMaps komp : kompList) {		
				Composition co = Composition.getInstance(komp.getName());	
				pdTableModel.addRow(new Object[] {"", co.getName()});
				
			}}}}

}
