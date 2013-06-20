package Logic;

import GUI.ControlPanel;
import GUI.GUI;
import GUI.LibraryPanel;
import GUI.PlaylistPanel;

public class Main {
	public static void main(String args[])	{
		if (GUI.isQuickLoad())	{
			GUI.LibraryLoad();
		}
		LibraryPanel lp = new LibraryPanel();
		ControlPanel cp = new ControlPanel();
		PlaylistPanel pp = new PlaylistPanel();
		new GUI(lp, cp, pp);
	
	}
}


// 241, 192, 56 (lite orange)
// 244, 141, 6  (dark orange)
//  75, 121, 225(blue)