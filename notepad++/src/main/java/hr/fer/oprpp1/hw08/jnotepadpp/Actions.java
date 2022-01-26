package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;

public class Actions {

	private DefaultMultipleDocumentModel model;
	private ILocalizationProvider flp;
	private String mem;

	public Actions(DefaultMultipleDocumentModel model, ILocalizationProvider flp) {
		this.model = model;
		this.flp = flp;
	}

	public Action newAction() {
		Action newDocumentAction = new LocalizableAction("new", flp) {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				model.createNewDocument();

			}
		};

		return newDocumentAction;
	}

	public Action open() {
		Action openDocumentAction = new LocalizableAction("open", flp) {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Open file");
				if (fc.showOpenDialog(model) != JFileChooser.APPROVE_OPTION) {
					return;
				}
				File fileName = fc.getSelectedFile();
				Path filePath = fileName.toPath();
				if (!Files.isReadable(filePath)) {
					JOptionPane.showMessageDialog(model, "Datoteka " + fileName.getAbsolutePath() + " ne postoji!",
							"Pogreška", JOptionPane.ERROR_MESSAGE);
					return;
				}

				model.loadDocument(filePath);

			}
		};

		return openDocumentAction;
	}

	public Action save() {
		Action saveDocumentAction = new LocalizableAction("save", flp) {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (model.getCurrent() == null)
					return;
				if (model.getOppenedPath() == null) {

					JFileChooser jfc = new JFileChooser();
					jfc.setDialogTitle("Save document");
					if (jfc.showSaveDialog(model) != JFileChooser.APPROVE_OPTION) {
						JOptionPane.showMessageDialog(model, "Nista nije snimljeno.", "Upozorenje",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					Path newpath = jfc.getSelectedFile().toPath();
					model.saveDocument(model.getCurrent(), newpath);
				} else {

					model.saveDocument(model.getCurrent(), model.getOppenedPath());
				}

			}
		};

		return saveDocumentAction;
	}

	public Action saveAs() {
		Action saveAsDocumentAction = new LocalizableAction("saveas", flp) {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (model.getCurrent() == null)
					return;
				Path newpath = null;
				if (model.getOppenedPath() == null) {
					JFileChooser jfc = new JFileChooser();
					jfc.setDialogTitle("Save document");
					if (jfc.showSaveDialog(model) != JFileChooser.APPROVE_OPTION) {
						JOptionPane.showMessageDialog(model, "Nista nije snimljeno.", "Upozorenje",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					newpath = jfc.getSelectedFile().toPath();

				}
				model.saveDocument(model.getCurrent(), newpath);

			}
		};

		return saveAsDocumentAction;
	}

	public Action close() {
		Action closeDocumentAction = new LocalizableAction("close", flp) {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (model.getCurrent() == null)
					return;

				String path = "";
				Path p = model.getOppenedPath();
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
					if (jfc.showSaveDialog(model) != JFileChooser.APPROVE_OPTION) {
						JOptionPane.showMessageDialog(model, "Nista nije snimljeno.", "Upozorenje",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					Path newpath = jfc.getSelectedFile().toPath();

					model.saveDocument(model.getCurrent(), newpath);
				}
				model.closeDocument();

			}
		};

		return closeDocumentAction;
	}

	public Action cut() {
		Action cutDocumentAction = new LocalizableAction("cut", flp) {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (model.getCurrent() == null)
					return;
				JTextArea editor = model.getCurrent().getTextComponent();
				Document doc = editor.getDocument();
				int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
				if (len == 0)
					return;
				int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
				try {
					mem = doc.getText(offset, len);
					doc.remove(offset, len);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}

			}
		};

		return cutDocumentAction;
	}

	public Action copy() {
		Action copyDocumentAction = new LocalizableAction("copy", flp) {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (model.getCurrent() == null)
					return;
				JTextArea editor = model.getCurrent().getTextComponent();
				Document doc = editor.getDocument();
				int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
				if (len == 0)
					return;
				int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
				try {
					mem = doc.getText(offset, len);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}

			}
		};

		return copyDocumentAction;
	}

	public Action paste() {
		Action pasteDocumentAction = new LocalizableAction("paste", flp) {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (model.getCurrent() == null)
					return;
				JTextArea editor = model.getCurrent().getTextComponent();
				Document doc = editor.getDocument();

				try {
					doc.insertString(editor.getCaret().getDot(), mem, null);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				mem = "";

			}
		};

		return pasteDocumentAction;
	}

	public Action exit() {
		Action exitDocumentAction = new LocalizableAction("exit", flp) {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				model.checkExit();
				System.exit(0);

			}
		};

		return exitDocumentAction;
	}

	public Action uppercase() {
		Action uppercaseAction = new LocalizableAction("uppercase", flp) {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				toggle(1);
			}
		};

		return uppercaseAction;
	}

	public Action lowercase() {
		Action lowercaseAction = new LocalizableAction("lowercase", flp) {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				toggle(2);

			}
		};

		return lowercaseAction;
	}

	public Action inverse() {
		Action inverseAction = new LocalizableAction("inverse", flp) {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				toggle(3);
			}
		};

		return inverseAction;
	}

	public Action ascending() {
		Action ascendingAction = new LocalizableAction("asc", flp) {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (model.getCurrent() == null)
					return;
				sort(true);

			}
		};

		return ascendingAction;
	}

	public Action descending() {
		Action descendingAction = new LocalizableAction("des", flp) {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (model.getCurrent() == null)
					return;
				sort(false);

			}
		};

		return descendingAction;
	}

	public Action uni() {
		Action uniAction = new LocalizableAction("unique", flp) {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (model.getCurrent() == null)
					return;
				Locale hrLocale = new Locale("hr");
				Collator.getInstance(hrLocale);

				JTextArea textarea = model.getCurrent().getTextComponent();
				Document doc = textarea.getDocument();

				int begin = textarea.getCaret().getMark();
				int finish = textarea.getCaret().getDot();
				try {
					int line1 = textarea.getLineOfOffset(begin);
					int line2 = textarea.getLineOfOffset(finish);
					int start = textarea.getLineStartOffset(line1);
					int end = textarea.getLineEndOffset(line2);

					int len = Math.abs(end - start);
					String text = doc.getText(start, len);
					doc.remove(start, len);
					Set<String> set = new LinkedHashSet<>();

					for (String s : text.split("\n"))
						set.add(s);
					text = "";
					for (String s : set)
						text = text + s + "\n";

					doc.insertString(start, text, null);

				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		};

		return uniAction;
	}

	public Action hr() {
		Action hrAction = new LocalizableAction("hr", flp) {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				LocalizationProvider.getInstance().setLanguage("hr");
			}
		};

		return hrAction;
	}

	public Action en() {
		Action enAction = new LocalizableAction("en", flp) {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				LocalizationProvider.getInstance().setLanguage("en");
			}
		};

		return enAction;
	}

	public Action de() {
		Action deAction = new LocalizableAction("de", flp) {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				LocalizationProvider.getInstance().setLanguage("de");
			}
		};

		return deAction;
	}

	

	private void toggle(int b) {

		if (model.getSel() > 0) {
			JTextArea editor = model.getCurrent().getTextComponent();
			Document doc = editor.getDocument();

			int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
			int offset = 0;
			if (len != 0) {
				offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
			} else {
				len = doc.getLength();
			}
			try {
				String text = doc.getText(offset, len);
				text = changeCase(text, b);
				doc.remove(offset, len);
				doc.insertString(offset, text, null);
			} catch (BadLocationException ex) {
				ex.printStackTrace();
			}
		}
	}

	private String changeCase(String text, int b) {
		char[] znakovi = text.toCharArray();
		for (int i = 0; i < znakovi.length; i++) {
			char c = znakovi[i];
			if (b == 1)
				znakovi[i] = Character.toUpperCase(c);
			else if (b == 2)
				znakovi[i] = Character.toLowerCase(c);
			else if (b == 3) {
				if (Character.isLowerCase(c)) {
					znakovi[i] = Character.toUpperCase(c);
				} else if (Character.isUpperCase(c)) {
					znakovi[i] = Character.toLowerCase(c);
				}
			}

		}
		return new String(znakovi);
	}

	private void sort(boolean b) {

		Locale hrLocale = new Locale("hr");
		Collator hrCollator = Collator.getInstance(hrLocale);

		JTextArea textarea = model.getCurrent().getTextComponent();
		Document doc = textarea.getDocument();

		int begin = textarea.getCaret().getMark();
		int finish = textarea.getCaret().getDot();
		try {
			int line1 = textarea.getLineOfOffset(begin);
			int line2 = textarea.getLineOfOffset(finish);
			int start = textarea.getLineStartOffset(line1);
			int end = textarea.getLineEndOffset(line2);

			int len = Math.abs(end - start);
			String text = doc.getText(start, len);
			doc.remove(start, len);
			List<String> list = new ArrayList<>();
			for (String s : text.split("\n"))
				list.add(s);
			text = "";
			if (b)
				list.sort(hrCollator);
			else
				list.sort(hrCollator.reversed());
			for (String s : list)
				text = text + s + "\n";

			doc.insertString(start, text, null);

		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
