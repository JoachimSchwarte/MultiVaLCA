/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.511
 */

public abstract class MCAPanel extends JPanel{ 
	protected final String key;
	private static final HashMap<String, MCAPanel> PANEL_MAP = new HashMap<>();
	
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
