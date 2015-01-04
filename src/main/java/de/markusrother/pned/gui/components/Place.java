package de.markusrother.pned.gui.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import de.markusrother.pned.core.control.EventBus;
import de.markusrother.pned.core.events.PlaceEventObject;
import de.markusrother.pned.core.listeners.PlaceListener;
import de.markusrother.pned.gui.PlaceLayout;
import de.markusrother.pned.gui.layout.commands.PlaceLayoutCommand;
import de.markusrother.pned.gui.layout.listeners.PlaceLayoutListener;
import de.markusrother.pned.gui.layout.style.NodeStyle;
import de.markusrother.pned.gui.listeners.MarkingEditor;
import de.markusrother.util.JsonBuilder;

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
 * @author Markus Rother
 * @version 1.0
 */
public class Place extends AbstractNode
	implements
		PlaceListener,
		PlaceLayoutListener {

	private final Marking marking;
	private final MarkingEditor markingEditor;
	private final NodeStyle style = NodeStyle.DEFAULT;

	private int diameter;

	/**
	 * <p>
	 * Constructor for Place.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.core.control.EventBus} object.
	 * @param diameter
	 *            a int.
	 * @param markingEditor
	 *            a {@link de.markusrother.pned.gui.listeners.MarkingEditor}
	 *            object.
	 */
	public Place(final EventBus eventBus, final MarkingEditor markingEditor, final int diameter) {
		super(eventBus, new PlaceLayout());

		this.diameter = diameter;
		this.marking = new Marking(eventBus);
		this.markingEditor = markingEditor;

		markingEditor.addToComponent(this);

		// FIXME - dispose!
		eventBus.addListener(PlaceLayoutListener.class, this);
		eventBus.addListener(PlaceListener.class, this);

		add(this.marking, PlaceLayout.CENTER);
		setOpaque(false);
	}

	/** {@inheritDoc} */
	@Override
	public Dimension getPreferredSize() {
		// TODO - when to use getPreferredSize() vs. setPreferredSize()?
		return new Dimension(diameter, diameter);
	}

	/** {@inheritDoc} */
	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		final Graphics2D g2 = (Graphics2D) g;
		// TODO - how to manage node locations?
		g2.fill(getShape());
	}

	/** {@inheritDoc} */
	@Override
	protected Shape getShape() {
		return getEllipse();
	}

	/**
	 * <p>
	 * getEllipse.
	 * </p>
	 *
	 * @return a {@link java.awt.geom.Ellipse2D} object.
	 */
	private Ellipse2D getEllipse() {
		return new Ellipse2D.Double(0, 0, diameter, diameter);
	}

	/** {@inheritDoc} */
	@Override
	public Point2D getBoundaryPoint(final double theta) {
		final Ellipse2D ellipse = getEllipse();
		// TODO - assumes that ellipse is circle
		final double r = ellipse.getWidth() / 2.0;
		return new Point2D.Double( //
				r * (1 + Math.cos(theta)), //
				r * (1 + Math.sin(theta)));
	}

	/**
	 * <p>
	 * Getter for the field <code>marking</code>.
	 * </p>
	 *
	 * @return a int.
	 */
	public int getMarking() {
		return marking.getValue();
	}

	/** {@inheritDoc} */
	@Override
	public void setMarking(final PlaceEventObject evt) {
		final String myId = getId();
		final String placeId = evt.getPlaceId();
		if (myId.equals(placeId)) {
			marking.setValue(evt.getMarking());
		}
	}

	/** {@inheritDoc} */
	@Override
	protected NodeStyle getStyle() {
		return style;
	}

	/** {@inheritDoc} */
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

	@Override
	protected void installListeners() {
		super.installListeners();
		markingEditor.addToComponent(this);
	}

	@Override
	protected void suspendListeners() {
		super.suspendListeners();
		markingEditor.removeFromComponent(this);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return toJson();
	}

	/** {@inheritDoc} */
	@Override
	public String toJson() {
		final JsonBuilder builder = new JsonBuilder();
		return builder.append("id", getId()) //
				.append("marking", getMarking()) //
				.toString();
	}

}
