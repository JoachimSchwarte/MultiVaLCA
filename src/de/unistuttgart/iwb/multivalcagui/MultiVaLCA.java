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
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.525
 */

public class MultiVaLCA {
	
	Language l = GUILanguage.getChosenLanguage();
	
	private JFrame frame = new JFrame();
	private JPanel panel = new JPanel();
	private CardLayout cl = new CardLayout(0, 0);
	private final Action newModuleAction 		= new newModuleAction();
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
		// Zeile 94 bis 98 nach oben verschoben

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
		panel.add(new CFListPanel("cfList"), "cflist");		
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
		
		cl.show(panel, "leer"); // zeigt Startfenster an
		
		InfoPanel.lblInfo1.setText(GuiStrings.getGS("head1", l));
		InfoPanel.lblInfo2.setText(GuiStrings.getGS("info1", l));
		InfoPanel.lblInfo3.setText(GuiStrings.getGS("info2", l));
		InfoPanel.lblInfo4.setText(GuiStrings.getGS("info3", l));
		InfoPanel.lblInfo5.setText(GuiStrings.getGS("head2", l)+"     "+GuiStrings.getGS("date", l));	
		
	
		
		/*
		 * Organisation der Menuleiste
		 */
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		final JMenu mnDatei = new JMenu(GuiStrings.getGS("mp6",l)); 	//Datei
		menuBar.add(mnDatei);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem();
		mntmNewMenuItem_4.setAction(xmlExportAction); 			//xmlExport
		mnDatei.add(mntmNewMenuItem_4);	
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem();
		mntmNewMenuItem_5.setAction(xmlImportAction); 			//xmlImport
		mnDatei.add(mntmNewMenuItem_5);
		
		JMenu mnNew = new JMenu(GuiStrings.getGS("mp1",l)); 	//Neu/Bearbeiten
		menuBar.add(mnNew);
		
		JMenuItem mntmFlow = new JMenuItem();
		Action a1 = new MCAAction(GuiStrings.getGS("mp11", l), GuiStrings.getGS("mp11e", l), "neuFluss") {
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
		Action a2 = new MCAAction(GuiStrings.getGS("mp121", l), GuiStrings.getGS("mp121e", l), "neuModul") {
			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}		
		};
		mntmSingleModule.setAction(a2);							//Einzelprozess
		mntmProcessModule.add(mntmSingleModule);
		
		JMenuItem mntmGroupModule = new JMenuItem();
		Action a3 = new MCAAction(GuiStrings.getGS("mp122", l), GuiStrings.getGS("mp122e", l), "neuModGroup") {
			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}		
		};
		mntmGroupModule.setAction(a3);							//Prozessmodulgruppe
		mntmProcessModule.add(mntmGroupModule);	
				
		JMenuItem mntmProductSystem = new JMenuItem();
		Action a4 = new MCAAction(GuiStrings.getGS("mp13", l), GuiStrings.getGS("mp13e", l), "neuProdukt") {
			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};		
		mntmProductSystem.setAction(a4);						//Produktsystem
		mnNew.add(mntmProductSystem);
		
		JMenuItem mntmCategory = new JMenuItem();
		Action a5 = new MCAAction(GuiStrings.getGS("mp14", l), GuiStrings.getGS("mp14e", l), "neuImCat") {
			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};
		mntmCategory.setAction(a5);								//Wirkungskategorie
		mnNew.add(mntmCategory);
		
		JMenuItem mntmCF = new JMenuItem();
		Action a6 = new MCAAction(GuiStrings.getGS("mp15", l), GuiStrings.getGS("mp15e", l), "neuCF") {
			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};
		mntmCF.setAction(a6);									//Charakterisierungsfaktoren
		mnNew.add(mntmCF);
		
		JMenuItem mntmLCIAnew = new JMenuItem();
		Action a7 = new MCAAction(GuiStrings.getGS("mp16", l), GuiStrings.getGS("mp16e", l), "neuLCIA") {
			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};
		mntmLCIAnew.setAction(a7);								//Wirkungsabschätzung
		mnNew.add(mntmLCIAnew);
				
		JMenuItem mntmDeclaration = new JMenuItem();
		Action a8 = new MCAAction(GuiStrings.getGS("mp17", l), GuiStrings.getGS("mp17e", l), "neuDekl") {
			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};
		mntmDeclaration.setAction(a8);							//Produktdeklaration
		mnNew.add(mntmDeclaration);
		
		JMenuItem mntmComponent = new JMenuItem();
				Action a9 = new MCAAction(GuiStrings.getGS("mp18", l), GuiStrings.getGS("mp18e", l), "neuKente") {
			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};		
		mntmComponent.setAction(a9);							//Komponente
		mnNew.add(mntmComponent);
		
		JMenuItem mntmComposition = new JMenuItem();
		Action a10 = new MCAAction(GuiStrings.getGS("mp19", l), GuiStrings.getGS("mp19e", l), "neuKtion") {
			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};
		mntmComposition.setAction(a10);							//Komposition
		mnNew.add(mntmComposition);	
		
		JMenu mnListe = new JMenu(GuiStrings.getGS("mp4", l));
		menuBar.add(mnListe);									//Liste
		
		JMenuItem mntmFlsse = new JMenuItem();
		Action a13 = new MCAAction(GuiStrings.getGS("mp41", l), GuiStrings.getGS("mp41e", l), "listeFluss") {
			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};		
		mntmFlsse.setAction(a13);								//Liste aller Flüsse
		mnListe.add(mntmFlsse);
		
		JMenuItem mntmProzessmodule = new JMenuItem();
		Action a14 = new MCAAction(GuiStrings.getGS("mp42", l), GuiStrings.getGS("mp42e", l), "listePM") {
			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};
		mntmProzessmodule.setAction(a14);						//Liste der Prozessmodule
		mnListe.add(mntmProzessmodule);
		
		JMenuItem mntmProduktsysteme = new JMenuItem();
		Action a15 = new MCAAction(GuiStrings.getGS("mp43", l), GuiStrings.getGS("mp43e", l), "listePS") {
			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};		
		mntmProduktsysteme.setAction(a15);						//Liste der Produktsysteme
		mnListe.add(mntmProduktsysteme);
		
		JMenuItem mntmCategories = new JMenuItem();
		Action a16 = new MCAAction(GuiStrings.getGS("mp44", l), GuiStrings.getGS("mp44e", l), "categories") {
			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};		
		mntmCategories.setAction(a16);							//Liste der Wirkungskategorien
		mnListe.add(mntmCategories);
		
		JMenuItem mntmCFs = new JMenuItem();
		Action a17 = new MCAAction(GuiStrings.getGS("mp45", l), GuiStrings.getGS("mp45e", l), "cfList") {
			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};
		mntmCFs.setAction(a17);									//Liste der Charakterisierungsfaktoren
		mnListe.add(mntmCFs);
		
		JMenuItem mntmLCIAlist = new JMenuItem();
		Action a18 = new MCAAction(GuiStrings.getGS("mp46", l), GuiStrings.getGS("mp46e", l), "bmList") {
			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};
		mntmLCIAlist.setAction(a18);							//Liste der Bewertungsmethoden
		mnListe.add(mntmLCIAlist);
		
		JMenuItem mntmDeclarationlist = new JMenuItem();
		Action a19 = new MCAAction(GuiStrings.getGS("mp47", l), GuiStrings.getGS("mp47e", l), "listDekl") {
			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};		
		mntmDeclarationlist.setAction(a19);						//Liste der Produktdeklarationen
		mnListe.add(mntmDeclarationlist);
		
		JMenuItem mntmComponentlist = new JMenuItem();
		Action a20 = new MCAAction(GuiStrings.getGS("mp48", l), GuiStrings.getGS("mp48e", l), "listKente") {
			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};				
		mntmComponentlist.setAction(a20);						//Liste der Komponenten
		mnListe.add(mntmComponentlist);
		
		JMenuItem mntmCompositionlist = new JMenuItem();
		Action a21 = new MCAAction(GuiStrings.getGS("mp49", l), GuiStrings.getGS("mp49e", l), "listKtion") {
			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};	
		mntmCompositionlist.setAction(a21);						//Liste der Kompositionen
		mnListe.add(mntmCompositionlist);
		
		JMenu mnBerechnen = new JMenu(GuiStrings.getGS("mp5", l));
		menuBar.add(mnBerechnen);
		
		JMenuItem mntmLci = new JMenuItem();
		Action a23 = new MCAAction(GuiStrings.getGS("mp51", l), GuiStrings.getGS("mp51e", l), "berechnen") {
			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};
		mntmLci.setAction(a23);									//Sachbilanzberechnung
		mnBerechnen.add(mntmLci);
		
		JMenuItem mntmLCIAcalc = new JMenuItem();
		Action a24 = new MCAAction(GuiStrings.getGS("mp52", l), GuiStrings.getGS("mp52e", l), "berechnen2") {
			@Override
			public void performAction(ActionEvent e) {
				cl.show(panel, getKey());
				MCAPanel.get(getKey()).showMe();
			}			
		};
		mntmLCIAcalc.setAction(a24);							//Wirkungsabschätzungsberechnung
		mnBerechnen.add(mntmLCIAcalc);							
			
		JMenu mnPrefs = new JMenu(GuiStrings.getGS("mp3", l));
		menuBar.add(mnPrefs);									//Einstellungen-Reiter
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem();
		mntmNewMenuItem_3.setAction(prefsAction);
		mnPrefs.add(mntmNewMenuItem_3);							//
		
		JMenu mnHilfe = new JMenu(GuiStrings.getGS("mp2", l));	//Hilfe-Reiter
		menuBar.add(mnHilfe);
		
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem();
		Action a28 = new MCAAction(GuiStrings.getGS("mp21", l), GuiStrings.getGS("mp21e", l), "leer") {
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
				mntmSingleModule.setText(GuiStrings.getGS("mp121",l));
				mntmSingleModule.setToolTipText(GuiStrings.getGS("mp121e",l));
				mntmGroupModule.setText(GuiStrings.getGS("mp122",l));
				mntmGroupModule.setToolTipText(GuiStrings.getGS("mp122e",l));
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
				
				mntmDeclaration.setText(GuiStrings.getGS("mp17",l));
				mntmDeclaration.setToolTipText(GuiStrings.getGS("mp17e",l));
				mntmDeclarationlist.setText(GuiStrings.getGS("mp47",l));
				mntmDeclarationlist.setToolTipText(GuiStrings.getGS("mp47e",l));
				mntmComponent.setText(GuiStrings.getGS("mp18",l));
				mntmComponent.setToolTipText(GuiStrings.getGS("mp18e",l));
				mntmComponentlist.setText(GuiStrings.getGS("mp48",l));
				mntmComponentlist.setToolTipText(GuiStrings.getGS("mp48e",l));
				mntmComposition.setText(GuiStrings.getGS("mp19",l));
				mntmComposition.setToolTipText(GuiStrings.getGS("mp19e",l));
				mntmCompositionlist.setText(GuiStrings.getGS("mp49",l));
				mntmCompositionlist.setToolTipText(GuiStrings.getGS("mp49e",l));
				
				mntmLCIAcalc.setText(GuiStrings.getGS("mp52",l));
				mntmLCIAcalc.setToolTipText(GuiStrings.getGS("mp52e",l));
				lblP05n1.setText(GuiStrings.getGS("mp31e", l));
				lblP05n2.setText(GuiStrings.getGS("mp31", l));
				btn05n1.setText(GuiStrings.getGS("bt01", l));
			}
		});
	}

	private class newModuleAction extends AbstractAction {
		private static final long serialVersionUID = 6190606710625748526L;
		public newModuleAction() {
			putValue(NAME, GuiStrings.getGS("mp12", l));
			putValue(SHORT_DESCRIPTION, GuiStrings.getGS("mp12e", l));
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
}

/*
<<<<<<< HEAD
	}	
=======
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
	*/
/*
	
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
			for(String mn : ProcessModuleGroup.getAllInstances().keySet()) {
				ProcessModuleGroup akModul = ProcessModuleGroup.getInstance(mn);
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
						if (sysAktuell.getProduktflussvektor().size() > 0) {
							sysErgebnis = sysAktuell.getProduktflussvektor();
							for(Flow sysFluss : sysErgebnis.keySet()){
								boolean ausgabe = false;
								if (sysAktuell.getVorUndKoppelprodukte().contains(sysFluss)) {
									ausgabe = true;
								}
								if (ausgabe == true) {
									for (ValueType vt : sysAktuell.getProduktflussvektor().get(sysFluss).keySet()) {
										lciTableModel.addRow(new Object[] {"",sysFluss.getName(),"" + 
												ValueTypeStringMap.getFVTS(l).get(vt),								
												sysErgebnis.get(sysFluss).get(vt) + " " + sysFluss.getEinheit() + ""});
									}
								}
							}
						} 
					} catch (ArithmeticException vz) {
							lciTableModel.addRow(new Object[] 
									{"",vz.getMessage(),"",""});					
					}					 
				}
			}
			cl.show(panel, "berechnen");
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

		}		
	}
	
	private class newDeclarationAction extends AbstractAction {
		private static final long serialVersionUID = 2203400316440970860L;
		public newDeclarationAction() {
			putValue(NAME, GuiStrings.getGS("mp17", l));
			putValue(SHORT_DESCRIPTION, GuiStrings.getGS("mp17e", l));
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			lblP17n1.setText(GuiStrings.getGS("p17n1", l));
			lblP17n2.setText(GuiStrings.getGS("p17n2", l));
			lblP17n3.setText(GuiStrings.getGS("p01n4", l));
			lblP17n4.setText(GuiStrings.getGS("mp16", l));
			btnP17n1.setText(GuiStrings.getGS("mp17e", l));
			lblP17n5.setText(GuiStrings.getGS("mp14", l));
			lblP17n6.setText(GuiStrings.getGS("p02n4", l));
			btnP17n2.setText(GuiStrings.getGS("bt11", l));
			lblP17n7.setText(GuiStrings.getGS("p02n5", l));
			lblP17n8.setText(GuiStrings.getGS("p02n6", l));
			btnP17n3.setText(GuiStrings.getGS("bt10", l));
			btnP17n4.setText(GuiStrings.getGS("bt04", l));
			lblP17n9.setText(GuiStrings.getGS("stat01", l));
			
			
			cl.show(panel, "neuDekl");
		}
		
	}
	
	private class newComponentAction extends AbstractAction {
		private static final long serialVersionUID = 415176321121976554L;
		public newComponentAction() {
			putValue(NAME, GuiStrings.getGS("mp18", l));
			putValue(SHORT_DESCRIPTION, GuiStrings.getGS("mp18e", l));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			lblP18n1.setText(GuiStrings.getGS("p18n1", l));
			lblP18n2.setText(GuiStrings.getGS("p18n2", l));
			lblP18n3.setText(GuiStrings.getGS("p18n3", l));
			lblP18n4.setText(GuiStrings.getGS("p02n4", l));
			lblP18n5.setText(GuiStrings.getGS("p02n5", l));
			lblP18n6.setText(GuiStrings.getGS("p02n6", l));
			lblP18n7.setText(GuiStrings.getGS("stat01", l));
			btnP18n1.setText(GuiStrings.getGS("bt13", l));
			btnP18n2.setText(GuiStrings.getGS("bt04", l));
		
			
			cl.show(panel, "neuKente");
		}
		
	}
	
	private class newCompositionAction extends AbstractAction {
		private static final long serialVersionUID = -4249650427101951850L;
		public newCompositionAction() {
			putValue(NAME, GuiStrings.getGS("mp19", l));
			putValue(SHORT_DESCRIPTION, GuiStrings.getGS("mp19e", l));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			lblP19n1.setText(GuiStrings.getGS("p19n1", l));
			lblP19n2.setText(GuiStrings.getGS("p19n2", l));
			lblP19n3.setText(GuiStrings.getGS("p19n3", l));
			lblP19n4.setText(GuiStrings.getGS("stat01", l));
			btnP19n1.setText(GuiStrings.getGS("bt14", l));
			btnP19n2.setText(GuiStrings.getGS("bt15", l));
			btnP19n3.setText(GuiStrings.getGS("bt04", l));
			
			
			cl.show(panel, "neuKtion");
		}
		
	}
	
	private class listDeclarationAction extends AbstractAction {
		private static final long serialVersionUID = 2203400316440970860L;
		public listDeclarationAction() {
			putValue(NAME, GuiStrings.getGS("mp47", l));
			putValue(SHORT_DESCRIPTION, GuiStrings.getGS("mp47e", l));
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			lblP20n1.setText(GuiStrings.getGS("mp47e", l));
			pdTableModel.setRowCount(0);
			pdTable.setModel(pdTableModel);
			TableColumnModel tcm = pdTable.getColumnModel();
			tcm.getColumn(0).setHeaderValue(GuiStrings.getGS("p06n1", l));
			tcm.getColumn(1).setHeaderValue(GuiStrings.getGS("p01n4", l));
			tcm.getColumn(2).setHeaderValue(GuiStrings.getGS("mp16", l));
			for(String pdName : ProductDeclaration.getAllInstances().keySet()) {
				ProductDeclaration pd = ProductDeclaration.getInstance(pdName);			
				pdTableModel.addRow(new Object[] {pd.getName(), pd.getEinheit(), pd.getBM().getName()});			
			}
			cl.show(panel, "listDekl");
		}
		
	}
	
	private class listComponentAction extends AbstractAction {
		private static final long serialVersionUID = 415176321121976554L;
		public listComponentAction() {
			putValue(NAME, GuiStrings.getGS("mp48", l));
			putValue(SHORT_DESCRIPTION, GuiStrings.getGS("mp48e", l));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			lblP21n1.setText(GuiStrings.getGS("mp48e", l));
			
			
			cl.show(panel, "listKente");		
		}
		
	}
	
	private class listCompositionAction extends AbstractAction {
		private static final long serialVersionUID = -4249650427101951850L;
		public listCompositionAction() {
			putValue(NAME, GuiStrings.getGS("mp49", l));
			putValue(SHORT_DESCRIPTION, GuiStrings.getGS("mp49e", l));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			lblP22n1.setText(GuiStrings.getGS("mp49e", l));
			
			
			cl.show(panel, "listKtion");		
		}
		
	}
>>>>>>> branch 'master' of https://github.com/JoachimSchwarte/MultiVaLCA.git
}
*/