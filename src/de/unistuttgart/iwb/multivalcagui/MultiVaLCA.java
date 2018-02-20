/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.BorderLayout;
import java.awt.CardLayout;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.23
 */

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.unistuttgart.iwb.multivalca.*;
import net.miginfocom.swing.MigLayout;

public class MultiVaLCA {
	
	Language l = Language.Deutsch;
	
	private JFrame frame = new JFrame();;
	private JPanel panel = new JPanel();
	private CardLayout cl = new CardLayout(0, 0);
	private final Action newFlowAction 		= new newFlowAction();
	private final Action newModuleAction 	= new newModuleAction();
	private final Action newProductAction 	= new newProductAction();
	private final Action aboutAction 	= new aboutAction();
	private final Action prefsAction 	= new prefsAction();
	// Panel 1; Neuer Fluss
	private JPanel panel_01 = new JPanel();
	private JTextField txtP01n1 = new JTextField(); 	// Eingabefeld Flussname
	private JLabel lblP01n1 = new JLabel();				// "Neuer Fluss"
	private JLabel lblP01n2 = new JLabel();				// "Name des Flusses"
	private JLabel lblP01n3 = new JLabel();				// "Typ"
	private JLabel lblP01n4 = new JLabel();				// "Einheit"
	private JLabel lblP01n5 = new JLabel();				// Status
	private JButton btnP01n1 = new JButton();			// "speichern" 
	// Panel 2; Neues Prozessmodul
	private JPanel panel_02 = new JPanel();
	private JTextField txtP02n1 = new JTextField();		// Eingabefeld Modulname
	private JTextField txtP02n2 = new JTextField();		// Eingabefeld Flussname  
	private JTextField txtP02n3 = new JTextField();		// Eingabefeld Menge
	private JLabel lblP02n1 = new JLabel(); 			// "Neues Prozessmodul"
	private JLabel lblP02n2 = new JLabel();				// "Name des Prozessmoduls"
	private JLabel lblP02n3 = new JLabel();				// "Name des Flusses"
	private JLabel lblP02n4 = new JLabel();				// "Menge"
	private JLabel lblP02n5 = new JLabel();				// Status
	private JButton btnP02n1 = new JButton(); 			// "neues Prozessmodul anlegen"
	private JButton btnP02n2 = new JButton();			// "Fluss hinzufügen"
	private JButton btnP02n3 = new JButton();			// "fertig"
	// Panel 3; Neues Produktsystem
	private JTextField txtPSName;
	private JTextField txtModName2;
	private JTextField txtBV;
	private JTextField txtBVMenge;
	private JTextField txtVKName;
	


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MultiVaLCA window = new MultiVaLCA();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MultiVaLCA() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame.setTitle(GuiStrings.getGS("head1",l)+"   "+GuiStrings.getGS("head2",l));
		frame.setBounds(100, 100, 600, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);				
		frame.getContentPane().add(panel, BorderLayout.CENTER);		
		panel.setLayout(cl);
		
		/*
		 * Gestaltung der Panels
		 */
		
		// Panel 1; Neuer Fluss
			
		panel.add(panel_01, "neuFluss");
		panel_01.setLayout(new MigLayout("", "[108px,grow][108px][108px][108px,grow]", 
				"[20px][20px][20px][20px][20px][20px][20px,grow]"));		
		lblP01n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_01.add(lblP01n1, "flowy,cell 1 0 2 1,alignx center,growy");			
		panel_01.add(lblP01n2, "cell 1 1,grow");		
		txtP01n1.setText("");
		panel_01.add(txtP01n1, "cell 2 1,grow");
		txtP01n1.setColumns(10);	
		panel_01.add(lblP01n3, "cell 1 2,grow");		
		JComboBox<FlowType> comboBox = new JComboBox<FlowType>();
		comboBox.setModel(new DefaultComboBoxModel<FlowType>(FlowType.values()));
		panel_01.add(comboBox, "cell 2 2,grow");	
		panel_01.add(lblP01n4, "cell 1 3,grow");	
		JComboBox<FlowUnit> comboBox_1 = new JComboBox<FlowUnit>();
		comboBox_1.setModel(new DefaultComboBoxModel<FlowUnit>(FlowUnit.values()));
		panel_01.add(comboBox_1, "cell 2 3,grow");			
		panel_01.add(btnP01n1, "cell 1 4 2 1,alignx center");
		panel_01.add(lblP01n5, "cell 0 5 4 1,alignx center");		
		
		// Panel 2; Neues Prozessmodul
		

		panel.add(panel_02, "neuModul");
		panel_02.setLayout(new MigLayout("", "[108px,grow][108px][108px][108px,grow]", 
				"[20px][20px][20px][20px][20px][20px][20px][20px][20px,grow]"));		
		lblP02n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_02.add(lblP02n1, "flowy,cell 1 0 2 1,alignx center,growy");	
		panel_02.add(lblP02n2, "cell 1 1,grow");		
		txtP02n1.setText("");
		panel_02.add(txtP02n1, "cell 2 1,grow");
		txtP02n1.setColumns(10);		
		panel_02.add(btnP02n1, "cell 1 2 2 1,alignx center");		
		panel_02.add(lblP02n3, "cell 1 3,grow");	
		txtP02n2.setText("");
		panel_02.add(txtP02n2, "cell 2 3,grow");
		txtP02n2.setColumns(10);
		txtP02n2.setEnabled(false);	
		panel_02.add(lblP02n4, "cell 1 4,grow");
		txtP02n3.setText("");
		panel_02.add(txtP02n3, "cell 2 4,grow");
		txtP02n3.setColumns(10);
		txtP02n3.setEnabled(false);		
		btnP02n2.setEnabled(false);
		panel_02.add(btnP02n2, "cell 1 5,alignx center");
		btnP02n3.setEnabled(false);
		panel_02.add(btnP02n3, "cell 2 5,alignx center");
		panel_02.add(lblP02n5, "cell 0 6 4 1,alignx center");		

		// Panel 3; Neues Produktsystem
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3, "neuProdukt");	
		panel_3.setLayout(new MigLayout("", "[108px,grow][108px][108px][108px,grow]", 
				"[20px][20px][20px][20px][20px][20px][20px][20px][20px][20px][20px][20px,grow]"));
		JLabel lblNeuesProduktsystem = new JLabel("Neues Produktsystem");
		lblNeuesProduktsystem.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_3.add(lblNeuesProduktsystem, "flowy,cell 1 0 2 1,alignx center,growy");
		
		JLabel lblPSName = new JLabel("Name des Produktsystems");
		panel_3.add(lblPSName, "cell 1 1,grow");
		
		txtPSName = new JTextField();
		txtPSName.setText("");
		panel_3.add(txtPSName, "cell 2 1,grow");
		txtPSName.setColumns(10);
		
		JButton btnSpei3 = new JButton("neues Produktsystem anlegen");
		panel_3.add(btnSpei3, "cell 1 2 2 1,alignx center");
		
		JLabel lblModName2 = new JLabel("Prozessmodul/Subsystem");
		panel_3.add(lblModName2, "cell 1 3,grow");
		
		txtModName2 = new JTextField();
		txtModName2.setText("");
		panel_3.add(txtModName2, "cell 2 3,grow");
		txtModName2.setColumns(10);
		txtModName2.setEnabled(false);
		
		JButton btnAddMod = new JButton("Modul/System hinzuf\u00fcgen");
		btnAddMod.setEnabled(false);
		panel_3.add(btnAddMod, "cell 1 4,alignx center");
		
		JButton btnWeiter = new JButton("weiter");
		btnWeiter.setEnabled(false);
		panel_3.add(btnWeiter, "cell 2 4,alignx center");
		
		JLabel lblBV = new JLabel("Produkt im Bedarfsvektor");
		panel_3.add(lblBV, "cell 1 5,grow");
		
		txtBV = new JTextField();
		txtBV.setText("");
		panel_3.add(txtBV, "cell 2 5,grow");
		txtBV.setColumns(10);
		txtBV.setEnabled(false);
		
		JLabel lblBVMenge = new JLabel("Menge");
		panel_3.add(lblBVMenge, "cell 1 6,grow");
		
		txtBVMenge = new JTextField();
		txtBVMenge.setText("");
		panel_3.add(txtBVMenge, "cell 2 6,grow");
		txtBVMenge.setColumns(10);
		txtBVMenge.setEnabled(false);
		
		JButton btnAddBed = new JButton("Bedarfsvektor erg\u00e4nzen");
		btnAddBed.setEnabled(false);
		panel_3.add(btnAddBed, "cell 1 7,alignx center");
		
		JButton btnWei2 = new JButton("weiter");
		btnWei2.setEnabled(false);
		panel_3.add(btnWei2, "cell 2 7,alignx center");
		
		JLabel lblVKName = new JLabel("Vor- oder Koppelprodukt");
		panel_3.add(lblVKName, "cell 1 8,grow");
		
		txtVKName = new JTextField();
		txtVKName.setText("");
		panel_3.add(txtVKName, "cell 2 8,grow");
		txtVKName.setColumns(10);
		txtVKName.setEnabled(false);
		
		JButton btnAddVK = new JButton("VK-Flow hinzuf\u00fcgen");
		btnAddVK.setEnabled(false);
		panel_3.add(btnAddVK, "cell 1 9,alignx center");
		
		JButton btnFertig2 = new JButton("fertig");
		btnFertig2.setEnabled(false);
		panel_3.add(btnFertig2, "cell 2 9,alignx center");
		
		JLabel lblStatus3 = new JLabel(">>> ... <<<");
		panel_3.add(lblStatus3, "cell 0 10 4 1,alignx center");
		
		// Panel 4; Info
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4, "leer");
		panel_4.setLayout(new MigLayout("", "[108px,grow][200px][108px,grow]", 
				"[20px][20px][40px][20px][20px][20px][20px,grow][20px]"));
		JLabel lblInfo1 = new JLabel(GuiStrings.getGS("head1", l));
		lblInfo1.setFont(new Font("Tahoma", Font.BOLD, 26));
		panel_4.add(lblInfo1, "cell 1 2,alignx center,aligny top");
		JLabel lblInfo2 = new JLabel(GuiStrings.getGS("info1", l));
		lblInfo2.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_4.add(lblInfo2, "cell 1 3,alignx center,aligny top");
		JLabel lblInfo3 = new JLabel(GuiStrings.getGS("info2", l));
		lblInfo3.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_4.add(lblInfo3, "cell 1 4,alignx center,aligny top");
		JLabel lblInfo4 = new JLabel(GuiStrings.getGS("info3", l));
		lblInfo4.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_4.add(lblInfo4, "cell 1 5,alignx center,aligny top");
		JLabel lblInfo5 = new JLabel(GuiStrings.getGS("head2", l)+"     "+GuiStrings.getGS("date", l));
		lblInfo5.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_4.add(lblInfo5, "cell 1 7,alignx center,aligny top");
		
		// Panel 5; Sprachauswahl
		
		JPanel panel_5 = new JPanel();
		panel.add(panel_5, "lang");
		panel_5.setLayout(new MigLayout("", "[108px,grow][108px][108px][108px,grow]", 
				"[20px][20px][20px][20px][20px][20px][20px,grow]"));
		JLabel lblLangsel = new JLabel(GuiStrings.getGS("mp31e", l));
		lblLangsel.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_5.add(lblLangsel, "flowy,cell 1 0 2 1,alignx center,growy");
		
		JLabel lblLang = new JLabel(GuiStrings.getGS("mp31", l));
		panel_5.add(lblLang, "cell 1 1,grow");
		
		JComboBox<Language> comboBox2 = new JComboBox<Language>();
		comboBox2.setModel(new DefaultComboBoxModel<Language>(Language.values()));
		panel_5.add(comboBox2, "cell 2 1,grow");
		
		JButton btnLangSelect = new JButton(GuiStrings.getGS("bt01", l));
		panel_5.add(btnLangSelect, "cell 1 2 2 1,alignx center");
		
		
		
		cl.show(panel, "leer");
		
		/*
		 * Organisation der Menuleiste
		 */
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNew = new JMenu(GuiStrings.getGS("mp1",l));
		menuBar.add(mnNew);
		
		JMenuItem mntmFlow = new JMenuItem();
		mntmFlow.setAction(newFlowAction);
		mnNew.add(mntmFlow);
		
		JMenuItem mntmProcessModule = new JMenuItem();
		mntmProcessModule.setAction(newModuleAction);
		mnNew.add(mntmProcessModule);
		
		JMenuItem mntmProductSystem = new JMenuItem();
		mntmProductSystem.setAction(newProductAction);
		mnNew.add(mntmProductSystem);
			
		JMenu mnPrefs = new JMenu(GuiStrings.getGS("mp3", l));
		menuBar.add(mnPrefs);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem();
		mntmNewMenuItem_3.setAction(prefsAction);
		mnPrefs.add(mntmNewMenuItem_3);
		
		JMenu mnHilfe = new JMenu(GuiStrings.getGS("mp2", l));
		menuBar.add(mnHilfe);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem();
		mntmNewMenuItem_2.setAction(aboutAction);
		mnHilfe.add(mntmNewMenuItem_2);

		
		/*
		 * Aktivitäten der Schaltflächen
		 */
		
		/*
		 * neuer Fluss
		 */
		
		btnP01n1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name = txtP01n1.getText();	
				FlowType typ = comboBox.getItemAt(comboBox.getSelectedIndex());
				FlowUnit einheit = comboBox_1.getItemAt(comboBox_1.getSelectedIndex());
				if (name.equals("")) {
					lblP01n5.setText(">>> Es wurde kein Name angegeben. <<<");
				} else {
					if (Flow.containsName(name)) {
						lblP01n5.setText(">>> Der angegebene Name ist bereits vorhanden. <<<");
					} else {
						Flow.instance(name, typ, einheit);
						lblP01n5.setText(">>> Anzahl Flussobjekte: " + Flow.getAllNames().size() + " <<<");
						txtP01n1.setText("");
						comboBox.setSelectedIndex(0);
						comboBox_1.setSelectedIndex(0);
					}	
				} 		
			}
		});

		/*
		 * neues Prozessmodul
		 */
		
		btnP02n1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name = txtP02n1.getText();	
				if (name.equals("")) {
					lblP02n5.setText(">>> Es wurde kein Name angegeben. <<<");
				} else {
					if (NameCheck.containsFVName(name)) {
						lblP02n5.setText(">>> Der angegebene Name ist bereits vorhanden. <<<");
					} else {
						ProcessModule.instance(name);
						lblP02n5.setText(">>> Anzahl Prozessmodule: " + ProcessModule.getAllInstances().size() + " <<<");
						btnP02n1.setEnabled(false);
						txtP02n1.setEnabled(false);
						btnP02n2.setEnabled(true);
						txtP02n2.setEnabled(true);
						txtP02n3.setEnabled(true);
					}	
				} 		
			}
		});
		
		btnP02n2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String fname = txtP02n2.getText();
				String fmenge = txtP02n3.getText();
				Double menge;
				try {
					menge = Double.parseDouble(fmenge);
				} catch (NumberFormatException e){
					menge = 0.0;
				}
				if (fname.equals("") || (menge == 0.0)) {
					lblP02n5.setText(">>> unvollst\u00e4ndige Eingabe <<<");
				} else {
					if (Flow.containsName(fname)) {
						Flow akFlow = Flow.getInstance(fname);
						String mname = txtP02n1.getText();
						ProcessModule.getInstance(mname).addFluss(akFlow, FlowValueType.MeanValue, menge);
						txtP02n2.setText("");
						txtP02n3.setText("");
						btnP02n3.setEnabled(true);
						int anzPFlow = ProcessModule.getInstance(mname).getProduktflussvektor().size();
						int anzEFlow = ProcessModule.getInstance(mname).getElementarflussvektor().size();
						int anzGesamt = anzPFlow + anzEFlow;
						lblP02n5.setText(">>> Prozessmodul " + mname + " besitzt " +
								anzGesamt + " Fl\u00fcsse <<<");
						
					} else {
						lblP02n5.setText(">>> unbekannter Flowname <<<");
					}					
				}
			}
		});
		
		btnP02n3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnP02n1.setEnabled(true);
				txtP02n2.setText("");
				txtP02n3.setText("");
				txtP02n1.setText("");
				btnP02n2.setEnabled(false);
				btnP02n3.setEnabled(false);
				txtP02n1.setEnabled(true);
				txtP02n2.setEnabled(false);
				txtP02n3.setEnabled(false);
				lblP02n5.setText(">>> ... <<<");
			}
		});

		/*
		 * neues Produktsystem
		 */
		
		btnSpei3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name = txtPSName.getText();	
				if (name.equals("")) {
					lblStatus3.setText(">>> Es wurde kein Name angegeben. <<<");
				} else {
					boolean nameVorhanden = false;
					
					for(String mod : ProcessModule.getAllInstances().keySet()) {
						if (name.equals(mod)) {
							nameVorhanden = true;
						}
					}
					for(String mod : ProductSystem.getAllInstances().keySet()) {
						if (name.equals(mod)) {
							nameVorhanden = true;
						}
					}
					if (nameVorhanden == true) {
						lblStatus3.setText(">>> Der angegebene Name ist bereits vorhanden. <<<");
					} else {
						ProductSystem.instance(name, new HashMap<Flow, Double>(), 
								new LinkedList<Flow>());
						lblStatus3.setText(">>> Anzahl Produktsysteme: " + ProductSystem.getAllInstances().size() + " <<<");

						btnSpei3.setEnabled(false);
						txtPSName.setEnabled(false);
						btnAddMod.setEnabled(true);
						txtModName2.setEnabled(true);
					}	
				} 		
			}
		});
		
		btnAddMod.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String modname = txtModName2.getText();
				if (modname.equals("") || modname.equals(txtPSName.getText())) {
					if (modname.equals("")) {
						lblStatus3.setText(">>> unvollst\u00e4ndige Eingabe <<<");
					}
					if (modname.equals(txtPSName.getText())) {
						lblStatus3.setText(">>> unzul\u00e4ssige Rekursion <<<");
					}
				} else {
					boolean nameVorhanden = false;
					boolean typmod = false;
					for(String modn2 : ProcessModule.getAllInstances().keySet()) {
						if (modname.equals(modn2)) {
							nameVorhanden = true;
							typmod = true;
						}
					}
					if (nameVorhanden == false) {
						for(String modn3 : ProductSystem.getAllInstances().keySet()) {
							if (modname.equals(modn3)) {
								nameVorhanden = true;
								typmod = false;
							}
						}
					}
					if (nameVorhanden == true) {
						if (typmod == true){
							ProductSystem.getAllInstances().get(txtPSName.getText()).addProzessmodul(ProcessModule.getInstance(modname));
						} else {
							ProductSystem.getAllInstances().get(txtPSName.getText()).addProzessmodul(ProductSystem.getAllInstances().get(modname));
						}
						lblStatus3.setText(">>> Produktsystem " + txtPSName.getText() +
								" besteht aus " + ProductSystem.getAllInstances().get(txtPSName.getText()).getModulAnzahl() 
								+ " Elementen <<<");
						txtModName2.setText("");
						btnWeiter.setEnabled(true);
					} else {
						lblStatus3.setText(">>> Name ist weder Prozessmodul noch Produktsystem <<<");
					}					
				}
			}
		});
		
		btnWeiter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				txtModName2.setEnabled(false);
				txtModName2.setText("");
				btnAddMod.setEnabled(false);
				btnWeiter.setEnabled(false);
				txtBV.setEnabled(true);
				txtBVMenge.setEnabled(true);
				btnAddBed.setEnabled(true);
				lblStatus3.setText(">>> ... <<<");
			}
		});
		
		btnAddBed.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String fname = txtBV.getText();
				String fmenge = txtBVMenge.getText();
				Double menge;
				try {
					menge = Double.parseDouble(fmenge);
				} catch (NumberFormatException e){
					menge = 0.0;
				}
				if (fname.equals("") || (menge == 0.0)) {
					lblStatus3.setText(">>> unvollst\u00e4ndige Eingabe <<<");
				} else {
					if (Flow.containsName(fname)) {
						Flow akFlow = Flow.getInstance(fname);
						ProductSystem.getInstance(txtPSName.getText()).addBedarf(akFlow, Double.parseDouble(txtBVMenge.getText()));
						lblStatus3.setText(">>> Der Bedarfsvektor enth\u00e4lt " + 
								ProductSystem.getInstance(txtPSName.getText()).getBedarfsvektor().size() + " Fl\u00dcsse <<<");
						btnWei2.setEnabled(true);	
						txtBV.setText("");
						txtBVMenge.setText("");
					} else {
						lblStatus3.setText(">>> unbekannter Flowname <<<");
					}					
				}
			}
		});
		
		btnWei2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				txtModName2.setEnabled(false);
				txtModName2.setText("");
				btnAddMod.setEnabled(false);
				btnWeiter.setEnabled(false);
				txtBV.setEnabled(false);
				txtBVMenge.setEnabled(false);
				btnWei2.setEnabled(false);
				txtBV.setText("");
				txtBVMenge.setText("");
				btnAddBed.setEnabled(false);
				txtVKName.setEnabled(true);
				btnAddVK.setEnabled(true);
				btnFertig2.setEnabled(true);
				
				lblStatus3.setText(">>> ... <<<");
			}
		});
		
		btnAddVK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String vkname = txtVKName.getText();
				if (vkname.equals("")) {
					lblStatus3.setText(">>> unvollst\u00e4ndige Eingabe <<<");
				} else {					
					if (Flow.containsName(vkname)) {
						Flow akFlow = Flow.getInstance(vkname);
						ProductSystem.getInstance(txtPSName.getText()).addVuK(akFlow);
						txtVKName.setText("");
						lblStatus3.setText(">>> Der VK-Vektor enth\u00e4lt " + 
								ProductSystem.getInstance(txtPSName.getText()).getVorUndKoppelprodukte().size() + " Fl\u00fcsse <<<");										
					} else {
						lblStatus3.setText(">>> unbekannter Flowname <<<");
					}					
				}
			}
		});

		btnFertig2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				txtPSName.setEnabled(true);
				txtPSName.setText("");
				btnSpei3.setEnabled(true);
				txtModName2.setEnabled(false);
				txtModName2.setText("");
				btnAddMod.setEnabled(false);
				btnWeiter.setEnabled(false);
				txtBV.setEnabled(false);
				txtBVMenge.setEnabled(false);
				btnWei2.setEnabled(false);
				txtBV.setText("");
				txtBVMenge.setText("");
				btnAddBed.setEnabled(false);
				txtVKName.setEnabled(false);
				txtVKName.setText("");
				btnAddVK.setEnabled(false);
				btnFertig2.setEnabled(false);
				
				lblStatus3.setText(">>> ... <<<");
			}
			
		});
		
		/*
		 * Sprachauswahl
		 */
		
		btnLangSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				l = comboBox2.getItemAt(comboBox2.getSelectedIndex());
				frame.setTitle(GuiStrings.getGS("head1",l)+"   "+GuiStrings.getGS("head2",l));
				mnNew.setText(GuiStrings.getGS("mp1",l));
				mnPrefs.setText(GuiStrings.getGS("mp3",l));
				mnHilfe.setText(GuiStrings.getGS("mp2",l));
				mntmFlow.setText(GuiStrings.getGS("mp11",l));
				mntmFlow.setToolTipText(GuiStrings.getGS("mp11e",l));
				mntmProcessModule.setText(GuiStrings.getGS("mp12",l));
				mntmProcessModule.setToolTipText(GuiStrings.getGS("mp12e",l));
				mntmProductSystem.setText(GuiStrings.getGS("mp13",l));
				mntmProductSystem.setToolTipText(GuiStrings.getGS("mp13e",l));
				mntmNewMenuItem_3.setText(GuiStrings.getGS("mp31",l));
				mntmNewMenuItem_3.setToolTipText(GuiStrings.getGS("mp31e",l));
				mntmNewMenuItem_2.setText(GuiStrings.getGS("mp21",l));
				mntmNewMenuItem_2.setToolTipText(GuiStrings.getGS("mp21e",l));
				lblLangsel.setText(GuiStrings.getGS("mp31e", l));
				lblLang.setText(GuiStrings.getGS("mp31", l));
				btnLangSelect.setText(GuiStrings.getGS("bt01", l));
				


			}
		});

	}


	
	/*
	 * Actions der Menupunkte
	 */

	private class newFlowAction extends AbstractAction {
		private static final long serialVersionUID = 3159283296670804375L;
		public newFlowAction() {
			putValue(NAME, GuiStrings.getGS("mp11", l));
			putValue(SHORT_DESCRIPTION, GuiStrings.getGS("mp11e", l));
		}
		public void actionPerformed(ActionEvent e) {			
			lblP01n1.setText(GuiStrings.getGS("p01n1", l));
			lblP01n2.setText(GuiStrings.getGS("p01n2", l));
			lblP01n3.setText(GuiStrings.getGS("p01n3", l));
			lblP01n4.setText(GuiStrings.getGS("p01n4", l));
			lblP01n5.setText(GuiStrings.getGS("stat01", l));
			btnP01n1.setText(GuiStrings.getGS("bt01", l));
			cl.show(panel, "neuFluss");
						
		}
	}
	private class newModuleAction extends AbstractAction {
		private static final long serialVersionUID = 6190606710625748526L;
		public newModuleAction() {
			putValue(NAME, GuiStrings.getGS("mp12", l));
			putValue(SHORT_DESCRIPTION, GuiStrings.getGS("mp12e", l));
		}
		public void actionPerformed(ActionEvent e) {
			lblP02n1.setText(GuiStrings.getGS("p02n1", l));
			lblP02n2.setText(GuiStrings.getGS("p02n2", l));
			lblP02n3.setText(GuiStrings.getGS("p01n2", l));
			lblP02n4.setText(GuiStrings.getGS("p02n4", l));
			lblP02n5.setText(GuiStrings.getGS("stat01", l));
			btnP02n1.setText(GuiStrings.getGS("bt02", l));
			btnP02n2.setText(GuiStrings.getGS("bt03", l));
			btnP02n3.setText(GuiStrings.getGS("bt04", l));
			cl.show(panel, "neuModul");
		}
	}
	private class newProductAction extends AbstractAction {
		private static final long serialVersionUID = -7757652453649226474L;
		public newProductAction() {
			putValue(NAME, GuiStrings.getGS("mp13", l));
			putValue(SHORT_DESCRIPTION, GuiStrings.getGS("mp13e", l));
		}
		public void actionPerformed(ActionEvent e) {
			cl.show(panel, "neuProdukt");
		}
	}
	
	private class aboutAction extends AbstractAction {
		private static final long serialVersionUID = 8545097902506476895L;
		public aboutAction() {
			putValue(NAME, GuiStrings.getGS("mp21", l));
			putValue(SHORT_DESCRIPTION, GuiStrings.getGS("mp21e", l));
		}
		public void actionPerformed(ActionEvent e) {
			cl.show(panel, "leer");
		}
	}
	
	private class prefsAction extends AbstractAction {
		private static final long serialVersionUID = 8545097902306476895L;
		public prefsAction() {
			putValue(NAME, GuiStrings.getGS("mp31", l));
			putValue(SHORT_DESCRIPTION, GuiStrings.getGS("mp31e", l));
		}
		public void actionPerformed(ActionEvent e) {
			cl.show(panel, "lang");
		}
	}

}
