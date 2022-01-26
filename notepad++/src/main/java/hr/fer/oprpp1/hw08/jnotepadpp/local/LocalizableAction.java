package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.AbstractAction;
import javax.swing.Action;

public abstract class  LocalizableAction extends AbstractAction implements ILocalizationListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String key;
	private ILocalizationProvider lp;

	public LocalizableAction(String key, ILocalizationProvider lp) {
		this.lp = lp;
		this.key = key;
		this.putValue(Action.NAME, lp.getString(key));
		lp.addLocalizationListener(this);
		
	}

	

	@Override
	public void localizationChanged() {
		this.putValue(Action.NAME, lp.getString(key));

	}




}
