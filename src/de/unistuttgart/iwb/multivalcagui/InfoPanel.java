/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.Font;
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
	private Language l = GUILanguage.getChosenLanguage();
	private Locale locale = MultiVaLCA.LANGUAGES.get(l);
	private String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
	private ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);

	protected InfoPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		setLayout(new MigLayout("", "[108px,grow][200px][108px,grow]", 
				"[20px][20px][40px][20px][20px][20px][20px,grow][20px]"));		
		lblInfo1.setFont(new Font("Tahoma", Font.BOLD, 26));
		add(lblInfo1, "cell 1 2,alignx center,aligny top");		
		lblInfo2.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblInfo2, "cell 1 3,alignx center,aligny top");		
		lblInfo3.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblInfo3, "cell 1 4,alignx center,aligny top");		
		lblInfo4.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblInfo4, "cell 1 5,alignx center,aligny top");		
		lblInfo5.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblInfo5, "cell 1 7,alignx center,aligny top");	
	}

	@Override
	public void showSelf() {
		l = GUILanguage.getChosenLanguage();
		locale = MultiVaLCA.LANGUAGES.get(l);
		bundle = ResourceBundle.getBundle(baseName, locale);
		lblInfo1.setText(bundle.getString("head1"));
		lblInfo2.setText(bundle.getString("info1"));
		lblInfo3.setText(bundle.getString("info2"));
		lblInfo4.setText(bundle.getString("info3"));			
	}
}
