/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.BorderLayout;
import java.awt.CardLayout;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.21
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
	
	private JFrame frame;
	private JPanel panel = new JPanel();
	private CardLayout cl = new CardLayout(0, 0);
	private final Action newFlowAction 		= new newFlowAction();
	private final Action newModuleAction 	= new newModuleAction();
	private final Action newProductAction 	= new newProductAction();
	// Panel 1; Neuer Fluss
	private JTextField txtP01n1; 	// Name des Flusses
	// Panel 2; Neues Prozessmodul
	private JTextField txtModName;
	private JTextField txtFlowName;
	private JTextField txtMenge;
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
		frame = new JFrame();
		frame.setTitle("MulitVaLCA   Version 0.21");
		frame.setBounds(100, 100, 600, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);				
		frame.getContentPane().add(panel, BorderLayout.CENTER);		
		panel.setLayout(cl);
		
		/*
		 * Gestaltung der Panels
		 */
		
		// Panel 1; Neuer Fluss
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, "neuFluss");
		panel_1.setLayout(new MigLayout("", "[108px,grow][108px][108px][108px,grow]", 
				"[20px][20px][20px][20px][20px][20px][20px,grow]"));
		JLabel lblNeuerFlow = new JLabel("Neuer Fluss");
		lblNeuerFlow.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblNeuerFlow, "flowy,cell 1 0 2 1,alignx center,growy");
		
		JLabel lblName = new JLabel("Name des Flusses");
		panel_1.add(lblName, "cell 1 1,grow");
		
		txtP01n1 = new JTextField();
		txtP01n1.setText("");
		panel_1.add(txtP01n1, "cell 2 1,grow");
		txtP01n1.setColumns(10);
		
		JLabel lblTyp = new JLabel("Typ");
		panel_1.add(lblTyp, "cell 1 2,grow");
		
		JComboBox<FlowType> comboBox = new JComboBox<FlowType>();
		comboBox.setModel(new DefaultComboBoxModel<FlowType>(FlowType.values()));
		panel_1.add(comboBox, "cell 2 2,grow");
		
		JLabel lblEinheit = new JLabel("Einheit");
		panel_1.add(lblEinheit, "cell 1 3,grow");
		
		JComboBox<FlowUnit> comboBox_1 = new JComboBox<FlowUnit>();
		comboBox_1.setModel(new DefaultComboBoxModel<FlowUnit>(FlowUnit.values()));
		panel_1.add(comboBox_1, "cell 2 3,grow");
		
		JLabel lblStatusmeldung = new JLabel(">>> ... <<<");
		panel_1.add(lblStatusmeldung, "cell 0 5 4 1,alignx center");
		
		JButton btnSpeichern = new JButton("speichern");
		panel_1.add(btnSpeichern, "cell 1 4 2 1,alignx center");
		
		// Panel 2; Neues Prozessmodul
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, "neuModul");
		panel_2.setLayout(new MigLayout("", "[108px,grow][108px][108px][108px,grow]", 
				"[20px][20px][20px][20px][20px][20px][20px][20px][20px,grow]"));
		JLabel lblNeuesProzessmodul = new JLabel("Neues Prozessmodul");
		lblNeuesProzessmodul.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_2.add(lblNeuesProzessmodul, "flowy,cell 1 0 2 1,alignx center,growy");
		
		JLabel lblModName = new JLabel("Name des Prozessmoduls");
		panel_2.add(lblModName, "cell 1 1,grow");
		
		txtModName = new JTextField();
		txtModName.setText("");
		panel_2.add(txtModName, "cell 2 1,grow");
		txtModName.setColumns(10);
		
		JLabel lblStatus2 = new JLabel(">>> ... <<<");
		panel_2.add(lblStatus2, "cell 0 6 4 1,alignx center");
		
		JButton btnSpei2 = new JButton("neues Prozessmodul anlegen");
		panel_2.add(btnSpei2, "cell 1 2 2 1,alignx center");
		
		JLabel lblFlowName = new JLabel("Name des Flowes");
		panel_2.add(lblFlowName, "cell 1 3,grow");
		
		txtFlowName = new JTextField();
		txtFlowName.setText("");
		panel_2.add(txtFlowName, "cell 2 3,grow");
		txtFlowName.setColumns(10);
		txtFlowName.setEnabled(false);
		
		JLabel lblMenge = new JLabel("Menge");
		panel_2.add(lblMenge, "cell 1 4,grow");
		
		txtMenge = new JTextField();
		txtMenge.setText("");
		panel_2.add(txtMenge, "cell 2 4,grow");
		txtMenge.setColumns(10);
		txtMenge.setEnabled(false);
		
		JButton btnAddFlow = new JButton("Flow hinzuf\u00fcgen");
		btnAddFlow.setEnabled(false);
		panel_2.add(btnAddFlow, "cell 1 5,alignx center");
		
		JButton btnFertig = new JButton("fertig");
		btnFertig.setEnabled(false);
		panel_2.add(btnFertig, "cell 2 5,alignx center");

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
		
		/*
		 * Aktivitäten der Schaltflächen
		 */
		
		/*
		 * neuer Fluss
		 */
		
		btnSpeichern.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name = txtP01n1.getText();	
				FlowType typ = comboBox.getItemAt(comboBox.getSelectedIndex());
				FlowUnit einheit = comboBox_1.getItemAt(comboBox_1.getSelectedIndex());
				if (name.equals("")) {
					lblStatusmeldung.setText(">>> Es wurde kein Name angegeben. <<<");
				} else {
					if (Flow.containsName(name)) {
						lblStatusmeldung.setText(">>> Der angegebene Name ist bereits vorhanden. <<<");
					} else {
						Flow.instance(name, typ, einheit);
						lblStatusmeldung.setText(">>> Anzahl Flussobjekte: " + Flow.getAllNames().size() + " <<<");
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
		
		btnSpei2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name = txtModName.getText();	
				if (name.equals("")) {
					lblStatus2.setText(">>> Es wurde kein Name angegeben. <<<");
				} else {
					if (NameCheck.containsFVName(name)) {
						lblStatus2.setText(">>> Der angegebene Name ist bereits vorhanden. <<<");
					} else {
						ProcessModule.instance(name);
						lblStatus2.setText(">>> Anzahl Prozessmodule: " + ProcessModule.getAllInstances().size() + " <<<");
						btnSpei2.setEnabled(false);
						txtModName.setEnabled(false);
						btnAddFlow.setEnabled(true);
						txtFlowName.setEnabled(true);
						txtMenge.setEnabled(true);
					}	
				} 		
			}
		});
		
		btnAddFlow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String fname = txtFlowName.getText();
				String fmenge = txtMenge.getText();
				Double menge;
				try {
					menge = Double.parseDouble(fmenge);
				} catch (NumberFormatException e){
					menge = 0.0;
				}
				if (fname.equals("") || (menge == 0.0)) {
					lblStatus2.setText(">>> unvollst\u00e4ndige Eingabe <<<");
				} else {
					if (Flow.containsName(fname)) {
						Flow akFlow = Flow.getInstance(fname);
						String mname = txtModName.getText();
						ProcessModule.getInstance(mname).addFluss(akFlow, FlowValueType.MeanValue, menge);
						txtFlowName.setText("");
						txtMenge.setText("");
						btnFertig.setEnabled(true);
						int anzPFlow = ProcessModule.getInstance(mname).getProduktflussvektor().size();
						int anzEFlow = ProcessModule.getInstance(mname).getElementarflussvektor().size();
						int anzGesamt = anzPFlow + anzEFlow;
						lblStatus2.setText(">>> Prozessmodul " + mname + " besitzt " +
								anzGesamt + " Fl\u00fcsse <<<");
						
					} else {
						lblStatus2.setText(">>> unbekannter Flowname <<<");
					}					
				}
			}
		});
		
		btnFertig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnSpei2.setEnabled(true);
				txtFlowName.setText("");
				txtMenge.setText("");
				txtModName.setText("");
				btnAddFlow.setEnabled(false);
				btnFertig.setEnabled(false);
				txtModName.setEnabled(true);
				txtFlowName.setEnabled(false);
				txtMenge.setEnabled(false);
				lblStatus2.setText(">>> ... <<<");
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

}
