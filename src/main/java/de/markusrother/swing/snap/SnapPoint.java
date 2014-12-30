package de.markusrother.swing.snap;

import java.awt.Component;
import java.awt.Point;

/**
 * temporary stateless object!
 *
 * @author Markus Rother
 * @version 1.0
 */
public class SnapPoint extends Point {

	private final Component targetComponent;
	private final SnapPointComponent snapPointComponent;

	/**
	 * <p>Constructor for SnapPoint.</p>
	 *
	 * @param snapPointComponent a {@link de.markusrother.swing.snap.SnapPointComponent} object.
	 * @param targetComponent a {@link java.awt.Component} object.
	 * @param center a {@link java.awt.Point} object.
	 */
	SnapPoint(final SnapPointComponent snapPointComponent, final Component targetComponent, final Point center) {
		super(center);
		this.targetComponent = targetComponent;
		this.snapPointComponent = snapPointComponent;
	}

	/**
	 * <p>Getter for the field <code>targetComponent</code>.</p>
	 *
	 * @return a {@link java.awt.Component} object.
	 */
	public Component getTargetComponent() {
		return targetComponent;
	}

	/**
	 * <p>Getter for the field <code>snapPointComponent</code>.</p>
	 *
	 * @return a {@link de.markusrother.swing.snap.SnapPointComponent} object.
	 */
	public SnapPointComponent getSnapPointComponent() {
		return snapPointComponent;
	}

	/**
	 * <p>setVisible.</p>
	 *
	 * @param flag a boolean.
	 */
	public void setVisible(final boolean flag) {
		snapPointComponent.setVisible(flag);
	}

	/**
	 * <p>addSnapPointListener.</p>
	 *
	 * @param l a {@link de.markusrother.swing.snap.SnapPointListener} object.
	 */
	public void addSnapPointListener(final SnapPointListener l) {
		// TODO - If listeners are managed by snapTarget, we reduce coupling of
		// snapComponent with this framework, allowing to use any component as a
		// snapPointComponent.
		snapPointComponent.addSnapPointListener(l);
	}

	/**
	 * <p>hasSameSnapPointComponent.</p>
	 *
	 * @param other a {@link de.markusrother.swing.snap.SnapPoint} object.
	 * @return a boolean.
	 */
	public boolean hasSameSnapPointComponent(final SnapPoint other) {
		return snapPointComponent == other.getSnapPointComponent();
	}

	/**
	 * <p>setPermanentlyVisible.</p>
	 *
	 * @param flag a boolean.
	 */
	public void setPermanentlyVisible(final boolean flag) {
		snapPointComponent.setPermanentlyVisible(flag);
	}
}
