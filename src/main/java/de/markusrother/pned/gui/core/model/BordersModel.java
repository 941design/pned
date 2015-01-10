package de.markusrother.pned.gui.core.model;

import javax.swing.border.Border;

/**
 * <p>
 * Mutable model for bordered objects, providing different borders for different
 * states.
 * </p>
 */
public interface BordersModel {

	Border getDefaultBorder();

	void setDefaultBorder(Border defaultBorder);

	Border getHoverBorder();

	void setHoverBorder(Border hoverBorder);

	Border getSelectionBorder();

	void setSelectionBorder(Border selectionBorder);

}
