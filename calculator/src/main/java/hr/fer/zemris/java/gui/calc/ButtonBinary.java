package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

public class ButtonBinary extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;

	private String name;

	public ButtonBinary(String name) {
		this.name = name;
		setBackground(Color.cyan);
		setText(name);
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (Calculator.model.hasFrozenValue())
			throw new CalculatorInputException("has frozen value");

		if (Calculator.model.isActiveOperandSet()) {
			double result = Calculator.model.getPendingBinaryOperation()
					.applyAsDouble(Calculator.model.getActiveOperand(), Calculator.model.getValue());
			Calculator.model.freezeValue(String.valueOf(result));
			Calculator.model.setActiveOperand(result);
		} else {

			Calculator.model.freezeValue(String.valueOf(Calculator.model.getValue()));
			Calculator.model.setActiveOperand(Calculator.model.getValue());
		}
		switch (name) {
		case "+":
			Calculator.model.setPendingBinaryOperation((a, b) -> {
				return a + b;
			});
			break;
		case "-":
			Calculator.model.setPendingBinaryOperation((a, b) -> {
				return a - b;
			});
			break;
		case "*":
			Calculator.model.setPendingBinaryOperation((a, b) -> {
				return a * b;
			});
			break;
		case "/":
			Calculator.model.setPendingBinaryOperation((a, b) -> {
				return a / b;
			});
			break;
		}
		Calculator.model.clear();
	}

}
