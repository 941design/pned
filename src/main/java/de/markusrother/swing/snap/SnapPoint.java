package de.markusrother.swing.snap;

import java.awt.Component;
import java.awt.Point;

/**
 * temporary stateless object!
 */
public class SnapPoint extends Point {

	private final Component targetComponent;
	private final SnapPointComponent snapPointComponent;

	SnapPoint(final SnapPointComponent snapPointComponent, final Component targetComponent, final Point center) {
		super(center);
		this.targetComponent = targetComponent;
		this.snapPointComponent = snapPointComponent;
	}

	public Component getTargetComponent() {
		return targetComponent;
	}

	public SnapPointComponent getSnapPointComponent() {
		return snapPointComponent;
	}

	public void setVisible(final boolean flag) {
		snapPointComponent.setVisible(flag);
	}

	public void addSnapPointListener(final SnapPointListener l) {
		// TODO - If listeners are managed by snapTarget, we reduce coupling of
		// snapComponent with this framework, allowing to use any component as a
		// snapPointComponent.
		snapPointComponent.addSnapPointListener(l);
	}

	public boolean hasSameSnapPointComponent(final SnapPoint other) {
		return snapPointComponent == other.getSnapPointComponent();
	}

	public void setPermanentlyVisible(final boolean flag) {
		snapPointComponent.setPermanentlyVisible(flag);
	}
}
