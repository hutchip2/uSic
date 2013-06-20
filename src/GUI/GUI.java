package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableColumn;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import Logic.FileTraverse;
import Player.BasicPlayer;
import Player.BasicPlayerException;

public class GUI extends JFrame implements ActionListener, MouseListener, ChangeListener	{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5320722080594616360L;
	static LibraryPanel LP;
	ControlPanel CP;
	PlaylistPanel PP;
	JMenuItem about;
	JMenuItem hotkeys;
	JMenuItem setLibrary;
	JMenuItem loop;
	//String Library = new String("");
	public static JTextField titleEdit;
	public static JTextField artistEdit;
	public static JTextField albumEdit;
	public static JTextField genreEdit;
	public static JCheckBoxMenuItem albumart = new JCheckBoxMenuItem("Show Album Art");
	public static String albumArtSave = new String("true");
	public static JLabel libraryPath;
	public static JLabel totalSongs1 = new JLabel("Songs");
	public static JLabel totalSongs = new JLabel();
	public static JLabel totalSize = new JLabel();
	public static JLabel totalLength = new JLabel();
	public static JLabel artwork = new JLabel();
	private static int modelRow;
	public static Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
	public static Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
	public static DecimalFormat df = new DecimalFormat("#.##");
	private JCheckBox number = new JCheckBox();
	private JCheckBox title = new JCheckBox();
	private JCheckBox time = new JCheckBox();
	private JCheckBox artist = new JCheckBox();
	private JCheckBox album = new JCheckBox();
	private JCheckBox genre = new JCheckBox();
	private JCheckBox size = new JCheckBox();
	//private JCheckBox playCount = new JCheckBox();
	//private JCheckBox rating = new JCheckBox();
	private JPanel show = new JPanel();
	private static JPanel edit2;
	private static JPanel summary;
	private static JPanel summaryWest;
	private static JPanel summaryEast;
	private static ListSelectionModel selectionModel;
	public static JLabel filePath = new JLabel();
	public static JTabbedPane tabbedPane;
	JMenuItem selectAll;
	JMenuItem resume;
	JMenuItem pause;
	JMenuItem rw;
	JMenuItem ff;
	JMenuItem info;
	JMenuItem columns;
	JMenuItem shuffle;
	public static ImageIcon missing = new ImageIcon("Graphics\\missing.png");
	public static ImageIcon playing = new ImageIcon("Graphics\\playing.png");
	public static double oldVolume = 0;
	BasicPlayer player;

	public GUI(LibraryPanel lp, ControlPanel cp, PlaylistPanel pp)	{
		LP = lp;
		CP = cp;
		PP = pp;
		libraryPath = new JLabel(LP.getCurrentLibrary());
		setTitle("µSic");
		setBackground(Color.WHITE);
		setIconImage(null);
		setSize(1024,600);
		setLayout(new BorderLayout());
		centerFrame();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		ImageIcon logomenubar = new ImageIcon("Graphics/Control Panel Icons/logomenubar.png");
		setIconImage(logomenubar.getImage());
		SetupMenus();
		SetupActionListener();
		add(LP, BorderLayout.CENTER);
		add(CP, BorderLayout.SOUTH);
		new SysTray();
		validate(); //fixes startup bug where elements are invisible
		player = new BasicPlayer();
	}
	public void launchOpenDialog()	{
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setDialogTitle("Pick a library...");
		int i = fc.showOpenDialog(this);
		if (i == JFileChooser.APPROVE_OPTION) {
			LP.setCurrentLibrary(fc.getSelectedFile().toString());
			System.out.println("Current Library: " + LP.getCurrentLibrary());
		}
	}
	private void SetupShow()	{
		show.setLayout(new GridLayout(7,2));
		show.add(new JLabel("#: "));
		show.add(number);
		show.add(new JLabel("Title: "));
		show.add(title);
		show.add(new JLabel("Time: "));
		show.add(time);
		show.add(new JLabel("Artist: "));
		show.add(artist);
		show.add(new JLabel("Album: "));
		show.add(album);
		show.add(new JLabel("Genre: "));
		show.add(genre);
		show.add(new JLabel("Size: "));
		show.add(size);
		show.setBackground(Color.WHITE);
	}
	private void SetupMenus()	{
		UIManager.put( "Menu.selectionBackground", new Color(241, 192, 56, 200) );
		//	UIManager.put( "MenuItem.selectionBackground", new Color (241, 192, 56, 130) );
		JMenuBar bar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenu edit = new JMenu("Edit");
		JMenu view = new JMenu("View");
		JMenu controls = new JMenu("Controls");
		JMenu help = new JMenu("Help");
		hotkeys = new JMenuItem("Hotkeys");
		setLibrary = new JMenuItem("Set Library...");
		selectAll = new JMenuItem("Select All...");
		resume = new JMenuItem("Play/Resume");
		pause = new JMenuItem("Pause");
		rw = new JMenuItem("Back");
		ff = new JMenuItem("Forward");
		info = new JMenuItem("Info");
		columns = new JMenuItem("Columns");
		shuffle = new JMenuItem("Shuffle");
		loop = new JMenuItem("Loop");
		about = new JMenuItem("About...");
		JCheckBoxMenuItem albumart = new JCheckBoxMenuItem("Show Album Art");
		file.add(setLibrary);
		edit.add(selectAll);
		edit.add(info);
		controls.add(resume);
		controls.add(pause);
		controls.addSeparator();
		controls.add(rw);
		controls.add(ff);
		controls.addSeparator();
		controls.add(shuffle);
		controls.add(loop);
		help.add(about);
		help.add(hotkeys);
		view.add(albumart);
		view.add(columns);
		bar.add(file);
		bar.add(edit);
		bar.add(view);
		bar.add(controls);
		bar.add(help);
		bar.setVisible(true);
		add(bar, BorderLayout.NORTH);
	}
	public void SetupActionListener()	{
		LibraryPanel.table.addMouseListener(this);
		about.addActionListener(this);
		hotkeys.addActionListener(this);
		setLibrary.addActionListener(this);
		selectAll.addActionListener(this);
		resume.addActionListener(this);
		pause.addActionListener(this);
		rw.addActionListener(this);
		ff.addActionListener(this);
		info.addActionListener(this);
		columns.addActionListener(this);
		shuffle.addActionListener(this);
		ControlPanel.volume.addChangeListener(this);
		ControlPanel.pp.addActionListener(this);
		CP.ff.addActionListener(this);
		CP.rw.addActionListener(this);
		ControlPanel.loop.addActionListener(this);
		ControlPanel.shuffle.addActionListener(this);
	}
	public void centerFrame()	{
		Toolkit toolkit = Toolkit.getDefaultToolkit();  
		Dimension screenSize = toolkit.getScreenSize(); 
		int x = (screenSize.width - getWidth()) / 2;  
		int y = (screenSize.height - getHeight()) / 2;   
		setLocation(x, y); 
	}
	public void launchAbout()	{
		String about = new String("uSic" + "\n" + "-A light weight java music player" + "\n" + 
		"Copyright Paul Hutchinson, 2010");
		JOptionPane.showMessageDialog(null, about, "µSic - About", 0, new ImageIcon("Graphics/uSic2.jpg"));
	}
	public static void launchHotkeys()	{
		JPanel hotkeys = new JPanel(new GridLayout(11, 2));
		JLabel mute1 = new JLabel("Ctrl + M ");
		JLabel mute2 = new JLabel(": Mute/Unmute");
		JLabel pause1 = new JLabel("Ctrl + P ");
		JLabel pause2 = new JLabel(": Pause/Resume");
		JLabel loop1 = new JLabel("Ctrl + L ");
		JLabel loop2 = new JLabel(": Toggle Loop");
		JLabel shuffle1 = new JLabel("Ctrl + S ");
		JLabel shuffle2 = new JLabel(": Toggle Shuffle");
		JLabel art1 = new JLabel("Ctrl + V ");
		JLabel art2 = new JLabel(": View Album Art");
		JLabel vup1 = new JLabel("Ctrl + Up ");
		JLabel vup2 = new JLabel(": Volume Up");
		JLabel vdown1 = new JLabel("Ctrl + Down ");
		JLabel vdown2 = new JLabel(": Volume Down");
		JLabel next1 = new JLabel("Ctrl + Right ");
		JLabel next2 = new JLabel(": Next Track");
		JLabel back1 = new JLabel("Ctrl + Left ");
		JLabel back2 = new JLabel(": Previous Track");
		JLabel playlist1 = new JLabel("Ctrl + Q ");
		JLabel playlist2 = new JLabel(": Add To Playlist");
		JLabel play1 = new JLabel("Spacebar ");
		JLabel play2 = new JLabel(": Play");
		hotkeys.add(mute1);
		hotkeys.add(mute2);
		hotkeys.add(pause1);
		hotkeys.add(pause2);
		hotkeys.add(loop1);
		hotkeys.add(loop2);
		hotkeys.add(shuffle1);
		hotkeys.add(shuffle2);
		hotkeys.add(art1);
		hotkeys.add(art2);
		hotkeys.add(playlist1);
		hotkeys.add(playlist2);
		hotkeys.add(vup1);
		hotkeys.add(vup2);
		hotkeys.add(vdown1);
		hotkeys.add(vdown2);
		hotkeys.add(next1);
		hotkeys.add(next2);
		hotkeys.add(back1);
		hotkeys.add(back2);
		hotkeys.add(play1);
		hotkeys.add(play2);
		JOptionPane.showMessageDialog(null, hotkeys, "µSic - Hotkeys", 0, new ImageIcon());
	}
	public void launchView()	{
		TableColumn column = null;
		if (LP.tp.getSelectedIndex() == 0)	{
			column = LP.getTable().getColumnModel().getColumn(0);
		} else	{
			//column = PlaylistPanel.table2.getColumnModel().getColumn(0);
		}
		if (column.getWidth() == 0)	{
			number.setSelected(false);
		}	else	{
			number.setSelected(true);	
		}
		number.setBackground(Color.WHITE);
		if (LP.tp.getSelectedIndex() == 0)	{
			column = LP.getTable().getColumnModel().getColumn(1);
		} else	{
			//column = PlaylistPanel.table2.getColumnModel().getColumn(1);
		}
		if (column.getWidth() == 0)	{
			title.setSelected(false);
		}	else	{
			title.setSelected(true);	
		}
		title.setBackground(Color.WHITE);
		if (LP.tp.getSelectedIndex() == 0)	{
			column = LP.getTable().getColumnModel().getColumn(2);
		} else	{
			//column = PlaylistPanel.table2.getColumnModel().getColumn(2);
		}
		if (column.getWidth() == 0)	{
			time.setSelected(false);
		}	else	{
			time.setSelected(true);	
		}	
		time.setBackground(Color.WHITE);
		if (LP.tp.getSelectedIndex() == 0)	{
			column = LP.getTable().getColumnModel().getColumn(3);
		} else	{
			//column = PlaylistPanel.table2.getColumnModel().getColumn(3);
		}
		if (column.getWidth() == 0)	{
			artist.setSelected(false);
		}	else	{
			artist.setSelected(true);	
		}	
		artist.setBackground(Color.WHITE);
		if (LP.tp.getSelectedIndex() == 0)	{
			column = LP.getTable().getColumnModel().getColumn(4);
		} else	{
			//column = PlaylistPanel.table2.getColumnModel().getColumn(4);
		}
		if (column.getWidth() == 0)	{
			album.setSelected(false);
		}	else	{
			album.setSelected(true);	
		}	
		album.setBackground(Color.WHITE);
		if (LP.tp.getSelectedIndex() == 0)	{
			column = LP.getTable().getColumnModel().getColumn(5);
		} else	{
			//column = PlaylistPanel.table2.getColumnModel().getColumn(5);
		}
		if (column.getWidth() == 0)	{
			genre.setSelected(false);
		}	else	{
			genre.setSelected(true);	
		}	
		genre.setBackground(Color.WHITE);
		if (LP.tp.getSelectedIndex() == 0)	{
			column = LP.getTable().getColumnModel().getColumn(6);
		} else	{
			//column = PlaylistPanel.table2.getColumnModel().getColumn(6);
		}
		if (column.getWidth() == 0)	{
			size.setSelected(false);
		}	else	{
			size.setSelected(true);	
		}	
		size.setBackground(Color.WHITE);
		show = new JPanel();
		SetupShow();
		JOptionPane.showMessageDialog(null, show, "Columns", JOptionPane.PLAIN_MESSAGE);
	}
	public void LibrarySave()	{
		ArrayList<Object> data = new ArrayList<Object>();
		data.add(TableModel.data);
		data.add(FileTraverse.list);
		data.add(LibraryPanel.currentLibrary);
		data.add(albumArtSave);
		try{
			OutputStream file = new FileOutputStream("Data\\Library.µsic");
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
			output.writeObject(data);
			output.close();
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public static void LibraryLoad()	{
		try{
			InputStream file = new FileInputStream("Data\\Library.µsic");
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);
			ArrayList<Object> data = (ArrayList<Object>) input.readObject();
			TableModel.data = (Object[][]) data.get(0);
			FileTraverse.list = (ArrayList<File>) data.get(1);
			LibraryPanel.currentLibrary = (String)data.get(2);
			albumArtSave = (String)data.get(3);
			input.close();
			if (albumArtSave.equals("true"))	{
				albumart.setSelected(true);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static boolean isQuickLoad()	{
		File file = new File("Data\\Library.µsic");
		if (file.exists())	{
			return true;
		} else	{
			return false;
		}
	}
	public void Delete()	{
		this.setCursor(hourglassCursor);
		if (LP.getTable().getSelectedRowCount()==1)	{
			int row = LP.getTable().getSelectedRow();
			LP.deleteMenu();
			if (LP.jOption == 0)	{
				if (LP.rb1.isSelected())	{	//library
					TableModel.removeRow(row);
				}
				if (LP.rb2.isSelected())	{	//drive
					File f = FileTraverse.list.get(modelRow);
					f.delete();
					TableModel.removeRow(modelRow);
				}
				updateLabels();
			}
		} else if (LibraryPanel.table.getSelectedRowCount()>1)	{
			int Rows[] = LibraryPanel.table.getSelectedRows();
			LP.deleteMenu();
			if (LP.jOption == 0)	{
				if (LP.rb1.isSelected())	{	//library
					for (int i = 0; i < Rows.length; i++)	{
						TableModel.removeRow(Rows[0]);
					}
				}
				if (LP.rb2.isSelected())	{	//drive
					File f = FileTraverse.list.get(modelRow);
					f.delete();
					for (int i = 0; i < LibraryPanel.table.getSelectedRowCount(); i++)	{
						TableModel.removeRow(Rows[0]);
					}
				}
			}
			updateLabels();
		}
		LibrarySave();
		this.setCursor(normalCursor);
	}
	public void switchLibrary(String s)	{
		LibraryPanel.currentLibrary = s;
		LibrarySave();
		LibraryPanel.model.fireTableDataChanged();
	}
	public void setLib()	{
		//System.out.println("Setting Library...");
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setDialogTitle("Pick a library...");
		FileTraverse.totalSize = 0;
		FileTraverse.totalLength = 0;
		int i = fc.showOpenDialog(this);
		if (i == JFileChooser.APPROVE_OPTION) {
			switchLibrary(fc.getSelectedFile().toString());
			try {
				BuildLibrary();
			} catch (Exception e) {
				e.printStackTrace();
			} 
			LibrarySave();
		}
	}
	public void updateLibLabel()	{
		libraryPath.setText(LibraryPanel.currentLibrary);
	}
	public static void updateSongLabel()	{
		totalSongs.setText(Integer.toString(FileTraverse.list.size()));
	}
	public static void updateSizeLabel()	{
		double total = 0;
		for (int i = 0; i < FileTraverse.list.size(); i++)	{
			String current;
			double size;
			try	{
				current = TableModel.data[6][i].toString();
				size = Double.parseDouble(current.substring(0, current.indexOf(' ')));
			} catch (NullPointerException e)	{
				size = 0;
			}
			total += size;
		}
		totalSize.setText(df.format(total) + " MB");
	}
	public static void updateLengthLabel()	{
		String LibLength = getLibLength();
		totalLength.setText(LibLength);
	}
	public static void updateLabels()	{
		updateSongLabel();
		updateSizeLabel();
		updateLengthLabel();
	}
	public static String getLibLength()	{
		String current;
		int seconds = 0;
		int min;
		int sec;
		for(int i = 0; i < FileTraverse.list.size(); i++)	{
			try	{
				current = TableModel.data[2][i].toString();
				min = Integer.parseInt(current.substring(0, current.indexOf(':')));
				sec = Integer.parseInt(current.substring(current.indexOf(':')+1, current.length()));
			} catch (NullPointerException e)	{
				min = 0;
				sec = 0;
			}
			seconds += (min * 60) + sec;
		}
		int minutes = (int) seconds/60;
		int hours = (int) minutes/60;
		String total = new String(hours + " hours, " + minutes%60 + " minutes, " + seconds%60 + " seconds");
		return total;
	}
	public static void SaveBooleans()	{
		try	{
			File file = new File("Booleans.txt");
			PrintWriter writer = new PrintWriter(file);
			for (int i = 0; i < FileTraverse.list.size(); i++)	{
				if (TableModel.data[0][i] == Boolean.TRUE)	{
					writer.write(0 + "\n");
				}	else	{
					writer.write(1 + "\n");
				}
			}
			writer.close();
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
	}
	public void BuildLibrary() throws IOException  {
		setCursor(hourglassCursor);
		File library = new File(LibraryPanel.currentLibrary);
		System.out.println(library.getCanonicalPath());
		try {
			//System.out.println("building");
			FileTraverse.list.clear();
			FileTraverse.traverse(library);
			TableModel.data = new Object[7][FileTraverse.list.size()];
			FileTraverse.buildTable();
			GUI.updateLabels();
			//GUI.updateLibLabel();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CannotReadException e) {
			e.printStackTrace();
		} catch (TagException e) {
			e.printStackTrace();
		} catch (ReadOnlyFileException e) {
			e.printStackTrace();
		}
		LibrarySave();
		this.setCursor(normalCursor);
	}
	public void launchInfo(JTable source) throws  IOException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException	{
		edit2 = new JPanel();
		summary = new JPanel();
		summaryEast = new JPanel();
		summaryWest = new JPanel();
		String path = null;
		String lib[] = LibraryPanel.listLib();
		//String playlistLib[] = PlaylistPanel.listLib();
		selectionModel = source.getSelectionModel();
		if (source.getSelectedRow() < 0){
			selectionModel.setSelectionInterval(0, 0);
		}
		modelRow = source.convertRowIndexToModel(source.getSelectedRow());
		if (source == LibraryPanel.table)	{
			path = lib[modelRow];
		}
		//	if (source == PlaylistPanel.table2)	{
		//		path = playlistLib[modelRow];
		//	}
		try	{
			AudioFile f = AudioFileIO.read(new File(path));
			Tag tag = f.getTag();
			if (modelRow>=0)	{
				AudioHeader d = f.getAudioHeader();
				String trackLength = d.getTrackLength()/60 + ":" + d.getTrackLength()%60;
				if (trackLength.length() < 4)	{
					trackLength = trackLength.substring(0, 2) + "0" + trackLength.substring(2);
				}
				edit2.setLayout(new GridLayout(4,2));
				titleEdit = new JTextField(tag.getFirst(FieldKey.TITLE));
				titleEdit.setPreferredSize(new Dimension(100, 30));
				artistEdit = new JTextField(tag.getFirst(FieldKey.ARTIST));
				titleEdit.setPreferredSize(new Dimension(100, 30));
				albumEdit = new JTextField(tag.getFirst(FieldKey.ALBUM));
				titleEdit.setPreferredSize(new Dimension(100, 30));
				genreEdit = new JTextField(tag.getFirst(FieldKey.GENRE));
				titleEdit.setPreferredSize(new Dimension(100, 30));
				edit2.add(new JLabel("Title: "));
				edit2.add(titleEdit);
				edit2.add(new JLabel("Artist: "));
				edit2.add(artistEdit);
				edit2.add(new JLabel("Album: "));
				edit2.add(albumEdit);
				edit2.add(new JLabel("Genre: "));
				edit2.add(genreEdit);
				summary.setLayout(new BorderLayout()); //14
				summaryWest.setLayout(new GridLayout(12, 1));
				summaryEast.setLayout(new GridLayout(12, 1));
				JLabel Artist = new JLabel("Artist: ");
				Artist.setHorizontalAlignment(JLabel.RIGHT);
				summaryWest.add(Artist);
				String artist = null;
				try	{
					artist = tag.getFirst(FieldKey.ARTIST);
				} catch	(NullPointerException e)	{
					artist = "";
				}
				summaryEast.add(new JLabel (artist));
				JLabel Title = new JLabel("Title: ");
				Title.setHorizontalAlignment(JLabel.RIGHT);
				summaryWest.add(Title);
				String title = null;
				try	{
					title = tag.getFirst(FieldKey.TITLE);
				} catch	(NullPointerException e)	{
					title = "";
				}
				summaryEast.add(new JLabel (title));
				JLabel Album = new JLabel("Album: ");
				Album.setHorizontalAlignment(JLabel.RIGHT);
				summaryWest.add(Album);
				String album = null;
				try	{
					album = tag.getFirst(FieldKey.ALBUM);
				} catch	(NullPointerException e)	{
					album = "";
				}
				summaryEast.add(new JLabel(album));
				JLabel Track = new JLabel("Track: ");
				Track.setHorizontalAlignment(JLabel.RIGHT);
				summaryWest.add(Track);

				String track = null;
				try	{
					track = tag.getFirst(FieldKey.TRACK);
				} catch	(NullPointerException e)	{
					track = "0";
				}
				summaryEast.add(new JLabel(track));
				JLabel Year = new JLabel("Year: ");
				Year.setHorizontalAlignment(JLabel.RIGHT);
				summaryWest.add(Year);
				String year = null;
				try	{
					year = tag.getFirst(FieldKey.YEAR);
				} catch	(NullPointerException e)	{
					year = "";
				}
				summaryEast.add(new JLabel (year));
				JLabel Bitrate = new JLabel("Bitrate: ");
				Bitrate.setHorizontalAlignment(JLabel.RIGHT);
				summaryWest.add(Bitrate);
				String bitrate = null;
				try	{
					bitrate = d.getBitRate();
				} catch	(NullPointerException e)	{
					bitrate = "";
				}
				summaryEast.add(new JLabel (bitrate + " kbit/s"));
				JLabel Channel = new JLabel("Channel Mode: ");
				Channel.setHorizontalAlignment(JLabel.RIGHT);
				summaryWest.add(Channel);
				String channels = null;
				try	{
					channels = d.getChannels();
				} catch	(NullPointerException e)	{
					channels = "";
				}
				summaryEast.add(new JLabel (channels));
				JLabel Genre = new JLabel("Genre: ");
				Genre.setHorizontalAlignment(JLabel.RIGHT);
				summaryWest.add(Genre);
				String genre = null;
				try	{
					genre = tag.getFirst(FieldKey.GENRE);
				} catch	(NullPointerException e)	{
					genre = "";
				}
				summaryEast.add(new JLabel (genre));
				JLabel Runtime = new JLabel("Run Time: ");
				Runtime.setHorizontalAlignment(JLabel.RIGHT);
				summaryWest.add(Runtime);
				summaryEast.add(new JLabel (trackLength));
				JLabel Size = new JLabel("Size: ");
				Size.setHorizontalAlignment(JLabel.RIGHT);
				summaryWest.add(Size);
				summaryEast.add(new JLabel (df.format((double)new File(path).length()/(1024*1024))+ " MB"));
				JLabel BPM = new JLabel("BPM: ");
				BPM.setHorizontalAlignment(JLabel.RIGHT);
				summaryWest.add(BPM);
				if (tag.getFirst(FieldKey.BPM) == "")	{
					summaryEast.add(new JLabel("unknown"));
				}	else	{
					summaryEast.add(new JLabel (tag.getFirst(FieldKey.BPM)));
				}
				JLabel Location = new JLabel("Location:	");
				Location.setHorizontalAlignment(JLabel.RIGHT);
				summaryWest.add(Location);
				filePath.setText(path);
				summaryEast.add(filePath);
				filePath.setToolTipText(path);
			}
			summary.add(summaryWest, BorderLayout.WEST);
			summary.add(summaryEast, BorderLayout.CENTER);
			tabbedPane = new JTabbedPane();
			tabbedPane.setPreferredSize(new Dimension(400,300));
			tabbedPane.addTab("Summary", summary);
			tabbedPane.addTab("Edit Info", edit2);
			JOptionPane.showMessageDialog(null, tabbedPane, "Track Information", JOptionPane.PLAIN_MESSAGE);
			//	FileTraverse.editTags(source);
		} catch (Exception e1)	{
			e1.printStackTrace();
		}
	}
	public void launchMultiEdit(JTable source)	{
		edit2 = new JPanel();
		edit2.setLayout(new GridLayout(4,2));
		titleEdit = new JTextField();
		titleEdit.setPreferredSize(new Dimension(100, 30));
		artistEdit = new JTextField();
		titleEdit.setPreferredSize(new Dimension(100, 30));
		albumEdit = new JTextField();
		titleEdit.setPreferredSize(new Dimension(100, 30));
		genreEdit = new JTextField();
		titleEdit.setPreferredSize(new Dimension(100, 30));
		edit2.add(new JLabel("Title: "));
		edit2.add(titleEdit);
		edit2.add(new JLabel("Artist: "));
		edit2.add(artistEdit);
		edit2.add(new JLabel("Album: "));
		edit2.add(albumEdit);
		edit2.add(new JLabel("Genre: "));
		edit2.add(genreEdit);
		tabbedPane = new JTabbedPane();
		tabbedPane.setPreferredSize(new Dimension(400,300));
		tabbedPane.addTab("Edit Info", edit2);
		JOptionPane.showMessageDialog(null, tabbedPane, "Track Information", JOptionPane.PLAIN_MESSAGE);

		//	FileTraverse.editTags(source);

	}
	public JPopupMenu buildPopupMenu()	{
		JPopupMenu menu = new JPopupMenu();
		menu.setBackground(Color.WHITE);
		JMenuItem play = new JMenuItem("Play");
		JMenuItem delete = new JMenuItem("Delete");
		JMenuItem Info = new JMenuItem("Info");
		JMenuItem addPlaylist = new JMenuItem("Add to playlist...");
		JMenuItem albumart = new JMenuItem("View Album Art");
		if (LibraryPanel.table.getSelectedRowCount()<2)	{
			menu.add(Info);
			menu.add(albumart);
			menu.addSeparator();
			menu.add(addPlaylist);
			menu.addSeparator();
			menu.add(play);
			menu.addSeparator();
			menu.add(delete);
		} else	{
			menu.add(Info);
			menu.add(addPlaylist);
			menu.add(delete);
		}
		play.addActionListener(this);
		delete.addActionListener(this);
		Info.addActionListener(this);
		addPlaylist.addActionListener(this);
		albumart.addActionListener(this);
		return menu;
	}
	public JPopupMenu buildRolloverMenu()	{
		JPopupMenu menu = new JPopupMenu();
		menu.setBackground(Color.WHITE);
		return menu;
	}
	public static ImageIcon resize(ImageIcon icon)	{
		Image image = null;
		Image newImage = null;
		ImageIcon newImageIcon = null;
		try	{
			image = icon.getImage();
			newImage = image.getScaledInstance(115, 115, Image.SCALE_SMOOTH);
			newImageIcon = new ImageIcon(newImage);
		} catch	(NullPointerException e){
		}
		return newImageIcon;
	}
	public static ImageIcon resizeLarge(ImageIcon icon)	{
		Image image = null;
		Image newImage = null;
		ImageIcon newImageIcon = null;
		try	{
			image = icon.getImage();
			newImage = image.getScaledInstance(400, 400, Image.SCALE_SMOOTH);
			newImageIcon = new ImageIcon(newImage);
		} catch	(NullPointerException e){
		}
		return newImageIcon;
	}
	public static ImageIcon GetAlbumArt(JTable source)	{
		JLabel artwork = new JLabel();
		ImageIcon icon = null; 
		AudioFile f = null;
		selectionModel = source.getSelectionModel();
		if (source.getSelectedRow() < 0){
			selectionModel.setSelectionInterval(0, 0);
		}
		try {
			int modelRow = source.convertRowIndexToModel(source.getSelectedRow());
			if (source == LibraryPanel.table)	{
				f = AudioFileIO.read((FileTraverse.list.get(modelRow)));
			}
			//	if (source == PlaylistPanel.table2)	{
			//		f = AudioFileIO.read((PlaylistModel.playlist.get(modelRow)));
			//	}
			Tag tag = f.getTag();
			icon = new ImageIcon(tag.getFirstArtwork().getImage());
			artwork.setIcon(icon);
		} catch (Exception e1) {
			if (e1 instanceof NullPointerException || e1 instanceof IndexOutOfBoundsException)	{
				artwork.setIcon(resize(new ImageIcon("Graphics/uSic2.png")));
			}
		} 
		return icon;
	}
	public void updateNowPlaying()	{
		//String lib[] = LibraryPanel.listLib();
		setTitle("µSic: " + BasicPlayer.artist + " - " + BasicPlayer.title);
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ControlPanel.pp){
			if (ControlPanel.pp.getIcon()==ControlPanel.play)	{
				try {
					if (player.isStopped() || player.isUnknown())	{
						BasicPlayer.modelRow = LibraryPanel.table.convertRowIndexToModel(LibraryPanel.table.getSelectedRow());
						player.play();
						updateNowPlaying();
					} else if (player.isPaused())	{
						player.resume();
					} else if (player.isPlaying())	{
						player.stop();
						BasicPlayer.modelRow = LibraryPanel.table.convertRowIndexToModel(LibraryPanel.table.getSelectedRow());
						player.play();
						updateNowPlaying();
					}
				} catch (BasicPlayerException e1) {
					e1.printStackTrace();
				}
			} else if(ControlPanel.pp.getIcon()==ControlPanel.pause)	{
				try {
					player.pause();
				} catch (BasicPlayerException e1) {
					e1.printStackTrace();
				}
			}
		}
		if (e.getSource() == CP.ff)	{
			BasicPlayer.modelRow = LibraryPanel.table.convertRowIndexToModel(LibraryPanel.table.getSelectedRow());
			player.forward();
			updateNowPlaying();
		}
		if (e.getSource() == CP.rw)	{
			BasicPlayer.modelRow = LibraryPanel.table.convertRowIndexToModel(LibraryPanel.table.getSelectedRow());
			player.back();
			updateNowPlaying();
		}
		if (e.getActionCommand().equals("About..."))	{
			launchAbout();
		}
		if (e.getActionCommand().equals("Hotkeys"))	{
			launchHotkeys();
		}
		if (e.getActionCommand().equals("Set Library..."))	{
			setLib();
		}
		if (e.getActionCommand().equals("Shuffle"))	{
		}
		if (e.getActionCommand().equals("Loop"))	{
		}
		if (e.getActionCommand().equals("Get Info"))	{
			try {
				launchInfo(LibraryPanel.table);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if (e.getActionCommand().equals("Columns"))	{
			launchView();
		}
		if (e.getActionCommand().equals("Play/Resume"))	{
			BasicPlayer.modelRow = LibraryPanel.table.convertRowIndexToModel(LibraryPanel.table.getSelectedRow());
			try {
				if (player.isStopped() || player.isUnknown())	{
					player.play();
				} else if (player.isPaused())	{
					player.play();
				} else if (player.isPlaying())	{
					player.stop();
					player.play();
				}
				updateNowPlaying();
			} catch (BasicPlayerException e1) {
				e1.printStackTrace();
			}
		}
		if (e.getActionCommand().equals("Pause"))	{
			try {
				player.pause();
			} catch (BasicPlayerException e1) {
				e1.printStackTrace();
			}
		}
		if (e.getActionCommand().equals("Visualizer"))	{
		}
		if (e.getActionCommand().equals("Back"))	{
			player.back();
		}
		if (e.getActionCommand().equals("Forward"))	{
			player.forward();
		}
		if (e.getActionCommand().equals("Select All..."))	{
			LibraryPanel.table.selectAll();
		}
		if (e.getActionCommand().equals("Info"))	{
			if (LP.tp.getSelectedIndex() == 0)	{
				if (LibraryPanel.table.getSelectedRowCount() == 1)	{
					try {
						launchInfo(LibraryPanel.table);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else	{
					launchMultiEdit(LibraryPanel.table);
				}
			}
			if (LP.tp.getSelectedIndex() == 1)	{
				/*	if (PlaylistPanel.table2.getSelectedRowCount() == 1)	{
					try {
						uSicPanel.launchInfo(PlaylistPanel.table2);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else	{
					uSicPanel.launchMultiEdit(PlaylistPanel.table2);
				}*/
			}
		}
	}
	public void mouseClicked(MouseEvent e) {
		//	int modelRow;
		if (e.getSource() instanceof JTable){
			try	{
				JTable target = (JTable)e.getSource();
				int row = LibraryPanel.table.convertRowIndexToModel(LibraryPanel.table.getSelectedRow());
				int col = target.getSelectedColumn();
				if (e.getSource() == target && col == 0)	{ //if user clicks column[0] in the table...
					if (TableModel.data[0][row] == Boolean.TRUE)	{
						TableModel.data[0][row] = Boolean.FALSE;
					}	else	{
						TableModel.data[0][row] = Boolean.TRUE;
					}
				}
				if (e.getClickCount() == 2) {
					BasicPlayer.modelRow = LibraryPanel.table.convertRowIndexToModel(LibraryPanel.table.getSelectedRow());
					if (player.isStopped() || player.isUnknown())	{
						player.play();
					} else if (player.isPaused())	{
						player.play();
					} else if (player.isPlaying())	{
						player.stop();
						player.play();
					}
					updateNowPlaying();
				}
				if (e.getClickCount() == 1)	{
					//BasicPlayer.modelRow = LibraryPanel.table.convertRowIndexToModel(LibraryPanel.table.getSelectedRow());
				}
			} catch (Exception e1)	{
				e1.printStackTrace();
			}
		} else	{
			if (e.getSource() == CP.artwork)	{
				if (GetAlbumArt(LibraryPanel.table) != null)	{
					//			if (GetAlbumArt(table).getIconHeight()>400 || GetAlbumArt(LP.table).getIconWidth()>400)	{
					//				JOptionPane.showMessageDialog(null, resizeLarge(GetAlbumArt(LP.table)), "Album Art", JOptionPane.PLAIN_MESSAGE);
					//			}	else	{
					//				JOptionPane.showMessageDialog(null, GetAlbumArt(LP.table), "Album Art", JOptionPane.PLAIN_MESSAGE);
					//			}
				}
			}
		}
	}
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == LibraryPanel.currentLibrary)	{
			//LP.currentLib.setForeground(uSic.generalFontColor);
		}
		//if (e.getSource() == nowPlaying)	{
		//uSic.nowPlaying.setForeground(uSic.generalFontColor);
		//}
		if (e.getSource() == filePath)	{
			//filePath.setForeground(uSic.generalFontColor);
		}
	}
	public void mouseExited(MouseEvent e) {
		if (e.getSource() == LibraryPanel.currentLibrary)	{
			//	LP.currentLib.setForeground(Color.BLACK);
		}
		//	if (e.getSource() == uSic.nowPlaying)	{
		//		uSic.nowPlaying.setForeground(Color.BLACK);
		//	}
		if (e.getSource() == filePath)	{
			filePath.setForeground(Color.BLACK);
		}
	}
	public void mousePressed(MouseEvent e) {
		ListSelectionModel selectionModel = LibraryPanel.table.getSelectionModel();
		if (SwingUtilities.isRightMouseButton(e))	{
			if (LibraryPanel.table.getSelectedRowCount() < 2)	{
				Point p = e.getPoint();
				int rowNumber = LibraryPanel.table.rowAtPoint(p);
				selectionModel.setSelectionInterval( rowNumber, rowNumber );
			}
		}
		/*	if (e.getSource() == uSic.nowPlaying)	{
			int viewRow = player.getPlayingRow();
			selectionModel.setSelectionInterval(viewRow,viewRow);
			player.scrollToVisible(LP.table, viewRow, 0);
		}*/
		if (e.getSource() == filePath)	{
			/*	Desktop desktop = null;
			if (Desktop.isDesktopSupported()) {
				desktop = Desktop.getDesktop();
			}

				String filePath = filePath.getText();
				String containingFolder = filePath.substring(0, filePath.lastIndexOf("\\"));
				desktop.open(new File(containingFolder));
			 */	
		}
		if (e.getSource() == CP.speaker)	{
			oldVolume = ControlPanel.volume.getValue();
			ControlPanel.volume.setValue(0);
		}
		if (e.getSource() == LibraryPanel.currentLibrary)	{
			Desktop desktop = null;
			if (Desktop.isDesktopSupported()) {
				desktop = Desktop.getDesktop();
			}
			try {
				desktop.open(new File(LibraryPanel.currentLibrary));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}	
	}
	public void mouseReleased(MouseEvent e) {
		JPopupMenu menu = buildPopupMenu();
		if(e.isPopupTrigger()){
			menu.show(e.getComponent(), e.getX(), e.getY());
		}

		if (e.getSource() == ControlPanel.seek)	{
			//player.seekFinal = CP.seek.getValue();			
			//player.seek();
		}
	}
	public void mouseDragged(MouseEvent e) {
	}
	public void mouseMoved(MouseEvent e) {
	}
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == ControlPanel.volume)	{
			try {
				player.setGain(ControlPanel.volume.getValue() * 0.01);
				if (ControlPanel.volume.getValue() == 0)	{
					CP.speaker.setIcon(CP.speakerMuted);
					CP.speaker.setRolloverIcon(CP.speakerMutedRollover);
					CP.speaker.setPressedIcon(CP.speakerMutedPressed);
				} else {
					CP.speaker.setIcon(CP.speakermute);
					CP.speaker.setRolloverIcon(CP.speakerrollover);
					CP.speaker.setPressedIcon(CP.speakerpressed);
				}
			} catch (BasicPlayerException e1) {
				e1.printStackTrace();
			}
		}
	}
}
