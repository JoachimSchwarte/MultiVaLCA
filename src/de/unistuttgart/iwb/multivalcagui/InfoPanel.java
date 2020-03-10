/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;

/**
 * @author HH, JS
 * @version 0.537
 */

public class InfoPanel extends MCAPanel{
	
	protected static JLabel lblInfo1 = new JLabel(); 	
	protected static JLabel lblInfo2 = new JLabel(); 	
	protected static JLabel lblInfo3 = new JLabel();		
	protected static JLabel lblInfo4 = new JLabel();		
	protected static JLabel lblInfo5 = new JLabel();	

	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int height = (int) screen.getHeight();
	private Font multivalca = new Font ("Tahoma", Font.BOLD, height *28/1000);
	private Font genfontbold = new Font ("Tahoma", Font.BOLD, height *15/1000);
	private Font infofont = new Font ("Tahoma", Font.LAYOUT_LEFT_TO_RIGHT, height *12/1000);
	
	protected InfoPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		setLayout(new MigLayout("", "[grow][20%][grow]", 
				"[20%][4%]4%[4%][4%][grow][4%]"));		
		lblInfo1.setFont(multivalca);
		add(lblInfo1, "cell 1 1,alignx center,aligny top");		
		lblInfo2.setFont(genfontbold);
		add(lblInfo2, "cell 1 2,alignx center,aligny top");		
		lblInfo3.setFont(genfontbold);
		add(lblInfo3, "cell 1 3,alignx center,aligny top");		
		lblInfo4.setFont(genfontbold);
		add(lblInfo4, "cell 1 4,alignx center,aligny top");		
		lblInfo5.setFont(infofont);
		add(lblInfo5, "cell 1 5,alignx center,aligny top");	
	}
	

	@Override
	public void showSelf() {
		Language l = GUILanguage.getChosenLanguage();
		Locale locale = MultiVaLCA.LANGUAGES.get(l);
		String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
		lblInfo1.setText(bundle.getString("head1"));
		lblInfo2.setText(bundle.getString("info1"));
		lblInfo3.setText(bundle.getString("info2"));
		lblInfo4.setText(bundle.getString("info3"));			
	}
}
