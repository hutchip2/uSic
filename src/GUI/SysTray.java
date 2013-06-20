package GUI;

import java.awt.AWTException;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SysTray extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1820734607730588346L;
	public SysTray()	{
		if (!SystemTray.isSupported()) {
			System.out.println("SystemTray is not supported");
			return;
		}
		final PopupMenu popup = new PopupMenu();
		ImageIcon logotray = new ImageIcon("Graphics/Control Panel Icons/logotray.png");
		TrayIcon trayIcon = new TrayIcon(logotray.getImage(), "µSic Lite");
		SystemTray tray = SystemTray.getSystemTray();
		Menu displayMenu = new Menu("Display");
		MenuItem aboutItem = new MenuItem("About");
		MenuItem libItem = new MenuItem("Set Library");
		MenuItem errorItem = new MenuItem("Error");
		MenuItem warningItem = new MenuItem("Warning");
		MenuItem infoItem = new MenuItem("Info");
		MenuItem noneItem = new MenuItem("None");
		MenuItem exitItem = new MenuItem("Exit");
		popup.add(aboutItem);
		popup.addSeparator();
		popup.add(libItem);	  
		displayMenu.add(errorItem);
		displayMenu.add(warningItem);
		displayMenu.add(infoItem);
		displayMenu.add(noneItem);
		popup.add(exitItem);  
		aboutItem.addActionListener(this);
		libItem.addActionListener(this);
		errorItem.addActionListener(this);
		warningItem.addActionListener(this);
		infoItem.addActionListener(this);
		noneItem.addActionListener(this);
		exitItem.addActionListener(this);        
		trayIcon.setPopupMenu(popup);       
		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			System.out.println("TrayIcon could not be added.");
		}
	}
	public void launchAbout()	{
		String about = new String("uSic" + "\n" + "-A light weight java music player" + "\n" + 
		"Copyright Paul Hutchinson, 2010");
		JOptionPane.showMessageDialog(null, about, "µSic - About", 0, new ImageIcon("Graphics/uSic2.jpg"));
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("About"))	{
			launchAbout();
		}
		if (e.getActionCommand().equals("Set Library"))	{
			//	uSicPanel.setLib();
		}
		if (e.getActionCommand().equals("Exit"))	{
			System.exit(0);
		}
	}
}
