package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class FormLocalizationProvider extends LocalizationProviderBridge implements WindowListener{

	
	public FormLocalizationProvider(ILocalizationProvider lp,JFrame frame) {
		super(lp);
		frame.addWindowListener((WindowListener) this);
		
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		connect();
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		disconnect();
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		disconnect();
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		disconnect();
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		connect();
		
	}

}
