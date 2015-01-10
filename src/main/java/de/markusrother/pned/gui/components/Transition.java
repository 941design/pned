package de.markusrother.pned.gui.components;

import static de.markusrother.util.MathUtils.moduloPi;
import static java.lang.Math.PI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Point2D;

import javax.swing.event.ChangeEvent;

import de.markusrother.pned.control.EventBus;
import de.markusrother.pned.control.events.TransitionActivationEvent;
import de.markusrother.pned.control.events.TransitionActivationListener;
import de.markusrother.pned.gui.components.listeners.TransitionActivator;
import de.markusrother.pned.gui.core.model.NodeStyleModel;
import de.markusrother.util.JsonBuilder;

/**
 * <p>
 * Transition class.
 * </p>
 * 
 * TODO - apply TransitionStyle
 *
 * @author Markus Rother
 * @version 1.0
 */
public class Transition extends AbstractNode
	implements
		TransitionActivationListener {

	// TODO - move to TransitionStyleModel

	/** Constant <code>activatedColor</code> */
	private static final Color activatedColor = Color.GREEN;
	/** Constant <code>deactivatedColor</code> */
	private static final Color deactivatedColor = Color.GRAY;

	private final TransitionActivator activator;
	private boolean isActive = true;

	/**
	 * <p>
	 * Constructor for Transition.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.control.EventBus} object.
	 * @param transitionId
	 *            a {@link java.lang.String} object.
	 * @param style
	 *            a {@link de.markusrother.pned.gui.core.model.NodeStyleModel}
	 *            object.
	 */
	public Transition(final EventBus eventBus, final String transitionId, final NodeStyleModel style) {
		super(eventBus, transitionId, style);
		setOpaque(false);

		this.activator = new TransitionActivator(eventBus);
		activator.addToComponent(this);

		// TODO - dispose and assert removal of listeners!
		eventBus.addListener(TransitionActivationListener.class, this);
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
		// setPreferredSize(dimension);
		if (isActive) {
			g2.setColor(activatedColor);
		} else {
			g2.setColor(deactivatedColor);
		}
		g2.fill(getShape());
	}

	/** {@inheritDoc} */
	@Override
	protected Shape getShape() {
		return getRectangle();
	}

	/**
	 * <p>
	 * getRectangle.
	 * </p>
	 *
	 * @return a {@link java.awt.Rectangle} object.
	 */
	private Rectangle getRectangle() {
		final Dimension preferredSize = getPreferredSize();
		return new Rectangle(0, 0, preferredSize.width, preferredSize.height);
	}

	/** {@inheritDoc} */
	@Override
	public Point2D getBoundaryPoint(final double theta) {
		final double t = moduloPi(theta); // -PI <= t <= PI
		// TODO - Assumes that rectangle origin is 0,0!
		final Rectangle r = getRectangle();
		final double absTheta = Math.abs(t);
		if (absTheta <= PI * 0.25) {
			// theta intersects with right vertical.
			return new Point2D.Double(r.width, r.height * (1 + Math.tan(theta)) / 2.0);
		} else if (absTheta >= PI * 0.75) {
			// theta intersects with left vertical.
			return new Point2D.Double(0, r.height * (1 - Math.tan(theta)) / 2.0);
		} else if (t > 0) {
			// theta intersects with top horizontal.
			return new Point2D.Double(r.width * (1 - Math.cos(theta + PI)) / 2.0, r.height);
		} else if (t < 0) {
			// theta intersects with bottom horizontal.
			return new Point2D.Double(r.width * (1 - Math.cos(theta + PI)) / 2.0, 0);
		} else {
			throw new IllegalStateException();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void transitionActivated(final TransitionActivationEvent e) {
		final String myId = getId();
		if (myId.equals(e.getTransitionId()) && !isActive) {
			isActive = true;
			repaint();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void transitionDeactivated(final TransitionActivationEvent e) {
		final String myId = getId();
		if (myId.equals(e.getTransitionId()) && isActive) {
			isActive = false;
			repaint();
		}
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
				.append("isActive", isActive) //
				.toString();
	}

	/** {@inheritDoc} */
	@Override
	public void stateChanged(final ChangeEvent e) {
		if (e.getSource() == this.style) {
			setBounds(new Rectangle(getLocation(), getPreferredSize()));
		} else {
			throw new RuntimeException("Unexpected event source " + e.getSource());
		}
	}
}
