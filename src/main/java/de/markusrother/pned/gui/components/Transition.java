package de.markusrother.pned.gui.components;

import static de.markusrother.util.TrigUtils.modPi;
import static java.lang.Math.PI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Point2D;

import de.markusrother.pned.core.control.EventBus;
import de.markusrother.pned.core.events.TransitionActivationEvent;
import de.markusrother.pned.core.listeners.TransitionActivationListener;
import de.markusrother.pned.gui.layout.commands.TransitionLayoutCommand;
import de.markusrother.pned.gui.layout.listeners.TransitionLayoutListener;
import de.markusrother.pned.gui.layout.style.NodeStyle;
import de.markusrother.pned.gui.listeners.TransitionActivator;
import de.markusrother.util.JsonBuilder;

/**
 * <p>
 * Transition class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class Transition extends AbstractNode
	implements
		TransitionActivationListener,
		TransitionLayoutListener {

	/** Constant <code>activatedColor</code> */
	private static final Color activatedColor = Color.GREEN;
	/** Constant <code>deactivatedColor</code> */
	private static final Color deactivatedColor = Color.GRAY;

	private int extent;
	private final NodeStyle style = NodeStyle.DEFAULT;
	private final TransitionActivator activator;
	private boolean isActive = true;

	/**
	 * <p>
	 * Constructor for Transition.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.core.control.EventBus} object.
	 * @param extent
	 *            a int.
	 */
	public Transition(final EventBus eventBus, final int extent) {
		super(eventBus);
		// TODO - use model!
		this.extent = extent;
		setOpaque(false);

		this.activator = new TransitionActivator(eventBus);
		activator.addToComponent(this);

		// FIXME - dispose!
		eventBus.addListener(TransitionLayoutListener.class, this);
		eventBus.addListener(TransitionActivationListener.class, this);
	}

	/** {@inheritDoc} */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(extent, extent);
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
		return new Rectangle(0, 0, extent, extent);
	}

	/** {@inheritDoc} */
	@Override
	public Point2D getBoundaryPoint(final double theta) {
		final double t = modPi(theta); // -PI <= t <= PI
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
	protected NodeStyle getStyle() {
		return style;
	}

	/** {@inheritDoc} */
	@Override
	public void setSize(final TransitionLayoutCommand cmd) {
		this.extent = cmd.getSize();
		setSize(new Dimension(extent, extent));
		repaint(); // REDUNDANT
	}

	/** {@inheritDoc} */
	@Override
	public void transitionActivated(final TransitionActivationEvent e) {
		final String myId = getId();
		if (myId.equals(e.getTransitionId())) {
			isActive = true;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void transitionDeactivated(final TransitionActivationEvent e) {
		final String myId = getId();
		if (myId.equals(e.getTransitionId())) {
			isActive = false;
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

}
