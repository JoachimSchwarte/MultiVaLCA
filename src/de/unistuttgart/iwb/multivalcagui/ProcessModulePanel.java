/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import de.unistuttgart.iwb.multivalca.Flow;
import de.unistuttgart.iwb.multivalca.MCAObject;
import de.unistuttgart.iwb.multivalca.ProcessModule;
import de.unistuttgart.iwb.multivalca.ValueType;
import net.miginfocom.swing.MigLayout;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.539
 */

public class ProcessModulePanel extends MCAPanel{
	Language l = GUILanguage.getChosenLanguage();
	Locale locale = MultiVaLCA.LANGUAGES.get(l);
	String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
	ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);	
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

		setLayout(new MigLayout("", "[108px,grow][108px][108px][108px,grow]", 
				"[20px][20px][20px][20px][20px][20px][20px][20px][20px][20px][20px][20px,grow]"));		
		lblP02n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblP02n1, "flowy,cell 1 0 2 1,alignx center,growy");	
		add(lblP02n2, "cell 1 1,grow");		
		txtP02n1.setText("");
		add(txtP02n1, "cell 2 1,grow");
		txtP02n1.setColumns(10);		
		add(btnP02n1, "cell 1 2 2 1,alignx center");		
		add(lblP02n3, "cell 1 3,grow");	
		txtP02n2.setText("");
		add(txtP02n2, "cell 2 3,grow");
		txtP02n2.setColumns(10);
		txtP02n2.setEnabled(false);	
		add(lblP02n4, "cell 1 4,grow");
		txtP02n3.setText("");
		add(txtP02n3, "cell 2 4,grow");
		txtP02n3.setColumns(10);
		txtP02n3.setEnabled(false);		
		btnP02n2.setEnabled(false);
		add(btnP02n2, "cell 1 5 2 1,alignx center");	
		add(lblP02n6, "cell 1 6,grow");	
		txtP02n4.setText("");
		add(txtP02n4, "cell 2 6,grow");
		txtP02n4.setColumns(10);
		txtP02n4.setEnabled(false);	
		add(lblP02n7, "cell 1 7,grow");
		txtP02n5.setText("");
		add(txtP02n5, "cell 2 7,grow");
		txtP02n5.setColumns(10);
		txtP02n5.setEnabled(false);		
		btnP02n3.setEnabled(false);
		add(btnP02n3, "cell 1 8,alignx center");
		btnP02n4.setEnabled(false);
		add(btnP02n4, "cell 2 8,alignx center");
		add(lblP02n5, "cell 0 9 4 1,alignx center");		
		
		button1();		
		button2();		
		button3();		
		button4();
	}
	
	private void button1() {
		btnP02n1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name = txtP02n1.getText();	
				boolean nameOk = true;
				if ("".equals(name)) {
					nameOk = false;
					lblP02n5.setText(bundle.getString("stat02"));
				} 
				if (name != name.replaceAll(" ","")) {
					nameOk = false;
					lblP02n5.setText(bundle.getString("stat20"));
					txtP02n1.setText("");
				}
				if (MCAObject.containsName(name)) {
					nameOk = false;
					lblP02n5.setText(bundle.getString("stat03"));
					txtP02n1.setText("");
				}			
				if (nameOk) {
					ProcessModule.instance(name);
					lblP02n5.setText(bundle.getString("stat06") + 
							ProcessModule.getAllInstances().size() + bundle.getString("stat05"));
					btnP02n1.setEnabled(false);
					txtP02n1.setEnabled(false);
					btnP02n2.setEnabled(true);
					txtP02n2.setEnabled(true);
					txtP02n3.setEnabled(true);	
				} 		
			}
		});
	}
	
	private void button2() {
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
				if ("".equals(fname) || (menge == 0.0)) {
					lblP02n5.setText(bundle.getString("stat07"));
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
						btnP02n3.setEnabled(true);
						btnP02n4.setEnabled(false);
						txtP02n4.setEnabled(true);
						int anzPFlow = ProcessModule.getInstance(mname).getProduktflussvektor().size();
						int anzEFlow = ProcessModule.getInstance(mname).getElementarflussvektor().size();
						int anzGesamt = anzPFlow + anzEFlow;
						lblP02n5.setText(bundle.getString("stat08") + mname + bundle.getString("stat09") +
								anzGesamt + bundle.getString("stat10"));
						
					} else {
						lblP02n5.setText(bundle.getString("stat11"));
					}					
				}
			}
		});
	}
	private void button3() {
		btnP02n3.addActionListener(new ActionListener() {
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
					lblP02n5.setText(bundle.getString("stat21"));
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
					btnP02n4.setEnabled(true);
					btnP02n3.setEnabled(false);
					lblP02n5.setText(bundle.getString("stat01"));				
				}
			}			
		});
	}
	private void button4() {
		btnP02n4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnP02n1.setEnabled(true);
				txtP02n2.setText("");
				txtP02n3.setText("");
				txtP02n1.setText("");
				txtP02n4.setText("");
				txtP02n5.setText("");
				btnP02n2.setEnabled(false);
				btnP02n4.setEnabled(false);
				btnP02n3.setEnabled(false);
				txtP02n1.setEnabled(true);
				txtP02n2.setEnabled(false);
				txtP02n3.setEnabled(false);
				txtP02n4.setEnabled(false);
				txtP02n5.setEnabled(false);
				lblP02n5.setText(bundle.getString("stat01"));
			}
		});	
	}

	@Override
	public void showSelf() {
		l = GUILanguage.getChosenLanguage();
		locale = MultiVaLCA.LANGUAGES.get(l);
		baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		bundle = ResourceBundle.getBundle(baseName, locale);
		lblP02n1.setText(bundle.getString("p02n1"));
		lblP02n2.setText(bundle.getString("p02n2"));
		lblP02n3.setText(bundle.getString("p01n2"));
		lblP02n4.setText(bundle.getString("p02n4"));
		lblP02n5.setText(bundle.getString("stat01"));
		lblP02n6.setText(bundle.getString("p02n5"));
		lblP02n7.setText(bundle.getString("p02n6"));
		btnP02n1.setText(bundle.getString("bt02"));
		btnP02n2.setText(bundle.getString("bt03"));
		btnP02n4.setText(bundle.getString("bt04"));
		btnP02n3.setText(bundle.getString("bt10"));		
	}

}
