/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import de.unistuttgart.iwb.multivalca.IVMGroupType;
import de.unistuttgart.iwb.multivalca.ImpactCategory;
import de.unistuttgart.iwb.multivalca.ImpactValueMapGroup;
import de.unistuttgart.iwb.multivalca.ImpactValueMaps;
import de.unistuttgart.iwb.multivalca.MCAObject;
import de.unistuttgart.iwb.multivalca.ProductDeclaration;
import de.unistuttgart.iwb.multivalca.ValueType;
import net.miginfocom.swing.MigLayout;

/**
 * @author Dr.-Ing. Joachim Schwarte, Helen Hein, Johannes Dippon
 * @version 0.700
 */

public class DeklarationGroupListPanel extends MCAPanel{

	private JLabel lblP20n1 = new JLabel();
	private JTable pdTable 		= new JTable();
	private DefaultTableModel pdTableModel 		= new DefaultTableModel(0,5);
	
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int height = (int) screen.getHeight();
	private int width = (int) screen.getWidth();
	private Font titlefont = new Font ("Tahoma", Font.BOLD, height *22/1000);
	private Font generalfont = new Font ("Tahoma", Font.LAYOUT_LEFT_TO_RIGHT, height *15/1000);

	protected DeklarationGroupListPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		setLayout(new MigLayout("", "[grow]", "[16%][grow]"));	
		add(lblP20n1, "cell 0 0,alignx center,aligny center");
		add(new JScrollPane(pdTable), "cell 0 1,alignx center,aligny top");

	}

	@Override
	public void showSelf() {
		Language l = GUILanguage.getChosenLanguage();
		Locale locale = MultiVaLCA.LANGUAGES.get(l);
		String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
		lblP20n1.setText(bundle.getString("mp472e"));
		pdTableModel.setRowCount(0);
		
		pdTable.setModel(pdTableModel);
		TableColumnModel tcm = pdTable.getColumnModel();
		
		lblP20n1.setFont(titlefont);
		pdTable.setFont(generalfont);
		pdTable.setRowHeight(height *22/1000);
		pdTable.setPreferredScrollableViewportSize(new Dimension(width*40/100, height*50/100));
		JTableHeader header = pdTable.getTableHeader();
		header.setFont(generalfont);
		
		tcm.getColumn(0).setHeaderValue(bundle.getString("p06n1"));
		tcm.getColumn(1).setHeaderValue(bundle.getString("p01n4"));		
		tcm.getColumn(2).setHeaderValue(bundle.getString("mp14"));
		tcm.getColumn(3).setHeaderValue(bundle.getString("p01n3"));
		tcm.getColumn(4).setHeaderValue(bundle.getString("p02n4"));

		pdTable.getColumnModel().getColumn(2).setPreferredWidth(110);


		LinkedHashSet<String> imvgListe = new LinkedHashSet<String>();
		LinkedHashMap<String, MCAObject> instanceListe = new LinkedHashMap<String, MCAObject>();
		imvgListe.addAll(ImpactValueMapGroup.getAllInstances().keySet());
		instanceListe.putAll(ImpactValueMapGroup.getAllInstances());

		for(String imvg : imvgListe) {
			if (IVMGroupType.ProductDeclaration.equals(ImpactValueMapGroup.getInstance(imvg).getType())) {			
			ImpactValueMaps im = (ImpactValueMaps)instanceListe.get(imvg);	
			LinkedHashSet<String> pdl = new LinkedHashSet<String>();
			for (ImpactValueMaps ivml : ImpactValueMapGroup.getInstance(im.getName()).getIVMList()) {
				 pdl.add(ivml.getName());
			}
			String[] str = new String[pdl.size()];
			pdl.toArray(str);
			String str1 = str[0];
			for (int i = 1; i < str.length; i++) {
				  str1 = str1 + ", " + str[i];
				}
			String einheit = ProductDeclaration.getInstance(str[0]).getEinheit().toString(); 
			pdTableModel.addRow(new Object[] {im.getName() + " (" + str1 + ")", einheit});		
			for(ImpactCategory ic : im.getImpactValueMap().keySet()){
				for (ValueType vt : im.getImpactValueMap().get(ic).keySet()) {
					pdTableModel.addRow(new Object[] {"", "", ic.getName(), 
							ValueTypeStringMap.getFVTS(l).get(vt),
							im.getImpactValueMap().get(ic).get(vt)});
				}
				
			}}}	
	}
}
