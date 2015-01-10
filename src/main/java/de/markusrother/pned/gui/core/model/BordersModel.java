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

	Border getHoverBorder();

	Border getSelectionBorder();

}
