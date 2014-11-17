package de.markusrother.swing.snap;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

public class SnapLayoutManager implements LayoutManager {

	public static final String NORTH = "NORTH";
	public static final String SOUTH = "SOUTH";
	public static final String WEST = "WEST";
	public static final String EAST = "EAST";

	private Component north;
	private Component south;
	private Component west;
	private Component east;

	@Override
	public void addLayoutComponent(final String name, final Component comp) {
		synchronized (comp.getTreeLock()) {
			if (!(comp instanceof SnapPointComponent)) {
				throw new IllegalArgumentException("Can only add SnapPoints");
			}
			switch (name) {
			case NORTH:
				north = comp;
				break;
			case SOUTH:
				south = comp;
				break;
			case WEST:
				west = comp;
				break;
			case EAST:
				east = comp;
				break;
			default:
				throw new IllegalArgumentException("No such constraint " + name);
			}
		}
	}

	@Override
	public void removeLayoutComponent(final Component comp) {
		// TODO
		throw new RuntimeException("TODO");
	}

	@Override
	public Dimension preferredLayoutSize(final Container parent) {
		// TODO
		throw new RuntimeException("TODO");
	}

	@Override
	public Dimension minimumLayoutSize(final Container parent) {
		// TODO
		throw new RuntimeException("TODO");
	}

	@Override
	public void layoutContainer(final Container parent) {
		// Borrowed from BorderLayout:
		synchronized (parent.getTreeLock()) {
			final Insets insets = parent.getInsets();
			final int top = insets.top;
			final int bottom = parent.getHeight() - insets.bottom;
			final int left = insets.left;
			final int right = parent.getWidth() - insets.right;
			final int centerY = (right - left) / 2;
			final int centerX = (bottom - top) / 2;

			if (north != null) {
				final Dimension d = north.getPreferredSize();
				north.setBounds(centerY, top, d.width, d.height);
			}
			if (south != null) {
				final Dimension d = south.getPreferredSize();
				south.setBounds(centerY, bottom - d.height, d.width, d.height);
			}
			if (west != null) {
				final Dimension d = west.getPreferredSize();
				west.setBounds(left, centerX, d.width, d.height);
			}
			if (east != null) {
				final Dimension d = east.getPreferredSize();
				east.setBounds(right - d.width, centerX, d.width, d.height);
			}
		}
	}

}
