package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import javax.swing.JButton;

public class RoundButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = -22476090290214683L;
	public RoundButton(String label) {
		super(label);
		Dimension size = getPreferredSize();
		setBackground(Color.ORANGE);
		setPreferredSize(size);
		setContentAreaFilled(false);
	}
	Shape shape;
	public boolean contains(int x, int y) {
		if (shape == null || 
				!shape.getBounds().equals(getBounds())) {
			shape = new Ellipse2D.Float(0, 0, 
					getWidth(), getHeight());
		}
		return shape.contains(x, y);
	}
}