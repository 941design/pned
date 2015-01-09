package de.markusrother.pned.gui.model;

import java.awt.Color;

import javax.swing.border.Border;

import de.markusrother.swing.ChangeEventSource;

/**
 * <p>
 * NodeStyleModel interface.
 * </p>
 * 
 * FIXME - rename to StyleModel
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface NodeStyleModel
	extends
		ChangeEventSource,
		SizeModel {

	/**
	 * <p>
	 * getDefaultColor.
	 * </p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	Color getDefaultColor();

	/**
	 * <p>
	 * getSelectionColor.
	 * </p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	Color getSelectionColor();

	/**
	 * <p>
	 * getHoverColor.
	 * </p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	Color getHoverColor();

	/**
	 * <p>
	 * getDefaultBorder.
	 * </p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	Border getDefaultBorder();

	/**
	 * <p>
	 * getSelectionBorder.
	 * </p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	Border getSelectionBorder();

	/**
	 * <p>
	 * getHoverBorder.
	 * </p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	Border getHoverBorder();

}
