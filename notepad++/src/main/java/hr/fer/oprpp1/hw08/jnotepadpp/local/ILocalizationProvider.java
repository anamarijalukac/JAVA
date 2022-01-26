package hr.fer.oprpp1.hw08.jnotepadpp.local;

public interface ILocalizationProvider {
	String getString(String key);
	
	void addLocalizationListener( ILocalizationListener l);
	void removeLocalizationListener (ILocalizationListener l);
	
	

}
