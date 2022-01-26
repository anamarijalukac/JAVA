package hr.fer.zemris.java.gui.calc;

import java.awt.Color;

import java.awt.Container;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

public class Calculator extends JFrame {

	private static final long serialVersionUID = 1L;
	static ButtonInv inv;
	static CalcModelImpl model;

	private Stack<Double> stack;

	public Calculator() {
		stack = new Stack<>();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(600, 400);
		setTitle("Java calculator 1.0");
		initGUI();
	}

	private void initGUI() {

		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));
		model = new CalcModelImpl();
		Display dis = new Display();
		model.addCalcValueListener(dis);
		cp.add(dis, new RCPosition(1, 1));
		addNumberButtons(cp);
		addBinaryOperators(cp);
		addINVandCO(cp);
		addrest(cp);

	}

	private void addrest(Container cp) {

		JButton x1 = new JButton("1/x");
		x1.setBackground(Color.cyan);
		cp.add(x1, new RCPosition(2, 1));
		x1.addActionListener(e -> {
			double result = 1 / Calculator.model.getValue();
			Calculator.model.setValue(result);
		});

		JButton x2 = new JButton("+/-");
		x2.setBackground(Color.cyan);
		cp.add(x2, new RCPosition(5, 4));
		x2.addActionListener(e -> Calculator.model.swapSign());

		JButton x3 = new JButton(".");
		x3.setBackground(Color.cyan);
		cp.add(x3, new RCPosition(5, 5));
		x3.addActionListener(e -> Calculator.model.insertDecimalPoint());

		JButton x4 = new JButton("clr");
		x4.setBackground(Color.cyan);
		cp.add(x4, new RCPosition(1, 7));
		x4.addActionListener(e -> Calculator.model.clear());

		JButton x5 = new JButton("pop");
		x5.setBackground(Color.cyan);
		cp.add(x5, new RCPosition(4, 7));
		x5.addActionListener(e -> Calculator.model.setValue(stack.pop()));

		JButton x6 = new JButton("push");
		x6.setBackground(Color.cyan);
		cp.add(x6, new RCPosition(3, 7));
		x6.addActionListener(e -> stack.add(Calculator.model.getValue()));

		JButton x7 = new JButton("res");
		x7.setBackground(Color.cyan);
		cp.add(x7, new RCPosition(2, 7));
		x7.addActionListener(e -> Calculator.model.clearAll());

		JButton eq = new JButton("=");
		eq.setBackground(Color.cyan);
		cp.add(eq, new RCPosition(1, 6));
		eq.addActionListener(e -> {
			if (Calculator.model.isActiveOperandSet()) {

				double result = Calculator.model.getPendingBinaryOperation()
						.applyAsDouble(Calculator.model.getActiveOperand(), Calculator.model.getValue());
				Calculator.model.setValue(result);
				Calculator.model.clearActiveOperand();
				Calculator.model.setPendingBinaryOperation(null);
			}
		});

	}

	private void addLog_ln_xn(Container cp) {
		ButtonLogLnXn log = new ButtonLogLnXn("log");
		inv.list.add(log);
		cp.add(log, new RCPosition(3, 1));

		ButtonLogLnXn ln = new ButtonLogLnXn("ln");
		inv.list.add(ln);
		cp.add(ln, new RCPosition(4, 1));

		ButtonLogLnXn xn = new ButtonLogLnXn("x^n");
		inv.list.add(xn);
		cp.add(xn, new RCPosition(5, 1));

	}

	private void addINVandCO(Container cp) {
		inv = new ButtonInv();
		cp.add(inv, new RCPosition(5, 7));

		ButtonUnary sin = new ButtonUnary("sin");
		ButtonUnary cos = new ButtonUnary("cos");
		ButtonUnary tan = new ButtonUnary("tan");
		ButtonUnary ctg = new ButtonUnary("ctg");
		inv.list.add(sin);
		inv.list.add(cos);
		inv.list.add(tan);
		inv.list.add(ctg);

		cp.add(sin, new RCPosition(2, 2));
		cp.add(cos, new RCPosition(3, 2));
		cp.add(tan, new RCPosition(4, 2));
		cp.add(ctg, new RCPosition(5, 2));
		addLog_ln_xn(cp);

	}

	private void addBinaryOperators(Container cp) {
		cp.add(new ButtonBinary("+"), new RCPosition(5, 6));
		cp.add(new ButtonBinary("-"), new RCPosition(4, 6));
		cp.add(new ButtonBinary("*"), new RCPosition(3, 6));
		cp.add(new ButtonBinary("/"), new RCPosition(2, 6));

	}

	private void addNumberButtons(Container cp) {
		cp.add(new ButtonNumber("0"), new RCPosition(5, 3));
		cp.add(new ButtonNumber("1"), new RCPosition(4, 3));
		cp.add(new ButtonNumber("2"), new RCPosition(4, 4));
		cp.add(new ButtonNumber("3"), new RCPosition(4, 5));
		cp.add(new ButtonNumber("4"), new RCPosition(3, 3));
		cp.add(new ButtonNumber("5"), new RCPosition(3, 4));
		cp.add(new ButtonNumber("6"), new RCPosition(3, 5));
		cp.add(new ButtonNumber("7"), new RCPosition(2, 3));
		cp.add(new ButtonNumber("8"), new RCPosition(2, 4));
		cp.add(new ButtonNumber("9"), new RCPosition(2, 5));

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator().setVisible(true);
		});
	}

}
