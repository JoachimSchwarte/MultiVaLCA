package de.unistuttgart.iwb.multivalcagui;

import java.awt.Font;

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
		lblP21n1.setText(GuiStrings.getGS("mp48e", l));
		
	}

}
