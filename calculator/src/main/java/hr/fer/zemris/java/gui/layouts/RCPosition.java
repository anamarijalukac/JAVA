package hr.fer.zemris.java.gui.layouts;

public class RCPosition {

	private int row;
	private int column;

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public RCPosition(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	public RCPosition(RCPosition constraints) {
		this.row=constraints.row;
		this.column=constraints.column;
	}

	public static RCPosition parse(String text) {
		int row = 0;
		int column=0;
		char [] a=text.toCharArray();
		for(char c:a)
			if(Character.isDigit(c)) {
				if(row==0)
					row=Integer.parseInt(String.valueOf(c));
				else if(column==0)
					column=Integer.parseInt(String.valueOf(c));
			}
				
		return new RCPosition(row,column);
	}

}
