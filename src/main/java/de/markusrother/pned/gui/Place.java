package de.markusrother.pned.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

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
public class Place extends AbstractNode {

	private static final Color defaultColor = new Color(120, 120, 120, 120);

	private final Dimension dimension;
	private final JLabel marking;

	public Place(final Dimension dimension) {
		super(new PlaceLayout());
		// TODO - use model instead of label
		this.dimension = dimension;
		this.marking = new JLabel("23");
		add(this.marking, PlaceLayout.CENTER);
		setOpaque(false);
		addMouseListener(PlaceEditListener.INSTANCE);
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
		setForeground(defaultColor);
		g2.fill(getShape());
	}

	@Override
	protected Shape getShape() {
		return getEllipse();
	}

	@Override
	protected void setLayout(final State state) {
		switch (state) {
		case DEFAULT:
			setForeground(defaultColor);
			setBorder(null);
			break;
		case HOVER:
			setForeground(Color.BLUE);
			setBorder(new LineBorder(Color.GREEN));
			break;
		case SELECTED:
			setBorder(new LineBorder(Color.MAGENTA));
			break;
		default:
			throw new IllegalStateException();
		}
		repaint();
	}

	private Ellipse2D getEllipse() {
		return new Ellipse2D.Double(0, 0, dimension.width, dimension.height);
	}

	@Override
	public Point2D getIntersectionWithBounds(final double theta) {
		final Ellipse2D ellipse = getEllipse();
		// TODO - assumes that ellipse is circle
		final double r = ellipse.getWidth() / 2.0;
		return new Point2D.Double( //
				r * (1 + Math.cos(theta)), //
				r * (1 + Math.sin(theta)));
	}

	protected void setMarking(final String string) {
		this.marking.setText(string);
	}
}
