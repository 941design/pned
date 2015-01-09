package de.markusrother.pned.gui.core.model;

import java.awt.Font;

import de.markusrother.swing.ChangeEventSource;

/**
 * <p>FontModel interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface FontModel
	extends
		ChangeEventSource {

	/**
	 * <p>setFontStyle.</p>
	 *
	 * @param fontStyle a int.
	 */
	void setFontStyle(int fontStyle);

	/**
	 * <p>getFontStyle.</p>
	 *
	 * @return a int.
	 */
	int getFontStyle();

	/**
	 * <p>setFontName.</p>
	 *
	 * @param fontName a {@link java.lang.String} object.
	 */
	void setFontName(String fontName);

	/**
	 * <p>getFontName.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getFontName();

	/**
	 * <p>getFont.</p>
	 *
	 * @return a {@link java.awt.Font} object.
	 */
	Font getFont();

}
