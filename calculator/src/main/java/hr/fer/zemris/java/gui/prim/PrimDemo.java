package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class PrimDemo extends JFrame {

	public PrimDemo() {
		setLocation(20, 50);
		setSize(300, 200);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		initGUI();
	}

	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		DemoListModel<Integer> model = new DemoListModel<>();

		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);

		JPanel bottomPanel = new JPanel(new GridLayout(1, 0));

		JButton next = new JButton("Sljedeæi");
		bottomPanel.add(next);

		next.addActionListener(e -> {

			int num = primBroj(prev);
			model.add(num);
		});

		JPanel central = new JPanel(new GridLayout(1, 0));
		central.add(new JScrollPane(list1));
		central.add(new JScrollPane(list2));

		cp.add(central, BorderLayout.CENTER);
		cp.add(bottomPanel, BorderLayout.PAGE_END);
	}

	private int prev = 0;

	private int primBroj(int p) {
		if (p == 0) {
			prev = 1;
			return 1;
		}

		int i = p + 1;
		while (true) {

			int counter = 0;
			for (int num = i; num >= 1; num--) {
				if (i % num == 0) {
					counter = counter + 1;
				}
			}
			if (counter == 2) {
				break;
			} else
				i++;
		}

		prev = i;
		return i;
	}

	static class DemoListModel<T> implements ListModel<T> {
		private List<T> elementi = new ArrayList<>();
		private List<ListDataListener> promatraci = new ArrayList<>();

		@Override
		public void addListDataListener(ListDataListener l) {
			promatraci.add(l);
		}

		@Override
		public void removeListDataListener(ListDataListener l) {
			promatraci.remove(l);
		}

		@Override
		public int getSize() {
			return elementi.size();
		}

		@Override
		public T getElementAt(int index) {
			return elementi.get(index);
		}

		public void add(T element) {
			int pos = elementi.size();
			elementi.add(element);

			ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
			for (ListDataListener l : promatraci) {
				l.intervalAdded(event);
			}
		}

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new PrimDemo();
			frame.pack();
			frame.setVisible(true);
		});
	}

}
