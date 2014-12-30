package de.markusrother.swing.snap;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JComponent;

import de.markusrother.swing.HoverListener;

// TODO - The goal is to make this independent of the layer management!
/**
 * <p>SnapPointComponent class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
class SnapPointComponent extends JComponent {

	/** Constant <code>hoverColor</code> */
	protected static final Color hoverColor = Color.RED;
	/** Constant <code>standardColor</code> */
	protected static final Color standardColor = Color.BLUE;

	private final Collection<SnapPointListener> snapPointListeners;
	private final Shape highlightArea;
	private boolean permanentlyVisible;
	private Color bgColor;

	/**
	 * <p>Constructor for SnapPointComponent.</p>
	 */
	SnapPointComponent() {
		snapPointListeners = new LinkedList<>();
		highlightArea = new Ellipse2D.Double(0, 0, 10, 10);
		bgColor = standardColor;
		final HoverListener l = new HoverListener() {

			@Override
			protected boolean inHoverArea(final Point p) {
				return highlightArea.contains(p);
			}

			@Override
			protected void startHover(final Component component) {
				bgColor = hoverColor;
				revalidate();
				repaint();
			}

			@Override
			protected void endHover(final Component component) {
				bgColor = standardColor;
				revalidate();
				repaint();
			}
		};
		addMouseMotionListener(l);
		addMouseListener(l);
	}

	/**
	 * <p>addSnapPointListener.</p>
	 *
	 * @param l a {@link de.markusrother.swing.snap.SnapPointListener} object.
	 */
	public void addSnapPointListener(final SnapPointListener l) {
		snapPointListeners.add(l);
	}

	/** {@inheritDoc} */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(10, 10);
	}

	/** {@inheritDoc} */
	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		final Graphics2D g2 = (Graphics2D) g;
		setBackground(bgColor);
		setForeground(bgColor);
		g2.fill(highlightArea);
		// g.fillArc(0, 0, 10, 10, 0, 360);
	}

	/**
	 * <p>getCenter.</p>
	 *
	 * @return a {@link java.awt.Point} object.
	 */
	Point getCenter() {
		return new Point(getX() + 5, getY() + 5);
	}

	/**
	 * <p>fireComponentMovedEvent.</p>
	 *
	 * @param deltaX a int.
	 * @param deltaY a int.
	 */
	void fireComponentMovedEvent(final int deltaX, final int deltaY) {
		for (final SnapPointListener l : snapPointListeners) {
			l.snapPointMoved(deltaX, deltaY);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setVisible(final boolean flag) {
		super.setVisible(flag || permanentlyVisible);
	}

	/**
	 * <p>Setter for the field <code>permanentlyVisible</code>.</p>
	 *
	 * @param flag a boolean.
	 */
	public void setPermanentlyVisible(final boolean flag) {
		permanentlyVisible = flag;
		setVisible(flag);
	}
}
