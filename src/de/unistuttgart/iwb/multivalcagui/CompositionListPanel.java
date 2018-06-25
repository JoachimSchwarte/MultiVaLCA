package de.unistuttgart.iwb.multivalcagui;

import java.awt.Font;

import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

public class CompositionListPanel extends MCAPanel{
	
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

	private JLabel lblP22n1 = new JLabel();

	@Override
	public void showSelf() {
		Language l = GUILanguage.getChosenLanguage();
		lblP22n1.setText(GuiStrings.getGS("mp49e", l));
		
	}

}
