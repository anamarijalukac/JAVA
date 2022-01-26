package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.List;

import javax.swing.JComponent;

public class BarChartComponent extends JComponent {

	private BarChart bc;

	private int w;
	private int h;

	public BarChartComponent(BarChart bc) {

		this.bc = bc;

	}

	@Override
	protected void paintComponent(Graphics s) {
		w = this.getBounds().width;
		h = this.getBounds().height;

		s.setColor(Color.yellow);
		s.fillRect(0, 0, w, h);
		s.setColor(Color.black);
		s.drawString(bc.getxDes(), (w - 100) / 2, h - 20);

		AffineTransform orig = ((Graphics2D) s).getTransform();
		((Graphics2D) s).rotate(-Math.PI / 2);
		s.drawString(bc.getyDes(), -h / 2, 25);
		((Graphics2D) s).setTransform(orig);

		((Graphics2D) s).setStroke(new BasicStroke(2));
		s.drawLine(50, h - 50, 50, 50);
		s.drawLine(50, h - 50, w - 50, h - 50);
		
		
		s.fillPolygon(new int [] {45,55,50}, new int [] {50,50,43}, 3);
		
		s.fillPolygon(new int [] {w-50,w-50,w-43}, new int [] {h-55,h-45,h-50}, 3);
		

		List<XYValue> list = bc.getList();
		int size = bc.getList().size();
		int xsize = (w - 100) / size - bc.getSpace();
		int ysize = (h - 100) / bc.getMaxY();

		for (int i = 1; i <= size; i++) {
			s.drawLine(50 + (xsize * i) + ((i - 1) * bc.getSpace()), h - 50,
					50 + (xsize * i) + ((i - 1) * bc.getSpace()), h - 45);
			s.drawString(String.valueOf(list.get(i - 1).getX()),
					50 + (xsize * i) + ((i - 1) * bc.getSpace()) - (xsize / 2), h - 35);
		}
		for (int i = 0; i <= bc.getMaxY(); i++) {
			s.drawLine(50, (h - 50) - i * ysize, 45, (h - 50) - i * ysize);
			s.drawString(String.valueOf(i), 30, (h - 50 + ysize / 2) - i * (ysize));
		}
		s.setColor(Color.magenta);
		int x0 = 50;
		int y0 = h - 50;
		for (int i = 0; i < size; i++) {

			s.fillRect(x0 + (xsize * i) + (i * bc.getSpace()), y0 - list.get(i).getY() * ysize, xsize, ysize*list.get(i).getY());

		}
	}

}
