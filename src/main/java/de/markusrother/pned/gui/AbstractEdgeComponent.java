package de.markusrother.pned.gui;

import static de.markusrother.pned.gui.TrigUtils.getRadiansOfDelta;

import java.awt.Component;
import java.awt.Point;
import java.awt.geom.Point2D;

import javax.swing.JComponent;

/**
 * TODO - does this require source and target to have the same parent?
 *
 * @param <T>
 * @param <U>
 */
public class AbstractEdgeComponent<T extends Component & DefinitelyBounded, U extends Component & DefinitelyBounded>
		extends JComponent {

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

	public AbstractEdgeComponent(final T sourceComponent, final Point source, final Point target) {
		this.sourceComponent = sourceComponent;
		this.source = source;
		this.target = target;
	}

	public T getSourceComponent() {
		return sourceComponent;
	}

	public U getTargetComponent() {
		return targetComponent;
	}

	public double getAngle() {
		return getRadiansOfDelta(source, target);
	}

	public void setSourceComponent(final T sourceComponent) {
		this.sourceComponent = sourceComponent;
		connectToSource();
	}

	public void setTargetComponent(final U targetComponent) {
		this.targetComponent = targetComponent;
		connectToTarget();
	}

	public boolean hasSourceComponent() {
		return sourceComponent != null;
	}

	public boolean hasTargetComponent() {
		return targetComponent != null;
	}

	public void removeSourceComponent() {
		sourceComponent = null;
	}

	public void removeTargetComponent() {
		targetComponent = null;
	}

	public void setUnboundSource(final Point source) {
		if (sourceComponent != null) {
			throw new IllegalArgumentException("Edge is connected to source component");
		}
		this.source = source;
		repaint();
	}

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
	 */
	public void connectToSource() {
		final double angle = getAngle();
		final Point intersection = sourceComponent.getLocation();
		final Point boundary = round(sourceComponent.getIntersectionWithBounds(angle));
		// TODO - must move because...
		intersection.translate(boundary.x, boundary.y);
		source = intersection;
	}

	/**
	 * TODO - Currently requires component to be added to parent already, to
	 * retrieve position!
	 * 
	 * TODO - should this become the setter?
	 */
	public void connectToTarget() {
		final double angle = getAngle();
		final Point intersection = targetComponent.getLocation();
		final Point boundary = round(targetComponent.getIntersectionWithBounds(angle + Math.PI));
		// TODO - must move because...
		intersection.translate(boundary.x, boundary.y);
		target = intersection;
	}

}
