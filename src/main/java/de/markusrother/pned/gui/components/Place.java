package de.markusrother.pned.gui.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import javax.swing.event.ChangeEvent;

import de.markusrother.pned.control.EventBus;
import de.markusrother.pned.control.commands.MarkingEditCommand;
import de.markusrother.pned.control.commands.PlaceCommandListener;
import de.markusrother.pned.control.events.MarkingChangeEvent;
import de.markusrother.pned.control.events.MarkingEventObject;
import de.markusrother.pned.control.events.PlaceEventListener;
import de.markusrother.pned.gui.PlaceLayout;
import de.markusrother.pned.gui.listeners.MarkingEditor;
import de.markusrother.pned.gui.model.NodeStyleModel;
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
		PlaceCommandListener,
		PlaceEventListener {

	private final Marking marking;
	private final MarkingEditor markingEditor;

	/**
	 * <p>
	 * Constructor for Place.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.control.EventBus} object.
	 * @param placeId
	 *            a {@link java.lang.String} object.
	 * @param marking
	 *            a {@link Marking} object.
	 * @param markingEditor
	 *            a {@link de.markusrother.pned.gui.listeners.MarkingEditor}
	 *            object.
	 * @param markingEditor
	 *            a {@link de.markusrother.pned.gui.listeners.MarkingEditor}
	 *            object.
	 * @param style
	 *            a {@link de.markusrother.pned.gui.model.NodeStyleModel}
	 *            object.
	 */
	public Place(final EventBus eventBus, final String placeId, final Marking marking,
			final MarkingEditor markingEditor, final NodeStyleModel style) {
		super(eventBus, placeId, new PlaceLayout(), style);
		this.marking = marking;
		this.markingEditor = markingEditor;

		markingEditor.addToComponent(this);

		// TODO - dispose!
		eventBus.addListener(PlaceCommandListener.class, this);
		eventBus.addListener(PlaceEventListener.class, this);

		add(this.marking, PlaceLayout.CENTER);
		setOpaque(false);
	}

	/** {@inheritDoc} */
	@Override
	public Dimension getPreferredSize() {
		final int size = style.getSize();
		return new Dimension(size, size);
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
		final Dimension preferredSize = getPreferredSize();
		return new Ellipse2D.Double(0, 0, preferredSize.width, preferredSize.height);
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
	public void setMarking(final MarkingEditCommand evt) {
		delegateSetMarking(evt);
	}

	/** {@inheritDoc} */
	@Override
	public void setMarking(final MarkingChangeEvent evt) {
		delegateSetMarking(evt);
	}

	private void delegateSetMarking(final MarkingEventObject evt) {
		final String myId = getId();
		final String placeId = evt.getPlaceId();
		if (myId.equals(placeId)) {
			marking.setValue(evt.getMarking());
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void installListeners() {
		super.installListeners();
		markingEditor.addToComponent(this);
	}

	/** {@inheritDoc} */
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

	/** {@inheritDoc} */
	@Override
	public void stateChanged(final ChangeEvent e) {
		if (e.getSource() == this.style) {
			setBounds(new Rectangle(getLocation(), getPreferredSize()));
			doLayout();
		} else {
			throw new RuntimeException("Unexpected event source " + e.getSource());
		}
	}

}
