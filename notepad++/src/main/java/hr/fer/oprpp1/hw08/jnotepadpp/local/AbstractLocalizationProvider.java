package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	private List<ILocalizationListener> list;

	public AbstractLocalizationProvider() {
		this.list = new ArrayList<>();

	}

	
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		list.add(l);

	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		list.remove(l);
	}

	public void fire() {
		for (ILocalizationListener l : list) {
			
			l.localizationChanged();
		}
	}

}
