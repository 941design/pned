package de.markusrother.swing.snap;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

/**
 * Alternatively, create hidden labels or panels for each snapPoint. Which can
 * then be activated. There should be some sort of mechanism to make all
 * snapPoints visible, anyways.
 *
 * Subclass Container or JComponent, instead?
 *
 * Instantiate with a SnapPointFactory which could do the translation?
 * snapPointFactory.create(x,y);
 *
 * If this panel is to contain the entire points, we probably need space at the
 * borders? What if we want to exchange the factory at runtime?
 *
 * Apparently, getX() returns the absolute position in a frame. It is set by
 * setBounds, usually by a layout manager. That is, we do not need the container
 * to compute snap points.
 *
 * @author Markus Rother
 * @version 1.0
 */
class SnapPointLayer extends JPanel {

	/** Constant <code>borderWidth=10</code> */
	private static final int borderWidth = 10;

	private final SnapTarget snapTargetPanel;
	private final List<SnapPointComponent> snapPointComponents;

	/**
	 * <p>Constructor for SnapPointLayer.</p>
	 *
	 * @param snapTargetPanel a {@link de.markusrother.swing.snap.SnapTarget} object.
	 */
	SnapPointLayer(final SnapTarget snapTargetPanel) {
		// Move LM to caller
		super(new SnapLayoutManager());
		this.snapTargetPanel = snapTargetPanel;
		this.snapPointComponents = new LinkedList<>();
		setOpaque(false);
	}

	/** {@inheritDoc} */
	@Override
	public Dimension getPreferredSize() {
		// TODO - get preferred bounds of points then calculate the preferred
		// size.
		final Dimension d = getTargetComponent().getPreferredSize();
		return new Dimension(d.width + borderWidth, d.height + borderWidth);
	}

	/**
	 * <p>getTargetComponent.</p>
	 *
	 * @return a {@link java.awt.Component} object.
	 */
	private Component getTargetComponent() {
		return snapTargetPanel.getTargetComponent();
	}

	/**
	 * <p>createSnapPoint.</p>
	 *
	 * @param constraint a {@link java.lang.String} object.
	 * @param visible a boolean.
	 * @return a {@link java.awt.Component} object.
	 */
	Component createSnapPoint(final String constraint, final boolean visible) {
		// TODO - We may want to use a layout manager with default locations of
		// 4 corners and 4 midpoints.
		// TODO - Somewhat inconsistent, because this method creates AND adds
		// AND returns component.
		final SnapPointComponent snapPoint = snapTargetPanel.createSnapPointComponent(constraint);
		snapPointComponents.add(snapPoint);
		add(snapPoint, constraint);
		snapPoint.setVisible(visible);
		return snapPoint;
	}

	/**
	 * <p>showSnapPoints.</p>
	 */
	void showSnapPoints() {
		for (final SnapPointComponent snapPoint : snapPointComponents) {
			snapPoint.setVisible(true);
		}
	}

	/**
	 * <p>hideSnapPoints.</p>
	 */
	void hideSnapPoints() {
		for (final SnapPointComponent snapPoint : snapPointComponents) {
			snapPoint.setVisible(false);
		}
	}

	/**
	 * <p>getSnapPoints.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	List<SnapPoint> getSnapPoints() {
		final List<SnapPoint> result = new ArrayList<>(snapPointComponents.size());
		final int parentX = snapTargetPanel.getX();
		final int parentY = snapTargetPanel.getY();
		for (final SnapPointComponent snapPointComponent : snapPointComponents) {
			final Point point = snapPointComponent.getCenter();
			point.translate(parentX, parentY);
			result.add(new SnapPoint(snapPointComponent, getTargetComponent(), point));
		}
		return result;
	}

	/**
	 * <p>Getter for the field <code>snapPointComponents</code>.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	List<SnapPointComponent> getSnapPointComponents() {
		return Collections.unmodifiableList(snapPointComponents);
	}

}
