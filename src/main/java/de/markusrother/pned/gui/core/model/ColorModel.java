package de.markusrother.pned.gui.core.model;

import java.awt.Color;

import de.markusrother.swing.ChangeEventSource;

/**
 * <p>ColorModel interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface ColorModel
	extends
		ChangeEventSource {

	/**
	 * <p>getColor.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	Color getColor();

	/**
	 * <p>setColor.</p>
	 *
	 * @param color a {@link java.awt.Color} object.
	 */
	void setColor(Color color);

}
