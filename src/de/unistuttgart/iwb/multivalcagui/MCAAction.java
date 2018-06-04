/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * @author Dr.-Ing. Joachim Schwarte
 * @version 0.511
 */

public abstract class MCAAction extends AbstractAction 
implements IMCAAction {
	
	private String key;
	
	public MCAAction(String name, String description, String key){
		putValue(NAME, name);
		putValue(SHORT_DESCRIPTION, description);
		this.key = key;
	}

	protected String getKey() {
		return key;
	}

	protected void setKey(String key) {
		this.key = key;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		performAction(e);
		
	}
	
	@Override
	public abstract void performAction(ActionEvent e);

}
