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

import de.markusrother.pned.commands.TransitionLayoutCommand;
import de.markusrother.pned.commands.listeners.TransitionActivationListener;
import de.markusrother.pned.commands.listeners.TransitionLayoutListener;
import de.markusrother.pned.events.TransitionActivationEvent;
import de.markusrother.pned.gui.EventBus;

/**
 *
 */
public class Transition extends AbstractNode
	implements
		TransitionActivationListener,
		TransitionLayoutListener {

	private int extent;
	private final NodeStyle style = NodeStyle.DEFAULT;
	private boolean isActive = true;

	public Transition(final EventBus eventBus, final int extent) {
		super(eventBus);
		// TODO - use model!
		this.extent = extent;
		setOpaque(false);
		// FIXME - dispose!
		eventBus.addListener(TransitionLayoutListener.class, this);
		eventBus.addListener(TransitionActivationListener.class, this);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(extent, extent);
	}

	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		final Graphics2D g2 = (Graphics2D) g;
		// TODO - how to manage node locations?
		// setPreferredSize(dimension);
		if (isActive) {
			setForeground(Color.BLUE);
		} else {
			setForeground(Color.YELLOW);
		}
		g2.fill(getShape());
	}

	@Override
	protected Shape getShape() {
		return getRectangle();
	}

	private Rectangle getRectangle() {
		return new Rectangle(0, 0, extent, extent);
	}

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

	@Override
	protected NodeStyle getStyle() {
		return style;
	}

	@Override
	public void setSize(final TransitionLayoutCommand cmd) {
		this.extent = cmd.getSize();
		setSize(new Dimension(extent, extent));
		repaint(); // REDUNDANT
	}

	@Override
	public void transitionActivated(final TransitionActivationEvent e) {
		final String myId = getId();
		if (myId.equals(e.getTransitionId())) {
			isActive = true;
		}
	}

	@Override
	public void transitionDeactivated(final TransitionActivationEvent e) {
		final String myId = getId();
		if (myId.equals(e.getTransitionId())) {
			isActive = false;
		}
	}

}
