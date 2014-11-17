package de.markusrother.swing.snap;

import java.awt.Color;
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
class SnapPointComponent extends JComponent {

	protected static final Color hoverColor = Color.RED;
	protected static final Color standardColor = Color.BLUE;

	private final Collection<SnapPointListener> snapPointListeners;
	private final Shape highlightArea;
	private boolean permanentlyVisible;
	private Color bgColor;

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
			protected void startHover() {
				bgColor = hoverColor;
				revalidate();
				repaint();
			}

			@Override
			protected void endHover() {
				bgColor = standardColor;
				revalidate();
				repaint();
			}
		};
		addMouseMotionListener(l);
		addMouseListener(l);
	}

	public void addSnapPointListener(final SnapPointListener l) {
		snapPointListeners.add(l);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(10, 10);
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		final Graphics2D g2 = (Graphics2D) g;
		setBackground(bgColor);
		setForeground(bgColor);
		g2.fill(highlightArea);
		// g.fillArc(0, 0, 10, 10, 0, 360);
	}

	Point getCenter() {
		return new Point(getX() + 5, getY() + 5);
	}

	void fireComponentMovedEvent(final int deltaX, final int deltaY) {
		for (final SnapPointListener l : snapPointListeners) {
			l.snapPointMoved(deltaX, deltaY);
		}
	}

	@Override
	public void setVisible(final boolean flag) {
		super.setVisible(flag || permanentlyVisible);
	}

	public void setPermanentlyVisible(final boolean flag) {
		permanentlyVisible = flag;
		setVisible(flag);
	}
}
