package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

public class ButtonLogLnXn extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;

	private String name;

	public ButtonLogLnXn(String name) {
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
		double value = Calculator.model.getValue();
		double result = 0;

		if (name.equals("x^n")) {
			if (Calculator.inv.isSelected()) {
				if (!Calculator.model.isActiveOperandSet()) {
					Calculator.model.setActiveOperand(value);
					Calculator.model.freezeValue(String.valueOf(value));
					Calculator.model.clear();
					return;
				} else {
					result = Math.pow(Calculator.model.getActiveOperand(), 1/value);
				}
			} else {
				if (!Calculator.model.isActiveOperandSet()) {
					Calculator.model.setActiveOperand(value);
					Calculator.model.freezeValue(String.valueOf(value));
					Calculator.model.clear();
					return;
				} else {
					result = Math.pow(Calculator.model.getActiveOperand(), value);
				}
			}
		} else {
			if (Calculator.inv.isSelected()) {
				switch (name) {
				case "log":
					result = Math.pow(10, value);
					break;
				case "ln":
					result = Math.pow(Math.E, value);
					break;
				}
			} else {
				switch (name) {
				case "log":
					result = Math.log10(value);
					break;
				case "ln":
					result = Math.log(value);
					break;
				}
			}
		}

		Calculator.model.setValue(result);

	}
}