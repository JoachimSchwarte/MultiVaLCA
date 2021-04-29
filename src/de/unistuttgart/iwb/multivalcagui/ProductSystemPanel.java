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
import java.util.LinkedList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import de.unistuttgart.iwb.multivalca.FVMGroupType;
import de.unistuttgart.iwb.multivalca.Flow;
import de.unistuttgart.iwb.multivalca.FlowValueMapGroup;
import de.unistuttgart.iwb.multivalca.FlowValueMaps;
import de.unistuttgart.iwb.multivalca.MCAObject;
import de.unistuttgart.iwb.multivalca.ProcessModule;
import de.unistuttgart.iwb.multivalca.ProductSystem;
import net.miginfocom.swing.MigLayout;

/**
 * @author Dr.-Ing. Joachim Schwarte, Helen Hein, Johannes Dippon
 * @version 0.700
 */

public class ProductSystemPanel extends MCAPanel{
	
	private JTextField txtP03n1 = new JTextField();		// Eingabefeld Name des Systems
	private JTextField txtP03n2 = new JTextField();		// Eingabefeld Modulname
	private JTextField txtP03n3 = new JTextField();		// Eingabefeld Produkt im Bedarfsvektor
	private JTextField txtP03n4 = new JTextField();		// Eingabefeld Menge des Produkts im Bedarfsvektor
	private JTextField txtP03n5 = new JTextField();		// Eingabefeld Vor- und Koppelpr.
	private JLabel lblP03n1 = new JLabel();				// "Neues Produktsystem"
	private JLabel lblP03n2 = new JLabel();				// "Name des Produktsystems"
	private JLabel lblP03n3 = new JLabel();				// "Prozessmodul/Subsystem"
	private JLabel lblP03n4 = new JLabel();				// "Produkt im Bedarfsvektor"
	private JLabel lblP03n5 = new JLabel();				// "Menge"
	private JLabel lblP03n6 = new JLabel();				// "Vor- oder Koppelprodukt"
	private JLabel lblP03n7 = new JLabel(); 			// Status
	private JButton btnP03n1 = new JButton();			// "neues Produktsystem anlegen"
	private JButton btnP03n2 = new JButton();			// "Modul/System hinzufügen"
	private JButton btnP03n3 = new JButton();			// "weiter"
	private JButton btnP03n4 = new JButton();			// "Bedarfsvektor ergänzen"
	private JButton btnP03n5 = new JButton();			// "weiter"
	private JButton btnP03n6 = new JButton();			// "VK-Flow hinzufügen"
	private JButton btnP03n7 = new JButton();			// "fertig"
	private Language l = GUILanguage.getChosenLanguage();
	private Locale locale = MultiVaLCA.LANGUAGES.get(l);
	private String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
	private ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
	private LabeledInputDialog namePSdi = new LabeledInputDialog(lblP03n2, txtP03n1);
	private LabeledInputDialog nameMOdi = new LabeledInputDialog(lblP03n3, txtP03n2);
	private LabeledInputDialog nameBVdi = new LabeledInputDialog(lblP03n4, txtP03n3);
	private LabeledInputDialog wertBVdi = new LabeledInputDialog(lblP03n5, txtP03n4);
	private LabeledInputDialog nameVKdi = new LabeledInputDialog(lblP03n6, txtP03n5);
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int height = (int) screen.getHeight();
	private Font titlefont = new Font ("Tahoma", Font.BOLD, height *22/1000);
	private Font generalfont = new Font ("Tahoma", Font.LAYOUT_LEFT_TO_RIGHT, height *15/1000);

	protected ProductSystemPanel(String key) {
		super(key);
		initUI();
	}
		
		private void initUI() {
		setLayout(new MigLayout("", "[grow][20%][20%][grow]", 
				"[8%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%][grow]"));		
		lblP03n1.setFont(titlefont);
		Integer pos=0;
		add(lblP03n1, "flowy,cell 1 "+pos.toString()+" 2 1,alignx center,aligny center");	
		pos = namePSdi.draw(pos, this);			
		add(btnP03n1, "cell 1 "+(++pos).toString()+" 2 1,alignx center");
		pos = nameMOdi.draw(pos, this);	
		txtP03n2.setEnabled(false);		
		btnP03n2.setEnabled(false);
		add(btnP03n2, "cell 1 "+(++pos).toString()+",alignx center");		
		btnP03n3.setEnabled(false);
		add(btnP03n3, "cell 2 "+pos.toString()+",alignx center");	
		pos = nameBVdi.draw(pos, this);
		pos = wertBVdi.draw(pos, this);		
		txtP03n3.setEnabled(false);		
		txtP03n4.setEnabled(false);		
		btnP03n4.setEnabled(false);
		add(btnP03n4, "cell 1 "+(++pos).toString()+",alignx center");		
		btnP03n5.setEnabled(false);
		add(btnP03n5, "cell 2 "+pos.toString()+",alignx center");	
		pos = nameVKdi.draw(pos, this);
		txtP03n5.setEnabled(false);		
		btnP03n6.setEnabled(false);
		add(btnP03n6, "cell 1 "+(++pos).toString()+",alignx center");	
		btnP03n7.setEnabled(false);
		add(btnP03n7, "cell 2 "+pos.toString()+",alignx center");		
		add(lblP03n7, "cell 0 "+(++pos).toString()+" 4 1,alignx center");
		
		button1();
		button2();
		button3();
		button4();
		button5();
		button6();
		button7();	
	}

	private void button1() {
		btnP03n1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				LinkedHashMap<String, Set<String>> testMap1 = new LinkedHashMap<String, Set<String>>();
				testMap1.put(bundle.getString("stat43"), ProcessModule.getAllInstances().keySet());
				HashSet<String> test1 = new HashSet<String>();
				HashSet<String> test2 = new HashSet<String>();
				for(String fvmname : FlowValueMapGroup.getAllInstances().keySet()) {
					if (FVMGroupType.ProcessModule.equals(FlowValueMapGroup.getInstance(fvmname).getType())) {
						test1.add(fvmname);
					}
					if (FVMGroupType.ProductSystem.equals(FlowValueMapGroup.getInstance(fvmname).getType())) {
						test2.add(fvmname);
					}
				}
				testMap1.put(bundle.getString("stat44"), test1);
				testMap1.put(bundle.getString("stat441"), test2);
				testMap1.put(bundle.getString("stat45"), ProductSystem.getAllInstances().keySet());
				String name = namePSdi.getTextNew(testMap1, lblP03n7, bundle.getString("stat02"), bundle.getString("stat03"));
				if (!"".equals(name)) {
					ProductSystem.instance(name, new LinkedHashMap<Flow, Double>(), 
							new LinkedList<Flow>());
					lblP03n7.setText(bundle.getString("stat12") + 
							ProductSystem.getAllInstances().size() + bundle.getString("stat05"));

					btnP03n1.setEnabled(false);
					txtP03n1.setEnabled(false);
					btnP03n2.setEnabled(true);
					txtP03n2.setEnabled(true);
				}				
			}
		});		
	}
	
	private void button2() {
		btnP03n2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String modname = txtP03n2.getText();
				if ("".equals(modname) || modname.equals(txtP03n1.getText()) ||
						ProductSystem.getAllInstances().get(txtP03n1.getText()).getModulliste().contains(ProcessModule.getInstance(modname))) {
					if ("".equals(modname)) {
						lblP03n7.setText(bundle.getString("stat07"));
					}
					if (modname.equals(txtP03n1.getText())) {
						lblP03n7.setText(bundle.getString("stat13"));
					}
					if (ProductSystem.getAllInstances().get(txtP03n1.getText()).getModulliste().contains(ProcessModule.getInstance(modname)) ) {
						lblP03n7.setText(bundle.getString("stat31"));
					}
				} else {
					boolean nameVorhanden = false;
					for(String modn2 : ProcessModule.getAllInstances().keySet()) {
						if (modname.equals(modn2)) {
							nameVorhanden = true;
						}
					}
					if (!nameVorhanden) {
						for(String modn3 : ProductSystem.getAllInstances().keySet()) {
							if (modname.equals(modn3)) {
								nameVorhanden = true;
							}
						}
					}
					if (!nameVorhanden) {
						for(String modn4 : FlowValueMapGroup.getAllInstances().keySet()) {
							if (modname.equals(modn4)) {
								nameVorhanden = true;
							}
						}
					}
					if (nameVorhanden) {
						ProductSystem.getAllInstances().get(txtP03n1.getText()).addProzessmodul((FlowValueMaps) MCAObject.getAllInstances(MCAObject.getClass(modname)).get(modname));
						lblP03n7.setText(bundle.getString("stat14") + txtP03n1.getText() +
								bundle.getString("stat15") + ProductSystem.getAllInstances().get(txtP03n1.getText()).getModulAnzahl() 
								+ bundle.getString("stat16"));
						txtP03n2.setText("");
						btnP03n3.setEnabled(true);
					} else {
						lblP03n7.setText(bundle.getString("stat17"));
					}					
				}
			}
		});		
	}
	
	private void button3() {
		btnP03n3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				txtP03n2.setEnabled(false);
				txtP03n2.setText("");
				btnP03n2.setEnabled(false);
				btnP03n3.setEnabled(false);
				txtP03n3.setEnabled(true);
				txtP03n4.setEnabled(true);
				btnP03n4.setEnabled(true);
				lblP03n7.setText(bundle.getString("stat01"));
			}
		});		
	}

	private void button4() {
		btnP03n4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String fname = txtP03n3.getText();
				String fmenge = txtP03n4.getText();
				Double menge;
				try {
					menge = Double.parseDouble(fmenge);
				} catch (NumberFormatException e){
					menge = 0.0;
				}
				if ("".equals(fname) || (menge == 0.0)) {
					lblP03n7.setText(bundle.getString("stat07"));
				} else {
					if (Flow.containsName(fname)) {
						Flow akFlow = Flow.getInstance(fname);
						ProductSystem.getInstance(txtP03n1.getText()).addBedarf(akFlow, Double.parseDouble(txtP03n4.getText()));
						lblP03n7.setText(bundle.getString("stat18") + 
								ProductSystem.getInstance(txtP03n1.getText()).getBedarfsvektor().size() + bundle.getString("stat10"));
						btnP03n5.setEnabled(true);	
						txtP03n3.setText("");
						txtP03n4.setText("");
					} else {
						lblP03n7.setText(bundle.getString("stat11"));
					}					
				}
			}
		});		
	}
	
	private void button5() {
		btnP03n5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				txtP03n2.setEnabled(false);
				txtP03n2.setText("");
				btnP03n2.setEnabled(false);
				btnP03n3.setEnabled(false);
				txtP03n3.setEnabled(false);
				txtP03n4.setEnabled(false);
				btnP03n5.setEnabled(false);
				txtP03n3.setText("");
				txtP03n4.setText("");
				btnP03n4.setEnabled(false);
				txtP03n5.setEnabled(true);
				btnP03n6.setEnabled(true);
				btnP03n7.setEnabled(true);
				
				lblP03n7.setText(bundle.getString("stat01"));
			}
		});		
	}
	
	private void button6() {
		btnP03n6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String vkname = txtP03n5.getText();
				if ("".equals(vkname)) {
					lblP03n7.setText(bundle.getString("stat07"));
				} else {					
					if (Flow.containsName(vkname)) {
						Flow akFlow = Flow.getInstance(vkname);
						ProductSystem.getInstance(txtP03n1.getText()).addVuK(akFlow);
						txtP03n5.setText("");
						lblP03n7.setText(bundle.getString("stat19") + 
								ProductSystem.getInstance(txtP03n1.getText()).getVorUndKoppelprodukte().size() + bundle.getString("stat10"));										
					} else {
						lblP03n7.setText(bundle.getString("stat11"));
					}					
				}
			}
		});		
	}
	
	private void button7() {
		btnP03n7.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				txtP03n1.setEnabled(true);
				txtP03n1.setText("");
				btnP03n1.setEnabled(true);
				txtP03n2.setEnabled(false);
				txtP03n2.setText("");
				btnP03n2.setEnabled(false);
				btnP03n3.setEnabled(false);
				txtP03n3.setEnabled(false);
				txtP03n4.setEnabled(false);
				btnP03n5.setEnabled(false);
				txtP03n3.setText("");
				txtP03n4.setText("");
				btnP03n4.setEnabled(false);
				txtP03n5.setEnabled(false);
				txtP03n5.setText("");
				btnP03n6.setEnabled(false);
				btnP03n7.setEnabled(false);
				
				lblP03n7.setText(bundle.getString("stat01"));
			}
		});	
	}
	
	@Override
	public void showSelf() {
		l = GUILanguage.getChosenLanguage();
		locale = MultiVaLCA.LANGUAGES.get(l);
		baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		bundle = ResourceBundle.getBundle(baseName, locale);
		txtP03n1.setFont(generalfont);
		txtP03n2.setFont(generalfont);
		txtP03n3.setFont(generalfont);
		txtP03n4.setFont(generalfont);
		txtP03n5.setFont(generalfont);
		lblP03n1.setFont(titlefont);
		lblP03n2.setFont(generalfont);
		lblP03n3.setFont(generalfont);
		lblP03n4.setFont(generalfont);
		lblP03n5.setFont(generalfont);
		lblP03n6.setFont(generalfont);
		lblP03n7.setFont(generalfont);
		btnP03n1.setFont(generalfont);
		btnP03n2.setFont(generalfont);
		btnP03n3.setFont(generalfont);
		btnP03n4.setFont(generalfont);
		btnP03n5.setFont(generalfont);
		btnP03n6.setFont(generalfont);
		btnP03n7.setFont(generalfont);
		
		lblP03n1.setText(bundle.getString("p03n1"));
		lblP03n2.setText(bundle.getString("p03n2"));
		lblP03n3.setText(bundle.getString("p03n3"));
		lblP03n4.setText(bundle.getString("p03n4"));
		lblP03n5.setText(bundle.getString("p02n4"));
		lblP03n6.setText(bundle.getString("p03n6"));
		lblP03n7.setText(bundle.getString("stat01"));
		btnP03n1.setText(bundle.getString("bt05"));
		btnP03n2.setText(bundle.getString("bt06"));
		btnP03n3.setText(bundle.getString("bt07"));
		btnP03n4.setText(bundle.getString("bt08"));
		btnP03n5.setText(bundle.getString("bt07"));
		btnP03n6.setText(bundle.getString("bt09"));
		btnP03n7.setText(bundle.getString("bt04"));
		}	
	}
