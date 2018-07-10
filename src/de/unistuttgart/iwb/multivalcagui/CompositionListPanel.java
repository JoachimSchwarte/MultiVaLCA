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
 * @version 0.536
 */

public class CompositionListPanel extends MCAPanel{
	
	private JLabel lblP22n1 = new JLabel();
	private Language l = GUILanguage.getChosenLanguage();
	private Locale locale = MultiVaLCA.LANGUAGES.get(l);
	private String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
	private ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
	
	protected CompositionListPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		setLayout(new MigLayout("", "[108px,grow][108px][108px][108px,grow]", 
				"[20px][20px][20px][20px][20px][20px][20px,grow]"));
		lblP22n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblP22n1, "cell 1 0 2 1,alignx center,aligny top");
		
	}

	@Override
	public void showSelf() {
		l = GUILanguage.getChosenLanguage();
		locale = MultiVaLCA.LANGUAGES.get(l);
		baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		bundle = ResourceBundle.getBundle(baseName, locale);
		lblP22n1.setText(bundle.getString("mp49e"));
		
	}

}
