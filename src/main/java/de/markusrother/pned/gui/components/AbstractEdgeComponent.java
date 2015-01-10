package de.markusrother.pned.gui.components;

import static de.markusrother.util.MathUtils.getRadiansOfDelta;
import static de.markusrother.util.MathUtils.round;

import java.awt.Component;
import java.awt.Point;

import javax.swing.JComponent;

/**
 * <p>
 * Abstract superclass for edges connecting components. Either source or target
 * component may not be set. Source or target is then called unbound.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public abstract class AbstractEdgeComponent<T extends Component & Bounded, U extends Component & Bounded> extends JComponent {

	Point source;
	Point target;
	T sourceComponent;
	U targetComponent;

	/**
	 * <p>
	 * Constructor for AbstractEdgeComponent.
	 * </p>
	 *
	 * @param sourceComponent
	 *            a T object.
	 * @param targetComponent
	 *            a U object.
	 * @param sourceComponent
	 *            a T object.
	 * @param source
	 *            a {@link java.awt.Point} object.
	 * @param targetComponent
	 *            a U object.
	 * @param target
	 *            a {@link java.awt.Point} object.
	 */
	public AbstractEdgeComponent(final T sourceComponent, final U targetComponent, final Point source,
			final Point target) {
		this.sourceComponent = sourceComponent;
		this.targetComponent = targetComponent;
		this.source = source;
		this.target = target;
	}

	/**
	 * <p>
	 * Getter for the field <code>sourceComponent</code>.
	 * </p>
	 *
	 * @return a T object.
	 */
	public T getSourceComponent() {
		return sourceComponent;
	}

	/**
	 * <p>
	 * Getter for the field <code>targetComponent</code>.
	 * </p>
	 *
	 * @return a U object.
	 */
	public U getTargetComponent() {
		return targetComponent;
	}

	/**
	 * <p>
	 * getAngle.
	 * </p>
	 *
	 * @return a double.
	 */
	public double getAngle() {
		return getRadiansOfDelta(source, target);
	}

	/**
	 * <p>
	 * Setter for the field <code>sourceComponent</code>.
	 * </p>
	 *
	 * @param sourceComponent
	 *            a T object.
	 */
	public void setSourceComponent(final T sourceComponent) {
		this.sourceComponent = sourceComponent;
		reconnectToSource();
	}

	/**
	 * <p>
	 * Setter for the field <code>targetComponent</code>.
	 * </p>
	 *
	 * @param targetComponent
	 *            a U object.
	 */
	public void setTargetComponent(final U targetComponent) {
		this.targetComponent = targetComponent;
		reconnectToTarget();
	}

	/**
	 * <p>
	 * hasSourceComponent.
	 * </p>
	 *
	 * @return a boolean.
	 */
	public boolean hasSourceComponent() {
		return sourceComponent != null;
	}

	/**
	 * <p>
	 * hasTargetComponent.
	 * </p>
	 *
	 * @return a boolean.
	 */
	public boolean hasTargetComponent() {
		return targetComponent != null;
	}

	/**
	 * <p>
	 * removeSourceComponent.
	 * </p>
	 */
	public void removeSourceComponent() {
		sourceComponent = null;
	}

	/**
	 * <p>
	 * removeTargetComponent.
	 * </p>
	 */
	public void removeTargetComponent() {
		targetComponent = null;
	}

	/**
	 * <p>
	 * setUnboundSource.
	 * </p>
	 *
	 * @param source
	 *            a {@link java.awt.Point} object.
	 */
	public void setUnboundSource(final Point source) {
		if (sourceComponent != null) {
			throw new IllegalArgumentException("Edge is connected to source component");
		}
		this.source = source;
		repaint();
	}

	/**
	 * <p>
	 * setUnboundTarget.
	 * </p>
	 *
	 * point may or may not be a valid target. We are not interested on that.
	 * This method is not responsible for connecting to targets! That is done
	 * when entering potential target components. Also, we rely on target
	 * removal on exiting components. Therefore, we can safely connect to the
	 * point, without being bound to any target component.
	 *
	 * @param target
	 *            a {@link java.awt.Point} object.
	 */
	public void setUnboundTarget(final Point target) {
		if (targetComponent != null) {
			// Edge is already connected!
			return;
		}
		this.target = target;
		repaint();
	}

	/**
	 * <p>reconnectToSource.</p>
	 */
	public void reconnectToSource() {
		final double angle = getAngle();
		reconnectToSource(angle);
	}

	/**
	 * <p>
	 * connectToSource.
	 * </p>
	 *
	 * @param angle
	 *            a double.
	 */
	private void reconnectToSource(final double angle) {
		final Point intersection = sourceComponent.getLocation();
		final Point boundary = round(sourceComponent.getBoundaryPoint(angle));
		intersection.translate(boundary.x, boundary.y);
		source = intersection;
	}

	/**
	 * <p>reconnectToTarget.</p>
	 */
	public void reconnectToTarget() {
		final double angle = getAngle();
		reconnectToTarget(angle);
	}

	/**
	 * <p>
	 * connectToTarget.
	 * </p>
	 *
	 * @param angle
	 *            a double.
	 */
	private void reconnectToTarget(final double angle) {
		final Point intersection = targetComponent.getLocation();
		final Point boundary = round(targetComponent.getBoundaryPoint(angle + Math.PI));
		// TODO - must move because...
		intersection.translate(boundary.x, boundary.y);
		target = intersection;
	}

}
