package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;

public class Clock extends JPanel {

	private JLabel clock;

	public Clock() {
		setLayout(new BorderLayout());
		clock = new JLabel();
		clock.setHorizontalAlignment(JLabel.CENTER);
		tickTock();
		add(clock);

		Timer timer = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tickTock();
			}
		});
		timer.setRepeats(true);
		timer.setCoalesce(true);
		timer.setInitialDelay(0);
		timer.start();
	}

	public void tickTock() {
		clock.setText(DateFormat.getDateTimeInstance().format(new Date()));
	}
}
