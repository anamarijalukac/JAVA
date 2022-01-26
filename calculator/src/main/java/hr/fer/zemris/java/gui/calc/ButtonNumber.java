package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonNumber extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;

	private int number;

	public ButtonNumber(String name) {
		number = Integer.parseInt(name);
		setBackground(Color.cyan);
		setText(name);
		setFont(this.getFont().deriveFont(30f));
		addActionListener(this);
	}

	public void actionPerformed(ActionEvent ae) {

		Calculator.model.insertDigit(number);
	}

}
