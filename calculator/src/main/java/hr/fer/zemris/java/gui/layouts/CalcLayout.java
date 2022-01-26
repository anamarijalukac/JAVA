package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

public class CalcLayout implements LayoutManager2 {

	private int gap;
	private double cellH,cellW;

	Dimension gridSize;

	Map<Component, RCPosition> compTable;

	public CalcLayout() {
		this.gridSize = new Dimension(7, 5);
		this.gap = 0;
		this.compTable = new HashMap<Component, RCPosition>();
	}

	public CalcLayout(int gap) {

		this.gridSize = new Dimension(7, 5);
		this.gap = gap;
		this.compTable = new HashMap<Component, RCPosition>();

	}

	public void addLayoutComponent(Component comp, Object constraints) {
		RCPosition el = null;
		if (constraints instanceof String)
			el = RCPosition.parse((String) constraints);
		else if (constraints instanceof RCPosition) {
			el = (RCPosition) constraints;
		}
		if (el.getRow() < 1 || el.getRow() > 5) {
			throw new CalcLayoutException("cannot add to layout: row must be: r<1 || r>5");
		}
		if (el.getColumn() < 1 || el.getColumn() > 7) {
			throw new CalcLayoutException("cannot add to layout: column must be: s<1 || s>7");
		}

		if (el.getRow() == 1) {
			if (el.getColumn() > 1 && el.getColumn() < 6)
				throw new CalcLayoutException("cannot add to layout: first element!");
		}

		if (compTable.containsValue(el))
			throw new CalcLayoutException("cannot add to layout: already exists!");

		compTable.put(comp, el);

	}

	public void addLayoutComponent(String arg0, Component arg1) {
		// TODO Auto-generated method stub

	}

	public float getLayoutAlignmentX(Container arg0) {
		return 0.5f;
	}

	public float getLayoutAlignmentY(Container arg0) {
		return 0.5f;
	}

	public void invalidateLayout(Container arg0) {
		// TODO Auto-generated method stub

	}

	public void layoutContainer(Container parent) {

		Insets insets = parent.getInsets();
		int ncomponents = parent.getComponentCount();

		if (ncomponents == 0) {
			return;
		}

		Dimension size = parent.getSize();
		int totalW = size.width - (insets.left + insets.right);
		int totalH = size.height - (insets.top + insets.bottom);

		double totalCellW = totalW / gridSize.width;
		double totalCellH = totalH / gridSize.height;

		cellW = (totalW - ((gridSize.width + 1) * gap)) / gridSize.width;
		cellH = (totalH - ((gridSize.height + 1) * gap)) / gridSize.height;

		// mod
		int modW = totalW % gridSize.width;
		int modH = totalH % gridSize.height;
		Set<Integer> setW = new HashSet<>();
		Set<Integer> setH = new HashSet<>();

		while (setW.size() < modW) {
			setW.add((int) ((Math.random() * (7 - 1)) + 1));
		}
		while (setH.size() < modH) {
			setH.add((int) ((Math.random() * (5 - 1)) + 1));
		}

		for (int i = 0; i < ncomponents; i++) {
			Component c = parent.getComponent(i);
			RCPosition pos = compTable.get(c);
			if (pos.getColumn() == 1 && pos.getRow() == 1) {
				int x = (int) (insets.left + (totalCellW * (pos.getColumn() - 1)) + gap);
				int y = (int) (insets.top + (totalCellH * (pos.getRow() - 1)) + gap);
				int w = (int) ((cellW * 5) + gap * 3);
				int h = (int) ((cellH) - gap);

				c.setBounds(x, y, w, h);
			} else if (pos != null) {

				int x = (int) (insets.left + (totalCellW * (pos.getColumn() - 1)) + gap);
				int y = (int) (insets.top + (totalCellH * (pos.getRow() - 1)) + gap);
				int w = (int) Math.floor(cellW - gap);
				int h = (int) Math.floor(cellH - gap);
				if (setW.contains(pos.getColumn())) {
					w++;

				}
				if (setH.contains(pos.getRow())) {
					h++;

				}

				c.setBounds(x, y, w, h);
			}
		}

	}

	public Dimension minimumLayoutSize(Container parent) {
		return getLayoutSize(parent, false);
	}

	public Dimension preferredLayoutSize(Container parent) {
		return getLayoutSize(parent, true);
	}

	protected Dimension getLayoutSize(Container parent, boolean isPreferred) {
		Dimension largestSize = getLargestCellSize(parent, isPreferred);
		Insets insets = parent.getInsets();
		largestSize.width = (largestSize.width * gridSize.width) + (gap * (gridSize.width + 1)) + insets.left
				+ insets.right;
		largestSize.height = (largestSize.height * gridSize.height) + (gap * (gridSize.height + 1)) + insets.top
				+ insets.bottom;
		return largestSize;
	}

	protected Dimension getLargestCellSize(Container parent, boolean isPreferred) {
		int ncomponents = parent.getComponentCount();
		Dimension maxCellSize = new Dimension(0, 0);
		for (int i = 0; i < ncomponents; i++) {
			Component c = parent.getComponent(i);
			RCPosition pos = compTable.get(c);
			if (c != null && pos != null) {
				Dimension componentSize;
				if (isPreferred) {
					componentSize = c.getPreferredSize();
				} else {
					componentSize = c.getMinimumSize();
				}
				maxCellSize.width = (int) Math.max(maxCellSize.width, componentSize.width /  cellW);
				maxCellSize.height = (int) Math.max(maxCellSize.height, componentSize.height / cellH);
			}
		}
		return maxCellSize;
	}

	public void removeLayoutComponent(Component comp) {
		compTable.remove(comp);

	}

	@Override
	public Dimension maximumLayoutSize(Container arg0) {
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

}
