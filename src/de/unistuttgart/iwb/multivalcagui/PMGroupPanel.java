package de.unistuttgart.iwb.multivalcagui;

import java.awt.Font;

import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

public class PMGroupPanel extends MCAPanel {
	
	private JLabel lblP23n1 = new JLabel();

	protected PMGroupPanel(String key) {
		super(key);
		initUI();
	}

	private void initUI() {
		setLayout(new MigLayout("", "[108px,grow][108px][108px][108px,grow]", 
				"[20px][20px][20px][20px][20px][20px][20px,grow]"));
		lblP23n1.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblP23n1, "cell 1 0 2 1,alignx center,aligny top");
		
	}

	@Override
	public void showSelf() {
		// TODO Auto-generated method stub
		
	}

}
