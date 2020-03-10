/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
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
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import de.unistuttgart.iwb.multivalca.ImpactCategory;
import de.unistuttgart.iwb.multivalca.LCIAMethod;
import de.unistuttgart.iwb.multivalca.ObjectType;
import de.unistuttgart.iwb.multivalca.ProcessModule;
import de.unistuttgart.iwb.multivalca.ProductDeclaration;
import de.unistuttgart.iwb.multivalca.ImpactValueMapGroup;
import de.unistuttgart.iwb.multivalca.ProductSystem;
import de.unistuttgart.iwb.multivalca.Component;
import de.unistuttgart.iwb.multivalca.Composition;
import de.unistuttgart.iwb.multivalca.FVMGroupType;
import de.unistuttgart.iwb.multivalca.FlowValueMapGroup;
import de.unistuttgart.iwb.multivalca.IVMGroupType;
import de.unistuttgart.iwb.multivalca.ValueType;
import net.miginfocom.swing.MigLayout;

/**
 * @author Dr.-Ing. Joachim Schwarte, Helen Hein, Johannes Dippon
 * @version 0.700
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
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int width = (int) screen.getWidth();
	private int height = (int) screen.getHeight();
	private Font titlefont = new Font ("Tahoma", Font.BOLD, height *22/1000);
	private Font generalfont = new Font ("Tahoma", Font.LAYOUT_LEFT_TO_RIGHT, height *15/1000);

	protected LciaCalcPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		setLayout(new MigLayout("", "[grow][20%][20%][grow]", 
				"[8%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[grow]"));	
		lblP16n1.setFont(titlefont);
		add(lblP16n1, "cell 1 0 2 1,alignx center,aligny center");		
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
						cobP16n1.getSelectedItem().toString().equals(bundle.getString("mp132"))) {
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
				if (cobP16n1.getSelectedItem().toString().equals(bundle.getString("mp172")) ||
						cobP16n1.getSelectedItem().toString().equals(bundle.getString("mp182")) ||
								cobP16n1.getSelectedItem().toString().equals(bundle.getString("mp192"))) {
					ImpactValueMapGroup akObj = ImpactValueMapGroup.getInstance(cobP16n2.getSelectedItem().toString());
					LCIAMethod akMeth = LCIAMethod.instance(cobP16n3.getSelectedItem().toString());
					for (ImpactCategory wName : akObj.getImpactValueMap(akMeth).keySet()) {					
						waTableModel.addRow(new Object[] {wName.getName(), 
								wName.getEinheit().getName(),
								akObj.getImpactValueMap(akMeth).get(wName).get(vt)});							
					}
				}
				if (cobP16n1.getSelectedItem().toString().equals(bundle.getString("mp18"))) {
					Component akObj = Component.getInstance(cobP16n2.getSelectedItem().toString());
					LCIAMethod akMeth = LCIAMethod.instance(cobP16n3.getSelectedItem().toString());
					for (ImpactCategory wName : akObj.getImpactValueMap(akMeth).keySet()) {					
						waTableModel.addRow(new Object[] {wName.getName(), 
								wName.getEinheit().getName(),
								akObj.getImpactValueMap(akMeth).get(wName).get(vt)});							
					}
				}
				if (cobP16n1.getSelectedItem().toString().equals(bundle.getString("mp19"))) {
					Composition akObj = Composition.getInstance(cobP16n2.getSelectedItem().toString());
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
				if (cobP16n1.getSelectedItem().toString().equals(bundle.getString("mp132"))) {
					Vector<String> nameVektor = new Vector<String>();
					for (String obName : FlowValueMapGroup.getAllInstances().keySet()) {
						if (FVMGroupType.ProductSystem.equals(FlowValueMapGroup.getInstance(obName).getType())) {
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
				if (cobP16n1.getSelectedItem().toString().equals(bundle.getString("mp172"))) {
					Vector<String> nameVektor = new Vector<String>();
					for (String obName : ImpactValueMapGroup.getAllInstances().keySet()) {
						if (IVMGroupType.ProductDeclaration.equals(ImpactValueMapGroup.getInstance(obName).getType()))
						nameVektor.addElement(obName);
					}
					cobP16n2.setModel(new DefaultComboBoxModel<String>(nameVektor));
				}
				if (cobP16n1.getSelectedItem().toString().equals(bundle.getString("mp182"))) {
					Vector<String> nameVektor = new Vector<String>();
					for (String obName : ImpactValueMapGroup.getAllInstances().keySet()) {
						if (IVMGroupType.Component.equals(ImpactValueMapGroup.getInstance(obName).getType()))
						nameVektor.addElement(obName);
					}
					cobP16n2.setModel(new DefaultComboBoxModel<String>(nameVektor));
				}
				if (cobP16n1.getSelectedItem().toString().equals(bundle.getString("mp192"))) {
					Vector<String> nameVektor = new Vector<String>();
					for (String obName : ImpactValueMapGroup.getAllInstances().keySet()) {
						if (IVMGroupType.Composition.equals(ImpactValueMapGroup.getInstance(obName).getType()))
						nameVektor.addElement(obName);
					}
					cobP16n2.setModel(new DefaultComboBoxModel<String>(nameVektor));
				}
				if (cobP16n1.getSelectedItem().toString().equals(bundle.getString("mp18"))) {
					Vector<String> nameVektor = new Vector<String>();
					for (String obName : Component.getAllInstances().keySet()) {
						nameVektor.addElement(obName);
					}
					cobP16n2.setModel(new DefaultComboBoxModel<String>(nameVektor));
				}
				if (cobP16n1.getSelectedItem().toString().equals(bundle.getString("mp19"))) {
					Vector<String> nameVektor = new Vector<String>();
					for (String obName : Composition.getAllInstances().keySet()) {
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
		
		lblP16n1.setFont(titlefont);
		lblP16n2.setFont(generalfont);
		lblP16n3.setFont(generalfont);
		lblP16n4.setFont(generalfont);
		lblP16n5.setFont(generalfont);
		btnP16n1.setFont(generalfont);
		cobP16n1.setFont(generalfont);
		cobP16n2.setFont(generalfont);
		cobP16n3.setFont(generalfont);
		cobP16n4.setFont(generalfont);
		
		lblP16n1.setText(bundle.getString("p16n1"));
		lblP16n2.setText(bundle.getString("p16n2"));
		lblP16n3.setText(bundle.getString("p16n3"));
		lblP16n4.setText(bundle.getString("mp16"));
		lblP16n5.setText(bundle.getString("p16n4"));
		btnP16n1.setText(bundle.getString("bt12"));
		waTableModel.setRowCount(0);
		waTable.setModel(waTableModel);
		TableColumnModel tcm = waTable.getColumnModel();
		waTable.setFont(generalfont);
		waTable.setRowHeight(height *22/1000);
		waTable.setPreferredScrollableViewportSize(new Dimension(width*40/100, height*50/100));
		JTableHeader header = waTable.getTableHeader();
		header.setFont(generalfont);
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
