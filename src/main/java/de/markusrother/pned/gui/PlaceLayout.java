package de.markusrother.pned.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;

// TODO - create LayoutManagerAdapter?
/**
 * used to manage markings in places
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PlaceLayout
	implements
		LayoutManager {

	/** Constant <code>CENTER="CENTER"</code> */
	public static final String CENTER = "CENTER";

	private Component center;

	/** {@inheritDoc} */
	@Override
	public void addLayoutComponent(final String name, final Component comp) {
		switch (name) {
		case CENTER:
			center = comp;
			break;
		default:
			throw new IllegalArgumentException("Invalid constraint " + name);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void removeLayoutComponent(final Component comp) {
		// TODO
		throw new RuntimeException("TODO");
	}

	/** {@inheritDoc} */
	@Override
	public Dimension preferredLayoutSize(final Container parent) {
		// TODO
		throw new RuntimeException("TODO");
	}

	/** {@inheritDoc} */
	@Override
	public Dimension minimumLayoutSize(final Container parent) {
		// TODO
		throw new RuntimeException("TODO");
	}

	/** {@inheritDoc} */
	@Override
	public void layoutContainer(final Container parent) {
		// TODO - insests vs. bounds?
		final Rectangle b = parent.getBounds();
		final double centerX = b.getCenterX() - b.x;
		final double centerY = b.getCenterY() - b.y;
		// System.out.println(b);
		if (center != null) {
			final Dimension d = center.getPreferredSize();
			// System.out.println(d);
			final double offsetX = d.width / 2.0;
			final double offsetY = d.height / 2.0;
			final Point origin = new Point( //
					(int) Math.floor(centerX - offsetX + 0.5), //
					(int) Math.floor(centerY - offsetY + 0.5));
			// System.out.println(origin);
			center.setBounds(new Rectangle(origin, d));
		}
	}

}
