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
 */
class SnapPointLayer extends JPanel {

	private static final int borderWidth = 10;

	private final SnapTarget snapTargetPanel;
	private final List<SnapPointComponent> snapPointComponents;

	SnapPointLayer(final SnapTarget snapTargetPanel) {
		// Move LM to caller
		super(new SnapLayoutManager());
		this.snapTargetPanel = snapTargetPanel;
		this.snapPointComponents = new LinkedList<>();
		setOpaque(false);
	}

	@Override
	public Dimension getPreferredSize() {
		// TODO - get preferred bounds of points then calculate the preferred
		// size.
		final Dimension d = getTargetComponent().getPreferredSize();
		return new Dimension(d.width + borderWidth, d.height + borderWidth);
	}

	private Component getTargetComponent() {
		return snapTargetPanel.getTargetComponent();
	}

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

	void showSnapPoints() {
		for (final SnapPointComponent snapPoint : snapPointComponents) {
			snapPoint.setVisible(true);
		}
	}

	void hideSnapPoints() {
		for (final SnapPointComponent snapPoint : snapPointComponents) {
			snapPoint.setVisible(false);
		}
	}

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

	List<SnapPointComponent> getSnapPointComponents() {
		return Collections.unmodifiableList(snapPointComponents);
	}

}
