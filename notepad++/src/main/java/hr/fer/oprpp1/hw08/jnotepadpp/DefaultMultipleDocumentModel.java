package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.Color;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	private List<SingleDocumentModel> col;
	private SingleDocumentModel current;
	private Path openedFilePath;

	private int len;
	private int ln;
	private int c;
	private int sel;

	CaretListener cl;

	private List<MultipleDocumentListener> listeners;

	public DefaultMultipleDocumentModel() {

		listeners = new ArrayList<>();
		col = new ArrayList<>();
		cl = new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent arg0) {

				JTextArea textArea = current.getTextComponent();

				len = textArea.getText().length();

				int caretpos = textArea.getCaretPosition();

				ln = 0;
				try {
					ln = textArea.getLineOfOffset(caretpos);
					c = caretpos - textArea.getLineStartOffset(ln);
				
				} catch (BadLocationException e) {

					e.printStackTrace();
				}

				sel = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());

				for (MultipleDocumentListener l : listeners) {
					l.updateStatus(len, ln + 1, c+1, sel);

				}

			}
		};

		this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		this.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				int index = getSelectedIndex();

				if (index > -1 && col.size() > 0) {
					SingleDocumentModel prev = current;
					current = getDocument(index);
					openedFilePath = current.getFilePath();

					for (MultipleDocumentListener l : listeners) {
						l.currentDocumentChanged(current);
						l.currentStatusChanged(prev, current);
					}
					cl.caretUpdate(null);
				}

			}

		});

	}

	public SingleDocumentModel getCurrent() {
		return current;
	}

	public Path getOppenedPath() {
		return openedFilePath;
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return col.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {

		ImageIcon icon = null;
		try {
			icon = createImageIcon("icons/whitediskette.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SingleDocumentModel doc = new DefaultSingleDocumentModel(null, "");

		JScrollPane s = new JScrollPane(doc.getTextComponent());
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1, 1));
		p.add(s);

		this.addTab("unnamed", icon, p);

		col.add(doc);
		current = doc;
		openedFilePath = doc.getFilePath();
		this.setSelectedIndex(col.size() - 1);

		for (MultipleDocumentListener l : listeners) {
			l.documentAdded(doc);
			l.currentDocumentChanged(doc);
		}
		return doc;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return current;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {

		if (path == null)
			throw new NullPointerException();

		byte[] okteti;
		try {
			okteti = Files.readAllBytes(path);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this,
					"Pogreska prilikom citanja datoteke " + path.getFileName().toString() + ".", "Pogreska",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		String tekst = new String(okteti, StandardCharsets.UTF_8);

		ImageIcon icon = null;
		try {
			icon = createImageIcon("icons/whitediskette.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SingleDocumentModel doc = new DefaultSingleDocumentModel(path, tekst);

		JScrollPane s = new JScrollPane(doc.getTextComponent());
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1, 1));
		p.add(s);
		current = doc;
		openedFilePath = doc.getFilePath();
		this.addTab(path.getFileName().toString(), icon, p);
		col.add(doc);

		this.setSelectedIndex(col.size() - 1);
		for (MultipleDocumentListener l : listeners) {
			l.documentAdded(doc);

		}
		return doc;

	}

	@Override
	public void saveDocument(SingleDocumentModel doc, Path newpath) {

		byte[] podatci = doc.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
		try {
			Files.write(newpath, podatci);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(this,
					"Pogreška prilikom zapisivanja datoteke " + newpath.toFile().getAbsolutePath()
							+ ".\nPažnja: nije jasno u kojem je stanju datoteka na disku!",
					"Pogreška", JOptionPane.ERROR_MESSAGE);
			return;
		}
		JOptionPane.showMessageDialog(this, "Datoteka je snimljena.", "Informacija", JOptionPane.INFORMATION_MESSAGE);

		ImageIcon icon = null;
		try {
			icon = createImageIcon("icons/colordiskette.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		doc.setFilePath(newpath);
		doc.setModified(false);
		this.setIconAt(getSelectedIndex(), icon);
		this.setTitleAt(getSelectedIndex(), newpath.getFileName().toString());
		this.openedFilePath = newpath;

	}

	@Override
	public void closeDocument() {
		col.remove(current);
		for (MultipleDocumentListener l : listeners) {
			l.documentRemoved(current);
			l.currentDocumentChanged(null);
		}
		this.removeTabAt(getSelectedIndex());

	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);

	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);

	}

	@Override
	public int getNumberOfDocuments() {
		return col.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return col.get(index);
	}

	public void setImageIcon() {
		if (current.isModified()) {
			ImageIcon icon = null;
			try {
				icon = createImageIcon("icons/whitediskette.png");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.setIconAt(getSelectedIndex(), icon);

		}
	}

	protected ImageIcon createImageIcon(String path) throws IOException {

		InputStream is = this.getClass().getResourceAsStream(path);
		if (is == null)
			throw new IOException();
		byte[] bytes = is.readAllBytes();
		is.close();
		return new ImageIcon(bytes);

	}

	void checkExit() {
		for (SingleDocumentModel m : this) {
			if (m.isModified()) {
				String path = "";
				Path p = m.getFilePath();
				if (p == null)
					path = "(unnamed)";
				else
					path = p.toString();

				Object[] options = { "DA", "NE" };
				int n = JOptionPane.showOptionDialog(null, "Do you want to save changes for " + path + "?",
						"JNotepad++", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.DEFAULT_OPTION, null, options,
						options[1]);

				if (n == 0) {
					JFileChooser jfc = new JFileChooser();
					jfc.setDialogTitle("Save document");
					if (jfc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
						JOptionPane.showMessageDialog(this, "Nista nije snimljeno.", "Upozorenje",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					Path newpath = jfc.getSelectedFile().toPath();
					saveDocument(m, newpath);

				}

			}

		}

	}

	public int getSel() {
		return sel;
	}

}
