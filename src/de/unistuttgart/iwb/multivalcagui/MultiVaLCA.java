/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
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
import javax.swing.UIManager;


import net.miginfocom.swing.MigLayout;


/**
 * @author Dr.-Ing. Joachim Schwarte, Helen Hein, Johannes Dippon
 * @version 0.701
 */

public class MultiVaLCA {

	private Language l = GUILanguage.getChosenLanguage();
	private String versionString ="Version 0.701";
	private String dateString ="10.03.2020";

	private JFrame frame = new JFrame();
	private JPanel panel = new JPanel();
	private CardLayout cl = new CardLayout(0, 0);
	private final Action newModuleAction 		= new newModuleAction();
	private final Action newProductSystemAction = new newProductSystemAction();
	private final Action processModuleListAction 		= new processModuleListAction();
	private final Action productSystemListAction = new productSystemListAction(); 
	private final Action newDeclarationAction 	= new newDeclarationAction();
	private final Action declarationListAction 	= new declarationListAction();
	private final Action newComponentAction 	= new newComponentAction();
	private final Action newCompositionAction 	= new newCompositionAction();
	private final Action componentListAction	= new componentListAction();
	private final Action compositionListAction = new compositionListAction();
	private final Action prefsAction 			= new prefsAction();
	private final Action xmlExportAction 		= new XMLExportAction(l);
	private final Action xmlImportAction 		= new XMLImportAction(l);
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int width = (int) screen.getWidth();
	private int height = (int) screen.getHeight();
    private Font generalfont = new Font ("Tahoma", Font.LAYOUT_LEFT_TO_RIGHT, height *15/1000);
	private Font titlefont = new Font ("Tahoma", Font.BOLD, height *22/1000);

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
//		Locale französisch = new Locale ("fr", "FR");
//		Locale russisch = new Locale ("ru", "RU");
//		Locale portugiesisch = new Locale ("pt", "PT");
//		Locale kroatisch = new Locale ("hr", "HR");
		//...

		LANGUAGES.put(Language.Deutsch, deutsch);
		LANGUAGES.put(Language.English, english);  
//		LANGUAGES.put(Language.Francais, französisch);
//		LANGUAGES.put(Language.Português, portugiesisch);
//		LANGUAGES.put(Language.\u0440\u0443\u0441\u0441\u043A\u0438\u0439, russisch);
//		LANGUAGES.put(Language.Hrvatski, kroatisch);
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
		frame.setFont(generalfont);
		frame.setTitle(bundle.getString("head1")+"   "+versionString);
		frame.setSize(width *48/100, height * 60/100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);				
		frame.getContentPane().add(panel, BorderLayout.CENTER);	
		UIManager.put("ToolTip.font", generalfont);
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
		// Panel 2b; Neue Prozessmodulgruppe
		//
		panel.add(new ProcModGroupPanel("neuModGroup"), "neuModGroup");
		//
		// Panel 3; Neues Produktsystem
		//		
		panel.add(new ProductSystemPanel("neuProdukt"), "neuProdukt");	
		// 
		// Panel 3b; Neue Systemgruppe
		//
		panel.add(new SystemGroupPanel("neuSystemGroup"), "neuSystemGroup");
		//
		// Panel 4; Info
		//
		panel.add(new InfoPanel("leer"), "leer");
		//
		// Panel 5; Sprachauswahl
		//
		panel.add(panel_5, "lang");
		panel_5.setLayout(new MigLayout("", "[grow][20%][20%][grow]", 
				"[8%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%][grow]"));		
		lblP05n1.setFont(titlefont);
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
		// Panel 7; Prozessmodulliste(Einzelmodule)
		//
		panel.add(new ProcModListPanel("listePM"), "listePM");	
		//
		// Panel 7b; Prozessmodulliste(Gruppen)
		//
		panel.add(new ProcModGroupListPanel("listePMGroup"), "listePMGroup");
		//
		// Panel 8; Produktsystemliste
		//
		panel.add(new ProductSystemListPanel("listePS"), "listePS");	
        //		
		// Panel 8b; Liste der Systemgruppen
		//
		panel.add(new SystemGroupListPanel("listSystemGroup"), "listSystemGroup");
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
		// Panel 17b; Neue Produktdeklarationsgruppe
		//
		panel.add(new DeclarationGroupPanel("neuDeklGroup"), "neuDeklGroup");
		//
		// Panel 18; Neue Komponente
		//
		panel.add(new ComponentPanel("neuKente"), "neuKente");		
		//
		// Panel 18b; Neue Komponentengruppe
		//
		panel.add(new ComponentGroupPanel("neuKenteGroup"), "neuKenteGroup");
		//
		// Panel 19; Neue Komposition
		//
		panel.add(new CompositionPanel("neuKtion"), "neuKtion");
		//
		// Panel 19b; Neue Kompositionsgruppe
		//
		panel.add(new CompositionGroupPanel("neuKtionGroup"), "neuKtionGroup");
		//
		// Panel 20; Liste der Deklarationen
		//
		panel.add(new DeklarationListPanel("listDekl"), "listDekl");
		//
		// Panel 20b; Liste der Produktdeklarationsgruppen 
		//
		panel.add(new DeklarationGroupListPanel("listDeklGroup"), "listDeklGroup");
		//
		// Panel 21; Liste der Komponenten
		//
		panel.add(new ComponentListPanel("listKente"), "listKente");
		//
		// Panel 21b; Liste der Komponentengruppen
		//
		panel.add(new ComponentGroupListPanel("listKenteGroup"), "listKenteGroup");
		//
		// Panel 22; Liste der Kompositionen
		//
		panel.add(new CompositionListPanel("listKtion"), "listKtion");
		//
		// Panel 22b; Liste der Kompositionsgruppen
		//
		panel.add(new CompositionGroupListPanel("listKtionGroup"), "listKtionGroup");
		

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
		mnDatei.setFont(generalfont);
		menuBar.add(mnDatei);

		JMenuItem mntmNewMenuItem_4 = new JMenuItem();
		mntmNewMenuItem_4.setAction(xmlExportAction); 			//xmlExport
		mntmNewMenuItem_4.setFont(generalfont);
		mnDatei.add(mntmNewMenuItem_4);	

		JMenuItem mntmNewMenuItem_5 = new JMenuItem();
		mntmNewMenuItem_5.setAction(xmlImportAction); 			//xmlImport
		mntmNewMenuItem_5.setFont(generalfont);
		mnDatei.add(mntmNewMenuItem_5);

		JMenu mnNew = new JMenu(bundle.getString("mp1")); 	//Neu/Bearbeiten
		mnNew.setFont(generalfont);
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
		mntmFlow.setFont(generalfont);
		mnNew.add(mntmFlow);

		JMenu mntmProcessModule = new JMenu();
		mntmProcessModule.setAction(newModuleAction);			//Neues Prozessmodul
		mntmProcessModule.setFont(generalfont);
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
		mntmSingleModule.setFont(generalfont);
		mntmProcessModule.add(mntmSingleModule);
		mntmProcessModule.setFont(generalfont);

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
		mntmGroupModule.setFont(generalfont);
		mntmProcessModule.add(mntmGroupModule);	
		
		
		JMenu mntmProductSystem = new JMenu();
		mntmProductSystem.setAction(newProductSystemAction);			//Neues Produktsystem
		mnNew.add(mntmProductSystem);
		
		
		JMenuItem mntmSingleProductSystem = new JMenuItem();
		Action a4 = new MCAAction(bundle.getString("mp131"), bundle.getString("mp131e"), "neuProdukt") {
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
		mntmSingleProductSystem.setAction(a4);						//Einzelnes Produktsystem
		mntmSingleProductSystem.setFont(generalfont);
		mntmProductSystem.add(mntmSingleProductSystem);
		mntmProductSystem.setAction(a4);						//Produktsystem
		mntmProductSystem.setFont(generalfont);
		mnNew.add(mntmProductSystem);
		
		JMenuItem mntmSystemGroup = new JMenuItem();
		mntmSystemGroup.setFont(generalfont);
		Action a31 = new MCAAction(bundle.getString("mp132"), bundle.getString("mp132e"), "neuSystemGroup") {
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
		mntmSystemGroup.setAction(a31);							//Systemgruppe
		mntmProductSystem.add(mntmSystemGroup);

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
		mntmCategory.setFont(generalfont);
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
		mntmCF.setFont(generalfont);
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
		mntmLCIAnew.setFont(generalfont);
		mnNew.add(mntmLCIAnew);

		JMenu mntmDeclaration = new JMenu();
		mntmDeclaration.setAction(newDeclarationAction);				//Neue Produktdeklaration
		mntmDeclaration.setFont(generalfont);
		mnNew.add(mntmDeclaration);

		JMenuItem mntmSingleDeclaration = new JMenuItem();
		Action a11 = new MCAAction(bundle.getString("mp171"), bundle.getString("mp171e"), "neuDekl") {
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
		mntmSingleDeclaration.setFont(generalfont);
		mntmDeclaration.add(mntmSingleDeclaration);

		JMenuItem mntmGroupDeclaration = new JMenuItem();
		Action a12 = new MCAAction(bundle.getString("mp172"), bundle.getString("mp172e"), "neuDeklGroup") {
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
		mntmGroupDeclaration.setFont(generalfont);
		mntmDeclaration.add(mntmGroupDeclaration);	

		
		JMenu mntmComponent = new JMenu();
		mntmComponent.setAction(newComponentAction);				//Neue Komponente
		mntmComponent.setFont(generalfont);
		mnNew.add(mntmComponent);

		JMenuItem mntmSingleComponent = new JMenuItem();
		Action a9 = new MCAAction(bundle.getString("mp181"), bundle.getString("mp181e"), "neuKente") {
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
		mntmSingleComponent.setAction(a9);							//Einzelne Komponente
		mntmSingleComponent.setFont(generalfont);
		mntmComponent.add(mntmSingleComponent);
		
		JMenuItem mntmGroupComponent = new JMenuItem();
		Action a34 = new MCAAction(bundle.getString("mp182"), bundle.getString("mp182e"), "neuKenteGroup") {
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
		mntmGroupComponent.setAction(a34);							//Komponentengruppe
		mntmGroupComponent.setFont(generalfont);
		mntmComponent.add(mntmGroupComponent);
		
		JMenu mntmComposition= new JMenu();
		mntmComposition.setAction(newCompositionAction);				//Neue Komposition
		mntmComposition.setFont(generalfont);
		mnNew.add(mntmComposition);

		JMenuItem mntmSingleComposition = new JMenuItem();
		Action a10 = new MCAAction(bundle.getString("mp191"), bundle.getString("mp191e"), "neuKtion") {
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
		mntmSingleComposition.setAction(a10);							//Einzelne Komposition
		mntmSingleComposition.setFont(generalfont);
		mntmComposition.add(mntmSingleComposition);	  
		
		JMenuItem mntmGroupComposition = new JMenuItem();
		Action a40 = new MCAAction(bundle.getString("mp192"), bundle.getString("mp192e"), "neuKtionGroup") {
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
		mntmGroupComposition.setAction(a40);							//Kompositionsgruppe
		mntmGroupComposition.setFont(generalfont);
		mntmComposition.add(mntmGroupComposition);	

		JMenu mnListe = new JMenu(bundle.getString("mp4"));
		mnListe.setFont(generalfont);
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
		mntmFlsse.setFont(generalfont);
		mnListe.add(mntmFlsse);

		JMenu mntmProcessModuleList = new JMenu();
		mntmProcessModuleList.setAction(processModuleListAction);			//Liste der Prozessmodule
		mntmProcessModuleList.setFont(generalfont);
		mnListe.add(mntmProcessModuleList);

		JMenuItem mntmSingleModuleList = new JMenuItem();
		Action a14 = new MCAAction(bundle.getString("mp421"), bundle.getString("mp421e"), "listePM") {
			
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

		mntmSingleModuleList.setAction(a14);						//Liste der Einzelmodule
		mntmSingleModuleList.setFont(generalfont);
		mntmProcessModuleList.add(mntmSingleModuleList);

		
		JMenuItem mntmGroupModuleList = new JMenuItem();
		Action a33 = new MCAAction(bundle.getString("mp422"), bundle.getString("mp422e"), "listePMGroup") {
			
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
		mntmGroupModuleList.setAction(a33);						//Liste der Gruppenmodule
		mntmGroupModuleList.setFont(generalfont);
		mntmProcessModuleList.add(mntmGroupModuleList);

		
		JMenu mntmProductSystemList = new JMenu();
		mntmProductSystemList.setAction(productSystemListAction);			//Liste der Produktsysteme
		mntmProductSystemList.setFont(generalfont);
		mnListe.add(mntmProductSystemList);

		JMenuItem mntmProduktsysteme = new JMenuItem();
		Action a15 = new MCAAction(bundle.getString("mp431"), bundle.getString("mp431e"), "listePS") {
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

		mntmProduktsysteme.setAction(a15);						//Liste der einzelnen Produktsysteme
		mntmProduktsysteme.setFont(generalfont);
		mntmProductSystemList.add(mntmProduktsysteme);
																//Liste der Produktsysteme
		mnListe.add(mntmProduktsysteme);
		
		JMenuItem mntmSystemGroupList = new JMenuItem();
		Action a30 = new MCAAction(bundle.getString("mp432"), bundle.getString("mp432e"), "listSystemGroup") {
			/**
			 * 
			 */
			private static final long serialVersionUID = -2733567351856475594L;

			/**
			 * 
			 */

			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};
		mntmSystemGroupList.setAction(a30);						//Liste der Systemgruppen
		mntmSystemGroupList.setFont(generalfont);
		mntmProductSystemList.add(mntmSystemGroupList);

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
		mntmCategories.setFont(generalfont);
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
		mntmCFs.setFont(generalfont);
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
		mntmLCIAlist.setFont(generalfont);
		mnListe.add(mntmLCIAlist);

		JMenu mntmDeclarationList = new JMenu();
		mntmDeclarationList.setAction(declarationListAction);			//Liste der Produktdeklarationen
		mntmDeclarationList.setFont(generalfont);
		mnListe.add(mntmDeclarationList);

		JMenuItem mntmSingleDeclarationList = new JMenuItem();
		Action a19 = new MCAAction(bundle.getString("mp471"), bundle.getString("mp471e"), "listDekl") {
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
		mntmSingleDeclarationList.setAction(a19);						//Liste der Einzeldeklarationen
		mntmSingleDeclarationList.setFont(generalfont);
		mntmDeclarationList.add(mntmSingleDeclarationList);

		JMenuItem mntmGroupDeclarationList = new JMenuItem();
		Action a32 = new MCAAction(bundle.getString("mp472"), bundle.getString("mp472e"), "listDeklGroup") {


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
		mntmGroupDeclarationList.setAction(a32);						//Liste der Produktdeklarationsgruppen
		mntmGroupDeclarationList.setFont(generalfont);
		mntmDeclarationList.add(mntmGroupDeclarationList);
		
		JMenu mntmComponentlist = new JMenu();
		mntmComponentlist.setAction(componentListAction);			//Liste der Komponenten
		mntmComponentlist.setFont(generalfont);
		mnListe.add(mntmComponentlist);

		JMenuItem mntmSingleComponentlist = new JMenuItem();
		Action a20 = new MCAAction(bundle.getString("mp481"), bundle.getString("mp481e"), "listKente") {
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
		mntmSingleComponentlist.setAction(a20);						//Liste der Einzelkomponenten
		mntmSingleComponentlist.setFont(generalfont);
		mntmComponentlist.add(mntmSingleComponentlist);
		
		JMenuItem mntmGroupComponentlist = new JMenuItem();
		Action a35 = new MCAAction(bundle.getString("mp482"), bundle.getString("mp482e"), "listKenteGroup") {
			/**
			 * 
			 */
			private static final long serialVersionUID = -524273762667395019L;

			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};				
		mntmGroupComponentlist.setAction(a35);						//Liste der Komponentengruppen
		mntmGroupComponentlist.setFont(generalfont);
		mntmComponentlist.add(mntmGroupComponentlist);
		
		JMenu mntmCompositionlist = new JMenu();
		mntmCompositionlist.setAction(compositionListAction);			//Liste der Kompositionen
		mntmCompositionlist.setFont(generalfont);
		mnListe.add(mntmCompositionlist);

		JMenuItem mntmSingleCompositionlist = new JMenuItem();
		Action a21 = new MCAAction(bundle.getString("mp491"), bundle.getString("mp491e"), "listKtion") {

			/**
			 * 
			 */
			private static final long serialVersionUID = -7682974527236434112L;

			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};	
		mntmSingleCompositionlist.setAction(a21);						//Liste der Einzelkompositionen
		mntmSingleCompositionlist.setFont(generalfont);
		mntmCompositionlist.add(mntmSingleCompositionlist);    
		
		JMenuItem mntmGroupCompositionlist = new JMenuItem();
		Action a36 = new MCAAction(bundle.getString("mp492"), bundle.getString("mp492e"), "listKtionGroup") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 6820910285972988541L;

			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};	
		mntmGroupCompositionlist.setAction(a36);						//Liste der Kompositionsgruppen
		mntmGroupCompositionlist.setFont(generalfont);
		mntmCompositionlist.add(mntmGroupCompositionlist);  

		JMenu mnBerechnen = new JMenu(bundle.getString("mp5"));
		mnBerechnen.setFont(generalfont);
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
		mntmLci.setFont(generalfont);
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
		mntmLCIAcalc.setFont(generalfont);
		mnBerechnen.add(mntmLCIAcalc);							

		JMenu mnPrefs = new JMenu(bundle.getString("mp3"));
		mnPrefs.setFont(generalfont);
		menuBar.add(mnPrefs);									//Einstellungen-Reiter

		JMenuItem mntmNewMenuItem_3 = new JMenuItem();
		mntmNewMenuItem_3.setAction(prefsAction);
		mntmNewMenuItem_3.setFont(generalfont);
		mnPrefs.add(mntmNewMenuItem_3);							//

		JMenu mnHilfe = new JMenu(bundle.getString("mp2"));	//Hilfe-Reiter
		mnHilfe.setFont(generalfont);
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
		mntmNewMenuItem_2.setFont(generalfont);
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
				mntmSingleProductSystem.setText(bundle.getString("mp131"));
				mntmSingleProductSystem.setToolTipText(bundle.getString("mp131e"));
				mntmSystemGroup.setText(bundle.getString("mp132"));
				mntmSystemGroup.setToolTipText(bundle.getString("mp132e"));
				mntmNewMenuItem_3.setText(bundle.getString("mp31"));
				mntmNewMenuItem_3.setToolTipText(bundle.getString("mp31e"));
				mntmFlsse.setText(bundle.getString("mp41"));
				mntmFlsse.setToolTipText(bundle.getString("mp41e"));
				mntmProcessModuleList.setText(bundle.getString("mp42"));
				mntmProcessModuleList.setToolTipText(bundle.getString("mp42e"));
				mntmSingleModuleList.setText(bundle.getString("mp421"));
				mntmSingleModuleList.setToolTipText(bundle.getString("mp421e"));
				mntmGroupModuleList.setText(bundle.getString("mp422"));
				mntmGroupModuleList.setToolTipText(bundle.getString("mp422e"));
				mntmProductSystemList.setText(bundle.getString("mp43"));
				mntmProductSystemList.setToolTipText(bundle.getString("mp43e"));
				mntmProduktsysteme.setText(bundle.getString("mp431"));
				mntmProduktsysteme.setToolTipText(bundle.getString("mp431e"));
				mntmSystemGroupList.setText(bundle.getString("mp432"));
				mntmSystemGroupList.setToolTipText(bundle.getString("mp432e"));
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
				mntmSingleDeclaration.setText(bundle.getString("mp171"));
				mntmSingleDeclaration.setToolTipText(bundle.getString("mp171e"));
				mntmGroupDeclaration.setText(bundle.getString("mp172"));
				mntmGroupDeclaration.setToolTipText(bundle.getString("mp172e"));
				mntmDeclarationList.setText(bundle.getString("mp47"));
				mntmDeclarationList.setToolTipText(bundle.getString("mp47e"));
				mntmSingleDeclarationList.setText(bundle.getString("mp471"));
				mntmSingleDeclarationList.setToolTipText(bundle.getString("mp471e"));
				mntmGroupDeclarationList.setText(bundle.getString("mp472"));
				mntmGroupDeclarationList.setToolTipText(bundle.getString("mp472e"));				
				mntmComponent.setText(bundle.getString("mp18"));
				mntmComponent.setToolTipText(bundle.getString("mp18e"));
				mntmSingleComponent.setText(bundle.getString("mp181"));
				mntmSingleComponent.setToolTipText(bundle.getString("mp181e"));
				mntmGroupComponent.setText(bundle.getString("mp182"));
				mntmGroupComponent.setToolTipText(bundle.getString("mp182e"));				
				mntmComponentlist.setText(bundle.getString("mp48"));
				mntmComponentlist.setToolTipText(bundle.getString("mp48e"));
				mntmSingleComponentlist.setText(bundle.getString("mp481"));
				mntmSingleComponentlist.setToolTipText(bundle.getString("mp481e"));
				mntmGroupComponentlist.setText(bundle.getString("mp482"));
				mntmGroupComponentlist.setToolTipText(bundle.getString("mp482e"));
				mntmComposition.setText(bundle.getString("mp19"));
				mntmComposition.setToolTipText(bundle.getString("mp19e"));
				mntmSingleComposition.setText(bundle.getString("mp191"));
				mntmSingleComposition.setToolTipText(bundle.getString("mp191e"));
				mntmGroupComposition.setText(bundle.getString("mp192"));
				mntmGroupComposition.setToolTipText(bundle.getString("mp192e"));
				mntmCompositionlist.setText(bundle.getString("mp49"));
				mntmCompositionlist.setToolTipText(bundle.getString("mp49e"));
				mntmSingleCompositionlist.setText(bundle.getString("mp491"));
				mntmSingleCompositionlist.setToolTipText(bundle.getString("mp491e"));
				mntmGroupCompositionlist.setText(bundle.getString("mp492"));
				mntmGroupCompositionlist.setToolTipText(bundle.getString("mp492e"));		
				mntmLCIAcalc.setFont(generalfont);	
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
	
	private class newProductSystemAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2037480297861981351L;
		public newProductSystemAction() {
			locale = LANGUAGES.get(l);
			baseName = "de.unistuttgart.iwb.multivalcagui.messages";
			bundle = ResourceBundle.getBundle(baseName, locale);

			putValue(NAME, bundle.getString("mp13"));
			putValue(SHORT_DESCRIPTION, bundle.getString("mp13e"));
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		}
	}
	
	private class processModuleListAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 8499606037992071382L;
		public processModuleListAction() {
			locale = LANGUAGES.get(l);
			baseName = "de.unistuttgart.iwb.multivalcagui.messages";
			bundle = ResourceBundle.getBundle(baseName, locale);

			putValue(NAME, bundle.getString("mp42"));
			putValue(SHORT_DESCRIPTION, bundle.getString("mp42e"));
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		}
	}

	private class productSystemListAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = -1553167823374115192L;
		public productSystemListAction() {
			locale = LANGUAGES.get(l);
			baseName = "de.unistuttgart.iwb.multivalcagui.messages";
			bundle = ResourceBundle.getBundle(baseName, locale);

			putValue(NAME, bundle.getString("mp43"));
			putValue(SHORT_DESCRIPTION, bundle.getString("mp43e"));
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		}
	}
	
	private class declarationListAction extends AbstractAction {

		private static final long serialVersionUID = -7898869366628872139L;
		public declarationListAction() {
			locale = LANGUAGES.get(l);
			baseName = "de.unistuttgart.iwb.multivalcagui.messages";
			bundle = ResourceBundle.getBundle(baseName, locale);

			putValue(NAME, bundle.getString("mp47"));
			putValue(SHORT_DESCRIPTION, bundle.getString("mp47e"));
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
	
	private class newComponentAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = -8362251113803703888L;
		public newComponentAction() {
			locale = LANGUAGES.get(l);
			baseName = "de.unistuttgart.iwb.multivalcagui.messages";
			bundle = ResourceBundle.getBundle(baseName, locale);

			putValue(NAME, bundle.getString("mp18"));
			putValue(SHORT_DESCRIPTION, bundle.getString("mp18e"));
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		}
	}
	
	private class componentListAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = -7120579128736086312L;
		public componentListAction() {
			locale = LANGUAGES.get(l);
			baseName = "de.unistuttgart.iwb.multivalcagui.messages";
			bundle = ResourceBundle.getBundle(baseName, locale);

			putValue(NAME, bundle.getString("mp48"));
			putValue(SHORT_DESCRIPTION, bundle.getString("mp48e"));
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		}
	}
	
	private class newCompositionAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = -9107084786986056131L;
		public newCompositionAction() {
			locale = LANGUAGES.get(l);
			baseName = "de.unistuttgart.iwb.multivalcagui.messages";
			bundle = ResourceBundle.getBundle(baseName, locale);

			putValue(NAME, bundle.getString("mp19"));
			putValue(SHORT_DESCRIPTION, bundle.getString("mp19e"));
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		}
	}
	
	private class compositionListAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7495310739212892136L;
		public compositionListAction() {
			locale = LANGUAGES.get(l);
			baseName = "de.unistuttgart.iwb.multivalcagui.messages";
			bundle = ResourceBundle.getBundle(baseName, locale);

			putValue(NAME, bundle.getString("mp49"));
			putValue(SHORT_DESCRIPTION, bundle.getString("mp49e"));
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
			lblP05n1.setFont(titlefont);
			lblP05n2.setFont(generalfont);
			btn05n1.setFont(generalfont);
			comboBox2.setFont(generalfont);
			lblP05n1.setText(bundle.getString("mp31e"));
			lblP05n2.setText(bundle.getString("mp31"));
			btn05n1.setText(bundle.getString("bt01"));			
			cl.show(panel, "lang");
		}
	}
}