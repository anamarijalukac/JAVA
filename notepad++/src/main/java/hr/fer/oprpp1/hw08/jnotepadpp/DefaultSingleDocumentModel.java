package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public class DefaultSingleDocumentModel implements SingleDocumentModel {

	private JTextArea textArea;
	private Path path;
	private List<SingleDocumentListener> listeners;
	private boolean modified;

	public DefaultSingleDocumentModel(Path path, String context) {

		this.path = path;
		listeners = new ArrayList<>();
		modified = true;

		textArea = new JTextArea(context);
		textArea.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				if (!isModified())
					setModified(true);

			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if (!isModified())
					setModified(true);

			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				if (!isModified())
					setModified(true);

			}
		});

		

	}

	@Override
	public JTextArea getTextComponent() {
		return textArea;
	}

	@Override
	public Path getFilePath() {

		return path;
	}

	@Override
	public void setFilePath(Path path) {

		if (path == null)
			throw new NullPointerException();
		this.path = path;
		for (SingleDocumentListener l : listeners) {
			l.documentFilePathUpdated(this);

		}

	}

	@Override
	public boolean isModified() {

		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;

		for (SingleDocumentListener l : listeners)
			l.documentModifyStatusUpdated(this);
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);

	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);

	}

}
