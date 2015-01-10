package de.markusrother.pned.gui.core.model;

import java.awt.Color;

import de.markusrother.swing.ChangeEventSource;

/**
 * <p>
 * Mutable model for colored objects, where its color does <b>not</b> change
 * depending on its state.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface ColorModel
	extends
		ChangeEventSource {

	/**
	 * <p>
	 * Returns this objects color.
	 * </p>
	 *
	 * @return a {@link java.awt.Color} - the objects current color.
	 */
	Color getColor();

	/**
	 * <p>
	 * Sets this objects color.
	 * </p>
	 *
	 * @param color
	 *            a {@link java.awt.Color} - the objects new color.
	 */
	void setColor(Color color);

}
