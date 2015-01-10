package de.markusrother.pned.gui.core.model;

import java.awt.Color;

/**
 * <p>
 * Mutable model for colored objects, offering different colors for differnt
 * states.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface ColorsModel {

	/**
	 * <p>getDefaultColor.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	Color getDefaultColor();

	/**
	 * <p>setDefaultColor.</p>
	 *
	 * @param color a {@link java.awt.Color} object.
	 */
	void setDefaultColor(Color color);

	/**
	 * <p>getHoverColor.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	Color getHoverColor();

	/**
	 * <p>setHoverColor.</p>
	 *
	 * @param color a {@link java.awt.Color} object.
	 */
	void setHoverColor(Color color);

	/**
	 * <p>getValidColor.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	Color getValidColor();

	/**
	 * <p>setValidColor.</p>
	 *
	 * @param color a {@link java.awt.Color} object.
	 */
	void setValidColor(Color color);

	/**
	 * <p>getInvalidColor.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	Color getInvalidColor();

	/**
	 * <p>setInvalidColor.</p>
	 *
	 * @param color a {@link java.awt.Color} object.
	 */
	void setInvalidColor(Color color);

	/**
	 * <p>getSelectionColor.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	Color getSelectionColor();

	/**
	 * <p>setSelectionColor.</p>
	 *
	 * @param color a {@link java.awt.Color} object.
	 */
	void setSelectionColor(Color color);

}
