package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	private boolean connected;
	private ILocalizationProvider lp;
	
	public List<ILocalizationListener> elemListeners;
	

	public LocalizationProviderBridge(ILocalizationProvider lp) {
		elemListeners=new ArrayList<>();
		this.lp = lp;
		
	}
	
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		elemListeners.add(l);

	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		elemListeners.remove(l);
	}
	

	public void disconnect() {
		if (!connected)
			return;
		
		connected = false;
	}

	public void connect() {
		if (connected)
			return;
		connected = true;
		lp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				
				for (ILocalizationListener l : elemListeners) {
					
					l.localizationChanged();
				}

			}

		});
		

	}

	@Override
	public String getString(String key) {
		return lp.getString(key);

	}

}
