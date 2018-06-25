package de.unistuttgart.iwb.multivalcagui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

public class LanguagePanel extends MCAPanel {
	
	private JLabel lblP05n1 = new JLabel();				// "Sprachauswahl"
	private JLabel lblP05n2 = new JLabel();				// "Sprache"
	private JButton btn05n1 = new JButton();			// "speichern"
	private JComboBox<Language> comboBox2 = new JComboBox<Language>();
	
             
		protected LanguagePanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		Language l = GUILanguage.getChosenLanguage();
		setLayout(new MigLayout("", "[108px,grow][108px][108px][108px,grow]", 
				"[20px][20px][20px][20px][20px][20px][20px,grow]"));		
		lblP05n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblP05n1, "flowy,cell 1 0 2 1,alignx center,growy");		
		add(lblP05n2, "cell 1 1,grow");		
		comboBox2.setModel(new DefaultComboBoxModel<Language>(Language.values()));
		add(comboBox2, "cell 2 1,grow");	
		add(btn05n1, "cell 1 2 2 1,alignx center");	
		
		InfoPanel.lblInfo1.setText(GuiStrings.getGS("head1", l));
		InfoPanel.lblInfo2.setText(GuiStrings.getGS("info1", l));
		InfoPanel.lblInfo3.setText(GuiStrings.getGS("info2", l));
		InfoPanel.lblInfo4.setText(GuiStrings.getGS("info3", l));
		InfoPanel.lblInfo5.setText(GuiStrings.getGS("head2", l)+"     "+GuiStrings.getGS("date", l));	
		
		btn05n1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Language l = GUILanguage.getChosenLanguage();
				l = comboBox2.getItemAt(comboBox2.getSelectedIndex());
				GUILanguage.setChosenLanguage(l);
				/*
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
				*/
				lblP05n1.setText(GuiStrings.getGS("mp31e", l));
				lblP05n2.setText(GuiStrings.getGS("mp31", l));
				btn05n1.setText(GuiStrings.getGS("bt01", l));
			}
		});
		
		
	}

	@Override
	public void showSelf() {
		Language l = GUILanguage.getChosenLanguage();
		lblP05n1.setText(GuiStrings.getGS("mp31e", l));
		lblP05n2.setText(GuiStrings.getGS("mp31", l));
		btn05n1.setText(GuiStrings.getGS("bt01", l));	
		
	}
	
	

}
