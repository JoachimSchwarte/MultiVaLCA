/*	
 * MultiVaLCA
 */

package de.unistuttgart.iwb.multivalcagui;

import java.awt.Font;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;

/**
 * @author HH, JS
 * @version 0.533
 */

public class InfoPanel extends MCAPanel{
	
	protected static JLabel lblInfo1 = new JLabel(); 	
	protected static JLabel lblInfo2 = new JLabel(); 	
	protected static JLabel lblInfo3 = new JLabel();		
	protected static JLabel lblInfo4 = new JLabel();		
	protected static JLabel lblInfo5 = new JLabel();		

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
		Language l = GUILanguage.getChosenLanguage();
		lblInfo1.setText(GuiStrings.getGS("head1", l));
		lblInfo2.setText(GuiStrings.getGS("info1", l));
		lblInfo3.setText(GuiStrings.getGS("info2", l));
		lblInfo4.setText(GuiStrings.getGS("info3", l));
		lblInfo5.setText(GuiStrings.getGS("head2", l)+"     "+GuiStrings.getGS("date", l));		
		
		
	}

}
