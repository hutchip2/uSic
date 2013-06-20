package GUI;

import java.awt.Cursor;
import java.io.File;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import Logic.FileTraverse;

public class TableModel extends AbstractTableModel implements TableModelListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2237705674501111293L;
	
	public static Object[][] data;
	Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
	Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
	private String[] columnNames = {"*",
			"Title",
			"Time",
			"Artist",
			"Album",
			"Genre",
	"Size"};
	public int getColumnCount() {
		return columnNames.length;
	}
	public String getColumnName(int col) {
		return columnNames[col];
	}
	public int getRowCount() {
		return FileTraverse.list.size();
	}
	public Class<?> getColumnClass(int column) {
		if (column == 0) {
			return Boolean.class;
		}
		return Object.class;
	}
	public Object getValueAt(int row, int col) {
		if ((row < data[0].length && col < 7))	{
			return data[col][row];
		}	else	{
			return null;
		}
	}
	public boolean isCellEditable(int row, int col) {
		if (col == 0)	{
			return true;
		} else	{
			return false;
		}
	}
	public void tableChanged(TableModelEvent e) {
	}
	public void addRow(File f)	{
		AudioFile file = null;
		try {
			file = AudioFileIO.read(f);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		Tag tag = file.getTag();
		AudioHeader d = file.getAudioHeader();

		FileTraverse.list.add(FileTraverse.list.size(), f);

		Object[][] temp = new Object[7][FileTraverse.list.size()];
		for (int i = 0; i < data.length; i++)	{
			for (int j = 0; j < data[0].length; j++)	{
				temp[i][j] = data[i][j];
			}
		}
		String trackLength = d.getTrackLength()/60 + ":" + d.getTrackLength()%60;
		if (trackLength.length() < 4)	{
			trackLength = trackLength.substring(0, 2) + "0" + trackLength.substring(2);
		}
		double size = (double)f.length()/(1024*1024);	
		String displaySize = FileTraverse.df.format(size);
		if (displaySize.length() == 1 || displaySize.length() == 2)	{
			displaySize = FileTraverse.df.format(size) + ".00";
		}
		if (displaySize.length() == 3)	{
			displaySize = FileTraverse.df.format(size) + "0";
		}
		if (displaySize.substring(0, displaySize.indexOf('.')).length() == 1)	{
			displaySize = "0" + displaySize;
		}
		if (displaySize.length() == 4)	{
			displaySize = displaySize+"0";
		}
		temp[0][temp[0].length-1] = Boolean.TRUE;
		temp[1][temp[0].length-1] = tag.getFirst(FieldKey.TITLE);
		temp[2][temp[0].length-1] = trackLength;
		temp[3][temp[0].length-1] = tag.getFirst(FieldKey.ARTIST);
		temp[4][temp[0].length-1] = tag.getFirst(FieldKey.ALBUM);
		temp[5][temp[0].length-1] = tag.getFirst(FieldKey.GENRE);
		temp[6][temp[0].length-1] = displaySize + " MB";
		data = temp;
	//	LP.model.fireTableDataChanged();
	}
	public static void removeRow(int row) {
		int modelRow = LibraryPanel.table.convertRowIndexToModel(row);
	    FileTraverse.list.remove(modelRow);
		Object[][] temp1 = new Object[7][modelRow];
		Object[][] temp2 = new Object[7][(data[0].length-1) - modelRow];
		Object[][] tempEnd  = new Object[7][FileTraverse.list.size()];
		for (int i = 0; i < temp1.length; i++)	{
			for (int j = 0; j < temp1[0].length; j++)	{
				temp1[i][j] = data[i][j];
			}
		}
		int start = modelRow+1;
		for (int i = 0; i < temp2.length; i++)	{
			for (int j = 0; j < temp2[0].length; j++)	{
				temp2[i][j] = data[i][start+j];
			}
		}
		for (int i = 0; i < temp1.length; i++)	{
			for (int j = 0; j < temp1[0].length; j++)	{
				tempEnd[i][j] = temp1[i][j];
			}
		}
		for (int i = 0; i < temp2.length; i++)	{
			for (int j = 0; j < temp2[0].length; j++)	{
				tempEnd[i][modelRow+j] = temp2[i][j];
			}
		}
		data = tempEnd;
		//LibraryPanel.model.fireTableDataChanged();
	}
	
}
