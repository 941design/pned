package de.markusrother.swing.snap;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;

/**
 * Component for painting the actual grid. To be toggled on/off.
 *
 * TODO - drag and drop
 *
 * TODO - toggle visibility
 *
 * TODO - When to validate() ? What is revalidate() vs. validate() !?
 *
 * TODO - Get snap targets for a given point. Snap targets are composed of the
 * nearestGridPoint and component that implement SnapTarget.
 *
 * TODO - factor SnapGridComponent!!!
 *
 * TODO - introduce layers!
 *
 * TODO - use MouseAdapter!
 *
 * @author Markus Rother
 * @version 1.0
 */
public class SnapGridComponent extends JComponent {

	// TODO - wrap in settings object!
	private final Color gridColor;
	// TODO - dimension should be more or less infinite!
	private final Dimension gridDimension;
	private final List<SnapTarget> snapTargets;
	private SnapPoint currentSnapPoint;
	private final SnapGridMouseListener snapGridMouseListener;

	/**
	 * <p>
	 * Constructor for SnapGridComponent.
	 * </p>
	 *
	 * @param gridDimension
	 *            a {@link java.awt.Dimension} object.
	 * @param gridColor
	 *            a {@link java.awt.Color} object.
	 */
	public SnapGridComponent(final Dimension gridDimension, final Color gridColor) {
		this.gridDimension = gridDimension;
		this.gridColor = gridColor;
		snapTargets = new LinkedList<>();
		snapGridMouseListener = new SnapGridMouseListener(this);
		addMouseListener(snapGridMouseListener);
		addMouseMotionListener(snapGridMouseListener);
	}

	/**
	 * <p>
	 * calculatePoint.
	 * </p>
	 *
	 * @param x
	 *            The x coordinate.
	 * @param y
	 *            The y coordinate.
	 * @return The absolute location of the given coordinate as
	 *         {@link java.awt.Point}.
	 */
	private Point calculatePoint(final int x, final int y) {
		return new Point(gridDimension.width * x, gridDimension.height * y);
	}

	/**
	 * {@inheritDoc}
	 *
	 * Paints grid in entire (?) parent panel. TODO - What happens on resize.
	 * TODO - Does this also 'paint' in invisible area?
	 */
	@Override
	protected void paintComponent(final Graphics g) {
		// TODO - Overridden, because ...
		super.paintComponent(g);
		g.setColor(gridColor);
		final int height = this.getHeight();
		final int width = this.getWidth();

		// Drawing vertical lines:
		// TODO - offset for actual line width!
		assert (gridDimension.width > 0);
		for (int x = gridDimension.width; x <= width; x = x + gridDimension.width) {
			// TODO - different line styles!
			g.drawLine(x, 0, x, height);
		}

		// Drawing horizontal lines:
		// TODO - offset for actual line height!
		assert (gridDimension.height > 0);
		for (int y = gridDimension.height; y <= height; y = y + gridDimension.height) {
			// TODO - different line styles!
			g.drawLine(0, y, width, y);
		}
	}

	/**
	 * <p>
	 * findNearestGridSnapPoint.
	 * </p>
	 *
	 * @param point
	 *            a {@link java.awt.Point} object.
	 * @return a {@link de.markusrother.swing.snap.SnapPoint} object.
	 */
	private SnapPoint findNearestGridSnapPoint(final Point point) {
		// FIXME, WTF - What is the difference between this and
		// findNearsestSnapTarget()?
		// TODO - this is still somewhat imprecise!
		final int x = point.x //
				+ (point.x % gridDimension.width <= gridDimension.width / 2 ? 0 : gridDimension.width) //
				- point.x % gridDimension.width;
		final int y = point.y //
				+ (point.y % gridDimension.height <= gridDimension.height / 2 ? 0 : gridDimension.height) //
				- point.y % gridDimension.height;
		final Point gridCoordinate = new Point(x, y);
		// TODO - revert to factory!
		// FIXME - Lazy creation causes flickering and leak!
		final SnapPointComponent snapPointComponent = new SnapPointComponent();
		final Dimension dim = snapPointComponent.getPreferredSize();
		final Point origin = new Point(gridCoordinate);
		origin.translate(-dim.width / 2, -dim.height / 2);
		snapPointComponent.setBounds(new Rectangle(origin, snapPointComponent.getPreferredSize()));
		final Component targetComponent = this;
		return new SnapPoint(snapPointComponent, targetComponent, gridCoordinate);
	}

	/**
	 * <p>
	 * hasCurrentSnapPoint.
	 * </p>
	 *
	 * @return a boolean.
	 */
	private boolean hasCurrentSnapPoint() {
		return currentSnapPoint != null;
	}

	/**
	 * <p>
	 * Getter for the field <code>currentSnapPoint</code>.
	 * </p>
	 *
	 * @return a {@link de.markusrother.swing.snap.SnapPoint} object.
	 */
	public SnapPoint getCurrentSnapPoint() {
		return currentSnapPoint;
	}

	/**
	 * <p>
	 * Setter for the field <code>currentSnapPoint</code>.
	 * </p>
	 *
	 * @param snapPoint
	 *            a {@link de.markusrother.swing.snap.SnapPoint} object.
	 */
	private void setCurrentSnapPoint(final SnapPoint snapPoint) {
		currentSnapPoint = snapPoint;
	}

	/**
	 * <p>
	 * disableCurrentSnapPoint.
	 * </p>
	 */
	void disableCurrentSnapPoint() {
		if (currentSnapPoint == null) {
			return;
		}
		currentSnapPoint.setVisible(false);
		if (currentSnapPoint.getTargetComponent() == this) {
			remove(currentSnapPoint.getSnapPointComponent());
		}
	}

	/**
	 * <p>
	 * findNearestSnapTarget.
	 * </p>
	 *
	 * @param source
	 *            a {@link java.awt.Point} object.
	 * @return a {@link de.markusrother.swing.snap.SnapPoint} object.
	 */
	public SnapPoint findNearestSnapTarget(final Point source) {
		// TODO - If two targets share the same point, what should happen?
		SnapPoint point;
		point = findNearestGridSnapPoint(source);
		// snapTargets == children that are of type SnapComponent
		for (final SnapTarget snapTargetPanel : snapTargets) {
			for (final SnapPoint snapPoint : snapTargetPanel.getSnapPoints()) {
				if (snapPoint.distance(source) < point.distance(source)) {
					point = snapPoint;
				}
			}
		}
		if (point.getTargetComponent() == this) {
			// TODO - We have to make sure this does not leak!
			point.getSnapPointComponent().setVisible(false);
			add(point.getSnapPointComponent());
		}
		return point;
	}

	/**
	 * <p>
	 * createSnapTarget.
	 * </p>
	 *
	 * @param component
	 *            a {@link java.awt.Component} object.
	 * @param origin
	 *            a {@link java.awt.Point} object.
	 * @return a {@link de.markusrother.swing.snap.SnapTarget} object.
	 */
	public SnapTarget createSnapTarget(final Component component, final Point origin) {
		// TODO - Positioning should be done by a LayoutManager!
		// component.addMouseListener(snapGridMouseListener);
		// component.addMouseMotionListener(snapGridMouseListener);
		final SnapTarget snapTarget = new SnapTarget(component);
		// snapTarget.addMouseListener(snapGridMouseListener);
		// snapTarget.addMouseMotionListener(snapGridMouseListener);
		snapTargets.add(snapTarget);
		add(snapTarget);
		// TODO - include offsets in point, so that the actual wrapped component
		// is located at origin!
		snapTarget.setBounds(new Rectangle(origin, snapTarget.getPreferredSize()));
		return snapTarget;
	}

	/**
	 * <p>
	 * createSnapPoint.
	 * </p>
	 *
	 * @param snapTarget
	 *            a {@link de.markusrother.swing.snap.SnapTarget} object.
	 * @param constraint
	 *            a {@link java.lang.String} object.
	 * @return a {@link java.awt.Component} object.
	 */
	public Component createSnapPoint(final SnapTarget snapTarget, final String constraint) {
		final Component component = snapTarget.createSnapPoint(constraint);
		component.addMouseListener(snapGridMouseListener);
		component.addMouseMotionListener(snapGridMouseListener);
		return component;
	}

	/**
	 * <p>
	 * toggleNearestSnapPoint.
	 * </p>
	 *
	 * @param point
	 *            a {@link java.awt.Point} object.
	 */
	public void toggleNearestSnapPoint(final Point point) {
		// TODO - center vs. origin
		final SnapPoint nextSnapPoint = findNearestSnapTarget(point);
		// FIXME - lazy creation of snapPointcomponents causes leak, and
		// makes toggle more difficult!
		if (hasCurrentSnapPoint() && getCurrentSnapPoint().hasSameSnapPointComponent(nextSnapPoint)) {
			return;
		}
		disableCurrentSnapPoint();
		// FIXME - make sure that entirely covered points are not shown, but
		// visible points are on top of components!
		nextSnapPoint.setVisible(true); // OBSOLETE?
		setCurrentSnapPoint(nextSnapPoint);
	}
}
