package de.markusrother.pned.gui.components;

import java.awt.Color;

import javax.swing.border.Border;
import javax.swing.event.ChangeListener;

/**
 * <p>NodeStyleModel interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface NodeStyleModel {

	/**
	 * <p>addChangeListener.</p>
	 *
	 * @param listener a {@link javax.swing.event.ChangeListener} object.
	 */
	void addChangeListener(ChangeListener listener);

	/**
	 * <p>removeChangeListener.</p>
	 *
	 * @param listener a {@link javax.swing.event.ChangeListener} object.
	 */
	void removeChangeListener(ChangeListener listener);

	/**
	 * <p>getSize.</p>
	 *
	 * @return a int.
	 */
	int getSize();

	/**
	 * <p>setSize.</p>
	 *
	 * @param size a int.
	 */
	void setSize(int size);

	/**
	 * <p>getDefaultColor.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	Color getDefaultColor();

	/**
	 * <p>getSelectionColor.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	Color getSelectionColor();

	/**
	 * <p>getHoverColor.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	Color getHoverColor();

	/**
	 * <p>getDefaultBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	Border getDefaultBorder();

	/**
	 * <p>getSelectionBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	Border getSelectionBorder();

	/**
	 * <p>getHoverBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	Border getHoverBorder();

}
