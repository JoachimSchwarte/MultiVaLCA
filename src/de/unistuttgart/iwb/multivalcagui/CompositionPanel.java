package de.unistuttgart.iwb.multivalcagui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import de.unistuttgart.iwb.multivalca.Component;
import de.unistuttgart.iwb.multivalca.Composition;
import de.unistuttgart.iwb.multivalca.IVMGroupType;
import de.unistuttgart.iwb.multivalca.ImpactValueMapGroup;
import de.unistuttgart.iwb.multivalca.ImpactValueMaps;
import de.unistuttgart.iwb.multivalca.MCAObject;
import de.unistuttgart.iwb.multivalca.ProductSystem;
import net.miginfocom.swing.MigLayout;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.553
 */

public class CompositionPanel extends MCAPanel{

	private JLabel lblP19n1 = new JLabel();
	private JLabel lblP19n2 = new JLabel();				// "Name der Komposition"
	private JLabel lblP19n3 = new JLabel();				// "hinzuzufügende Komponente"
	private JLabel lblP19n4 = new JLabel();				// Status
	private JLabel lblP19n5 = new JLabel();				// Anzahl
	private JTextField txtP19n1 = new JTextField();		// Eingabefeld Name
	private JTextField txtP19n2 = new JTextField();		// Eingabefeld Komponente
	private JTextField txtP19n3 = new JTextField();		// Eingabefeld Anzahl
	private JButton btnP19n1 = new JButton(); 			// "neue Komposition anlegen"
	private JButton btnP19n2 = new JButton(); 			// "Komponente hinzufügen"
	private JButton btnP19n3 = new JButton(); 			// "fertig"

	private Language l = GUILanguage.getChosenLanguage();
	private Locale locale = MultiVaLCA.LANGUAGES.get(l);
	private String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
	private ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
	private LabeledInputDialog nameNGdi = new LabeledInputDialog(lblP19n2, txtP19n1);
	private LabeledInputDialog namePDi = new LabeledInputDialog(lblP19n3, txtP19n2);
	private LabeledInputDialog Anzahl = new LabeledInputDialog(lblP19n5, txtP19n3);


	protected CompositionPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		setLayout(new MigLayout("", "[108px,grow][108px][108px][108px,grow]", 
				"[20px][20px][20px][20px][20px][20px][20px][20px,grow]"));	
		lblP19n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		Integer pos=0;
		add(lblP19n1, "flowy,cell 1 "+pos.toString()+" 2 1,alignx center,aligny top");	
		pos = nameNGdi.draw(pos, this);			
		add(btnP19n1, "cell 1 "+(++pos).toString()+" 2 1,alignx center");	
		pos = namePDi.draw(pos, this);
		pos = Anzahl.draw(pos, this);
		add(btnP19n2, "cell 1 "+(++pos).toString()+",alignx center");
		add(btnP19n3, "cell 2 "+pos.toString()+",alignx center");
		btnP19n2.setEnabled(false);	
		btnP19n3.setEnabled(false);
		add(lblP19n4, "cell 0 "+(++pos).toString()+" 4 1,alignx center");
		txtP19n2.setEnabled(false);
		txtP19n3.setEnabled(false);
		button1();
		button2();
		button3();
	}

	private void button1() {
		btnP19n1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				LinkedHashMap<String, Set<String>> testMap1 = new LinkedHashMap<String, Set<String>>();
				testMap1.put(bundle.getString("stat56"), Composition.getAllInstances().keySet());
				String name = nameNGdi.getTextNew(testMap1, lblP19n4, bundle.getString("stat02"), bundle.getString("stat03"));					
				if (!"".equals(name)) {
					Composition.instance(name);
					txtP19n1.setEnabled(false);
					btnP19n1.setEnabled(false);
					txtP19n2.setEnabled(true);
					txtP19n3.setEnabled(true);
					btnP19n2.setEnabled(true);
					lblP19n4.setText(bundle.getString("stat57") + Composition.getAllInstances().size()
							+ bundle.getString("stat05"));
				}				
			}			
		});
	}

	private void button2() {
		btnP19n2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = txtP19n2.getText();
				String anzahl = txtP19n3.getText();
				boolean nameOk = true;
				Integer menge;
				try {
					menge = Integer.parseInt(anzahl);
				} catch (NumberFormatException e){
					menge = 0;
				}
				
				if ("".equals(name) || (menge == 0)) {
					nameOk = false;
					lblP19n4.setText(bundle.getString("stat07"));
					return;
				} 
				if (txtP19n2.getText().equals(txtP19n1.getText())) {
					nameOk=false;
					lblP19n4.setText(bundle.getString("stat21"));
					return;					
				}
				if (!ProductSystem.getAllInstances().containsKey(name) &&
						!Component.getAllInstances().containsKey(name) &&
							!Composition.getAllInstances().containsKey(name) &&!(
								ImpactValueMapGroup.getAllInstances().containsKey(name) && IVMGroupType.Component.equals(ImpactValueMapGroup.getInstance(name).getType()) && !(
										ImpactValueMapGroup.getAllInstances().containsKey(name) && IVMGroupType.Composition.equals(ImpactValueMapGroup.getInstance(name).getType())))) {
					lblP19n4.setText(bundle.getString("stat59"));
					nameOk = false;
				}
				if (nameOk) {
					Composition.getInstance(txtP19n1.getText()).addKomponente((ImpactValueMaps) MCAObject.getAllInstances(MCAObject.getClass(txtP19n2.getText())).get(name), menge);
					lblP19n4.setText(bundle.getString("stat60") + Composition.getInstance(txtP19n1.getText()).getKompAnz()
							+ bundle.getString("stat61"));
					btnP19n3.setEnabled(true);
					txtP19n2.setText("");	
					txtP19n3.setText("");	

				}			
			}
		});
	}
	
	private void button3() {
		btnP19n3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtP19n1.setText("");
				txtP19n1.setEnabled(true);
				txtP19n2.setEnabled(false);
				txtP19n3.setEnabled(false);
				btnP19n1.setEnabled(true);
				btnP19n2.setEnabled(false);
				btnP19n3.setEnabled(false);
				lblP19n4.setText(bundle.getString("stat01"));

			}
		});
	}

	@Override
	public void showSelf() {
		Language l = GUILanguage.getChosenLanguage();
		Locale locale = MultiVaLCA.LANGUAGES.get(l);
		String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
		lblP19n1.setText(bundle.getString("p19n1"));
		lblP19n2.setText(bundle.getString("p19n2"));
		lblP19n3.setText(bundle.getString("p19n3"));
		lblP19n4.setText(bundle.getString("stat01"));
		lblP19n5.setText(bundle.getString("p19n4"));
		btnP19n1.setText(bundle.getString("bt14"));
		btnP19n2.setText(bundle.getString("bt15"));
		btnP19n3.setText(bundle.getString("bt04"));	
	}
}
