package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider{

	private static final LocalizationProvider instance = new LocalizationProvider();

	private LocalizationProvider() {
		this.language="en";
		Locale locale = Locale.forLanguageTag(language);
		bundle =ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", locale);
	}

	public static LocalizationProvider getInstance() {
		return instance;
	}
	
	private String language;
	private ResourceBundle bundle;
	
	

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}
	
	public void setLanguage(String lan) {
		this.language=lan;
		Locale locale = Locale.forLanguageTag(language);
		bundle =ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", locale);
		fire();
		
	}

	

}
