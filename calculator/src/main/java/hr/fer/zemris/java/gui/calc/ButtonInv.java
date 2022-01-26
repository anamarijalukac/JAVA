package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;

public class ButtonInv extends JCheckBox implements ActionListener {

	List<JButton> list;

	public ButtonInv() {
		list = new ArrayList<>();
		setBackground(Color.cyan);
		setText("Inv");
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (this.isSelected()) {
			for (JButton b : list)
				switch (b.getName()) {
				case "sin":
				case "cos":
				case "tan":
				case "ctg":
					b.setText("arc" + b.getName());
					break;
				case "log":
					b.setText("10^");
					break;
				case "ln":
					b.setText("e^");
					break;
				case "x^n":
					b.setText("x^(1/n)");
					break;
				}
		}
		else {
			for (JButton b : list)
				switch (b.getName()) {
				case "sin":
				case "cos":
				case "tan":
				case "ctg":
					b.setText(b.getName());
					break;
				case "log":
					b.setText("log");
					break;
				case "ln":
					b.setText("ln");
					break;
				case "x^n":
					b.setText("x^n");
					break;
				}
			
		}

	}

	

}
