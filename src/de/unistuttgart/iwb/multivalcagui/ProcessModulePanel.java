/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.514
 */

public class ProcessModulePanel extends MCAPanel{
	
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
	private JButton btnP02n4 = new JButton();			// "fertig"
	private JButton btnP02n3 = new JButton();			// "Fluss hinzufügen"

	protected ProcessModulePanel(String key) {
		super(key);
		initUI();
	}
	
	private void initUI( ) {
		
	}

	@Override
	public void showSelf() {
		// TODO Auto-generated method stub
		
	}

}
