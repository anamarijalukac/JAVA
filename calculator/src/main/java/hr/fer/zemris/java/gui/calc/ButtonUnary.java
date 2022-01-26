package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

public class ButtonUnary extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;

	private String name;

	public ButtonUnary(String name) {
		this.name = name;
		this.setName(name);
		setBackground(Color.cyan);
		setText(name);
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (Calculator.model.hasFrozenValue())
			throw new CalculatorInputException("has frozen value");
		double value = Math.toRadians(Calculator.model.getValue());
		double result = 0;
		if (Calculator.inv.isSelected()) {
			switch (name) {
			case "sin":
				result = Math.asin(value);
				break;
			case "cos":
				result = Math.acos(value);
				break;
			case "tan":
				result = Math.atan(value);
				break;
			case "ctg":
				result = Math.PI / 2 - Math.atan(value);
				break;
			}
		} else {
			switch (name) {
			case "sin":
				result = Math.sin(value);
				break;
			case "cos":
				result = Math.cos(value);
				break;
			case "tan":
				result = Math.tan(value);
				break;
			case "ctg":
				result = 1 / Math.tan(value);
				break;

			}
		}

		Calculator.model.setValue(result);

	}

}
