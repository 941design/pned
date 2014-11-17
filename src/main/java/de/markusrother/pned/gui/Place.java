package de.markusrother.pned.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * TODO - on hover create pop up with + / - to add/remove weights
 * 
 * TODO - create floating label next to place, which listens to component move
 * events.
 * 
 * TODO - create generic, pluggable component dragged listener.
 * 
 * TODO - create model {marking, label}
 *
 */
public class Place extends JPanel {

	private static final Color standardColorBG = new Color(120, 120, 120, 120);
	private final Dimension dimension;
	private final JLabel marking;

	public Place(final Dimension dimension) {
		super(new PlaceLayout());
		// TODO - use model instead of label
		this.dimension = dimension;
		this.marking = new JLabel("23");
		add(this.marking, PlaceLayout.CENTER);
	}

	@Override
	public Dimension getPreferredSize() {
		// TODO - when to use getPreferredSize() vs. setPreferredSize()?
		return dimension;
	}

	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		final Graphics2D g2 = (Graphics2D) g;
		// TODO - how to manage node locations?
		// setBackground(standardColorBG);
		setForeground(standardColorBG);
		final Ellipse2D shape = new Ellipse2D.Double(0, 0, dimension.width, dimension.height);
		g2.fill(shape);
	}

}
