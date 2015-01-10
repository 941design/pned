package de.markusrother.pned.gui.core.model;

import java.awt.Font;

import de.markusrother.swing.ChangeEventSource;

/**
 * <p>
 * Mutable model for font-sensitive objects.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see {@link java.awt.Font}
 */
public interface FontModel
	extends
		ChangeEventSource {

	/**
	 * <p>
	 * Gets current font.
	 * </p>
	 *
	 * @return a {@link java.awt.Font} - the current font.
	 */
	Font getFont();

	/**
	 * <p>
	 * Gets current font name.
	 * </p>
	 *
	 * @return a {@link java.lang.String} - the current font name.
	 */
	String getFontName();

	/**
	 * <p>
	 * Sets new font name.
	 * </p>
	 *
	 * @param fontName
	 *            a {@link java.lang.String} - the new font name.
	 */
	void setFontName(String fontName);

	/**
	 * <p>
	 * Gets current font style.
	 * </p>
	 *
	 * @return a int - the current font style.
	 * @see {@link java.awt.Font}
	 */
	int getFontStyle();

	/**
	 * <p>
	 * Sets new font style.
	 * </p>
	 *
	 * @param fontStyle
	 *            a int - the new font style.
	 * @see {@link java.awt.Font}
	 */
	void setFontStyle(int fontStyle);

}
