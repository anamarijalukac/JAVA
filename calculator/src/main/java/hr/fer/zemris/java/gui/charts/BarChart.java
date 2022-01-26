package hr.fer.zemris.java.gui.charts;

import java.util.List;

public class BarChart {

	private List<XYValue> list;
	private String xDes;
	private String yDes;
	private int minY;
	private int maxY;
	private int space;

	public BarChart(List<XYValue> list, String xDes, String yDes, int minY, int maxY, int space) {
		this.list = list;
		this.xDes = xDes;
		this.yDes = yDes;
		if (minY < 0 || maxY <= minY)
			throw new IllegalArgumentException();
		this.minY = minY;
		this.maxY = maxY;
		this.space = space;
		if ((maxY - minY) % space != 0) {
			space = Math.round(minY);
		}
		for (XYValue el : list)
			if (el.getY() < minY)
				throw new IllegalArgumentException();

	}

	public List<XYValue> getList() {
		return list;
	}

	public String getxDes() {
		return xDes;
	}

	public String getyDes() {
		return yDes;
	}

	public int getMinY() {
		return minY;
	}

	public int getMaxY() {
		return maxY;
	}

	public int getSpace() {
		return space;
	}

	

}
