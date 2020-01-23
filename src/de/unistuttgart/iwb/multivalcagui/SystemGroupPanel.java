/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import de.unistuttgart.iwb.multivalca.FVMGroupType;
import de.unistuttgart.iwb.multivalca.Flow;
import de.unistuttgart.iwb.multivalca.FlowType;
import de.unistuttgart.iwb.multivalca.ProductSystem;
import de.unistuttgart.iwb.multivalca.FlowValueMapGroup;
import de.unistuttgart.iwb.multivalca.FlowValueMaps;
import net.miginfocom.swing.MigLayout;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.553
 */

public class SystemGroupPanel extends MCAPanel{
	
	private JTextField txt01 = new JTextField();	// Eingabefeld Gruppenname
	private JTextField txt02 = new JTextField();	// Eingabefeld Flussname
	private JTextField txt03 = new JTextField();	// Eingabefeld Menge
	private JTextField txt04 = new JTextField();	// Eingabefeld Modul-/Subsystemname
	private JLabel lbl01 = new JLabel(); 			// "Neue Subsystemgruppe"
	private JLabel lbl02 = new JLabel(); 			// "Name der Gruppe"
	private JLabel lbl03 = new JLabel(); 			// "relevanter Produktfluss"
	private JLabel lbl04 = new JLabel(); 			// "Menge"
	private JButton btn01 = new JButton(); 			// "neue Gruppe anlegen"
	private JLabel lbl05 = new JLabel(); 			// "Name des hinzuzufügenden Subsystem"
	private JButton btn02 = new JButton(); 			// "Modul/Subsystem zur Gruppe hinzufügen"
	private JButton btn03 = new JButton(); 			// "fertig"
	private JLabel lbl06 = new JLabel(); 			// Status
	private Language l = GUILanguage.getChosenLanguage();
	private Locale locale = MultiVaLCA.LANGUAGES.get(l);
	private String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
	private ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
	private LabeledInputDialog nameNGdi = new LabeledInputDialog(lbl02, txt01);
	private LabeledInputDialog nameRPdi = new LabeledInputDialog(lbl03, txt02);
	private LabeledInputDialog wertMEdi = new LabeledInputDialog(lbl04, txt03);
	private LabeledInputDialog nameHMdi = new LabeledInputDialog(lbl05, txt04);

	protected SystemGroupPanel(String key) {
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
		pos = nameRPdi.draw(pos, this);		
		pos = wertMEdi.draw(pos, this);	
		add(btn01, "cell 1 "+(++pos).toString()+" 2 1,alignx center");	
		pos = nameHMdi.draw(pos, this);	
		add(btn02, "cell 1 "+(++pos).toString()+",alignx center");
		add(btn03, "cell 2 "+pos.toString()+",alignx center");
		txt04.setEnabled(false);	
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
				boolean inputOK = true;
				Double menge = 0.0;
				if (wertMEdi.isPosNum(lbl06, bundle.getString("stat07"), bundle.getString("stat26"))) {
					menge = wertMEdi.getNum(lbl06, bundle.getString("stat07"), bundle.getString("stat26"));
				}
				else {
					inputOK = false;
				}
				Set<String> testSet1 = Flow.getAllInstances().keySet();
				HashSet<String> testSet2 = new HashSet<String>();
				for (String flowname : testSet1) {
					if (Flow.getInstance(flowname).getType()==FlowType.Product) {
						testSet2.add(flowname);
					}							
				}
				String flname = nameRPdi.getTextOld(bundle.getString("stat37"), testSet2, lbl06, bundle.getString("stat07"));
	
				LinkedHashMap<String, Set<String>> testMap1 = new LinkedHashMap<String, Set<String>>();
				testMap1.put(bundle.getString("stat441"), FlowValueMapGroup.getAllInstances().keySet());
				String name = nameNGdi.getTextNew(testMap1, lbl06, bundle.getString("stat02"), bundle.getString("stat03"));	
	
				if (inputOK && !"".equals(flname) && !"".equals(name)) {
					FlowValueMapGroup.createInstance(name, FVMGroupType.ProductSystem, Flow.getInstance(flname), menge);
					txt01.setEnabled(false);
					txt02.setEnabled(false);
					txt03.setEnabled(false);
					btn01.setEnabled(false);
					txt04.setEnabled(true);
					btn02.setEnabled(true);
					lbl06.setText(bundle.getString("stat40") + FlowValueMapGroup.getAllInstances().get(txt01.getText()).getFVMList().size()
							+ bundle.getString("stat41"));
				}				
			}			
		});
	}

	private void button2() {
		btn02.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = txt04.getText();	
				String flname = txt02.getText();
				boolean nameOk = true;
				if ("".equals(name)) {
					nameOk = false;
					lbl06.setText(bundle.getString("stat02"));
				} 
				if (nameOk && !ProductSystem.getAllInstances().containsKey(name)) {
					nameOk = false;
					lbl06.setText(bundle.getString("stat381"));
				}				
				if (nameOk && !ProductSystem.getAllInstances().get(name).getProduktflussvektor().containsKey(Flow.getAllInstances().get(flname))) {
					nameOk = false;
					lbl06.setText(bundle.getString("stat391"));					
				}
				
				if (nameOk) {
					FlowValueMapGroup.getAllInstances().get(txt01.getText()).addFlowValueMap((FlowValueMaps)ProductSystem.getAllInstances().get(txt04.getText()));
					lbl06.setText(bundle.getString("stat40") + FlowValueMapGroup.getAllInstances().get(txt01.getText()).getFVMList().size()
							+ bundle.getString("stat41"));
					btn03.setEnabled(true);
					txt04.setText("");
				}	
			}
		});
	}
	
	private void button3() {
		btn03.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txt01.setText("");
				txt02.setText("");
				txt03.setText("");
				txt04.setText("");
				txt01.setEnabled(true);
				txt02.setEnabled(true);
				txt03.setEnabled(true);
				txt04.setEnabled(false);
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
		lbl01.setText(bundle.getString("p03n7"));
		lbl02.setText(bundle.getString("p02n8"));
		lbl03.setText(bundle.getString("p02n9"));
		lbl04.setText(bundle.getString("p02n4"));
		lbl05.setText(bundle.getString("p03n8"));
		lbl06.setText(bundle.getString("stat01"));
		btn01.setText(bundle.getString("bt16"));
		btn02.setText(bundle.getString("bt18"));
		btn03.setText(bundle.getString("bt04"));
		
	}

}
