/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
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

import de.unistuttgart.iwb.multivalca.Flow;
import de.unistuttgart.iwb.multivalca.FlowType;
import de.unistuttgart.iwb.multivalca.ProcessModule;
//import de.unistuttgart.iwb.multivalca.ProcessModuleGroup;
import net.miginfocom.swing.MigLayout;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.553
 */

public class ProModGroupPanel extends MCAPanel{
	
	private JTextField txt01 = new JTextField();	// Eingabefeld Gruppenname
	private JTextField txt02 = new JTextField();	// Eingabefeld Flussname
	private JTextField txt03 = new JTextField();	// Eingabefeld Menge
	private JTextField txt04 = new JTextField();	// Eingabefeld Modulname
	private JLabel lbl01 = new JLabel(); 			// "Neue Prozessmodulgruppe"
	private JLabel lbl02 = new JLabel(); 			// "Name der Gruppe"
	private JLabel lbl03 = new JLabel(); 			// "relevanter Produktfluss"
	private JLabel lbl04 = new JLabel(); 			// "Menge"
	private JButton btn01 = new JButton(); 			// "neue Gruppe anlegen"
	private JLabel lbl05 = new JLabel(); 			// "Name des hinzuzufügenden Moduls"
	private JButton btn02 = new JButton(); 			// "Modul zur Gruppe hinzufügen"
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
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int height = (int) screen.getHeight();
	private Font titlefont = new Font ("Tahoma", Font.BOLD, height *22/1000);
	private Font generalfont = new Font ("Tahoma", Font.LAYOUT_LEFT_TO_RIGHT, height *15/1000);

	protected ProModGroupPanel(String key) {
		super(key);
		initUI();
	}
	
	private void initUI( ) {
		setLayout(new MigLayout("", "[grow][20%][20%][grow]", 
				"[8%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%][grow]"));		
		lbl01.setFont(titlefont);
		Integer pos=0;
		add(lbl01, "flowy,cell 1 "+pos.toString()+" 2 1,alignx center,aligny center");	
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
			//	testMap1.put(bundle.getString("stat44"), ProcessModuleGroup.getAllInstances().keySet());
				String name = nameNGdi.getTextNew(testMap1, lbl06, bundle.getString("stat02"), bundle.getString("stat03"));	
	
				if (inputOK && !"".equals(flname) && !"".equals(name)) {
				//	ProcessModuleGroup.createInstance(name, Flow.getInstance(flname), menge);
					txt01.setEnabled(false);
					txt02.setEnabled(false);
					txt03.setEnabled(false);
					btn01.setEnabled(false);
					txt04.setEnabled(true);
					btn02.setEnabled(true);
				//	lbl06.setText(bundle.getString("stat40") + ProcessModuleGroup.getAllInstances().get(txt01.getText()).getModList().size()
				//			+ bundle.getString("stat41"));
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
				if (nameOk && !ProcessModule.getAllInstances().containsKey(name)) {
					nameOk = false;
					lbl06.setText(bundle.getString("stat38"));
				}
				if (nameOk && !ProcessModule.getAllInstances().get(name).getProduktflussvektor().containsKey(Flow.getAllInstances().get(flname))) {
					nameOk = false;
					lbl06.setText(bundle.getString("stat39"));					
				}
				if (nameOk) {
				//	ProcessModuleGroup.getAllInstances().get(txt01.getText()).addModule(ProcessModule.getAllInstances().get(txt04.getText()));
				//	lbl06.setText(bundle.getString("stat40") + ProcessModuleGroup.getAllInstances().get(txt01.getText()).getModList().size()
			//				+ bundle.getString("stat41"));
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
		txt01.setFont(generalfont);
		txt02.setFont(generalfont);
		txt03.setFont(generalfont);
		txt04.setFont(generalfont);
		lbl01.setFont(titlefont);
		lbl02.setFont(generalfont);
		lbl03.setFont(generalfont);
		lbl04.setFont(generalfont);
		btn01.setFont(generalfont);
		lbl05.setFont(generalfont);
		btn02.setFont(generalfont);
		btn03.setFont(generalfont);
		lbl06.setFont(generalfont);
		
		lbl01.setText(bundle.getString("p20n1"));
		lbl02.setText(bundle.getString("p20n2"));
		lbl03.setText(bundle.getString("p20n3"));
		lbl04.setText(bundle.getString("p02n4"));
		lbl05.setText(bundle.getString("p20n4"));
		lbl06.setText(bundle.getString("stat01"));
		btn01.setText(bundle.getString("bt16"));
		btn02.setText(bundle.getString("bt17"));
		btn02.setText(bundle.getString("bt17"));
		btn03.setText(bundle.getString("bt04"));
		
	}

}
