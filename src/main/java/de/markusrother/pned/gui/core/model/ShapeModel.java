package de.markusrother.pned.gui.core.model;

import java.awt.Shape;
import java.awt.Stroke;

import de.markusrother.swing.ChangeEventSource;

/**
 * <p>
 * Mutable model for objects that have a shape.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface ShapeModel
	extends
		ChangeEventSource {

	/**
	 * <p>
	 * Returns current shape.
	 * </p>
	 *
	 * @return a {@link java.awt.Shape} object.
	 */
	Shape getShape();

	/**
	 * <p>
	 * Sets new shape.
	 * </p>
	 *
	 * @param shape
	 *            a {@link java.awt.Shape} object.
	 */
	void setShape(Shape shape);

	Stroke getStroke();

	void setStroke(Stroke stroke);

}
