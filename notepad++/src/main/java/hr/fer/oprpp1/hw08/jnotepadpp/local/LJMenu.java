package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JToolBar;

public class LJMenu extends JMenu implements ILocalizationListener {
	
	private String key;
	private ILocalizationProvider lp;
	
	public LJMenu (String key, ILocalizationProvider lp) {
		this.lp=lp;
		this.key=key;
		lp.addLocalizationListener(this);
		
		this.setText(lp.getString(key));
		
	}
	
	

	@Override
	public void localizationChanged() {
		this.setText(lp.getString(key));
		
	}

}
