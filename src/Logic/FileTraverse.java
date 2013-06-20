package Logic;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JTable;
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
import GUI.LibraryPanel;
import GUI.TableModel;
import GUI.GUI;


public class FileTraverse {
	public static ArrayList<File> list = new ArrayList<File>();
	public static DecimalFormat df = new DecimalFormat("#.##");
	public static double totalSize = 0;
	public static double totalLength = 0;
	static Boolean bool = Boolean.TRUE;

	public static void traverse(File f) throws IOException	{
	//	System.out.println("traversing " +f.getName());
		if (f.isDirectory()) {
			onDirectory(f);
		}
		if (f.isFile())	{
			onFile(f);
		}
	}
	public static void onDirectory(File d) throws IOException {
		final File[] childs = d.listFiles();
		for(int i = 0; i < childs.length; i++) {
			traverse(childs[i]);
		}
	}
	public static void onFile(File f) {
		String s = f.toString();
		if (s.endsWith(".mp3")||s.endsWith(".wav")||s.endsWith(".midi")||s.endsWith(".wma")||s.endsWith(".ogg"))	{
			//System.out.println("adding " + f.getName());
			list.add(f);
		}
	}
	public static void buildTable() throws IOException, CannotReadException, TagException, ReadOnlyFileException	{
		for (int i = 0; i < list.size(); i++)	{
			String current = list.get(i).toString();
			if (current.endsWith(".mp3")||current.endsWith(".wav")||current.endsWith(".midi")||current.endsWith(".wma"))	{	
				try	{
					AudioFile f = AudioFileIO.read((list.get(i)));
					Tag tag = f.getTag();
					AudioHeader d = f.getAudioHeader();		
					double size = (double)list.get(i).length()/(1024*1024);	
					String displaySize = df.format(size);
					if (displaySize.length() == 1 || displaySize.length() == 2)	{
						displaySize = df.format(size) + ".00";
					}
					if (displaySize.length() == 3)	{
						displaySize = df.format(size) + "0";
					}
					//if (displaySize.substring(0, displaySize.indexOf('.')).length() == 1)	{
					//	displaySize = "0" + displaySize;
					//}
				//	if (displaySize.length() == 4)	{
				//		displaySize = displaySize+"0";
				//	}
					String trackLength = d.getTrackLength()/60 + ":" + d.getTrackLength()%60;
					if (trackLength.length() < 4)	{
						trackLength = trackLength.substring(0, 2) + "0" + trackLength.substring(2);
					}
					String genre = "";			//Fixes Genre Codes
					String cur = tag.getFirst(FieldKey.GENRE);
					if (cur.contains("(") && (cur.length() == 3 || cur.length() == 4))	{
					//	genre = uSic.Genres.get(Integer.parseInt(cur.substring(cur.indexOf('(')+1, cur.lastIndexOf(')'))));
						tag.setField(FieldKey.GENRE, genre);	
						try {
							f.commit();
						} catch (CannotWriteException e) {
							e.printStackTrace();
						}
					}
					if (tag.getFirst(FieldKey.TITLE).equals(""))	{
						tag.setField(FieldKey.TITLE, list.get(i).toString().substring(list.get(i).toString().lastIndexOf("\\")+1, list.get(i).toString().length()));
						try {
							f.commit();
						} catch (CannotWriteException e) {
							e.printStackTrace();
						}
					}
					TableModel.data[0][i] = bool;
					TableModel.data[1][i] = tag.getFirst(FieldKey.TITLE);
					TableModel.data[2][i] = trackLength;
					TableModel.data[3][i] = tag.getFirst(FieldKey.ARTIST);
					TableModel.data[4][i] = tag.getFirst(FieldKey.ALBUM);
					TableModel.data[5][i] = tag.getFirst(FieldKey.GENRE);
					TableModel.data[6][i] = displaySize + " MB";	//df.format(size) + " MB";
					totalSize += size;
					totalLength += d.getTrackLength(); 					
				}
				catch (InvalidAudioFrameException e){
					e.printStackTrace();
				}
				catch (NullPointerException e){
					e.printStackTrace();
				}
			//	uSic.updateLibLabel();
			}
		}
	}
	public static ArrayList<String> BuildGenreList()	{
		ArrayList<String> genreCodes = new ArrayList<String>();
		genreCodes.add("Blues");genreCodes.add("Classic Rock");genreCodes.add("Country");
		genreCodes.add("Dance");genreCodes.add("Disco");genreCodes.add("Funk");
		genreCodes.add("Grunge");genreCodes.add("Hip-Hop");genreCodes.add("Jazz");
		genreCodes.add("Metal");genreCodes.add("New Age");genreCodes.add("Oldies");
		genreCodes.add("Other");genreCodes.add("Pop");genreCodes.add("R&B");
		genreCodes.add("Rap");genreCodes.add("Reggae");genreCodes.add("Rock");
		genreCodes.add("Techno");genreCodes.add("Industrial");genreCodes.add("Alternative");
		genreCodes.add("Ska");genreCodes.add("Death Metal");genreCodes.add("Pranks");
		genreCodes.add("Soundtrack");genreCodes.add("Euro-Techno");genreCodes.add("Ambient");
		genreCodes.add("Trip-Hop");genreCodes.add("Vocal");genreCodes.add("Jazz+Funk");
		genreCodes.add("Fusion");genreCodes.add("Trance");genreCodes.add("Classical");
		genreCodes.add("Instrumental");genreCodes.add("Acid");genreCodes.add("House");
		genreCodes.add("Game");genreCodes.add("Sound Clip");genreCodes.add("Gospel");
		genreCodes.add("Noise");genreCodes.add("AlternRock");genreCodes.add("Bass");
		genreCodes.add("Soul");genreCodes.add("Punk");genreCodes.add("Space");
		genreCodes.add("Meditative");genreCodes.add("Instrumental Pop");genreCodes.add("Instrumental Rock");
		genreCodes.add("Ethnic");genreCodes.add("Gothic");genreCodes.add("Darkwave");
		genreCodes.add("Techno-Industrial");genreCodes.add("Electronic");genreCodes.add("Pop-Folk");
		genreCodes.add("Eurodance");genreCodes.add("Dream");genreCodes.add("Southern Rock");
		genreCodes.add("Comedy");genreCodes.add("Cult");genreCodes.add("Gangsta");
		genreCodes.add("Top 40");genreCodes.add("Christian Rap");genreCodes.add("Pop/Funk");
		genreCodes.add("Jungle");genreCodes.add("Native American");genreCodes.add("Cabaret");
		genreCodes.add("New Wave");genreCodes.add("Psychadelic");genreCodes.add("Rave");
		genreCodes.add("Showtunes");genreCodes.add("Trailer");genreCodes.add("Lo-Fi");
		genreCodes.add("Tribal");genreCodes.add("Acid Punk");genreCodes.add("Acid Jazz");
		genreCodes.add("Polka");genreCodes.add("Retro");genreCodes.add("Musical");
		genreCodes.add("Rock & Roll");genreCodes.add("Hard Rock");genreCodes.add("Folk");
		genreCodes.add("Folk-Rock");genreCodes.add("National Folk");genreCodes.add("Swing");
		genreCodes.add("Fast Fusion");genreCodes.add("Bebob");genreCodes.add("Latin");
		genreCodes.add("Revival");genreCodes.add("Celtic");genreCodes.add("Bluegrass");
		genreCodes.add("Avantgarde");genreCodes.add("Gothic Rock");genreCodes.add("Progressive Rock");
		genreCodes.add("Psychedelic Rock");genreCodes.add("Symphonic Rock");genreCodes.add("Slow Rock");
		genreCodes.add("Big Band");genreCodes.add("Chorus");genreCodes.add("Easy Listening");
		genreCodes.add("Acoustic");genreCodes.add("Humour");genreCodes.add("Speech");
		genreCodes.add("Chanson");genreCodes.add("Opera");genreCodes.add("Chamber Music");
		genreCodes.add("Sonata");genreCodes.add("Symphony");genreCodes.add("Booty Bass");
		genreCodes.add("Primus");genreCodes.add("Porn Groove");genreCodes.add("Satire");
		genreCodes.add("Slow Jam");genreCodes.add("Club");genreCodes.add("Tango");
		genreCodes.add("Samba");genreCodes.add("Folklore");genreCodes.add("Ballad");
		genreCodes.add("Power Ballad");genreCodes.add("Rhythmic Soul");genreCodes.add("Freestyle");
		genreCodes.add("Duet");genreCodes.add("Punk Rock");genreCodes.add("Drum Solo");
		genreCodes.add("A capella");genreCodes.add("Euro-House");genreCodes.add("Dance Hall");
		return genreCodes;
	}
	public static void updateRow(JTable source, int row) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException	{
		AudioFile f = null;
		double size = 0;
		if (source == LibraryPanel.table)	{
			f = AudioFileIO.read((list.get(row)));
			size = (double)list.get(row).length()/(1024*1024);
		}	/*else if (source == PlaylistPanel.table2)	{
			f = AudioFileIO.read((PlaylistModel.playlist.get(row)));
			size = (double)PlaylistModel.playlist.get(row).length()/(1024*1024);
		}*/
		
		Tag tag = f.getTag();
		AudioHeader d = f.getAudioHeader();		
		String displaySize = df.format(size);
		if (displaySize.length() == 1 || displaySize.length() == 2)	{
			displaySize = df.format(size) + ".00";
		}
		if (displaySize.length() == 3)	{
			displaySize = df.format(size) + "0";
		}
		if (displaySize.substring(0, displaySize.indexOf('.')).length() == 1)	{
			displaySize = "0" + displaySize;
		}
		if (displaySize.length() == 4)	{
			displaySize = displaySize+"0";
		}
		String trackLength = d.getTrackLength()/60 + ":" + d.getTrackLength()%60;
		if (trackLength.length() < 4)	{
			trackLength = trackLength.substring(0, 2) + "0" + trackLength.substring(2);
		}
		if (source == LibraryPanel.table)	{
			TableModel.data[0][row] = bool; //Boolean.TRUE;
			TableModel.data[1][row] = tag.getFirst(FieldKey.TITLE);
			TableModel.data[2][row] = trackLength;
			TableModel.data[3][row] = tag.getFirst(FieldKey.ARTIST);
			TableModel.data[4][row] = tag.getFirst(FieldKey.ALBUM);
			TableModel.data[5][row] = tag.getFirst(FieldKey.GENRE);
			TableModel.data[6][row] = displaySize + " MB";	//df.format(size) + " MB";
		}	/*else if (source == PlaylistPanel.table2)	{
			PlaylistModel.playdata[0][row] = bool; //Boolean.TRUE;
			PlaylistModel.playdata[1][row] = tag.getFirst(FieldKey.TITLE);
			PlaylistModel.playdata[2][row] = trackLength;
			PlaylistModel.playdata[3][row] = tag.getFirst(FieldKey.ARTIST);
			PlaylistModel.playdata[4][row] = tag.getFirst(FieldKey.ALBUM);
			PlaylistModel.playdata[5][row] = tag.getFirst(FieldKey.GENRE);
			PlaylistModel.playdata[6][row] = displaySize + " MB";	//df.format(size) + " MB";
		}*/
	}
	public String[] listLib()	{
		String filenames[] = new String[FileTraverse.list.size()];
		for (int i = 0; i < FileTraverse.list.size(); i++){
			filenames[i] = FileTraverse.list.get(i).toString();
		}
		return filenames;
	}
	public int getTrackLengthSec() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException	{
		String[] lib = listLib();
		int modelRow = LibraryPanel.table.convertRowIndexToModel(LibraryPanel.table.getSelectedRow());
		AudioFile f = AudioFileIO.read(new File(lib[modelRow]));
		AudioHeader d = f.getAudioHeader();		
		return d.getTrackLength();
	}
	public void editTags(JTable source) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException	{				
		String[] lib = listLib();
//		String[] playlistLib = PlaylistPanel.listLib();
		if (source.getSelectedRowCount() < 2)	{
			int modelRow = source.convertRowIndexToModel(source.getSelectedRow());
			AudioFile f = null;
			if (source == LibraryPanel.table){
				f = AudioFileIO.read(new File(lib[modelRow]));
			}
	/*		if (source == PlaylistPanel.table2){
				f = AudioFileIO.read(new File(playlistLib[modelRow]));
			}*/
			Tag tag = f.getTag();
			tag.setField(FieldKey.TITLE, GUI.titleEdit.getText());
			tag.setField(FieldKey.ARTIST, GUI.artistEdit.getText());
			tag.setField(FieldKey.ALBUM, GUI.albumEdit.getText());
			tag.setField(FieldKey.GENRE, GUI.genreEdit.getText());
			f.commit();
			updateRow(source,modelRow);	
		} else	{
			int rows[] = source.getSelectedRows();
			ArrayList<Integer> modelRows = new ArrayList<Integer>();
			for (int i = 0; i < rows.length; i++)	{
				modelRows.add(source.convertRowIndexToModel(rows[i]));
			}
			if (source == LibraryPanel.table){
				AudioFile f = null;
				for (int i = 0; i < modelRows.size(); i++)	{
					f = AudioFileIO.read(new File(lib[modelRows.get(i)]));
					Tag tag = f.getTag();
					if (GUI.titleEdit.getText().length() > 0)	{
						tag.setField(FieldKey.TITLE, GUI.titleEdit.getText());
					}
					if (GUI.artistEdit.getText().length() > 0)	{
						tag.setField(FieldKey.ARTIST, GUI.artistEdit.getText());
					}
					if (GUI.albumEdit.getText().length() > 0)	{
						tag.setField(FieldKey.ALBUM, GUI.albumEdit.getText());
					}
					if (GUI.genreEdit.getText().length() > 0)	{
						tag.setField(FieldKey.GENRE, GUI.genreEdit.getText());
					}
					f.commit();
					updateRow(source,modelRows.get(i));	
				}
			}
		/*	if (source == PlaylistPanel.table2){
				AudioFile f = null;
				for (int i = 0; i < modelRows.size(); i++)	{
					f = AudioFileIO.read(new File(playlistLib[modelRows.get(i)]));
					Tag tag = f.getTag();
					if (uSicPanel.titleEdit.getText().length() > 0)	{
						tag.setField(FieldKey.TITLE, uSicPanel.titleEdit.getText());
					}
					if (uSicPanel.artistEdit.getText().length() > 0)	{
						tag.setField(FieldKey.ARTIST, uSicPanel.artistEdit.getText());
					}
					if (uSicPanel.albumEdit.getText().length() > 0)	{
						tag.setField(FieldKey.ALBUM, uSicPanel.albumEdit.getText());
					}
					if (uSicPanel.genreEdit.getText().length() > 0)	{
						tag.setField(FieldKey.GENRE, uSicPanel.genreEdit.getText());
					}
					f.commit();
					updateRow(source,modelRows.get(i));	
				}
			}*/
		}
	}
	public static String getPath(File f) throws IOException	{
		//Compares first HALF of song name
		String s = TableModel.data[1][LibraryPanel.table.getSelectedRow()].toString().substring(0, TableModel.data[1][LibraryPanel.table.getSelectedRow()].toString().length()/2);
		String path = null;
		if (f.isDirectory()){
			final File[] childs = f.listFiles();
			for(int i = 0; i < childs.length; i++) {
				if (childs[i].toString().contains(s))	{
					path = childs[i].getCanonicalPath();
				}
				getPath(childs[i]);
			}
		}
		return path;
	}
}
