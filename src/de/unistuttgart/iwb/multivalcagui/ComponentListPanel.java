package de.unistuttgart.iwb.multivalcagui;

import java.awt.Font;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

public class ComponentListPanel extends MCAPanel{
	
	private JLabel lblP21n1 = new JLabel();


	protected ComponentListPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		setLayout(new MigLayout("", "[108px,grow][108px][108px][108px,grow]", 
				"[20px][20px][20px][20px][20px][20px][20px,grow]"));	
		lblP21n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblP21n1, "cell 1 0 2 1,alignx center,aligny top");
		
		
	}

	@Override
	public void showSelf() {
		Language l = GUILanguage.getChosenLanguage();
		Locale locale = MultiVaLCA.LANGUAGES.get(l);
		String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
		lblP21n1.setText(bundle.getString("mp48e"));
		
	}

}
