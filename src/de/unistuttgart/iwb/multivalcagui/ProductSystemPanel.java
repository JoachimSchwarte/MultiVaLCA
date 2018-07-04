/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import de.unistuttgart.iwb.multivalca.Flow;
import de.unistuttgart.iwb.multivalca.ProcessModule;
import de.unistuttgart.iwb.multivalca.ProcessModuleGroup;
import de.unistuttgart.iwb.multivalca.ProductSystem;
import net.miginfocom.swing.MigLayout;

/**
 * @author HH, JS
 * @version 0.534
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

	protected ProductSystemPanel(String key) {
		super(key);
		initUI();
	}
		
		private void initUI() {
			Language l = GUILanguage.getChosenLanguage();
			Locale locale = MultiVaLCA.LANGUAGES.get(l);
			String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
			ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
		setLayout(new MigLayout("", "[108px,grow][108px][108px][108px,grow]", 
				"[20px][20px][20px][20px][20px][20px][20px][20px][20px][20px][20px][20px,grow]"));		
		lblP03n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblP03n1, "flowy,cell 1 0 2 1,alignx center,growy");	
		add(lblP03n2, "cell 1 1,grow");		
		txtP03n1.setText("");
		add(txtP03n1, "cell 2 1,grow");
		txtP03n1.setColumns(10);		
		add(btnP03n1, "cell 1 2 2 1,alignx center");
		add(lblP03n3, "cell 1 3,grow");
		txtP03n2.setText("");
		add(txtP03n2, "cell 2 3,grow");
		txtP03n2.setColumns(10);
		txtP03n2.setEnabled(false);		
		btnP03n2.setEnabled(false);
		add(btnP03n2, "cell 1 4,alignx center");		
		btnP03n3.setEnabled(false);
		add(btnP03n3, "cell 2 4,alignx center");	
		add(lblP03n4, "cell 1 5,grow");
		txtP03n3.setText("");
		add(txtP03n3, "cell 2 5,grow");
		txtP03n3.setColumns(10);
		txtP03n3.setEnabled(false);		
		add(lblP03n5, "cell 1 6,grow");
		txtP03n4.setText("");
		add(txtP03n4, "cell 2 6,grow");
		txtP03n4.setColumns(10);
		txtP03n4.setEnabled(false);		
		btnP03n4.setEnabled(false);
		add(btnP03n4, "cell 1 7,alignx center");		
		btnP03n5.setEnabled(false);
		add(btnP03n5, "cell 2 7,alignx center");		
		add(lblP03n6, "cell 1 8,grow");
		txtP03n5.setText("");
		add(txtP03n5, "cell 2 8,grow");
		txtP03n5.setColumns(10);
		txtP03n5.setEnabled(false);		
		btnP03n6.setEnabled(false);
		add(btnP03n6, "cell 1 9,alignx center");	
		btnP03n7.setEnabled(false);
		add(btnP03n7, "cell 2 9,alignx center");		
		add(lblP03n7, "cell 0 10 4 1,alignx center");
		
		btnP03n1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name = txtP03n1.getText();	
				boolean nameOk = true;
				if (name.equals("")) {
					nameOk = false;
					lblP03n7.setText(bundle.getString("stat02"));
				} 
				if (name != name.replaceAll(" ","")) {
					nameOk = false;
					lblP03n7.setText(bundle.getString("stat20"));
					txtP03n1.setText("");
				}	
				if (nameOk) {
					boolean nameVorhanden = false;
					
					for(String mod : ProcessModule.getAllInstances().keySet()) {
						if (name.equals(mod)) {
							nameVorhanden = true;
						}
					}
					for(String mod : ProcessModuleGroup.getAllInstances().keySet()) {
						if (name.equals(mod)) {
							nameVorhanden = true;
						}
					}
					for(String mod : ProductSystem.getAllInstances().keySet()) {
						if (name.equals(mod)) {
							nameVorhanden = true;
						}
					}

					if (nameVorhanden) {
						lblP03n7.setText(bundle.getString("stat03"));
					} else {
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
			}
		});
		
		btnP03n2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String modname = txtP03n2.getText();
				if (modname.equals("") || modname.equals(txtP03n1.getText()) ||
						ProductSystem.getAllInstances().get(txtP03n1.getText()).getModulliste().contains(ProcessModule.getInstance(modname))) {
					if (modname.equals("")) {
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
					boolean typmod = false;
					boolean typgroup = false;
					for(String modn2 : ProcessModule.getAllInstances().keySet()) {
						if (modname.equals(modn2)) {
							nameVorhanden = true;
							typmod = true;
						}
					}
					if (!nameVorhanden) {
						for(String modn3 : ProductSystem.getAllInstances().keySet()) {
							if (modname.equals(modn3)) {
								nameVorhanden = true;
								typmod = false;
							}
						}
					}
					if (!nameVorhanden) {
						for(String modn3 : ProcessModuleGroup.getAllInstances().keySet()) {
							if (modname.equals(modn3)) {
								nameVorhanden = true;
								typmod = false;
								typgroup = true;
							}
						}
					}
					if (nameVorhanden) {
						if (typmod){
							ProductSystem.getAllInstances().get(txtP03n1.getText()).addProzessmodul(ProcessModule.getInstance(modname));
						} else {
							if (typgroup) {
								ProductSystem.getAllInstances().get(txtP03n1.getText()).addProzessmodul(ProcessModuleGroup.getAllInstances().get(modname));
							} else {
								ProductSystem.getAllInstances().get(txtP03n1.getText()).addProzessmodul(ProductSystem.getAllInstances().get(modname));
							}
						}
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
				if (fname.equals("") || (menge == 0.0)) {
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
		
		btnP03n6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String vkname = txtP03n5.getText();
				if (vkname.equals("")) {
					lblP03n7.setText(bundle.getString("stat07"));
				} else {					
					if (Flow.containsName(vkname)) {
						Flow akFlow = Flow.getInstance(vkname);
						ProductSystem.getInstance(txtP03n1.getText()).addVuK(akFlow);
						txtP03n5.setText("");
						lblP03n7.setText(bundle.getString("stat19") + 
								ProductSystem.getInstance(txtP03n1.getText()).getVorUndKoppelprodukte().size() + ("stat10"));										
					} else {
						lblP03n7.setText(bundle.getString("stat11"));
					}					
				}
			}
		});

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
		Language l = GUILanguage.getChosenLanguage();
		Locale locale = MultiVaLCA.LANGUAGES.get(l);
		String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
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

