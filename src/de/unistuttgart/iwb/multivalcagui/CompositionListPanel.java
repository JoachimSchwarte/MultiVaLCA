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
import de.unistuttgart.iwb.multivalca.Composition;
import de.unistuttgart.iwb.multivalca.IVMGroupType;
import de.unistuttgart.iwb.multivalca.ImpactValueMapGroup;
import de.unistuttgart.iwb.multivalca.ImpactValueMaps;
import de.unistuttgart.iwb.multivalca.MCAObject;
import de.unistuttgart.iwb.multivalca.ProductSystem;
import net.miginfocom.swing.MigLayout;

/**
 * @author Dr.-Ing. Joachim Schwarte, Helen Hein, Johannes Dippon
 * @version 0.700
 */

public class CompositionListPanel extends MCAPanel{

	private JLabel lblP22n1 = new JLabel();
	private JTable pdTable 		= new JTable();
	private DefaultTableModel pdTableModel 		= new DefaultTableModel(0,4);

	protected CompositionListPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		setLayout(new MigLayout("", "[74px,grow]", "[14px][grow]"));
		lblP22n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblP22n1, "cell 0 0,alignx center,aligny top");	
		add(new JScrollPane(pdTable), "cell 0 1,alignx center,aligny top");
	}

	@Override
	public void showSelf() {
		Language l = GUILanguage.getChosenLanguage();
		Locale locale = MultiVaLCA.LANGUAGES.get(l);
		String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
		lblP22n1.setText(bundle.getString("mp491e"));
		pdTableModel.setRowCount(0);
		pdTable.setModel(pdTableModel);
		TableColumnModel tcm = pdTable.getColumnModel();
		tcm.getColumn(0).setHeaderValue(bundle.getString("p06n1"));
		tcm.getColumn(1).setHeaderValue(bundle.getString("p08n1"));
		tcm.getColumn(2).setHeaderValue(bundle.getString("p08n2"));
		tcm.getColumn(3).setHeaderValue(bundle.getString("p19n4"));

		LinkedHashSet<String> komposListe = new LinkedHashSet<String>();
		LinkedHashMap<String, MCAObject> instanceListe = new LinkedHashMap<String, MCAObject>();
		komposListe.addAll(Composition.getAllInstances().keySet());
		instanceListe.putAll(Composition.getAllInstances());

		for(String kompos : komposListe) {		
			Composition co = (Composition) instanceListe.get(kompos);
			pdTableModel.addRow(new Object[] {co.getName()});
			for (ImpactValueMaps ivm : co.getZusammensetzung().keySet()) {
				if (ivm.getClass().equals(Component.class)) {
					pdTableModel.addRow(new Object[] {"",bundle.getString("mp18"), ivm.getName(), co.getZusammensetzung().get(ivm)});
				} 
			}
			for (ImpactValueMaps ivm : co.getZusammensetzung().keySet()) {
				if (ivm.getClass().equals(ImpactValueMapGroup.class)) {
					if (IVMGroupType.Component.equals(ImpactValueMapGroup.getInstance(ivm.getName()).getType())) {
						pdTableModel.addRow(new Object[] {"",bundle.getString("mp182"), ivm.getName(), co.getZusammensetzung().get(ivm)});
					}
				}
			}
			for (ImpactValueMaps ivm : co.getZusammensetzung().keySet()) {
				if (ivm.getClass().equals(Composition.class)) {
					pdTableModel.addRow(new Object[] {"",bundle.getString("p22n1"), ivm.getName(), co.getZusammensetzung().get(ivm)});
				}
			}
			for (ImpactValueMaps ivm : co.getZusammensetzung().keySet()) {
				if (ivm.getClass().equals(ImpactValueMapGroup.class)) {
					if (IVMGroupType.Composition.equals(ImpactValueMapGroup.getInstance(ivm.getName()).getType())) {
						pdTableModel.addRow(new Object[] {"",bundle.getString("p22n2"), ivm.getName(), co.getZusammensetzung().get(ivm)});
					}
				}
			}
			for (ImpactValueMaps ivm : co.getZusammensetzung().keySet()) {
				if (ivm.getClass().equals(ProductSystem.class)) {
					pdTableModel.addRow(new Object[] {"",bundle.getString("mp13"), ivm.getName(), co.getZusammensetzung().get(ivm)});
				}
			}
			
		}

	}	
}

