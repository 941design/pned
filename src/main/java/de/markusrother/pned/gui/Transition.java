package de.markusrother.pned.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This component should neither know its location nor its context!
 *
 */
class Transition extends JPanel {

	private static final Color nodeColorBG = new Color(120, 120, 120, 120);
	private final Dimension dimension;
	private final JTextField label;

	public Transition(final String label, final Dimension dimension) {
		// TODO - use model instead of label
		this.dimension = dimension;
		this.label = new JTextField(label);
		// addMouseListener(this);
		// addMouseMotionListener(this);
		add(this.label);
	}

	@Override
	public Dimension getPreferredSize() {
		return dimension;
	}

	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		// TODO - how to manage node locations?
		// setPreferredSize(dimension);
		setBackground(nodeColorBG);
		setForeground(nodeColorBG);
		g.drawRect(0, 0, dimension.width, dimension.height);
	}

}
