package de.markusrother.pned.gui.components;

import static de.markusrother.pned.gui.components.PnGridPanel.eventBus;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import de.markusrother.pned.commands.PlaceLayoutCommand;
import de.markusrother.pned.commands.listeners.PlaceLayoutListener;
import de.markusrother.pned.gui.PlaceLayout;
import de.markusrother.pned.gui.listeners.MarkingEditListener;

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
public class Place extends AbstractNode
	implements
		PlaceLayoutListener {

	private final Marking marking;

	private int diameter;
	private final NodeStyle style = NodeStyle.DEFAULT;

	public Place(final int diameter) {
		super(new PlaceLayout());
		// TODO - use model instead of label
		this.diameter = diameter;
		this.marking = new Marking();
		add(this.marking, PlaceLayout.CENTER);
		setOpaque(false);
		addMouseListener(MarkingEditListener.INSTANCE);
		// FIXME - dispose!
		eventBus.addListener(PlaceLayoutListener.class, this);
	}

	@Override
	public Dimension getPreferredSize() {
		// TODO - when to use getPreferredSize() vs. setPreferredSize()?
		return new Dimension(diameter, diameter);
	}

	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		final Graphics2D g2 = (Graphics2D) g;
		// TODO - how to manage node locations?
		g2.fill(getShape());
	}

	@Override
	protected Shape getShape() {
		return getEllipse();
	}

	private Ellipse2D getEllipse() {
		return new Ellipse2D.Double(0, 0, diameter, diameter);
	}

	@Override
	public Point2D getBoundaryPoint(final double theta) {
		final Ellipse2D ellipse = getEllipse();
		// TODO - assumes that ellipse is circle
		final double r = ellipse.getWidth() / 2.0;
		return new Point2D.Double( //
				r * (1 + Math.cos(theta)), //
				r * (1 + Math.sin(theta)));
	}

	public int getMarking() {
		return marking.getValue();
	}

	public void setMarking(final int value) {
		marking.setValue(value);
	}

	@Override
	protected NodeStyle getStyle() {
		return style;
	}

	@Override
	public void setSize(final PlaceLayoutCommand cmd) {
		this.diameter = cmd.getSize();
		setSize(new Dimension(diameter, diameter));
		// TODO - Are there any children to be validated?
		// for (final Component child : getComponents()) {
		// child.revalidate();
		// }
		repaint(); // REDUNDANT
	}

}
