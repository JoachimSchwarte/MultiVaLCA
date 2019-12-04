/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import de.unistuttgart.iwb.multivalca.ImpactCategory;
import de.unistuttgart.iwb.multivalca.LCIAMethod;
import de.unistuttgart.iwb.multivalca.ObjectType;
import de.unistuttgart.iwb.multivalca.ProcessModule;
import de.unistuttgart.iwb.multivalca.ProductDeclaration;
import de.unistuttgart.iwb.multivalca.ImpactValueMapGroup;
import de.unistuttgart.iwb.multivalca.ProductSystem;
import de.unistuttgart.iwb.multivalca.FVMGroupType;
import de.unistuttgart.iwb.multivalca.FlowValueMapGroup;
import de.unistuttgart.iwb.multivalca.IVMGroupType;
import de.unistuttgart.iwb.multivalca.ValueType;
import net.miginfocom.swing.MigLayout;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.535
 */

public class LciaCalcPanel extends MCAPanel {
	
	private JLabel lblP16n1 = new JLabel();				// "Wirkungsabschätzung"
	private JLabel lblP16n2 = new JLabel();				// "Objekttyp"
	private JLabel lblP16n3 = new JLabel();				// "Objektname"
	private JLabel lblP16n4 = new JLabel();				// "Bewertungsmethode"
	private JLabel lblP16n5 = new JLabel();				// "Werttyp"
	private JButton btnP16n1 = new JButton(); 			// "Berechnungsergebnisse anzeigen
	private JComboBox<String> cobP16n1 = new JComboBox<String>();	// Objekttypen
	private JComboBox<String> cobP16n2 = new JComboBox<String>();	// Objektnamen
	private JComboBox<String> cobP16n3 = new JComboBox<String>();	// Methoden
	private JComboBox<String> cobP16n4 = new JComboBox<String>();	// Werttypen
	private JTable waTable 		= new JTable();
	private DefaultTableModel waTableModel 		= new DefaultTableModel(0,3);
	private Language l = GUILanguage.getChosenLanguage();
	private Locale locale = MultiVaLCA.LANGUAGES.get(l);
	private String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
	private ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);

	protected LciaCalcPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		setLayout(new MigLayout("", "[108px,grow][108px][108px][108px,grow]", 
				"[20px][20px][20px][20px][20px][20px][20px,grow]"));	
		lblP16n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblP16n1, "cell 1 0 2 1,alignx center,aligny top");		
		add(lblP16n2, "cell 1 1,grow");
		add(cobP16n1, "cell 2 1,grow");
		add(lblP16n3, "cell 1 2,grow");
		add(cobP16n2, "cell 2 2,grow");
		add(lblP16n4, "cell 1 3,grow");
		add(cobP16n3, "cell 2 3,grow");
		add(lblP16n5, "cell 1 4,grow");
		add(cobP16n4, "cell 2 4,grow");	
		cobP16n2.setEnabled(false);
		cobP16n3.setEnabled(false);
		cobP16n4.setEnabled(false);		
		btnP16n1.setEnabled(false);
		add(btnP16n1, "cell 1 5 2 0,alignx center");
		add(new JScrollPane(waTable), "cell 1 6 2 0,alignx center,aligny top");
		
		
		btnP16n1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cobP16n2.setEnabled(false);
				cobP16n3.setEnabled(false);
				cobP16n4.setEnabled(false);
				btnP16n1.setEnabled(false);
				ValueType vt = ValueType.MeanValue;
				if (ValueTypeStringMap.getFVTS(l).get(ValueType.UpperBound).equals(cobP16n4.getSelectedItem().toString())) {
					vt = ValueType.UpperBound;
				}
				if (ValueTypeStringMap.getFVTS(l).get(ValueType.LowerBound).equals(cobP16n4.getSelectedItem().toString())) {
					vt = ValueType.LowerBound;
				}
				if (cobP16n1.getSelectedItem().toString().equals(bundle.getString("mp12"))) {
					ProcessModule akObj = ProcessModule.getInstance(cobP16n2.getSelectedItem().toString());
					LCIAMethod akMeth = LCIAMethod.instance(cobP16n3.getSelectedItem().toString());
					for (ImpactCategory wName : akObj.getImpactValueMap(akMeth).keySet()) {
						waTableModel.addRow(new Object[] {wName.getName(), 
								wName.getEinheit().getName(),
								akObj.getImpactValueMap(akMeth).get(wName).get(vt)});
					}
				}
				if (cobP16n1.getSelectedItem().toString().equals(bundle.getString("mp122")) ||
						cobP16n1.getSelectedItem().toString().equals(bundle.getString("mp23"))) {
					FlowValueMapGroup akObj = FlowValueMapGroup.getInstance(cobP16n2.getSelectedItem().toString());
					LCIAMethod akMeth = LCIAMethod.instance(cobP16n3.getSelectedItem().toString());
					for (ImpactCategory wName : akObj.getImpactValueMap(akMeth).keySet()) {
						waTableModel.addRow(new Object[] {wName.getName(), 
								wName.getEinheit().getName(),
								akObj.getImpactValueMap(akMeth).get(wName).get(vt)});
					}
				}
				if (cobP16n1.getSelectedItem().toString().equals(bundle.getString("mp13"))) {
					ProductSystem akObj = ProductSystem.getInstance(cobP16n2.getSelectedItem().toString());
					LCIAMethod akMeth = LCIAMethod.instance(cobP16n3.getSelectedItem().toString());
					for (ImpactCategory wName : akObj.getImpactValueMap(akMeth).keySet()) {					
						waTableModel.addRow(new Object[] {wName.getName(), 
								wName.getEinheit().getName(),
								akObj.getImpactValueMap(akMeth).get(wName).get(vt)});							
					}
				}
				if (cobP16n1.getSelectedItem().toString().equals(bundle.getString("mp17"))) {
					ProductDeclaration akObj = ProductDeclaration.getInstance(cobP16n2.getSelectedItem().toString());
					LCIAMethod akMeth = LCIAMethod.instance(cobP16n3.getSelectedItem().toString());
					for (ImpactCategory wName : akObj.getImpactValueMap(akMeth).keySet()) {					
						waTableModel.addRow(new Object[] {wName.getName(), 
								wName.getEinheit().getName(),
								akObj.getImpactValueMap(akMeth).get(wName).get(vt)});							
					}
				}
				if (cobP16n1.getSelectedItem().toString().equals(bundle.getString("mp22"))) {
					ImpactValueMapGroup akObj = ImpactValueMapGroup.getInstance(cobP16n2.getSelectedItem().toString());
					LCIAMethod akMeth = LCIAMethod.instance(cobP16n3.getSelectedItem().toString());
					for (ImpactCategory wName : akObj.getImpactValueMap(akMeth).keySet()) {					
						waTableModel.addRow(new Object[] {wName.getName(), 
								wName.getEinheit().getName(),
								akObj.getImpactValueMap(akMeth).get(wName).get(vt)});							
					}
				}
				

			}
		});	
	
		combobox1();
		combobox2();
		combobox3();
		combobox4();
	}

	private void combobox1() {
		cobP16n1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cobP16n2.setEnabled(true);
				cobP16n3.setEnabled(false);
				waTableModel.setRowCount(0);
				if (cobP16n1.getSelectedItem().toString().equals(bundle.getString("mp12"))) {
					Vector<String> nameVektor = new Vector<String>();
					for (String obName : ProcessModule.getAllInstances().keySet()) {
						nameVektor.addElement(obName);
					}
					cobP16n2.setModel(new DefaultComboBoxModel<String>(nameVektor));
				}
				if (cobP16n1.getSelectedItem().toString().equals(bundle.getString("mp122"))) {
					Vector<String> nameVektor = new Vector<String>();
					for (String obName : FlowValueMapGroup.getAllInstances().keySet()) {
						if (FVMGroupType.ProcessModule.equals(FlowValueMapGroup.getInstance(obName).getType())) {
							nameVektor.addElement(obName);
						}						
					}
					cobP16n2.setModel(new DefaultComboBoxModel<String>(nameVektor));
				}
				if (cobP16n1.getSelectedItem().toString().equals(bundle.getString("mp23"))) {
					Vector<String> nameVektor = new Vector<String>();
					for (String obName : FlowValueMapGroup.getAllInstances().keySet()) {
						if (FVMGroupType.Subsystem.equals(FlowValueMapGroup.getInstance(obName).getType())) {
							nameVektor.addElement(obName);
						}						
					}
					cobP16n2.setModel(new DefaultComboBoxModel<String>(nameVektor));
				}
				if (cobP16n1.getSelectedItem().toString().equals(bundle.getString("mp13"))) {
					Vector<String> nameVektor = new Vector<String>();
					for (String obName : ProductSystem.getAllInstances().keySet()) {
						nameVektor.addElement(obName);
					}
					cobP16n2.setModel(new DefaultComboBoxModel<String>(nameVektor));
				}
				if (cobP16n1.getSelectedItem().toString().equals(bundle.getString("mp17"))) {
					Vector<String> nameVektor = new Vector<String>();
					for (String obName : ProductDeclaration.getAllInstances().keySet()) {
						nameVektor.addElement(obName);
					}
					cobP16n2.setModel(new DefaultComboBoxModel<String>(nameVektor));
				}
				if (cobP16n1.getSelectedItem().toString().equals(bundle.getString("mp22"))) {
					Vector<String> nameVektor = new Vector<String>();
					for (String obName : ImpactValueMapGroup.getAllInstances().keySet()) {
						if (IVMGroupType.ProductDeclaration.equals(ImpactValueMapGroup.getInstance(obName).getType()))
						nameVektor.addElement(obName);
					}
					cobP16n2.setModel(new DefaultComboBoxModel<String>(nameVektor));
				}
			}		
		});
	}

	private void combobox2() {
		cobP16n2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Vector<String> methVektor = new Vector<String>();
				for (String methName : LCIAMethod.getAllInstances().keySet()) {
					methVektor.addElement(methName);
				}
				cobP16n3.setModel(new DefaultComboBoxModel<String>(methVektor));
				cobP16n3.setEnabled(true);
			}
		});
	}

	private void combobox3() {
		cobP16n3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cobP16n4.setEnabled(true);
			}
		});	
	}

	private void combobox4() {
		cobP16n4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnP16n1.setEnabled(true);
			}
		});
	}

	@Override
	public void showSelf() {
		l = GUILanguage.getChosenLanguage();
		locale = MultiVaLCA.LANGUAGES.get(l);
		baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		bundle = ResourceBundle.getBundle(baseName, locale);
		lblP16n1.setText(bundle.getString("p16n1"));
		lblP16n2.setText(bundle.getString("p16n2"));
		lblP16n3.setText(bundle.getString("p16n3"));
		lblP16n4.setText(bundle.getString("mp16"));
		lblP16n5.setText(bundle.getString("p16n4"));
		btnP16n1.setText(bundle.getString("bt12"));
		waTableModel.setRowCount(0);
		waTable.setModel(waTableModel);
		TableColumnModel tcm = waTable.getColumnModel();
		tcm.getColumn(0).setHeaderValue(bundle.getString("mp14"));
		tcm.getColumn(1).setHeaderValue(bundle.getString("p11n1"));
		tcm.getColumn(2).setHeaderValue(bundle.getString("p16n5"));
		LinkedHashMap<ObjectType, String> ot = ObjectTypeStringMap.getOTS(l);
		String[] ota = new String[ot.size()];
		int i=0;
		for (ObjectType o : ObjectType.values()) {
			String otl = ot.get(o);
			ota[i] = otl;
			i++;
		}
		cobP16n1.setModel(new DefaultComboBoxModel<String>(ota));
		
		LinkedHashMap<ValueType, String> vt = ValueTypeStringMap.getFVTS(l);
		String[] vta = new String[vt.size()];		
		i=0;
		for (ValueType v : ValueType.values()) {
			String vtl = vt.get(v);
			vta[i] = vtl;
			i++;
		}
		cobP16n4.setModel(new DefaultComboBoxModel<String>(vta));
		cobP16n2.setEnabled(false);
		cobP16n3.setEnabled(false);
		cobP16n4.setEnabled(false);		
		btnP16n1.setEnabled(false);
	}
}
