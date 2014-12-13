package de.markusrother.pned.gui.components;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Marking extends JPanel {

	private final MarkingStyle style;
	private int value;
	private final JLabel label;

	Marking() {
		this.style = MarkingStyle.DEFAULT;
		this.label = new JLabel();
		add(label);
		setOpaque(false);
	}

	@Override
	protected void paintComponent(final Graphics g) {
		label.setVisible(value > 1);
		g.setColor(style.getColor());
		super.paintComponent(g);
		if (value == 1) {
			final Graphics2D g2 = (Graphics2D) g;
			g2.fill(style.getShape());
		}
	}

	public int getValue() {
		return value;
	}

	public void setValue(final int value) {
		if (this.value != value) {
			this.value = value;
			label.setText(String.valueOf(value));
			repaint();
		}
	}

}
