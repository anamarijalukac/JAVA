package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class BarChartDemo extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private static String path;
	private static BarChart bc;

	public BarChartDemo() {
		setLocation(20, 50);
		setSize(600,600);
		setTitle("Chart");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		initGUI();
	}

	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(new JLabel(path), BorderLayout.PAGE_START);
		cp.add(new BarChartComponent(bc), BorderLayout.CENTER);
		
	}

	public static void main(String[] args) {
		path = args[0];
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<XYValue> list = new ArrayList<>();
		String xDes = null;
		String yDes = null;
		int minY = 0;
		int maxY = 0;
		int space = 0;

		try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
			String line;
			for (int i = 0; i < 6; i++) {
				line = bufferedReader.readLine();
				switch (i) {
				case 0:
					xDes = line;
					break;
				case 1:
					yDes = line;
					break;
				case 2:
					String[] s = line.split(" ");
					for (String el : s)
						list.add(new XYValue(el.split(",")[0], el.split(",")[1]));
					break;
				case 3:
					minY = Integer.parseInt(line);
					break;
				case 4:
					maxY = Integer.parseInt(line);
					break;
				case 5:
					space = Integer.parseInt(line);
					break;
				}
			}
			bc = new BarChart(list, xDes, yDes, minY, maxY, space);
			
			fileReader.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(() -> {
			JFrame frame = new BarChartDemo();
			frame.setVisible(true);
		});

	}
}
