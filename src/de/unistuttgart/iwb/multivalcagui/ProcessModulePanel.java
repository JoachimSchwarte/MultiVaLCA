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
 * @version 0.541
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
	private JLabel reldifText = new JLabel();			// "Differenz in Prozent"
	private JButton btnP02n1 = new JButton(); 			// "neues Prozessmodul anlegen"
	private JButton btnP02n2 = new JButton();			// "Grenzwerte bestätigen"
	private JButton btnP02n4 = new JButton();			// "fertig"
	private JButton btnP02n3 = new JButton();			// "Fluss hinzufügen"
	private JButton reldifButton = new JButton(); 		// "Prozentsatz anwenden"
	private Language l = GUILanguage.getChosenLanguage();
	private Locale locale = MultiVaLCA.LANGUAGES.get(l);
	private String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
	private ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
	private LowerUpperDialog lud = new LowerUpperDialog(reldifText, reldifButton, lblP02n6, lblP02n7, txtP02n4, txtP02n5);

	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int height = (int) screen.getHeight();
	private Font titlefont = new Font ("Tahoma", Font.BOLD, height *22/1000);
	private Font generalfont = new Font ("Tahoma", Font.LAYOUT_LEFT_TO_RIGHT, height *15/1000);

	protected ProcessModulePanel(String key) {
		super(key);
		initUI();
	}
	
	private void initUI( ) {		
		setLayout(new MigLayout("", "[grow][20%][20%][grow]", 
				"[8%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%][grow]"));		
		Integer pos=0;
		add(lblP02n1, "cell 1 "+pos.toString()+" 2 1,alignx center,aligny center");	
		add(lblP02n2, "cell 1 "+(++pos).toString()+",grow");		
		txtP02n1.setText("");
		add(txtP02n1, "cell 2 "+pos.toString()+",grow");
		txtP02n1.setColumns(10);		
		add(btnP02n1, "cell 1 "+(++pos).toString()+" 2 1,alignx center");		
		add(lblP02n3, "cell 1 "+(++pos).toString()+",grow");	
		txtP02n2.setText("");
		add(txtP02n2, "cell 2 "+pos.toString()+",grow");
		txtP02n2.setColumns(10);
		txtP02n2.setEnabled(false);	
		add(lblP02n4, "cell 1 "+(++pos).toString()+",grow");
		txtP02n3.setText("");
		add(txtP02n3, "cell 2 "+pos.toString()+",grow");
		txtP02n3.setColumns(10);
		txtP02n3.setEnabled(false);		
		btnP02n2.setEnabled(false);
		add(btnP02n2, "cell 1 "+(++pos).toString()+" 2 1,alignx center");
		pos = lud.draw(pos, this);
		btnP02n3.setEnabled(false);
		add(btnP02n3, "cell 1 "+(++pos).toString()+",alignx center");
		btnP02n4.setEnabled(false);
		add(btnP02n4, "cell 2 "+pos.toString()+",alignx center");
		add(lblP02n5, "cell 0 "+(++pos).toString()+" 4 1,alignx center");		
		
		
		
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
						lud.start(txtP02n3.getText());
						btnP02n3.setEnabled(true);
						btnP02n4.setEnabled(false);
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
				LinkedHashMap<ValueType, Double> mengen = lud.getValues(txtP02n3, lblP02n5);
				if (mengen.size() == 3) {
					String mname = txtP02n1.getText();
					String fname = txtP02n2.getText();
					Flow akFlow = Flow.getInstance(fname);
					ProcessModule.getInstance(mname).addFluss(akFlow, ValueType.LowerBound, mengen.get(ValueType.LowerBound));
					ProcessModule.getInstance(mname).addFluss(akFlow, ValueType.UpperBound, mengen.get(ValueType.UpperBound));
					txtP02n2.setText("");
					txtP02n3.setText("");
					txtP02n2.setEnabled(true);
					txtP02n3.setEnabled(true);
					lud.stop();
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
				btnP02n2.setEnabled(false);
				btnP02n4.setEnabled(false);
				btnP02n3.setEnabled(false);
				txtP02n1.setEnabled(true);
				txtP02n2.setEnabled(false);
				txtP02n3.setEnabled(false);
				lud.stop();
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
		txtP02n1.setFont(generalfont);
		txtP02n2.setFont(generalfont);
		txtP02n3.setFont(generalfont);
		txtP02n4.setFont(generalfont);
		txtP02n5.setFont(generalfont);
		lblP02n1.setFont(titlefont);
		lblP02n2.setFont(generalfont);
		lblP02n3.setFont(generalfont);
		lblP02n4.setFont(generalfont);
		lblP02n5.setFont(generalfont);
		lblP02n6.setFont(generalfont);
		lblP02n7.setFont(generalfont);
		btnP02n1.setFont(generalfont);
		btnP02n2.setFont(generalfont);
		btnP02n4.setFont(generalfont);
		btnP02n3.setFont(generalfont);
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
		reldifButton.setText(bundle.getString("btn18"));
		reldifText.setText(bundle.getString("lbl01"));
	}

}
