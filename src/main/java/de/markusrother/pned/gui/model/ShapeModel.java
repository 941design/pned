package de.markusrother.pned.gui.model;

import java.awt.Shape;

import de.markusrother.swing.ChangeEventSource;

/**
 * <p>ShapeModel interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface ShapeModel
	extends
		ChangeEventSource {

	/**
	 * <p>getShape.</p>
	 *
	 * @return a {@link java.awt.Shape} object.
	 */
	Shape getShape();

	/**
	 * <p>setShape.</p>
	 *
	 * @param shape a {@link java.awt.Shape} object.
	 */
	void setShape(Shape shape);

}
