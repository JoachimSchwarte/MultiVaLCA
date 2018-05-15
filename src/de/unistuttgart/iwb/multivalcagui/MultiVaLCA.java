/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Vector;

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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import de.unistuttgart.iwb.multivalca.*;
import net.miginfocom.swing.MigLayout;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.5
 */

public class MultiVaLCA {
	
	Language l = Language.Deutsch;
	
	private JFrame frame = new JFrame();;
	private JPanel panel = new JPanel();
	private CardLayout cl = new CardLayout(0, 0);
	private final Action newFlowAction 			= new newFlowAction();
	private final Action newModuleAction 		= new newModuleAction();
	private final Action newProductAction 		= new newProductAction();
	private final Action aboutAction 			= new aboutAction();
	private final Action prefsAction 			= new prefsAction();
	private final Action listFlowAction 		= new listFlowAction();
	private final Action listModuleAction 		= new listModuleAction();
	private final Action listProductAction 		= new listProductAction();
	private final Action calculateAction 		= new calculateAction();	
	private final Action xmlExportAction 		= new XMLExportAction(l);
	private final Action xmlImportAction 		= new XMLImportAction(l);
	private final Action newCategoryAction 		= new newCategoryAction();
	private final Action listCategoriesAction 	= new listCategoriesAction();
	private final Action newCFAction 			= new newCFAction();
	private final Action listCFAction 			= new listCFAction();
	private final Action newLCIAAction 			= new newLCIAAction();
	private final Action listLCIAAction 		= new listLCIAAction();
	private final Action calculateLCIAAction 	= new calculateLCIAAction();	
	
	//
	// Panel 1; Neuer Fluss
	//
	private JPanel panel_01 = new JPanel();
	private JTextField txtP01n1 = new JTextField(); 	// Eingabefeld Flussname
	private JLabel lblP01n1 = new JLabel();				// "Neuer Fluss"
	private JLabel lblP01n2 = new JLabel();				// "Name des Flusses"
	private JLabel lblP01n3 = new JLabel();				// "Typ"
	private JLabel lblP01n4 = new JLabel();				// "Einheit"
	private JLabel lblP01n5 = new JLabel();				// Status
	private JButton btnP01n1 = new JButton();			// "speichern" 
	private JComboBox<String> comboBox = new JComboBox<String>();
	//
	// Panel 2; Neues Prozessmodul
	//
	private JPanel panel_02 = new JPanel();
	private JTextField txtP02n1 = new JTextField();		// Eingabefeld Modulname
	private JTextField txtP02n2 = new JTextField();		// Eingabefeld Flussname  
	private JTextField txtP02n3 = new JTextField();		// Eingabefeld Menge
	private JTextField txtP02n4 = new JTextField();		// Eingabefeld Untergrenze
	private JTextField txtP02n5 = new JTextField();		// Eingabefeld Obergrenze
	private JLabel lblP02n1 = new JLabel(); 			// "Neues Prozessmodul"
	private JLabel lblP02n2 = new JLabel();				// "Name des Prozessmoduls"
	private JLabel lblP02n3 = new JLabel();				// "Name des Flusses"
	private JLabel lblP02n4 = new JLabel();				// "Menge"
	private JLabel lblP02n5 = new JLabel();				// Status
	private JLabel lblP02n6 = new JLabel();				// "Untergrenze"
	private JLabel lblP02n7 = new JLabel();				// "Obergrenze"
	private JButton btnP02n1 = new JButton(); 			// "neues Prozessmodul anlegen"
	private JButton btnP02n2 = new JButton();			// "Grenzwerte bestätigen"
	private JButton btnP02n3 = new JButton();			// "fertig"
	private JButton btnP02n4 = new JButton();			// "Fluss hinzufügen"
	//
	// Panel 3; Neues Produktsystem
	//
	private JPanel panel_3 = new JPanel();
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
	//
	// Panel 4; Info
	//
	private JPanel panel_4 = new JPanel();
	private JLabel lblInfo1 = new JLabel(); 	
	private JLabel lblInfo2 = new JLabel(); 	
	private JLabel lblInfo3 = new JLabel();		
	private JLabel lblInfo4 = new JLabel();		
	JLabel lblInfo5 = new JLabel();		
	//
	// Panel 5; Sprachauswahl
	//
	private JPanel panel_5 = new JPanel();
	private JLabel lblP05n1 = new JLabel();				// "Sprachauswahl"
	private JLabel lblP05n2 = new JLabel();				// "Sprache"
	private JButton btn05n1 = new JButton();			// "speichern"
	private JComboBox<Language> comboBox2 = new JComboBox<Language>();
	//
	// Panel 6; Flussliste
	//
	private JPanel panel_6 = new JPanel();
	private JLabel lblP06n1 = new JLabel();
	private JTable flowsTable 		= new JTable();
	DefaultTableModel flowsTableModel 		= new DefaultTableModel(0,3);
	//
	// Panel 7; Prozessmodulliste
	//
	private JPanel panel_7 = new JPanel();
	private JLabel lblP07n1 = new JLabel();
	private JTable pmTable 		= new JTable();
	DefaultTableModel pmTableModel 		= new DefaultTableModel(0,4);
	//
	// Panel 8; Produktsystemliste
	//
	private JPanel panel_8 = new JPanel();
	private JLabel lblP08n1 = new JLabel();
	private JTable psTable 		= new JTable();
	DefaultTableModel psTableModel 		= new DefaultTableModel(0,3);
	//
	// Panel 9; LCI Berechnung
	//
	private JPanel panel_9 = new JPanel();
	private JLabel lblP09n1 = new JLabel();
	private JTable lciTable 		= new JTable();
	DefaultTableModel lciTableModel 		= new DefaultTableModel(0,4);
	//
	// Panel 10; Neue Wirkungskategorie
	//
	private JPanel panel_10 = new JPanel();
	private JTextField txtP10n1 = new JTextField();		// Eingabefeld Kategorie
	private JTextField txtP10n2 = new JTextField();		// Eingabefeld Indikator
	private JLabel lblP10n1 = new JLabel(); 			// "Neue Wirkungskategorie"
	private JLabel lblP10n2 = new JLabel();				// "Name der Wirkungskategorie"
	private JLabel lblP10n3 = new JLabel();				// "Indikator"
	private JLabel lblP10n4 = new JLabel();				// Status
	private JButton btnP10n1 = new JButton(); 			// "speichern"
	//
	// Panel 11; Kategorieliste
	//
	private JPanel panel_11 = new JPanel();
	private JLabel lblP11n1 = new JLabel();
	private JTable catTable 		= new JTable();
	DefaultTableModel catTableModel 		= new DefaultTableModel(0,2);
	//
	// Panel 12; Neuer Charakterisierungsfaktor
	//
	private JPanel panel_12 = new JPanel();
	private JTextField txtP12n1 = new JTextField();		// Eingabefeld CF-Name
	private JTextField txtP12n2 = new JTextField();		// Eingabefeld Fluss-Name
	private JTextField txtP12n3 = new JTextField();		// Eingabefeld Wirkungskategorie
	private JTextField txtP12n4 = new JTextField();		// Eingabefeld Faktor
	private JTextField txtP12n5 = new JTextField();		// Eingabefeld Untergrenze
	private JTextField txtP12n6 = new JTextField();		// Eingabefeld Obergrenze
	private JLabel lblP12n1 = new JLabel(); 			// "Neuer Charakterisierungsfaktor"
	private JLabel lblP12n2 = new JLabel(); 			// "Name des Charakterisierungsfaktors"
	private JLabel lblP12n3 = new JLabel(); 			// "Name des Flusses"
	private JLabel lblP12n4 = new JLabel(); 			// "Name der Wirkungskategorie"
	private JLabel lblP12n5 = new JLabel(); 			// "Faktor"
	private JLabel lblP12n6 = new JLabel(); 			// "Untergrenze"
	private JLabel lblP12n7 = new JLabel(); 			// "Obergrenze"
	private JLabel lblP12n8 = new JLabel(); 			// Status
	private JButton btnP12n1 = new JButton(); 			// "speichern"
	private JButton btnP12n2 = new JButton(); 			// "Grenzwerte bestätigen"
	//
	// Panel 13; CF-Liste
	//
	private JPanel panel_13 = new JPanel();
	private JLabel lblP13n1 = new JLabel();
	private JTable cfTable 		= new JTable();
	DefaultTableModel cfTableModel 		= new DefaultTableModel(0,5);
	//
	// Panel 14; Neue Bewertungsmethode
	//
	private JPanel panel_14 = new JPanel();
	private JTextField txtP14n1 = new JTextField();		// Eingabefeld LCIA-Name
	private JTextField txtP14n2 = new JTextField();		// Eingabefeld Kategorie
	private JTextField txtP14n3 = new JTextField();		// Eingabefeld CF-Name
	private JLabel lblP14n1 = new JLabel(); 			// "Neue Bewertungsmethode"
	private JLabel lblP14n2 = new JLabel(); 			// "Name der Bewertungsmethode"
	private JLabel lblP14n3 = new JLabel(); 			// "Wirkungskategorie"
	private JLabel lblP14n4 = new JLabel(); 			// "Charakterisierungsfaktor"
	private JLabel lblP14n5 = new JLabel(); 			// Status
	private JButton btnP14n1 = new JButton(); 			// "neue Bewertungsmethode anlegen"
	private JButton btnP14n2 = new JButton(); 			// Wirkungskategorie "hinzufügen"
	private JButton btnP14n3 = new JButton(); 			// Charakterisierungsfaktor "hinzufügen"
	private JButton btnP14n4 = new JButton(); 			// "fertig"
	//
	// Panel 15; Liste der Bewertungsmethoden
	//
	private JPanel panel_15 = new JPanel();
	private JLabel lblP15n1 = new JLabel();
	private JTable bmTable 		= new JTable();
	DefaultTableModel bmTableModel 		= new DefaultTableModel(0,3);
	//
	// Panel 16; Berechnung der Wirkungsabschätzung
	//
	private JPanel panel_16 = new JPanel();	
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
	DefaultTableModel waTableModel 		= new DefaultTableModel(0,3);

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
		
		//
		// Panel 1; Neuer Fluss
		//	
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
		panel_01.add(comboBox, "cell 2 2,grow");	
		panel_01.add(lblP01n4, "cell 1 3,grow");	
		JComboBox<FlowUnit> comboBox_1 = new JComboBox<FlowUnit>();
		comboBox_1.setModel(new DefaultComboBoxModel<FlowUnit>(FlowUnit.values()));
		panel_01.add(comboBox_1, "cell 2 3,grow");			
		panel_01.add(btnP01n1, "cell 1 4 2 1,alignx center");
		panel_01.add(lblP01n5, "cell 0 5 4 1,alignx center");		
		//
		// Panel 2; Neues Prozessmodul
		//
		panel.add(panel_02, "neuModul");
		panel_02.setLayout(new MigLayout("", "[108px,grow][108px][108px][108px,grow]", 
				"[20px][20px][20px][20px][20px][20px][20px][20px][20px][20px][20px][20px,grow]"));		
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
		panel_02.add(btnP02n2, "cell 1 5 2 1,alignx center");	
		panel_02.add(lblP02n6, "cell 1 6,grow");	
		txtP02n4.setText("");
		panel_02.add(txtP02n4, "cell 2 6,grow");
		txtP02n4.setColumns(10);
		txtP02n4.setEnabled(false);	
		panel_02.add(lblP02n7, "cell 1 7,grow");
		txtP02n5.setText("");
		panel_02.add(txtP02n5, "cell 2 7,grow");
		txtP02n5.setColumns(10);
		txtP02n5.setEnabled(false);		
		btnP02n4.setEnabled(false);
		panel_02.add(btnP02n4, "cell 1 8,alignx center");
		btnP02n3.setEnabled(false);
		panel_02.add(btnP02n3, "cell 2 8,alignx center");
		panel_02.add(lblP02n5, "cell 0 9 4 1,alignx center");		
		//
		// Panel 3; Neues Produktsystem
		//		
		panel.add(panel_3, "neuProdukt");	
		panel_3.setLayout(new MigLayout("", "[108px,grow][108px][108px][108px,grow]", 
				"[20px][20px][20px][20px][20px][20px][20px][20px][20px][20px][20px][20px,grow]"));		
		lblP03n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_3.add(lblP03n1, "flowy,cell 1 0 2 1,alignx center,growy");	
		panel_3.add(lblP03n2, "cell 1 1,grow");		
		txtP03n1.setText("");
		panel_3.add(txtP03n1, "cell 2 1,grow");
		txtP03n1.setColumns(10);		
		panel_3.add(btnP03n1, "cell 1 2 2 1,alignx center");
		panel_3.add(lblP03n3, "cell 1 3,grow");
		txtP03n2.setText("");
		panel_3.add(txtP03n2, "cell 2 3,grow");
		txtP03n2.setColumns(10);
		txtP03n2.setEnabled(false);		
		btnP03n2.setEnabled(false);
		panel_3.add(btnP03n2, "cell 1 4,alignx center");		
		btnP03n3.setEnabled(false);
		panel_3.add(btnP03n3, "cell 2 4,alignx center");	
		panel_3.add(lblP03n4, "cell 1 5,grow");
		txtP03n3.setText("");
		panel_3.add(txtP03n3, "cell 2 5,grow");
		txtP03n3.setColumns(10);
		txtP03n3.setEnabled(false);		
		panel_3.add(lblP03n5, "cell 1 6,grow");
		txtP03n4.setText("");
		panel_3.add(txtP03n4, "cell 2 6,grow");
		txtP03n4.setColumns(10);
		txtP03n4.setEnabled(false);		
		btnP03n4.setEnabled(false);
		panel_3.add(btnP03n4, "cell 1 7,alignx center");		
		btnP03n5.setEnabled(false);
		panel_3.add(btnP03n5, "cell 2 7,alignx center");		
		panel_3.add(lblP03n6, "cell 1 8,grow");
		txtP03n5.setText("");
		panel_3.add(txtP03n5, "cell 2 8,grow");
		txtP03n5.setColumns(10);
		txtP03n5.setEnabled(false);		
		btnP03n6.setEnabled(false);
		panel_3.add(btnP03n6, "cell 1 9,alignx center");	
		btnP03n7.setEnabled(false);
		panel_3.add(btnP03n7, "cell 2 9,alignx center");		
		panel_3.add(lblP03n7, "cell 0 10 4 1,alignx center");
		//
		// Panel 4; Info
		//
		panel.add(panel_4, "leer");
		panel_4.setLayout(new MigLayout("", "[108px,grow][200px][108px,grow]", 
				"[20px][20px][40px][20px][20px][20px][20px,grow][20px]"));		
		lblInfo1.setFont(new Font("Tahoma", Font.BOLD, 26));
		panel_4.add(lblInfo1, "cell 1 2,alignx center,aligny top");		
		lblInfo2.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_4.add(lblInfo2, "cell 1 3,alignx center,aligny top");		
		lblInfo3.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_4.add(lblInfo3, "cell 1 4,alignx center,aligny top");		
		lblInfo4.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_4.add(lblInfo4, "cell 1 5,alignx center,aligny top");		
		lblInfo5.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_4.add(lblInfo5, "cell 1 7,alignx center,aligny top");
		//
		// Panel 5; Sprachauswahl
		//
		panel.add(panel_5, "lang");
		panel_5.setLayout(new MigLayout("", "[108px,grow][108px][108px][108px,grow]", 
				"[20px][20px][20px][20px][20px][20px][20px,grow]"));		
		lblP05n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_5.add(lblP05n1, "flowy,cell 1 0 2 1,alignx center,growy");		
		panel_5.add(lblP05n2, "cell 1 1,grow");		
		comboBox2.setModel(new DefaultComboBoxModel<Language>(Language.values()));
		panel_5.add(comboBox2, "cell 2 1,grow");	
		panel_5.add(btn05n1, "cell 1 2 2 1,alignx center");		
		lblInfo1.setText(GuiStrings.getGS("head1", l));
		lblInfo2.setText(GuiStrings.getGS("info1", l));
		lblInfo3.setText(GuiStrings.getGS("info2", l));
		lblInfo4.setText(GuiStrings.getGS("info3", l));
		lblInfo5.setText(GuiStrings.getGS("head2", l)+"     "+GuiStrings.getGS("date", l));		
		//
		// Panel 6; Flussliste
		//
		panel.add(panel_6, "listeFluss");		
		panel_6.setLayout(new MigLayout("", "[74px,grow]", "[14px][grow]"));			
		lblP06n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_6.add(lblP06n1, "cell 0 0,alignx center,aligny top");		
		panel_6.add(new JScrollPane(flowsTable), "cell 0 1,alignx center,aligny top");
		//
		// Panel 7; Prozessmodulliste
		//
		panel.add(panel_7, "listePM");		
		panel_7.setLayout(new MigLayout("", "[74px,grow]", "[14px][grow]"));		
		lblP07n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_7.add(lblP07n1, "cell 0 0,alignx center,aligny top");		
		panel_7.add(new JScrollPane(pmTable), "cell 0 1,alignx center,aligny top");	
		//
		// Panel 8; Produktsystemliste
		//
		panel.add(panel_8, "listePS");		
		panel_8.setLayout(new MigLayout("", "[74px,grow]", "[14px][grow]"));		
		lblP08n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_8.add(lblP08n1, "cell 0 0,alignx center,aligny top");		
		panel_8.add(new JScrollPane(psTable), "cell 0 1,alignx center,aligny top");	
		//
		// Panel 9; LCI Berechnung
		//
		panel.add(panel_9, "berechnen");		
		panel_9.setLayout(new MigLayout("", "[74px,grow]", "[14px][grow]"));		
		lblP09n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_9.add(lblP09n1, "cell 0 0,alignx center,aligny top");		
		panel_9.add(new JScrollPane(lciTable), "cell 0 1,alignx center,aligny top");
		//
		// Panel 10; Neue Wirkungskategorie
		//
		panel.add(panel_10, "neuKategorie");
		panel_10.setLayout(new MigLayout("", "[108px,grow][108px][108px][108px,grow]", 
				"[20px][20px][20px][20px][20px][20px][20px][20px][20px][20px][20px][20px,grow]"));		
		lblP10n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_10.add(lblP10n1, "flowy,cell 1 0 2 1,alignx center,growy");	
		panel_10.add(lblP10n2, "cell 1 1,grow");	
		txtP10n1.setText("");
		panel_10.add(txtP10n1, "cell 2 1,grow");	
		txtP10n1.setColumns(10);
		panel_10.add(lblP10n3, "cell 1 2,grow");	
		txtP10n2.setText("");
		panel_10.add(txtP10n2, "cell 2 2,grow");	
		txtP10n2.setColumns(10);
		panel_10.add(btnP10n1, "cell 1 3 2 1,alignx center");	
		btnP10n1.setEnabled(true);
		panel_10.add(lblP10n4, "cell 0 4 4 1,alignx center");	
		//
		// Panel 11; Kategorieliste
		//
		panel.add(panel_11, "categories");		
		panel_11.setLayout(new MigLayout("", "[74px,grow]", "[14px][grow]"));		
		lblP11n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_11.add(lblP11n1, "cell 0 0,alignx center,aligny top");		
		panel_11.add(new JScrollPane(catTable), "cell 0 1,alignx center,aligny top");
		//
		// Panel 12; Neuer Charakterisierungsfaktor
		//
		panel.add(panel_12, "neuCF");
		panel_12.setLayout(new MigLayout("", "[108px,grow][108px][108px][108px,grow]", 
				"[20px][20px][20px][20px][20px][20px][20px][20px][20px][20px][20px][20px,grow]"));		
		lblP12n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_12.add(lblP12n1, "flowy,cell 1 0 2 1,alignx center,growy");	
		panel_12.add(lblP12n2, "cell 1 1,grow");		
		txtP12n1.setText("");
		panel_12.add(txtP12n1, "cell 2 1,grow");
		txtP12n1.setColumns(10);					
		panel_12.add(lblP12n3, "cell 1 2,grow");	
		txtP12n2.setText("");
		panel_12.add(txtP12n2, "cell 2 2,grow");
		txtP12n2.setColumns(10);		
		panel_12.add(lblP12n4, "cell 1 3,grow");
		txtP12n3.setText("");
		panel_12.add(txtP12n3, "cell 2 3,grow");
		txtP12n3.setColumns(10);		
		panel_12.add(lblP12n5, "cell 1 4,grow");
		txtP12n4.setText("");
		panel_12.add(txtP12n4, "cell 2 4,grow");
		txtP12n4.setColumns(10);	
		panel_12.add(btnP12n1, "cell 1 5 2 1,alignx center");		
		panel_12.add(lblP12n6, "cell 1 6,grow");	
		txtP12n5.setText("");
		panel_12.add(txtP12n5, "cell 2 6,grow");
		txtP12n5.setColumns(10);
		txtP12n5.setEnabled(false);			
		panel_12.add(lblP12n7, "cell 1 7,grow");
		txtP12n6.setText("");
		panel_12.add(txtP12n6, "cell 2 7,grow");
		txtP12n6.setColumns(10);		
		txtP12n6.setEnabled(false);			
		btnP12n2.setEnabled(false);
		panel_12.add(btnP12n2, "cell 1 8 2 1,alignx center");
		panel_12.add(lblP12n8, "cell 0 9 4 1,alignx center");				
		//
		// Panel 13; CF-iste
		//
		panel.add(panel_13, "cfList");		
		panel_13.setLayout(new MigLayout("", "[74px,grow]", "[14px][grow]"));		
		lblP13n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_13.add(lblP13n1, "cell 0 0,alignx center,aligny top");		
		panel_13.add(new JScrollPane(cfTable), "cell 0 1,alignx center,aligny top");
		//
		// Panel 14; Neue Bewertungsmethode (LCIA)
		//
		panel.add(panel_14, "neuLCIA");
		panel_14.setLayout(new MigLayout("", "[108px,grow][108px][108px][108px,grow]", 
				"[20px][20px][20px][20px][20px][20px][20px][20px][20px][20px][20px][20px,grow]"));		
		lblP14n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_14.add(lblP14n1, "flowy,cell 1 0 2 1,alignx center,growy");		
		panel_14.add(lblP14n2, "cell 1 1,grow");		
		txtP14n1.setText("");
		panel_14.add(txtP14n1, "cell 2 1,grow");
		txtP14n1.setColumns(10);			
		btnP14n1.setEnabled(true);
		panel_14.add(btnP14n1, "cell 1 2 2 1,alignx center");			
		panel_14.add(lblP14n3, "cell 1 3,grow");		
		txtP14n2.setText("");
		panel_14.add(txtP14n2, "cell 2 3,grow");
		txtP14n2.setColumns(10);	
		txtP14n2.setEnabled(false);		
		btnP14n2.setEnabled(false);
		panel_14.add(btnP14n2, "cell 1 4 2 1,alignx center");				
		panel_14.add(lblP14n4, "cell 1 5,grow");		
		txtP14n3.setText("");
		panel_14.add(txtP14n3, "cell 2 5,grow");
		txtP14n3.setColumns(10);	
		txtP14n3.setEnabled(false);		
		btnP14n3.setEnabled(false);
		panel_14.add(btnP14n3, "cell 1 6,alignx center");		
		btnP14n4.setEnabled(false);
		panel_14.add(btnP14n4, "cell 2 6,alignx center");			
		panel_14.add(lblP14n5, "cell 0 7 4 1,alignx center");	
		//
		// Panel 15; Liste der Bewertungsmethoden
		//
		panel.add(panel_15, "bmList");		
		panel_15.setLayout(new MigLayout("", "[74px,grow]", "[14px][grow]"));		
		lblP15n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_15.add(lblP15n1, "cell 0 0,alignx center,aligny top");		
		panel_15.add(new JScrollPane(bmTable), "cell 0 1,alignx center,aligny top");
		//
		// Panel 16; Berechnung der Wirkungsabschätzung
		//
		panel.add(panel_16, "berechnen2");
		panel_16.setLayout(new MigLayout("", "[108px,grow][108px][108px][108px,grow]", 
				"[20px][20px][20px][20px][20px][20px][20px,grow]"));	
		lblP16n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_16.add(lblP16n1, "cell 1 0 2 1,alignx center,aligny top");		
		panel_16.add(lblP16n2, "cell 1 1,grow");
		panel_16.add(cobP16n1, "cell 2 1,grow");
		panel_16.add(lblP16n3, "cell 1 2,grow");
		panel_16.add(cobP16n2, "cell 2 2,grow");
		panel_16.add(lblP16n4, "cell 1 3,grow");
		panel_16.add(cobP16n3, "cell 2 3,grow");
		panel_16.add(lblP16n5, "cell 1 4,grow");
		panel_16.add(cobP16n4, "cell 2 4,grow");	
		cobP16n2.setEnabled(false);
		cobP16n3.setEnabled(false);
		cobP16n4.setEnabled(false);		
		btnP16n1.setEnabled(false);
		panel_16.add(btnP16n1, "cell 1 5 2 0,alignx center");
		panel_16.add(new JScrollPane(waTable), "cell 1 6 2 0,alignx center,aligny top");
		
		
		cl.show(panel, "leer");
	
		/*
		 * Organisation der Menuleiste
		 */
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnDatei = new JMenu(GuiStrings.getGS("mp6",l));
		menuBar.add(mnDatei);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem();
		mntmNewMenuItem_4.setAction(xmlExportAction);
		mnDatei.add(mntmNewMenuItem_4);	
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem();
		mntmNewMenuItem_5.setAction(xmlImportAction);
		mnDatei.add(mntmNewMenuItem_5);
		
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
		
		JMenuItem mntmCategory = new JMenuItem();
		mntmCategory.setAction(newCategoryAction);
		mnNew.add(mntmCategory);
		
		JMenuItem mntmCF = new JMenuItem();
		mntmCF.setAction(newCFAction);
		mnNew.add(mntmCF);
		
		JMenuItem mntmLCIAnew = new JMenuItem();
		mntmLCIAnew.setAction(newLCIAAction);
		mnNew.add(mntmLCIAnew);
		
		JMenu mnListe = new JMenu(GuiStrings.getGS("mp4", l));
		menuBar.add(mnListe);
		
		JMenuItem mntmFlsse = new JMenuItem();
		mntmFlsse.setAction(listFlowAction);
		mnListe.add(mntmFlsse);
		
		JMenuItem mntmProzessmodule = new JMenuItem();
		mntmProzessmodule.setAction(listModuleAction);
		mnListe.add(mntmProzessmodule);
		
		JMenuItem mntmProduktsysteme = new JMenuItem();
		mntmProduktsysteme.setAction(listProductAction);
		mnListe.add(mntmProduktsysteme);
		
		JMenuItem mntmCategories = new JMenuItem();
		mntmCategories.setAction(listCategoriesAction);
		mnListe.add(mntmCategories);
		
		JMenuItem mntmCFs = new JMenuItem();
		mntmCFs.setAction(listCFAction);
		mnListe.add(mntmCFs);
		
		JMenuItem mntmLCIAlist = new JMenuItem();
		mntmLCIAlist.setAction(listLCIAAction);
		mnListe.add(mntmLCIAlist);
		
		JMenu mnBerechnen = new JMenu(GuiStrings.getGS("mp5", l));
		menuBar.add(mnBerechnen);
		
		JMenuItem mntmLci = new JMenuItem();
		mntmLci.setAction(calculateAction);
		mnBerechnen.add(mntmLci);
		
		JMenuItem mntmLCIAcalc = new JMenuItem();
		mntmLCIAcalc.setAction(calculateLCIAAction);
		mnBerechnen.add(mntmLCIAcalc);
			
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
				LinkedHashMap<FlowType, String> ft = FlowTypeStringMap.getFTS(l);
				String typs = comboBox.getItemAt(comboBox.getSelectedIndex());
				FlowType typ = FlowType.Elementary;
				for (FlowType fte : ft.keySet()) {
					if (ft.get(fte) == typs) {
						typ = fte;
					}
				}

				FlowUnit einheit = comboBox_1.getItemAt(comboBox_1.getSelectedIndex());
				boolean nameOk = true;
				if (name.equals("")) {
					nameOk = false;
					lblP01n5.setText(GuiStrings.getGS("stat02", l));
				}
				if (name != name.replaceAll(" ","")) {
					nameOk = false;
					lblP01n5.setText(GuiStrings.getGS("stat20", l));
					txtP01n1.setText("");
				}								
				if (nameOk == true) {
					if (Flow.containsName(name)) {
						lblP01n5.setText(GuiStrings.getGS("stat03", l));
					} else {
						Flow.instance(name, typ, einheit);
						lblP01n5.setText(GuiStrings.getGS("stat04", l) + 
								Flow.getAllNames().size() + GuiStrings.getGS("stat05", l));
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
				boolean nameOk = true;
				if (name.equals("")) {
					nameOk = false;
					lblP02n5.setText(GuiStrings.getGS("stat02", l));
				} 
				if (name != name.replaceAll(" ","")) {
					nameOk = false;
					lblP02n5.setText(GuiStrings.getGS("stat20", l));
					txtP02n1.setText("");
				}
				if (NameCheck.containsFVName(name)) {
					nameOk = false;
					lblP02n5.setText(GuiStrings.getGS("stat03", l));
					txtP02n1.setText("");
				}			
				if (nameOk == true) {
					ProcessModule.instance(name);
					lblP02n5.setText(GuiStrings.getGS("stat06", l) + 
							ProcessModule.getAllInstances().size() + GuiStrings.getGS("stat05", l));
					btnP02n1.setEnabled(false);
					txtP02n1.setEnabled(false);
					btnP02n2.setEnabled(true);
					txtP02n2.setEnabled(true);
					txtP02n3.setEnabled(true);	
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
					lblP02n5.setText(GuiStrings.getGS("stat07", l));
				} else {
					if (Flow.containsName(fname)) {
						Flow akFlow = Flow.getInstance(fname);
						String mname = txtP02n1.getText();
						ProcessModule.getInstance(mname).addFluss(akFlow, ValueType.MeanValue, menge);
						ProcessModule.getInstance(mname).addFluss(akFlow, ValueType.LowerBound, menge);
						ProcessModule.getInstance(mname).addFluss(akFlow, ValueType.UpperBound, menge);
						txtP02n2.setEnabled(false);
						txtP02n3.setEnabled(false);
						btnP02n2.setEnabled(false);
						txtP02n4.setText(txtP02n3.getText());
						txtP02n4.setEnabled(true);
						txtP02n5.setText(txtP02n3.getText());
						txtP02n5.setEnabled(true);
						btnP02n4.setEnabled(true);
						btnP02n3.setEnabled(false);
						txtP02n4.setEnabled(true);
						int anzPFlow = ProcessModule.getInstance(mname).getProduktflussvektor().size();
						int anzEFlow = ProcessModule.getInstance(mname).getElementarflussvektor().size();
						int anzGesamt = anzPFlow + anzEFlow;
						lblP02n5.setText(GuiStrings.getGS("stat08", l) + mname + GuiStrings.getGS("stat09", l) +
								anzGesamt + GuiStrings.getGS("stat10", l));
						
					} else {
						lblP02n5.setText(GuiStrings.getGS("stat11", l));
					}					
				}
			}
		});
		
		btnP02n4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String fug = txtP02n4.getText();
				String fog = txtP02n5.getText();
				Double fugv;
				Double fogv;
				try {
					fugv = Double.parseDouble(fug);
				} catch (NumberFormatException e){
					fugv = 0.0;
				}
				try {
					fogv = Double.parseDouble(fog);
				} catch (NumberFormatException e){
					fogv = 0.0;
				}
				String fmenge = txtP02n3.getText();
				Double menge;
				try {
					menge = Double.parseDouble(fmenge);
				} catch (NumberFormatException e){
					menge = 0.0;
				}
				if ((fugv > menge) || (fogv < menge)) {
					txtP02n4.setText(txtP02n3.getText());
					txtP02n5.setText(txtP02n3.getText());
					lblP02n5.setText(GuiStrings.getGS("stat21", l));
				} else {
					String mname = txtP02n1.getText();
					String fname = txtP02n2.getText();
					Flow akFlow = Flow.getInstance(fname);
					ProcessModule.getInstance(mname).addFluss(akFlow, ValueType.LowerBound, fugv);
					ProcessModule.getInstance(mname).addFluss(akFlow, ValueType.UpperBound, fogv);
					txtP02n2.setText("");
					txtP02n3.setText("");
					txtP02n4.setText("");
					txtP02n5.setText("");
					txtP02n2.setEnabled(true);
					txtP02n3.setEnabled(true);
					txtP02n4.setEnabled(false);
					txtP02n5.setEnabled(false);
					btnP02n2.setEnabled(true);
					btnP02n3.setEnabled(true);
					btnP02n4.setEnabled(false);
					lblP02n5.setText(GuiStrings.getGS("stat01", l));
					
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
				txtP02n4.setText("");
				txtP02n5.setText("");
				btnP02n2.setEnabled(false);
				btnP02n3.setEnabled(false);
				btnP02n4.setEnabled(false);
				txtP02n1.setEnabled(true);
				txtP02n2.setEnabled(false);
				txtP02n3.setEnabled(false);
				txtP02n4.setEnabled(false);
				txtP02n5.setEnabled(false);
				lblP02n5.setText(GuiStrings.getGS("stat01", l));
			}
		});

		/*
		 * neues Produktsystem
		 */
		
		btnP03n1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name = txtP03n1.getText();	
				boolean nameOk = true;
				if (name.equals("")) {
					nameOk = false;
					lblP03n7.setText(GuiStrings.getGS("stat02", l));
				} 
				if (name != name.replaceAll(" ","")) {
					nameOk = false;
					lblP03n7.setText(GuiStrings.getGS("stat20", l));
					txtP03n1.setText("");
				}	
				if (nameOk == true) {
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
						lblP03n7.setText(GuiStrings.getGS("stat03", l));
					} else {
						ProductSystem.instance(name, new LinkedHashMap<Flow, Double>(), 
								new LinkedList<Flow>());
						lblP03n7.setText(GuiStrings.getGS("stat12", l) + 
								ProductSystem.getAllInstances().size() + GuiStrings.getGS("stat05", l));

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
						lblP03n7.setText(GuiStrings.getGS("stat07", l));
					}
					if (modname.equals(txtP03n1.getText())) {
						lblP03n7.setText(GuiStrings.getGS("stat13", l));
					}
					if (ProductSystem.getAllInstances().get(txtP03n1.getText()).getModulliste().contains(ProcessModule.getInstance(modname)) ) {
						lblP03n7.setText(GuiStrings.getGS("stat31", l));
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
							ProductSystem.getAllInstances().get(txtP03n1.getText()).addProzessmodul(ProcessModule.getInstance(modname));
						} else {
							ProductSystem.getAllInstances().get(txtP03n1.getText()).addProzessmodul(ProductSystem.getAllInstances().get(modname));
						}
						lblP03n7.setText(GuiStrings.getGS("stat14", l) + txtP03n1.getText() +
								GuiStrings.getGS("stat15", l) + ProductSystem.getAllInstances().get(txtP03n1.getText()).getModulAnzahl() 
								+ GuiStrings.getGS("stat16", l));
						txtP03n2.setText("");
						btnP03n3.setEnabled(true);
					} else {
						lblP03n7.setText(GuiStrings.getGS("stat17", l));
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
				lblP03n7.setText(GuiStrings.getGS("stat01", l));
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
					lblP03n7.setText(GuiStrings.getGS("stat07", l));
				} else {
					if (Flow.containsName(fname)) {
						Flow akFlow = Flow.getInstance(fname);
						ProductSystem.getInstance(txtP03n1.getText()).addBedarf(akFlow, Double.parseDouble(txtP03n4.getText()));
						lblP03n7.setText(GuiStrings.getGS("stat18", l) + 
								ProductSystem.getInstance(txtP03n1.getText()).getBedarfsvektor().size() + GuiStrings.getGS("stat10", l));
						btnP03n5.setEnabled(true);	
						txtP03n3.setText("");
						txtP03n4.setText("");
					} else {
						lblP03n7.setText(GuiStrings.getGS("stat11", l));
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
				
				lblP03n7.setText(GuiStrings.getGS("stat01", l));
			}
		});
		
		btnP03n6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String vkname = txtP03n5.getText();
				if (vkname.equals("")) {
					lblP03n7.setText(GuiStrings.getGS("stat07", l));
				} else {					
					if (Flow.containsName(vkname)) {
						Flow akFlow = Flow.getInstance(vkname);
						ProductSystem.getInstance(txtP03n1.getText()).addVuK(akFlow);
						txtP03n5.setText("");
						lblP03n7.setText(GuiStrings.getGS("stat19", l) + 
								ProductSystem.getInstance(txtP03n1.getText()).getVorUndKoppelprodukte().size() + GuiStrings.getGS("stat10", l));										
					} else {
						lblP03n7.setText(GuiStrings.getGS("stat11", l));
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
				
				lblP03n7.setText(GuiStrings.getGS("stat01", l));
			}
			
		});
		
		/*
		 * Sprachauswahl
		 */
		
		btn05n1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				l = comboBox2.getItemAt(comboBox2.getSelectedIndex());
				frame.setTitle(GuiStrings.getGS("head1",l)+"   "+GuiStrings.getGS("head2",l));
				mnDatei.setText(GuiStrings.getGS("mp6",l));
				mnNew.setText(GuiStrings.getGS("mp1",l));
				mnListe.setText(GuiStrings.getGS("mp4",l));
				mnBerechnen.setText(GuiStrings.getGS("mp5",l));
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
				mntmFlsse.setText(GuiStrings.getGS("mp41",l));
				mntmFlsse.setToolTipText(GuiStrings.getGS("mp41e",l));
				mntmProzessmodule.setText(GuiStrings.getGS("mp42",l));
				mntmProzessmodule.setToolTipText(GuiStrings.getGS("mp42e",l));
				mntmProduktsysteme.setText(GuiStrings.getGS("mp43",l));
				mntmProduktsysteme.setToolTipText(GuiStrings.getGS("mp43e",l));
				mntmLci.setText(GuiStrings.getGS("mp51",l));
				mntmLci.setToolTipText(GuiStrings.getGS("mp51e",l));
				mntmNewMenuItem_2.setText(GuiStrings.getGS("mp21",l));
				mntmNewMenuItem_2.setToolTipText(GuiStrings.getGS("mp21e",l));
				mntmNewMenuItem_4.setText(GuiStrings.getGS("mp61",l));
				mntmNewMenuItem_4.setToolTipText(GuiStrings.getGS("mp61e",l));
				mntmNewMenuItem_5.setText(GuiStrings.getGS("mp62",l));
				mntmNewMenuItem_5.setToolTipText(GuiStrings.getGS("mp62e",l));
				mntmCategory.setText(GuiStrings.getGS("mp14",l));
				mntmCategory.setToolTipText(GuiStrings.getGS("mp14e",l));
				mntmCategories.setText(GuiStrings.getGS("mp44",l));
				mntmCategories.setToolTipText(GuiStrings.getGS("mp44e",l));
				mntmCF.setText(GuiStrings.getGS("mp15",l));
				mntmCF.setToolTipText(GuiStrings.getGS("mp15e",l));
				mntmCFs.setText(GuiStrings.getGS("mp45",l));
				mntmCFs.setToolTipText(GuiStrings.getGS("mp45e",l));
				mntmLCIAnew.setText(GuiStrings.getGS("mp16",l));
				mntmLCIAnew.setToolTipText(GuiStrings.getGS("mp16e",l));
				mntmLCIAlist.setText(GuiStrings.getGS("mp46",l));
				mntmLCIAlist.setToolTipText(GuiStrings.getGS("mp46e",l));
				mntmLCIAcalc.setText(GuiStrings.getGS("mp52",l));
				mntmLCIAcalc.setToolTipText(GuiStrings.getGS("mp52e",l));
				lblP05n1.setText(GuiStrings.getGS("mp31e", l));
				lblP05n2.setText(GuiStrings.getGS("mp31", l));
				btn05n1.setText(GuiStrings.getGS("bt01", l));
			}
		});
		
		/*
		 * neue Wirkungskategorie
		 */
		
		btnP10n1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name = txtP10n1.getText();	
				String wi = txtP10n2.getText();
				boolean inputOK = true;
				if (wi.equals("")) {
					lblP10n4.setText(GuiStrings.getGS("stat24",l));
					inputOK = false;
				} 
				if (name.equals("")) {
					lblP10n4.setText(GuiStrings.getGS("stat22",l));
					inputOK = false;
				} 
				if (ImpactCategory.containsName(name)) {
					lblP10n4.setText(GuiStrings.getGS("stat23",l));
					inputOK = false;
				}
				if (inputOK == true) {
					CategoryIndicator indi = CategoryIndicator.instance(wi);
					ImpactCategory.instance(name, indi);
					lblP10n4.setText(GuiStrings.getGS("stat25",l) + ImpactCategory.getAllInstances().size() + GuiStrings.getGS("stat05",l));
					txtP10n1.setText("");
					txtP10n2.setText("");
				}		
			}
		});
		
		/*
		 * neuer CF
		 */
		
		btnP12n1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String nameCF = txtP12n1.getText();
				String nameFl = txtP12n2.getText();
				String nameWK = txtP12n3.getText();
				String facText = txtP12n4.getText();
				boolean inputOK = true;
				if (nameCF.equals("") || nameFl.equals("") || nameWK.equals("") || facText.equals("")) {
					lblP12n8.setText(GuiStrings.getGS("stat07",l));
					inputOK = false;					
				}
				Double facVal = 0.0;
				try {
					facVal = Double.parseDouble(facText);
				} catch (NumberFormatException e){
					facVal = 0.0;
				}
				if (facVal <= 0.0) {
					lblP12n8.setText(GuiStrings.getGS("stat26",l));
					inputOK = false;	
				}
				if (ImpactCategory.containsName(nameWK) == false) {
					lblP12n8.setText(GuiStrings.getGS("stat27",l));
					inputOK = false;
				}
				if (Flow.containsName(nameFl) == false) {
					lblP12n8.setText(GuiStrings.getGS("stat11",l));
					inputOK = false;
				}
				if (CharacFactor.containsName(nameCF)) {
					lblP12n8.setText(GuiStrings.getGS("stat03",l));
					inputOK = false;
				}
				if (inputOK == true) {
					CharacFactor.instance(nameCF, Flow.getInstance(nameFl), ImpactCategory.getInstance(nameWK), facVal);
					txtP12n1.setEnabled(false);
					txtP12n2.setEnabled(false);
					txtP12n3.setEnabled(false);
					txtP12n4.setEnabled(false);
					txtP12n5.setText(facText);
					txtP12n5.setEnabled(true);
					txtP12n6.setText(facText);
					txtP12n6.setEnabled(true);
					btnP12n1.setEnabled(false);
					btnP12n2.setEnabled(true);
				}				
			}			
		});
		
		btnP12n2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String lbText = txtP12n5.getText();
				String obText = txtP12n6.getText();
				Double lbv;
				Double obv;
				try {
					lbv = Double.parseDouble(lbText);
				} catch (NumberFormatException e){
					lbv = 0.0;
				}
				try {
					obv = Double.parseDouble(obText);
				} catch (NumberFormatException e){
					obv = 0.0;
				}
				String facText = txtP12n4.getText();
				Double facVal = Double.parseDouble(facText);
				if ((lbv > facVal) || (obv < facVal) || (lbv < 0)) {
					txtP12n5.setText(txtP12n4.getText());
					txtP12n6.setText(txtP12n4.getText());
					lblP12n8.setText(GuiStrings.getGS("stat21", l));
				} else {
					String nameCF = txtP12n1.getText();
					CharacFactor.setLowerBound(nameCF, lbv);
					CharacFactor.setUpperBound(nameCF, obv);
					txtP12n1.setText("");
					txtP12n2.setText("");
					txtP12n3.setText("");
					txtP12n4.setText("");
					txtP12n5.setText("");
					txtP12n6.setText("");
					txtP12n1.setEnabled(true);
					txtP12n2.setEnabled(true);
					txtP12n3.setEnabled(true);
					txtP12n4.setEnabled(true);
					txtP12n5.setEnabled(false);
					txtP12n6.setEnabled(false);
					btnP12n1.setEnabled(true);
					btnP12n2.setEnabled(false);		
					lblP12n8.setText(GuiStrings.getGS("stat01", l));
				}
				
			}
			
		});		
		
		/*
		 * neu LCIA
		 */
		
		btnP14n1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String nameILCA = txtP14n1.getText();
				boolean inputOK = true;
				if (nameILCA.equals("")) {
					lblP14n5.setText(GuiStrings.getGS("stat02",l));
					inputOK = false;					
				}
				if (LCIAMethod.containsName(nameILCA)) {
					lblP14n5.setText(GuiStrings.getGS("stat03",l));
					inputOK = false;
				}
				if (inputOK == true) {
					LCIAMethod.instance(nameILCA);
					txtP14n1.setEnabled(false);
					txtP14n2.setEnabled(true);
					txtP14n3.setEnabled(true);
					btnP14n1.setEnabled(false);
					btnP14n2.setEnabled(true);
					btnP14n3.setEnabled(true);
					lblP14n5.setText(GuiStrings.getGS("stat28",l) + 
							LCIAMethod.instance(nameILCA).categoryList().size() +
							GuiStrings.getGS("stat29",l) +
							LCIAMethod.instance(nameILCA).getFactorSet().size() +
							GuiStrings.getGS("stat05",l));
				}			
			}			
		});

		btnP14n2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nameILCA = txtP14n1.getText();
				String nameWK = txtP14n2.getText();
				boolean inputOK = true;
				if (ImpactCategory.containsName(nameWK) == false) {
					lblP14n5.setText(GuiStrings.getGS("stat27",l));
					inputOK = false;					
				}
				if (inputOK == true) {
					LCIAMethod.instance(nameILCA).addCategory(ImpactCategory.getInstance(nameWK));
					btnP14n4.setEnabled(true);
					lblP14n5.setText(GuiStrings.getGS("stat28",l) + 
							LCIAMethod.instance(nameILCA).categoryList().size() +
							GuiStrings.getGS("stat29",l) +
							LCIAMethod.instance(nameILCA).getFactorSet().size() +
							GuiStrings.getGS("stat05",l));
				}			
			}
			
		});
		btnP14n3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nameILCA = txtP14n1.getText();
				String nameCF = txtP14n3.getText();
				boolean inputOK = true;
				if (CharacFactor.containsName(nameCF) == false) {
					lblP14n5.setText(GuiStrings.getGS("stat30",l));
					inputOK = false;					
				}
				if (inputOK == true) {
					LCIAMethod.instance(nameILCA).addFactor(CharacFactor.getInstance(nameCF));
					btnP14n4.setEnabled(true);				
					lblP14n5.setText(GuiStrings.getGS("stat28",l) + 
							LCIAMethod.instance(nameILCA).categoryList().size() +
							GuiStrings.getGS("stat29",l) +
							LCIAMethod.instance(nameILCA).getFactorSet().size() +
							GuiStrings.getGS("stat05",l));
				}
			}
		});
		btnP14n4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnP14n1.setEnabled(true);
				btnP14n2.setEnabled(false);
				btnP14n3.setEnabled(false);
				btnP14n4.setEnabled(false);
				txtP14n1.setEnabled(true);
				txtP14n2.setEnabled(false);
				txtP14n3.setEnabled(false);
				txtP14n1.setText("");
				txtP14n2.setText("");
				txtP14n3.setText("");
				lblP14n5.setText(GuiStrings.getGS("stat01", l));
			}
		});
		
		
		/*
		 * Wirkungsabschätzung
		 */
		
		cobP16n1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cobP16n2.setEnabled(true);
				cobP16n3.setEnabled(false);
				waTableModel.setRowCount(0);
				if (cobP16n1.getSelectedItem().toString().equals(GuiStrings.getGS("mp12", l))) {
					Vector<String> nameVektor = new Vector<String>();
					for (String obName : ProcessModule.getAllInstances().keySet()) {
						nameVektor.addElement(obName);
					}
					cobP16n2.setModel(new DefaultComboBoxModel<String>(nameVektor));
				}
				if (cobP16n1.getSelectedItem().toString().equals(GuiStrings.getGS("mp13", l))) {
					Vector<String> nameVektor = new Vector<String>();
					for (String obName : ProductSystem.getAllInstances().keySet()) {
						nameVektor.addElement(obName);
					}
					cobP16n2.setModel(new DefaultComboBoxModel<String>(nameVektor));
				}				
			}		
		});
		cobP16n2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Vector<String> methVektor = new Vector<String>();
				for (String methName : LCIAMethod.getAllLMs().keySet()) {
					methVektor.addElement(methName);
				}
				cobP16n3.setModel(new DefaultComboBoxModel<String>(methVektor));
				cobP16n3.setEnabled(true);
			}
		});
		cobP16n3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cobP16n4.setEnabled(true);
			}
		});
		cobP16n4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnP16n1.setEnabled(true);
			}
		});
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
				if (cobP16n1.getSelectedItem().toString().equals(GuiStrings.getGS("mp12", l))) {
					ProcessModule akObj = ProcessModule.getInstance(cobP16n2.getSelectedItem().toString());
					LCIAMethod akMeth = LCIAMethod.instance(cobP16n3.getSelectedItem().toString());
					for (ImpactCategory wName : akObj.getImpactValueMap(akMeth).keySet()) {
						waTableModel.addRow(new Object[] {wName.getName(), 
								wName.getEinheit().getName(),
								akObj.getImpactValueMap(akMeth).get(wName).get(vt)});
					}
				}
				if (cobP16n1.getSelectedItem().toString().equals(GuiStrings.getGS("mp13", l))) {
					ProductSystem akObj = ProductSystem.getInstance(cobP16n2.getSelectedItem().toString());
					LCIAMethod akMeth = LCIAMethod.instance(cobP16n3.getSelectedItem().toString());
					for (ImpactCategory wName : akObj.getImpactValueMap(akMeth).keySet()) {					
						waTableModel.addRow(new Object[] {wName.getName(), 
								wName.getEinheit().getName(),
								akObj.getImpactValueMap(akMeth).get(wName).get(vt)});							
					}
				}

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
		@Override
		public void actionPerformed(ActionEvent e) {			
			lblP01n1.setText(GuiStrings.getGS("p01n1", l));
			lblP01n2.setText(GuiStrings.getGS("p01n2", l));
			lblP01n3.setText(GuiStrings.getGS("p01n3", l));
			lblP01n4.setText(GuiStrings.getGS("p01n4", l));
			lblP01n5.setText(GuiStrings.getGS("stat01", l));
			btnP01n1.setText(GuiStrings.getGS("bt01", l));
			LinkedHashMap<FlowType, String> ft = FlowTypeStringMap.getFTS(l);
			String[] fta = new String[ft.size()];
			int i=0;
			for (FlowType f : FlowType.values()) {
				String ftl = ft.get(f);
				fta[i] = ftl;
				i++;
			}
			comboBox.setModel(new DefaultComboBoxModel<String>(fta));
			cl.show(panel, "neuFluss");
						
		}
	}
	private class newModuleAction extends AbstractAction {
		private static final long serialVersionUID = 6190606710625748526L;
		public newModuleAction() {
			putValue(NAME, GuiStrings.getGS("mp12", l));
			putValue(SHORT_DESCRIPTION, GuiStrings.getGS("mp12e", l));
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			lblP02n1.setText(GuiStrings.getGS("p02n1", l));
			lblP02n2.setText(GuiStrings.getGS("p02n2", l));
			lblP02n3.setText(GuiStrings.getGS("p01n2", l));
			lblP02n4.setText(GuiStrings.getGS("p02n4", l));
			lblP02n5.setText(GuiStrings.getGS("stat01", l));
			lblP02n6.setText(GuiStrings.getGS("p02n5", l));
			lblP02n7.setText(GuiStrings.getGS("p02n6", l));
			btnP02n1.setText(GuiStrings.getGS("bt02", l));
			btnP02n2.setText(GuiStrings.getGS("bt03", l));
			btnP02n3.setText(GuiStrings.getGS("bt04", l));
			btnP02n4.setText(GuiStrings.getGS("bt10", l));
			cl.show(panel, "neuModul");
		}
	}
	private class newProductAction extends AbstractAction {
		private static final long serialVersionUID = -7757652453649226474L;
		public newProductAction() {
			putValue(NAME, GuiStrings.getGS("mp13", l));
			putValue(SHORT_DESCRIPTION, GuiStrings.getGS("mp13e", l));
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			lblP03n1.setText(GuiStrings.getGS("p03n1", l));
			lblP03n2.setText(GuiStrings.getGS("p03n2", l));
			lblP03n3.setText(GuiStrings.getGS("p03n3", l));
			lblP03n4.setText(GuiStrings.getGS("p03n4", l));
			lblP03n5.setText(GuiStrings.getGS("p02n4", l));
			lblP03n6.setText(GuiStrings.getGS("p03n6", l));
			lblP03n7.setText(GuiStrings.getGS("stat01", l));
			btnP03n1.setText(GuiStrings.getGS("bt05", l));
			btnP03n2.setText(GuiStrings.getGS("bt06", l));
			btnP03n3.setText(GuiStrings.getGS("bt07", l));
			btnP03n4.setText(GuiStrings.getGS("bt08", l));
			btnP03n5.setText(GuiStrings.getGS("bt07", l));
			btnP03n6.setText(GuiStrings.getGS("bt09", l));
			btnP03n7.setText(GuiStrings.getGS("bt04", l));
			cl.show(panel, "neuProdukt");
		}
	}
	
	private class aboutAction extends AbstractAction {
		private static final long serialVersionUID = 8545097902506476895L;
		public aboutAction() {
			putValue(NAME, GuiStrings.getGS("mp21", l));
			putValue(SHORT_DESCRIPTION, GuiStrings.getGS("mp21e", l));
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			lblInfo1.setText(GuiStrings.getGS("head1", l));
			lblInfo2.setText(GuiStrings.getGS("info1", l));
			lblInfo3.setText(GuiStrings.getGS("info2", l));
			lblInfo4.setText(GuiStrings.getGS("info3", l));
			lblInfo5.setText(GuiStrings.getGS("head2", l)+"     "+GuiStrings.getGS("date", l));			
			cl.show(panel, "leer");
		}
	}
	
	private class prefsAction extends AbstractAction {
		private static final long serialVersionUID = 8545097902306476895L;
		public prefsAction() {
			putValue(NAME, GuiStrings.getGS("mp31", l));
			putValue(SHORT_DESCRIPTION, GuiStrings.getGS("mp31e", l));
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			lblP05n1.setText(GuiStrings.getGS("mp31e", l));
			lblP05n2.setText(GuiStrings.getGS("mp31", l));
			btn05n1.setText(GuiStrings.getGS("bt01", l));			
			cl.show(panel, "lang");
		}
	}
	
	private class listFlowAction extends AbstractAction {
		private static final long serialVersionUID = 8545097902306456895L;
		public listFlowAction() {
			putValue(NAME, GuiStrings.getGS("mp41", l));
			putValue(SHORT_DESCRIPTION, GuiStrings.getGS("mp41e", l));
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			lblP06n1.setText(GuiStrings.getGS("mp41e", l));
			flowsTableModel.setRowCount(0);
			flowsTable.setModel(flowsTableModel);
			TableColumnModel tcm = flowsTable.getColumnModel();
			tcm.getColumn(0).setHeaderValue(GuiStrings.getGS("p06n1", l));
			tcm.getColumn(1).setHeaderValue(GuiStrings.getGS("p01n3", l));
			tcm.getColumn(2).setHeaderValue(GuiStrings.getGS("p01n4", l));
			for(String flussname : Flow.getAllInstances().keySet()) {
				Flow fluss = Flow.getInstance(flussname);			
				flowsTableModel.addRow(new Object[] {fluss.getName(), 
						FlowTypeStringMap.getFTS(l).get(fluss.getType()), fluss.getEinheit()});			
			}			
			cl.show(panel, "listeFluss");
		}
	}
	
	private class listModuleAction extends AbstractAction {
		private static final long serialVersionUID = 8545097602306456895L;
		public listModuleAction() {
			putValue(NAME, GuiStrings.getGS("mp42", l));
			putValue(SHORT_DESCRIPTION, GuiStrings.getGS("mp42e", l));
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			lblP07n1.setText(GuiStrings.getGS("mp42e", l));
			pmTableModel.setRowCount(0);
			pmTable.setModel(pmTableModel);
			TableColumnModel tcm = pmTable.getColumnModel();
			tcm.getColumn(0).setHeaderValue(GuiStrings.getGS("mp12", l));
			tcm.getColumn(1).setHeaderValue(GuiStrings.getGS("mp11", l));
			tcm.getColumn(2).setHeaderValue(GuiStrings.getGS("p01n3", l));
			tcm.getColumn(3).setHeaderValue(GuiStrings.getGS("p02n4", l));
			for(String mn : ProcessModule.getAllInstances().keySet()) {
				ProcessModule akModul = ProcessModule.getInstance(mn);
				pmTableModel.addRow(new Object[] {mn, "", "", ""});
				for(Flow pf : akModul.getElementarflussvektor().keySet()){
					for (ValueType vt : akModul.getElementarflussvektor().get(pf).keySet()) {
						pmTableModel.addRow(new Object[] {"", pf.getName(), 
								ValueTypeStringMap.getFVTS(l).get(vt),
								akModul.getElementarflussvektor().get(pf).get(vt)});
					}
				}						
				for(Flow pf : akModul.getProduktflussvektor().keySet()){
					for (ValueType vt : akModul.getProduktflussvektor().get(pf).keySet()) {
						pmTableModel.addRow(new Object[] {"", pf.getName(), 
								ValueTypeStringMap.getFVTS(l).get(vt),
								akModul.getProduktflussvektor().get(pf).get(vt)});							
					}							
				}
			}	
			cl.show(panel, "listePM");
		}
	}
	
	private class listProductAction extends AbstractAction {
		private static final long serialVersionUID = 8545197602306456895L;
		public listProductAction() {
			putValue(NAME, GuiStrings.getGS("mp43", l));
			putValue(SHORT_DESCRIPTION, GuiStrings.getGS("mp43e", l));
		}
		@Override
		public void actionPerformed(ActionEvent e) {	
			lblP08n1.setText(GuiStrings.getGS("mp43e", l));
			psTableModel.setRowCount(0);
			psTable.setModel(psTableModel);
			TableColumnModel tcm = psTable.getColumnModel();
			tcm.getColumn(0).setHeaderValue(GuiStrings.getGS("mp13", l));
			tcm.getColumn(1).setHeaderValue(GuiStrings.getGS("p08n1", l));
			tcm.getColumn(2).setHeaderValue(GuiStrings.getGS("p08n2", l));
			for(String psn : ProductSystem.getAllInstances().keySet()) {
				psTableModel.addRow(new Object[] {psn, "", ""});
				for (FlowValueMaps mnif : ProductSystem.getInstance(psn).getModulliste()){
					String mni = mnif.getName();
					boolean typmod = false;
					for(String modn2 : ProcessModule.getAllInstances().keySet()) {
						if (mni.equals(modn2)) {
							typmod = true;
						}
					}
					if (typmod == true){
						psTableModel.addRow(new Object[] {"",GuiStrings.getGS("mp12", l), mni});							
					} else {
						psTableModel.addRow(new Object[] {"",GuiStrings.getGS("p08n3", l), mni});	
					}					
				}
				for (Flow bvf : ProductSystem.getInstance(psn).getBedarfsvektor().keySet()) {
					psTableModel.addRow(new Object[] {"" ,GuiStrings.getGS("p08n4", l) 
							,"" + bvf.getName() + " (" + 
									ProductSystem.getInstance(psn).getBedarfsvektor().get(bvf) + 
							" " + bvf.getEinheit()+")"});
				}
				for (Flow vk : ProductSystem.getInstance(psn).getVorUndKoppelprodukte()) {
					psTableModel.addRow(new Object[] {"" ,GuiStrings.getGS("p03n6", l) 
							,vk.getName() });		
				}
			}
			cl.show(panel, "listePS");
		}
	}
	
	private class calculateAction extends AbstractAction {
		private static final long serialVersionUID = 8545197602406456695L;
		public calculateAction() {
			putValue(NAME, GuiStrings.getGS("mp51", l));
			putValue(SHORT_DESCRIPTION, GuiStrings.getGS("mp51e", l));
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			lblP09n1.setText(GuiStrings.getGS("mp51e", l));
			lciTableModel.setRowCount(0);
			lciTable.setModel(lciTableModel);
			TableColumnModel tcm = lciTable.getColumnModel();
			tcm.getColumn(0).setHeaderValue(GuiStrings.getGS("mp13", l));
			tcm.getColumn(1).setHeaderValue(GuiStrings.getGS("mp11", l));
			tcm.getColumn(2).setHeaderValue(GuiStrings.getGS("p01n3", l));
			tcm.getColumn(3).setHeaderValue(GuiStrings.getGS("p02n4", l));
			LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>> sysErgebnis = new LinkedHashMap<Flow, LinkedHashMap<ValueType, Double>>();
			if (ProductSystem.getAllInstances().size() > 0) {
				for(String sysName : ProductSystem.getAllInstances().keySet()) {
					lciTableModel.addRow(new Object[] {sysName,"","",""});
					ProductSystem sysAktuell = ProductSystem.getAllInstances().get(sysName);
					try {
						if (sysAktuell.getElementarflussvektor().size() > 0) {
							sysErgebnis = sysAktuell.getElementarflussvektor();
							for(Flow sysFluss : sysErgebnis.keySet()){
								for (ValueType vt : sysAktuell.getElementarflussvektor().get(sysFluss).keySet()) {
									lciTableModel.addRow(new Object[] {"",sysFluss.getName(),"" + 
											ValueTypeStringMap.getFVTS(l).get(vt),								
											sysErgebnis.get(sysFluss).get(vt) + " " + sysFluss.getEinheit() + ""});
								}
							}
						}
/*						if (sysAktuell.getProduktflussvektor().size() > 0) {
							sysErgebnis = sysAktuell.getProduktflussvektor();
							for(Flow sysFluss : sysErgebnis.keySet()){
								for (ValueType vt : sysAktuell.getProduktflussvektor().get(sysFluss).keySet()) {
									lciTableModel.addRow(new Object[] {"",sysFluss.getName(),"" + 
											ValueTypeStringMap.getFVTS(l).get(vt),								
											sysErgebnis.get(sysFluss).get(vt) + " " + sysFluss.getEinheit() + ""});
								}
							}
						} */
					} catch (ArithmeticException vz) {
							lciTableModel.addRow(new Object[] 
									{"",vz.getMessage(),"",""});					
					}					 
				}
			}
			cl.show(panel, "berechnen");
		}		
	}
	private class newCategoryAction extends AbstractAction {
		private static final long serialVersionUID = 3009404662175614709L;
		public newCategoryAction() {
			putValue(NAME, GuiStrings.getGS("mp14", l));
			putValue(SHORT_DESCRIPTION, GuiStrings.getGS("mp14e", l));
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			lblP10n1.setText(GuiStrings.getGS("p10n1", l));
			lblP10n2.setText(GuiStrings.getGS("p10n2", l));
			lblP10n3.setText(GuiStrings.getGS("p10n3", l));
			btnP10n1.setText(GuiStrings.getGS("bt01", l));	
			lblP10n4.setText(GuiStrings.getGS("stat01", l));
			cl.show(panel, "neuKategorie");
		}	
	}
	private class listCategoriesAction extends AbstractAction {
		private static final long serialVersionUID = 8545097901306456895L;
		public listCategoriesAction() {
			putValue(NAME, GuiStrings.getGS("mp44", l));
			putValue(SHORT_DESCRIPTION, GuiStrings.getGS("mp44e", l));
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			lblP11n1.setText(GuiStrings.getGS("mp44e", l));
			catTableModel.setRowCount(0);
			catTable.setModel(catTableModel);
			TableColumnModel tcm = catTable.getColumnModel();
			tcm.getColumn(0).setHeaderValue(GuiStrings.getGS("mp14", l));
			tcm.getColumn(1).setHeaderValue(GuiStrings.getGS("p11n1", l));
			for(String catName : ImpactCategory.getAllInstances().keySet()) {
				ImpactCategory ic = ImpactCategory.getInstance(catName);			
				catTableModel.addRow(new Object[] {ic.getName(), 
						ic.getEinheit().getName()});			
			}			
			cl.show(panel, "categories");
		}
	}
	private class newCFAction extends AbstractAction {
		private static final long serialVersionUID = 4429058458938628148L;
		public newCFAction() {
			putValue(NAME, GuiStrings.getGS("mp15", l));
			putValue(SHORT_DESCRIPTION, GuiStrings.getGS("mp15e", l));
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			lblP12n1.setText(GuiStrings.getGS("p12n1", l));
			lblP12n2.setText(GuiStrings.getGS("p12n2", l));
			lblP12n3.setText(GuiStrings.getGS("p01n2", l));
			lblP12n4.setText(GuiStrings.getGS("p10n2", l));
			lblP12n5.setText(GuiStrings.getGS("p12n3", l));
			lblP12n6.setText(GuiStrings.getGS("p02n5", l));
			lblP12n7.setText(GuiStrings.getGS("p02n6", l));
			lblP12n8.setText(GuiStrings.getGS("stat01", l));
			btnP12n1.setText(GuiStrings.getGS("bt01", l));
			btnP12n2.setText(GuiStrings.getGS("bt10", l));
			
			cl.show(panel, "neuCF");		
		}		
	}
	private class listCFAction extends AbstractAction {
		private static final long serialVersionUID = 5499424576812764168L;
		public listCFAction() {
			putValue(NAME, GuiStrings.getGS("mp45", l));
			putValue(SHORT_DESCRIPTION, GuiStrings.getGS("mp45e", l));
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			lblP13n1.setText(GuiStrings.getGS("mp45e", l));
			cfTableModel.setRowCount(0);
			cfTable.setModel(cfTableModel);
			TableColumnModel tcm = cfTable.getColumnModel();
			tcm.getColumn(0).setHeaderValue(GuiStrings.getGS("p06n1", l));
			tcm.getColumn(1).setHeaderValue(GuiStrings.getGS("mp11", l));
			tcm.getColumn(2).setHeaderValue(GuiStrings.getGS("p13n1", l));
			tcm.getColumn(3).setHeaderValue(GuiStrings.getGS("p01n3", l));
			tcm.getColumn(4).setHeaderValue(GuiStrings.getGS("p12n3", l));
			for(String cfName : CharacFactor.getAllInstances().keySet()) {
				CharacFactor cf = CharacFactor.getInstance(cfName);
				cfTableModel.addRow(new Object[] {cf.getName(),
						cf.getFlow().getName(), cf.getWirkung().getName(),
						ValueTypeStringMap.getFVTS(l).get(ValueType.MeanValue),
						cf.getValues().get(ValueType.MeanValue)});
				cfTableModel.addRow(new Object[] {"",
						"", "", ValueTypeStringMap.getFVTS(l).get(ValueType.LowerBound),
						cf.getValues().get(ValueType.LowerBound)});
				cfTableModel.addRow(new Object[] {"",
						"", "", ValueTypeStringMap.getFVTS(l).get(ValueType.UpperBound),
						cf.getValues().get(ValueType.UpperBound)});
			}
			
			cl.show(panel, "cfList");		
		}		
	}

	private class newLCIAAction extends AbstractAction {
		private static final long serialVersionUID = -7780878398290965073L;
		public newLCIAAction() {
			putValue(NAME, GuiStrings.getGS("mp16", l));
			putValue(SHORT_DESCRIPTION, GuiStrings.getGS("mp16e", l));
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			lblP14n1.setText(GuiStrings.getGS("p14n1", l));
			lblP14n2.setText(GuiStrings.getGS("p14n2", l));
			lblP14n3.setText(GuiStrings.getGS("mp14", l));
			lblP14n4.setText(GuiStrings.getGS("mp15", l));
			lblP14n5.setText(GuiStrings.getGS("stat01", l));			
			btnP14n1.setText(GuiStrings.getGS("bt01", l));
			btnP14n2.setText(GuiStrings.getGS("bt11", l));	
			btnP14n3.setText(GuiStrings.getGS("bt11", l));	
			btnP14n4.setText(GuiStrings.getGS("bt04", l));	
			
			cl.show(panel, "neuLCIA");				
		}		
	}
	
	private class listLCIAAction extends AbstractAction {
		private static final long serialVersionUID = 3661738843444354844L;
		public listLCIAAction() {
			putValue(NAME, GuiStrings.getGS("mp46", l));
			putValue(SHORT_DESCRIPTION, GuiStrings.getGS("mp46e", l));
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			lblP15n1.setText(GuiStrings.getGS("mp46e", l));
			bmTableModel.setRowCount(0);
			bmTable.setModel(bmTableModel);
			TableColumnModel tcm = bmTable.getColumnModel();
			tcm.getColumn(0).setHeaderValue(GuiStrings.getGS("mp16", l));
			tcm.getColumn(1).setHeaderValue(GuiStrings.getGS("p08n1", l));
			tcm.getColumn(2).setHeaderValue(GuiStrings.getGS("p08n2", l));
			for(String bmName : LCIAMethod.getAllInstances().keySet()) {
				LCIAMethod bm = LCIAMethod.instance(bmName);
				bmTableModel.addRow(new Object[] {bm.getName(),"",""});
				for(String wkName : bm.categoryList().keySet()) {
					bmTableModel.addRow(new Object[] {"",GuiStrings.getGS("mp14", l),wkName});
				}
				for(String cfName : bm.getFactorSet().keySet()) {
					bmTableModel.addRow(new Object[] {"",GuiStrings.getGS("mp15", l),cfName});
				}				
			}			
			cl.show(panel, "bmList");
		}		
	}
	
	private class calculateLCIAAction extends AbstractAction {
		private static final long serialVersionUID = -7444593062727670849L;
		public calculateLCIAAction() {
			putValue(NAME, GuiStrings.getGS("mp52", l));
			putValue(SHORT_DESCRIPTION, GuiStrings.getGS("mp52e", l));
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			lblP16n1.setText(GuiStrings.getGS("p16n1", l));
			lblP16n2.setText(GuiStrings.getGS("p16n2", l));
			lblP16n3.setText(GuiStrings.getGS("p16n3", l));
			lblP16n4.setText(GuiStrings.getGS("mp16", l));
			lblP16n5.setText(GuiStrings.getGS("p16n4", l));
			btnP16n1.setText(GuiStrings.getGS("bt12", l));
			waTableModel.setRowCount(0);
			waTable.setModel(waTableModel);
			TableColumnModel tcm = waTable.getColumnModel();
			tcm.getColumn(0).setHeaderValue(GuiStrings.getGS("mp14", l));
			tcm.getColumn(1).setHeaderValue(GuiStrings.getGS("p11n1", l));
			tcm.getColumn(2).setHeaderValue(GuiStrings.getGS("p16n5", l));
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
			
			cl.show(panel, "berechnen2");
			
			
//			private JPanel panel_16 = new JPanel();	
//			private JLabel lblP16n1 = new JLabel();				// "Wirkungsabschätzung"
//			private JLabel lblP16n2 = new JLabel();				// "Object-Typ"
//			private JButton btnP16n1 = new JButton(); 			// "Berechnungsergebnisse anzeigen
//			private JComboBox<String> cobP16n1 = new JComboBox<String>();	// Objekt-Typen
		}		
	}
}
