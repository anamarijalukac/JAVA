package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;

public class Display extends JLabel implements CalcValueListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Display() {
		setText("0");
		setBackground(Color.yellow);
		setOpaque(true);
		setHorizontalAlignment(SwingConstants.RIGHT);
		setFont(this.getFont().deriveFont(30f));
	}

	@Override
	public void valueChanged(CalcModel model) {
		setText(model.toString());

	}

}