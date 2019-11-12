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
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

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

import net.miginfocom.swing.MigLayout;


/**
 * @author JS, HH
 * @version 0.610
 */

public class MultiVaLCA {

	private Language l = GUILanguage.getChosenLanguage();
	private String versionString ="Version 0.610";
	private String dateString ="21.03.2019";

	private JFrame frame = new JFrame();
	private JPanel panel = new JPanel();
	private CardLayout cl = new CardLayout(0, 0);
	private final Action newModuleAction 		= new newModuleAction();
	private final Action newDeclarationAction 	= new newDeclarationAction();
	private final Action prefsAction 			= new prefsAction();
	private final Action xmlExportAction 		= new XMLExportAction(l);
	private final Action xmlImportAction 		= new XMLImportAction(l);

	//
	//
	// Panel 5; Sprachauswahl
	//
	private JPanel panel_5 = new JPanel();
	private JLabel lblP05n1 = new JLabel();				// "Sprachauswahl"
	private JLabel lblP05n2 = new JLabel();				// "Sprache"
	private JButton btn05n1 = new JButton();			// "speichern"
	private JComboBox<Language> comboBox2 = new JComboBox<Language>();
	//
	public static final Map<Language, Locale> LANGUAGES;

	static {
		LANGUAGES = new EnumMap<>(Language.class);

		Locale english = new Locale("en", "UK"); //Locale.UK
		Locale deutsch = new Locale("de", "DE"); //Locale.DE
		Locale französisch = new Locale ("fr", "FR");
		Locale russisch = new Locale ("ru", "RU");
		Locale portugiesisch = new Locale ("pt", "PT");
		Locale kroatisch = new Locale ("hr", "HR");
		//...

		LANGUAGES.put(Language.Deutsch, deutsch);
		LANGUAGES.put(Language.English, english);  
		LANGUAGES.put(Language.Francais, französisch);
		LANGUAGES.put(Language.Português, portugiesisch);
		LANGUAGES.put(Language.\u0440\u0443\u0441\u0441\u043A\u0438\u0439, russisch);
		LANGUAGES.put(Language.Hrvatski, kroatisch);
	}

	private Locale locale = LANGUAGES.get(l);
	private String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
	private ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
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

		frame.setTitle(bundle.getString("head1")+"   "+versionString);
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
		panel.add(new FlowPanel("neuFluss"), "neuFluss");
		//
		// Panel 2; Neues Prozessmodul
		//
		panel.add(new ProcessModulePanel("neuModul"), "neuModul");
		//
		// Panel 2b; Neues Prozessmodul
		//
		panel.add(new ProModGroupPanel("neuModGroup"), "neuModGroup");
		//
		// Panel 3; Neues Produktsystem
		//		
		panel.add(new ProductSystemPanel("neuProdukt"), "neuProdukt");	
		//
		// Panel 4; Info
		panel.add(new InfoPanel("leer"), "leer");
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
		//
		// Panel 6; Flussliste
		//
		panel.add(new FlowListPanel("listeFluss"), "listeFluss");		
		//
		// Panel 7; Prozessmodulliste
		//
		panel.add(new ProcModListPanel("listePM"), "listePM");		
		//
		// Panel 8; Produktsystemliste
		//
		panel.add(new ProductSystemListPanel("listePS"), "listePS");		
		//
		// Panel 9; LCI Berechnung
		//
		panel.add(new LciCalcPanel("berechnen"), "berechnen");		
		//
		// Panel 10; Neue Wirkungskategorie
		//
		panel.add(new ImpactCatPanel("neuImCat"), "neuImCat");
		//
		// Panel 11; Kategorieliste
		//
		panel.add(new ImpactListPanel("categories"), "categories");		
		//
		// Panel 12; Neuer Charakterisierungsfaktor
		//
		panel.add(new CharacFactorPanel ("neuCF"), "neuCF");		
		//
		// Panel 13; CF-iste
		//
		panel.add(new CFListPanel("cfList"), "cfList");		
		//
		// Panel 14; Neue Bewertungsmethode (LCIA)
		//
		panel.add(new EvalMethodPanel("neuLCIA"), "neuLCIA");
		//
		// Panel 15; Liste der Bewertungsmethoden
		//
		panel.add(new EvalMListPanel("bmList"), "bmList");		
		//
		// Panel 16; Berechnung der Wirkungsabschätzung
		//
		panel.add(new LciaCalcPanel("berechnen2"), "berechnen2");
		//
		// Panel 17; Neue Produktdeklaration
		//
		panel.add(new DeklarationPanel("neuDekl"), "neuDekl");
		//
		// Panel 18; Neue Komponente
		//
		panel.add(new ComponentPanel("neuKente"), "neuKente");			
		//
		// Panel 19; Neue Komposition
		//
		panel.add(new CompositionPanel("neuKtion"), "neuKtion");
		//
		// Panel 20; Liste der Deklarationen
		//
		panel.add(new DeklarationListPanel("listDekl"), "listDekl");
		//
		// Panel 21; Liste der Komponenten
		//
		panel.add(new ComponentListPanel("listKente"), "listKente");
		//
		// Panel 22; Liste der Kompositionen
		//
		panel.add(new CompositionListPanel("listKtion"), "listKtion");
		//		
		// Panel 22; Liste der Kompositionen
		//
		panel.add(new DeclarationGroupPanel("neuDeklGroup"), "neuDeklGroup");

		cl.show(panel, "leer"); // zeigt Startfenster an

		InfoPanel.lblInfo1.setText(bundle.getString("head1"));
		InfoPanel.lblInfo2.setText(bundle.getString("info1"));
		InfoPanel.lblInfo3.setText(bundle.getString("info2"));
		InfoPanel.lblInfo4.setText(bundle.getString("info3"));
		InfoPanel.lblInfo5.setText(versionString+"     "+dateString);	

		/*
		 * Organisation der Menuleiste
		 */

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		final JMenu mnDatei = new JMenu(bundle.getString("mp6")); 	//Datei
		menuBar.add(mnDatei);

		JMenuItem mntmNewMenuItem_4 = new JMenuItem();
		mntmNewMenuItem_4.setAction(xmlExportAction); 			//xmlExport
		mnDatei.add(mntmNewMenuItem_4);	

		JMenuItem mntmNewMenuItem_5 = new JMenuItem();
		mntmNewMenuItem_5.setAction(xmlImportAction); 			//xmlImport
		mnDatei.add(mntmNewMenuItem_5);

		JMenu mnNew = new JMenu(bundle.getString("mp1")); 	//Neu/Bearbeiten
		menuBar.add(mnNew);

		JMenuItem mntmFlow = new JMenuItem();
		Action a1 = new MCAAction(bundle.getString("mp11"), bundle.getString("mp11e"), "neuFluss") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 2835159943929154045L;

			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}		
		};
		mntmFlow.setAction(a1);									//Neuer Fluss
		mnNew.add(mntmFlow);

		JMenu mntmProcessModule = new JMenu();
		mntmProcessModule.setAction(newModuleAction);			//Neues Prozessmodul
		mnNew.add(mntmProcessModule);

		JMenuItem mntmSingleModule = new JMenuItem();
		Action a2 = new MCAAction(bundle.getString("mp121"), bundle.getString("mp121e"), "neuModul") {
			/**
			 * 
			 */
			private static final long serialVersionUID = -4250878733464869164L;

			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}		
		};
		mntmSingleModule.setAction(a2);							//Einzelprozess
		mntmProcessModule.add(mntmSingleModule);

		JMenuItem mntmGroupModule = new JMenuItem();
		Action a3 = new MCAAction(bundle.getString("mp122"), bundle.getString("mp122e"), "neuModGroup") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 426405479780639787L;

			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}		
		};
		mntmGroupModule.setAction(a3);							//Prozessmodulgruppe
		mntmProcessModule.add(mntmGroupModule);	

		JMenuItem mntmProductSystem = new JMenuItem();
		Action a4 = new MCAAction(bundle.getString("mp13"), bundle.getString("mp13e"), "neuProdukt") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 6226301146519768393L;

			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};		
		mntmProductSystem.setAction(a4);						//Produktsystem
		mnNew.add(mntmProductSystem);

		JMenuItem mntmCategory = new JMenuItem();
		Action a5 = new MCAAction(bundle.getString("mp14"), bundle.getString("mp14e"), "neuImCat") {
			/**
			 * 
			 */
			private static final long serialVersionUID = -3820806030895359979L;

			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};
		mntmCategory.setAction(a5);								//Wirkungskategorie
		mnNew.add(mntmCategory);

		JMenuItem mntmCF = new JMenuItem();
		Action a6 = new MCAAction(bundle.getString("mp15"), bundle.getString("mp15e"), "neuCF") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1052458229020404221L;

			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};
		mntmCF.setAction(a6);									//Charakterisierungsfaktoren
		mnNew.add(mntmCF);

		JMenuItem mntmLCIAnew = new JMenuItem();
		Action a7 = new MCAAction(bundle.getString("mp16"), bundle.getString("mp16e"), "neuLCIA") {
			/**
			 * 
			 */
			private static final long serialVersionUID = -3693924284420168323L;

			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};
		mntmLCIAnew.setAction(a7);								//Wirkungsabschätzung
		mnNew.add(mntmLCIAnew);

		//		JMenuItem mntmDeclaration = new JMenuItem();
		//		Action a8 = new MCAAction(bundle.getString("mp17"), bundle.getString("mp17e"), "neuDekl") {
		//			@Override
		//			public void performAction(ActionEvent e) {
		//				cl.show(panel, getKey());
		//				MCAPanel.get(getKey()).showMe();
		//			}			
		//		};
		//		mntmDeclaration.setAction(a8);							//Produktdeklaration
		//		mnNew.add(mntmDeclaration);

		JMenu mntmDeclaration = new JMenu();
		mntmDeclaration.setAction(newDeclarationAction);				//Neue Produktdeklaration
		mnNew.add(mntmDeclaration);

		JMenuItem mntmSingleDeclaration = new JMenuItem();
		Action a11 = new MCAAction(bundle.getString("mp20"), bundle.getString("mp20e"), "neuDekl") {
			/**
			 * 
			 */
			private static final long serialVersionUID = -7154206582526063033L;

			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}		
		};
		mntmSingleDeclaration.setAction(a11);							//Einzeldeklaration
		mntmDeclaration.add(mntmSingleDeclaration);

		JMenuItem mntmGroupDeclaration = new JMenuItem();
		Action a12 = new MCAAction(bundle.getString("mp22"), bundle.getString("mp22e"), "neuDeklGroup") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 2626834353163940160L;

			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}		
		};
		mntmGroupDeclaration.setAction(a12);							//Deklarationsgruppe
		mntmDeclaration.add(mntmGroupDeclaration);	


		JMenuItem mntmComponent = new JMenuItem();
		Action a9 = new MCAAction(bundle.getString("mp18"), bundle.getString("mp18e"), "neuKente") {
			/**
			 * 
			 */
			private static final long serialVersionUID = -6376352846398610330L;

			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};		
		mntmComponent.setAction(a9);							//Komponente
		mnNew.add(mntmComponent);

		JMenuItem mntmComposition = new JMenuItem();
		Action a10 = new MCAAction(bundle.getString("mp19"), bundle.getString("mp19e"), "neuKtion") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4004667055895155214L;

			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};
		mntmComposition.setAction(a10);							//Komposition
		mnNew.add(mntmComposition);	                                                                 

		JMenu mnListe = new JMenu(bundle.getString("mp4"));
		menuBar.add(mnListe);									//Liste

		JMenuItem mntmFlsse = new JMenuItem();
		Action a13 = new MCAAction(bundle.getString("mp41"), bundle.getString("mp41e"), "listeFluss") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 3663428120601847987L;

			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};		
		mntmFlsse.setAction(a13);								//Liste aller Flüsse
		mnListe.add(mntmFlsse);

		JMenuItem mntmProzessmodule = new JMenuItem();
		Action a14 = new MCAAction(bundle.getString("mp42"), bundle.getString("mp42e"), "listePM") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 2281720160378053260L;

			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};
		mntmProzessmodule.setAction(a14);						//Liste der Prozessmodule
		mnListe.add(mntmProzessmodule);

		JMenuItem mntmProduktsysteme = new JMenuItem();
		Action a15 = new MCAAction(bundle.getString("mp43"), bundle.getString("mp43e"), "listePS") {
			/**
			 * 
			 */
			private static final long serialVersionUID = -2733567351856475594L;

			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};		
		mntmProduktsysteme.setAction(a15);						//Liste der Produktsysteme
		mnListe.add(mntmProduktsysteme);

		JMenuItem mntmCategories = new JMenuItem();
		Action a16 = new MCAAction(bundle.getString("mp44"), bundle.getString("mp44e"), "categories") {
			/**
			 * 
			 */
			private static final long serialVersionUID = -5277407125040246782L;

			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};		
		mntmCategories.setAction(a16);							//Liste der Wirkungskategorien
		mnListe.add(mntmCategories);

		JMenuItem mntmCFs = new JMenuItem();
		Action a17 = new MCAAction(bundle.getString("mp45"), bundle.getString("mp45e"), "cfList") {
			/**
			 * 
			 */
			private static final long serialVersionUID = -3947659842459441268L;

			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};
		mntmCFs.setAction(a17);									//Liste der Charakterisierungsfaktoren
		mnListe.add(mntmCFs);

		JMenuItem mntmLCIAlist = new JMenuItem();
		Action a18 = new MCAAction(bundle.getString("mp46"), bundle.getString("mp46e"), "bmList") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 2899526871233373436L;

			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};
		mntmLCIAlist.setAction(a18);							//Liste der Bewertungsmethoden
		mnListe.add(mntmLCIAlist);

		JMenuItem mntmDeclarationlist = new JMenuItem();
		Action a19 = new MCAAction(bundle.getString("mp47"), bundle.getString("mp47e"), "listDekl") {
			/**
			 * 
			 */
			private static final long serialVersionUID = -6941096016763042679L;

			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};		
		mntmDeclarationlist.setAction(a19);						//Liste der Produktdeklarationen
		mnListe.add(mntmDeclarationlist);

		JMenuItem mntmComponentlist = new JMenuItem();
		Action a20 = new MCAAction(bundle.getString("mp48"), bundle.getString("mp48e"), "listKente") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 364472270895421963L;

			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};				
		mntmComponentlist.setAction(a20);						//Liste der Komponenten
		mnListe.add(mntmComponentlist);

		JMenuItem mntmCompositionlist = new JMenuItem();
		Action a21 = new MCAAction(bundle.getString("mp49"), bundle.getString("mp49e"), "listKtion") {
			/**
			 * 
			 */
			private static final long serialVersionUID = -605730857270753649L;

			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};	
		mntmCompositionlist.setAction(a21);						//Liste der Kompositionen
		mnListe.add(mntmCompositionlist);                                                             

		JMenu mnBerechnen = new JMenu(bundle.getString("mp5"));
		menuBar.add(mnBerechnen);

		JMenuItem mntmLci = new JMenuItem();
		Action a23 = new MCAAction(bundle.getString("mp51"), bundle.getString("mp51e"), "berechnen") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1253993595201768096L;

			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};
		mntmLci.setAction(a23);									//Sachbilanzberechnung
		mnBerechnen.add(mntmLci);

		JMenuItem mntmLCIAcalc = new JMenuItem();
		Action a24 = new MCAAction(bundle.getString("mp52"), bundle.getString("mp52e"), "berechnen2") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1496096188093353892L;

			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};
		mntmLCIAcalc.setAction(a24);							//Wirkungsabschätzungsberechnung
		mnBerechnen.add(mntmLCIAcalc);							

		JMenu mnPrefs = new JMenu(bundle.getString("mp3"));
		menuBar.add(mnPrefs);									//Einstellungen-Reiter

		JMenuItem mntmNewMenuItem_3 = new JMenuItem();
		mntmNewMenuItem_3.setAction(prefsAction);
		mnPrefs.add(mntmNewMenuItem_3);							//

		JMenu mnHilfe = new JMenu(bundle.getString("mp2"));	//Hilfe-Reiter
		menuBar.add(mnHilfe);


		JMenuItem mntmNewMenuItem_2 = new JMenuItem();
		Action a28 = new MCAAction(bundle.getString("mp21"), bundle.getString("mp21e"), "leer") {
			/**
			 * 
			 */
			private static final long serialVersionUID = -5417569602036007738L;

			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};
		mntmNewMenuItem_2.setAction(a28);
		mnHilfe.add(mntmNewMenuItem_2);	
		//

		/*
		 * Sprachauswahl
		 */
		btn05n1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				l = comboBox2.getItemAt(comboBox2.getSelectedIndex());
				GUILanguage.setChosenLanguage(l);
				locale = LANGUAGES.get(l); 
				bundle = ResourceBundle.getBundle(baseName, locale);

				frame.setTitle(bundle.getString("head1")+"   "+versionString);
				mnDatei.setText(bundle.getString("mp6"));
				mnNew.setText(bundle.getString("mp1"));
				mnListe.setText(bundle.getString("mp4"));
				mnBerechnen.setText(bundle.getString("mp5"));
				mnPrefs.setText(bundle.getString("mp3"));
				mnHilfe.setText(bundle.getString("mp2"));
				mntmFlow.setText(bundle.getString("mp11"));
				mntmFlow.setToolTipText(bundle.getString("mp11e"));
				mntmProcessModule.setText(bundle.getString("mp12"));
				mntmProcessModule.setToolTipText(bundle.getString("mp12e"));
				mntmSingleModule.setText(bundle.getString("mp121"));
				mntmSingleModule.setToolTipText(bundle.getString("mp121e"));
				mntmGroupModule.setText(bundle.getString("mp122"));
				mntmGroupModule.setToolTipText(bundle.getString("mp122e"));
				mntmProductSystem.setText(bundle.getString("mp13"));
				mntmProductSystem.setToolTipText(bundle.getString("mp13e"));
				mntmNewMenuItem_3.setText(bundle.getString("mp31"));
				mntmNewMenuItem_3.setToolTipText(bundle.getString("mp31e"));
				mntmFlsse.setText(bundle.getString("mp41"));
				mntmFlsse.setToolTipText(bundle.getString("mp41e"));
				mntmProzessmodule.setText(bundle.getString("mp42"));
				mntmProzessmodule.setToolTipText(bundle.getString("mp42e"));
				mntmProduktsysteme.setText(bundle.getString("mp43"));
				mntmProduktsysteme.setToolTipText(bundle.getString("mp43e"));
				mntmLci.setText(bundle.getString("mp51"));
				mntmLci.setToolTipText(bundle.getString("mp51e"));
				mntmNewMenuItem_2.setText(bundle.getString("mp21"));
				mntmNewMenuItem_2.setToolTipText(bundle.getString("mp21e"));
				mntmNewMenuItem_4.setText(bundle.getString("mp61"));
				mntmNewMenuItem_4.setToolTipText(bundle.getString("mp61e"));
				mntmNewMenuItem_5.setText(bundle.getString("mp62"));
				mntmNewMenuItem_5.setToolTipText(bundle.getString("mp62e"));
				mntmCategory.setText(bundle.getString("mp14"));
				mntmCategory.setToolTipText(bundle.getString("mp14e"));
				mntmCategories.setText(bundle.getString("mp44"));
				mntmCategories.setToolTipText(bundle.getString("mp44e"));
				mntmCF.setText(bundle.getString("mp15"));
				mntmCF.setToolTipText(bundle.getString("mp15e"));
				mntmCFs.setText(bundle.getString("mp45"));
				mntmCFs.setToolTipText(bundle.getString("mp45e"));
				mntmLCIAnew.setText(bundle.getString("mp16"));
				mntmLCIAnew.setToolTipText(bundle.getString("mp16e"));
				mntmLCIAlist.setText(bundle.getString("mp46"));
				mntmLCIAlist.setToolTipText(bundle.getString("mp46e"));

				mntmDeclaration.setText(bundle.getString("mp17"));
				mntmDeclaration.setToolTipText(bundle.getString("mp17e"));
				mntmDeclarationlist.setText(bundle.getString("mp47"));
				mntmDeclarationlist.setToolTipText(bundle.getString("mp47e"));
				mntmComponent.setText(bundle.getString("mp18"));
				mntmComponent.setToolTipText(bundle.getString("mp18e"));
				mntmComponentlist.setText(bundle.getString("mp48"));
				mntmComponentlist.setToolTipText(bundle.getString("mp48e"));
				mntmComposition.setText(bundle.getString("mp19"));
				mntmComposition.setToolTipText(bundle.getString("mp19e"));
				mntmCompositionlist.setText(bundle.getString("mp49"));
				mntmCompositionlist.setToolTipText(bundle.getString("mp49e"));

				mntmLCIAcalc.setText(bundle.getString("mp52"));
				mntmLCIAcalc.setToolTipText(bundle.getString("mp52e"));
				lblP05n1.setText(bundle.getString("mp31e"));
				lblP05n2.setText(bundle.getString("mp31"));
				btn05n1.setText(bundle.getString("bt01"));
			}
		});
	}

	private class newModuleAction extends AbstractAction {
		private static final long serialVersionUID = 6190606710625748526L;
		public newModuleAction() {
			locale = LANGUAGES.get(l);
			baseName = "de.unistuttgart.iwb.multivalcagui.messages";
			bundle = ResourceBundle.getBundle(baseName, locale);

			putValue(NAME, bundle.getString("mp12"));
			putValue(SHORT_DESCRIPTION, bundle.getString("mp12e"));
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		}
	}

	private class newDeclarationAction extends AbstractAction {
		private static final long serialVersionUID = -1210137078073096014L;
		public newDeclarationAction() {
			locale = LANGUAGES.get(l);
			baseName = "de.unistuttgart.iwb.multivalcagui.messages";
			bundle = ResourceBundle.getBundle(baseName, locale);

			putValue(NAME, bundle.getString("mp17"));
			putValue(SHORT_DESCRIPTION, bundle.getString("mp17e"));
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		}
	}

	//Sprachauswahl 

	private class prefsAction extends AbstractAction {
		private static final long serialVersionUID = 8545097902306476895L;
		public prefsAction() {
			locale = LANGUAGES.get(l);
			baseName = "de.unistuttgart.iwb.multivalcagui.messages";
			bundle = ResourceBundle.getBundle(baseName, locale);
			putValue(NAME, bundle.getString("mp31"));
			putValue(SHORT_DESCRIPTION, bundle.getString("mp31e"));
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			lblP05n1.setText(bundle.getString("mp31e"));
			lblP05n2.setText(bundle.getString("mp31"));
			btn05n1.setText(bundle.getString("bt01"));			
			cl.show(panel, "lang");
		}
	}
}