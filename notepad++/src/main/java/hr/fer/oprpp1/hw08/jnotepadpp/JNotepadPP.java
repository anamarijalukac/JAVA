package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LJMenu;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;

public class JNotepadPP extends JFrame implements SingleDocumentListener, MultipleDocumentListener {

	private static final long serialVersionUID = 1L;

	private DefaultMultipleDocumentModel model;
	private Actions actions;
	
	
	private FormLocalizationProvider flp;

	private StatusBar status;

	public JNotepadPP() {
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(0, 0);
		setSize(600, 600);
		initGUI();

	}
	
	

	private void initGUI() {

		model = new DefaultMultipleDocumentModel();
		actions = new Actions(model,flp);
		
		model.addMultipleDocumentListener(this);

		add(model, BorderLayout.CENTER);
		this.setTitle("JNotepad++");
		createMenus();
		createToolbars();
		status = new StatusBar();
		add(status, BorderLayout.SOUTH);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

				model.checkExit();
				dispose();
				System.exit(0);
			}
		});

	}

	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		
		
		
		
		

		JMenu fileMenu = new LJMenu("file",flp);		
		menuBar.add(fileMenu);
		
		fileMenu.add(new JMenuItem(actions.newAction()));
		fileMenu.add(new JMenuItem(actions.open()));
		fileMenu.add(new JMenuItem(actions.save()));
		fileMenu.add(new JMenuItem(actions.saveAs()));
		fileMenu.add(new JMenuItem(actions.close()));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(actions.exit()));

		JMenu editMenu = new LJMenu("edit",flp);
		menuBar.add(editMenu);
		editMenu.add(new JMenuItem(actions.cut()));
		editMenu.add(new JMenuItem(actions.copy()));
		editMenu.add(new JMenuItem(actions.paste()));
		

		JMenu toolsMenu = new LJMenu("tools",flp);
		menuBar.add(toolsMenu);
		JMenu change = new LJMenu("changecase",flp);
		toolsMenu.add(change);
		change.add(new JMenuItem(actions.uppercase()));
		change.add(new JMenuItem(actions.lowercase()));
		change.add(new JMenuItem(actions.inverse()));		
		JMenu sort = new LJMenu("sort",flp);
		toolsMenu.add(sort);
		sort.add(new JMenuItem(actions.ascending()));
		sort.add(new JMenuItem(actions.descending()));		
		toolsMenu.add(new JMenuItem(actions.uni()));
		this.setJMenuBar(menuBar);
		
		JMenu lanMenu = new LJMenu("languages",flp);
		menuBar.add(lanMenu);
		lanMenu.add(new JMenuItem(actions.hr()));
		lanMenu.add(new JMenuItem(actions.en()));
		lanMenu.add(new JMenuItem(actions.de()));
		
		
		
	}

	private void createToolbars() {
		JToolBar toolBar = new JToolBar("Alati");
		toolBar.setFloatable(true);

		toolBar.add(new JButton(actions.newAction()));
		toolBar.add(new JButton(actions.open()));
		toolBar.add(new JButton(actions.save()));
		toolBar.add(new JButton(actions.saveAs()));
		toolBar.add(new JButton(actions.close()));

		toolBar.addSeparator();
		toolBar.add(new JButton(actions.cut()));

		toolBar.add(new JButton(actions.copy()));
		toolBar.add(new JButton(actions.paste()));

		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new JNotepadPP().setVisible(true);
			}
		});
	}

	@Override
	public void currentDocumentChanged( SingleDocumentModel current) {
		if (current == null)
			this.setTitle("");
		else {
			String path = "";
			Path p = current.getFilePath();
			if (p == null)
				path = "(unnamed)";
			else
				path = p.toString();
			this.setTitle(path + " - JNotepad++");
		}
		

	}

	@Override
	public void documentAdded(SingleDocumentModel doc) {
		doc.addSingleDocumentListener(this);
		doc.getTextComponent().addCaretListener(model.cl);

	}

	@Override
	public void documentRemoved(SingleDocumentModel doc) {
		doc.removeSingleDocumentListener(this);
		doc.getTextComponent().removeCaretListener(model.cl);
	}

//single

	@Override
	public void documentModifyStatusUpdated(SingleDocumentModel doc) {
		if (doc.isModified())
			model.setImageIcon();

	}

	@Override
	public void documentFilePathUpdated(SingleDocumentModel doc) {
		this.setTitle(doc.getFilePath().toString());
	}

	@Override
	public void updateStatus(int len, int ln, int col, int sel) {
		status.setLength(len);
		status.setLn(ln);
		status.setCol(col);
		status.setSel(sel);

	}

	@Override
	public void currentStatusChanged(SingleDocumentModel prev, SingleDocumentModel current) {
		if(prev!=null)
			prev.getTextComponent().removeCaretListener(model.cl);
		current.getTextComponent().addCaretListener(model.cl);
		
	}

}
