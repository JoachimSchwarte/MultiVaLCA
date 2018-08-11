/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import de.unistuttgart.iwb.multivalca.CharacFactor;
import de.unistuttgart.iwb.multivalca.Flow;
import de.unistuttgart.iwb.multivalca.ImpactCategory;
import de.unistuttgart.iwb.multivalca.ValueType;
import net.miginfocom.swing.MigLayout;

/**
 * @author HH, JS
 * @version 0.544
 */

public class CharacFactorPanel extends MCAPanel{
	
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
	private Language l = GUILanguage.getChosenLanguage();
	private Locale locale = MultiVaLCA.LANGUAGES.get(l);
	private String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
	private ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
	private LabeledInputDialog nameCFdi = new LabeledInputDialog(lblP12n2, txtP12n1);
	private LabeledInputDialog nameFLdi = new LabeledInputDialog(lblP12n3, txtP12n2);
	private LabeledInputDialog nameWKdi = new LabeledInputDialog(lblP12n4, txtP12n3);
	private LabeledInputDialog wertCFdi = new LabeledInputDialog(lblP12n5, txtP12n4);
	private LowerUpperDialog lud = new LowerUpperDialog(lblP12n6, lblP12n7, txtP12n5, txtP12n6);
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int height = (int) screen.getHeight();
	private Font titlefont = new Font ("Tahoma", Font.BOLD, height *22/1000);
	private Font generalfont = new Font ("Tahoma", Font.LAYOUT_LEFT_TO_RIGHT, height *15/1000);
	
	protected CharacFactorPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {		
		setLayout(new MigLayout("", "[grow][20%][20%][grow]", 
				"[8%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%][grow]"));		
		
		Integer pos=0;
		
		add(lblP12n1, "cell 1 "+pos.toString()+" 2 1,alignx center, aligny center");	
		pos = nameCFdi.draw(pos, this);				
		pos = nameFLdi.draw(pos, this);			
		pos = nameWKdi.draw(pos, this);			
		pos = wertCFdi.draw(pos, this);		
		add(btnP12n1, "cell 1 "+(++pos).toString()+" 2 1,alignx center");		
		pos = lud.draw(pos, this);	
		btnP12n2.setEnabled(false);
		add(btnP12n2, "cell 1 "+(++pos).toString()+" 2 1,alignx center");
		add(lblP12n8, "cell 0 "+(++pos).toString()+" 4 1,alignx center");	
		
		button1();		
		button2();	
	}

	private void button1() {
		btnP12n1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {	
				boolean inputOK = true;
				Double facVal = 0.0;
				if (wertCFdi.isPosNum(lblP12n8, bundle.getString("stat07"), bundle.getString("stat26"))) {
					facVal = wertCFdi.getNum(lblP12n8, bundle.getString("stat07"), bundle.getString("stat26"));
				}
				else {
					inputOK = false;
				}
				Set<String> testSet1 = ImpactCategory.getAllInstances().keySet();				
				String nameWK = nameWKdi.getTextOld(bundle.getString("stat27"), testSet1, lblP12n8, bundle.getString("stat07")) ;
				Set<String> testSet2 = Flow.getAllInstances().keySet();
				String nameFl = nameFLdi.getTextOld(bundle.getString("stat11"), testSet2, lblP12n8, bundle.getString("stat07")) ;
				LinkedHashMap<String, Set<String>> testMap1 = new LinkedHashMap<String, Set<String>>();
				testMap1.put(bundle.getString("stat03"), CharacFactor.getAllInstances().keySet());
				String nameCF = nameCFdi.getTextNew(testMap1, lblP12n8, bundle.getString("stat07"), bundle.getString("stat03"));								
				if ("".equals(nameCF) || "".equals(nameFl) || "".equals(nameWK) ) {
					inputOK = false;				
				}
				if (inputOK) {
					CharacFactor.instance(nameCF, Flow.getInstance(nameFl), ImpactCategory.getInstance(nameWK), facVal);
					txtP12n1.setEnabled(false);
					txtP12n2.setEnabled(false);
					txtP12n3.setEnabled(false);
					txtP12n4.setEnabled(false);
					lud.start(txtP12n4.getText());
					btnP12n1.setEnabled(false);
					btnP12n2.setEnabled(true);
				}				
			}			
		});
	}

	private void button2() {
		btnP12n2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				LinkedHashMap<ValueType, Double> mengen = lud.getValues(txtP12n4, lblP12n8);
				if (mengen.size() == 3) {
					String nameCF = txtP12n1.getText();
					CharacFactor.setLowerBound(nameCF, mengen.get(ValueType.LowerBound));
					CharacFactor.setUpperBound(nameCF, mengen.get(ValueType.UpperBound));
					txtP12n1.setText("");
					txtP12n2.setText("");
					txtP12n3.setText("");
					txtP12n4.setText("");
					txtP12n1.setEnabled(true);
					txtP12n2.setEnabled(true);
					txtP12n3.setEnabled(true);
					txtP12n4.setEnabled(true);
					btnP12n1.setEnabled(true);
					btnP12n2.setEnabled(false);	
					lud.stop();
					lblP12n8.setText(bundle.getString("stat01"));
				}		
			}		
		});				
	}

	@Override
	public void showSelf() {
		l = GUILanguage.getChosenLanguage();
		locale = MultiVaLCA.LANGUAGES.get(l);
		baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		bundle = ResourceBundle.getBundle(baseName, locale);	
		
		lblP12n1.setFont(titlefont);
		txtP12n1.setFont(generalfont);
		txtP12n2.setFont(generalfont);
		txtP12n3.setFont(generalfont);
		txtP12n4.setFont(generalfont);
		txtP12n5.setFont(generalfont);
		txtP12n6.setFont(generalfont);
		lblP12n2.setFont(generalfont);
		lblP12n3.setFont(generalfont);
		lblP12n4.setFont(generalfont);
		lblP12n5.setFont(generalfont);
		lblP12n6.setFont(generalfont);
		lblP12n7.setFont(generalfont);
		lblP12n8.setFont(generalfont);
		btnP12n1.setFont(generalfont);
		btnP12n2.setFont(generalfont);
		
		lblP12n1.setText(bundle.getString("p12n1"));
		lblP12n2.setText(bundle.getString("p12n2"));
		lblP12n3.setText(bundle.getString("p01n2"));
		lblP12n4.setText(bundle.getString("p10n2"));
		lblP12n5.setText(bundle.getString("p12n3"));
		lblP12n6.setText(bundle.getString("p02n5"));
		lblP12n7.setText(bundle.getString("p02n6"));
		lblP12n8.setText(bundle.getString("stat01"));
		btnP12n1.setText(bundle.getString("bt01"));
		btnP12n2.setText(bundle.getString("bt10"));	
	}
}
