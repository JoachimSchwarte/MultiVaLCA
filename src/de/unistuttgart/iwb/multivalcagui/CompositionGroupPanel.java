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
import java.util.Set;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import de.unistuttgart.iwb.multivalca.Component;
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

public class CompositionGroupPanel extends MCAPanel{

	private JTextField txt01 = new JTextField();	// Eingabefeld Gruppenname
	private JComboBox<String> cobP23n1 = new JComboBox<String>(); // Eingabefeld Kompositionsname
	private JLabel lbl01 = new JLabel(); 			// "Neue Kompositionsgruppe"
	private JLabel lbl02 = new JLabel(); 			// "Name der Gruppe"
	private JButton btn01 = new JButton(); 			// "neue Gruppe anlegen"
	private JLabel lbl05 = new JLabel(); 			// "Name der hinzuzufügenden Komposition"
	private JButton btn02 = new JButton(); 			// "Komposition zur Gruppe hinzufügen"
	private JButton btn03 = new JButton(); 			// "fertig"
	private JLabel lbl06 = new JLabel(); 			// Status

	private Language l = GUILanguage.getChosenLanguage();
	private Locale locale = MultiVaLCA.LANGUAGES.get(l);
	private String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
	private ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
	private LabeledInputDialog nameNGdi = new LabeledInputDialog(lbl02, txt01);
	private LabeledInputComboBox namePDi = new LabeledInputComboBox(lbl05, cobP23n1);
	
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int height = (int) screen.getHeight();
	private Font titlefont = new Font ("Tahoma", Font.BOLD, height *22/1000);
	private Font generalfont = new Font ("Tahoma", Font.LAYOUT_LEFT_TO_RIGHT, height *15/1000);

	protected CompositionGroupPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI( ) {
		setLayout(new MigLayout("", "[grow][20%][20%][grow]", 
				"[8%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%][grow]"));	
		lbl01.setFont(new Font("Tahoma", Font.BOLD, 14));
		Integer pos=0;
		add(lbl01, "flowy,cell 1 "+pos.toString()+" 2 1,alignx center,aligny center");	
		pos = nameNGdi.draw(pos, this);			
		add(btn01, "cell 1 "+(++pos).toString()+" 2 1,alignx center");	
		pos = namePDi.draw(pos, this);
		add(btn02, "cell 1 "+(++pos).toString()+",alignx center");
		add(btn03, "cell 2 "+pos.toString()+",alignx center");
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
			public void actionPerformed(ActionEvent arg0) {	
				LinkedHashMap<String, Set<String>> testMap1 = new LinkedHashMap<String, Set<String>>();
				testMap1.put(bundle.getString("stat63"), ImpactValueMapGroup.getAllInstances().keySet());
				String name = nameNGdi.getTextNew(testMap1, lbl06, bundle.getString("stat02"), bundle.getString("stat03"));					
				if (!"".equals(name)) {
					ImpactValueMapGroup.instance(name, IVMGroupType.Composition);
					txt01.setEnabled(false);
					btn01.setEnabled(false);
					cobP23n1.setEnabled(true);
					btn02.setEnabled(true);
					lbl06.setText(bundle.getString("stat40") + ImpactValueMapGroup.getAllInstances().get(txt01.getText()).getIVMList().size()
							+ bundle.getString("stat63"));
				}				
			}			
		});
	}

	private void button2() {
		btn02.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = cobP23n1.getSelectedItem().toString();
				boolean nameOk = true;
				if ("".equals(name)) {
					nameOk = false;
					lbl06.setText(bundle.getString("stat02"));
				} 
				if (!Component.getAllInstances().containsKey(name)) {
					lbl06.setText(bundle.getString("stat64"));
					nameOk = false;
				}
				if (nameOk) {
					ImpactValueMapGroup ivmGroup = ImpactValueMapGroup.getAllInstances().get(txt01.getText());
					if ("".equals(ivmGroup.getType().toString())) {
						ivmGroup.setType(IVMGroupType.Composition);
					}
						ivmGroup.addImpactValueMap((ImpactValueMaps) MCAObject.getAllInstances(MCAObject.getClass(name)).get(name));
						lbl06.setText(bundle.getString("stat40") + ivmGroup.getIVMList().size()
								+ bundle.getString("stat63"));
						btn03.setEnabled(true);
						cobP23n1.setSelectedIndex(0);					
					
				}			
			}
		});
	}

	private void button3() {
		btn03.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txt01.setText("");
				txt01.setEnabled(true);
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
		
		lbl01.setFont(titlefont);
		lbl02.setFont(generalfont);
		lbl05.setFont(generalfont);
		lbl06.setFont(generalfont);
		btn01.setFont(generalfont);
		btn02.setFont(generalfont);
		btn03.setFont(generalfont);

		lbl01.setText(bundle.getString("p19n5"));
		lbl02.setText(bundle.getString("p02n8"));
		lbl05.setText(bundle.getString("p19n6"));
		lbl06.setText(bundle.getString("stat01"));
		btn01.setText(bundle.getString("bt16"));
		btn02.setText(bundle.getString("bt21"));
		btn03.setText(bundle.getString("bt04"));

		Vector<String> nameVektor = new Vector<String>();
		nameVektor.addElement("");
		for (String obName : Composition.getAllInstances().keySet()) {
			nameVektor.addElement(obName);
		}			
		cobP23n1.setModel(new DefaultComboBoxModel<String>(nameVektor));
	}




}
