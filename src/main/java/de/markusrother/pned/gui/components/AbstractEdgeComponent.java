package de.markusrother.pned.gui.components;

import static de.markusrother.util.MathUtils.getRadiansOfDelta;

import java.awt.Component;
import java.awt.Point;
import java.awt.geom.Point2D;

import javax.swing.JComponent;

import de.markusrother.pned.gui.DefinitelyBounded;

/**
 * TODO - does this require source and target to have the same parent?
 *
 * @author Markus Rother
 * @version 1.0
 */
public class AbstractEdgeComponent<T extends Component & DefinitelyBounded, U extends Component & DefinitelyBounded> extends JComponent {

	/**
	 * <p>round.</p>
	 *
	 * @param point a {@link java.awt.geom.Point2D} object.
	 * @return a {@link java.awt.Point} object.
	 */
	private static Point round(final Point2D point) {
		// TODO - could go to util class
		return new Point( //
				(int) Math.floor(point.getX() + 0.5), //
				(int) Math.floor(point.getY() + 0.5));
	}

	Point source;
	Point target;
	T sourceComponent;
	U targetComponent;

	/**
	 * <p>Constructor for AbstractEdgeComponent.</p>
	 *
	 * @param sourceComponent a T object.
	 * @param targetComponent a U object.
	 * @param sourceComponent a T object.
	 * @param source a {@link java.awt.Point} object.
	 * @param targetComponent a U object.
	 * @param target a {@link java.awt.Point} object.
	 */
	public AbstractEdgeComponent(final T sourceComponent, final U targetComponent, final Point source,
			final Point target) {
		this.sourceComponent = sourceComponent;
		this.targetComponent = targetComponent;
		this.source = source;
		this.target = target;
	}

	/**
	 * <p>Getter for the field <code>sourceComponent</code>.</p>
	 *
	 * @return a T object.
	 */
	public T getSourceComponent() {
		return sourceComponent;
	}

	/**
	 * <p>Getter for the field <code>targetComponent</code>.</p>
	 *
	 * @return a U object.
	 */
	public U getTargetComponent() {
		return targetComponent;
	}

	/**
	 * <p>getAngle.</p>
	 *
	 * @return a double.
	 */
	public double getAngle() {
		return getRadiansOfDelta(source, target);
	}

	/**
	 * <p>Setter for the field <code>sourceComponent</code>.</p>
	 *
	 * @param sourceComponent a T object.
	 */
	public void setSourceComponent(final T sourceComponent) {
		this.sourceComponent = sourceComponent;
		connectToSource();
	}

	/**
	 * <p>Setter for the field <code>targetComponent</code>.</p>
	 *
	 * @param targetComponent a U object.
	 */
	public void setTargetComponent(final U targetComponent) {
		this.targetComponent = targetComponent;
		connectToTarget();
	}

	/**
	 * <p>hasSourceComponent.</p>
	 *
	 * @return a boolean.
	 */
	public boolean hasSourceComponent() {
		return sourceComponent != null;
	}

	/**
	 * <p>hasTargetComponent.</p>
	 *
	 * @return a boolean.
	 */
	public boolean hasTargetComponent() {
		return targetComponent != null;
	}

	/**
	 * <p>removeSourceComponent.</p>
	 */
	public void removeSourceComponent() {
		sourceComponent = null;
	}

	/**
	 * <p>removeTargetComponent.</p>
	 */
	public void removeTargetComponent() {
		targetComponent = null;
	}

	/**
	 * <p>setUnboundSource.</p>
	 *
	 * @param source a {@link java.awt.Point} object.
	 */
	public void setUnboundSource(final Point source) {
		if (sourceComponent != null) {
			throw new IllegalArgumentException("Edge is connected to source component");
		}
		this.source = source;
		repaint();
	}

	/**
	 * <p>setUnboundTarget.</p>
	 *
	 * @param target a {@link java.awt.Point} object.
	 */
	public void setUnboundTarget(final Point target) {
		if (targetComponent != null) {
			throw new IllegalArgumentException("Edge is connected to target component");
		}
		this.target = target;
		repaint();
	}

	/**
	 * TODO - Currently requires component to be added to parent already, to
	 * retrieve position!
	 *
	 * TODO - could be done in revalidate()!
	 */
	public void connectToSource() {
		final double angle = getAngle();
		connectToSource(angle);
	}

	/**
	 * <p>connectToSource.</p>
	 *
	 * @param angle a double.
	 */
	private void connectToSource(final double angle) {
		final Point intersection = sourceComponent.getLocation();
		final Point boundary = round(sourceComponent.getBoundaryPoint(angle));
		// TODO - must move because...
		intersection.translate(boundary.x, boundary.y);
		source = intersection;
	}

	/**
	 * TODO - Currently requires component to be added to parent already, to
	 * retrieve position!
	 *
	 * TODO - should this become the setter?
	 *
	 * TODO - could be done in revalidate()!
	 */
	public void connectToTarget() {
		final double angle = getAngle();
		connectToTarget(angle);
	}

	/**
	 * <p>connectToTarget.</p>
	 *
	 * @param angle a double.
	 */
	private void connectToTarget(final double angle) {
		final Point intersection = targetComponent.getLocation();
		final Point boundary = round(targetComponent.getBoundaryPoint(angle + Math.PI));
		// TODO - must move because...
		intersection.translate(boundary.x, boundary.y);
		target = intersection;
	}

}
