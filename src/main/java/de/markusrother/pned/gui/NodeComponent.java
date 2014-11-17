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
class NodeComponent extends JPanel {

	private static final Color nodeColorBG = new Color(120, 120, 120, 120);
	private final Dimension dimension;
	private final JTextField label;

	public NodeComponent(final String label, final Dimension dimension) {
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
		// TODO - how to manage different shapes?
		g.drawRect(0, 0, dimension.width, dimension.height);
		// g.fillRect(0, 0, dimension.width, dimension.height);
		// final Polygon polygon = new Polygon(new int[] { 10, 0, 10, 20 }, new
		// int[] { 0, 10, 20, 10 }, 4);
		// g.fillPolygon(polygon);
		// TODO - draw circle:
		// g.fillArc(flags, flags, flags, flags, flags, flags);
	}

}
