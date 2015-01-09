package de.markusrother.pned.gui.model;

import java.awt.Color;
import java.awt.Stroke;

import de.markusrother.swing.ChangeEventSource;

/**
 * <p>
 * Aggregate interface
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface EdgeStyleModel
	extends
		ChangeEventSource,
		ShapeModel,
		SizeModel {

	/**
	 * <p>getDefaultColor.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	Color getDefaultColor();

	/**
	 * <p>setDefaultColor.</p>
	 *
	 * @param black a {@link java.awt.Color} object.
	 */
	void setDefaultColor(Color black);

	/**
	 * <p>getHoverColor.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	Color getHoverColor();

	/**
	 * <p>setHoverColor.</p>
	 *
	 * @param blue a {@link java.awt.Color} object.
	 */
	void setHoverColor(Color blue);

	/**
	 * <p>getValidColor.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	Color getValidColor();

	/**
	 * <p>setValidColor.</p>
	 *
	 * @param green a {@link java.awt.Color} object.
	 */
	void setValidColor(Color green);

	/**
	 * <p>getInvalidColor.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	Color getInvalidColor();

	/**
	 * <p>setInvalidColor.</p>
	 *
	 * @param red a {@link java.awt.Color} object.
	 */
	void setInvalidColor(Color red);

	/**
	 * <p>getStroke.</p>
	 *
	 * @return a {@link java.awt.Stroke} object.
	 */
	Stroke getStroke();

	/**
	 * <p>setStroke.</p>
	 *
	 * @param stroke a {@link java.awt.Stroke} object.
	 */
	void setStroke(Stroke stroke);

}
