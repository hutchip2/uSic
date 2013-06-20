package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalSliderUI;

public class ControlPanel extends JPanel implements ActionListener, MouseWheelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7511509059910514891L;
	public static RoundButton pp = new RoundButton("");
	public static RoundButton shuffle = new RoundButton("");
	public static RoundButton loop = new RoundButton("");
	RoundButton rw = new RoundButton("");
	RoundButton ff = new RoundButton("");
	RoundButton plusButton = new RoundButton("");
	RoundButton minusButton = new RoundButton("");
	RoundButton speaker = new RoundButton("");
	RoundButton goBack = new RoundButton("");
	static RoundButton goForward = new RoundButton("");
	public static JSlider volume = new JSlider(0, 100, 100);
	public static JSlider seek = new JSlider(0, 100, 100);
	public static ImageIcon play = new ImageIcon("Graphics/Control Panel Icons/play.png");
	public static ImageIcon playrollover = new ImageIcon("Graphics/Control Panel Icons/playglow.png");
	public static ImageIcon playpressed = new ImageIcon("Graphics/Control Panel Icons/playpressed.png");
	public static ImageIcon pause = new ImageIcon("Graphics/Control Panel Icons/pause.png");
	public static ImageIcon pausepressed = new ImageIcon("Graphics/Control Panel Icons/pausepressed.png");
	public static ImageIcon pauserollover = new ImageIcon("Graphics/Control Panel Icons/pauserollover.png");
	ImageIcon repeaticon = new ImageIcon("Graphics/Control Panel Icons/repeat.png");
	public static ImageIcon shuffleicon = new ImageIcon("Graphics/Control Panel Icons/shuffle.png");
	ImageIcon shufflerollover = new ImageIcon("Graphics/Control Panel Icons/shuffleglow.png");
	ImageIcon shufflepressed= new ImageIcon("Graphics/Control Panel Icons/shufflepressed.png");
	ImageIcon shuffleOff = new ImageIcon("Graphics/Control Panel Icons/shuffleoff.png");
	ImageIcon shuffleOffRollover = new ImageIcon("Graphics/Control Panel Icons/shuffleoffrollover.png");
	ImageIcon shuffleOffPressed = new ImageIcon("Graphics/Control Panel Icons/shuffleoffpressed.png");
	public static ImageIcon loopOnce = new ImageIcon("Graphics/Control Panel Icons/loopone.png");
	public static ImageIcon loopAll = new ImageIcon("Graphics/Control Panel Icons/loopall.png");
	ImageIcon loopOncerollover = new ImageIcon("Graphics/Control Panel Icons/looponeglow.png");
	ImageIcon loopOncepressed = new ImageIcon("Graphics/Control Panel Icons/looponepressed.png");
	ImageIcon loopAllrollover = new ImageIcon("Graphics/Control Panel Icons/loopallglow.png");
	ImageIcon loopAllpressed = new ImageIcon("Graphics/Control Panel Icons/loopallpressed.png");
	ImageIcon loopOff = new ImageIcon("Graphics/Control Panel Icons/loopoff.png");
	ImageIcon loopOffRollover = new ImageIcon("Graphics/Control Panel Icons/loopoffrollover.png");
	ImageIcon loopOffPressed = new ImageIcon("Graphics/Control Panel Icons/loopoffpressed.png");
	ImageIcon rwicon = new ImageIcon("Graphics/Control Panel Icons/skipback.png");
	ImageIcon fficon = new ImageIcon("Graphics/Control Panel Icons/skipforward.png");
	ImageIcon rwrollover = new ImageIcon("Graphics/Control Panel Icons/skipbackglow.png");
	ImageIcon ffpressed = new ImageIcon("Graphics/Control Panel Icons/skipforwardpressed.png");
	ImageIcon ffrollover = new ImageIcon("Graphics/Control Panel Icons/skipforwardglow.png");
	ImageIcon rwpressed = new ImageIcon("Graphics/Control Panel Icons/skipbackpressed.png");
	ImageIcon plus = new ImageIcon("Graphics/Control Panel Icons/plusshrunk.png");
	ImageIcon minus = new ImageIcon("Graphics/Control Panel Icons/speakermuteshrunk.png");
	ImageIcon speakermute = new ImageIcon("Graphics/Control Panel Icons/speakermuteshrunk.png");
	ImageIcon speakerrollover = new ImageIcon("Graphics/Control Panel Icons/speakermuteshrunkrollover.png");
	ImageIcon speakerpressed = new ImageIcon("Graphics/Control Panel Icons/speakermuteshrunkpressed.png");
	ImageIcon speakerMuted = new ImageIcon("Graphics/Control Panel Icons/speakermuted.png");
	ImageIcon speakerMutedRollover = new ImageIcon("Graphics/Control Panel Icons/speakermutedrollover.png");
	ImageIcon speakerMutedPressed = new ImageIcon("Graphics/Control Panel Icons/speakermutedpressed.png");
	ImageIcon plusrollover = new ImageIcon("Graphics/Control Panel Icons/plusshrunkrollover.png");
	ImageIcon pluspressed = new ImageIcon("Graphics/Control Panel Icons/plusshrunkpressed.png");
	ImageIcon gobackward = new ImageIcon("Graphics/Control Panel Icons/gobackwardshrunk.png");
	ImageIcon goforward = new ImageIcon("Graphics/Control Panel Icons/goforwardshrunk.png");
	ImageIcon gobackwardrollover = new ImageIcon("Graphics/Control Panel Icons/gobackwardshrunkrollover.png");
	ImageIcon gobackwardpressed = new ImageIcon("Graphics/Control Panel Icons/gobackwardshrunkpressed.png");
	ImageIcon goforwardrollover = new ImageIcon("Graphics/Control Panel Icons/goforwardshrunkrollover.png");
	ImageIcon goforwardpressed = new ImageIcon("Graphics/Control Panel Icons/goforwardshrunkpressed.png");
	JPanel center = new JPanel();
	JPanel east = new JPanel();
	JPanel west = new JPanel();
	public JLabel artwork = new JLabel();

	public ControlPanel()	{
		SetupPanels();
		SetupBackgrounds();
		SetupButtons();
		SetupIcons();
		SetupRolloverIcons();
		SetupPressedIcons();
		SetupLabels();
		SetupListener();
		SetupSizes();
	//	SetupSliders();
	}
	private void SetupListener()	{
		pp.addActionListener(this);
		rw.addActionListener(this);
		ff.addActionListener(this);
		shuffle.addActionListener(this);
		loop.addActionListener(this);
	//	volume.addChangeListener(this);
		volume.addMouseWheelListener(this);
		speaker.addActionListener(this);
	//	speaker.addChangeListener(this);
		plusButton.addActionListener(this);
		goBack.addActionListener(this);
		//goForward.addActionListener(this);
	//	goForward.addChangeListener(this);
		goForward.addActionListener(this);
	}
	private void SetupRolloverIcons()	{
		pp.setRolloverIcon(playrollover);
		rw.setRolloverIcon(rwrollover);
		ff.setRolloverIcon(ffrollover);
		loop.setRolloverIcon(loopOffRollover);
		shuffle.setRolloverIcon(shuffleOffRollover);
		speaker.setRolloverIcon(speakerrollover);
		plusButton.setRolloverIcon(plusrollover);
		goBack.setRolloverIcon(gobackwardrollover);
		goForward.setRolloverIcon(goforwardrollover);
	}
	private void SetupPressedIcons()	{
		ff.setPressedIcon(ffpressed);
		rw.setPressedIcon(rwpressed);
		pp.setPressedIcon(playpressed);
		rw.setPressedIcon(rwpressed);
		ff.setPressedIcon(ffpressed);
		loop.setPressedIcon(loopOffPressed);
		shuffle.setPressedIcon(shuffleOffPressed);
		speaker.setPressedIcon(speakerpressed);
		plusButton.setPressedIcon(pluspressed);
		goBack.setPressedIcon(gobackwardpressed);
		goForward.setPressedIcon(goforwardpressed);
	}
	private void SetupButtons()	{
		pp.setOpaque(false);
		pp.setBorderPainted(false);
		pp.setFocusPainted(false);
		rw.setOpaque(false);
		rw.setBorderPainted(false);
		rw.setFocusPainted(false);
		ff.setOpaque(false);
		ff.setBorderPainted(false);
		ff.setFocusPainted(false);
		loop.setOpaque(false);
		loop.setBorderPainted(false);
		loop.setFocusPainted(false);
		shuffle.setOpaque(false);
		shuffle.setBorderPainted(false);
		shuffle.setFocusPainted(false);
		plusButton.setOpaque(false);
		plusButton.setBorderPainted(false);
		plusButton.setFocusPainted(false);
		minusButton.setOpaque(false);
		minusButton.setBorderPainted(false);
		minusButton.setFocusPainted(false);
		speaker.setOpaque(false);
		speaker.setBorderPainted(false);
		speaker.setFocusPainted(false);
		goBack.setOpaque(false);
		goBack.setBorderPainted(false);
		goBack.setFocusPainted(false);
		goForward.setOpaque(false);
		goForward.setBorderPainted(false);
		goForward.setFocusPainted(false);
	}
	private void SetupIcons()	{
		pp.setIcon(play);
		rw.setIcon(rwicon);
		ff.setIcon(fficon);
		loop.setIcon(loopOff);
		shuffle.setIcon(shuffleOff);
		plusButton.setIcon(plus);
		minusButton.setIcon(minus);
		speaker.setIcon(speakermute);
		goBack.setIcon(gobackward);
		goForward.setIcon(goforward);
		seek.setValue(0);
		volume.setOrientation(0);
	}
	private void SetupPanels()	{
		Dimension dim1 = new Dimension(220,5);
		setLayout(new BorderLayout());
		center.setSize(1011, 110);
		center.setVisible(true);
		center.setLayout(new FlowLayout(1, 0, -15));
		center.add(loop);
		center.add(rw);
		center.add(pp);
		center.add(ff);
		center.add(shuffle);
		west.add(goBack);
		west.add(seek);
		west.add(goForward);
		east.add(speaker);
		east.add(volume);
		east.add(plusButton);
		east.setPreferredSize(dim1);
		west.setPreferredSize(dim1);
		add(center, BorderLayout.CENTER);
		add(east, BorderLayout.EAST);
		add(west, BorderLayout.WEST);
	}
	private void SetupLabels()	{
		speaker.setToolTipText("Mute");
		plusButton.setToolTipText("Max");
	}
	private void SetupSizes()	{
		Dimension size1 = new Dimension(110,120);
		Dimension size2 = new Dimension(110,110);
		Dimension size3 = new Dimension(40, 40);
		pp.setPreferredSize(size1);
		ff.setPreferredSize(size2);
		rw.setPreferredSize(size2);
		shuffle.setPreferredSize(size2);
		loop.setPreferredSize(size2);
		plusButton.setPreferredSize(size3);
		minusButton.setPreferredSize(size3);
		speaker.setPreferredSize(size3);
		goBack.setPreferredSize(size3);
		goForward.setPreferredSize(size3);
		setPreferredSize(new Dimension(300,110));
		//volume.setPreferredSize(new Dimension(125,110));
		volume.setPreferredSize(new Dimension(125,100));
		seek.setPreferredSize(new Dimension(125, 100));
		UIManager.put("Slider.altTrackColor", new Color(241, 192, 56));
	}
	private void SetupBackgrounds()	{
		volume.setBackground(Color.WHITE);
		center.setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		east.setBackground(Color.WHITE);
		west.setBackground(Color.WHITE);
		seek.setBackground(Color.WHITE);
	}
	@SuppressWarnings("unused")
	private void SetupSliders()	{
		seek.setUI(new MetalSliderUI() {
			protected void scrollDueToClickInTrack(int direction) {
				int value = slider.getValue(); 
				if (slider.getOrientation() == JSlider.HORIZONTAL) {
					value = this.valueForXPosition(slider.getMousePosition().x);
				} else if (slider.getOrientation() == JSlider.VERTICAL) {
					value = this.valueForYPosition(slider.getMousePosition().y);
				}
				slider.setValue(value);				
			}			
		});

		volume.setUI(new MetalSliderUI() {
			protected void scrollDueToClickInTrack(int direction) {
				int value = slider.getValue(); 
				if (slider.getOrientation() == JSlider.HORIZONTAL) {
					value = this.valueForXPosition(slider.getMousePosition().x);
				} else if (slider.getOrientation() == JSlider.VERTICAL) {
					value = this.valueForYPosition(slider.getMousePosition().y);
				}
				slider.setValue(value);
			}
		});
	}
	
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == speaker)	{
			//Player.setGain(0); MUTES
			volume.setValue(0);
		}
		if (e.getSource() == plusButton){
			volume.setValue(100);
		}
		if (e.getSource() == goBack)	{
			//System.out.println("going back");
			//seek.setValue(seek.getValue()-5);
		}
		if (e.getSource() == goForward)	{
			//System.out.println("going for");
			//seek.setValue(seek.getValue()+5);			
		}
		if (e.getSource() == pp)	{
		/*	if (pp.getIcon() == play)	{
				pp.setIcon(pause);
				pp.setRolloverIcon(pauserollover);
				pp.setPressedIcon(pausepressed);
			} else	if (pp.getIcon() == pause){
				pp.setIcon(play);
				pp.setRolloverIcon(playrollover);
				pp.setPressedIcon(playpressed);
			}*/
		}
		if (e.getSource() == loop)	{
			if (loop.getIcon() == loopAll){
				loop.setIcon(loopOnce);
				loop.setRolloverIcon(loopOncerollover);
				loop.setPressedIcon(loopOncepressed);
			} else if (loop.getIcon() == loopOnce){
				loop.setIcon(loopOff);
				loop.setRolloverIcon(loopOffRollover);
				loop.setPressedIcon(loopOffPressed);
			} else if (loop.getIcon() == loopOff)	{
				loop.setIcon(loopAll);
				loop.setRolloverIcon(loopAllrollover);
				loop.setPressedIcon(loopAllpressed);
			}
		}
		if (e.getSource() == shuffle)	{
			if (shuffle.getIcon() == shuffleicon)	{
				shuffle.setIcon(shuffleOff);
				shuffle.setRolloverIcon(shuffleOffRollover);
				shuffle.setPressedIcon(shuffleOffPressed);
			} else if (shuffle.getIcon() == shuffleOff)	{
				shuffle.setIcon(shuffleicon);
				shuffle.setRolloverIcon(shufflerollover);
				shuffle.setPressedIcon(shufflepressed);
			}
		}
	}
	
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getWheelRotation() < 0)	{
			volume.setValue(volume.getValue() + 2);
		}
		if (e.getWheelRotation() > 0)	{
			volume.setValue(volume.getValue() - 2);
		}
	}
}
