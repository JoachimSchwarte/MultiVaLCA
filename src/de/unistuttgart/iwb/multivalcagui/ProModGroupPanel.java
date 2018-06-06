/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.516
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

	protected ProModGroupPanel(String key) {
		super(key);
		initUI();
	}
	
	private void initUI( ) {
		Language l = GUILanguage.getChosenLanguage();
		setLayout(new MigLayout("", "[108px,grow][108px][108px][108px,grow]", 
				"[20px][20px][20px][20px][20px][20px][20px][20px][20px][20px][20px][20px,grow]"));		
		lbl01.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lbl01, "flowy,cell 1 0 2 1,alignx center,growy");	
		add(lbl02, "cell 1 1,grow");		
		txt01.setText("");
		add(txt01, "cell 2 1,grow");
		txt01.setColumns(10);			
		add(lbl03, "cell 1 2,grow");		
		txt02.setText("");
		add(txt02, "cell 2 2,grow");
		txt02.setColumns(10);		
		add(lbl04, "cell 1 3,grow");		
		txt03.setText("");
		add(txt03, "cell 2 3,grow");
		txt03.setColumns(10);
		add(btn01, "cell 1 4 2 1,alignx center");	
		add(lbl05, "cell 1 5,grow");		
		txt04.setText("");
		add(txt04, "cell 2 5,grow");
		txt04.setColumns(10);
		add(btn02, "cell 1 6,alignx center");
		add(btn03, "cell 2 6,alignx center");
		txt04.setEnabled(false);	
		btn02.setEnabled(false);	
		btn03.setEnabled(false);
		add(lbl06, "cell 0 7 4 1,alignx center");
		
		btn01.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = txt01.getText();	
				boolean nameOk = true;
				if (name.equals("")) {
					nameOk = false;
					lbl06.setText(GuiStrings.getGS("stat02", l));
				} 
				
			}			
		});
	}

	@Override
	public void showSelf() {
		Language l = GUILanguage.getChosenLanguage();
		lbl01.setText(GuiStrings.getGS("p20n1", l));
		lbl02.setText(GuiStrings.getGS("p20n2", l));
		lbl03.setText(GuiStrings.getGS("p20n3", l));
		lbl04.setText(GuiStrings.getGS("p02n4", l));
		lbl05.setText(GuiStrings.getGS("p20n4", l));
		lbl06.setText(GuiStrings.getGS("stat01", l));
		btn01.setText(GuiStrings.getGS("bt16", l));
		btn02.setText(GuiStrings.getGS("bt17", l));
		btn02.setText(GuiStrings.getGS("bt17", l));
		btn03.setText(GuiStrings.getGS("bt04", l));
		
	}

}
