package de.markusrother.pned.gui.core.model;

import java.awt.Color;

/**
 * <p>
 * Mutable model for colored objects, offering different colors for differnt
 * states.
 * </p>
 *
 */
public interface ColorsModel {

	Color getDefaultColor();

	void setDefaultColor(Color color);

	Color getHoverColor();

	void setHoverColor(Color color);

	Color getValidColor();

	void setValidColor(Color color);

	Color getInvalidColor();

	void setInvalidColor(Color color);

	Color getSelectionColor();

	void setSelectionColor(Color color);

}
