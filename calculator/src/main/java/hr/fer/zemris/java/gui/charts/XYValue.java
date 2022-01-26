package hr.fer.zemris.java.gui.charts;

public class XYValue {
	
	private int x;
	private int y;
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public XYValue(String x,String y) {
		this.x=Integer.parseInt(x);
		this.y=Integer.parseInt(y);
	}

}
