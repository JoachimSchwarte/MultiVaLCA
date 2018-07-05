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
import de.unistuttgart.iwb.multivalca.ProcessModuleGroup;
import net.miginfocom.swing.MigLayout;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.536
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
				Language l = GUILanguage.getChosenLanguage();
				Locale locale = MultiVaLCA.LANGUAGES.get(l);
				String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
				ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
				String fmenge = txt03.getText();
				Double menge;
				try {
					menge = Double.parseDouble(fmenge);
				} catch (NumberFormatException e){
					menge = 0.0;
				}
				if (menge == 0.0) {
					lbl06.setText(bundle.getString("stat07"));
				}
				String flname = txt02.getText();
				boolean flOk = true;					
				if (!Flow.getAllInstances().containsKey(flname)) {
					flOk = false;
					lbl06.setText(bundle.getString("stat37"));
				}			
				String name = txt01.getText();	
				boolean nameOk = true;
				if ("".equals(name)) {
					nameOk = false;
					lbl06.setText(bundle.getString("stat02"));
				} 
				if (name != name.replaceAll(" ","")) {
					nameOk = false;
					lbl06.setText(bundle.getString("stat20"));
					txt01.setText("");
				}
				if (MCAObject.containsName(name)) {
					nameOk = false;
					lbl06.setText(bundle.getString("stat03"));
					txt01.setText("");
				}	
				if (menge != 0.0 && flOk && nameOk) {
					ProcessModuleGroup.createInstance(name, Flow.getInstance(flname), menge);
					txt01.setEnabled(false);
					txt02.setEnabled(false);
					txt03.setEnabled(false);
					btn01.setEnabled(false);
					txt04.setEnabled(true);
					btn02.setEnabled(true);
				}				
			}			
		});
		
		btn02.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Language l = GUILanguage.getChosenLanguage();
				Locale locale = MultiVaLCA.LANGUAGES.get(l);
				String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
				ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
				String name = txt04.getText();	
				String flname = txt02.getText();
				boolean nameOk = true;
				if ("".equals(name)) {
					nameOk = false;
					lbl06.setText(bundle.getString("stat02"));
				} 
				if (nameOk && !ProcessModule.getAllInstances().containsKey(name)) {
					nameOk = false;
					lbl06.setText(bundle.getString("stat38"));
				}
				if (nameOk && !ProcessModule.getAllInstances().get(name).getProduktflussvektor().containsKey(Flow.getAllInstances().get(flname))) {
					nameOk = false;
					lbl06.setText(bundle.getString("stat39"));					
				}
				if (nameOk) {
					ProcessModuleGroup.getAllInstances().get(txt01.getText()).addModule(ProcessModule.getAllInstances().get(txt04.getText()));
					lbl06.setText(bundle.getString("stat40") + ProcessModuleGroup.getAllInstances().get(txt01.getText()).getModList().size()
							+ bundle.getString("stat41"));
					btn03.setEnabled(true);
					txt04.setText("");
				}			
			}
		});
		
		btn03.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txt01.setText("");
				txt02.setText("");
				txt03.setText("");
				txt04.setText("");
				txt01.setEnabled(true);
				txt02.setEnabled(true);
				txt03.setEnabled(true);
				txt04.setEnabled(false);
				btn01.setEnabled(true);
				btn02.setEnabled(false);
				btn03.setEnabled(false);
				
			}
		});
	}

	@Override
	public void showSelf() {
		Language l = GUILanguage.getChosenLanguage();
		Locale locale = MultiVaLCA.LANGUAGES.get(l);
		String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
		lbl01.setText(bundle.getString("p20n1"));
		lbl02.setText(bundle.getString("p20n2"));
		lbl03.setText(bundle.getString("p20n3"));
		lbl04.setText(bundle.getString("p02n4"));
		lbl05.setText(bundle.getString("p20n4"));
		lbl06.setText(bundle.getString("stat01"));
		btn01.setText(bundle.getString("bt16"));
		btn02.setText(bundle.getString("bt17"));
		btn02.setText(bundle.getString("bt17"));
		btn03.setText(bundle.getString("bt04"));
		
	}

}
