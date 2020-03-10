/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import de.unistuttgart.iwb.multivalca.Component;
import de.unistuttgart.iwb.multivalca.Flow;
import de.unistuttgart.iwb.multivalca.FlowUnit;
import de.unistuttgart.iwb.multivalca.IVMGroupType;
import de.unistuttgart.iwb.multivalca.ImpactValueMapGroup;
import de.unistuttgart.iwb.multivalca.ImpactValueMaps;
import de.unistuttgart.iwb.multivalca.MCAObject;
import de.unistuttgart.iwb.multivalca.ProductDeclaration;
import de.unistuttgart.iwb.multivalca.ProductSystem;
import de.unistuttgart.iwb.multivalca.ValueType;
import net.miginfocom.swing.MigLayout;

/**
 * @author Dr.-Ing. Joachim Schwarte, Helen Hein, Johannes Dippon
 * @version 0.700
 */

public class ComponentPanel extends MCAPanel{
	private Language l = GUILanguage.getChosenLanguage();
	private Locale locale = MultiVaLCA.LANGUAGES.get(l);
	private String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
	private ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);	
	private JLabel lblP18n1 = new JLabel();				// "neue Komponente"
	private JLabel lblP18n2 = new JLabel();				// "Name der Komponente"
	private JLabel lblP18n3 = new JLabel();				// "Bezug"
	private JLabel lblP18n4 = new JLabel();				// "Menge"
	private JLabel lblP18n5 = new JLabel();				// "Untergrenze"
	private JLabel lblP18n6 = new JLabel();				// "Obergrenze"
	private JLabel lblP18n7 = new JLabel();				// Status
	private JLabel reldifText = new JLabel();			// "Differenz in Prozent"
	private JTextField txtP18n1 = new JTextField();		// Eingabefeld Name
	private JTextField txtP18n2 = new JTextField();		// Eingabefeld Bezug
	private JTextField txtP18n3 = new JTextField();		// Eingabefeld Menge
	private JTextField txtP18n4 = new JTextField();		// Eingabefeld Untergrenze
	private JTextField txtP18n5 = new JTextField();		// Eingabefeld Obergrenze
	private JButton btnP18n1 = new JButton(); 			// "neue Komponente anlegen"
	private JButton btnP18n2 = new JButton(); 			// "fertig"
	private JButton reldifButton = new JButton(); 		// "Prozentsatz anwenden"
	private LowerUpperDialog lud = new LowerUpperDialog(reldifText, reldifButton, lblP18n5, lblP18n6, txtP18n4, txtP18n5);

	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int height = (int) screen.getHeight();
	private Font titlefont = new Font ("Tahoma", Font.BOLD, height *22/1000);
	private Font generalfont = new Font ("Tahoma", Font.LAYOUT_LEFT_TO_RIGHT, height *15/1000);

	
	protected ComponentPanel(String key) {
		super(key);
		
		setLayout(new MigLayout("", "[grow][20%][20%][grow]", 
				"[8%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%]2%[4%][grow]"));	

		
		Integer pos=0;
		add(lblP18n1, "cell 1 "+pos.toString()+" 2 1,alignx center,aligny center");
		add(lblP18n2, "cell 1 "+(++pos).toString()+",grow");		
		txtP18n1.setText("");
		add(txtP18n1, "cell 2 "+pos.toString()+",grow");
		txtP18n1.setColumns(10);
		add(lblP18n3, "cell 1 "+(++pos).toString()+",grow");		
		txtP18n2.setText("");
		add(txtP18n2, "cell 2 "+pos.toString()+",grow");
		txtP18n2.setColumns(10);
		add(lblP18n4, "cell 1 "+(++pos).toString()+",grow");		
		txtP18n3.setText("");
		add(txtP18n3, "cell 2 "+pos.toString()+",grow");
		txtP18n3.setColumns(10);
		btnP18n1.setEnabled(true);
		add(btnP18n1, "cell 1 "+(++pos).toString()+" 2 1,alignx center");		
		pos = lud.draw(pos, this);
		btnP18n2.setEnabled(false); 
		add(btnP18n2, "cell 1 "+(++pos).toString()+" 2 1,alignx center");
		add(lblP18n7, "cell 0 "+(++pos).toString()+" 4 1,alignx center");

		button1();		
		button2();	
	}

	private void button1() {
		btnP18n1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String comName = txtP18n1.getText();	
				String refName = txtP18n2.getText();
				String fmenge = txtP18n3.getText();
				Double menge;
				FlowUnit fu = null;
				if ("".equals(comName)) {
					lblP18n7.setText(bundle.getString("stat02"));
					return;
				} 
				if (comName != comName.replaceAll(" ","")) {
					lblP18n7.setText(bundle.getString("stat20"));
					txtP18n1.setText("");
					return;
				}
				if (MCAObject.containsName(comName)) {
					lblP18n7.setText(bundle.getString("stat03"));
					txtP18n1.setText("");
					return;
				}
				if (!ProductSystem.getAllInstances().containsKey(refName) &&
						!ProductDeclaration.getAllInstances().containsKey(refName) && !(
							ImpactValueMapGroup.getAllInstances().containsKey(refName) && IVMGroupType.ProductDeclaration.equals(ImpactValueMapGroup.getInstance(refName).getType()))) {
					lblP18n7.setText(bundle.getString("stat42"));
					return;
				}
				try {
					menge = Double.parseDouble(fmenge);
				} catch (NumberFormatException e){
					menge = 0.0;
				}
				if (menge == 0.0) {
					lblP18n7.setText(bundle.getString("stat07"));
					return;
				}									
				Component.newInstance(comName, refName);
				
				if (ProductSystem.getAllInstances().containsKey(refName)) {
					LinkedHashMap<Flow, Double> ps = ProductSystem.getInstance(refName).getBedarfsvektor();
					List<Flow> keyList = new ArrayList<Flow>(ps.keySet());
					fu = Flow.getInstance(keyList.get(0).getName()).getEinheit();				
				}
				if(ProductDeclaration.getAllInstances().containsKey(refName)) {
					fu = ProductDeclaration.getInstance(refName).getEinheit();					
				}
				if(ImpactValueMapGroup.getAllInstances().containsKey(refName) && IVMGroupType.ProductDeclaration.equals(ImpactValueMapGroup.getInstance(refName).getType())) {
					LinkedHashSet<String> pdl = new LinkedHashSet<String>();
					for (ImpactValueMaps ivml : ImpactValueMapGroup.getInstance(refName).getIVMList()) {
						 pdl.add(ivml.getName());
					}	
					String[] str = new String[pdl.size()];
					pdl.toArray(str);
					fu = ProductDeclaration.getInstance(str[0]).getEinheit(); 
				}				
				Component.getInstance(comName).setEinheit(fu);				
				txtP18n1.setEnabled(false);
				txtP18n2.setEnabled(false);
				txtP18n3.setEnabled(false);
				btnP18n1.setEnabled(false);
				lud.start(fmenge);
				btnP18n2.setEnabled(true);			
			}		
		});
	}

	private void button2() {
		btnP18n2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				LinkedHashMap<ValueType, Double> mengen = lud.getValues(txtP18n3, lblP18n7);
				if (mengen.size() == 3) {
					String comName = txtP18n1.getText();
					Component.getInstance(comName).setValues(mengen);
					txtP18n1.setEnabled(true);
					txtP18n2.setEnabled(true);
					txtP18n3.setEnabled(true);
					btnP18n1.setEnabled(true);
					btnP18n2.setEnabled(false);	
					txtP18n1.setText("");
					txtP18n2.setText("");
					txtP18n3.setText("");
					lud.stop();
					lblP18n7.setText(bundle.getString("stat01"));	
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
		
		lblP18n1.setFont(titlefont);
		lblP18n2.setFont(generalfont);
		lblP18n3.setFont(generalfont);
		lblP18n4.setFont(generalfont);
		lblP18n5.setFont(generalfont);
		lblP18n6.setFont(generalfont);
		lblP18n7.setFont(generalfont);
		txtP18n1.setFont(generalfont);
		txtP18n2.setFont(generalfont);
		txtP18n3.setFont(generalfont);
		txtP18n4.setFont(generalfont);
		txtP18n5.setFont(generalfont);
		btnP18n1.setFont(generalfont);
		btnP18n2.setFont(generalfont);
		
		lblP18n1.setText(bundle.getString("p18n1"));
		lblP18n2.setText(bundle.getString("p18n2"));
		lblP18n3.setText(bundle.getString("p18n3"));
		lblP18n4.setText(bundle.getString("p02n4"));
		lblP18n5.setText(bundle.getString("p02n5"));
		lblP18n6.setText(bundle.getString("p02n6"));
		lblP18n7.setText(bundle.getString("stat01"));
		btnP18n1.setText(bundle.getString("bt13"));
		btnP18n2.setText(bundle.getString("bt04"));	
		reldifButton.setText(bundle.getString("btn18"));
		reldifText.setText(bundle.getString("lbl01"));
	}
}
