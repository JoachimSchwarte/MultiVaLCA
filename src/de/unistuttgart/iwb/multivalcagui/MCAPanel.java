/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JPanel;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.804
 */

public abstract class MCAPanel extends JPanel{ 
	protected final String key;
	private static final HashMap<String, MCAPanel> PANEL_MAP = new HashMap<>();
	
	Language l = GUILanguage.getChosenLanguage();
	Locale locale = MultiVaLCA.LANGUAGES.get(l);
	String baseName = "de.unistuttgart.iwb.multivalcagui.messages";
	ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	int height = (int) screen.getHeight();
	Font titlefont = new Font ("Tahoma", Font.BOLD, height *22/1000);
	Font generalfont = new Font ("Tahoma", Font.LAYOUT_LEFT_TO_RIGHT, height *15/1000);
	
	public static MCAPanel get(String key) {		
		return PANEL_MAP.get(key);
	}
	
	protected MCAPanel(String key) {
		this.key = key;
		PANEL_MAP.put(key, this);		
	}

	protected String getKey() {
		return key;
	}
	
	public abstract void showSelf(); 
 
	public void showMe() {
		showSelf();
	}
}
