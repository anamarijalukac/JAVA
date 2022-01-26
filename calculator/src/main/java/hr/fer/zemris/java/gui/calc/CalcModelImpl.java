package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

public class CalcModelImpl implements CalcModel {

	private boolean editable;
	private boolean positive;
	private String input;
	private double value;
	private String frozen;
	
	
	private double activeOperand;
	private DoubleBinaryOperator pendingOperation;

	private List<CalcValueListener> promatraci = new ArrayList<>();

	public CalcModelImpl() {
	
		editable = true;
		positive = true;
		input = "";
		value = 0;
		frozen = null;
		activeOperand = 0;
		pendingOperation = null;

	}

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		promatraci.add(l);

	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		promatraci.remove(l);

	}

	@Override
	public String toString() {
		if (hasFrozenValue()) {
			return frozen;

		}

		if (input.equals("")) {
			if (positive)
				return "0";
			else
				return "-0";
		}
		if (positive)
			return input;
		else
			return "-" + input;

	}

	@Override
	public double getValue() {
		if (positive)
			return value;
		else
			return value * -1;

	}

	@Override
	public void setValue(double value) {
		frozen = null;
		this.value = value;
		input = String.valueOf(value);
		editable = false;
		for (CalcValueListener l : promatraci) {
			l.valueChanged(this);
		}

	}



	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		editable=true;
		input = "";
		for (CalcValueListener l : promatraci) {
			l.valueChanged(this);
		}

	}

	@Override
	public void clearAll() {
		
		editable = true;
		positive = true;
		input = "";
		value = 0;
		frozen = null;
		activeOperand = 0;
		pendingOperation = null;
		for (CalcValueListener l : promatraci) {
			l.valueChanged(this);
		}

	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if (!isEditable())
			throw new CalculatorInputException("not editable");
		positive = !positive;
		frozen = null;

		for (CalcValueListener l : promatraci) {
			l.valueChanged(this);
		}

	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (!isEditable())
			throw new CalculatorInputException("not editable");
		if (input.equals(""))
			throw new CalculatorInputException("no digits");
		if (input.contains("."))
			throw new CalculatorInputException("already has decimal point");
		frozen = null;
		input = input.concat(".");
		for (CalcValueListener l : promatraci) {
			l.valueChanged(this);
		}
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if (!isEditable())
			throw new CalculatorInputException("not editable");
		
		

		if(digit==0 && input.equals("0"))
			return;
		if(input.equals("0") && digit!=0)
			input="";

		frozen = null;

		String pom = input + String.valueOf(digit);

		try {
			value = Double.parseDouble(pom);
			if (value == Double.POSITIVE_INFINITY)
				throw new CalculatorInputException("number too big");
			if (value == Double.NEGATIVE_INFINITY)
				throw new CalculatorInputException("number too small");
		} catch (NumberFormatException e) {

			throw new CalculatorInputException("can't parse to double");
		}
		input = input.concat(String.valueOf(digit));

		for (CalcValueListener l : promatraci) {
			l.valueChanged(this);
		}

	}

	@Override
	public boolean isActiveOperandSet() {
		return (activeOperand != 0);
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (activeOperand == 0)
			throw new IllegalStateException("active operand is 0");
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;

	}

	@Override
	public void clearActiveOperand() {
		activeOperand = 0;

	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperation = op;

	}

	public void freezeValue(String value) {
		frozen = value;
		for (CalcValueListener l : promatraci) {
			l.valueChanged(this);
		}
	}

	public boolean hasFrozenValue() {
		return (frozen != null);
	}
	
	
}
