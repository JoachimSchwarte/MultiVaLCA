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
import java.util.Set;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import de.unistuttgart.iwb.multivalca.ImpactValueMaps;
import de.unistuttgart.iwb.multivalca.MCAObject;
import de.unistuttgart.iwb.multivalca.ProductDeclaration;
import de.unistuttgart.iwb.multivalca.ProductDeclarationGroup;
import de.unistuttgart.iwb.multivalca.ProductSystem;
import net.miginfocom.swing.MigLayout;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.553
 */

public class DeclarationGroupPanel extends MCAPanel{

	private JTextField txt01 = new JTextField();	// Eingabefeld Gruppenname
//	private JTextField txt04 = new JTextField();	// Eingabefeld Produktdeclarationname
	private JComboBox<String> cobP23n1 = new JComboBox<String>(); // Eingabefeld Produktdeklarationname
	private JLabel lbl01 = new JLabel(); 			// "Neue Produktdeklarationsgruppe"
	private JLabel lbl02 = new JLabel(); 			// "Name der Gruppe"
	private JButton btn01 = new JButton(); 			// "neue Gruppe anlegen"
	private JLabel lbl05 = new JLabel(); 			// "Name der hinzuzufügenden Porduktdeklaration"
	private JButton btn02 = new JButton(); 			// "Modul zur Gruppe hinzufügen"
	private JButton btn03 = new JButton(); 			// "fertig"
	private JLabel lbl06 = new JLabel(); 			// Status
	
	private Language l = GUILanguage.getChosenLanguage();
	private Locale locale = MultiVaLCA.LANGUAGES.get(l);
	private String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
	private ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
	private LabeledInputDialog nameNGdi = new LabeledInputDialog(lbl02, txt01);
//	private LabeledInputDialog nameHMdi = new LabeledInputDialog(lbl05, txt04);
	private LabeledInputComboBox namePDi = new LabeledInputComboBox(lbl05, cobP23n1);

	protected DeclarationGroupPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI( ) {
		setLayout(new MigLayout("", "[108px,grow][108px][108px][108px,grow]", 
				"[20px][20px][20px][20px][20px][20px][20px][20px][20px][20px][20px][20px,grow]"));		
		lbl01.setFont(new Font("Tahoma", Font.BOLD, 14));
		Integer pos=0;
		add(lbl01, "flowy,cell 1 "+pos.toString()+" 2 1,alignx center,aligny top");	
		pos = nameNGdi.draw(pos, this);			
		add(btn01, "cell 1 "+(++pos).toString()+" 2 1,alignx center");	
//		pos = nameHMdi.draw(pos, this);
		pos = namePDi.draw(pos, this);
		add(btn02, "cell 1 "+(++pos).toString()+",alignx center");
		add(btn03, "cell 2 "+pos.toString()+",alignx center");
//		txt04.setEnabled(false);
		cobP23n1.setEnabled(false);
		btn02.setEnabled(false);	
		btn03.setEnabled(false);
		add(lbl06, "cell 0 "+(++pos).toString()+" 4 1,alignx center");



		button1();
		button2();
		button3();
	}



	private void button1() {
		btn01.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {//	
				LinkedHashMap<String, Set<String>> testMap1 = new LinkedHashMap<String, Set<String>>();
				testMap1.put(bundle.getString("stat46"), ProductDeclarationGroup.getAllInstances().keySet());
				String name = nameNGdi.getTextNew(testMap1, lbl06, bundle.getString("stat02"), bundle.getString("stat03"));	
				//	
				if (!"".equals(name)) {
					ProductDeclarationGroup.instance(name);
					txt01.setEnabled(false);
					btn01.setEnabled(false);
					cobP23n1.setEnabled(true);
//					txt04.setEnabled(true);
					btn02.setEnabled(true);
					lbl06.setText(bundle.getString("stat40") + ProductDeclarationGroup.getAllInstances().get(txt01.getText()).getDecList().size()
							+ bundle.getString("stat47"));
				}				
			}			
		});
	}

	private void button2() {
		btn02.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				String name = txt04.getText();	
				String name = cobP23n1.getSelectedItem().toString();
				boolean nameOk = true;
				if ("".equals(name)) {
					nameOk = false;
					lbl06.setText(bundle.getString("stat02"));
				} 
				if (!ProductSystem.getAllInstances().containsKey(name) &&
						!ProductDeclaration.getAllInstances().containsKey(name) &&
						!ProductDeclarationGroup.getAllInstances().containsKey(name) 
						//							&& !ProcessModule.getAllInstances().containsKey(name) &&
						//									!ProcessModuleGroup.getAllInstances().containsKey(name)
						) {
					lbl06.setText(bundle.getString("stat48"));
					nameOk = false;
				}

				if (nameOk) {
					ProductDeclarationGroup.getAllInstances().get(txt01.getText()).addDeclaration((ImpactValueMaps) MCAObject.getAllInstances(MCAObject.getClass(name)).get(name));
					lbl06.setText(bundle.getString("stat40") + ProductDeclarationGroup.getAllInstances().get(txt01.getText()).getDecList().size()
							+ bundle.getString("stat47"));
					btn03.setEnabled(true);
//					txt04.setText("");
					cobP23n1.setSelectedIndex(0);
				}			
			}
		});
	}

	private void button3() {
		btn03.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txt01.setText("");
//				txt04.setText("");
				txt01.setEnabled(true);
//				txt04.setEnabled(false);
				cobP23n1.setEnabled(false);
				btn01.setEnabled(true);
				btn02.setEnabled(false);
				btn03.setEnabled(false);
				lbl06.setText(bundle.getString("stat01"));

			}
		});
	}

	@Override
	public void showSelf() {
		l = GUILanguage.getChosenLanguage();
		locale = MultiVaLCA.LANGUAGES.get(l);
		baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		bundle = ResourceBundle.getBundle(baseName, locale);
		lbl01.setText(bundle.getString("p22n1"));
		lbl02.setText(bundle.getString("p20n2"));
		lbl05.setText(bundle.getString("p22n4"));
		lbl06.setText(bundle.getString("stat01"));
		btn01.setText(bundle.getString("bt16"));
		btn02.setText(bundle.getString("bt18"));
		btn03.setText(bundle.getString("bt04"));

		Vector<String> nameVektor = new Vector<String>();
		nameVektor.addElement("");
		for (String obName : ProductDeclaration.getAllInstances().keySet()) {
			nameVektor.addElement(obName);
		}			
		for (String obName : ProductDeclarationGroup.getAllInstances().keySet()) {
			nameVektor.addElement(obName);
		}			
		for (String obName : ProductSystem.getAllInstances().keySet()) {
			nameVektor.addElement(obName);
		}
		cobP23n1.setModel(new DefaultComboBoxModel<String>(nameVektor));
	}




}
