/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import de.unistuttgart.iwb.multivalca.FlowUnit;
import de.unistuttgart.iwb.multivalca.ImpactCategory;
import de.unistuttgart.iwb.multivalca.ProductDeclaration;
import de.unistuttgart.iwb.multivalca.ValueType;
import net.miginfocom.swing.MigLayout;

/**
 * @author Dr.-Ing. Joachim Schwarte, Helen Hein, Johannes Dippon
 * @version 0.700
 */

public class DeklarationPanel extends MCAPanel {
	private JLabel lblP17n1 = new JLabel();				// "neue Produktdeklaration"
	private JLabel lblP17n2 = new JLabel();				// "Name des ..."
	private JLabel lblP17n3 = new JLabel();				// "Einheit"
	private JLabel lblP17n5 = new JLabel();				// "Wirkungskategorie"
	private JLabel lblP17n6 = new JLabel();				// "Menge"
	private JLabel lblP17n7 = new JLabel();				// "Untergrenze"
	private JLabel lblP17n8 = new JLabel();				// "Obergrenze"
	private JLabel lblP17n9 = new JLabel();				// Status
	private JLabel reldifText = new JLabel();			// "Differenz in Prozent"
	private JButton btnP17n1 = new JButton(); 			// "neue Produktdeklaration anlegen"
	private JButton btnP17n2 = new JButton(); 			// "Wirkungsabschätzung hinzufügen"
	private JButton btnP17n3 = new JButton(); 			// "Grenzwerte bestätigen"
	private JButton btnP17n4 = new JButton(); 			// "fertig"
	private JButton reldifButton = new JButton(); 		// "Prozentsatz anwenden"
	private JTextField txtP17n1 = new JTextField();		// Eingabefeld Produkt-Name
	private JTextField txtP17n3 = new JTextField();		// Eingabefeld Wirkungskategorie
	private JTextField txtP17n4 = new JTextField();		// Eingabefeld Menge
	private JTextField txtP17n5 = new JTextField();		// Eingabefeld Untergrenze
	private JTextField txtP17n6 = new JTextField();		// Eingabefeld Obergrenze
	private JComboBox<FlowUnit> comboBox_2 = new JComboBox<FlowUnit>();
	private Language l = GUILanguage.getChosenLanguage();
	private Locale locale = MultiVaLCA.LANGUAGES.get(l);
	private String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
	private ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
	private LabeledInputDialog namePNdi = new LabeledInputDialog(lblP17n2, txtP17n1);
	private LabeledInputDialog nameWKdi = new LabeledInputDialog(lblP17n5, txtP17n3);
	private LabeledInputDialog wertMEdi = new LabeledInputDialog(lblP17n6, txtP17n4);
	private LowerUpperDialog lud = new LowerUpperDialog(reldifText, reldifButton, lblP17n7, lblP17n8, txtP17n5, txtP17n6);

	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int height = (int) screen.getHeight();
	private Font titlefont = new Font ("Tahoma", Font.BOLD, height *22/1000);
	private Font generalfont = new Font ("Tahoma", Font.LAYOUT_LEFT_TO_RIGHT, height *15/1000);
	
	protected DeklarationPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		
		setLayout(new MigLayout("", "[grow][20%][20%][grow]", 
				"[8%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%][grow]"));
			
		Integer pos=0;
		add(lblP17n1, "cell 1 "+pos.toString()+" 2 1,alignx center,aligny center");	
		pos = namePNdi.draw(pos, this);	
		add(lblP17n3, "cell 1 "+(++pos).toString()+",grow");	
		comboBox_2.setModel(new DefaultComboBoxModel<FlowUnit>(FlowUnit.values()));
		add(comboBox_2, "cell 2 "+(pos).toString()+",grow");	
		add(btnP17n1, "cell 1 "+(++pos).toString()+" 2 1,alignx center");		
		pos = nameWKdi.draw(pos, this);	
		pos = wertMEdi.draw(pos, this);					
		btnP17n2.setEnabled(false);
		add(btnP17n2, "cell 1 "+(++pos).toString()+" 2 1,alignx center");	
		pos = lud.draw(pos, this);	
		btnP17n3.setEnabled(false);
		add(btnP17n3, "cell 1 "+(++pos).toString()+",alignx center");
		btnP17n4.setEnabled(false);
		add(btnP17n4, "cell 2 "+(pos).toString()+",alignx center");
		add(lblP17n9, "cell 0 "+(++pos).toString()+" 4 1,alignx center");	
		txtP17n3.setEnabled(false);
		txtP17n4.setEnabled(false);
		
		button1();
		button2();
		button3();
		button4();	
		
	}
			
		private void button1() {
			btnP17n1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String nameProd = txtP17n1.getText();
					FlowUnit einheit = comboBox_2.getItemAt(comboBox_2.getSelectedIndex());
					LinkedHashMap<String, Set<String>> testMap1 = new LinkedHashMap<String, Set<String>>();
					testMap1.put(bundle.getString("stat50"), ProductDeclaration.getAllInstances().keySet());
					String name = namePNdi.getTextNew(testMap1, lblP17n9, bundle.getString("stat02"), bundle.getString("stat03"));	
					if (!"".equals(name)) {
						ProductDeclaration.instance(nameProd, einheit);
						btnP17n1.setEnabled(false);	
						txtP17n1.setEnabled(false);
						comboBox_2.setEnabled(false);
						btnP17n2.setEnabled(true);
						txtP17n3.setEnabled(true);
						txtP17n4.setEnabled(true);
						lblP17n9.setText(bundle.getString("stat34") + 
								ProductDeclaration.getAllInstances().size() +
								bundle.getString("stat05"));
					}			
				}		
			});
		}
		
		private void button2() {
			btnP17n2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String nameProd = txtP17n1.getText();
					String fname = txtP17n3.getText();
					String fmenge = txtP17n4.getText();
					Double menge;
					try {
						menge = Double.parseDouble(fmenge);
					} catch (NumberFormatException e){
						menge = 0.0;
					}
					if ("".equals(fname) || (menge == 0.0)) {
						lblP17n9.setText(bundle.getString("stat07"));
					} else {
						if (ImpactCategory.containsName(fname)) {
							ImpactCategory ic = ImpactCategory.getInstance(fname);
							LinkedHashMap<ValueType, Double> values = new LinkedHashMap<ValueType, Double>();
							values.put(ValueType.MeanValue, menge);
							values.put(ValueType.LowerBound, menge);
							values.put(ValueType.UpperBound, menge);
							ProductDeclaration.getInstance(nameProd).addWirkung(ic, values);
							txtP17n3.setEnabled(false);
							txtP17n4.setEnabled(false);
							btnP17n2.setEnabled(false);
							lud.start(txtP17n4.getText());
							btnP17n3.setEnabled(true);
							lblP17n9.setText(bundle.getString("stat51") + " " + nameProd + bundle.getString("stat09") + 
									ProductDeclaration.getInstance(nameProd).getImpactValueMap().size() + bundle.getString("stat49"));
							
						} else {
							lblP17n9.setText(bundle.getString("stat35"));
						}					
					}
				}
			});
		}
		
		private void button3() {
			btnP17n3.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					LinkedHashMap<ValueType, Double> mengen = lud.getValues(txtP17n4, lblP17n9);
					if (mengen.size() == 3) {
						String nameProd = txtP17n1.getText();
						String fname = txtP17n3.getText();
						ImpactCategory ic = ImpactCategory.getInstance(fname);
						LinkedHashMap<ValueType, Double> values = new LinkedHashMap<ValueType, Double>();
						values.put(ValueType.MeanValue, mengen.get(ValueType.MeanValue));
						values.put(ValueType.LowerBound, mengen.get(ValueType.LowerBound));
						values.put(ValueType.UpperBound, mengen.get(ValueType.UpperBound));
						ProductDeclaration.getInstance(nameProd).addWirkung(ic, values);
						txtP17n3.setText("");
						txtP17n4.setText("");
						txtP17n3.setEnabled(true);
						txtP17n4.setEnabled(true);
						lud.stop();
						btnP17n2.setEnabled(true);				
						btnP17n3.setEnabled(false);
						btnP17n4.setEnabled(true);
						lblP17n9.setText(bundle.getString("stat01"));
					}
				}			
			});	
		}
		
	private void button4() {
		btnP17n4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnP17n1.setEnabled(true);
				txtP17n1.setText("");
				txtP17n3.setText("");
				txtP17n4.setText("");
				btnP17n2.setEnabled(false);
				btnP17n4.setEnabled(false);
				btnP17n3.setEnabled(false);
				txtP17n1.setEnabled(true);
				comboBox_2.setEnabled(true);
				comboBox_2.setSelectedIndex(0);
				txtP17n3.setEnabled(false);
				txtP17n4.setEnabled(false);
				lud.stop();
				lblP17n9.setText(bundle.getString("stat01"));
			}
		});	
	}		

	@Override
	public void showSelf() {

		l = GUILanguage.getChosenLanguage();
		locale = MultiVaLCA.LANGUAGES.get(l);
		baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		bundle = ResourceBundle.getBundle(baseName, locale);
		
		lblP17n1.setFont(titlefont);
		lblP17n2.setFont(generalfont);
		lblP17n3.setFont(generalfont);
		lblP17n5.setFont(generalfont);
		lblP17n6.setFont(generalfont);
		lblP17n7.setFont(generalfont);
		lblP17n8.setFont(generalfont);
		lblP17n9.setFont(generalfont);
		btnP17n1.setFont(generalfont);
		btnP17n2.setFont(generalfont);
		btnP17n3.setFont(generalfont);
		btnP17n4.setFont(generalfont);
		txtP17n1.setFont(generalfont);
		txtP17n3.setFont(generalfont);
		txtP17n4.setFont(generalfont);
		txtP17n5.setFont(generalfont);
		txtP17n6.setFont(generalfont);
		comboBox_2.setFont(generalfont);

		
		lblP17n1.setText(bundle.getString("p17n1"));
		lblP17n2.setText(bundle.getString("p17n2"));
		lblP17n3.setText(bundle.getString("p01n4"));
		btnP17n1.setText(bundle.getString("mp17e"));
		lblP17n5.setText(bundle.getString("mp14"));
		lblP17n6.setText(bundle.getString("p02n4"));
		btnP17n2.setText(bundle.getString("bt11"));
		lblP17n7.setText(bundle.getString("p02n5"));
		lblP17n8.setText(bundle.getString("p02n6"));
		btnP17n3.setText(bundle.getString("bt10"));
		btnP17n4.setText(bundle.getString("bt04"));
		lblP17n9.setText(bundle.getString("stat01"));
		reldifButton.setText(bundle.getString("btn18"));
		reldifText.setText(bundle.getString("lbl01"));
	}
}
