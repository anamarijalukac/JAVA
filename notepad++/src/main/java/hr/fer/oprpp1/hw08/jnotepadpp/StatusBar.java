package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class StatusBar extends JPanel {

	private JLabel lenghtLabel;
	private JLabel lnLabel;
	private JLabel colLabel;
	private JLabel selLabel;

	public StatusBar() {

		initGUI();

	}

	public void setLength(int length) {

		lenghtLabel.setText("length:" + length);

	}

	public void setLn(int ln) {

		lnLabel.setText("Ln:" + ln);
	}

	public void setCol(int col) {

		colLabel.setText("Col:" + col);
	}

	public void setSel(int sel) {

		selLabel.setText("Sel:" + sel);
	}

	private void initGUI() {
		this.setBorder(new BevelBorder(BevelBorder.LOWERED));

		this.setPreferredSize(new Dimension(getWidth(), 20));
		this.setLayout(new GridLayout(0, 3));

		JPanel leftPanel = new JPanel(new BorderLayout());
		lenghtLabel = new JLabel("lenght:");

		leftPanel.add(lenghtLabel);

		add(leftPanel);

		JPanel rightPanel = new JPanel(new GridLayout(0, 3));

		lnLabel = new JLabel("Ln:");
		colLabel = new JLabel("Col:");
		selLabel = new JLabel("Sel:");
		rightPanel.add(lnLabel);
		rightPanel.add(colLabel);
		rightPanel.add(selLabel);
		add(rightPanel);

		JPanel clockPanel = new JPanel(new BorderLayout());
		clockPanel.add(new Clock());
		add(clockPanel);
	}

}
