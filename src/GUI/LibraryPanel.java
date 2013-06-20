package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import Logic.FileTraverse;

public class LibraryPanel extends JPanel implements FocusListener, DropTargetListener	{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6470313007794156279L;
	JTextField searchField = new JTextField();
	static String currentLibrary = new String("");
	public static TableModel model;
	JRadioButton rb1 = new JRadioButton();
	JRadioButton rb2 = new JRadioButton();
	ButtonGroup group = new ButtonGroup();
	int jOption = 0;
	public static JTable table;
	DropTarget dt;
	private TableRowSorter<TableModel> sorter;
	JTabbedPane tp;

	public LibraryPanel()	{
		tp = new JTabbedPane();
		tp.setPreferredSize(new Dimension(1024,300));
		setBackground(Color.WHITE);
		setSize(1011, 600);
		setVisible(true);
		setLayout(new BorderLayout());
		model = new TableModel();
		table = new JTable(model)	{
			/**
			 * 
			 */
			private static final long serialVersionUID = 2506055194944039222L;
			public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int vColIndex) {
				Component c = super.prepareRenderer(renderer, rowIndex, vColIndex);
				if (rowIndex % 2 != 0) {
					c.setBackground(new Color(243,247,251,255));
				} else {
					c.setBackground(Color.WHITE);
				}
				if (isCellSelected(rowIndex, vColIndex))	{
					c.setBackground(new Color(241,210,56,130));	
				}
			//	if (vColIndex==6)	{
					//((JLabel) c).setHorizontalAlignment(JTextField.RIGHT);
			//	}
				return c;
			}
		};
		setColumnWidths();
		JScrollPane sp = new JScrollPane(table);
		dt = new DropTarget(sp.getViewport(), this);
		PlaylistPanel pp = new PlaylistPanel();
		VideoPanel vp = new VideoPanel();
		tp.addTab("Music", sp);
		tp.addTab("Playlists", pp);
		tp.addTab("Video", vp);
		searchField.setHorizontalAlignment(JTextField.CENTER);
		searchField.setText("[search]");
		searchField.addFocusListener(this);
		add(searchField, BorderLayout.NORTH);
		add(tp, BorderLayout.CENTER);
		sorter = new TableRowSorter<TableModel>(model);
		table.setRowSorter(sorter);
		searchField.getDocument().addDocumentListener(
				new DocumentListener() {
					public void changedUpdate(DocumentEvent e) {
						newFilter();
					}
					public void insertUpdate(DocumentEvent e) {
						newFilter();
					}
					public void removeUpdate(DocumentEvent e) {
						newFilter();
					}
				});
	}
	private void setColumnWidths()	{
		//table.setMaximumSize(new Dimension(100, 100));
		//System.out.println(table.getColumn("Artist").getWidth());
		table.getColumn("*").setPreferredWidth(5);
		table.getColumn("Title").setPreferredWidth(250);
		table.getColumn("Time").setPreferredWidth(35);
		table.getColumn("Artist").setPreferredWidth(200);
		table.getColumn("Album").setPreferredWidth(200);
		table.getColumn("Genre").setPreferredWidth(100);
		table.getColumn("Size").setPreferredWidth(50);
		/*
		 * Right-align cells in size column.
		 */
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		table.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
		
	} 

	private void newFilter() {
		RowFilter<TableModel, Object> rf = null;
		try {
			rf = RowFilter.regexFilter("(?i)" + searchField.getText());
		} catch (java.util.regex.PatternSyntaxException e) {
			return;	
		}
		sorter.setRowFilter(rf);
	}
	public static String[] listLib()	{
		String filenames[] = new String[FileTraverse.list.size()];
		for (int i = 0; i < FileTraverse.list.size(); i++){
			filenames[i] = FileTraverse.list.get(i).toString();
		}
		return filenames;
	}
	public void deleteMenu()	{
		JPanel menu = new JPanel(new BorderLayout());
		JPanel east = new JPanel(new GridLayout(2,1));
		JPanel west = new JPanel(new GridLayout(2,1));
		JLabel a = new JLabel("Delete from Library");
		JLabel b = new JLabel("Delete from Drive");
		group.add(rb1);
		group.add(rb2);
		west.add(a);
		west.add(b);
		east.add(rb1);
		east.add(rb2);
		menu.add(east, BorderLayout.EAST);
		menu.add(west, BorderLayout.WEST);
		menu.setVisible(true);
		jOption = JOptionPane.showConfirmDialog(null, menu,"", JOptionPane.YES_NO_OPTION);
	}
	public String getCurrentLibrary()	{
		return currentLibrary;
	}
	public void setCurrentLibrary(String s)	{
		currentLibrary = s;
	}
	public JTable getTable()	{
		return table;
	}
	public void focusGained(FocusEvent e) {
		if (e.getSource() == searchField)	{
			searchField.selectAll();
		}
	}
	public void focusLost(FocusEvent e) {
		if (e.getSource() == searchField)	{
			if (searchField.getText().isEmpty())	{
				searchField.setText("[search]");
			}
		}
	}
	public void dragEnter(DropTargetDragEvent e) {
	}
	public void dragExit(DropTargetEvent e) {
	}
	public void dragOver(DropTargetDragEvent e) {
	}
	public void drop(DropTargetDropEvent e) {
		UIManager.put("OptionPane.background", Color.WHITE);
		UIManager.put("Panel.background", Color.WHITE);
		Transferable tr = e.getTransferable();
		DataFlavor[] flavors = tr.getTransferDataFlavors();
		File file;
		for (int i = 0; i < flavors.length; i++) {
			if (flavors[i].isFlavorJavaFileListType()) {
				e.acceptDrop(DnDConstants.ACTION_COPY);
				List<?> list = null;
				try {
					list = (List<?>) tr.getTransferData(flavors[i]);

					for (int j = 0; j < list.size(); j++) {
						file = new File(list.get(j).toString());
						if (file.isDirectory())	{
							currentLibrary = file.getCanonicalPath();
						}	else if (currentLibrary.isEmpty())	{
							JOptionPane.showMessageDialog(null, "Please choose a directory for your library.");
						}	else	{
							model.addRow(file);
							model.fireTableDataChanged();
						}
					}
					//	GUI.LibrarySave();
					e.dropComplete(true);
					return;
				} catch (UnsupportedFlavorException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		e.rejectDrop();
	}
	public void dropActionChanged(DropTargetDragEvent e) {
	}
}
